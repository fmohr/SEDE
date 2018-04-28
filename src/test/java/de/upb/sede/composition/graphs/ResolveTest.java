package de.upb.sede.composition.graphs;

import java.util.HashMap;

import org.junit.Test;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.gateway.ClientInfo;
import de.upb.sede.gateway.ExecutorCoordinator;
import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.gateway.ResolveInfo;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.GraphToDotSerializer;

public class ResolveTest {

	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/demo-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/demo-typeconf.json");
	}

	private static ExecutorCoordinator getTestExecCoordinator1() {
		ExecutorCoordinator coordinator = new ExecutorCoordinator();
		
		ExecutorHandle execHandle1 = new ExecutorHandle("executor_1", "java");
		execHandle1.getExecutionerCapabilities().addAllServiceClasses("demo.math.Addierer");
		ExecutorHandle execHandle2 = new ExecutorHandle("executor_2", "java");
		execHandle2.getExecutionerCapabilities().addAllServiceClasses("demo.math.Gerade");
		
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
		inputFieldTypes.put("b", "Array");
		inputFieldTypes.put("c", "Array");
		inputFieldTypes.put("e",  "ServiceInstanceHandle");
		ServiceInstanceHandle serviceInstanceHandle = new ServiceInstanceHandle("executor_2", "demo.math.Gerade", "id0");
		
		HashMap<String, ServiceInstanceHandle> serviceHandleFields = new HashMap<>();
		serviceHandleFields.put("e", serviceInstanceHandle);
		
		InputFields inputFields = new InputFields(inputFieldTypes, serviceHandleFields);

//		String fmComposition = "";
//		fmComposition += "a = testlib.A::__construct();";
//		fmComposition += "d = a::m({i1=b,i2=c});";
//		fmComposition += "f = e::m({i1=d});";
//		fmComposition += "d = e::m({i1=c});";
		
		String fmComposition = "";
		fmComposition += "a = demo.math.Addierer::__construct({100});";
		fmComposition += "d = a::addier({20});";
		fmComposition += "f = a::addierListe({b});";
		fmComposition += "d = e::liagtAufGerade({f});";

		resolveInfo.setResolvePolicy(policy);
		resolveInfo.setInputFields(inputFields);

		GraphConstruction graphComposition = GraphConstruction.constructFromFMComp(fmComposition, resolveInfo);

		for (Execution execution : graphComposition.getExecutions()) {
			String svgGraph = GraphToDotSerializer.getSVGForGraph(execution.getGraph());
			String file = "testout/resolved-graphs/" + execution.getExecutor().getHostAddress() + ".svg";
			FileUtil.writeStringToFile(file,
					svgGraph);
		}

	}
}