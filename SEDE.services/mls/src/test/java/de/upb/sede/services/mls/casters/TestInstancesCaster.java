package de.upb.sede.services.mls.casters;

import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.services.mls.util.MLDataSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import weka.core.Instances;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;


public class TestInstancesCaster {

	private final static Logger logger = LogManager.getLogger();

	@Test
	public void testCasting() {
		Instances weather = MLDataSets.getDataSetWithLastIndexClass("weather.arff");
		ByteArrayOutputStream semanticRepr = new ByteArrayOutputStream();
		SemanticStreamer.streamObjectInto(semanticRepr, new ObjectDataField(Instances.class.getName(), weather),
				InstancesCaster.class.getName(), "dataset");
		String arffData = semanticRepr.toString();
		assertTrue(arffData.contains(InstancesCaster.classAttributePrefix));
		ByteArrayInputStream semanticData = new ByteArrayInputStream(arffData.getBytes());
		Instances fromSemantic = (Instances) SemanticStreamer.
				readObjectFrom(semanticData, InstancesCaster.class.getName(),
				"dataset", Instances.class.getName()).getDataField();
		assertEquals(weather.classIndex(), fromSemantic.classIndex());
		assertEquals(weather.toString().trim(), fromSemantic.toString().trim());
	}

//	@Test
	public void benchmarkCasting() {
		long stop = System.currentTimeMillis();
		Instances cifar = MLDataSets.getDataSetWithLastIndexClass("cifar.arff");
		long loadTime = System.currentTimeMillis() - stop;

		ByteArrayOutputStream semanticRepr = new ByteArrayOutputStream();
		stop = System.currentTimeMillis();
		SemanticStreamer.streamObjectInto(semanticRepr, new ObjectDataField(Instances.class.getName(), cifar),
				InstancesCaster.class.getName(), "dataset");
		long toSemanticTime = System.currentTimeMillis() - stop;
		byte[] semanticData = semanticRepr.toByteArray();
		logger.info("Semantic data size: {} bytes", semanticData.length);
		ByteArrayInputStream semanticInput = new ByteArrayInputStream(semanticData);
		stop = System.currentTimeMillis();
		Instances fromSemantic = (Instances) SemanticStreamer.
				readObjectFrom(semanticInput, InstancesCaster.class.getName(),
						"dataset", Instances.class.getName()).getDataField();
		long fromSemanticTime = System.currentTimeMillis() - stop;

		logger.info("loading cifar.arff took: {}ms.", loadTime);
		logger.info("casting to semantic took: {}ms.", toSemanticTime);
		logger.info("casting from semantic took: {}ms.", fromSemanticTime);
	}
}
