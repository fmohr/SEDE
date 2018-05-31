package de.upb.sede.composition.graphs;

import java.util.*;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.config.ClassesConfig.MethodInfo;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exceptions.CompositionSemanticException;
import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.gateway.ResolveInfo;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.util.DefaultMap;

public class DataFlowAnalysis {
	private final ResolveInfo resolveInfo;

	// private final List<AcceptDataNode> clientInputNodes = new ArrayList<>();

	private final Map<String, List<FieldType>> fieldnameTypeResult = new HashMap<>();

	private final List<ExecPlan> execPlans;

	private final ExecPlan clientExecPlan;

	private final CompositionGraph dataTransmissionGraph;

	private final DefaultMap<BaseNode, List<FieldType>> nodeConsumingFields = new DefaultMap<>(ArrayList::new);
	private final DefaultMap<FieldType, List<BaseNode>> fieldProducers = new DefaultMap<>(ArrayList::new);

	private final Map<BaseNode, ExecPlan> nodeExecutionAssignment = new HashMap<>();

	private final List<String> returnFieldnames = new ArrayList<>();

	public DataFlowAnalysis(ResolveInfo resolveInfo, List<InstructionNode> instructionNodes) {
		this.resolveInfo = Objects.requireNonNull(resolveInfo);
		clientExecPlan = new ExecPlan(resolveInfo.getClientExecutor());
		execPlans = new ArrayList<>();
		execPlans.add(clientExecPlan);
		dataTransmissionGraph = new CompositionGraph();
		analyzeDataFlow(Objects.requireNonNull(instructionNodes));
	}

	private void analyzeDataFlow(List<InstructionNode> instructionNodes) {
		assert !instructionNodes.isEmpty();
		addInputNodesFieldTypes();
		for (InstructionNode instNode : instructionNodes) {
			resolveExecution(instNode);
			analyzeDataFlow(instNode);
		}
		resolveResults();
		connectDependencyEdges();
		if(resolveInfo.getResolvePolicy().isBlockTillFinished()) {
			addFinishingNodes();
		}
	}

	private void addInputNodesFieldTypes() {
		InputFields inputFields = resolveInfo.getInputFields();
		for (String inputFieldname : inputFields.iterateInputs()) {
			AcceptDataNode acceptNode = clientAcceptInput(inputFieldname);

			boolean isServiceInstance = inputFields.isServiceInstance(inputFieldname);
			String typeName;
			TypeClass typeClass;
			if (isServiceInstance) {
				String serviceClasspath = inputFields.getServiceInstanceHandle(inputFieldname).getClasspath();
				typeName = serviceClasspath;
				typeClass = TypeClass.ServiceInstanceHandle;

				FieldType fieldType = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(fieldType);
			} else {
				typeName = inputFields.getInputFieldType(inputFieldname);
				if(SEDEObject.isPrimitive(typeName)) {
					typeClass = TypeClass.PrimitiveType;
				} else if(SEDEObject.isReal(typeName)) {
					typeClass = TypeClass.RealDataType;
				} else if(SEDEObject.isSemantic(typeName)){
					typeClass = TypeClass.SemanticDataType;
				} else{
					throw new RuntimeException("BUG: The type of the given input field cannot be reolved to a type class, "
							+ inputFieldname + ":" + typeName);
				}
				FieldType fieldType = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(fieldType);
			}
		}
	}

