package de.upb.sede.exec;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.procedure.AcceptDataProcedure;
import de.upb.sede.util.Observable;

import java.util.Map;
import java.util.function.Function;

public interface ExecutionEnvironment extends Map<String, SEDEObject> {
	public Observable<ExecutionEnvironment> getState();

	/**
	 * Marks the fieldname unavailable, suggesting that it won't be delivered.
	 * @param fieldname fieldname that wont be available.
	 */
	public void markUnavailable(String fieldname);

	/**
	 * Querries if the given fieldname is equal to an unavailable marked fieldname.
	 *
	 * @param fieldname fieldname whose unavailability is checked.
	 *
	 * @return true if the given fieldname has been marked unavailable.
	 */
	public boolean isUnavailable(Object fieldname);


	void registerCacher(String fieldname,  Function<SemanticDataField, SEDEObject> cacher);
}