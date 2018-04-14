package de.upb.sede.composition.graphs.serialization;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.SendDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.util.FileUtil;

/**
 * 
 * @author aminfaez
 *
 */
public class NodeSerializationTest {

	private static final String jsonTestResourceFolder = "testrsc/graph-json-serializations/";

	@SuppressWarnings("unchecked")
	@Test
	public void testInstructionNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();

		/*
		 * Test serialization
		 */
		InstructionNode instNode1 = new InstructionNode("some.service.Library::method1()", "some.service.Library",
				"method1");
		JSONAssert.assertEquals(getJSONResource("instNode1"), njs.toJSON(instNode1).toJSONString(), true);

		InstructionNode instNode2 = new InstructionNode("a=some.service.Library::method1()", "some.service.Library",
				"method1");
		instNode2.setLeftSideFieldname("a");
		JSONAssert.assertEquals(getJSONResource("instNode2"), njs.toJSON(instNode2).toJSONString(), true);

		InstructionNode instNode3 = new InstructionNode("localhost:10/some.service.Library::method1()",
				"some.service.Library", "method1");
		instNode3.setHost("localhost:10");
		JSONAssert.assertEquals(getJSONResource("instNode3"), njs.toJSON(instNode3).toJSONString(), true);

		InstructionNode instNode4 = new InstructionNode("some.service.Library::method1({1,true,b})",
				"some.service.Library", "method1");
		List<String> params = Arrays.asList("1", "true", "b");
		instNode4.setParameterFields(params);
		JSONAssert.assertEquals(getJSONResource("instNode4"), njs.toJSON(instNode4).toJSONString(), true);

		InstructionNode instNode5 = new InstructionNode("some.service.Library::__construct()", "some.service.Library",
				"__construct");
		JSONAssert.assertEquals(getJSONResource("instNode5"), njs.toJSON(instNode5).toJSONString(), true);

		InstructionNode instNode6 = new InstructionNode("someFieldname::method1()", "someFieldname", "method1");
		instNode6.setContextIsField(true);
		JSONAssert.assertEquals(getJSONResource("instNode6"), njs.toJSON(instNode6).toJSONString(), true);


		/*
		 * Test deserialization
		 */
		InstructionNode instNode1_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode1"));
		InstructionNode instNode2_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode2"));
		InstructionNode instNode3_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode3"));
		InstructionNode instNode4_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode4"));
		InstructionNode instNode5_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode5"));
		InstructionNode instNode6_ = (InstructionNode) njs.fromJSON(getJSONResourceAsObject("instNode6"));

		assertEquals(instNode1, instNode1_);
		assertEquals(instNode2, instNode2_);
		assertEquals(instNode3, instNode3_);
		assertEquals(instNode4, instNode4_);
		assertEquals(instNode5, instNode5_);
		assertEquals(instNode6, instNode6_);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testParseConstantNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();

		/*
		 * Test serialization
		 */
		ParseConstantNode constantNodeBool = new ParseConstantNode("True");
		ParseConstantNode constantNodeString = new ParseConstantNode("\"SomeText\"");
		ParseConstantNode constantNodeNumber = new ParseConstantNode("1e10");
		ParseConstantNode constantNodeNull = new ParseConstantNode("null");

