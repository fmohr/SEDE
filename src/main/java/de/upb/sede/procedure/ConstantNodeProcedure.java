package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public class ConstantNodeProcedure implements Procedure {
	public Object process(Task task) {
		String constant = (String) task.getAttributes().get("constant");
		constant = constant.substring(1, constant.length() - 1);
		boolean isBool = (boolean) task.getAttributes().get("isBool");
		boolean isNumber = (boolean) task.getAttributes().get("isNumber");
		boolean isNULL = (boolean) task.getAttributes().get("isNULL");
		boolean isString = (boolean) task.getAttributes().get("isString");
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
