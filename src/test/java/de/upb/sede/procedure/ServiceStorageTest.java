package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ServiceInstance;
import de.upb.sede.exec.Task;
import demo.math.Gerade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests the service instance storage node procedure.
 * @author aminfaez
 */
public class ServiceStorageTest {
	static Execution dummyExec;

	@Before public void clearExecution() {
		dummyExec = new Execution("", null);
	}

	/**
	 * Positive Tests of service instance storage procedure using <tt>demo.math.Gerade</tt> as service instances.
	 */
	@Test public void testGeradeInstances() {
		Gerade gerade1 = new Gerade(10,10);
		ServiceInstance serviceInstance = new ServiceInstance("", "demo.math.Gerade", "0", gerade1);
		SEDEObject sedeObject = new SEDEObject(serviceInstance);
		dummyExec.getExecutionEnvironment().put("gerade1", sedeObject);


		runTask(false, "0", "demo.math.Gerade", "gerade1");

		runTask(true, "0", "demo.math.Gerade", "gerade2");

		Assert.assertTrue(dummyExec.getExecutionEnvironment().containsKey("gerade2"));

		Gerade gerade2 = dummyExec.getExecutionEnvironment().get("gerade2").getServiceInstance();
		Assert.assertEquals(gerade1, gerade2);


	}

	static void runTask(boolean isLoad, String id, String cp, String fieldname) {
		Task task = createTask(isLoad,id,cp,fieldname);
		ServiceInstanceStorage procedure = new ServiceInstanceStorage();
		procedure.process(task);
	}

	static Task createTask(boolean isLoad, String id, String cp, String fieldname) {
		Map<String, Object> taskParams = createTaskParams(isLoad,id,cp,fieldname);
		Task task = new Task(dummyExec, "ServiceInstanceStorage", taskParams);
		return task;
	}

	static Map<String, Object> createTaskParams(boolean isLoad, String id, String cp, String fieldname) {
		/* example of a serivce instance storage node:

		"nodetype": "ServiceInstanceStorage",
		"is-load-instruction": true,
		"instance-id": "id_123",
		"service-classpath": "serviceClasspath.someLib.SomeServiceClass",
		"serviceinstance-fieldname": "a"

		 */
		Map<String, Object> params = new HashMap<>();
		params.put("nodetype", "ServiceInstanceStorage");
		params.put("is-load-instruction", isLoad);
		params.put("instance-id", id);
		params.put("service-classpath", cp);
		params.put("serviceinstance-fieldname", fieldname);
		return params;
	}
}
