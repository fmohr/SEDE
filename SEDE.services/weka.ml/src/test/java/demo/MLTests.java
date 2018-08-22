package demo;

import de.upb.sede.BuiltinCaster;
import de.upb.sede.client.CoreClient;
import de.upb.sede.client.HttpCoreClient;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import ml.data.LabeledInstancesCaster;
import de.upb.sede.services.mls.DataSetService;
import de.upb.sede.services.mls.util.MLDataSets;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

import javax.swing.*;
import java.util.*;

public class MLTests {
	private final static Logger logger = LogManager.getLogger();

	static CoreClient coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7000;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6000;

	static ExecutorHttpServer executor1;
	static ExecutorHttpServer executor2;

	static GatewayHttpServer gateway;

	static Instances dataset;
	static Instances weatherTrainSet;
	static Instances weatherTestSet;

	private static final String datasetRef = "cifar_0.arff";

	@BeforeClass
	public static void startClient() {
		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());

		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor-weka-bayesnet");
		creator.withSupportedServices(DataSetService.class.getName(), "weka.classifiers.bayes.BayesNet");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		executor1 = new ExecutorHttpServer(configuration, "localhost",  9000);
		gateway.register(executor1.getBasisExecutor().registration());

		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor-weka-naivebayes");
		creator.withSupportedServices(DataSetService.class.getName(), "weka.classifiers.bayes.NaiveBayes");
		configuration = ExecutorConfiguration.parseJSON(creator.toString());
		executor2 = new ExecutorHttpServer(configuration, "localhost",  9001);
		gateway.register(executor2.getBasisExecutor().registration());

		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = HttpCoreClient.createNew(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);
		/*
			Disable if you dont have dot installed.
		 */
		coreClient.writeDotGraphToDir("testrsc/ml");


	}

	/*
	 * Comment the annotation out if you dont have a python executor running on 'localhost:5000'.
	 */
	@BeforeClass
	public static void registerScikitExecutor() {
		// register the python executor
		String pyExecutorId = "PY-Scikit-Executor";
		String pythonExecutorConfig = ExecutorConfigurationCreator.newConfigFile()
				.withExecutorId(pyExecutorId)
				.withCapabilities("python")
				.withSupportedServices("sklearn.ensemble.RandomForestClassifier",
						"sklearn.gaussian_process.GaussianProcessClassifier",
						"sklearn.naive_bayes.GaussianNB")
				.withThreadNumberId(1).toString();
		ExecutorConfiguration pythonExecConfig = ExecutorConfiguration.parseJSON(pythonExecutorConfig);
		Map<String, Object> pythonExecutorContactInfo = new HashMap<>();
		pythonExecutorContactInfo.put("id", pyExecutorId);
		pythonExecutorContactInfo.put("host-address", "localhost:5000");

		ExecutorRegistration pythonExecutorRegistration = new ExecutorRegistration(pythonExecutorContactInfo,
				pythonExecConfig.getExecutorCapabilities(),
				pythonExecConfig.getSupportedServices());

		gateway.register(pythonExecutorRegistration);
	}

