package de.upb.sede.procedure;

import de.upb.sede.exceptions.ErrorInProcedureException;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

public class ParseConstantProcedure implements Procedure {

	@SuppressWarnings("unchecked")
	public void process(Task task) {
		String constant = (String) task.getAttributes().get("constant");
		SEDEObject.PrimitiveType type = typeFromAttributes((Map<String, Boolean>)task.getAttributes());

		Object object;
		if (isBool) {
			object = new Boolean(constant);
			type = "bool";
		} else if (isNumber) {
			object = Double.parseDouble(constant);
			type = "number";
		} else if (isNULL) {
			object = null;
			type = "null";
		} else if (isString) {
			object = constant;
			type = "string";
		} else {
			throw new ErrorInProcedureException(
					"None of the flags in the constant node:\"" + constant + "\"were true.");
		}
		SEDEObject sedeObject = parsePrimitive(constant, type);

		ExecutionEnvironment environment = task.getExecution().getExecutionEnvironment();
		environment.put(constant, sedeObject);
	}
	private static SEDEObject.PrimitiveType typeFromAttributes(Map<String, Boolean> attributes){
		if(attributes.get("isBool")){
			return SEDEObject.PrimitiveType.Bool;
		}
		if(attributes.get("isNumber")){
			return SEDEObject.PrimitiveType.Number;
		}
		if(attributes.get("isNULL")){
			return SEDEObject.PrimitiveType.NULL;
		}
		if(attributes.get("isString")){
			return SEDEObject.PrimitiveType.String;
		}
		throw new ErrorInProcedureException(
				"None of the flags in the constant node were true.");
	}

	public static SEDEObject parsePrimitive(String constantStr, SEDEObject.PrimitiveType primitiveType) {
		Object data;
		switch (primitiveType){
			case NULL: data = null; break;
			case Bool: data = Boolean.valueOf(constantStr); break;
			case Number:
				try {
					data = NumberFormat.getInstance().parse(constantStr);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
		}
//		constant = constant.substring(1, constant.length() - 1);

	}
}
