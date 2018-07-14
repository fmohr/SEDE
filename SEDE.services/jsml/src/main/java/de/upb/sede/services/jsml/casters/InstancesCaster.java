package de.upb.sede.services.jsml.casters;

import de.upb.sede.util.Streams;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.*;

/**
 * Provides casting methods for: Instances <-> arff
 */
public class InstancesCaster {

	/**
	 * Reads the arff data from the inputstream and creates a instances object.
	 */
	public Instances cfs_Instances(InputStream is) throws Exception {
		ConverterUtils.DataSource source = new ConverterUtils.DataSource(is);
		return source.getDataSet();
	}

	/**
	 * Writes the arff representation of the given dataset into the output stream.
	 */
	public void cts_Instances(OutputStream os, Instances dataSet) throws IOException {
		Streams.OutWriteString(os, dataSet.toString(), false);
	}

}
