package de.upb.sede.composition.graphs.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import de.upb.sede.composition.graphs.nodes.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.exceptions.CompositionGraphSerializationException;

/**
 * This class defines methods to serialize and deserialize instruction nodes
 * into their JSON representation. These methods are only to be used from
 * GraphJsonSerializer.
 * 
 * @author aminfaez
 *
 */
public final class NodeJsonSerializer {

	public static final String NODETYPE = "nodetype";
	public static final String NODETYPE_INSTRUCTION = "Instruction";
	public static final String NODETYPE_ACCEPT_DATA = "AcceptData";
	public static final String NODETYPE_CAST_TYPE = "CastType";
	public static final String NODETYPE_PARSE_CONSTANT = "ParseConstant";
	public static final String NODETYPE_TRANSMIT_DATA = "TransmitData";
	public static final String NODETYPE_SEND_GRAPH = "SendGraph";
	public static final String NODETYPE_INT_EXEC = "InterruptExec";
	public static final String NODETYPE_FINISH = "Finish";
	public static final String NODETYPE_SERVICE_INSTANCE_STORAGE = "ServiceInstanceStorage";

	public final BaseNode fromJSON(Map<Object, Object> jsonObject) {
		if (!jsonObject.containsKey(NODETYPE)) {
			throw new CompositionGraphSerializationException(
					"The given json object doens't contain the " + NODETYPE + " field.");
		}
		/*
		 * Get the right method name. The deserialization method name is equal to the
		 * nodetype field plus 'NodeFromJson'
		 */
		String nodeType = (String) jsonObject.get(NODETYPE);
		String deserializeMethodName = nodeType + "NodeFromJSON";
		Method deserializeMethod;
		BaseNode deserializedNode;
		/*
		 * Deserialize from json using reflection
		 */
		try {
			deserializeMethod = NodeJsonSerializer.class.getMethod(deserializeMethodName, Map.class);
			deserializedNode = (BaseNode) deserializeMethod.invoke(this, jsonObject);
		} catch (NoSuchMethodException e) {
			// Deserialization method not found.
			throw new CompositionGraphSerializationException(
					"No deserializer " + deserializeMethodName + " defined in NodeJsonSerializer.");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new RuntimeException(e); // cant access reflection api
		}
		return deserializedNode;
	}

	public final JSONObject toJSON(BaseNode baseNode) {
		/*
		 * Get the right method name. The serialization method name is equal to the
		 * simpleclassname field plus 'ToJson'
		 */
		String nodeSimpleClassname = baseNode.getClass().getSimpleName();
		String serializeMethodName = nodeSimpleClassname + "ToJSON";
		Method serializeMethod;
		JSONObject serializedJsonObject;
		/*
		 * serialize to json using reflection
		 */
		try {
			serializeMethod = NodeJsonSerializer.class.getMethod(serializeMethodName, baseNode.getClass());
			serializedJsonObject = (JSONObject) serializeMethod.invoke(this, baseNode);
		} catch (NoSuchMethodException e) {
			// Deserialization method not found.
			throw new CompositionGraphSerializationException(
					"No serializer " + serializeMethodName + " defined in NodeJsonSerializer: " + e.getMessage());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new RuntimeException(e); // cant access reflection api
		}
		return serializedJsonObject;
	}