	private void resolveExecution(InstructionNode instNode) {
		ExecPlan resolvedExecPlan = null;
		if (instNode.isContextAFieldname()) {
			/*
			 * instructions context is field. so it needs to be assigned to the correct
			 * execution.
			 */
			String serviceInstanceFieldname = instNode.getContext();

			List<FieldType> serviceInstances = resolveFieldname(serviceInstanceFieldname);

			if (serviceInstances.size() != 1) {
				throw new CompositionSemanticException(
						"The context of the instruction cannot be resolved: " + instNode.toString());
			}
			FieldType serviceInstanceField = serviceInstances.get(0);

			if (serviceInstanceField.isServiceInstanceHandle()) {
				/*
				 * service handles are in the input fields:
				 */
				ServiceInstanceHandle serviceInstanceHandle = resolveInfo.getInputFields()
						.getServiceInstanceHandle(serviceInstanceFieldname);
				resolvedExecPlan = getOrCreateExecutionForId(serviceInstanceHandle.getExecutorId());
			} else {
				/*
				 * The field is bound to a new service instance. producer is another instruction
				 * node.
				 */
				BaseNode producer = serviceInstanceField.getProducer();
				resolvedExecPlan = getAssignedExec(producer);
			}
		} else {
			/*
			 * instructions context is a service classpath.
			 */
			String serviceClasspath = instNode.getContext();
			boolean found = false;
			for (ExecPlan exec : execPlans) {
				ExecutorHandle executor = exec.getExecutor();
				if (executor.getExecutionerCapabilities().supportsServiceClass(serviceClasspath)) {
					found = true;
					resolvedExecPlan = exec;
					break;
				}
			}
			if (!found) {
				/*
				 * no execution in the list supports the required service. find a new executor
				 * that supports the service and add it to the exections.
				 */
				ExecutorHandle newExecutor = resolveInfo.getExecutorCoordinator()
						.randomExecutorWithServiceClass(serviceClasspath);
				resolvedExecPlan = new ExecPlan(newExecutor);
				execPlans.add(resolvedExecPlan);
			}
		}
		assignNodeToExec(instNode, resolvedExecPlan);
	}

