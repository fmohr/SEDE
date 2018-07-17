package de.upb.sede.services.jsml.util;


import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.sun.tools.javac.comp.Resolve;
import de.upb.sede.BuiltinCaster;
import de.upb.sede.client.CoreClient;
import de.upb.sede.client.EasyClient;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.services.jsml.DataSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;


/**
 *
 * Note, while this class is serializable, it MUST NOT BE CLONED USING AbstractClassifier.makeCopy,
 * because the references to the used services are serialized and, hence, makeCopy does not work
 * properly. Instead, use the clone method (as adopted by WekaUtils.clone).
 *
 * @author fmohr
 *
 */
@SuppressWarnings("serial")
public class MLServicePipeline implements Classifier, Serializable {

	private long expirationDate = -1;
	private boolean trained;
	private int timeForTrainingPipeline;
	private final MLPipelinePlan constructionPlan;

	private static final Logger logger = LoggerFactory.getLogger(MLServicePipeline.class);

	public static CoreClient cc; // TODO set this singleton

	private final List<String> PPFieldNames = new LinkedList<>();

	private final String classifierFieldName;

	private final Map<String, ServiceInstanceHandle> services = new HashMap<>();

	private final int getSecondsRemaining() {
		return this.expirationDate > 0 ? (int) (this.expirationDate - System.currentTimeMillis()) / 1000 : Integer.MAX_VALUE;
	}

	private String prefixExecutionId = null;


	public MLServicePipeline(final MLPipelinePlan plan) throws InterruptedException {
		this(plan, Optional.empty());
	}

	public MLServicePipeline(final MLPipelinePlan plan, Optional<String> executionId) throws InterruptedException {
		super();
		if (!plan.isValid()) {
			throw new RuntimeException("The given plan is not valid..");
		}
		this.constructionPlan = plan;

		int varIDCounter = 1;
		EasyClient constructorEC = new EasyClient(cc);
		if(executionId.isPresent()) {
			prefixExecutionId = executionId.get();
			constructorEC.withId(prefixExecutionId + "_construct");
		}

		// build composition
		for (MLPipelinePlan.MLPipe attrPipe : plan.getAttrSelections()) {
			String ppFieldName = "service" + varIDCounter;
			this.PPFieldNames.add(ppFieldName);

			if(attrPipe instanceof MLPipelinePlan.WekaAttributeSelectionPipe) {
				MLPipelinePlan.WekaAttributeSelectionPipe wekaASPipe = (MLPipelinePlan.WekaAttributeSelectionPipe) attrPipe;
				String searcherFieldName = "searcher" + varIDCounter;
				String searchOptions = "searcherOptions" + varIDCounter;

				String evalFieldName = "eval" + varIDCounter;
				String evalOptions = "evalOptions" + varIDCounter;

				// add inputs from attribute selection
				SEDEObject searcher = new SEDEObject(wekaASPipe.getSearcher());
				SEDEObject eval = new SEDEObject(wekaASPipe.getEval());
				constructorEC.withArgument(searcherFieldName, searcher).withArgument(evalFieldName, eval)
						.withArgument_StringList(searchOptions, wekaASPipe.getSearcherOptions())
						.withArgument_StringList(evalOptions, wekaASPipe.getEvalOptions());

				// add a construction line to the composition
				constructorEC.withAddedConstructOperation(ppFieldName, // output field name of the created servicehandle
						attrPipe.getName(), // classpath of the preprocessor
						searcherFieldName, evalFieldName, searchOptions, evalOptions);
			} else {
				String asOptionsFieldname = "asOptions" + varIDCounter;
				constructorEC.withArgument_StringList(asOptionsFieldname, attrPipe.getOptions());
				// add a construction line to the composition
				constructorEC.withAddedConstructOperation(ppFieldName, // output field name of the created servicehandle
						attrPipe.getName(), // classpath of the preprocessor
						asOptionsFieldname);

			}
			varIDCounter++;
		}
		this.classifierFieldName = "service" + varIDCounter;
		String classifierOptions = "classifierOptions";


		// add a stringlist for classifier
		constructorEC.withArgument_StringList(classifierOptions, plan.getClassifierPipe().getOptions());

		constructorEC.withAddedConstructOperation(this.classifierFieldName, // output field name of the created servicehandle
				plan.getClassifierPipe().getName()// classpath of the classifier
				);
		// set options of the classifier:
		constructorEC.withAddedMethodOperation(null, classifierFieldName, "set_options", classifierOptions);


		Map<String, Result> results;
		try {
			results = constructorEC.dispatch().get();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}

		// Service creation done!

		// for (String fieldname : servicesContainer.serviceHandleFieldNames()) {
		// ServiceHandle sh = (ServiceHandle) servicesContainer.retrieveField(fieldname).getData();
		// System.out.println("\t" + fieldname + ":=" + sh.getServiceAddress());
		// }

		// check if all our preprocessors and the classifier is created:
		for (String ppFieldName : this.PPFieldNames) {
			if (!results.containsKey(ppFieldName) || // if it isn't returned by the server
					(results.get(ppFieldName).hasFailed())) {
				throw new RuntimeException("Could not create preprocessing service for " + ppFieldName);
			} else {
				services.put(ppFieldName, results.get(ppFieldName).getServiceInstanceHandle());
			}
		}
		// same check for the classifier:
		if (!results.containsKey(this.classifierFieldName) || // if it isn't returned by the server
				(results.get(classifierFieldName).hasFailed())) {
			throw new RuntimeException("Could not create classifier service for " + this.classifierFieldName);
		} else {
			services.put(classifierFieldName, results.get(classifierFieldName).getServiceInstanceHandle());
		}
		logger.debug("Successfully spawned all necessary services for pipeline of plan {}", plan);
	}

