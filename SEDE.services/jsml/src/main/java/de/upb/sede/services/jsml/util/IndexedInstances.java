package de.upb.sede.services.jsml.util;

import de.upb.sede.services.jsml.DataSetService;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;

/**
 * References a dataset by filesystem path.
 * Holds a list of indices that refers to a subset of data from the dataset.
 * Can also keep the class index.
 */
public class IndexedInstances extends Instances {

	private final String datasetReference;
	private List<Integer> indices;
	private Optional<Integer> classIndex;

	private IndexedInstances(String datasetReference, List<Integer> indices, Optional<Integer> classIndex) {
		super(datasetReference, new ArrayList<>(), 0);
		this.datasetReference = datasetReference;
		this.indices = Objects.requireNonNull(indices);
		this.classIndex = classIndex;
	}

	public IndexedInstances(String datasetReference, List<Integer> indices, int classIndex) {
		this(datasetReference, indices, Optional.of(classIndex));
	}

	public IndexedInstances(String datasetReference) {
		this(datasetReference, new ArrayList<>(), Optional.empty());
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = Optional.of(classIndex);
	}

	public void setIndices(List<Integer> indices) {
		indices = new ArrayList<>(Objects.requireNonNull(indices));
	}

	public Instances toInstances() {
		DataSetService dataSetService = new DataSetService(datasetReference);
		if(classIndex.isPresent()){
			return dataSetService.allLabeled(classIndex.get());
		} else {
			return dataSetService.all();
		}
	}

	public String getDatasetReference() {
		return datasetReference;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public Optional<Integer> getClassIndex() {
		return classIndex;
	}

	public String toString() {
		return datasetReference + ":" + indices.size();
	}

}