	private void analyzeDataFlow(InstructionNode instNode) {
		/*
		 * map this instruction to all fieldtypes it is depending on. (consuming)
		 */
		ExecPlan instExec = getAssignedExec(instNode);
		String contextClasspath;
		if (instNode.isContextAFieldname()) {
			String serviceInstanceFieldname = instNode.getContext();
			/*
			 * connect the instnode to the fieldtype of the serviceinstance of the
			 * instruction.
			 */
			List<FieldType> serviceInstances = resolveFieldname(serviceInstanceFieldname);
			if (serviceInstances.size() != 1) {
				throw new CompositionSemanticException(
						"The service handle cannot be resolved to a single entity: " + serviceInstances.size());
			}
			FieldType serviceInstance = serviceInstances.get(0);
			if (!(serviceInstance.isServiceInstance() || serviceInstance.isServiceInstanceHandle())) {
				throw new CompositionSemanticException("The context of instruction: \"" + instNode.toString()
						+ "\" is of type: " + serviceInstance.toString());

			}
			contextClasspath = serviceInstance.getTypeName();
			/*
			 * make sure the service is available on the instExec:
			 */
			ExecPlan sourceExec = getAssignedExec(serviceInstance.getProducer());
			if (getAssignedExec(serviceInstance.getProducer()) != instExec) {
				/*
				 * this service is not present on instExec. First transmit the service instance
				 * handle the executor to instExec:
				 */
				serviceInstance = createTransmission(sourceExec, instExec, serviceInstance);
			}
			if (serviceInstance.isServiceInstanceHandle()) {
				/*
				 * add an intermediate service instance storage node to load the service
				 * instance.
				 */
				ServiceInstanceHandle serviceInstanceHandle = resolveInfo.getInputFields()
						.getServiceInstanceHandle(serviceInstanceFieldname);
				ServiceInstanceStorageNode loadService = new ServiceInstanceStorageNode(serviceInstanceHandle.getId(), serviceInstanceFieldname,
						contextClasspath);
				assignNodeToExec(loadService, instExec);

				nodeConsumesField(loadService, serviceInstance);

				serviceInstance = new FieldType(loadService, serviceInstanceFieldname, TypeClass.ServiceInstance,
						contextClasspath, true);
				addFieldType(serviceInstance);

			}
			nodeConsumesField(instNode, serviceInstance);

		} else {
			contextClasspath = instNode.getContext();
		}

		/*
		 * for each paramter find or create a fieldtype and add it to the consumerlist
		 * of this instruction node.
		 */
		String methodname = instNode.getMethod();
		List<String> instParamFieldnames = instNode.getParameterFields();
		MethodInfo methodInfo;
		if (instNode.isServiceConstruct()) {
			methodInfo = resolveInfo.getClassesConfiguration().classInfo(contextClasspath).constructInfo();
		} else {
			methodInfo = resolveInfo.getClassesConfiguration().classInfo(contextClasspath).methodInfo(methodname);
			if(!instNode.isContextAFieldname() && !methodInfo.isStatic()) {
				throw new CompositionSemanticException("Method \"" + methodname + "\" is not static but it is tried to access it in a static manner: " + instNode.toString());
			}
		}
		List<String> requiredParamTypes = methodInfo.paramTypes();
		instNode.setParameterType(requiredParamTypes);

		if (instParamFieldnames.size() != requiredParamTypes.size()) {
			int stated = instParamFieldnames.size();
			int actual = requiredParamTypes.size();
			throw new CompositionSemanticException("Instruction: " + instNode.toString() + " states that there are  "
					+ stated + " amount of parameters while the classes config defines " + actual + " many.");
		}
		for (int index = 0, size = instParamFieldnames.size(); index < size; index++) {
			String parameter = instParamFieldnames.get(index);
			String requiredType = requiredParamTypes.get(index);
			if (FMCompositionParser.isConstant(parameter)) {
				/*
				 * if the parameter is a constant it needs to be parsed.
				 */
				FieldType constantType = null; // null safe - the compiler is stupid though..
				boolean parsedConstant = false;
				if (isResolvable(parameter)) {
					List<FieldType> constantFields = resolveFieldname(parameter);
					for (FieldType ft : constantFields) {
						if (getAssignedExec(ft.getProducer()) == instExec) {
							/*
							 * the parsing is already done on instExec:
							 */
							parsedConstant = true;
							constantType = ft;
						}
					}
				}
				if (!parsedConstant) {
					/*
					 * add constant parser and its fieldtype:
					 */
					ParseConstantNode parseConstantNode = new ParseConstantNode(parameter);
					constantType = new FieldType(parseConstantNode, parameter, TypeClass.PrimitiveType,
							parseConstantNode.getType().name(), true);
					addFieldType(constantType);
					assignNodeToExec(parseConstantNode, instExec);
				}
				if (constantType.getTypeName().equals(requiredType)) {
					nodeConsumesField(instNode, constantType);
				} else {
					throw new CompositionSemanticException("Type mismatch for invocation of: " + instNode.toString()
							+ "\nConstant parameter \"" + parameter + "\" is of type: " + constantType.getTypeName()
							+ ", but the required type of the method signature is: " + requiredType);
				}
			} else {
				/*
				 * Field is not a constant.
				 * if the parameter is data the required field needs to be found/created.
				 */
				List<FieldType> paramFieldTypes = resolveFieldname(parameter);
				boolean found = false;
				boolean resolvedDependency = false;
				FieldType requiredData = null;
				/*
				 * first look for fields that are available on instExec:
				 */
				for (FieldType paramType : paramFieldTypes) {
					BaseNode producer = paramType.getProducer();
					if (getAssignedExec(producer) == instExec) {
						if (paramType.getTypeClass() == TypeClass.RealDataType
								&& paramType.getTypeName().equals(requiredType)) {
							/*
							 * type matches exactly:
							 */
							found = true;
							resolvedDependency = true;
							nodeConsumesField(instNode, paramType);
							break;
						} else {
							found = true;
							requiredData = paramType;
						}
					}
				}
				if (resolvedDependency) {
					/*
					 * fully resolved the dependency of this paramter. continue with the next
					 * parameter:
					 */
					continue;
				}
				if (!found) {
					/*
					 * the field was not found on the instExec. Transmit the data from another
					 * executor first:
					 */
					for (FieldType paramType : paramFieldTypes) {
						if (paramType.isSemanticData()) {
							found = true;
							requiredData = paramType;
							break;
						}
					}
					if (!found) {
						/*
						 * no semantic data found for the field. use any other field for transmission:
						 */
						requiredData = paramFieldTypes.get(0);
					}
					/* transmit data to instExec: */
					ExecPlan sourceExec = getAssignedExec(requiredData.getProducer());
					// the return value of createTransmission is the datafield which is present on
					// instExec:
					requiredData = createTransmission(sourceExec, instExec, requiredData);
				}

				if (requiredData.isRealData()) {
					/*
					 * the required data is not semantic but its not equal to the required type. So
					 * double casting is required: found RealType -> semantic -> required RealType
					 */
					if (requiredData.getTypeName().equals(requiredType)) {
						throw new RuntimeException(
								"Code error: it cannot happen that the required type matches the found data.");
					}
					/*
					 * cast to its semantic type:
					 */
					String semanticTypename = resolveInfo.getTypeConfig()
							.getOnthologicalType(requiredData.getTypeName());
					String caster = resolveInfo.getTypeConfig().getOnthologicalCaster(requiredData.getTypeName());
					CastTypeNode castToSemantic = new CastTypeNode(parameter, requiredData.getTypeName(),
							semanticTypename, true, caster);
					assignNodeToExec(castToSemantic, instExec);
					nodeConsumesField(castToSemantic, requiredData);
					requiredData = new FieldType(castToSemantic, parameter, TypeClass.SemanticDataType,
							semanticTypename, false);
					addFieldType(requiredData);
				}

				if(requiredData.getTypeName().equalsIgnoreCase(requiredType)){
					/*
					 * incase the type matches already just add it as a consumer and continue:
					 */
					nodeConsumesField(instNode, requiredData);
					continue;
				} else if(requiredData.isPrimitive()){
					/*
					 *
					 */
					throw new CompositionSemanticException("Type mismatch: " + instExec.toString() + "\nfield:" + requiredData.getFieldname());
				}

				/*
				 * at this point the required Type should be present and it should be of
				 * semantic type:
				 */
				if (!requiredData.isSemanticData()) {
					throw new RuntimeException("Coding error: " + requiredData.toString());
				}

				/*
				 * cast the semantic type to the required type:
				 */
				String originSemanticType = requiredData.getTypeName();
				/*
				 * assert that the found semantic type equals the semantic type of the required
				 * type as stated by the type configuration.
				 */
				String configurationSemanticType = resolveInfo.getTypeConfig().getOnthologicalType(requiredType);
				if (!configurationSemanticType.equals(originSemanticType)) {
					throw new CompositionSemanticException("The required parameter(" + parameter + ") type, "
							+ requiredType + ", corresponds to a different semantic type, " + configurationSemanticType
							+ ", than what is bounded to the fieldname: " + originSemanticType + ".\t"
							+ instNode.toString());
				}
				String casterClasspath = resolveInfo.getTypeConfig().getOnthologicalCaster(requiredType);
				CastTypeNode castTypeNode = new CastTypeNode(parameter, originSemanticType, requiredType, false,
						casterClasspath);
				assignNodeToExec(castTypeNode, instExec);
				nodeConsumesField(castTypeNode, requiredData);

				FieldType requiredParamType = new FieldType(castTypeNode, parameter, TypeClass.RealDataType,
						requiredType, false);
				addFieldType(requiredParamType);
				nodeConsumesField(instNode, requiredParamType);
			}
		}

		/*
		 * Create a new fieldtype for each fieldname this instruction is writing onto
		 * (producing).
		 */
		if (instNode.isContextAFieldname() && methodInfo.isStateMutating()) {
			/*
			 * the instruction is changing the state of its serviceinstance (context).
			 */
			String fieldname = instNode.getContext();

			FieldType serviceInstanceFieldType = new FieldType(instNode, fieldname, TypeClass.ServiceInstance,
					contextClasspath, true);
			addFieldType(serviceInstanceFieldType);
		}
		if (instNode.isAssignedLeftSideFieldname()) {
			String leftsideFieldname = instNode.getLeftSideFieldname();
			/*
			 * the instruction outputs a new value to the leftside fieldname.
			 * See if the fieldname is already defined and add dependency to avoid collision:
			 */
			if(isResolvable(leftsideFieldname) ) {
				FieldType leftsideField = resultFieldtype(leftsideFieldname);
				/*
				 * only consume the fieldtype if its on the same executor:
				 */
				if(getAssignedExec(leftsideField.getProducer())==getAssignedExec(instNode)) {
					/*
					 * look if the field is needed by another instruction:
					 */
					List<BaseNode> consumers = getConsumingersOfField(leftsideField);
					if(consumers.isEmpty()) {
						/*
						 * the produced field is not neccessary.
						 * if the old producer of the field is an instruction and the field is its leftside, remove the leftside assignment:
						 * if the old producer is an instruction and the field is the service instance of the operation, add a dependency to the current instruction 
						 */
						BaseNode oldProducer = leftsideField.getProducer();
						if(oldProducer instanceof InstructionNode) {
							InstructionNode oldInst = (InstructionNode) oldProducer;
							if(oldInst.isAssignedLeftSideFieldname() && oldInst.getLeftSideFieldname().equals(leftsideFieldname)) {
								oldInst.unsetLeftSideField();
							} 
							if(oldInst.getContext().equals(leftsideFieldname)) {
								nodeConsumesField(instNode, leftsideField);
							}
						}
					} else {
						/*
						 * there are consumers of the leftside fieldname.
						 * because those consumers were added before this instruction they have to operate on the old state of the field.
						 * So they have to consume the fieldname before this instruction can produce a new version of it.
						 * Thus add them as a dependency to this instruction to avoid having this instruction rewrite the fieldname before they can consume it.
						 */
						for(BaseNode consumer : consumers) {
							if(consumer == instNode) {
								continue;
							}
							if(getAssignedExec(consumer) != getAssignedExec(instNode)) {
								/*
								 * dont need to add dependency between nodes that aren't on the same executor:
								 */
								continue;
							}
							getAssignedExec(instNode).getGraph().connectNodes(consumer, instNode);
						}
					}
				}
			}
			/*
			 * Resolve the type of the left side fieldname and mark this instruction as a producer of it:
			 */
			TypeClass typeClass;
			String typeName;
			if (instNode.isServiceConstruct()) {
				/*
				 * leftside field is a new service instance:
				 */
				typeClass = TypeClass.ServiceInstance;
				/*
				 * the type of the leftside field has the classpath of the constructor that is
				 * invoked:
				 */
				typeName = instNode.getContext();
			} else {
				/* return type defined in the classes configuration */
				if (!methodInfo.hasReturnType()) {
					throw new CompositionSemanticException(
							"The type of the leftside fieldname of instruction: " + instNode.toString()
									+ " cannot be resolved. The return type of method signature is void.");
				}
				typeName = methodInfo.getReturnType();
				if(SEDEObject.isPrimitive(typeName)) {
					typeClass = TypeClass.PrimitiveType;
				} else if(SEDEObject.isReal(typeName)) {
					typeClass = TypeClass.RealDataType;
				} else if(SEDEObject.isSemantic(typeName)){
					typeClass = TypeClass.SemanticDataType;
				} else{
					throw new RuntimeException("BUG: The return type of the instruction " +  instNode.toString() +" cannot be reolved to a type class, "
							+ typeName);
				}
			}
			FieldType leftSideFieldType = new FieldType(instNode, leftsideFieldname, typeClass, typeName,
					true);
			addFieldType(leftSideFieldType);
			instNode.setLeftSideFieldtype(typeName);
		}
	}

//	private void removeUnusedInputs() {
//		/*
//			Remove every input from the client that was not used:
//		 */
//		Collection<String> notUsedInputs = resolveInfo.getInputFields().getInputFields();
//		/*
//		 	Iterate over all the transmit nodes in the client graph
//		 	and remove every fieldname that is used by a transmit node.
//		  */
//		for(BaseNode baseNode :
//				GraphTraversal.iterateNodesWithClassname(getClientExecPlan().getGraph(), TransmitDataNode.class.getName())) {
//			TransmitDataNode transmitDataNode = (TransmitDataNode) baseNode;
//			notUsedInputs.remove(transmitDataNode.getSendingFieldName());
//		}
//		/*
//			The remaining elements in notUsedInputs are not sent to any executor.
//			Add a delete node to remove them from the client:
//		 */
//		for(String notUsedInput : notUsedInputs) {
//			DeleteFieldNode deleteField = new DeleteFieldNode(notUsedInput);
//			nodeConsumesField(deleteField, );
//		}
//	}

