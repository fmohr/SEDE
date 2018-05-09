package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public abstract class Procedure implements Runnable{

	private Task task;

	public Procedure(Task task){
		this.task = task;
	}

	abstract void process(Task task);

	public void run() {
		task.setStarted();
		try{
			this.process(task);
		} catch(Exception ex) {
			task.setError(ex);
			task.failed();
		}
		finally {
			task.isDoneRunning();
		}
	}

	private Procedure procedureForTask(Task task) {
		String taskName = task.getTaskName();
		String procedureClassName = taskName + "Procedure";
		Class<?> procedureClass;
		try {
			procedureClass = Class.forName(procedureClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find procedure class for task name: " + taskName);
		}

		Procedure procedure;
		try {
			procedure = (Procedure) procedureClass.getConstructor().newInstance();
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException("Cannot instantiate procedure: " + procedureClassName);
		}
		return procedure;
	}
}
