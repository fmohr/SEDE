package de.upb.sede.procedure;

import de.upb.sede.exec.Task;

public interface Procedure {

	void processTask(Task task);

	default void processFail(Task task) {}

}