	private void resolveResults() {
		/*
		 * iterate all the fieldnames and return results to client.
		 */

		for (String resultFieldname : resultFieldnames()) {
			if (FMCompositionParser.isConstant(resultFieldname)) {
				continue; // no need to send back constants
			}
			if(resolveInfo.getInputFields().isInputField(resultFieldname)) {
				continue;
			}
			FieldType resultFieldType = resultFieldtype(resultFieldname);
			// Node that last produced the result:
			BaseNode resultProducer = resultFieldType.getProducer();
			ExecPlan resultExecPlan = getAssignedExec(resultProducer);

			boolean servicePersistant = resultFieldType.isServiceInstance()
					&& resolveInfo.getResolvePolicy().isPersistentService(resultFieldname);
			boolean toBeReturned = servicePersistant || (!resultFieldType.isServiceInstance() && resolveInfo.getResolvePolicy().isToReturn(resultFieldname));

			if (servicePersistant) {
				/*
				 * store service instance:
				 */
				ServiceInstanceStorageNode store = new ServiceInstanceStorageNode(resultFieldname,
						resultFieldType.getTypeName());
				assignNodeToExec(store, resultExecPlan);
				nodeConsumesField(store, resultFieldType);
			}

			if (toBeReturned) {

				if (clientExecPlan != resultExecPlan) {
					/*
					 * return result to client
					 */
					createTransmission(resultExecPlan, clientExecPlan, resultFieldType);
				}
				markAsResult(resultFieldType);
			}
		}
	}

