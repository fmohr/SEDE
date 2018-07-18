package demo;

import de.upb.sede.client.CoreClient;
import de.upb.sede.client.CoreClientHttpServer;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.exec.ServiceInstance;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.BuiltinCaster;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import demo.types.DemoCaster;
import demo.types.NummerList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class IntegrationTest_PyExecutor {

	private final static String pyExecutorId = "PY_EXECUTOR";
	private final static String javaExecutorId = "JAVA_EXECUTOR";

	private static Gateway gateway;

	private static CoreClient client;

	private static final GraphJsonSerializer GJS = new GraphJsonSerializer();

	private static final Logger logger = LogManager.getLogger();

	private static final String[] classconfFiles = {
			"testrsc/config/plainlib-classconf.json",
			"testrsc/config/demo-classconf.json",
			"testrsc/config/builtin-classconf.json"};
	private static final String[] typecongFiles  = {
			"testrsc/config/plainlib-typeconf.json",
			"testrsc/config/demo-typeconf.json",
			"testrsc/config/builtin-typeconf.json"};
	@BeforeClass
	public static void setupGateway() {
		System.out.println(System.getProperty("user.dir"));
		gateway = new Gateway(new ClassesConfig(), new OnthologicalTypeConfig());
		gateway.getClassesConfig().appendConfigFromFiles(classconfFiles);
		gateway.getTypeConfig().appendConfigFromFiles(typecongFiles);

		String exec1Config = ExecutorConfigurationCreator.newConfigFile().withExecutorId(javaExecutorId)
				.withSupportedServices("demo.math.Addierer", "demo.math.Gerade").toString();
		ExecutorConfiguration config = ExecutorConfiguration.parseJSON(exec1Config);
		ExecutorHttpServer clientExec = new ExecutorHttpServer(config, "localhost", 9001);
		client = new CoreClient(clientExec, gateway::resolve);


		// register the python executor
		String pythonExecutorConfig = ExecutorConfigurationCreator.newConfigFile().withExecutorId(pyExecutorId)
				.withCapabilities("python").withSupportedServices("plainlib.package1.b.B").toString();
		ExecutorConfiguration pythonExecConfig = ExecutorConfiguration.parseJSON(pythonExecutorConfig);
		Map<String, String> pythonExecutorContactInfo = new HashMap<>();
		pythonExecutorContactInfo.put("id", pyExecutorId);
		pythonExecutorContactInfo.put("host-address", "localhost:5000");

		ExecutorRegistration pythonExecutorRegistration = new ExecutorRegistration(pythonExecutorContactInfo,
				pythonExecConfig.getExecutorCapabilities(),
				pythonExecConfig.getSupportedServices());

		gateway.register(pythonExecutorRegistration);
	}

	/**
	 * This test assumes that there is a python executor accessible through localhost:5000 <br>
	 * Tests the python executor' ability to run single-target execution graphs. <br>
	 * In other words it works in isolation...
	 * and there is no other executor cooperating on the execution except the client executor. <br>
	 */
	@Test
	public void test_scenarios_singletarget(){
		client.setDotGraphConsumer((executionId, svgString) -> {
			String pathToDotGraph = "testrsc/exec-requests/scenario_st/" + executionId + ".resolution.svg";
			FileUtil.writeStringToFile(pathToDotGraph, svgString);
		});

		/* test basic service instance construction and method invocation */
		int execNr = 1;
		String composition = "b = plainlib.package1.b.B::__construct({0,1});" +
				"erg = b::calc({3})";
		Map<String, SEDEObject> inputs = new HashMap<>();
		RunRequest rr = createRR(execNr, composition, inputs);

		Map<String, Result> resultMap = client.blockingRun(rr);
		Number erg = (Number) resultMap.get("erg").getResultData().getObject();
		ServiceInstanceHandle b = resultMap.get("b").getServiceInstanceHandle();

		Assert.assertEquals(erg.intValue(), 3);
		Assert.assertEquals(pyExecutorId, b.getExecutorId());
		Assert.assertEquals("plainlib.package1.b.B", b.getClasspath());

		/* test if the service instance can be loaded and if its state is changable
		 * Also test apply in space invocation. */
		execNr = 2;
		composition = "state_before = b::__str__();" +
				"b::set_b({2});" +
				"state_after = b::__str__();" +
				"n2 = b::calc_append_inplace({n1, 2});";

		inputs.clear();
		inputs.put("b", new SEDEObject(b));
		inputs.put("n1", new SEDEObject(new NummerList(1,2,3)));
		rr = createRR(execNr, composition, inputs);

		resultMap = client.blockingRun(rr);
		String state_before = (String) resultMap.get("state_before").getResultData().getObject();
		String state_after = (String) resultMap.get("state_after").getResultData().getObject();
		NummerList n2 = (NummerList) resultMap.get("n2").castResultData("NummerList", DemoCaster.class).getObject();
		
		assertThat(state_after, not(equalTo(state_before)));
		Assert.assertEquals("a = 0 b = 1", state_before);
		Assert.assertEquals("a = 0 b = 2", state_after);
		Assert.assertEquals(Arrays.asList(1.,2.,3.,4.), n2);



		/* Test static method invocations. First one creates a service instance by a static factory method.
		 * The second one is a simple static method invocation. */
		execNr = 3;
		composition = "b2 = plainlib.package1.b.B::random();" +
				"textUpper = plainlib.package1.b.B::upper({\"abcd\"});";
		inputs.clear();
		rr = createRR(execNr, composition, inputs);;
		resultMap = client.blockingRun(rr);
		ServiceInstanceHandle b2 = resultMap.get("b2").getServiceInstanceHandle();
		String textUpper = (String) resultMap.get("textUpper").getResultData().getObject();
		Assert.assertEquals("ABCD", textUpper);

		Assert.assertEquals(pyExecutorId, b2.getExecutorId());
		Assert.assertEquals("plainlib.package1.b.B", b2.getClasspath());

	}


	/**
	 * This test assumes that there is a python executor running accessible through localhost:5000 <br>
	 * Tests the python executor's ability to cooperate with a java executor.
	 * Specifically it tests if they can parse the data they send each other. <br>
	 *
	 * To simplify the test the client executor acts as the other java-executor, as...
	 * for the purposes of this test, this is equal to having third java executor working.
	 */
	@Test
	public void test_scenarios_cooperation(){
		client.setDotGraphConsumer((executionId, svgString) -> {
			String pathToDotGraph = "testrsc/exec-requests/scenario_coop/" + executionId + ".resolution.svg";
			FileUtil.writeStringToFile(pathToDotGraph, svgString);
		});

		int execNr = 1;
		String composition = "b = plainlib.package1.b.B::__construct({1,2});" +
				"g = demo.math.Gerade::__construct({1,2});";
		Map<String, SEDEObject> inputs = new HashMap<>();
		RunRequest rr = createRR(execNr, composition, inputs);

		Map<String, Result> resultMap = client.blockingRun(rr);
		ServiceInstanceHandle b = resultMap.get("b").getServiceInstanceHandle();
		ServiceInstanceHandle g = resultMap.get("g").getServiceInstanceHandle();
		Assert.assertEquals(pyExecutorId ,b.getExecutorId());
		Assert.assertEquals(javaExecutorId ,g.getExecutorId());

		execNr = 2;
		composition = "n3 = b::calc_append({n1, 3});" +
				"bool1 = g::liagtAufGerade({n3});" +
				"n4 = demo.math.Addierer::summierListe({n3, n2});" +
				"n5 = b::calc_append({n4, 5});";
		inputs.clear();
		inputs.put("n1", new SEDEObject(new NummerList(3)));
		inputs.put("n2", new SEDEObject(new NummerList(3, 7)));
		inputs.put("b", new SEDEObject(b));
		inputs.put("g", new SEDEObject(g));
		rr = createRR(execNr, composition, inputs);
		resultMap = client.blockingRun(rr);
		NummerList n3 = (NummerList) resultMap.get("n3")
				.castResultData("NummerList", DemoCaster.class)
				.getObject();
		NummerList n4 = (NummerList) resultMap.get("n4")
				.castResultData("NummerList", DemoCaster.class)
				.getObject();
		NummerList n5 = (NummerList) resultMap.get("n5")
				.castResultData("NummerList", DemoCaster.class)
				.getObject();
		Boolean bool1 = (Boolean) resultMap.get("bool1").getResultData().getObject();


		Assert.assertTrue(bool1);
		Assert.assertEquals(Arrays.asList(3., 7.), n3);
		Assert.assertEquals(Arrays.asList(6., 14.), n4);
		Assert.assertEquals(Arrays.asList(6., 14., 11.), n5);

	}

	@Test
	public void test_builtins(){
		client.setDotGraphConsumer((executionId, svgString) -> {
			String pathToDotGraph = "testrsc/exec-requests/builtins/" + executionId + ".resolution.svg";
			FileUtil.writeStringToFile(pathToDotGraph, svgString);
		});
		int execNr = 1;
		String composition =
				"l2 = demo.math.Addierer::addierBuiltIn({l1,2});";
		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("l1", new SEDEObject("builtin.List", Arrays.asList(0,1,2)));
		RunRequest rr = createRR(execNr, composition, inputs);

		Map<String, Result> resultMap = client.blockingRun(rr);
		List<Double> l2 = (List<Double>) resultMap.get("l2").
				castResultData("builtin.List", BuiltinCaster.class).getObject();
		Assert.assertEquals(Arrays.asList(2.,3.,4.) ,l2);

	}

	private static RunRequest createRR(int execNr, String composition, Map<String, SEDEObject> inputs) {
		return  new RunRequest("execution_" + execNr, composition, new ResolvePolicy(), inputs);

	}



}
