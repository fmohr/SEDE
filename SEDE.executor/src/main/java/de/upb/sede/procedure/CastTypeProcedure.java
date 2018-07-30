package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticDataField;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exec.Task;

import java.io.*;
import java.util.Map;

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

		SEDEObject field = task.getExecution().getEnvironment().get(fieldname);
		SEDEObject castedField;
		if(castToSemantic) {
			/*
			 * TODO if there is a need to read the input byte multiple times change this behaviour to ByteArrayInputStream instead.
			 * (Which offers seek)
			 */
			PipedInputStream byteInputSteam = new PipedInputStream();
			PipedOutputStream byteOutputStream = null;
			try {
				byteOutputStream = new PipedOutputStream(byteInputSteam);
				SemanticStreamer.streamObjectInto(byteOutputStream, field, casterClasspath, targetType);
				castedField = new SemanticDataField(targetType, byteInputSteam, true);
				byteOutputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("This exception shouldn't have been thrown..");
			}
		} else if(field.isSemantic()){
			InputStream byteStream = field.getDataField();
			castedField = SemanticStreamer.readObjectFrom(byteStream, casterClasspath, originalType, targetType);
		} else {
			throw new RuntimeException("Task states to cast \"" + fieldname + "\" to  semantic " +
					"but the field is not semantic: \n" + field.toString());
		}
		task.getExecution().getEnvironment().put(fieldname, castedField);
		task.setSucceeded();
	}
}
