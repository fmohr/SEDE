package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.nodes.AcceptDataNode;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.CastTypeNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.composition.graphs.nodes.TransmitDataNode;
import de.upb.sede.config.ClassesConfig.ClassInfo;
import de.upb.sede.config.ClassesConfig.MethodInfo;
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

	private final List<Execution> executions;

	private final Execution clientExecution;

	private final CompositionGraph dataTransmissionGraph;

	private final DefaultMap<BaseNode, List<FieldType>> nodeConsumingFields = new DefaultMap<>(ArrayList::new);
	private final DefaultMap<FieldType, List<BaseNode>> fieldProducers = new DefaultMap<>(ArrayList::new);

	private final Map<BaseNode, Execution> nodeExecutionAssignment = new HashMap<>();

	public DataFlowAnalysis(ResolveInfo resolveInfo, List<InstructionNode> instructionNodes) {
		this.resolveInfo = Objects.requireNonNull(resolveInfo);
		clientExecution = new Execution(resolveInfo.getClientInfo().getClientExecutor());
		executions = new ArrayList<>();
		executions.add(clientExecution);
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
				typeClass = TypeClass.SemanticDataType;
				FieldType fieldType = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(fieldType);
			}
		}
	}

	private void resolveExecution(InstructionNode instNode) {
		Execution resolvedExecution = null;
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
				resolvedExecution = getOrCreateExecutionForHost(serviceInstanceHandle.getHost());
			} else {
				/*
				 * The field is bound to a new service instance. producer is another instruction
				 * node.
				 */
				BaseNode producer = serviceInstanceField.getProducer();
				resolvedExecution = getAssignedExec(producer);
			}
		} else {
			/*
			 * instructions context is a service classpath.
			 */
			String serviceClasspath = instNode.getContext();
			boolean found = false;
			for (Execution exec : executions) {
				ExecutorHandle executor = exec.getExecutor();
				if (executor.getExecutionerCapabilities().supportsServiceClass(serviceClasspath)) {
					found = true;
					resolvedExecution = exec;
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
				resolvedExecution = new Execution(newExecutor);
				executions.add(resolvedExecution);
			}
		}
		assignNodeToExec(instNode, resolvedExecution);
	}

	private void analyzeDataFlow(InstructionNode instNode) {
		/*
		 * map this instruction to all fieldtypes it is depending on. (consuming)
		 */
		Execution instExec = getAssignedExec(instNode);
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
			Execution sourceExec = getAssignedExec(serviceInstance.getProducer());
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
				ServiceInstanceStorageNode storageNode = new ServiceInstanceStorageNode(true, serviceInstanceFieldname,
						contextClasspath);
				assignNodeToExec(storageNode, instExec);

				nodeConsumesField(storageNode, serviceInstance);

				serviceInstance = new FieldType(storageNode, serviceInstanceFieldname, TypeClass.ServiceInstance,
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
		}
		List<String> requiredParamTypes = methodInfo.paramTypes();

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
					constantType = new FieldType(parseConstantNode, parameter, TypeClass.ConstantPrimitivType,
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
					Execution sourceExec = getAssignedExec(requiredData.getProducer());
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
			if(hasFieldname(leftsideFieldname) ) {
				FieldType fieldType = resultFieldtype(leftsideFieldname);
				/*
				 * only consume the fieldtype if its on the same executor:
				 */
				if(getAssignedExec(fieldType.getProducer())==getAssignedExec(instNode)) {
					nodeConsumesField(instNode, resultFieldtype(leftsideFieldname));
				}
			}
			/*
			 * Resolve the type of the left side fieldname:
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
				typeClass = TypeClass.RealDataType;
				typeName = methodInfo.getReturnType();
			}
			FieldType leftSideFieldType = new FieldType(instNode, leftsideFieldname, typeClass, typeName,
					true);
			addFieldType(leftSideFieldType);
		}
	}

	private void resolveResults() {
		/*
		 * iterate all the fieldnames and return results to client.
		 */

		for (String resultFieldname : resultFieldnames()) {
			FieldType resultFieldType = resultFieldtype(resultFieldname);
			if (resultFieldType.isConstant()) {
				continue; // no need to send back constants
			}
			BaseNode resultProducer = resultFieldType.getProducer();
			Execution resultExecution = getAssignedExec(resultProducer);

			if (clientExecution == resultExecution) {
				continue;
			}
			if (resolveInfo.getResolvePolicy().isToReturn(resultFieldname)) {

				/*
				 * return result to client
				 */
				createTransmission(resultExecution, clientExecution, resultFieldType);
			}
			if (resultFieldType.isServiceInstance()
					&& resolveInfo.getResolvePolicy().isPersistentService(resultFieldname)) {
				/*
				 * store service instance:
				 */
				ServiceInstanceStorageNode store = new ServiceInstanceStorageNode(false, resultFieldname,
						resultFieldType.getTypeName());
				assignNodeToExec(store, resultExecution);
				nodeConsumesField(store, resultFieldType);
			}
		}
	}

	private void connectDependencyEdges() {
		for(Execution exec : getInvolvedExecutions()) {
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

	private Execution getOrCreateExecutionForHost(String executorHost) {
		if (resolveInfo.getExecutorCoordinator().hasExecutor(executorHost)) {
			ExecutorHandle executor = resolveInfo.getExecutorCoordinator().getExecutorFor(executorHost);
			for (Execution exec : executions) {
				if (exec.getExecutor().equals(executor)) {
					return exec;
				}
			}
			/*
			 * None found
			 */
			Execution exec = new Execution(executor);
			executions.add(exec);
			return exec;

		} else {
			throw new RuntimeException("Cannot resolve executor with host: \"" + executorHost + "\". No such executor is registered.");
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
		assignNodeToExec(accept, clientExecution);
		String clientHost = clientExecution.getExecutor().getHostAddress();
		TransmitDataNode transmit = new TransmitDataNode(fieldname, clientHost);

		addTransmission(transmit, accept);

		return accept;
	}

	private FieldType createTransmission(Execution sourceExec, Execution targetExec, FieldType datafield) {
		String fieldname = datafield.getFieldname();
		AcceptDataNode accept = new AcceptDataNode(fieldname);
		assignNodeToExec(accept, targetExec);

		TransmitDataNode transmit = new TransmitDataNode(fieldname, targetExec.getExecutor().getHostAddress());
		assignNodeToExec(transmit, sourceExec);
		nodeConsumesField(transmit, datafield);
		addTransmission(transmit, accept);

		FieldType inputField;
		if (datafield.isRealData()) {
			TypeClass inputTypeClass = TypeClass.SemanticDataType;
			String typeName = resolveInfo.getTypeConfig().getOnthologicalType(datafield.getTypeName());
			inputField = new FieldType(accept, fieldname, inputTypeClass, typeName, false);

		} else if (datafield.isServiceInstance() || datafield.isServiceInstanceHandle() || datafield.isSemanticData()) {
			inputField = datafield.clone(accept, false);
		} else {
			throw new RuntimeException(
					"Coding error: Primitive datatypes should not be transmitted: " + datafield.toString());
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

	private void assignNodeToExec(BaseNode node, Execution exec) {
		if (isAssignedToExec(node)) {
			throw new RuntimeException("Coding error: each node can only be assigned to a single execution.");
		}
		exec.getGraph().addNode(node);
		nodeExecutionAssignment.put(node, exec);
	}

	private Execution getAssignedExec(BaseNode node) {
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

	private boolean hasFieldname(String fieldname) {
		return this.fieldnameTypeResult.containsKey(fieldname);
	}

	private boolean isResolvable(String fieldname) {
		return this.fieldnameTypeResult.containsKey(fieldname) && !this.fieldnameTypeResult.get(fieldname).isEmpty();
	}

	void nodeConsumesField(BaseNode consumer, FieldType consumedField) {
		// assert that they both are on the same executor
		assert getAssignedExec(consumedField.getProducer()) == getAssignedExec(consumer);
		nodeConsumingFields.get(consumer).add(consumedField);
	}

	FieldType getFieldType(BaseNode consumer, String fieldname) {
		for (FieldType ft : nodeConsumingFields.get(consumer)) {
			if (ft.getFieldname().equals(fieldname)) {
				return ft;
			}
		}
		throw new RuntimeException("Consumer  " + consumer.toString() + " doesn't know the field: " + fieldname + ".");
	}

	List<FieldType> getConsumingFields(BaseNode consumer) {
		return Collections.unmodifiableList(nodeConsumingFields.get(consumer));
	}

	Set<String> resultFieldnames() {
		return fieldnameTypeResult.keySet();
	}

	FieldType resultFieldtype(String fieldname) {
		for (FieldType fieldType : fieldnameTypeResult.get(fieldname)) {
			if (fieldType.isChangedState()) {
				return fieldType;
			}
		}
		throw new RuntimeException("Field " + fieldname + " doens't contain any original fieldtype.");
	}

	public Execution getClientExecution() {
		return clientExecution;
	}

	public List<Execution> getInvolvedExecutions() {
		return executions;
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

	public boolean isConstant() {
		return typeClass == TypeClass.ConstantPrimitivType;
	}

	public String toString() {
		return fieldname + " type=(" + typeClass + ", " + typeName + ")" + (changedState ? "" : "...");
	}

	public FieldType clone(BaseNode newProducer, boolean changesState) {
		return new FieldType(newProducer, getFieldname(), getTypeClass(), getTypeName(), changesState);
	}

}

enum TypeClass {
	ServiceInstance, SemanticDataType, RealDataType, ConstantPrimitivType, ServiceInstanceHandle;
}

class Execution {
	private final CompositionGraph graph;
	private final ExecutorHandle executor;

	Execution(ExecutorHandle executor) {
		this.graph = new CompositionGraph();
		this.executor = executor;
	}

	public CompositionGraph getGraph() {
		return graph;
	}

	public ExecutorHandle getExecutor() {
		return executor;
	}

}
