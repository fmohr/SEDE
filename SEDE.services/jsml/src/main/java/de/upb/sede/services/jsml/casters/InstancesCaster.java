package de.upb.sede.services.jsml.casters;

import de.upb.sede.util.Streams;
import org.w3c.dom.Attr;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.WekaEnumeration;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Optional;

/**
 * Provides casting methods for: Instances <-> arff
 */
public class InstancesCaster {

	public static final String classAttributePrefix ="$class$";

	/**
	 * Reads the arff data from the inputstream and creates a instances object.
	 */
	public Instances cfs_Instances(InputStream is) throws Exception {
		ConverterUtils.DataSource source = new ConverterUtils.DataSource(is);
		Instances loadedInstaces = (Instances) source.getDataSet();
		removeLabelFromClassAttr(loadedInstaces);
		return loadedInstaces;
	}

	/**
	 * Writes the arff representation of the given dataset into the output stream.
	 */
	public void cts_Instances(OutputStream os, Instances dataSet) throws IOException {
		addLabelToClassAttr(dataSet);
		Streams.OutWriteString(os, dataSet.toString(), false);
		removeLabelFromClassAttr(dataSet);
	}

	private Optional<Attribute> getClassAttribute(Instances dataSet) {
		if(dataSet.classIndex() >= 0) {
			return Optional.of(dataSet.attribute(dataSet.classIndex()));
		} else {
			for(Enumeration<Attribute> e = dataSet.enumerateAttributes();
					e.hasMoreElements();) {
				Attribute attribute = e.nextElement();
				if(attribute.name().startsWith(classAttributePrefix)) {
					return Optional.of(attribute);
				}
			}
		}
		return Optional.empty();
	}

	private void addLabelToClassAttr(Instances dataSet) {
		Optional<Attribute> attr = getClassAttribute(dataSet);
		if(attr.isPresent()) {
			Attribute clsAttr = attr.get();
			String classAttrName = "$class$" + clsAttr.name();
			dataSet.renameAttribute(clsAttr.index(), classAttrName);
		}
	}

	private void removeLabelFromClassAttr(Instances dataSet) {
		Optional<Attribute> classAttr = getClassAttribute(dataSet);
		if(classAttr.isPresent()) {
			Attribute attr = classAttr.get();
			dataSet.setClass(attr);
			if(attr.name().startsWith(classAttributePrefix)) {
				String classAttrName = attr.name().substring(classAttributePrefix.length());
				dataSet.renameAttribute(attr.index(), classAttrName);
			}
		}
	}

}
