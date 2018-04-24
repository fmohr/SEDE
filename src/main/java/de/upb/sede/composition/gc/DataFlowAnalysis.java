package de.upb.sede.composition.gc;

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
import de.upb.sede.config.ClassesConfig.ClassInfo;
import de.upb.sede.config.ClassesConfig.MethodInfo;
import de.upb.sede.exceptions.CompositionSemanticException;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.util.DefaultMap;

public class DataFlowAnalysis {
	private final ResolveInfo resolveInfo;

	private final List<AcceptDataNode> clientInputNodes = new ArrayList<>();

	private final Map<String, List<FieldType>> fieldnameTypeResult = new HashMap<>();

	private final DefaultMap<BaseNode, List<FieldType>> nodeConsumingFields = new DefaultMap<>(ArrayList::new);
	private final DefaultMap<FieldType, List<BaseNode>> fieldProducers = new DefaultMap<>(ArrayList::new);

	public DataFlowAnalysis(ResolveInfo resolveInfo, List<InstructionNode> instructionNodes) {
		this.resolveInfo = Objects.requireNonNull(resolveInfo);
		analyzeDataFlow(Objects.requireNonNull(instructionNodes));
	}

	private void analyzeDataFlow(List<InstructionNode> instructionNodes) {
		assert !instructionNodes.isEmpty();
		addInputNodesFieldTypes();
		resolveInstructionNodes(instructionNodes);

	}

	private void addInputNodesFieldTypes() {
		InputFields inputFields = resolveInfo.getInputFields();
		for (String inputFieldname : inputFields.iterateInputs()) {
			AcceptDataNode acceptNode = new AcceptDataNode(inputFieldname);
			clientInputNodes.add(acceptNode);
			boolean isServiceInstance = inputFields.isServiceInstance(inputFieldname);
			String typeName;
			TypeClass typeClass;
			if (isServiceInstance) {
				String serviceClasspath = inputFields.getServiceInstanceHandle(inputFieldname).getClasspath();
				typeName = serviceClasspath;
				typeClass = TypeClass.ServiceInstance;
				/* add intermediate service instance storage node */
				ServiceInstanceStorageNode storageNode = new ServiceInstanceStorageNode(true, inputFieldname,
						serviceClasspath);
				FieldType serviceHandle = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(serviceHandle);
				nodeConsumesField(storageNode, serviceHandle);

				FieldType serviceInstance = new FieldType(storageNode, inputFieldname, typeClass, typeName, true);
				addFieldType(serviceInstance);
			} else {
				typeName = inputFields.getInputFieldType(inputFieldname);
				typeClass = TypeClass.SemanticDataType;
				FieldType fieldType = new FieldType(acceptNode, inputFieldname, typeClass, typeName, true);
				addFieldType(fieldType);
			}
		}
	}

