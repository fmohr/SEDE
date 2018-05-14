package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public abstract class Procedure {


	public abstract void process(Task task);


//	public static Procedure procedureForTask(Task task) {
//		String taskName = task.getTaskName();
//		String procedureClassName = taskName + "Procedure";
//		Class<?> procedureClass;
//		try {
//			procedureClass = Class.forName(procedureClassName);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Could not find procedure class for task name: " + taskName);
//		}
//
//		Procedure procedure;
//		try {
//			procedure = (Procedure) procedureClass.getConstructor(Task.class).newInstance(task);
//		} catch (ReflectiveOperationException ex) {
//			throw new RuntimeException("Cannot instantiate procedure: " + procedureClassName);
//		}
//		return procedure;
//	}
}
