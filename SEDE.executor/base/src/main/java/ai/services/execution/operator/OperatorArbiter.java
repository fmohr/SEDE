package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collects a group of tasks and delegates invocations to them.
 * Operators can be added with priority.
 */
public class OperatorArbiter implements TaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(OperatorArbiter.class);

    private static final int MIN_PRIO = -99;
    private static final int MAX_PRIO = 99;

    private int numOperators;

    private final TreeMap<Integer, Set<TaskOperator>> operators = new TreeMap<>(Comparator.reverseOrder());

    public OperatorArbiter() {
        addOperator(-100, new MainTaskFinisherOp());
    }

    public void addMainOperator(TaskOperator operator) {
        addOperator(0, operator);
    }

    public void addPostOperator(TaskOperator operator) {
        addOperator(-10, operator);
    }

    public void addPreOperator(TaskOperator operator) {
        addOperator(10, operator);
    }

    public void addOperator(int priority, TaskOperator operator) {
        if(!(operator instanceof MainTaskFinisherOp) && priority < MIN_PRIO) {
            throw new IllegalArgumentException("Priority of Operator " + operator.getClass().getSimpleName()
                    + " is too small: " + priority+ "\n Min prio is: " + MIN_PRIO);
        } else if(priority > MAX_PRIO) {
            throw new IllegalArgumentException("Priority of Operator "
                    + operator.getClass().getSimpleName() + " is too large: " + priority+ "\n Max prio is: " + MAX_PRIO);
        }
        Optional<Set<TaskOperator>> any = operators.values().stream().filter(set -> set.contains(operator)).findAny();
        if (any.isPresent()) {
            logAlreadyAdded(operator);
            any.get().remove(operator);
        } else {
            numOperators++;
        }
        boolean added = operators.computeIfAbsent(priority, p -> new HashSet<>()).add(operator);
        if (!added) {
            throw new IllegalStateException("This indicates a bug. The operator is said to be present, "
                    + "although it should have been removed before.");
        }
    }

    private void logAlreadyAdded(TaskOperator operator) {
        logger.warn("The operator {} was already added to the arbiter.", operator.getClass().getSimpleName());

    }

    private int numPerforms() {
        assert numOperators == operators.values().stream().mapToInt(Set::size).sum();
        return numOperators;
    }

    @Override
    public TaskTransition apply(Task task) {
        TaskOperator op = getMatchingOperator(task);
        logger.debug("Applying operation {} for task `{}`.", op.getClass().getSimpleName(), task);
        return op.apply(task);
    }

    private TaskOperator getMatchingOperator(Task task) {
        for (Map.Entry<Integer, Set<TaskOperator>> prioOpEntry : operators.entrySet()) {
            int prio = prioOpEntry.getKey();
            Set<TaskOperator> ops = prioOpEntry.getValue();

            List<TaskOperator> matchingOperators = ops.stream()
                    .filter(op -> op.test(task))
                    .collect(Collectors.toList());

            if (logger.isTraceEnabled() && matchingOperators.isEmpty()) {
                logger.trace("No operator with prio {} among {} many operators for task `{}`.",
                        prio, ops.size(), task);
            }
            if (matchingOperators.size() == 1) {
                return matchingOperators.get(0);
            } else if (matchingOperators.size() > 1) {
                logger.warn("{} many operators in prio {} match task `{}`:\n{}\nReturning the first found: {}",
                        matchingOperators.size(), prio, task,
                        matchingOperators.stream().map(op -> op.getClass().getName()).collect(Collectors.joining("\n")),
                        matchingOperators.get(0).getClass().getSimpleName());

                return matchingOperators.get(0);
            }
        }
        return new MainTaskFinisherOp();
//        throw new IllegalStateException("BUG this shouldn't have been reached, No operator found for task " + task);
    }


    @Override
    public boolean test(Task task) {
        return true;
    }

}
