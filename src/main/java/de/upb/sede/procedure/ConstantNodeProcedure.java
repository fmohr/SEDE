package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public class ConstantNodeProcedure implements Procedure {
	public Object process(Task task) {
		String constant = (String) task.getParameters().get("constant");
		constant = constant.substring(1, constant.length() - 1);
		boolean isBool = (boolean) task.getParameters().get("isBool");
		boolean isNumber = (boolean) task.getParameters().get("isNumber");
		boolean isNULL = (boolean) task.getParameters().get("isNULL");
		boolean isString = (boolean) task.getParameters().get("isString");
		if (isBool) {
			return (constant == "True") ? true : false;
		} else if (isNumber) {
			return Double.parseDouble(constant);
		} else if (isNULL) {
			return null;
		} else if (isString) {
			return constant;
		} else
			return null;
	}
}
