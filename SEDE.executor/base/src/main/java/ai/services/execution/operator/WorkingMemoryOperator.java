package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Operator that can has some working form of working memory stored in each task.
 * This operator can requeue the same task for later execution and store intermediate data into the working memory.
 * @param <T> Type of the working memory.
 */
public abstract class WorkingMemoryOperator<T> extends AbstractOperator {

	private static final Logger logger = LoggerFactory.getLogger(WorkingMemoryOperator.class);

	private final Object workingMemAddress;

	private final Supplier<T> initialMemValue;

	public WorkingMemoryOperator(Object workingMemAddress, Supplier<T> initialMemValue) {
		this.workingMemAddress = workingMemAddress;
		this.initialMemValue = initialMemValue;
	}
    @Override
    public boolean test(Task task) {
		try {
			return test(task, fetchData(task));
		} catch(Exception ex) {
			logger.warn("Performer {} threw exception while testing match of task:\n{}", this.getClass().getSimpleName(),
					task.getNode().getText(), ex);
			return false;
		}
	}

	@Override
    public TaskTransition runTask(Task task) {
		try {
			return runTask(task, fetchData(task));
        } catch (Exception exception) {
			logger.info("Marking task as failed. Error message: {}", exception.getMessage());
			return TaskTransition.error(exception);
		}
	}

    private T fetchData(Task task) throws Exception {
	    try{
            T workingMemVal = task.getWorkingMemVal(workingMemAddress);
            if(workingMemVal == null) {
                workingMemVal = initialMemValue.get();
                task.putWorkingMemVal(workingMemAddress, workingMemVal);
            }
            return workingMemVal;
        } catch (Exception ex) {
            logger.error("Unexpected error trying to get the working memory data of type: " + workingMemAddress.toString(), ex);
            throw ex;
        }
    }

    protected void setMemVal(Task task, T val) {
	    task.putWorkingMemVal(this.workingMemAddress, val);
    }

    abstract boolean test(Task task, T data) throws Exception;

	abstract TaskTransition runTask(Task task, T data) throws Exception;

}