	private void markAsResult(FieldType resultFieldType) {
		returnFieldnames.add(resultFieldType.getFieldname());
	}

	private void connectDependencyEdges() {
		for(ExecPlan exec : getInvolvedExecutions()) {
			CompositionGraph graph = exec.getGraph();
			/*
			 * connect every consumer in the graph to its producer:
			 */
			for(BaseNode consumer : GraphTraversal.iterateNodes(graph)) {
				for(FieldType  consumedField : getConsumingFields(consumer)) {
					BaseNode producer = consumedField.getProducer();
					/*
					 * assert that the producer is assigned to the same execution:
					 */
					if(getAssignedExec(producer) != getAssignedExec(consumer)) {
						throw new RuntimeException("Coding error: consumer is somewhere else than the producer!");
					}
					exec.getGraph().connectNodes(producer, consumer);
				}
			}
		}
	}

	private void addFinishingNodes() {

		for(ExecPlan exec : getInvolvedExecutions()) {
			if(exec == getClientExecPlan()) {
				continue;
			}
			/*
				Add node that indicate that the execution is done:
		 	*/
			String flagname = ("&finished&" + exec.getExecutor().getExecutorId());
			FinishNode finishNode = new FinishNode(getClientExecPlan().getExecutor().getContactInfo(), flagname);
			exec.getGraph().executeLast(Arrays.asList(finishNode));

			/*
				Add accept node to client:
			 */
			AcceptDataNode acceptFinishFlag = new AcceptDataNode(flagname);
			getClientExecPlan().getGraph().addNode(acceptFinishFlag);

			getTransmissionGraph().addNode(finishNode);
			getTransmissionGraph().addNode(acceptFinishFlag);
			getTransmissionGraph().connectNodes(finishNode, acceptFinishFlag);
		}
	}