	@SuppressWarnings("unchecked")
	public JSONObject InstructionNodeToJSON(InstructionNode instructionNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_INSTRUCTION);
		jsonObject.put("fmInstruction", instructionNode.getFmInstruction());
		jsonObject.put("leftsidefieldname",
				instructionNode.isAssignedLeftSideFieldname() ? instructionNode.getLeftSideFieldname() : null);
		jsonObject.put("leftsidefieldtype",
				instructionNode.isAssignedLeftSideFieldtype() ? instructionNode.getLeftSideFieldtype() : null);
		jsonObject.put("host", instructionNode.isAssignedHost() ? instructionNode.getHost() : null);
		jsonObject.put("context", instructionNode.getContext());
		jsonObject.put("method", instructionNode.getMethod());
		jsonObject.put("params", instructionNode.getParameterFields());
		jsonObject.put("param-types", instructionNode.getParameterTypes());
		jsonObject.put("is-service-construction", instructionNode.isServiceConstruct());
		jsonObject.put("is-context-a-fieldname", instructionNode.isContextAFieldname());
		return jsonObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InstructionNode InstructionNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String fmInstruction = (String) node.get("fmInstruction");
		String leftsidefieldname = (String) node.get("leftsidefieldname");
		String leftsidefieldtype = (String) node.get("leftsidefieldtype");
		String host = (String) node.get("host");
		String context = (String) node.get("context");
		boolean contextisfield = (Boolean) node.get("is-context-a-fieldname");
		String method = (String) node.get("method");
		List params = (JSONArray) node.get("params");
		List paramTypes = (JSONArray) node.get("param-types");

