package de.upb.sede.composition;

import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.util.Cache;
import de.upb.sede.util.ExpiringCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class schedules executors per round robin.
 */
public class RoundRobinScheduler {
	private final static Logger logger = LoggerFactory.getLogger(RoundRobinScheduler.class);

	private Map<String, ExpiringCache<ExecutorAccessMonitor>> executorAccess =
			new HashMap<>();

//	public synchronized void updateExecutors(Collection<ExecutorHandle> updatedList) {
//		Iterator<String> iterator = executorAccess.keySet().iterator();
//		/*
//		 * Iterate over all executors and update handles and remove unretained executors.
//		 */
//		while(iterator.hasNext()) {
//			String executorId = iterator.next();
//			ExecutorAccessMonitor executor = executorAccess.get(executorId);
//			Optional<ExecutorHandle> updatedHandle = Optional.empty();
//			/*
//			 * Find the executor in the updated list
//			 */
//			for (ExecutorHandle executorHandle : updatedList) {
//				if (executorId.equals(executorHandle.getExecutorId())) {
//					updatedHandle = Optional.of(executorHandle);
//					break;
//				}
//			}
//			logger.info("Kicking `{}` out of Round robin scheduler.", executor);
//			iterator.remove();
//		}
//		/*
//		 * Add new executor handles
//		 */
//		for (ExecutorHandle executorHandle : updatedList) {
//			if(!executorAccess.keySet().contains(executorHandle.getExecutorId())) {
//				ExecutorAccessMonitor executorAccessMonitor = new ExecutorAccessMonitor(executorHandle.getExecutorId());
//				executorAccess.put(executorHandle.getExecutorId(), executorAccessMonitor);
//				logger.info("Added new executor to Round Robing scheduling `{}`.", executorAccessMonitor);
//			}
//		}
//	}

	public synchronized String scheduleNextAmong(List<ExecutorHandle> candidates) {
		if(candidates.isEmpty()) {
			throw new IllegalArgumentException("Cannot decide between 0 candidates!");
		}

		ExecutorAccessMonitor highestPriority = null;
		for(ExecutorHandle handle : candidates) {
			ExecutorAccessMonitor monitor;
            ExpiringCache<ExecutorAccessMonitor> monitorCache = executorAccess.get(handle.getExecutorId());

            if(monitorCache == null || monitorCache.isExpired()) {
                monitor = new ExecutorAccessMonitor(handle.getExecutorId());
                monitorCache = new ExpiringCache<ExecutorAccessMonitor>(10, TimeUnit.MINUTES, monitor);
                executorAccess.put(handle.getExecutorId(), monitorCache);
            } else {
                monitorCache.prolong();
                monitor = monitorCache.forceAccess();
            }

            if (highestPriority == null) {
				highestPriority = monitor;
			} else {
				if(monitor.compareTo(highestPriority) < 0) {
					highestPriority = monitor;
				}
			}
		}
		if(highestPriority != null) {
			return highestPriority.access();
		} else {
			throw new IllegalArgumentException(String.format("None of the executor candidates are found in the access map of the round robin scheduler." +
					"\nCandidates: %s" +
					"\n%s",
					candidates.stream().map(ExecutorHandle::getExecutorId).collect(Collectors.joining(", ")),
					this.toString()));
		}
	}

	public synchronized String toString() {
		StringBuilder builder = new StringBuilder("Round-robin table:");
		for(ExpiringCache<ExecutorAccessMonitor> monitorCache : executorAccess.values()) {
		    if(!monitorCache.isExpired()) {
                builder.append("\n\t").append(monitorCache.forceAccess());
            }
		}
		return builder.toString();
	}

	public synchronized void clean() {
        Iterator<String> iterator = executorAccess.keySet().iterator();
        while(iterator.hasNext()) {
            ExpiringCache<ExecutorAccessMonitor> cache = executorAccess.get(iterator.next());
            if(cache.isExpired()) {
                iterator.remove();
            }
        }
    }

	public synchronized void reset() {
	    clean();
		for(ExpiringCache<ExecutorAccessMonitor> monitorCache : executorAccess.values()) {
		    if(!monitorCache.isExpired()) {
		        monitorCache.forceAccess().reset();
            }
		}
	}

	static class ExecutorAccessMonitor implements Comparable<ExecutorAccessMonitor>, Cache<String> {

		String executorId;
		long accessCount = 0;

		public ExecutorAccessMonitor(String executorId) {
			this.executorId = executorId;
		}

		public String access() {
			accessCount++;
			return executorId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o instanceof String) return executorId.equals(o);
			if (o == null || getClass() != o.getClass()) return false;
			ExecutorAccessMonitor monitor = (ExecutorAccessMonitor) o;
			return executorId.equals(monitor.executorId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(executorId);
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
