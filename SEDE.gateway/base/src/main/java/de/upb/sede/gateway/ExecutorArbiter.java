package de.upb.sede.gateway;

import de.upb.sede.composition.RoundRobinScheduler;
import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.exec.IExecutorHandle;
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
public class ExecutorArbiter { // TODO let this class implement OnDemandExecutorSupplier

    private static final Logger logger = LoggerFactory.getLogger(ExecutorArbiter.class);

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
                    List<IExecutorHandle> handles = executorSupplier.supplyWithService(service);
                    capableExecutors.addAll(handles);
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
            .map(supplier -> supplier.supplyWithExecutorId(id))
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


	public synchronized void removeExecutor(String executorId) {
        executorSuppliers.removeIf(supplier -> supplier.getIdentifier().equals(executorId));
	}

	public ExecutorHandle scheduleNextAmong(List<ExecutorHandle> candidates) {
		String id = scheduler.scheduleNextAmong(candidates.stream().map(ExecutorHandle::getQualifier).collect(Collectors.toList()));
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
    public synchronized OnDemandExecutorSupplier supplier() {
	    return new OnDemandExecutorSupplier() {

            @Override
            public boolean isSupported(String service) {
                return executorSuppliers.stream().anyMatch(es -> es.isSupported(service));
            }

            @Override
            public List<IExecutorHandle> supplyWithService(String service) {
                return supplyExecutor(service);
            }


            @Override
            public List<String> supportedServices() {
                return executorSuppliers.stream()
                    .map(OnDemandExecutorSupplier::supportedServices)
                    .reduce(new ArrayList<>(), (a1, a2) -> { a1.addAll(a2); return a1;})
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
            }

            @Override
            public String getIdentifier() {
                throw new RuntimeException();
            }


            @Override
            public Optional<IExecutorHandle> supplyWithExecutorId(String executorId) {
                return executorSuppliers.stream()
                    .map(es -> es.supplyWithExecutorId(executorId))
                    .filter(opt -> opt.isPresent())
                    .map(opt -> opt.get())
                    .findAny();
            }
        };
    }
}
