package de.upb.sede.services.mls;

import java.io.Serializable;
import java.util.List;

import de.upb.sede.services.mls.util.Options;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;

public class WekaASWrapper implements Serializable {

	private static final Logger logger = LogManager.getLogger("MLS");

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private final String asSearcherName, asEvaluatorName;
	private final String[] searcherOptions, evalOptions;

	private AttributeSelection attributeSelection;

	private Attribute cachedClassAttribute;
	private Instances cachedInstances;


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
		ASSearch searcher = (ASSearch) ConstructorUtils.invokeConstructor(Class.forName(asSearcherName));
		if(searcher instanceof OptionHandler) {
			((OptionHandler) searcher).setOptions(searcherOptions);
		}
		ASEvaluation eval = (ASEvaluation) ConstructorUtils.invokeConstructor(Class.forName(asEvaluatorName));
		if(eval instanceof OptionHandler) {
			((OptionHandler) eval).setOptions(evalOptions);
		}
		attributeSelection.setSearch(searcher);
		attributeSelection.setEvaluator(eval);
	}

	public void train(Instances instances) throws Exception {
		System.out.println("SELECTING ATTRs");
		attributeSelection.SelectAttributes(instances);
	}

	public Instances preprocess(Instances instances) throws Exception {
		System.out.println("REDUCING DEMS");
		return attributeSelection.reduceDimensionality(instances);
	}
}
