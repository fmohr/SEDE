package de.upb.sede.composition.graphs.serialization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.sede.composition.graphs.nodes.*;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import de.upb.sede.composition.FMCompositionParser;
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
		FileUtil.writeStringToFile(getJSONResourcePath("instNode1"), njs.toJSON(instNode1).toJSONString());
		JSONAssert.assertEquals(getJSONResource("instNode1"), njs.toJSON(instNode1).toJSONString(), true);

		InstructionNode instNode2 = new InstructionNode("a=some.service.Library::method1()", "some.service.Library",
				"method1");
		instNode2.setLeftSideFieldname("a");
		instNode2.setLeftSideFieldtype("Number");
		FileUtil.writeStringToFile(getJSONResourcePath("instNode2"), njs.toJSON(instNode2).toJSONString());
		JSONAssert.assertEquals(getJSONResource("instNode2"), njs.toJSON(instNode2).toJSONString(), true);

		InstructionNode instNode3 = new InstructionNode("localhost:10/some.service.Library::method1()",
				"some.service.Library", "method1");
		instNode3.setHost("localhost:10");
		FileUtil.writeStringToFile(getJSONResourcePath("instNode3"), njs.toJSON(instNode3).toJSONString());
		JSONAssert.assertEquals(getJSONResource("instNode3"), njs.toJSON(instNode3).toJSONString(), true);

		InstructionNode instNode4 = new InstructionNode("some.service.Library::method1({1,true,b})",
				"some.service.Library", "method1");
		List<String> params = Arrays.asList("1", "true", "b");
		instNode4.setParameterFields(params);
		List<String> param_types = Arrays.asList("Number", "Bool", "List");
		instNode4.setParameterType(param_types);
		FileUtil.writeStringToFile(getJSONResourcePath("instNode4"), njs.toJSON(instNode4).toJSONString());
		JSONAssert.assertEquals(getJSONResource("instNode4"), njs.toJSON(instNode4).toJSONString(), true);

		InstructionNode instNode5 = new InstructionNode("some.service.Library::__construct()", "some.service.Library",
				"__construct");
		FileUtil.writeStringToFile(getJSONResourcePath("instNode5"), njs.toJSON(instNode5).toJSONString());
		JSONAssert.assertEquals(getJSONResource("instNode5"), njs.toJSON(instNode5).toJSONString(), true);

		InstructionNode instNode6 = new InstructionNode("someFieldname::method1()", "someFieldname", "method1");
		instNode6.setContextIsField(true);
		FileUtil.writeStringToFile(getJSONResourcePath("instNode6"), njs.toJSON(instNode6).toJSONString());
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



		String path = getJSONResourcePath("constantNodeBool");
		FileUtil.writeStringToFile(path, njs.toJSON(constantNodeBool).toJSONString());
		path = getJSONResourcePath("constantNodeString");
		FileUtil.writeStringToFile(path, njs.toJSON(constantNodeString).toJSONString());
		path = getJSONResourcePath("constantNodeNumber");
		FileUtil.writeStringToFile(path, njs.toJSON(constantNodeNumber).toJSONString());
		path = getJSONResourcePath("constantNodeNull");
		FileUtil.writeStringToFile(path, njs.toJSON(constantNodeNull).toJSONString());


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
	public void testTransmitNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		Map<String, String> contactInfo = basicContactInfo("id0", "10.10.10.10:100");
		TransmitDataNode sendDataNode = new TransmitDataNode("a", contactInfo, "caster1", "semtype1");

		String path = getJSONResourcePath("transmitDataNode");
		FileUtil.writeStringToFile(path, njs.toJSON(sendDataNode).toJSONString());
		JSONAssert.assertEquals(getJSONResource("transmitDataNode"), njs.toJSON(sendDataNode).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		TransmitDataNode sendDataNode_ = (TransmitDataNode) njs.fromJSON(getJSONResourceAsObject("transmitDataNode"));
		assertEquals(sendDataNode, sendDataNode_);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSendGraphNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		Map<String, String> contactInfo = basicContactInfo("id0", "10.10.10.10:100");
		SendGraphNode sendGraphNode = new SendGraphNode("<graph serialization>", contactInfo);
		String path = getJSONResourcePath("sendGraphNode");
		FileUtil.writeStringToFile(path, njs.toJSON(sendGraphNode).toJSONString());
		JSONAssert.assertEquals(getJSONResource("sendGraphNode"), njs.toJSON(sendGraphNode).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		SendGraphNode sendGraphNode_ = (SendGraphNode) njs.fromJSON(getJSONResourceAsObject("sendGraphNode"));
		assertEquals(sendGraphNode, sendGraphNode_);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAcceptNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		AcceptDataNode receiveDataNode = new AcceptDataNode("a");
		String path = getJSONResourcePath("acceptDataNode");
		FileUtil.writeStringToFile(path, njs.toJSON(receiveDataNode).toJSONString());
		JSONAssert.assertEquals(getJSONResource("acceptDataNode"), njs.toJSON(receiveDataNode).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		AcceptDataNode receiveDataNode_ = (AcceptDataNode) njs.fromJSON(getJSONResourceAsObject("acceptDataNode"));
		assertEquals(receiveDataNode, receiveDataNode_);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCastTypeNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		CastTypeNode castType = new CastTypeNode("a", "some.Lib", "semanticType1", true, "casters.Caster1");
		FileUtil.writeStringToFile(getJSONResourcePath("castTypeNode"), njs.toJSON(castType).toJSONString());
		JSONAssert.assertEquals(getJSONResource("castTypeNode"), njs.toJSON(castType).toJSONString(), true);

		/*
		 * Test deserialization
		 */
		CastTypeNode castTypeNode_ = (CastTypeNode) njs.fromJSON(getJSONResourceAsObject("castTypeNode"));
		Assert.assertEquals(castType.getCasterClasspath(), castTypeNode_.getCasterClasspath());
		Assert.assertEquals(castType.getFieldname(), castTypeNode_.getFieldname());
		Assert.assertEquals(castType.getOriginType(), castTypeNode_.getOriginType());
		Assert.assertEquals(castType.getTargetType(), castTypeNode_.getTargetType());
		Assert.assertEquals(castType.isCastToSemantic(), castTypeNode_.isCastToSemantic());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testServiceInstanceStorageNodeSerializations() throws JSONException {
		NodeJsonSerializer njs = new NodeJsonSerializer();
		/*
		 * Test serialization
		 */
		ServiceInstanceStorageNode serviceInstanceStorageNode = new ServiceInstanceStorageNode("id_123", "a",
				"serviceClasspath.someLib.SomeServiceClass");
		FileUtil.writeStringToFile(getJSONResourcePath("serviceInstanceStorageNode"), njs.toJSON(serviceInstanceStorageNode).toJSONString());
		JSONAssert.assertEquals(getJSONResource("serviceInstanceStorageNode"),
				njs.toJSON(serviceInstanceStorageNode).toJSONString(), true);

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

	public void assertEquals(InstructionNode expected, InstructionNode actual) {
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

	public void assertEquals(ParseConstantNode expected, ParseConstantNode actual) {
		Assert.assertEquals(expected.getConstant(), actual.getConstant());
		Assert.assertEquals(expected.getType(), actual.getType());
	}

	public void assertEquals(AcceptDataNode expected, AcceptDataNode actual) {
		Assert.assertEquals(expected.getReceivingFieldname(), actual.getReceivingFieldname());
	}

	public void assertEquals(TransmitDataNode expected, TransmitDataNode actual) {
		Assert.assertEquals(expected.getSendingFieldName(), actual.getSendingFieldName());
		Assert.assertEquals(expected.getContactInfo(), actual.getContactInfo());
		Assert.assertEquals(expected.getCaster(), actual.getCaster());
		Assert.assertEquals(expected.getSemanticTypename(), actual.getSemanticTypename());
	}


	private void assertEquals(SendGraphNode expected, SendGraphNode actual) {
		Assert.assertEquals(expected.getGraph(), actual.getGraph());
		Assert.assertEquals(expected.getContactInfo(), actual.getContactInfo());
	}


	public void assertEquals(ServiceInstanceStorageNode expected, ServiceInstanceStorageNode actual) {
		Assert.assertEquals(expected.getServiceClasspath(), actual.getServiceClasspath());
		Assert.assertEquals(expected.getServiceInstanceFieldname(), actual.getServiceInstanceFieldname());
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

	private Map<String, String> basicContactInfo (String id, String host){
		Map<String, String> contactInfo = new HashMap<>();
		contactInfo.put("id", id);
		contactInfo.put("host-address", host);
		return contactInfo;
	}
}