	private void resolveInstructionNodes(List<InstructionNode> instructionNodes) {
		for (InstructionNode instNode : instructionNodes) {
			/*
			 * map this instruction to all fieldtypes it is depending on. (consuming)
			 */
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
				nodeConsumesField(instNode, serviceInstance);
				contextClasspath = serviceInstance.getTypeName();

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
				throw new CompositionSemanticException(
						"Instruction: " + instNode.toString() + " states that there are  " + stated
								+ " amount of parameters while the classes config defines " + actual + " many.");
			}
			for (int index = 0, size = instParamFieldnames.size(); index < size; index++) {
				String parameter = instParamFieldnames.get(index);
				String requiredType = requiredParamTypes.get(index);
				if (FMCompositionParser.isConstant(parameter)) {
					/*
					 * if the parameter is a constant it needs to be parsed.
					 */
					FieldType constantType;
					if (!isResolvable(parameter)) {
						/*
						 * add constant parser and its fieldtype:
						 */
						ParseConstantNode parseConstantNode = new ParseConstantNode(parameter);
						constantType = new FieldType(parseConstantNode, parameter, TypeClass.ConstantPrimitivType,
								"constant", true);
						addFieldType(constantType);
					} else {
						/*
						 * constant is already being parsed.
						 */
						constantType = resolveFieldname(parameter).get(0);
					}
					nodeConsumesField(instNode, constantType);
					// TODO check if the required type matchers the type of the constant.
				} else {
					/*
					 * if the parameter is data the required type needs to be found/created.
					 */
					List<FieldType> paramFieldTypes = resolveFieldname(parameter);
					boolean found = false;
					FieldType semanticDataType = null;
					for (FieldType paramType : paramFieldTypes) {
						if (paramType.getTypeClass() == TypeClass.RealDataType
								&& paramType.getTypeName().equals(requiredType)) {
							found = true;
							nodeConsumesField(instNode, paramType);
							break;
						}
						if (paramType.getTypeClass() == TypeClass.SemanticDataType) {
							semanticDataType = paramType;
						}
					}
					if (!found) {
						/*
						 * no field with fitting type found. if no semantic field is available cast one
						 * field to its semantic type. cast the field with the semantic type to the
						 * required type.
						 */
						if (semanticDataType == null) {
							/*
							 * cast one fieldtype to its semantic type:
							 */
							FieldType castFrom = paramFieldTypes.get(0);
							String originType = castFrom.getTypeName();
							if (castFrom.getTypeClass() != TypeClass.RealDataType) {
								throw new CompositionSemanticException(
										"Parameters can only have real data types bounded to their fieldname("
												+ parameter + ") but this fieldnames type is: " + originType + ".\n"
												+ instNode.toString());
							}
							String targetSemanticType = resolveInfo.getTypeConfig().getOnthologicalType(originType);
							String casterClasspath = resolveInfo.getTypeConfig().getOnthologicalCaster(originType);
							CastTypeNode castTypeNode = new CastTypeNode(parameter, originType, targetSemanticType,
									true, casterClasspath);
							nodeConsumesField(castTypeNode, castFrom);
							semanticDataType = new FieldType(castTypeNode, parameter, TypeClass.SemanticDataType,
									targetSemanticType, false);
							addFieldType(semanticDataType);
						}

						/*
						 * cast the semantic type to the required type:
						 */
						String originSemanticType = semanticDataType.getTypeName();
						/*
						 * assert that the found semantic type equals the semantic type of the required
						 * type as stated by the type configuration.
						 */
						String configurationSemanticType = resolveInfo.getTypeConfig()
								.getOnthologicalType(requiredType);
						if (!configurationSemanticType.equals(originSemanticType)) {
							throw new CompositionSemanticException("The required parameter(" + parameter + ") type, "
									+ requiredType + ", corresponds to a different semantic type, "
									+ configurationSemanticType + ", than what is bounded to the fieldname: "
									+ originSemanticType + ".\t" + instNode.toString());
						}
						String casterClasspath = resolveInfo.getTypeConfig().getOnthologicalCaster(requiredType);
						CastTypeNode castTypeNode = new CastTypeNode(parameter, originSemanticType, requiredType, false,
								casterClasspath);
						nodeConsumesField(castTypeNode, semanticDataType);
						FieldType requiredParamType = new FieldType(castTypeNode, parameter, TypeClass.RealDataType,
								requiredType, false);
						addFieldType(requiredParamType);
						nodeConsumesField(instNode, requiredParamType);
					}

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
				String serviceInstanceFieldname = instNode.getContext();

				FieldType serviceInstanceFieldType = new FieldType(instNode, serviceInstanceFieldname,
						TypeClass.ServiceInstance, contextClasspath, true);
				addFieldType(serviceInstanceFieldType);
			}
			if (instNode.isAssignedLeftSideFieldname()) {
				/*
				 * the instruction outputs a new value to the leftside fieldname: Resolve the
				 * type of the left side fieldname:
				 */
				TypeClass typeClass;
				String typeName;

				if (instNode.isServiceConstruct()) {
					typeClass = TypeClass.ServiceInstance;
					/*
					 * the type of the leftside field is the classpath of the constructor that is
					 * invoked
					 */
					typeName = instNode.getContext();
				} else if (resolveInfo.getClassesConfiguration().classInfo(contextClasspath).hasMethod(methodname)) {
					typeClass = TypeClass.RealDataType;
					/* return type defined in the classes configuration */
					typeName = resolveInfo.getClassesConfiguration().classInfo(contextClasspath).methodInfo(methodname)
							.getReturnType();
				} else {
					throw new CompositionSemanticException("The type of the leftside fieldname of instruction: "
							+ instNode.toString() + " cannot be resolved.");
				}
				FieldType leftSideFieldType = new FieldType(instNode, instNode.getLeftSideFieldname(), typeClass,
						typeName, true);
				addFieldType(leftSideFieldType);
			}

		}
	}

	private void addFieldType(FieldType fieldType) {
		if (fieldType.isChangedState() || !fieldnameTypeResult.containsKey(fieldType.getFieldname())) {
			fieldnameTypeResult.put(fieldType.getFieldname(), new ArrayList<>());
		}
		fieldnameTypeResult.get(fieldType.getFieldname()).add(fieldType);
		nodeProducesField(fieldType.getProducer(), fieldType);
	}

	void nodeProducesField(BaseNode node, FieldType fieldType) {
		if (!fieldProducers.get(fieldType).contains(node)) {
			fieldProducers.get(fieldType).add(node);
		}
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
		nodeConsumingFields.get(consumer).add(consumedField);
	}

	List<AcceptDataNode> getClientInputNodes() {
		return clientInputNodes;
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

	public boolean isSemanticData() {
		return typeClass == TypeClass.SemanticDataType;
	}

	public boolean isRealData() {
		return typeClass == TypeClass.RealDataType;
	}

	public String toString() {
		return fieldname + " (" + typeClass + "|" + typeName + ")" + (changedState ? "!" : "");
	}

}

enum TypeClass {
	ServiceInstance, SemanticDataType, RealDataType, ConstantPrimitivType;
}
