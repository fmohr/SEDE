package de.upb.sede.services.jsml.util;

import de.upb.sede.services.jsml.casters.InstancesCaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.*;

public class MLDataSets {

	private static final Logger logger = LogManager.getLogger("JSML");

	private static final String DATASET_PATH;

	static {
		/*
		 * Read the path of data set folder from environment variable: 'DATASET_PATH'
		 */
		if(System.getenv().containsKey("DATASET_PATH")) {
			DATASET_PATH = System.getenv().get(System.getenv().get("DATASET_PATH"));
		} else {
			DATASET_PATH = "../CrcTaskBasedConfigurator/testrsc/";
			logger.info("Environment variable 'DATASET_PATH' isn't defined.");
			logger.info("Using {} as default path to data sets. Change default in: MLDataSets.java",
					DATASET_PATH);
		}
		File datasetDirectory = new File(DATASET_PATH);
		if(!datasetDirectory.exists() || !datasetDirectory.isDirectory()){
			logger.fatal("Dataset folder {} doesn't exist.", datasetDirectory.getAbsolutePath());
		}
	}

	/**
	 * Reads all the instances from the file (ARFF, CSV, XRFF, ...) on the given filepath.
	 *
	 * @param relativeFilePath dataset file path relative to 'DATASET_PATH'.
	 * @return loaded dataset
	 */
	public static Instances getDataSet(String relativeFilePath) {
		String dataSetPath = getAbsolutePathFromRelative(relativeFilePath);
		try {
			ConverterUtils.DataSource source = new ConverterUtils.DataSource(dataSetPath);
			Instances instances = source.getDataSet();
			return instances;
		} catch (Exception e) {
			logger.error("Exception during loading dataset from folder " + dataSetPath, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reads all the instances from the file (ARFF, CSV, XRFF, ...) on the given filepath.
	 * Marks the classIndex'th attribute as the class attribute of the dataset.
	 *
	 * @param relativeFilePath dataset file path relative to 'DATASET_PATH'.
	 * @param classIndex class index of the dataset. Negative values start from end. e.g.: -1 is last index.
	 * @return loaded dataset
	 */
	public static Instances getClassifiedDataSet(String relativeFilePath, int classIndex) {
		Instances dataset = getDataSet(relativeFilePath);
		setClassIndex(dataset, classIndex);
		return dataset;
	}

	/**
	 * Reads all the instances from the file (ARFF, CSV, XRFF, ...) on the given filepath.
	 * Marks the last attribute as the class attribute of the dataset.
	 *
	 * @param relativeFilePath dataset file path relative to 'DATASET_PATH'.
	 * @return loaded dataset
	 */
	public static Instances getDataSetWithLastIndexClass(String relativeFilePath) {
		return getClassifiedDataSet(relativeFilePath, -1);
	}

	/**
	 * @param instances
	 * @param relativeFilePath
	 */
	public synchronized static void storeDataSet(Instances instances, String relativeFilePath) throws IOException {
		String dataSetPath = getAbsolutePathFromRelative(relativeFilePath);
		File dataSetFile = new File(dataSetPath);
		/*
			Make sure the parent directory exists:
		 */
		File parentDirectory = dataSetFile.getParentFile();
		if(!parentDirectory.exists()) {
			if(!parentDirectory.mkdirs()) {
				throw new RuntimeException("Cannot create the necessary directory to store the instances: "
						+ dataSetFile.getAbsolutePath());
			}
		} else if(!parentDirectory.isDirectory()) {
			throw new RuntimeException("Illegal path:" + dataSetFile.getAbsolutePath());
		}
		try(OutputStream out = new FileOutputStream(dataSetFile)) {
			new InstancesCaster().cts_Instances(out, instances);
		}
	}

	public static String getAbsolutePathFromRelative(String relativeFilePath) {
		return new File(DATASET_PATH + relativeFilePath).getAbsolutePath();
	}

	public static void setClassIndex(Instances dataset, int classIndex) {
		int dataAttrCount = dataset.numAttributes();
		/*
			modulo enforces classIndex to be in bound.
			also converts negative indices to the corresponding offset index from the end:
			e.g.: -1 % 5 > 4
		 */
		while(classIndex<0 || classIndex>= dataAttrCount) {
			classIndex = classIndex % dataAttrCount;
			classIndex = dataAttrCount + classIndex;
		}
		dataset.setClassIndex(classIndex);
	}
}
