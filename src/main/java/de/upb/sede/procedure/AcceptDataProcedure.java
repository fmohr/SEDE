package de.upb.sede.procedure;

import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Observer;

public class AcceptDataProcedure implements Procedure {

	@Override
	public void process(Task task) {
		final String fieldname = (String) task.getAttributes().get("fieldname");
		Observer<ExecutionEnvironment> envObserver = Observer
				.<ExecutionEnvironment>lambda(env -> env.containsKey(fieldname), env -> task.setSucceeded());

		ExecutionEnvironment environment = task.getExecution().getEnvironment();
		environment.getState().observe(envObserver);
	}

}