		InstructionNode instructionNode = new InstructionNode(fmInstruction, context, method);
		instructionNode.setContextIsField(contextisfield);
		if (leftsidefieldname != null) {
			instructionNode.setLeftSideFieldname(leftsidefieldname);
		}
		if(leftsidefieldtype != null) {
			instructionNode.setLeftSideFieldtype(leftsidefieldtype);
		}
		if (host != null) {
			instructionNode.setHost(host);
		}
		if (params != null) {
			instructionNode.setParameterFields(params);
		}
		if(paramTypes != null){
			instructionNode.setParameterType(paramTypes);
		}
		return instructionNode;
	}

	@SuppressWarnings("unchecked")
	public JSONObject AcceptDataNodeToJSON(AcceptDataNode receiveNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_ACCEPT_DATA);
		jsonObject.put("fieldname", receiveNode.getReceivingFieldname());
		return jsonObject;
	}

	public AcceptDataNode AcceptDataNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_ACCEPT_DATA);
		String fieldname = (String) node.get("fieldname");
		AcceptDataNode n = new AcceptDataNode(fieldname);
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject ParseConstantNodeToJSON(ParseConstantNode parseConstantNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_PARSE_CONSTANT);
		jsonObject.put("constant", parseConstantNode.getConstant());

		jsonObject.put("isString", parseConstantNode.getType() == ParseConstantNode.ConstantType.String);
		jsonObject.put("isBool", parseConstantNode.getType() == ParseConstantNode.ConstantType.Bool);
		jsonObject.put("isNumber", parseConstantNode.getType() == ParseConstantNode.ConstantType.Number);
		jsonObject.put("isNULL", parseConstantNode.getType() == ParseConstantNode.ConstantType.NULL);

		return jsonObject;
	}

	public ParseConstantNode ParseConstantNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_PARSE_CONSTANT);
		String constant = (String) node.get("constant");
		ParseConstantNode n = new ParseConstantNode(constant);
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject TransmitDataNodeToJSON(TransmitDataNode transmitDataNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_TRANSMIT_DATA);
		jsonObject.put("contact-info", transmitDataNode.getContactInfo());
		jsonObject.put("fieldname", transmitDataNode.getSendingFieldName());
		if(transmitDataNode.hasCaster()){
			jsonObject.put("caster", transmitDataNode.getCaster());
		}
		if(transmitDataNode.hasSemanticType()){
			jsonObject.put("semantic-type", transmitDataNode.getSemanticTypename());
		}
		return jsonObject;
	}

	public TransmitDataNode TransmitDataNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_TRANSMIT_DATA);
		Map<String, String> contactInfo = (Map<String, String>) node.get("contact-info");
		String fieldname = (String) node.get("fieldname");

		String caster = (String) node.get("caster");
		String semanticType = (String) node.get("semantic-type");
		TransmitDataNode n;
		if(caster == null || semanticType == null){
			n = new TransmitDataNode(fieldname, contactInfo);
		} else{
			n = new TransmitDataNode(fieldname, contactInfo, caster, semanticType);
		}

		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject SendGraphNodeToJSON(SendGraphNode sendGraphNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_SEND_GRAPH);
		jsonObject.put("contact-info", sendGraphNode.getContactInfo());
		jsonObject.put("graph", sendGraphNode.getGraph());
		return jsonObject;
	}

	public SendGraphNode SendGraphNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_SEND_GRAPH);
		Object contactInfo = (Object) node.get("contact-info");
		String graph = (String) node.get("graph");
		SendGraphNode n = new SendGraphNode(graph, contactInfo);
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject InterruptExecNodeToJSON(InterruptExecNode interruptExecNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_INT_EXEC);
		jsonObject.put("contact-info", interruptExecNode.getContactInfo());
		return jsonObject;
	}

	public InterruptExecNode InterruptExecNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INT_EXEC);
		Object contactInfo = (Object) node.get("contact-info");
		InterruptExecNode n = new InterruptExecNode(contactInfo);
		return n;
	}


	@SuppressWarnings("unchecked")
	public JSONObject FinishNodeToJSON(FinishNode finishNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_FINISH);
		jsonObject.put("contact-info", finishNode.getContactInfo());
		jsonObject.put("fieldname", finishNode.getFieldname());
		return jsonObject;
	}

	public FinishNode FinishExecNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_FINISH);
		Object contactInfo = (Object) node.get("contact-info");
		String fieldname = (String) node.get("fieldname");

		FinishNode n = new FinishNode((Map<String, String>) contactInfo, fieldname);
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject ServiceInstanceStorageNodeToJSON(ServiceInstanceStorageNode serviceInstanceStorageNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_SERVICE_INSTANCE_STORAGE);
		jsonObject.put("service-classpath", serviceInstanceStorageNode.getServiceClasspath());
		jsonObject.put("serviceinstance-fieldname", serviceInstanceStorageNode.getServiceInstanceFieldname());
		jsonObject.put("is-load-instruction", serviceInstanceStorageNode.isLoadInstruction());
		if(serviceInstanceStorageNode.isLoadInstruction()) {
			jsonObject.put("instance-id", serviceInstanceStorageNode.getId());
		} else {
			jsonObject.put("instance-id", "NO_ID_SPECIFIED!");
		}
		return jsonObject;
	}

	public ServiceInstanceStorageNode ServiceInstanceStorageNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_SERVICE_INSTANCE_STORAGE);
		String serviceClasspath = (String) node.get("service-classpath");
		String serviceinstanceFieldname = (String) node.get("serviceinstance-fieldname");
		Boolean isLoadInstruction = (Boolean) node.get("is-load-instruction");

		ServiceInstanceStorageNode n;
		if(isLoadInstruction) {
			String instanceId = (String) node.get("instance-id");
			n = new ServiceInstanceStorageNode(instanceId, serviceinstanceFieldname, serviceClasspath);
		} else {
			n = new ServiceInstanceStorageNode( serviceinstanceFieldname, serviceClasspath);
		}
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject CastTypeNodeToJSON(CastTypeNode castTypeNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_CAST_TYPE);

		jsonObject.put("fieldname", castTypeNode.getFieldname());
		jsonObject.put("caster-classpath", castTypeNode.getCasterClasspath());
		jsonObject.put("original-type", castTypeNode.getOriginType());
		jsonObject.put("target-type", castTypeNode.getTargetType());
		jsonObject.put("cast-to-semantic", castTypeNode.isCastToSemantic());

		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	public CastTypeNode CastTypeNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_CAST_TYPE);
		String fieldname = (String) node.get("fieldname");
		String casterClasspath = (String) node.get("caster-classpath");
		String originalType = (String) node.get("original-type");
		String targetType = (String) node.get("target-type");
		boolean castToSemantic = (Boolean) node.get("cast-to-semantic");
		CastTypeNode n = new CastTypeNode(fieldname, originalType, targetType, castToSemantic, casterClasspath);

		return n;
	}
}
