package de.upb.sede.composition.graphs.serialization;

import java.util.Arrays;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.SendDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.util.FileUtil;

public class GraphSerializationTest {
	

	private static final String jsonTestResourceFolder = "testrsc/graph-json-serializations/";
	
	@Test public void deserializeGraphTest() throws JSONException {
		ReceiveDataNode receiveA = new ReceiveDataNode("a");
		ReceiveDataNode receiveB = new ReceiveDataNode("b");
		ParseConstantNode parseConstantNode = new ParseConstantNode("100");
		InstructionNode instNodeCon = new InstructionNode("c=de.upb.SEDE.testlib.A::__construct({100})", "de.upb.SEDE.testlib.A", "__construct");
		instNodeCon.setLeftSideFieldname("c");
		instNodeCon.setParameterFields(Arrays.asList("100"));
		InstructionNode instNodeAdd = new InstructionNode("d=c::add({a,b})", "a", "b");
		instNodeAdd.setLeftSideFieldname("d");
		instNodeCon.setParameterFields(Arrays.asList("a", "b"));
		SendDataNode sendToClient = new SendDataNode("d", "clientdomain.com");
		ServiceInstanceStorageNode saveSI = new ServiceInstanceStorageNode("c", "de.upb.SEDE.testlib.A");
		
		
		CompositionGraph graph1 = new CompositionGraph();
		graph1.addNode(receiveA);
		graph1.addNode(receiveB);
		graph1.addNode(parseConstantNode);
		graph1.addNode(instNodeCon);
		graph1.addNode(instNodeAdd);
		graph1.addNode(sendToClient);
		graph1.addNode(saveSI);

		graph1.connectNodes(parseConstantNode, instNodeCon);
		
		graph1.connectNodes(receiveA, instNodeAdd);
		graph1.connectNodes(receiveB, instNodeAdd);
		graph1.connectNodes(instNodeCon, instNodeAdd);

		graph1.connectNodes(instNodeAdd, sendToClient);
		graph1.connectNodes(instNodeAdd, saveSI);
		
		GraphJsonSerializer graphJsonSerializer = new GraphJsonSerializer();
		
//		FileUtil.writeStringToFile(getJSONResourcePath("graph1"), graphJsonSerializer.toJson(graph1).toJSONString());
//		JSONAssert.assertEquals(getJSONResource("graph1"), graphJsonSerializer.toJson(graph1).toJSONString(), false); // wont work because the order of the nodes change.
		
		
		
		CompositionGraph graph1_ =  graphJsonSerializer.fromJson(getJSONResourceAsObject("graph1"));
		
		
		// TODO assert that graphs are the same.
		
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
