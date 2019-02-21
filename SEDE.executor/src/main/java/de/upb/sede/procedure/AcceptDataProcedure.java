package de.upb.sede.procedure;

import java.util.function.Function;

import de.upb.sede.util.AsyncObserver;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Observer;

public class AcceptDataProcedure implements Procedure, Function<SemanticDataField, SEDEObject> {
	private final static Logger logger = LoggerFactory.getLogger(AcceptDataProcedure.class);
	private String fieldname;
	private JSONObject castType;
	private Task task;

	@Override
	public void processTask(Task task) {
		fieldname = task.getAttribute("fieldname");
		castType = task.getAttribute("CastType");
		this.task = task;
//		try {
////			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		ExecutionEnvironment environment = task.getExecution().getEnvironment();
		/*
		 * Add an observer to the environment to be notified when the field is there
		 */
		Observer<ExecutionEnvironment> fieldObserver = new AsyncObserver<>(Observer.lambda(
				env -> env.containsKey(fieldname) || env.isUnavailable(fieldname),
				this::eventHandler), task.getExecution().getMessenger());

		environment.observe(fieldObserver);
		if(castType != null) {
			environment.registerCacher(fieldname, this);
		}
	}

	private void eventHandler(ExecutionEnvironment env) {
		if(env.isUnavailable(fieldname)) {
			task.setFailed();
		} else if(env.containsKey(fieldname)) {

			if(castType != null) {
				/*
				 * This accept data procedure has to cast the field in place.
				 */
				SEDEObject rawField = env.get(fieldname);
				if(rawField.isSemantic()) {
					SEDEObject castedField = castInPlace((SemanticDataField) rawField);
					env.put(fieldname, castedField);
				} else {
					logger.trace("AcceptDataNode didn't need to cast {} in place. " +
							"Wasn't of semantic type.", fieldname);
				}
			}
			task.setSucceeded();
		} else {
			logger.error("Something went wrong during Accept Data procedure. " +
					"Notification was triggered but field '{}' is not inside the environment.", fieldname);
			task.setFailed();
		}
	}

	public SEDEObject castInPlace(SemanticDataField dataInput) {
		String casterClasspath = (String) castType.get("caster-classpath");
		String originalType = (String) castType.get("original-type");
		String targetType = (String) castType.get("target-type");
		return SemanticStreamer.readObjectFrom(dataInput, casterClasspath, originalType, targetType);
	}

	@Override
	public SEDEObject apply(SemanticDataField dataField) {
		logger.debug("Casting semantic data in place as a cacher.");
		return castInPlace(dataField);
	}
}
