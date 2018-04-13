package de.upb.sede.composition.graphs.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.SendDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.exceptions.NodeSerializationException;

public final class NodeJsonSerializer {


    public static final String NODETYPE = "nodetype";
    public static final String NODETYPE_INSTRUCTION = "Instruction";
    public static final String NODETYPE_RECEIVE_DATA = "ReceiveData";
    public static final String NODETYPE_PARSE_CONSTANT = "ParseConstant";
    public static final String NODETYPE_SEND_DATA = "SendData";
    public static final String NODETYPE_SERVICE_INSTANCE_STORAGE = "ServiceInstanceStorage";
    


    public final BaseNode fromJSON(Map<Object, Object> jsonObject) {
    	if(!jsonObject.containsKey(NODETYPE)) {
    		throw new NodeSerializationException("The given json object doens't contain the " + NODETYPE + " field.");
    	}
    	/*
    	 * Get the right method name.
    	 * The deserialization method name is equal to the nodetype field plus 'NodeFromJson'
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
			throw new NodeSerializationException("No deserializer " + deserializeMethodName + " defined in NodeJsonSerializer.");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new RuntimeException(e); // cant access reflection api
		}
    	return deserializedNode;
    }

    public final JSONObject toJSON(BaseNode baseNode) {
    	/*
    	 * Get the right method name.
    	 * The serialization method name is equal to the simpleclassname field plus 'ToJson'
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
			throw new NodeSerializationException("No serializer " + serializeMethodName + " defined in NodeJsonSerializer.");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new RuntimeException(e); // cant access reflection api
		}
    	return serializedJsonObject;
    }
    
	@SuppressWarnings("unchecked")
	JSONObject InstructionNodeToJSON(InstructionNode instructionNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_INSTRUCTION);
		jsonObject.put("fmInstruction", instructionNode.getFmInstruction());
		jsonObject.put("leftsidefieldname", instructionNode.isAssignedLeftSideFieldname() ? instructionNode.getLeftSideFieldname() : null);
		jsonObject.put("host", instructionNode.isAssignedHost() ? instructionNode.getHost() : null);
		jsonObject.put("context", instructionNode.getContext());
		jsonObject.put("contextisfield", instructionNode.isContextAFieldname());
		jsonObject.put("method", instructionNode.getMethod());
		jsonObject.put("params", instructionNode.getParameterFields());
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
    JSONObject ReceiveNodeToJSON(ReceiveDataNode receiveNode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_RECEIVE_DATA);
		jsonObject.put("fieldname", receiveNode.getReceivingFieldname());
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
    JSONObject ParseConstantNodeToJSON(ParseConstantNode parseConstantNode) {
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_PARSE_CONSTANT);
		jsonObject.put("constant", parseConstantNode.getConstant());
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
    JSONObject SendDataNodeToJSON(SendDataNode sendDataNode) {
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_SEND_DATA);
		jsonObject.put("targetaddress", sendDataNode.getTargetAddress());
		jsonObject.put("fieldname", sendDataNode.getSendingFieldName());
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
    JSONObject ServiceInstanceStorageNodeToJSON(ServiceInstanceStorageNode serviceInstanceStorageNode) {
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put(NODETYPE, NODETYPE_SERVICE_INSTANCE_STORAGE);
		jsonObject.put("id", serviceInstanceStorageNode.getId());
		jsonObject.put("hasId", serviceInstanceStorageNode.hasId());
		jsonObject.put("serviceClasspath", serviceInstanceStorageNode.getServiceClasspath());
		jsonObject.put("serviceinstanceFieldname", serviceInstanceStorageNode.getServiceInstanceFieldname());
		jsonObject.put("isLoadInstruction", serviceInstanceStorageNode.isLoadInstruction());
		return jsonObject;
	}
    
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	InstructionNode InstructionNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String fmInstruction = (String) node.get("fmInstruction");
		String leftsidefieldname = (String) node.get("leftsidefieldname");
		String host = (String) node.get("host");
		String context = (String) node.get("context");
		boolean contextisfield = (Boolean) node.get("contextisfield");
		String method = (String) node.get("method");
		List params = (JSONArray) node.get("params");
		
		InstructionNode instructionNode = new InstructionNode(fmInstruction, context, method);
		instructionNode.setContextIsField(contextisfield);
		if(leftsidefieldname != null) {
			instructionNode.setLeftSideFieldname(leftsidefieldname);
		}
		if(host != null) {
			instructionNode.setHost(host);
		}
		if(params != null) {
			instructionNode.setParameterFields(params);
		}
		return instructionNode;
	}

	ParseConstantNode ParseConstantNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String constant = (String) node.get("constant");
		ParseConstantNode n = new ParseConstantNode(constant);
		return n;
	}
	
	ReceiveDataNode ReceiveDataNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String fieldname = (String) node.get("fieldname");
		ReceiveDataNode n = new ReceiveDataNode(fieldname);
		return n;
	}
	
	SendDataNode SendDataNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String targetaddress = (String) node.get("targetaddress");
		String fieldname = (String) node.get("fieldname");
		SendDataNode n = new SendDataNode(fieldname, targetaddress);
		return n;
	}
	
	ServiceInstanceStorageNode ServiceInstanceStorageNodeFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String id = (String) node.get("id");
		Boolean hasId = (Boolean) node.get("hasId");
		String serviceClasspath = (String) node.get("serviceClasspath");
		String serviceinstanceFieldname = (String) node.get("serviceinstanceFieldname");
		Boolean isLoadInstruction = (Boolean) node.get("isLoadInstruction");

		ServiceInstanceStorageNode n;
		if(hasId) {
			n = new ServiceInstanceStorageNode(isLoadInstruction, serviceinstanceFieldname, serviceClasspath, id);
		} else {
			n = new ServiceInstanceStorageNode(serviceinstanceFieldname, serviceClasspath);
		}
		return n;
	}
}
