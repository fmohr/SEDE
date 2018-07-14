package de.upb.sede.services.jsml.services;

import java.io.Serializable;
import java.util.List;

import de.upb.sede.services.jsml.util.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class WekaASWrapper implements Serializable {

	private static final Logger logger = LogManager.getLogger("JSML");

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private final String asSearcherName, asEvaluatorName;
	private final String[] searcherOptions, evalOptions;

	private AttributeSelection attributeSelection;

	private Attribute cachedClassAttribute;
	private Instances cachedInstances;

	private boolean trained = false;

	public WekaASWrapper(String asSearcherName, String asEvaluatorName, List searcherOptions, List evalOptions) throws Exception {
		logger.debug("Created wrapper for AS: {}/{}", asSearcherName, asEvaluatorName);
		logger.trace("Options for AS: \n\t{}\n\t{}", searcherOptions, evalOptions);
		this.asSearcherName = asSearcherName;
		this.asEvaluatorName = asEvaluatorName;
		this.searcherOptions = Options.splitStringIntoArr(searcherOptions);
		this.evalOptions =     Options.splitStringIntoArr(evalOptions);
		construct();
	}

	private void construct() throws Exception {
		attributeSelection = new AttributeSelection();
		attributeSelection.setSearch(ASSearch.forName(asSearcherName, searcherOptions));
		attributeSelection.setEvaluator(ASEvaluation.forName(asEvaluatorName, evalOptions));
	}

	public void train(Instances instances) throws Exception {
		if(trained){
			construct();
		}
		if(cachedClassAttribute==null) {
			cachedClassAttribute = instances.classAttribute();
			cachedInstances = new Instances(instances, 0);
		}
		attributeSelection.SelectAttributes(instances);
		trained = true;
	}

	public Instances preprocess(Instances instances) throws Exception {
		if(cachedInstances==null) {
			throw new RuntimeException("First call SelectAttribute");
		}
		for(Instance instance : instances) {
			instance.setDataset(cachedInstances);
		}
		return attributeSelection.reduceDimensionality(instances);
	}
}
