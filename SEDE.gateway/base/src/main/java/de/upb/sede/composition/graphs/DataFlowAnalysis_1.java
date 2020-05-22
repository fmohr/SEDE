package de.upb.sede.composition.graphs;

import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.gateway.ResolveInfo;
import de.upb.sede.util.DefaultMap;

import java.util.*;

public class DataFlowAnalysis_1 {

	private final ResolveInfo_1 resolveInfo;

	// private final List<AcceptDataNode> clientInputNodes = new ArrayList<>();

	private final Map<String, List<FieldType>> fieldnameTypeResult = new HashMap<>();

	/**
	 * Node: the client executor is part of this list.
	 */
	private final List<ExecPlan_1> execPlans;

	private final ExecPlan_1 clientExecPlan;

	private final CompositionGraph dataTransmissionGraph;

	private final DefaultMap<BaseNode, List<FieldType>> nodeConsumingFields = new DefaultMap<>(ArrayList::new);

//	private final DefaultMap<FieldType, List<IBaseNode>> fieldProducers = new DefaultMap<>(ArrayList::new);

	private final Map<BaseNode, ExecPlan> nodeExecutionAssignment = new HashMap<>();

	private final List<String> returnFieldnames = new ArrayList<>();

	public DataFlowAnalysis_1(ResolveInfo_1 resolveInfo, ICompositionCompilation cc) {
		this.resolveInfo = Objects.requireNonNull(resolveInfo);

		clientExecPlan = new ExecPlan_1(resolveInfo.getClientExecutor());
		execPlans = new ArrayList<>();
		execPlans.add(clientExecPlan);
		dataTransmissionGraph = new CompositionGraph();
		analyzeDataFlow(Objects.requireNonNull(cc));
	}
//
	private void analyzeDataFlow(ICompositionCompilation cc) {
	    if(cc.getProgramOrder().isEmpty()) {
	        throw new IllegalArgumentException("Program is empty.");
        }
		addInputNodesFieldTypes(cc);
//		for (InstructionNode instNode : instructionNodes) {
//			resolveExecution(instNode);
//			analyzeDataFlow(instNode);
//		}
//		determineExecutors();
//		resolveResults();
//		connectDependencyEdges();
//		mergeAcceptAndCasts();
//		addErrorCollectors();
//		fillinContactInforamtions();
//		if(resolveInfo.getResolvePolicy().isBlockExecRequested()) {
//			addFinishingNodes();
//		}
	}
//
//	/**
//	 * Merges a subset of {@link AcceptDataNode AcceptDataNodes} and their following {@link CastTypeNode CastTypeNodes} on every executor
//	 * removing the casttypenode and only using the acceptdatanode to do the casting in place.
//	 * Acceptdatanodes that have more than one cast type following nodes are ignored.
//	 * ExecPlans which don't have cast in place enabled are also ignored.
//	 */
//	private void mergeAcceptAndCasts() {
//		for(ExecPlan execPlan : getInvolvedExecutions()) {
//			if(!execPlan.getTarget().getExecutionerCapabilities().canCastInPlace()) {
//				continue;
//			}
//			CompositionGraph g = execPlan.getGraph();
//			Set<CastTypeNode> toBeRemovedNodes = new HashSet<>();
//			for(AcceptDataNode acceptDataNode :
//					GraphTraversal.iterateNodesWithClass(g, AcceptDataNode.class)) {
//				/*
//				 * iterate nodes the accepter points towards.
//				 * If there are more than 1 caster ignore the accepter
//				 */
//				CastTypeNode caster = null;
//				boolean tooManyCasters = false;
//				for( BaseNode target : GraphTraversal.targetingNodes(g, acceptDataNode) ) {
//					if(CastTypeNode.class.isInstance(target)) {
//						if(caster == null) {
//							caster = (CastTypeNode) target;
//						} else {
//							tooManyCasters = true;
//						}
//					}
//				}
//				if(tooManyCasters) {
//					continue; // skip to accept data node
//				} else if(caster != null) {
//					acceptDataNode.setCastInPlace(caster);
//					/*
//					 * remove caster from the graph but keep its dependecies:
//					 */
//					g.connectToItsTargets(acceptDataNode, caster);
//					toBeRemovedNodes.add(caster);
//				}
//			}
//			/*
//			 * Remove merged casters:
//			 */
//			toBeRemovedNodes.forEach(g::removeNode);
//		}
//	}
//
//
	private void addInputNodesFieldTypes(ICompositionCompilation cc) {
        Long firstIndex = cc.getProgramOrder().get(0);
        cc.getTypeContext().get(firstIndex);
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
				if(SEDEObject.isPrimitive(typeName)) { // is the input either Number String boolean or nul?
					typeClass = TypeClass.PrimitiveType;
				} else if(resolveInfo.getTypeConfig().hasOnthologicalType(typeName)) { // is the input real data type?
					typeClass = TypeClass.RealDataType;
				} else {
					typeClass = TypeClass.SemanticDataType;
				}
				FieldType fieldType = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(fieldType);
			}
		}
	}
