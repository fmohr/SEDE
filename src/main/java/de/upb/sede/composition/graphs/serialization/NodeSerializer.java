package de.upb.sede.composition.graphs.serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.SendDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;

public class NodeSerializer {


    public static final String NODETYPE = "nodetype";
    public static final String NODETYPE_INSTRUCTION = "Instruction";
    public static final String NODETYPE_RECEIVE_DATA = "ReceiveData";
    public static final String NODETYPE_PARSE_CONSTANT = "ParseConstant";
    public static final String NODETYPE_SEND_DATA = "SendData";
    public static final String NODETYPE_SERVICE_INSTANCE_STORAGE = "ServiceInstanceStorage";
    

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	ParseConstantNode ParseConstantFromJson(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String constant = (String) node.get("constant");
		ParseConstantNode n = new ParseConstantNode(constant);
		return n;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	ReceiveDataNode ReceiveDataFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String fieldname = (String) node.get("fieldname");
		ReceiveDataNode n = new ReceiveDataNode(fieldname);
		return n;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	SendDataNode SendDataFromJSON(Map<Object, Object> node) {
		assert node.get(NODETYPE).equals(NODETYPE_INSTRUCTION);
		String targetaddress = (String) node.get("targetaddress");
		String fieldname = (String) node.get("fieldname");
		SendDataNode n = new SendDataNode(fieldname, targetaddress);
		return n;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	ServiceInstanceStorageNode ServiceInstanceStorageFromJSON(Map<Object, Object> node) {
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
