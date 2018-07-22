package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exceptions.DependecyTaskFailed;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

public class AcceptDataProcedure implements Procedure {
	private final static Logger logger = LogManager.getLogger();
	private String fieldname;
	private JSONObject castType;
	private Task task;

	@Override
	public void processTask(Task task) {
		fieldname = task.getAttribute("fieldname");
		castType = task.getAttribute("CastType");
		this.task = task;
		Observer<ExecutionEnvironment> envObserver = Observer
				.<ExecutionEnvironment>lambda(env -> env.isUnavailable(fieldname) || env.containsKey(fieldname) ,
						this::eventHandler);

		ExecutionEnvironment environment = task.getExecution().getEnvironment();
		environment.getState().observe(envObserver);
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
					ByteArrayInputStream rawDataInput =
							new ByteArrayInputStream((byte[])rawField.getObject());
					SEDEObject castedField = castInPlace(rawDataInput);
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
		}
	}

	public SEDEObject castInPlace(InputStream dataInput) {
		Map<String, Object> parameters = (Map<String, Object>) castType;

		String casterClasspath = (String) parameters.get("caster-classpath");
		String originalType = (String) parameters.get("original-type");
		String targetType = (String) parameters.get("target-type");

		return SemanticStreamer.readObjectFrom(dataInput, casterClasspath, originalType, targetType);

	}

}