		JSONAssert.assertEquals(getJSONResource("constantNodeBool"), njs.toJSON(constantNodeBool).toJSONString(), true);
		JSONAssert.assertEquals(getJSONResource("constantNodeString"), njs.toJSON(constantNodeString).toJSONString(),
				true);
		JSONAssert.assertEquals(getJSONResource("constantNodeNumber"), njs.toJSON(constantNodeNumber).toJSONString(),
				true);
		JSONAssert.assertEquals(getJSONResource("constantNodeNull"), njs.toJSON(constantNodeNull).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		ParseConstantNode constantNodeBool_ = (ParseConstantNode) njs
				.fromJSON(getJSONResourceAsObject("constantNodeBool"));
		ParseConstantNode constantNodeString_ = (ParseConstantNode) njs
				.fromJSON(getJSONResourceAsObject("constantNodeString"));
		ParseConstantNode constantNodeNumber_ = (ParseConstantNode) njs
				.fromJSON(getJSONResourceAsObject("constantNodeNumber"));
		ParseConstantNode constantNodeNull_ = (ParseConstantNode) njs
				.fromJSON(getJSONResourceAsObject("constantNodeNull"));
		

		assertEquals(constantNodeBool, constantNodeBool_);
		assertEquals(constantNodeString, constantNodeString_);
		assertEquals(constantNodeNumber, constantNodeNumber_);
		assertEquals(constantNodeNull, constantNodeNull_);
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void testSendDataNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		SendDataNode sendDataNode = new SendDataNode("a", "10.10.10.10:100");
		JSONAssert.assertEquals(getJSONResource("sendDataNode"), njs.toJSON(sendDataNode).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		SendDataNode sendDataNode_ = (SendDataNode) njs
				.fromJSON(getJSONResourceAsObject("sendDataNode"));
		assertEquals(sendDataNode, sendDataNode_);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReceiveDataNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		ReceiveDataNode receiveDataNode = new ReceiveDataNode("a");
		JSONAssert.assertEquals(getJSONResource("receiveDataNode"), njs.toJSON(receiveDataNode).toJSONString(), true);
		
		/*
		 * Test deserialization
		 */
		ReceiveDataNode receiveDataNode_ = (ReceiveDataNode) njs
				.fromJSON(getJSONResourceAsObject("receiveDataNode"));
		assertEquals(receiveDataNode, receiveDataNode_);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testServiceInstanceStorageNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		ServiceInstanceStorageNode serviceInstanceStorageNode = new ServiceInstanceStorageNode("a", "10.10.10.10:100");
		JSONAssert.assertEquals(getJSONResource("serviceInstanceStorageNode"), njs.toJSON(serviceInstanceStorageNode).toJSONString(), true);
		
		/*
		 * Test deserialization
		 */
		ServiceInstanceStorageNode serviceInstanceStorageNode_ = (ServiceInstanceStorageNode) njs
				.fromJSON(getJSONResourceAsObject("serviceInstanceStorageNode"));

		assertEquals(serviceInstanceStorageNode, serviceInstanceStorageNode_);
	}

	/*
	 * Helper functions:
	 */

	private void assertEquals(InstructionNode expected, InstructionNode actual) {
		Assert.assertEquals(expected.getContext(), actual.getContext());
		Assert.assertEquals(expected.getFmInstruction(), actual.getFmInstruction());
		Assert.assertEquals(expected.getMethod(), actual.getMethod());
		Assert.assertEquals(expected.getParameterFields(), actual.getParameterFields());

		Assert.assertEquals(expected.isAssignedLeftSideFieldname(), actual.isAssignedLeftSideFieldname());
		if (expected.isAssignedLeftSideFieldname()) {
			Assert.assertEquals(expected.getLeftSideFieldname(), actual.getLeftSideFieldname());

		}
		Assert.assertEquals(expected.isAssignedHost(), actual.isAssignedHost());
		if (expected.isAssignedHost()) {
			Assert.assertEquals(expected.getHost(), actual.getHost());
		}

		Assert.assertEquals(expected.isContextAFieldname(), actual.isContextAFieldname());
		Assert.assertEquals(expected.isServiceConstruct(), actual.isServiceConstruct());
	}

	private void assertEquals(ParseConstantNode expected, ParseConstantNode actual) {
		Assert.assertEquals(expected.getConstant(), actual.getConstant());
		Assert.assertEquals(expected.getType(), actual.getType());
	}
	
	private void assertEquals(ReceiveDataNode expected, ReceiveDataNode actual) {
		Assert.assertEquals(expected.getReceivingFieldname(), actual.getReceivingFieldname());
	}
	
	private void assertEquals(SendDataNode expected, SendDataNode actual) {
		Assert.assertEquals(expected.getSendingFieldName(), actual.getSendingFieldName());
		Assert.assertEquals(expected.getTargetAddress(), actual.getTargetAddress());
	}
	
	private void assertEquals(ServiceInstanceStorageNode expected, ServiceInstanceStorageNode actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getServiceClasspath(), actual.getServiceClasspath());
		Assert.assertEquals(expected.getServiceInstanceFieldname(), actual.getServiceInstanceFieldname());
		Assert.assertEquals(expected.hasId(), actual.hasId());
		Assert.assertEquals(expected.isLoadInstruction(), actual.isLoadInstruction());
	}

	private JSONObject getJSONResourceAsObject(String jsonFileName) {
		try {
			return (JSONObject) new JSONParser().parse(getJSONResource(jsonFileName));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	private String getJSONResourcePath(String jsonFileName) {
		return jsonTestResourceFolder + jsonFileName + ".json";
	}

	private String getJSONResource(String jsonFileName) {
		String path = getJSONResourcePath(jsonFileName);
		return FileUtil.readFileAsString(path);
	}

}
