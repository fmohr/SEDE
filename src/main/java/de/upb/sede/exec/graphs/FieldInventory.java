package de.upb.sede.exec.graphs;

import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.SEDEObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class FieldInventory {

	private final Map<String, ExecutionInv> executionInvironmentMap = new HashMap<>();



	static class ExecutionInv extends ConcurrentHashMap<String, SEDEObject> implements ExecutionEnvironment {

	}


}
