package de.upb.sede.procedure;

import de.upb.sede.exceptions.DependecyTaskFailed;
import de.upb.sede.exceptions.ErrorInProcedureException;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Task;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

/*
 * Procedure to parse a primitive type into a constant type and to put it into the environment.
 * The name for the SEDEObject of the constant is the constant itself.
 */
public class ParseConstantProcedure implements Procedure {

	@SuppressWarnings("unchecked")
	public void processTask(Task task)
	{
		String constant = (String) task.getAttributes().get("constant");

		SEDEObject.PrimitiveType type = typeFromAttributes((Map<String, Boolean>) task.getAttributes());

		SEDEObject sedeObject = parsePrimitive(constant, type);

		ExecutionEnvironment environment = task.getExecution().getEnvironment();

		environment.put(constant, sedeObject);

		task.setSucceeded();
	}

	private static SEDEObject.PrimitiveType typeFromAttributes(Map<String, Boolean> attributes) {
		if (attributes.get("isBool")) {
			return SEDEObject.PrimitiveType.Bool;
		}
		if (attributes.get("isNumber")) {
			return SEDEObject.PrimitiveType.Number;
		}
		if (attributes.get("isNULL")) {
			return SEDEObject.PrimitiveType.NULL;
		}
		if (attributes.get("isString")) {
			return SEDEObject.PrimitiveType.String;
		}
		throw new ErrorInProcedureException("None of the flags in the constant node were true.");
	}

	public static SEDEObject parsePrimitive(String constantStr, SEDEObject.PrimitiveType primitiveType) {
		Object data;
		switch (Objects.requireNonNull(primitiveType)) {
		case NULL:
			data = null;
			break;
		case Bool:
			data = Boolean.valueOf(constantStr);
			break;
		case Number:
			try {
				data = NumberFormat.getInstance().parse(constantStr);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			break;
		case String:
			data = constantStr.substring(1, constantStr.length() - 1);
			break;
		default:
			throw new RuntimeException("All cases covered. " + "Default to have data initialized.");
		}
		return new SEDEObject(primitiveType.name(), data);
	}
}