	@Override
	public void buildClassifier(final Instances data) throws Exception {
		int secondsRemaining = this.getSecondsRemaining();
		if (secondsRemaining < 5) {
			throw new IllegalStateException("Cannot train, only " + secondsRemaining + " lifetime remain!");
		}
		/*
			TODO if data is instanceof IndexedInstances, use data set service to build the data serverside.
		 */
		int invocationNumber = 1;
		String dataInFieldName = "i1";
		EasyClient trainEC = new EasyClient(cc).withServices(this.services);

		if(prefixExecutionId != null) {
			trainEC.withId(prefixExecutionId + "_train");
		}

		buildDataSetServerSide(trainEC, data, dataInFieldName);

		// create train composition
		for (String ppFieldname : this.PPFieldNames) {
			String dataOutFieldName = "data" + invocationNumber;
			trainEC.withAddedMethodOperation(null, ppFieldname, "train", dataInFieldName);
			trainEC.withAddedMethodOperation(dataOutFieldName, ppFieldname, "preprocess", dataInFieldName);
			// output of this pipe is the input of the next one:
			dataInFieldName = dataOutFieldName;
			invocationNumber++;
		}
		trainEC.withAddedMethodOperation(null, this.classifierFieldName, "train", dataInFieldName);

		/*
			Not interested in any data or service instance:
		 */
		trainEC.getPolicy().setReturnPolicy(ResolvePolicy.none);
		trainEC.getPolicy().setServicePolicy(ResolvePolicy.all);

		long start = System.currentTimeMillis();
		try {
			// System.out.println("Sending the following train composition:\n " +
			// trainEC.getCurrentCompositionText());
			// send train request:
			Future<Map<String, Result>> future = trainEC.dispatch();
			future.get();

			this.timeForTrainingPipeline = (int) (System.currentTimeMillis() - start);
			this.trained = true;
//			this.timesForPrediction = new DescriptiveStatistics(); //  TODO
		} catch (InterruptedException ex) {
			logger.info("The build process was interrupted.");
			throw ex;
		} catch (Exception ex) {
			logger.error("Could not train pipeline " + this.constructionPlan + ". Details:\n", ex);
			throw ex;
		}
	}

	@Override
	public double classifyInstance(final Instance instance) throws Exception {
		if (!this.trained) {
			throw new IllegalStateException("Cannot classify instances as the pipeline has not been (successfully) trained!");
		}
		Instances instances = new Instances(instance.dataset(), 1);
		instances.add(instance);
		return this.classifyInstances(instances)[0];
	}