//	@BeforeClass
	public static void blockTest() throws Exception {
		int confirmation = 1;
		while(confirmation!= 0) {
			Object[] choices = {"Continue", "Refresh", "Cancel"};
			Object defaultChoice = choices[1];
			String registeredExecutors = "";
			for(ExecutorHandle executorHandle : gateway.getExecutorCoord().getExecutors()) {
				registeredExecutors += "\n" + executorHandle.getContactInfo().toString();
			}
			confirmation = JOptionPane.showOptionDialog(null,
					"Registered Exeuctors:" + registeredExecutors,
					"Registered executors",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					choices,
					defaultChoice);
			if(confirmation == 2) {
				System.exit(1);
			}
		}
	}

	@BeforeClass
	public static void loadDataSet() throws Exception {
		dataset = MLDataSets.getDataSetWithLastIndexClass(datasetRef);
		RemovePercentage splitter = new RemovePercentage();
		splitter.setInputFormat(dataset);
		splitter.setPercentage(50);

		weatherTrainSet = Filter.useFilter(dataset, splitter);

		splitter.setInputFormat(dataset);
		splitter.setInvertSelection(true);
		weatherTestSet = Filter.useFilter(dataset, splitter);
	}
	@AfterClass
	public static  void shutdownClient() {
		executor1.shutdown();
		executor2.shutdown();
		coreClient.getClientExecutor().shutdown();
		gateway.shutdown();
	}

	@Test
	public void testClassification1() {
		String composition =
				"s1 = weka.classifiers.bayes.BayesNet::__construct();\n" +
				"s1::train({trainset});\n" +
				"predictions = s1::predict({testset});\n";

		logger.info("Test classification with composition: \n" + composition);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setPersistentServices(Arrays.asList("s1"));
		policy.setReturnFieldnames(Arrays.asList("predictions"));

		SEDEObject trainset = getDataField( weatherTrainSet);
		SEDEObject testset = getDataField( weatherTestSet);

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("trainset", trainset);
		inputs.put("testset", testset);

		RunRequest runRequest = new RunRequest("classification1", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("predictions");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result missing...");
		}
		/*
			Cast it to List:
		 */
		 List prediction = (List) result.castResultData(
				"builtin.List", BuiltinCaster.class).getDataField();
		 double correctPredictions = 0.;
		for (int i = 0; i < prediction.size(); i++) {
			Instance testInstance = weatherTestSet.get(i);
			if(testInstance.classValue() == ((Number)prediction.get(i)).doubleValue()) {
				correctPredictions++;
			}
		}
		logger.info("{}/{} correct predictions.", (int) correctPredictions, prediction.size());
	}


	@Test
	/**
	 * This test assumes that a python executor is running on 'localhost:5000'.
	 * Comment the annotation out if you cant satisfy that.
	 */
	public void testClassification2() {
		String composition =
				"dataset = de.upb.sede.services.mls.DataSetService::__construct({\"" +datasetRef + "\"});" +
				"trainset = dataset::fromIndicesLabeled({indices_train, -1});" +
				"testset  = dataset::fromIndicesLabeled({indices_test, -1});" +
				"s1 = weka.classifiers.bayes.BayesNet::__construct();" +
				"s1::train({trainset});" +
				"predictions = s1::predict({testset});";

		logger.info("Test classification with composition: \n" + composition);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setPersistentServices(Arrays.asList("s1"));
		policy.setReturnPolicy(ResolvePolicy.all);

		List<List<Integer>> splits = MLDataSets.split(MLDataSets.intRange(0, dataset.size()),0.5);


		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("indices_train", new ObjectDataField("builtin.List", splits.get(0)));
		inputs.put("indices_test", new ObjectDataField("builtin.List", splits.get(1)));

		RunRequest runRequest = new RunRequest("classification2", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("predictions");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result missing...");
		}

		/*
			Cast it to List:
		 */
		List prediction = (List) result.castResultData(
				"builtin.List", BuiltinCaster.class).getDataField();
		Instances testDataFromExecutor = resultMap.get("testset").castResultData("LabeledInstances", LabeledInstancesCaster.class).getDataField();
		double correctPredictions = 0.;
		Instances weatherTestSet = new DataSetService(datasetRef).fromIndicesLabeled(splits.get(1), -1);
		Assert.assertEquals(weatherTestSet.toString().trim(), testDataFromExecutor.toString().trim());
		for (int i = 0; i < prediction.size(); i++) {
			Instance testInstance = weatherTestSet.get(i);
			if(testInstance.classValue() == ((Number)prediction.get(i)).doubleValue()) {
				correctPredictions++;
			}
		}
		logger.info("{}/{} correct predictions.", (int) correctPredictions, prediction.size());
	}


	@Test
	public void testClassificationScikit1() {
		String composition =
				"s1 = sklearn.naive_bayes.GaussianNB::__construct();\n" +
						"s1::train({trainset});\n" +
						"predictions = s1::predict({testset});\n";

		logger.info("Test classification with composition: \n" + composition);

		ResolvePolicy policy = new ResolvePolicy();
		policy.setPersistentServices(Arrays.asList("s1"));
		policy.setReturnFieldnames(Arrays.asList("predictions"));

		SEDEObject trainset = getDataField(weatherTrainSet);
		SEDEObject testset = getDataField(weatherTestSet);

		Map<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("trainset", trainset);
		inputs.put("testset", testset);

		RunRequest runRequest = new RunRequest("scikit-classification", composition, policy, inputs);

		Map<String, Result> resultMap = coreClient.blockingRun(runRequest);
		Result result = resultMap.get("predictions");
		if(result == null || result.hasFailed()) {
			Assert.fail("Result missing...");
		}
		/*
			Cast it to List:
		 */
		List prediction = (List) result.castResultData(
				"builtin.List", BuiltinCaster.class).getDataField();
		double correctPredictions = 0.;
		for (int i = 0; i < prediction.size(); i++) {
			Instance testInstance = weatherTestSet.get(i);
			String value = weatherTestSet.classAttribute().value((int)testInstance.classValue());
			if(value.equals(prediction.get(i).toString())) {
				correctPredictions++;
			}
		}
		logger.info("{}/{} correct predictions.", (int) correctPredictions, prediction.size());
	}


	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig(FileUtil.getPathOfResource("config/weka-ml-classifiers-classconf.json"),
				FileUtil.getPathOfResource("config/sl-ml-classifiers-classconf.json"));
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		OnthologicalTypeConfig conf = new OnthologicalTypeConfig();

		conf.appendConfigFromJsonStrings(FileUtil.readResourceAsString("config/builtin-typeconf.json"),
				FileUtil.readResourceAsString("config/weka-ml-typeconf.json"),
				FileUtil.readResourceAsString("config/sl-ml-typeconf.json"));
		return conf;
	}
	private static SEDEObject getDataField(Instances dataSet) {
		return new ObjectDataField("LabeledInstances", dataSet);
	}
}
