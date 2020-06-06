package de.upb.sede.composition.graphs.serialization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.AcceptDataNode;
import de.upb.sede.composition.graphs.nodes.TransmitDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.util.FileUtil;

public class GraphSerializationTest {

	private static final String jsonTestResourceFolder = "testrsc/graph-json-serializations/";

	@Test
	public void deserializeGraphTest() {
//		AcceptDataNode receiveA = new AcceptDataNode("a");
//		AcceptDataNode receiveB = new AcceptDataNode("b");
//		ParseConstantNode parseConstantNode = new ParseConstantNode("100");
//		InstructionNode instNodeCon = new InstructionNode("c=de.upb.SEDE.testlib.A::__construct({100})",
//				"de.upb.SEDE.testlib.A", "__construct");
//		instNodeCon.setLeftSideFieldname("c");
//		instNodeCon.setParameterFields(Arrays.asList("100"));
//		InstructionNode instNodeAdd = new InstructionNode("d=c::add({a,b})", "a", "b");
//		instNodeAdd.setLeftSideFieldname("d");
//		instNodeCon.setParameterFields(Arrays.asList("a", "b"));
//		TransmitDataNode sendToClient = new TransmitDataNode("d", basicContactInfo("id0", "some.host:1000"), "caster1" , "semtype1");
//		ServiceInstanceStorageNode saveSI = new ServiceInstanceStorageNode("id123", "c", "de.upb.SEDE.testlib.A");
//
//		CompositionGraph graph1 = new CompositionGraph();
//		graph1.addNode(receiveA);
//		graph1.addNode(receiveB);
//		graph1.addNode(parseConstantNode);
//		graph1.addNode(instNodeCon);
//		graph1.addNode(instNodeAdd);
//		graph1.addNode(sendToClient);
//		graph1.addNode(saveSI);
//
//		graph1.connectNodes(parseConstantNode, instNodeCon);
//
//		graph1.connectNodes(receiveA, instNodeAdd);
//		graph1.connectNodes(receiveB, instNodeAdd);
//		graph1.connectNodes(instNodeCon, instNodeAdd);
//
//		graph1.connectNodes(instNodeAdd, sendToClient);
//		graph1.connectNodes(instNodeAdd, saveSI);
//
//		GraphJsonSerializer graphJsonSerializer = new GraphJsonSerializer();
//
//		FileUtil.writeStringToFile(getJSONResourcePath("graph1"), graphJsonSerializer.toJson(graph1).toJSONString());
//		// JSONAssert.assertEquals(getJSONResource("graph1"),
//		// graphJsonSerializer.toJson(graph1).toJSONString(), false); // wont work
//		// because the order of the nodes change.
//
//		CompositionGraph graph1_ = graphJsonSerializer.fromJson(getJSONResourceAsObject("graph1"));
//
//		// TODO assert that graphs are the same.

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

	private Map<String, Object> basicContactInfo (String id, String host){
		Map<String, Object> contactInfo = new HashMap<>();
		contactInfo.put("id", id);
		contactInfo.put("host-address", host);
		return contactInfo;
	}

}
