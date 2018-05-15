package de.upb.sede.procedure;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Observer;

public class AcceptDataProcedure extends Procedure {


	@Override
	public void process(Task task) {
		String fieldname = (String) task.getAttributes().get("fieldname");


		Observer<ExecutionEnvironment> envObserver = Observer.<ExecutionEnvironment>lambda(env -> env.containsKey(fieldname),
				env -> task.setSucceeded());

		ExecutionEnvironment environment = task.getExecution().getExecutionEnvironment();
		environment.getState().observe(envObserver);
	}


}
