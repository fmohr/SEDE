package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.SemanticStreamer;
import de.upb.sede.exceptions.DependecyTaskFailed;
import de.upb.sede.exec.Task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			SemanticStreamer.streamObjectInto(byteStream, field, casterClasspath, targetType);
			byte[] semanticData = byteStream.toByteArray();
			castedField = new SEDEObject(targetType, semanticData);
		} else if(field.isSemantic()){
			byte[] semanticData = (byte[]) field.getObject();
			ByteArrayInputStream byteStream = new ByteArrayInputStream(semanticData);
			castedField = SemanticStreamer.readObjectFrom(byteStream, casterClasspath, originalType, targetType);
		} else {
			throw new RuntimeException("Task states to cast \"" + fieldname + "\" to  semantic " +
					"but the field is not semantic: \n" + field.toString());
		}
		task.getExecution().getEnvironment().put(fieldname, castedField);
		task.setSucceeded();
	}
}