	private ExecPlan getOrCreateExecutionForId(String executorId) {
		/*
		 * First search the list of already involved executors to look for it:
		 */
		for (ExecPlan exec : execPlans) {
			if (exec.getExecutor().getExecutorId().equals(executorId)) {
				return exec;
			}
		}
		
		if (resolveInfo.getExecutorCoordinator().hasExecutor(executorId)) {
			ExecutorHandle executor = resolveInfo.getExecutorCoordinator().getExecutorFor(executorId);
			
			/*
			 * Add a new executor to the involved list of executors:
			 */
			ExecPlan exec = new ExecPlan(executor);
			execPlans.add(exec);
			return exec;

		} else {
			throw new RuntimeException("BUG: Cannot resolve executor with id: \"" + executorId + "\". No such executor is registered.");
		}
	}

	private void addFieldType(FieldType fieldType) {
		if (fieldType.isChangedState() || !fieldnameTypeResult.containsKey(fieldType.getFieldname())) {
			fieldnameTypeResult.put(fieldType.getFieldname(), new ArrayList<>());
		}
		fieldnameTypeResult.get(fieldType.getFieldname()).add(fieldType);
		nodeProducesField(fieldType.getProducer(), fieldType);
	}

	private AcceptDataNode clientAcceptInput(String fieldname) {
		AcceptDataNode accept = new AcceptDataNode(fieldname);
		assignNodeToExec(accept, clientExecPlan);
		Map<String, String> clientContactInfo = clientExecPlan.getExecutor().getContactInfo();
		TransmitDataNode transmit = new TransmitDataNode(fieldname, clientContactInfo);

		addTransmission(transmit, accept);

		return accept;
	}

