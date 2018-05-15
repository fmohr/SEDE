package de.upb.sede.exec;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.util.Observable;

import java.util.Map;

public interface ExecutionEnvironment extends Map<String, SEDEObject> {
	public Observable<ExecutionEnvironment> getState();
}