//
//	private void resolveExecution(InstructionNode instNode) {
//		ExecPlan resolvedExecPlan = null;
//		if (instNode.isContextAFieldname()) {
//			/*
//			 * instructions context is field. so it needs to be assigned to the correct
//			 * execution.
//			 */
//			String serviceInstanceFieldname = instNode.getContext();
//
//			List<FieldType> serviceInstances = resolveFieldname(serviceInstanceFieldname);
//
//			if (serviceInstances.size() != 1) {
//				throw new CompositionSemanticException(
//						"The context of the instruction cannot be resolved: " + instNode.toString());
//			}
//			FieldType serviceInstanceField = serviceInstances.get(0);
//
//			if (serviceInstanceField.isServiceInstanceHandle()) {
//				/*
//				 * service handles are in the input fields:
//				 */
//				ServiceInstanceHandle serviceInstanceHandle = resolveInfo.getInputFields()
//						.getServiceInstanceHandle(serviceInstanceFieldname);
//				resolvedExecPlan = getOrCreateExecutionForId(serviceInstanceHandle.getExecutorId());
//			} else {
//				/*
//				 * The field is bound to a new service instance. producer is another instruction
//				 * node.
//				 */
//				BaseNode producer = serviceInstanceField.getProducer();
//				resolvedExecPlan = getAssignedExec(producer);
//			}
//		} else {
//			/*
//			 * instructions context is a service classpath.
//			 */
//			String serviceClasspath = instNode.getContext();
//			boolean found = false;
//			for (ExecPlan exec : execPlans) {
//				if(exec.targetDetermined()) {
//					ExecutorHandle executor = exec.getTarget();
//					if (executor.getExecutionerCapabilities().supportsServiceClass(serviceClasspath)) {
//						found = true;
//						resolvedExecPlan = exec;
//						break;
//					}
//				} else {
//					/*
//						The execPlan can be assigned to a number of executors.
//					 */
//					Predicate<ExecutorHandle> supportsService = candidate ->
//							candidate.getExecutionerCapabilities().supportsServiceClass(serviceClasspath);
//					boolean execSupportsService = exec.candidates().stream().anyMatch(supportsService);
//					if(execSupportsService) {
//						/*
//							There is at least one executor that supports the service..
//							remove the candidates who dont:
//						 */
//						exec.removeCandidates(supportsService.negate());
//						found = true;
//						resolvedExecPlan = exec;
//						break;
//					}
//				}
//			}
//			if (!found) {
//				/*
//				 * no execution in the list supports the required service. find a new executor
//				 * that supports the service and add it to the exections.
//				 */
//				List<ExecutorHandle> executors = resolveInfo.getExecutorSupplyCoordinator()
//						.supplyExecutor(serviceClasspath);
//				if(executors.size()==0) {
//					throw new RuntimeException("No registered execution supports the requested service: " + serviceClasspath);
//				}
//				resolvedExecPlan = new ExecPlan(executors);
//				execPlans.add(resolvedExecPlan);
//			}
//		}
//		assignNodeToExec(instNode, resolvedExecPlan);
//	}
//
//	private void analyzeDataFlow(InstructionNode instNode) {
//		/*
//		 * map this instruction to all fieldtypes it is depending on. (consuming)
//		 */
//		ExecPlan instExec = getAssignedExec(instNode);
//		String contextClasspath;
//		if (instNode.isContextAFieldname()) {
//			String serviceInstanceFieldname = instNode.getContext();
//			/*
//			 * connect the instnode to the fieldtype of the serviceinstance of the
//			 * instruction.
//			 */
//			List<FieldType> serviceInstances = resolveFieldname(serviceInstanceFieldname);
//			if (serviceInstances.size() != 1) {
//				throw new CompositionSemanticException(
//						"The service handle cannot be resolved to a single entity: " + serviceInstances.size());
//			}
//			FieldType serviceInstance = serviceInstances.get(0);
//			if (!(serviceInstance.isServiceInstance() || serviceInstance.isServiceInstanceHandle())) {
//				throw new CompositionSemanticException("The context of instruction: \"" + instNode.toString()
//						+ "\" is of type: " + serviceInstance.toString());
//
//			}
//			contextClasspath = serviceInstance.getTypeName();
//			/*
//			 * make sure the service is available on the instExec:
//			 */
//			ExecPlan sourceExec = getAssignedExec(serviceInstance.getProducer());
//			if (getAssignedExec(serviceInstance.getProducer()) != instExec) {
//				/*
//				 * this service is not present on instExec. First transmit the service instance
//				 * handle the executor to instExec:
//				 */
//				serviceInstance = createTransmission(sourceExec, instExec, serviceInstance);
//			}
//			if (serviceInstance.isServiceInstanceHandle()) {
//				/*
//				 * add an intermediate service instance storage node to load the service
//				 * instance.
//				 */
//				ServiceInstanceHandle serviceInstanceHandle = resolveInfo.getInputFields()
//						.getServiceInstanceHandle(serviceInstanceFieldname);
//				ServiceInstanceStorageNode loadService = new ServiceInstanceStorageNode(serviceInstanceHandle.getId(), serviceInstanceFieldname,
//						serviceInstance.getTypeName());
//				assignNodeToExec(loadService, instExec);
//
//				nodeConsumesField(loadService, serviceInstance);
//
//				serviceInstance = new FieldType(loadService, serviceInstanceFieldname, TypeClass.ServiceInstance,
//						contextClasspath, true);
//				addFieldType(serviceInstance);
//
//			}
//			nodeConsumesField(instNode, serviceInstance);
//
//		} else {
//			contextClasspath = instNode.getContext();
//		}
//
//		/*
//		 * for each paramter find or create a fieldtype and add it to the consumerlist
//		 * of this instruction node.
//		 */
//		String methodname = instNode.getMethod();
//		List<String> instParamFieldnames = new ArrayList<>(instNode.getParameterFields());
//		MethodInfo methodInfo;
//		ClassesConfig.ClassInfo contextClassInfo = resolveInfo.getClassesConfiguration().classInfo(contextClasspath);
//		if (instNode.isServiceConstruct()) {
//			methodInfo = contextClassInfo.constructInfo();
//		} else {
//			methodInfo = contextClassInfo.methodInfo(methodname);
//			if(!instNode.isContextAFieldname() && !methodInfo.isStatic()) {
//				throw new CompositionSemanticException("Method \"" + methodname + "\" is not static but it is tried to access it in a static manner: " + instNode.toString());
//			}
//		}
//		/*
//		 * Some methods are realised with other service method names.
//		 * Set the method name of the given instruction to the name it is realised with:
//		 */
//		String realisedMethodName = contextClassInfo.actualMethoname(methodname);
//		instNode.setMethod(realisedMethodName);
//
//		/*
//		 * Some methods do define fixed constants.
//		 * Insert them into the parameter list and act like they are all given by the client:
//		 */
//		methodInfo.insertFixedConstants(instParamFieldnames);
//		instNode.setParameterFields(instParamFieldnames);
//
//		List<String> requiredParamTypes = methodInfo.paramTypes();
//		instNode.setParameterType(requiredParamTypes);
//
//		if (instParamFieldnames.size() != requiredParamTypes.size()) {
//			int stated = instParamFieldnames.size();
//			int actual = requiredParamTypes.size();
//			throw new CompositionSemanticException("Instruction: " + instNode.toString() + " states that there are  "
//					+ stated + " amount of parameters while the classes config defines " + actual + " many.");
//		}
//
//		/*
//		 * Create an array of fieldtype which represents the consumed parameters.
//		 */
//		FieldType[] consumedParams = new FieldType[instParamFieldnames.size()];
//
//		for (int index = 0, size = instParamFieldnames.size(); index < size; index++) {
//			String parameter = instParamFieldnames.get(index);
//			String requiredType = requiredParamTypes.get(index);
//			if (FMCompositionParser.isConstant(parameter)) {
//				/*
//				 * if the parameter is a constant it needs to be parsed.
//				 */
//				FieldType constantType = null; // null safe - the compiler is stupid though..
//				boolean parsedConstant = false;
//				if (isResolvable(parameter)) {
//					List<FieldType> constantFields = resolveFieldname(parameter);
//					for (FieldType ft : constantFields) {
//						if (getAssignedExec(ft.getProducer()) == instExec) {
//							/*
//							 * the parsing is already done on instExec:
//							 */
//							parsedConstant = true;
//							constantType = ft;
//						}
//					}
//				}
//				if (!parsedConstant) {
//					/*
//					 * add constant parser and its fieldtype:
//					 */
//					ParseConstantNode parseConstantNode = new ParseConstantNode(parameter);
//					constantType = new FieldType(parseConstantNode, parameter, TypeClass.PrimitiveType,
//							parseConstantNode.getType().name(), true);
//					addFieldType(constantType);
//					assignNodeToExec(parseConstantNode, instExec);
//				}
//				/**
//				 * Check if the type of the parsed primitive value matches the required type of the signature of the method:
//				 */
//				if (constantType.getTypeName().equals(requiredType)) {
//					consumedParams[index] = constantType;
//					nodeConsumesField(instNode, constantType);
//				}
//				/**
//				 * Edge case: primitive type is Null which can fill in any real type:
//				 */
//				else if(constantType.getTypeName().equalsIgnoreCase("null")&& // primitive is null
//						resolveInfo.getTypeConfig().hasOnthologicalType(requiredType)) // required type is real
//				{
//					consumedParams[index] = constantType;
//					nodeConsumesField(instNode, constantType);
//				}
//				else{
//					throw new CompositionSemanticException("Type mismatch for invocation of: " + instNode.toString()
//							+ "\nConstant parameter \"" + parameter + "\" is of type: " + constantType.getTypeName()
//							+ ", but the required type of the method signature is: " + requiredType);
//				}
//			} else {
//				/*
//				 * Field is not a constant.
//				 * if the parameter is data the required field needs to be found/created.
//				 */
//				List<FieldType> paramFieldTypes = resolveFieldname(parameter);
//				boolean found = false;
//				boolean resolvedDependency = false;
//				FieldType requiredData = null;
//				/*
//				 * first look for fields that are available on instExec:
//				 */
//				for (FieldType paramType : paramFieldTypes) {
//					BaseNode producer = paramType.getProducer();
//					if (getAssignedExec(producer) == instExec) {
//						if (paramType.getTypeClass() == TypeClass.RealDataType
//								&& paramType.getTypeName().equals(requiredType)) {
//							/*
//							 * type matches exactly:
//							 */
//							found = true;
//							resolvedDependency = true;
//							consumedParams[index] = paramType;
//							nodeConsumesField(instNode, paramType);
//							break;
//						} else {
//							found = true;
//							requiredData = paramType;
//						}
//					}
//				}
//				if (resolvedDependency) {
//					/*
//					 * fully resolved the dependency of this paramter. continue with the next
//					 * parameter:
//					 */
//					continue;
//				}
//				if (!found) {
//					/*
//					 * the field was not found on the instExec.
//					 * The required data is available on sourceExec:
//					 */
//					requiredData = paramFieldTypes.get(0);
//					ExecPlan sourceExec = getAssignedExec(requiredData.getProducer());
//
//					/*
//					 *	Transmit the data from the source executor:
//					 */
//
//					// the return value of createTransmission is the datafield which is present on
//					// instExec:
//					requiredData = createTransmission(sourceExec, instExec, requiredData);
//				}
//
//				if (requiredData.isRealData()) {
//					/*
//					 * the required data is not semantic but its not equal to the required type. So
//					 * double casting is required: found RealType -> semantic -> required RealType
//					 */
//					if (requiredData.getTypeName().equals(requiredType)) {
//						throw new RuntimeException(
//								"Code error: it cannot happen that the required type matches the found data.");
//					}
//					/*
//					 * cast to its semantic type:
//					 */
//					String semanticTypename = resolveInfo.getTypeConfig()
//							.getOnthologicalType(requiredData.getTypeName());
//					String caster = resolveInfo.getTypeConfig().getOnthologicalCaster(requiredData.getTypeName());
//					CastTypeNode castToSemantic = new CastTypeNode(parameter, requiredData.getTypeName(),
//							semanticTypename, true, caster);
//					assignNodeToExec(castToSemantic, instExec);
//					nodeConsumesField(castToSemantic, requiredData);
//					requiredData = new FieldType(castToSemantic, parameter, TypeClass.SemanticDataType,
//							semanticTypename, false);
//					addFieldType(requiredData);
//				}
//
//				if(requiredData.getTypeName().equalsIgnoreCase(requiredType)){
//					/*
//					 * incase the type matches already just add it as a consumer and continue:
//					 */
//					consumedParams[index] = requiredData;
//					nodeConsumesField(instNode, requiredData);
//					continue;
//				} else if(requiredData.isPrimitive()){
//					/*
//					 *
//					 */
//					throw new CompositionSemanticException("Type mismatch: " + instExec.toString() + "\nfield:" + requiredData.getFieldname());
//				}
//
//				/*
//				 * at this point the required Type should be present and it should be of
//				 * semantic type:
//				 */
//				if (!requiredData.isSemanticData()) {
//					throw new RuntimeException("Coding error: " + requiredData.toString() + ". Expected semantic type.");
//				}
//
//				/*
//				 * cast the semantic type to the required type:
//				 */
//				String originSemanticType = requiredData.getTypeName();
//				/*
//				 * assert that the found semantic type equals the semantic type of the required
//				 * type as stated by the type configuration.
//				 */
//				String configurationSemanticType = resolveInfo.getTypeConfig().getOnthologicalType(requiredType);
//				if (!configurationSemanticType.equals(originSemanticType)) {
//					throw new CompositionSemanticException("The required parameter(" + parameter + ") type, "
//							+ requiredType + ", corresponds to a different semantic type, " + configurationSemanticType
//							+ ", than what is bounded to the fieldname: " + originSemanticType + ".\t"
//							+ instNode.toString());
//				}
//				String casterClasspath = resolveInfo.getTypeConfig().getOnthologicalCaster(requiredType);
//				CastTypeNode castTypeNode = new CastTypeNode(parameter, originSemanticType, requiredType, false,
//						casterClasspath);
//				assignNodeToExec(castTypeNode, instExec);
//				nodeConsumesField(castTypeNode, requiredData);
//
//				FieldType requiredParamType = new FieldType(castTypeNode, parameter, TypeClass.RealDataType,
//						requiredType, false);
//				addFieldType(requiredParamType);
//				consumedParams[index] = requiredParamType;
//				nodeConsumesField(instNode, requiredParamType);
//			}
//		}
//		/**
//		 * The instruction might change the state of its parameters:
//		 */
//		for (int index = 0, size = instParamFieldnames.size(); index < size; index++) {
//			/*
//				For each parameter which is stated to be changed by the config let this instruction be a new producer of it.
//			 */
//			FieldType consumedParam = consumedParams[index];
//			if(consumedParam == null) {
//				throw new RuntimeException("Coding error. The " + index + "th parameter type of instruciton " + instNode.toString() + " is null.");
//			}
//			if(methodInfo.isParamStateMutating(index) && !consumedParam.isPrimitive()){ // primitive parameters are ignored.
//				FieldType mutatedParameter = consumedParam.clone(instNode, true);
//				addFieldType(mutatedParameter);
//			}
//		}
//
//		/*
//		 * Create a new fieldtype for each fieldname this instruction is writing onto
//		 * (producing).
//		 */
//		if (instNode.isContextAFieldname() //  && methodInfo.isStateMutating() // ignore state mutating. Execution needs to keep the order.
//				) {
//			/*
//			 * the instruction is changing the state of its serviceinstance (context).
//			 */
//			String fieldname = instNode.getContext();
//
//			FieldType serviceInstanceFieldType = new FieldType(instNode, fieldname, TypeClass.ServiceInstance,
//					contextClasspath, true);
//			addFieldType(serviceInstanceFieldType);
//		}
//
//
//
//		if (instNode.isAssignedLeftSideFieldname()) {
//			String leftsideFieldname = instNode.getLeftSideFieldname();
//			/*
//			 * the instruction outputs a new value to the leftside fieldname.
//			 * See if the fieldname is already defined and add dependency to avoid collision:
//			 */
//			if(isResolvable(leftsideFieldname) ) {
//				FieldType leftsideField = resultFieldtype(leftsideFieldname);
//				/*
//				 * only consume the fieldtype if its on the same executor:
//				 */
//				if(getAssignedExec(leftsideField.getProducer())==getAssignedExec(instNode)) {
//					/*
//					 * look if the field is needed by another instruction:
//					 */
//					List<BaseNode> consumers = getConsumersOfField(leftsideField);
//					if(consumers.isEmpty()) {
//						/*
//						 * the produced field is not necessary.
//						 * if the old producer of the field is an instruction and the field is its leftside, remove the leftside assignment:
//						 * if the old producer is an instruction and the field is the service instance of the operation, add a dependency to the current instruction
//						 */
//						BaseNode oldProducer = leftsideField.getProducer();
//						if(oldProducer instanceof InstructionNode) {
//							InstructionNode oldInst = (InstructionNode) oldProducer;
//							if(oldInst.isAssignedLeftSideFieldname() && oldInst.getLeftSideFieldname().equals(leftsideFieldname)) {
//								oldInst.unsetLeftSideField();
//							}
//							if(oldInst.getContext().equals(leftsideFieldname)) {
//								nodeConsumesField(instNode, leftsideField);
//							}
//						}
//					} else {
//						/*
//						 * there are consumers of the leftside fieldname.
//						 * because those consumers were added before this instruction they have to operate on the old state of the field.
//						 * So they have to consume the fieldname before this instruction can produce a new version of it.
//						 * Thus add them as a dependency to this instruction to avoid having this instruction rewrite the fieldname before they can consume it.
//						 */
//						for(BaseNode consumer : consumers) {
//							if(consumer == instNode) {
//								continue;
//							}
//							if(getAssignedExec(consumer) != getAssignedExec(instNode)) {
//								/*
//								 * dont need to add dependency between nodes that aren't on the same executor:
//								 */
//								continue;
//							}
//							getAssignedExec(instNode).getGraph().connectNodes(consumer, instNode);
//						}
//					}
//				}
//			}
//			/*
//			 * Resolve the type of the left side fieldname and mark this instruction as a producer of it:
//			 */
//			TypeClass typeClass;
//			String typeName;
//			if (instNode.isServiceConstruct()) {
//				/*
//				 * leftside field is a new service instance:
//				 */
//				typeClass = TypeClass.ServiceInstance;
//				/*
//				 * the type of the leftside field has the classpath of the constructor that is
//				 * invoked:
//				 */
//				typeName = instNode.getContext();
//			} else if(methodInfo.hasReturnType()){
//				/* return type defined in the classes configuration */
//
//				typeName = methodInfo.returnType();
//				if(SEDEObject.isPrimitive(typeName)) {
//					typeClass = TypeClass.PrimitiveType;
//				} else if( resolveInfo.getClassesConfiguration().classknown(typeName)) {
//					/* The method might return another service as its output.
//					 * Think of static factory methods for example.
//					 * So first check if the return type is known as a service name: */
//					typeClass = TypeClass.ServiceInstance;
//				} else if( resolveInfo.getTypeConfig().hasOnthologicalType(typeName)){
//					typeClass = TypeClass.RealDataType;
//
//				} else {
//					typeClass = TypeClass.SemanticDataType;
//				}
//			} else {
//				/*
//					The method info doesn't define a return type.
//					This means that the method returns nothing.
//					However there is a leftside fieldname defined.
//					This field will be filled with the first state mutating parameter.
//					This covers the case that some methods apply changes in place but the fm-composition treats it as if it has a return value:
//				 */
//				int paramIndex = methodInfo.indexOfNthStateMutatingParam(0);
//				if(paramIndex >= 0) {
//					/*
//						paramIndex points to the first parameter which changes its state:
//					 */
//					typeClass = consumedParams[paramIndex].getTypeClass();
//					typeName = consumedParams[paramIndex].getTypeName();
//					instNode.setOutputIndex(paramIndex);
//				} else{
//					/*
//						No parameter changes its state.
//						Let the instruction return null:
//					 */
//					typeClass = TypeClass.PrimitiveType;
//					typeName = "Null";
//				}
//			}
//			FieldType leftSideFieldType = new FieldType(instNode, leftsideFieldname, typeClass, typeName,
//					true);
//			addFieldType(leftSideFieldType);
//			instNode.setLeftSideFieldtype(typeName);
//			instNode.setLeftSideFieldclass(typeClass.name());
//			/*
//			 * End of if(has leftside field) body.
//			 */
//		}
//		/*
//	     * Finally if the instruction is a construction of a wrapped service,
//	     * replace the context of the instruction (Whose instance is created) by the classpath of the wrapper:
//	     *
//	     * Note that the leftsidefieldtype is still the actual wrapped service classpath but this is intentional,
//	     * because when the service is created a new service handle is constructed.
//	     * This service handle needs to contain the same name that was requested
//	     * by the client in order to be recognized as such in later graph constructions.
//		 */
//		if(instNode.isServiceConstruct() && contextClassInfo.isWrapped()) {
//			instNode.setContext(contextClassInfo.actualClasspath());
//		}
//	}
//
//	// TODO to be implemented.
////	private void removeUnusedInputs() {
////		/*
////			Remove every input from the client that was not used:
////		 */
////		Collection<String> notUsedInputs = resolveInfo.getInputFields().getInputFields();
////		/*
////		 	Iterate over all the transmit nodes in the client graph
////		 	and remove every fieldname that is used by a transmit node.
////		  */
////		for(IBaseNode baseNode :
////				GraphTraversal.iterateNodesWithClassname(getClientExecPlan().getGraph(), TransmitDataNode.class.getName())) {
////			TransmitDataNode transmitDataNode = (TransmitDataNode) baseNode;
////			notUsedInputs.remove(transmitDataNode.getSendingFieldName());
////		}
////		/*
////			The remaining elements in notUsedInputs are not sent to any executor.
////			Add a delete node to remove them from the client:
////		 */
////		for(String notUsedInput : notUsedInputs) {
////			DeleteFieldNode deleteField = new DeleteFieldNode(notUsedInput);
////			nodeConsumesField(deleteField, );
////		}
////	}
//
//
//	/**
//	 * Determin an executor for each plan:
//	 * Current implementation just selects one by random.
//	 * This follows the idea that if all requests and executors are homogeneous,
//	 * assigning executors by random would divide the load onto all of them evenly.
//	 * TODO Strong assumptions though, so consider implementing a feedback system off to help distribute the load more intelligently.
//	 */
//	private void determineExecutors() {
//		for(ExecPlan plan: execPlans) {
//			if(plan == getClientExecPlan()) {
//				continue;
//			}
//			ExecutorHandle chosedTarget = resolveInfo.getExecutorSupplyCoordinator().scheduleNextAmong(plan.candidates());
//			/*
//				Remove every other executor:
//			 */
//			plan.removeCandidates(candidate->candidate!=chosedTarget);
//		}
//	}
//
//	private void resolveResults() {
//		/*
//		 * iterate all the fieldnames and return results to client.
//		 */
//
//		for (String resultFieldname : resultFieldnames()) {
//			if (FMCompositionParser.isConstant(resultFieldname)) {
//				continue; // no need to send back constants
//			}
//			FieldType resultFieldType = resultFieldtype(resultFieldname);
//			// Node that last produced the result:
//			BaseNode resultProducer = resultFieldType.getProducer();
//			ExecPlan resultExecPlan = getAssignedExec(resultProducer);
//
//			boolean servicePersistant = resultFieldType.isServiceInstance()
//					&& resolveInfo.getResolvePolicy().isPersistentService(resultFieldname);
//			boolean toBeReturned = servicePersistant || (!resultFieldType.isServiceInstance() && resolveInfo.getResolvePolicy().isToReturn(resultFieldname));
//
//			if (servicePersistant) {
//				/*
//				 * store service instance:
//				 */
//				ServiceInstanceStorageNode store = new ServiceInstanceStorageNode(resultFieldname,
//						resultFieldType.getTypeName());
//				assignNodeToExec(store, resultExecPlan);
//				nodeConsumesField(store, resultFieldType);
//			}
//			/*
//				If it comped from the client then dont return it:
//			 */
//			if(resolveInfo.getInputFields().isInputField(resultFieldname)) {
//				continue;
//			}
//
//			if (toBeReturned) {
//				/*
//				 * First check if the data is already available:
//				 */
//				boolean resultFoundOnClient = false;
//				for(FieldType field : resolveFieldname(resultFieldname)) {
//					if(getAssignedExec(field.getProducer()) == clientExecPlan) {
//						resultFoundOnClient = true;
//					}
//				}
//
//				if (!resultFoundOnClient) {
//					/*
//					 * return result to client
//					 */
//					createTransmission(resultExecPlan, clientExecPlan, resultFieldType);
//				}
//				markAsResult(resultFieldType);
//			}
//		}
//	}
//
//	private void markAsResult(FieldType resultFieldType) {
//		returnFieldnames.add(resultFieldType.getFieldname());
//	}
//
//	private void connectDependencyEdges() {
//		for(ExecPlan exec : getInvolvedExecutions()) {
//			CompositionGraph graph = exec.getGraph();
//			/*
//			 * connect every consumer in the graph to its producer:
//			 */
//			for(BaseNode consumer : GraphTraversal.iterateNodes(graph)) {
//				for(FieldType  consumedField : getConsumingFields(consumer)) {
//					BaseNode producer = consumedField.getProducer();
//					/*
//					 * assert that the producer is assigned to the same execution:
//					 */
//					if(getAssignedExec(producer) != getAssignedExec(consumer)) {
//						throw new RuntimeException("Coding error: consumer is somewhere else than the producer!");
//					}
//					exec.getGraph().connectNodes(producer, consumer);
//				}
//			}
//		}
//	}
//
//	/**
//	 * Add CollectError nodes to all executors and collect all errors at the client.
//	 * This needs to be performed after executors are determined.
//	 */
//	private void addErrorCollectors() {
//		/*
//		 * Add error collectors node to the executors.
//		 * These will collects execution exceptions from tasks into a map and write this map as a field called __execution_erros_id.
//		 * This field needs to be sent to the client.
//		 * The client collector needs to collect these maps and include it in its final error collection.
//		 */
//		final TypeClass errorFieldTypeClass = TypeClass.RealDataType;
//		final String errorFieldTypeName = "builtin.Dict";
//		final String errorSemanticType = "jsonobj";
//		final String caster = "de.upb.sede.BuiltinCaster";
//
//		List<String> clientFetschedErrorFields = new ArrayList<>(getInvolvedExecutions().size());
//		for(ExecPlan exec : getInvolvedExecutions()) {
//			if(exec == getClientExecPlan()) {
//				continue;
//			}
//			/*
//				Add node that collects errors:
//			 */
//			String executorId =  exec.getTarget().getExecutorId();
//			CollectErrorsNode collectErrorsNode = new CollectErrorsNode(executorId);
//			exec.getGraph().executeLast(Collections.singletonList(collectErrorsNode));
//			assignNodeToExec(collectErrorsNode, exec);
//
//			String errorFielname = collectErrorsNode.getFieldname();
//			clientFetschedErrorFields.add(errorFielname);
//			FieldType errorField = new FieldType(collectErrorsNode, errorFielname, errorFieldTypeClass, errorFieldTypeName,
//					true);
//			addFieldType(errorField);
//
//
//			/*
//				Transmit error to clinet:
//			 */
//			FieldType fetchedErrors = createTransmission(exec, getClientExecPlan(), errorField);
//
//			CastTypeNode castTypeNode = new CastTypeNode(errorFielname, errorSemanticType, errorFieldTypeName, false,
//					caster);
//			assignNodeToExec(castTypeNode, getClientExecPlan());
//
//			nodeConsumesField(castTypeNode, fetchedErrors);
//			FieldType parsedFetchedErrors = new FieldType(castTypeNode, errorFielname, errorFieldTypeClass, errorFieldTypeName,
//					false);
//			addFieldType(parsedFetchedErrors);
//
//			/*
//			   HACK:
//			 * Add transmit and cast dependency edges.
//			 */
//			exec.getGraph().connectNodes(collectErrorsNode, getConsumersOfField(errorField).get(0));
//			getClientExecPlan().getGraph().connectNodes(fetchedErrors.getProducer(), castTypeNode);
//		}
//
//		/*
//		 * Now collect the errors on the client:
//		 */
//		CollectErrorsNode clientSideCollect = new CollectErrorsNode(clientFetschedErrorFields, getClientExecPlan().getTarget().getExecutorId());
//		getClientExecPlan().getGraph().executeLast(Collections.singletonList(clientSideCollect));
//		assignNodeToExec(clientSideCollect, getClientExecPlan());
//
//		String errorFielname = clientSideCollect.getFieldname();
//		FieldType errorField = new FieldType(clientSideCollect, errorFielname, errorFieldTypeClass, errorFieldTypeName,
//				true);
//		addFieldType(errorField);
//	}
//
//	/**
//	 * This method fills in the contact information to all concerned transmission nodes.
//	 * Background:
//	 * Transmission nodes up until now dont yet have the concrete contact information.
//	 * That is because the execPlans hold candidates of executors.
//	 * Before this method is invoked 'determineExecutors' should have been invoked.
//	 * Thus at the time of the invocation of this method every exec plan now has a specific target executor.
//	 */
//	private void fillinContactInforamtions() {
//		for(ExecPlan execPlan : execPlans){
//			assert execPlan.targetDetermined();
//			/*
//				For every accept data node in the graph:
//				fill in the contact information to the corresponding transmission node.
//			 */
//			Map<String, Object> contactInformation = execPlan.getTarget().getContactInfo();
//			for(AcceptDataNode accepter : GraphTraversal.iterateNodesWithClass(execPlan.getGraph(),
//					AcceptDataNode.class)) {
//				getCorrespondingSender(accepter).getContactInfo().putAll(contactInformation);
//
//			}
//		}
//	}
//
//	private void addFinishingNodes() {
//
//		for(ExecPlan exec : getInvolvedExecutions()) {
//			if(exec == getClientExecPlan()) {
//				continue;
//			}
//
//			/*
//				Add node that indicate that the execution is done:
//		 	*/
//			String flagname = ("&finished&" + exec.getTarget().getExecutorId());
//			FinishNode finishNode = new FinishNode(getClientExecPlan().getTarget().getContactInfo(), flagname);
//			exec.getGraph().executeLast(Collections.singletonList(finishNode));
//
//
//			/*
//				Add accept node to client:
//			 */
//			AcceptDataNode acceptFinishFlag = new AcceptDataNode(flagname);
//			getClientExecPlan().getGraph().addNode(acceptFinishFlag);
//
//			getTransmissionGraph().addNode(finishNode);
//			getTransmissionGraph().addNode(acceptFinishFlag);
//			getTransmissionGraph().connectNodes(finishNode, acceptFinishFlag);
//		}
//	}
//
//	private ExecPlan getOrCreateExecutionForId(final String executorId) {
//		/*
//		 * First search the list of already involved executors to look for it:
//		 */
//		Predicate<ExecutorHandle> executorWithId = handle ->  handle.getExecutorId().equals(executorId);
//		for (ExecPlan exec : execPlans) {
//			if(exec.targetDetermined()) {
//				if (executorWithId.test(exec.getTarget())) {
//					return exec;
//				}
//			} else {
//				boolean executorWithIdFound = exec.candidates().stream().anyMatch(executorWithId);
//				if(executorWithIdFound) {
//					/*
//						Remove all other executor:
//					 */
//					exec.removeCandidates(executorWithId.negate());
//					return exec;
//				}
//			}
//		}
//		/*
//		 * No involved executor has the given id.
//		 * Search in the registered executors list:
//		 */
//		if (resolveInfo.getExecutorSupplyCoordinator().hasExecutor(executorId)) {
//			ExecutorHandle executor = resolveInfo.getExecutorSupplyCoordinator().getExecutorFor(executorId);
//
//			/*
//			 * Add a new executor to the involved list of executors:
//			 */
//			ExecPlan exec = new ExecPlan(executor);
//			execPlans.add(exec);
//			return exec;
//		} else {
//			throw new RuntimeException("An unknown executor with id: \"" + executorId + "\" in referenced in the composition." +
//                " No such executor is registered.");
//		}
//	}
//
//	private void addFieldType(FieldType fieldType) {
//		if (fieldType.isChangedState() || !fieldnameTypeResult.containsKey(fieldType.getFieldname())) {
//			fieldnameTypeResult.put(fieldType.getFieldname(), new ArrayList<>());
//		}
//		fieldnameTypeResult.get(fieldType.getFieldname()).add(fieldType);
//		/*
//			Add producer:
//			(Not needed)
//		 */
////		if (!fieldProducers.get(fieldType).contains(fieldType.getProducer())) {
////			fieldProducers.get(fieldType).add(fieldType.getProducer());
////		}
//	}
//
//	private AcceptDataNode clientAcceptInput(String fieldname) {
//		AcceptDataNode accept = new AcceptDataNode(fieldname);
//		assignNodeToExec(accept, clientExecPlan);
//		Map<String, Object> clientContactInfo = clientExecPlan.getTarget().getContactInfo();
//		TransmitDataNode transmit = new TransmitDataNode(fieldname, clientContactInfo);
//
//		addToTransGraph(transmit, accept);
//
//		return accept;
//	}
//
//	private FieldType createTransmission(ExecPlan sourceExec, ExecPlan targetExec, FieldType datafield) {
//		String fieldname = datafield.getFieldname();
//		AcceptDataNode accept = new AcceptDataNode(fieldname);
//		assignNodeToExec(accept, targetExec);
//		/*
//		 * The exec plan might still have multiple execution candidates.
//		 * So there is no specific contact information to choose yet.
//		 * Instead use an empty map. Later down the line when the target executor is clear,
//		 * fill this map with the actual contact information of the chosen executors.
//		 * This happens in fillContactInformation method. (See above)
//		 */
//		Map<String, Object> contactInfo = new HashMap<>();
//		TransmitDataNode transmit;
//		if(datafield.isRealData()) {
//			String caster = resolveInfo.getTypeConfig().getOnthologicalCaster(datafield.getTypeName());
//			String semanticType = resolveInfo.getTypeConfig().getOnthologicalType(datafield.getTypeName());
//			transmit = new TransmitDataNode(fieldname, contactInfo, caster, semanticType);
//		}
//		else {
//			transmit = new TransmitDataNode(fieldname, contactInfo);
//		}
//
//		assignNodeToExec(transmit, sourceExec);
//		nodeConsumesField(transmit, datafield);
//		addToTransGraph(transmit, accept);
//
//		FieldType inputField;
//		if (datafield.isRealData()) {
//			TypeClass inputTypeClass = TypeClass.SemanticDataType;
//			String typeName = resolveInfo.getTypeConfig().getOnthologicalType(datafield.getTypeName());
//			inputField = new FieldType(accept, fieldname, inputTypeClass, typeName, false);
//		} else  {
//			inputField = datafield.clone(accept, false);
//		}
//		addFieldType(inputField);
//		return inputField;
//	}
//
//	private void addToTransGraph(TransmitDataNode transmit, AcceptDataNode accept) {
//		dataTransmissionGraph.addNode(transmit);
//		dataTransmissionGraph.addNode(accept);
//		dataTransmissionGraph.connectNodes(transmit, accept);
//	}
//
//	private TransmitDataNode getCorrespondingSender(AcceptDataNode accepter) {
//		/*
//		 * Find the transmission node which sends the data that is in turn accepted by the given accepter:
//		 */
//		return (TransmitDataNode) GraphTraversal.sourceNodes(dataTransmissionGraph, accepter).iterator().next();
//	}
//
//	private void assignNodeToExec(BaseNode node, ExecPlan exec) {
//		if (isAssignedToExec(node)) {
//			throw new RuntimeException("Coding error: each node can only be assigned to a single execution.");
//		}
//		exec.getGraph().addNode(node);
//		nodeExecutionAssignment.put(node, exec);
//	}
//
//	private ExecPlan getAssignedExec(BaseNode node) {
//		if (!isAssignedToExec(node)) {
//			throw new CompositionSemanticException("The node `" + node.toString() + "` isn't assigned to a execution.");
//		}
//		return nodeExecutionAssignment.get(node);
//	}
//
//	private boolean isAssignedToExec(BaseNode node) {
//		return nodeExecutionAssignment.containsKey(node);
//	}
//
//	/*
//		(Not needed)
//	 */
////	List<IBaseNode> getProducers(FieldType fieldType) {
////		return Collections.unmodifiableList(fieldProducers.get(fieldType));
////	}
//
//	private List<FieldType> resolveFieldname(String fieldname) {
//		if (!this.fieldnameTypeResult.containsKey(fieldname)) {
//			throw new CompositionSemanticException("The fieldname " + fieldname + " cannot be resolved.");
//		} else {
//			return this.fieldnameTypeResult.get(fieldname);
//		}
//	}
//	private boolean isResolvable(String fieldname) {
//		return this.fieldnameTypeResult.containsKey(fieldname) && !this.fieldnameTypeResult.get(fieldname).isEmpty();
//	}
//
//	void nodeConsumesField(BaseNode consumer, FieldType consumedField) {
//		// assert that they both are on the same executor
//		assert getAssignedExec(consumedField.getProducer()) == getAssignedExec(consumer);
//		nodeConsumingFields.get(consumer).add(consumedField);
//	}
//
//	private FieldType getFieldType(BaseNode consumer, String fieldname) {
//		for (FieldType ft : nodeConsumingFields.get(consumer)) {
//			if (ft.getFieldname().equals(fieldname)) {
//				return ft;
//			}
//		}
//		throw new RuntimeException("Consumer  " + consumer.toString() + " doesn't know the field: " + fieldname + ".");
//	}
//
//	private List<FieldType> getConsumingFields(BaseNode consumer) {
//		return Collections.unmodifiableList(nodeConsumingFields.get(consumer));
//	}
//	private List<BaseNode> getConsumersOfField(FieldType field) {
//		List<BaseNode> consumers = new ArrayList<>();
//		for(BaseNode baseNode : GraphTraversal.iterateNodes(getAssignedExec(field.getProducer()).getGraph())) {
//			if(getConsumingFields(baseNode).contains(field)) {
//				consumers.add(baseNode);
//			}
//		}
//		return consumers;
//	}
//
//	private Set<String> resultFieldnames() {
//		return fieldnameTypeResult.keySet();
//	}
//
//	private FieldType resultFieldtype(String fieldname) {
//		for (FieldType fieldType : fieldnameTypeResult.get(fieldname)) {
//			if (fieldType.isChangedState()) {
//				return fieldType;
//			}
//		}
//		throw new RuntimeException("Field " + fieldname + " doens't contain any original fieldtype.");
//	}
//
//	/**
//	 * Returns true if the field can be moved to the executor.
//	 */
//	private boolean canMoveOntoExecutor(FieldType field, ExecPlan execution) {
//		BaseNode bn = field.getProducer();
//		for(BaseNode consumerNodes : getConsumersOfField(field)){
//			/*
//				if there is a node that needs this field which isn't on the execution  outside from the target execution we cannot move the field
//			 */
//			if(!canMoveOntoExecutor(consumerNodes, execution)){
//
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * Returns true if the node can be moved to the execution.
//	 */
//	private boolean canMoveOntoExecutor(BaseNode node, ExecPlan execution) {
//		if(node instanceof InstructionNode) {
//			InstructionNode instruction = (InstructionNode) node;
//			if(instruction.isContextAFieldname()){
//				if(!isResolvable(instruction.getContext())){
//					/*
//						The context of the instruction cannot be resolved.
//						That means this instruction cannot be moved:
//					 */
//					return false;
//				} else{
//					FieldType serviceInstance = resolveFieldname(instruction.getContext()).get(0);
//					String serviceName = serviceInstance.getTypeName();
//					/*
//						We can only move the service instance to the executor if it supports the service:
//					 */
//					if(!execution.getTarget().getExecutionerCapabilities().supportsServiceClass(serviceName)) {
//						return false;
//					}
//
//				}
//			}
//
//			/*
//				See if the executor supports the service:
//			 */
//		}
//		return false;
//	}


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