	private FieldType createTransmission(ExecPlan sourceExec, ExecPlan targetExec, FieldType datafield) {
		String fieldname = datafield.getFieldname();
		AcceptDataNode accept = new AcceptDataNode(fieldname);
		assignNodeToExec(accept, targetExec);
		Map<String, String> contactInfo = targetExec.getExecutor().getContactInfo();
		TransmitDataNode transmit;
		if(datafield.isRealData()) {
			String caster = resolveInfo.getTypeConfig().getOnthologicalCaster(datafield.getTypeName());
			String semanticType = resolveInfo.getTypeConfig().getOnthologicalType(datafield.getTypeName());
			transmit = new TransmitDataNode(fieldname, contactInfo, caster, semanticType);
		}
		else {
			transmit = new TransmitDataNode(fieldname, contactInfo);
		}

		assignNodeToExec(transmit, sourceExec);
		nodeConsumesField(transmit, datafield);
		addTransmission(transmit, accept);

		FieldType inputField;
		if (datafield.isRealData()) {
			TypeClass inputTypeClass = TypeClass.SemanticDataType;
			String typeName = resolveInfo.getTypeConfig().getOnthologicalType(datafield.getTypeName());
			inputField = new FieldType(accept, fieldname, inputTypeClass, typeName, false);

		} else  {
			inputField = datafield.clone(accept, false);
		}
		addFieldType(inputField);
		return inputField;
	}

	private void addTransmission(TransmitDataNode transmit, AcceptDataNode accept) {
		dataTransmissionGraph.addNode(transmit);
		dataTransmissionGraph.addNode(accept);
		dataTransmissionGraph.connectNodes(transmit, accept);
	}

	private void nodeProducesField(BaseNode node, FieldType fieldType) {
		if (!fieldProducers.get(fieldType).contains(node)) {
			fieldProducers.get(fieldType).add(node);
		}
	}

	private void assignNodeToExec(BaseNode node, ExecPlan exec) {
		if (isAssignedToExec(node)) {
			throw new RuntimeException("Coding error: each node can only be assigned to a single execution.");
		}
		exec.getGraph().addNode(node);
		nodeExecutionAssignment.put(node, exec);
	}

	private ExecPlan getAssignedExec(BaseNode node) {
		if (!isAssignedToExec(node)) {
			throw new CompositionSemanticException("The node " + node.toString() + " isn't assigned to a execution.");
		}
		return nodeExecutionAssignment.get(node);
	}

	private boolean isAssignedToExec(BaseNode node) {
		return nodeExecutionAssignment.containsKey(node);
	}

	List<BaseNode> getProducers(FieldType fieldType) {
		return Collections.unmodifiableList(fieldProducers.get(fieldType));
	}

	private List<FieldType> resolveFieldname(String fieldname) {
		if (!this.fieldnameTypeResult.containsKey(fieldname)) {
			throw new CompositionSemanticException("The fieldname " + fieldname + " cannot be resolved.");
		} else {
			return this.fieldnameTypeResult.get(fieldname);
		}
	}
	private boolean isResolvable(String fieldname) {
		return this.fieldnameTypeResult.containsKey(fieldname) && !this.fieldnameTypeResult.get(fieldname).isEmpty();
	}

