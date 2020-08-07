package de.upb.sede.procedure;

import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;
import de.upb.sede.util.Streams;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CollectErrorsProcedure implements Procedure {
	private static final Logger logger = LoggerFactory.getLogger(CollectErrorsProcedure.class);
	@Override
	public void processTask(Task task) {
		String fieldname = task.getAttribute("fieldname");
		List<String> errorFields= task.getAttribute("error_fields");
		process(task, fieldname, errorFields);
	}

	@Override
	public void processFail(Task task) {
		processTask(task);
	}

	private void process(Task task, String fieldname, List<String> errorFields) {
		logger.debug("Collecting all errors of exection `{}` and field `{}`", task.getExecution().getExecutionId(), errorFields);
		JSONObject executorErrorCollection = new JSONObject();
		for(Task otherTasks : task.getExecution().getAllTasks()) {
			if(otherTasks == task) {
				continue;
			}
			Optional<Exception> error = otherTasks.getError();
			if(error.isPresent()){
				String taskDesc = otherTasks.getDescription();
				if(executorErrorCollection.containsKey(taskDesc)) {
					logger.warn("Overwriting error for task={}. Old error={}", taskDesc, executorErrorCollection.get(taskDesc));
				}
				executorErrorCollection.put(taskDesc, Streams.ErrToString(error.get()));
			}
		}
		JSONObject errorCollection = new JSONObject();
		errorCollection.put(fieldname, executorErrorCollection);
		for(String otherErrorCollectionField : errorFields) {
			if(!task.getExecution().getEnvironment().isUnavailable(otherErrorCollectionField) && task.getExecution().getEnvironment().containsKey(otherErrorCollectionField)) {
				Map otherErrorCollection =
						null;
				try {
					ExecutionEnvironment environment = task.getExecution().getEnvironment();
					otherErrorCollection = (Map) task.getExecution().performLater(
							() -> environment.get(otherErrorCollectionField).getDataField()
							).get();
				} catch (InterruptedException | ExecutionException e) {
					logger.warn("Interrupted while collecting errors from: {} ", otherErrorCollection, e);
				}
				errorCollection.putAll(otherErrorCollection);
				logger.debug("Collected errors from: {}", otherErrorCollectionField);
			} else  {
				logger.error("Errorcollection is unavailable: {}", otherErrorCollectionField);

			}
		}

		SEDEObject errorCollectionField = new ObjectDataField("builtin.Dict", errorCollection);
		task.getExecution().performLater( () -> {
			task.getExecution().getEnvironment().put(fieldname, errorCollectionField);
			task.setSucceeded();
		});
	}

}
