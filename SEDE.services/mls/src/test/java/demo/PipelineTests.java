package demo;

import de.upb.sede.BuiltinCaster;
import de.upb.sede.client.CoreClient;
import de.upb.sede.client.CoreClientHttpServer;
import de.upb.sede.client.EasyClient;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.services.mls.DataSetService;
import de.upb.sede.services.mls.casters.InstancesCaster;
import de.upb.sede.services.mls.util.IndexedInstances;
import de.upb.sede.services.mls.util.MLDataSets;
import de.upb.sede.services.mls.util.MLPipelinePlan;
import de.upb.sede.services.mls.util.MLServicePipeline;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineTests {

	private final static Logger logger = LogManager.getLogger("JSML");

	static CoreClientHttpServer coreClient;

	static String clientAddress = WebUtil.HostIpAddress();
	static int clientPort = 7000;

	static String gatewayAddress = "localhost";
	static int gatewayPort = 6000;

	static ExecutorHttpServer executor1;
	static ExecutorHttpServer executor2;

	static GatewayHttpServer gateway;

	static Instances weather;
	static Instances weatherTrainSet;
	static Instances weatherTestSet;

	@BeforeClass
	public static void startClient() {
		gateway = new GatewayHttpServer(gatewayPort, getTestClassConfig(), getTestTypeConfig());

		ExecutorConfigurationCreator creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor1");
		creator.withSupportedServices(DataSetService.class.getName(),
				"weka.attributeSelection.AttributeSelection",
				"weka.classifiers.bayes.BayesNet");
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSON(creator.toString());
		executor1 = new ExecutorHttpServer(configuration, "localhost",  9000);
		gateway.register(executor1.registration());

		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("executor2");
		creator.withSupportedServices(DataSetService.class.getName(), "weka.classifiers.bayes.NaiveBayes");
		configuration = ExecutorConfiguration.parseJSON(creator.toString());
		executor2 = new ExecutorHttpServer(configuration, "localhost",  9001);
		gateway.register(executor2.registration());



		creator = new ExecutorConfigurationCreator();
		creator.withExecutorId("Client");
		configuration = ExecutorConfiguration.parseJSON(creator.toString());
		coreClient = new CoreClientHttpServer(configuration, clientAddress, clientPort, gatewayAddress, gatewayPort);
		MLServicePipeline.cc = coreClient; // singleton of servicepipeline.
		/*
			Disabled if you dont have dot installed.

		 */
		coreClient.writeDotGraphToDir("testrsc/ml/pipeline/");

	}

	@BeforeClass
	public static void loadDataSet() throws Exception {
		weather = MLDataSets.getDataSetWithLastIndexClass("weather.arff");
		RemovePercentage splitter = new RemovePercentage();
		splitter.setInputFormat(weather);
		splitter.setPercentage(50);

		weatherTrainSet = Filter.useFilter(weather, splitter);

		splitter.setInputFormat(weather);
		splitter.setInvertSelection(true);
		weatherTestSet = Filter.useFilter(weather, splitter);

		MLDataSets.setClassIndex(weather, -1);
		MLDataSets.setClassIndex(weatherTrainSet, -1);
		MLDataSets.setClassIndex(weatherTestSet, -1);
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

	@AfterClass
	public static  void shutdownClient() {
		executor1.shutdown();
		coreClient.getClientExecutor().shutdown();
		gateway.shutdown();
	}

	@Test
	public void testPiplineService1() throws Exception {
		/*
			The actual pipe based on java objects:
		 */
		ASSearch searcher = new BestFirst();
		String[] searchOpt = {"-D", "1", "-N", "5"};
		((BestFirst) searcher).setOptions(searchOpt);

		CfsSubsetEval attrEval = new CfsSubsetEval();
		String[] attrEvalOpt = {"-P", "1", "-E", "1"};
		attrEval.setOptions(attrEvalOpt);

		NaiveBayes classifier = new NaiveBayes();
		String[] classifierOpt = {"-D"};
		classifier.setOptions(classifierOpt);

		/*
			Encoding this pipe into a plan:
		 */
		MLPipelinePlan plan = new MLPipelinePlan();
		plan.addWekaAttributeSelection(searcher, attrEval);
		plan.setClassifier(classifier);

		logger.info("Plan created:\n{}" , plan);

		MLServicePipeline pipeline = new MLServicePipeline(plan, java.util.Optional.of("pipe1"));

		logger.info("Pipeline Service created.");

		pipeline.buildClassifier(weatherTrainSet);


		logger.info("Pipeline Service trained.");

		Double[] prediction = pipeline.classifyInstances(weatherTestSet);


		logger.info("Pipeline Service predicted inputs.");

		double correctPredictions = 0.;
		for (int i = 0; i < prediction.length; i++) {
			Instance testInstance = weatherTestSet.get(i);
			if(testInstance.classValue() == prediction[i]) {
				correctPredictions++;
			}
		}
		logger.info("{}/{} correct predictions.", (int) correctPredictions, prediction.length);
	}


	@Test
	public void testPiplineService2_DatasetService() throws Exception {
		/*
			The actual pipe based on java objects:
		 */
		ASSearch searcher = new BestFirst();
		String[] searchOpt = {"-D", "1", "-N", "5"};
		((BestFirst) searcher).setOptions(searchOpt);

		CfsSubsetEval attrEval = new CfsSubsetEval();
		String[] attrEvalOpt = {"-P", "1", "-E", "1"};
		attrEval.setOptions(attrEvalOpt);

		NaiveBayes classifier = new NaiveBayes();
		String[] classifierOpt = {"-D"};
		classifier.setOptions(classifierOpt);

		/*
			Encoding this pipe into a plan:
		 */
		MLPipelinePlan plan = new MLPipelinePlan();
		plan.addWekaAttributeSelection(searcher, attrEval);
		plan.setClassifier(classifier);

		/*
			Prepare data into indexed instances:
		 */
		List<List<Integer>> splits = MLDataSets.split(MLDataSets.intRange(0, weather.size()),0.7);
		IndexedInstances trainset = new IndexedInstances("weather.arff", splits.get(0), -1);
		IndexedInstances testset = new IndexedInstances("weather.arff", splits.get(1), -1);

		logger.info("Plan created:\n{}" , plan);

		MLServicePipeline pipeline = new MLServicePipeline(plan, java.util.Optional.of("pipe2_datasetservice"));

		logger.info("Pipeline Service created.");

		pipeline.buildClassifier(trainset);


		logger.info("Pipeline Service trained.");

		Double[] prediction = pipeline.classifyInstances(testset);


		logger.info("Pipeline Service predicted inputs.");

		double correctPredictions = 0.;
		for (int i = 0; i < prediction.length; i++) {
			Instance testInstance = weatherTestSet.get(i);
			if(testInstance.classValue() == prediction[i]) {
				correctPredictions++;
			}
		}
		logger.info("{}/{} correct predictions.", (int) correctPredictions, prediction.length);
	}


	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/ml-classifiers-classconf.json", "testrsc/config/ml-pp-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/builtin-typeconf.json","testrsc/config/ml-typeconf.json");
	}
}