	void nodeConsumesField(BaseNode consumer, FieldType consumedField) {
		// assert that they both are on the same executor
		assert getAssignedExec(consumedField.getProducer()) == getAssignedExec(consumer);
		nodeConsumingFields.get(consumer).add(consumedField);
	}

	private FieldType getFieldType(BaseNode consumer, String fieldname) {
		for (FieldType ft : nodeConsumingFields.get(consumer)) {
			if (ft.getFieldname().equals(fieldname)) {
				return ft;
			}
		}
		throw new RuntimeException("Consumer  " + consumer.toString() + " doesn't know the field: " + fieldname + ".");
	}

	private List<FieldType> getConsumingFields(BaseNode consumer) {
		return Collections.unmodifiableList(nodeConsumingFields.get(consumer));
	}
	private List<BaseNode> getConsumingersOfField(FieldType field) {
		List<BaseNode> consumers = new ArrayList<>();
		for(BaseNode baseNode : GraphTraversal.iterateNodes(getAssignedExec(field.getProducer()).getGraph())) {
			if(getConsumingFields(baseNode).contains(field)) {
				consumers.add(baseNode);
			}
		}
		return consumers;
	}

	private Set<String> resultFieldnames() {
		return fieldnameTypeResult.keySet();
	}

	private FieldType resultFieldtype(String fieldname) {
		for (FieldType fieldType : fieldnameTypeResult.get(fieldname)) {
			if (fieldType.isChangedState()) {
				return fieldType;
			}
		}
		throw new RuntimeException("Field " + fieldname + " doens't contain any original fieldtype.");
	}

	public ExecPlan getClientExecPlan() {
		return clientExecPlan;
	}

	public List<ExecPlan> getInvolvedExecutions() {
		return execPlans;
	}


	public List<String> getReturnFieldnames() {
		return returnFieldnames;
	}


	public CompositionGraph getTransmissionGraph() {
		return dataTransmissionGraph;
	}

}

/**
 * This class determines the type for each fieldname.
 */
class FieldType {
	private final BaseNode producer;
	private final String fieldname;
	private final TypeClass typeClass;
	private final String typeName;
	private final boolean changedState;

	FieldType(BaseNode producer, String fieldname, TypeClass typeClass, String typeName, boolean changesState) {
		this.producer = producer;
		this.fieldname = fieldname;
		this.typeClass = typeClass;
		this.typeName = typeName;
		this.changedState = changesState;
		if ((isServiceInstance() || isServiceInstanceHandle()) && !changesState) {
//			throw new RuntimeException("Coding error: each new service instance fieldtype has to change its state");
		}
	}

	public BaseNode getProducer() {
		return producer;
	}

	public String getFieldname() {
		return fieldname;
	}

	public TypeClass getTypeClass() {
		return typeClass;
	}

	public String getTypeName() {
		return typeName;
	}

	public boolean isChangedState() {
		return changedState;
	}

	public boolean isServiceInstance() {
		return typeClass == TypeClass.ServiceInstance;
	}

	public boolean isServiceInstanceHandle() {
		return typeClass == TypeClass.ServiceInstanceHandle;
	}

	public boolean isSemanticData() {
		return typeClass == TypeClass.SemanticDataType;
	}

	public boolean isRealData() {
		return typeClass == TypeClass.RealDataType;
	}

	public boolean isPrimitive() {
		return typeClass == TypeClass.PrimitiveType;
	}

	public String toString() {
		return fieldname + " type=(" + typeClass + ", " + typeName + ")" + (changedState ? "" : "...");
	}

	public FieldType clone(BaseNode newProducer, boolean changesState) {
		return new FieldType(newProducer, getFieldname(), getTypeClass(), getTypeName(), changesState);
	}

}

enum TypeClass {
	ServiceInstance, SemanticDataType, RealDataType, PrimitiveType, ServiceInstanceHandle;
}

