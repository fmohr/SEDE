package de.upb.sede.composition;

import de.upb.sede.gateway.ExecutorHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class schedules executors per round robin.
 */
public class RoundRobinScheduler {
	private final static Logger logger = LoggerFactory.getLogger(RoundRobinScheduler.class);

	private Map<String, ExecutorAccessMonitor> executorAccess =
			new HashMap<>();

	public synchronized void updateExecutors(Collection<ExecutorHandle> updatedList) {
		Iterator<String> iterator = executorAccess.keySet().iterator();
		/*
		 * Iterate over all executors and update handles and remove unretained executors.
		 */
		while(iterator.hasNext()) {
			String executorId = iterator.next();
			ExecutorAccessMonitor executor = executorAccess.get(executorId);
			Optional<ExecutorHandle> updatedHandle = Optional.empty();
			/*
			 * Find the executor in the updated list
			 */
			for (ExecutorHandle executorHandle : updatedList) {
				if (executorId.equals(executorHandle.getExecutorId())) {
					updatedHandle = Optional.of(executorHandle);
					break;
				}
			}
			logger.info("Kicking `{}` out of Round robin scheduler.", executor);
			iterator.remove();
		}
		/*
		 * Add new executor handles
		 */
		for (ExecutorHandle executorHandle : updatedList) {
			if(!executorAccess.keySet().contains(executorHandle.getExecutorId())) {
				ExecutorAccessMonitor executorAccessMonitor = new ExecutorAccessMonitor(executorHandle.getExecutorId());
				executorAccess.put(executorHandle.getExecutorId(), executorAccessMonitor);
				logger.info("Added new executor to Round Robing scheduling `{}`.", executorAccessMonitor);
			}
		}
	}

	public synchronized String scheduleNextAmong(List<ExecutorHandle> candidates) {
		if(candidates.isEmpty()) {
			throw new IllegalArgumentException("Cannot decide between 0 candidates!");
		}
		Optional<ExecutorAccessMonitor> monitor = candidates.stream().map(handle -> executorAccess.get(handle.getExecutorId())).sorted().findFirst();
		if(monitor.isPresent()) {
			return monitor.get().access();
		} else {
			throw new IllegalArgumentException(String.format("None of the executor candidates are found in the access map of the round robin scheduler." +
					"\nCandidates: %s" +
					"\n%s",
					candidates.toString(), this.toString()));
		}
	}

	public synchronized String toString() {
		StringBuilder builder = new StringBuilder("Round-robin table:");
		for(ExecutorAccessMonitor monitor : executorAccess.values()) {
			builder.append("\n\t").append(monitor);
		}
		return builder.toString();
	}

	public synchronized void reset() {
		for(ExecutorAccessMonitor monitor : executorAccess.values()) {
			monitor.reset();
		}
	}

	static class ExecutorAccessMonitor implements Comparable<ExecutorAccessMonitor> {

		String executorId;
		long accessCount = 0;

		public ExecutorAccessMonitor(String executorId) {
			this.executorId = executorId;
		}

		String access() {
			accessCount++;
			return executorId;
		}


		public String toString() {
			return "id=" + executorId + "\t\t accesses=" + accessCount;
		}

		@Override
		public int compareTo(ExecutorAccessMonitor o) {
			return Long.compare(accessCount, o.accessCount);
		}

		public void reset() {
			accessCount = 0;
		}
	}
}
