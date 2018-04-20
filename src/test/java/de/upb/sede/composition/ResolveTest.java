package de.upb.sede.composition;

import org.junit.Test;

import de.upb.sede.composition.gc.ClientInfo;
import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.ExecutorHandle;
import de.upb.sede.composition.gc.GraphConstruction;
import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;

public class ResolveTest {

	@Test
	public void testResolve() {
		ClassesConfig classConfig = new ClassesConfig();
		classConfig.put("some.Lib", new Object());
		InputFields inputFields = new InputFields();
		ClientInfo clientInfo = new ClientInfo("1.1.1.1:100");
		ExecutorCoordinator coordinator = new ExecutorCoordinator();
		ExecutorHandle execHandle = new ExecutorHandle("2.2.2.2:200", "java");
		execHandle.getExecutionerCapabilities().addAllServiceClasses("some.Lib");
		coordinator.addExecutor(execHandle);
		ResolvePolicy policy = new ResolvePolicy();
		
		
		ResolveInfo resolveInfo = new ResolveInfo();
		resolveInfo.setClassesConfiguration(classConfig);;
		resolveInfo.setInputFields(inputFields);
		resolveInfo.setClientInfo(clientInfo);
		resolveInfo.setExecutorCoordinator(coordinator);
		resolveInfo.setResolvePolicy(policy);
		
		String fmComposition = "a=some.Lib::__construct()";
		
		CompositionGraph graphComposition = GraphConstruction.resolveClientGraph(fmComposition, resolveInfo);
		System.out.println("Done");
	}
}