	public Double[] classifyInstances(final Instances instances) throws Exception {
		if (!this.trained) {
			throw new IllegalStateException("Cannot classify instances as the pipeline has not been (successfully) trained!");
		}

		int secondsRemaining = this.getSecondsRemaining();
		if (secondsRemaining < 5) {
			throw new IllegalStateException("Cannot predict, only " + secondsRemaining + " lifetime remain!");
		}

		int invocationNumber = 1;
		String dataInFieldName = "i1";

		EasyClient predictEC = new EasyClient(cc).withServices(this.services);

		if(prefixExecutionId != null) {
			predictEC.withId(prefixExecutionId + "_predict");
		}

		buildDataSetServerSide(predictEC, instances, dataInFieldName);



		// create train composition
		for (String ppFieldname : this.PPFieldNames) {
			String dataOutFieldName = "data" + invocationNumber;
			predictEC.withAddedMethodOperation(dataOutFieldName, ppFieldname, "preprocess", dataInFieldName);
			// pipe output as the input of the next one:
			dataInFieldName = dataOutFieldName;
			// create output name for the next data
			invocationNumber++;
		}
		predictEC.withAddedMethodOperation("predictions", this.classifierFieldName, "predict", dataInFieldName);

		/*
			Only interested in 'predictions' field.
			No need to keep any service persistent.
		 */
		predictEC.getPolicy().setServicePolicy(ResolvePolicy.none);
		predictEC.getPolicy().setReturnFieldnames(Arrays.asList("predictions"));

		long start = System.currentTimeMillis();
		Result result;
		try {
			// System.out.println("Sending the following predict composition:\n " +
			// predictEC.getCurrentCompositionText());
			// send predict request:
			Future<Map<String, Result>> results = predictEC.dispatch();
			result = results.get().get("predictions");
		} catch (Throwable ex) {
			logger.error("Could not obtain predictions from pipeline " + this.constructionPlan + ". Training flag: " + this.trained + ". Details:\n", ex);
			throw new RuntimeException(ex);
		}
		long end = System.currentTimeMillis();
//		this.timesForPrediction.addValue(end - start); // TODO

		if (result.hasFailed()) {
			logger.error("Did not receive predictions from the server!");
			throw new RuntimeException("Did not receive predictions from the server.");
		}

		@SuppressWarnings("unchecked")
		List<Double> predictedIndices = (List<Double>) result.castResultData("builtin.List", BuiltinCaster.class).getObject();
		return predictedIndices.toArray(new Double[0]);
	}

	private boolean buildDataSetServerSide(EasyClient ec, Instances instances, String datasetFieldName) {
		/*
			If the input instances are just IndexedInstances,
			use dataset service to construct the input on the other side:
		 */
		if(instances instanceof IndexedInstances) {
			IndexedInstances indexedInst = (IndexedInstances) instances;
			String datasetServiceFieldname = "datasetService1";
			String indicesFieldname = "indices1";



			ec.withAddedConstructOperation(datasetServiceFieldname, DataSetService.class.getName(),
					"\"" + indexedInst.getDatasetReference() + "\"");

			ec.withArgument(indicesFieldname, new SEDEObject("builtin.List", indexedInst.getIndices()));
			if(indexedInst.getClassIndex().isPresent()) {
				ec.withAddedMethodOperation(datasetFieldName, datasetServiceFieldname, "fromIndicesLabeled",
						indicesFieldname, String.valueOf(indexedInst.getClassIndex().get()));
			} else {
				ec.withAddedMethodOperation(datasetFieldName, datasetServiceFieldname, "fromIndices",
						indicesFieldname);
			}
			return true;
		} else {
			/*
				data set wasn't build server side. Send it instead:
			 */
			ec.withArgument(datasetFieldName, new SEDEObject(Instances.class.getName(),instances));
			return false;
		}
	}

	@Override
	public double[] distributionForInstance(final Instance instance) throws Exception {
		if (!this.trained) {
			throw new IllegalStateException("Cannot classify instances as the pipeline has not been (successfully) trained!");
		}
		throw new Exception("Service Pipeline doesn't support distributionForInstance.");
	}

	@Override
	public Capabilities getCapabilities() {
		throw new RuntimeException("Service Pipeline doesn't support getCapabilities.");
	}

	@Override
	public MLServicePipeline clone() {
		try {
			return new MLServicePipeline(this.constructionPlan);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> getPPFieldNames() {
		return this.PPFieldNames;
	}

	public String getClassifierFieldName() {
		return this.classifierFieldName;
	}

	public int getTimeForTrainingPipeline() {
		return this.timeForTrainingPipeline;
	}

	// TODO
//	public DescriptiveStatistics getTimesForPrediction() {
//		return this.timesForPrediction;
//	}

	public long getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final long expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setPrefixExecutionId(String executionId) {
		this.prefixExecutionId = executionId;
	}

	public MLPipelinePlan getConstructionPlan() {
		return this.constructionPlan;
	}

	@Override
	public String toString() {
		return this.constructionPlan.toString();
	}
}
