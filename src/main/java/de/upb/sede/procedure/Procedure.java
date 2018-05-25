package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public interface Procedure {


	public abstract void process(Task task);


	public default void processFail(Task task) {
		// default implementation does nothing
	}
}
