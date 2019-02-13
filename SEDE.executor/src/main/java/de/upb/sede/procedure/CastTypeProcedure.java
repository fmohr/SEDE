package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.Task;
import de.upb.sede.util.ExtendedByteArrayOutputStream;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CastTypeProcedure implements Procedure {
	@Override
	public void processTask(Task task) {

		/* gather cast information */
		/* example of the attribute map :
		        "fieldname": "a",
				"nodetype": "CastType",
				"cast-to-semantic": true,
				"original-type": "some.Lib",
				"caster-classpath": "casters.Caster1",
				"target-type": "semanticType1"
		 */
		Map<String, Object> parameters = task.getAttributes();
		String fieldname = (String) parameters.get("fieldname");
		String casterClasspath = (String) parameters.get("caster-classpath");
		String originalType = (String) parameters.get("original-type");
		String targetType = (String) parameters.get("target-type");
		boolean castToSemantic = (Boolean) parameters.get("cast-to-semantic");

		/*
		 * Retrieve the data that needs to be casted:
		 */
		SEDEObject field = null;
		try {
			field = task.getExecution().performLater(() -> task.getExecution().getEnvironment().get(fieldname)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Interrupted while accessing field: " + fieldname, e);
		}
		/*
		 * Cast the data:
		 */
		SEDEObject castedField;
		if(castToSemantic) {
			try {
				ExtendedByteArrayOutputStream byteOutputStream = new ExtendedByteArrayOutputStream();
				SemanticStreamer.streamObjectInto(byteOutputStream, field, casterClasspath, targetType);
				InputStream inputSteam = byteOutputStream.toInputStream();
				castedField = new SemanticDataField(targetType, inputSteam, true);
				byteOutputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("This exception shouldn't have been thrown..");
			}
		} else if(field.isSemantic()){
			InputStream byteStream = field.getDataField();
			castedField = SemanticStreamer.readObjectFrom(byteStream, casterClasspath, originalType, targetType);
		} else {
			throw new RuntimeException("Task states to cast \"" + fieldname + "\" to  real data type " +
					"but the field is not semantic: \n" + field.toString());
		}
		/*
		 * Perform execution changes later:
		 */
		task.getExecution().performLater( () -> {
			/*
			 * Add this field to the environment.
			 */
			task.getExecution().getEnvironment().put(fieldname, castedField);
			task.setSucceeded();
		});
	}
}
