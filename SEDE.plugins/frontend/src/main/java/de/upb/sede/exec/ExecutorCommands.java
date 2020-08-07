package de.upb.sede.exec;

import static de.upb.sede.webinterfaces.server.CommandTree.lastMatch;
import static de.upb.sede.webinterfaces.server.CommandTree.node;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.webinterfaces.server.Command;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;

public class ExecutorCommands {
	private static final Logger logger = LoggerFactory.getLogger(ExecutorCommands.class);
	private final Executor executor;

	public static ExecutorCommands enablePlugin(Executor executor, ServerCommandListeners scl){
		return new ExecutorCommands(executor, scl);
	}

	public ExecutorCommands(Executor executor, ServerCommandListeners scl){
		this.executor = executor;
		CommandTree services = new CommandTree(
				node(new Command.Strings(false, "services"),
						node(new Command.Strings("ls").addExe(t->this.listServices()))
				)
		);

		CommandTree heartbeat = new CommandTree(node(
				new Command.Strings("heartbeat").addExe(t-> heartbeat())));

		CommandTree execution = new CommandTree(
				node(new Command.Strings(false, "executions"),
						node(new Command.Strings("info"),
								node(new Command.Strings(false, "all").addExe(t->this.executionsInfo())),
								node(new Command.Token().addExe(t->this.executionInfo(lastMatch(t))))),
						node(new Command.Strings("interrupt"),
								node(new Command.Strings(false, "all").addExe(t->this.interruptAllExecutions())),
								node(new Command.Token().addExe(t->this.interruptExecution(lastMatch(t))))),
						node(new Command.Strings("ls").addExe(t -> this.executionList()))
				)
		);

		scl.addCommandHandle(services);
		scl.addCommandHandle(heartbeat);
		scl.addCommandHandle(execution);
	}

	private String executionList() {
		StringBuilder outputString = new StringBuilder();
		executor.getExecPool().forAll(execution -> {
			outputString.append(execution.getExecutionId()).append("\n");
		});
		return outputString.toString();
	}


	private String executionsInfo() {
		StringBuilder outputString = new StringBuilder();
		outputString.append("Info of all running executions:");
		executor.getExecPool().forAll(execution -> {
			addExecutionInfo(outputString, "\t", execution);
			outputString.append("\n");
		});
		return outputString.toString();
	}

	private String executionInfo(String executionId) {
		StringBuilder outputString = new StringBuilder();
		Execution execution = executor.getExecPool().getExecution(executionId).orElse(null);
		if(execution == null) {
			outputString.append(String.format("Execution with id `%s` not found.", executionId));
		} else {
			addExecutionInfo(outputString, "", execution);
		}
		return outputString.toString();
	}

	private void addExecutionInfo(StringBuilder outputString, String indent, Execution execution) {
		outputString.append(indent).append("execution id: ").append(execution.getExecutionId());
		outputString.append("\n").append(indent).append("\thas started: ").append(execution.hasStarted());
		outputString.append("\n").append(indent).append("\tunfinished tasks: ").append(execution.getUnfinishedTasksCount());
		execution.forEachTask(task -> {
			if(task.hasNotFinished()) {
				outputString.append("\n").append(indent)
						.append("\t\t - task ");
				if(task.isWaiting())
					outputString.append(" - WAITING: ");
				else if(task.isRunning())
					outputString.append(" - RUNNING: ");
				else if(task.isDoneRunning())
					outputString.append(" - FINISHED: ");
				outputString.append("\n").append(indent).append("\t\t\t ");
				outputString.append(task.getDescription());
			}
		});
		synchronized (execution.getEnvironment()) {

		}
		outputString.append("\n").append(indent).append("\tfield environment: ");
		synchronized (execution.getEnvironment()) {
			for (String field : execution.getEnvironment().keySet()) {
				outputString.append("\n").append(indent).append("\t\t - field: ").append(field).append(": ")
						.append(execution.getEnvironment().isUnavailable(field) ?
									"unavailable": execution.getEnvironment().get(field).getType());
			}
		}
	}

	public String interruptExecution(String executionId) {
		executor.interrupt(executionId);
		return "Send interrupt signal to execution with id: " + executionId;
	}

	public String interruptAllExecutions() {
		executor.interruptAll();
		return "Send interrupt signal to all executions";
	}


	public String listServices() {
		ExecutorConfiguration configuration = executor.getExecutorConfiguration();
		String supportedServices = configuration.getSupportedServices().stream().sorted()
				.collect(Collectors.joining("\n"));
		return supportedServices;
	}


	public String heartbeat() {
		logger.debug("Someone querried if this executor is still alive.");
		return "true";
	}
}
