package de.upb.sede.composition.gc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.Test;

import de.upb.sede.composition.gc.ClientInfo;
import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.ExecutorHandle;
import de.upb.sede.composition.gc.GraphConstruction;
import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.GraphToDotSerializer;

public class ResolveTest {

	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/test-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/test-typeconf.json");
	}

	private static ExecutorCoordinator getTestExecCoordinator1() {
		ExecutorCoordinator coordinator = new ExecutorCoordinator();
		
		ExecutorHandle execHandle1 = new ExecutorHandle("executor_1", "java");
		execHandle1.getExecutionerCapabilities().addAllServiceClasses("testlib.A");
		ExecutorHandle execHandle2 = new ExecutorHandle("executor_2", "java");
		execHandle2.getExecutionerCapabilities().addAllServiceClasses("testlib.B");
		
		coordinator.addExecutor(execHandle1);
		coordinator.addExecutor(execHandle2);
		
		return coordinator;
	}

	private static ClientInfo getTestClientInfo() {
		ClientInfo clientInfo = new ClientInfo("clienthost");
		return clientInfo;
	}

	@Test
	public void testResolve1() {
		ResolveInfo resolveInfo = new ResolveInfo();
		resolveInfo.setClassesConfiguration(getTestClassConfig());
		resolveInfo.setTypeConfig(getTestTypeConfig());
		resolveInfo.setClientInfo(getTestClientInfo());

		resolveInfo.setExecutorCoordinator(getTestExecCoordinator1());

		ResolvePolicy policy = new ResolvePolicy();
		HashMap<String, String> inputFieldTypes = new HashMap<>();
		inputFieldTypes.put("b", "semType1");
		inputFieldTypes.put("c", "semType2");
		inputFieldTypes.put("e",  "ServiceInstanceHandle");
		ServiceInstanceHandle serviceInstanceHandle = new ServiceInstanceHandle("executor_2", "testlib.B", "id0");
		
		HashMap<String, ServiceInstanceHandle> serviceHandleFields = new HashMap<>();
		serviceHandleFields.put("e", serviceInstanceHandle);
		
		InputFields inputFields = new InputFields(inputFieldTypes, serviceHandleFields);

		String fmComposition = "";
		fmComposition += "a = testlib.A::__construct();";
		fmComposition += "d = a::m({i1=b,i2=c});";
		fmComposition += "f = e::m({i1=d});";
		

		resolveInfo.setResolvePolicy(policy);
		resolveInfo.setInputFields(inputFields);

		GraphConstruction graphComposition = GraphConstruction.resolveClientGraph(fmComposition, resolveInfo);

		for (GraphConstruction.Execution execution : graphComposition.getExecutions()) {

			String svgGraph = GraphToDotSerializer.getSVGForGraph(execution.getGraph());
			String file = "testrsc/dot-files/" + execution.getExecutor().getHostAddress() + ".svg";
			FileUtil.writeStringToFile(file,
					svgGraph);
		}

	}
	
	public static void dotToSvg(String dotPath) {
		Process p;
		try {
			String svgpath = dotPath.substring(0, dotPath.length()-3) + "svg";
			String commmand = "/bin/bash -c dot -Tsvg " + dotPath + " -o " + svgpath;
			p = Runtime.getRuntime().exec(commmand);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

			StringBuffer output = new StringBuffer();
            String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			if(!output.toString().isEmpty()) {
				System.out.println(output);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}
