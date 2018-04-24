package de.upb.sede.composition.gc;

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

public class ResolveTest {

	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/test-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/test-typeconf.json");
	}

	private static ExecutorCoordinator getTestExecCoordinator1() {
		ExecutorCoordinator coordinator = new ExecutorCoordinator();
		ExecutorHandle execHandle = new ExecutorHandle("2.2.2.2:200", "java");
		execHandle.getExecutionerCapabilities().addAllServiceClasses("testlib.A", "testlib.B");
		coordinator.addExecutor(execHandle);
		return coordinator;
	}

	private static ClientInfo getTestClientInfo() {
		ClientInfo clientInfo = new ClientInfo("1.1.1.1:100");
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
		InputFields inputFields = new InputFields(inputFieldTypes, new HashMap<>());

		String fmComposition = "";
		fmComposition += "a = testlib.A::__construct();";
		fmComposition += "d = a::m({b,c});";

		resolveInfo.setResolvePolicy(policy);
		resolveInfo.setInputFields(inputFields);

		GraphConstruction graphComposition = GraphConstruction.resolveClientGraph(fmComposition, resolveInfo);

		for (GraphConstruction.Execution execution : graphComposition.getExecutions()) {

			execution.getGraph();
		}

		System.out.println("Done");
	}
}
