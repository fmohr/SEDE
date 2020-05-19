package de.upb.sede.gateway;

import de.upb.sede.composition.RoundRobinScheduler;
import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.gateway.edd.CachedExecutorHandleSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Offers synchronized access to executor handles.
 *
 * @author aminfaez
 *
 */
public class ExecutorSupplyCoordinator { // TODO let this class implement OnDemandExecutorSupplier

    private final static Logger logger = LoggerFactory.getLogger(ExecutorSupplyCoordinator.class);

	/*
	 * The scheduler:
	 */
	private final RoundRobinScheduler scheduler = new RoundRobinScheduler();

	private final List<OnDemandExecutorSupplier> executorSuppliers = new ArrayList<>();

	public synchronized List<IExecutorHandle> supplyExecutor(String service) {
		List<IExecutorHandle> capableExecutors = new ArrayList<>();
		for (OnDemandExecutorSupplier executorSupplier : executorSuppliers) {
			if (executorSupplier.isSupported(service)) {
				try {
                    IExecutorHandle handle = executorSupplier.supply(service);
                    capableExecutors.add(handle);
                } catch(UnsupportedOperationException ex) {
                    logger.warn("Executor supplier {} didn't supply executors for the demanded service {}.", executorSupplier, service, ex);
                }
			}
		}
		return capableExecutors;
	}

	public synchronized IExecutorHandle executorsWithServiceClass(String ServiceClass) {
		List<IExecutorHandle> executors = supplyExecutor(ServiceClass);
		if(executors.isEmpty()) {
			throw new RuntimeException("No registered executor supports the given class: " + ServiceClass);
		}
		int randomIndex = (int) (executors.size() * Math.random());
		return executors.get(randomIndex);
	}

	public synchronized boolean hasExecutor(String id) {
		return executorSuppliers.stream().anyMatch(h -> h.getIdentifier().equals(id));
	}

	public synchronized IExecutorHandle getExecutorFor(String id) {
        Optional<IExecutorHandle> any = executorSuppliers.stream()
            .map(supplier -> supplier.getHandle(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findAny();

        return any.<RuntimeException>orElseThrow(() -> {
            throw new RuntimeException("No executor found for id=`" + id + "`. First query hasExecutor before retrieving it.");
        });
	}

//	synchronized void addExecutor(ExecutorHandle eh) {
//
//		this.executors.add(Objects.requireNonNull(eh));
//		scheduler.updateExecutors(executors);
//	}

	List<IExecutorHandle> getExecutors() {
	    return executorSuppliers.stream()
            .map(OnDemandExecutorSupplier::allHandles)
            .flatMap(Collection::stream) // merge lists
            .collect(Collectors.toList());
	}

	public synchronized void removeExecutor(String executorId) {
        executorSuppliers.removeIf(supplier -> supplier.getIdentifier().equals(executorId));
	}

	public ExecutorHandle scheduleNextAmong(List<ExecutorHandle> candidates) {
		String id = scheduler.scheduleNextAmong(candidates);
		for (ExecutorHandle executor : candidates) {
			if (executor.getContactInfo().getQualifier().equals(id)) {
				return executor;
			}
		}
		throw new RuntimeException("No executor found for for id=`\" + id + \"`.");
	}

	public RoundRobinScheduler getScheduler() {
		return scheduler;
	}

    public synchronized void addSupplier(OnDemandExecutorSupplier supplier) {
        executorSuppliers.add(supplier);
    }
}
