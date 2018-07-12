package de.upb.sede.services.jsml.wrappers;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for Weka-base classifier.
 */
public class WrapBaseClassifier {

	private static final Logger logger = LogManager.getLogger("JSML");

	private final String classifierName;
	private Classifier classifier;

	/**
	 * Default constructor of the wrapper.
	 */
	public WrapBaseClassifier(String classifierName) throws ClassNotFoundException {
		logger.trace("Wrapper for classifier '{}' created.", classifierName);
		this.classifierName = classifierName;
		// make sure the given classname is a classifier:
		if(!Classifier.class.isAssignableFrom(Class.forName(classifierName))){
			throw new RuntimeException("The specified classname is not a classifier: " + classifierName);
		}
	}

	/**
	 * Constructs a new instance of a classifier.
	 * @throws Exception
	 */
	private void construct() throws Exception {
		Class<?> classifierClass = Class.forName(classifierName);
		classifier = (Classifier) ConstructorUtils.invokeConstructor(classifierClass);
	}

	/**
	 *	Fits the model of the classifier using the given data.
	 * @param data training data.
	 * @throws Exception exception thrown during invocation of 'buildClassifier'.
	 */
	public void fit(Instances data) throws Exception {
		/* Recreate the classifier object.
		 * to allow warm start move this invocation to the constructor. */
		construct();
		classifier.buildClassifier(data);
	}

	/**
	 * Uses the trained model to do prediction based on the given data.
	 * @param data input data
	 * @return predicted class index
	 * @throws Exception throw during invocation of 'classifyInstance'
	 */
	public List<Double> predict(Instances data) throws Exception {
		if(classifier== null) {
			throw new RuntimeException("First fit the model.");
		}
		List<Double> predictions = new ArrayList<>(data.size());
		for(Instance instance : data) {
			predictions.add(classifier.classifyInstance(instance));
		}
		return predictions;
	}
}
