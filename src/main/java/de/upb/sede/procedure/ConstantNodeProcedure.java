package de.upb.sede.procedure;

import de.upb.sede.exceptions.ErrorInProcedureException;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.SEDEObject;
import de.upb.sede.exec.Task;

public class ConstantNodeProcedure implements Procedure {
	public void process(Task task) {
		ExecutionEnvironment environment = task.getExecution().getExecutionEnvironment();
		String constant = (String) task.getAttributes().get("constant");
		constant = constant.substring(1, constant.length() - 1);
		boolean isBool = (boolean) task.getAttributes().get("isBool");
		boolean isNumber = (boolean) task.getAttributes().get("isNumber");
		boolean isNULL = (boolean) task.getAttributes().get("isNULL");
		boolean isString = (boolean) task.getAttributes().get("isString");
		Object object;
		String type;
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
		SEDEObject sedeObject = new SEDEObject(type, object);
		environment.put(constant, sedeObject);
	}
}
