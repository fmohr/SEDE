package de.upb.sede.edd.runtime;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import de.upb.sede.edd.process.ProcessHandle;
import de.upb.sede.edd.process.ProcessHandleState;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.upb.sede.requests.deploy.ExecutorDemandRequest.SatMode.*;

public class LocalRuntimeRegistry implements RuntimeRegistry {

    private Timer runtimeKiller = new Timer(true);

    private List<ExecutorRuntime> runtimeList = new ArrayList<>();

    private EDD edd;

    public LocalRuntimeRegistry(EDD edd) {
        this.edd = edd;
    }

    public synchronized ExecutorDemandFulfillment demand(ExecutorDemandRequest request) {
        ExecutorDemandFulfillment fulfillment = new ExecutorDemandFulfillment();
        if(request.isSet(Spawn)) {
            fulfillment.add(spwan(request));
        }
        if(request.isSet(Reuse)) {
            fulfillment.add(reuse(request));
        }
        if(request.isSet(AllAvailable)) {
                ExecutorDemandFulfillment all = new ExecutorDemandFulfillment(
                    getAllRunning()
                    .filter(er ->
                        er.getSource().getServiceNamespace().equals(request.getServiceNamespace())
                    )
                    .map(er -> er.getTarget().getRegistration())
                    .collect(Collectors.toList()));
                fulfillment.add(all);
        }
        return fulfillment;
    }

    public synchronized Stream<ExecutorRuntime> getAllRunning() {
        return runtimeList.stream()
            .filter(er -> !er.getTimeToLive().isExpired())
            .filter(er -> {
                Optional<ProcessHandle> javaProcessHandle = er.getTarget().getExecutorProcess().getJavaProcessHandle();
                return javaProcessHandle.filter(processHandle -> (processHandle.getState() == ProcessHandleState.STARTED)).isPresent();
            });
    }

    public ExecutorDemandFulfillment spwan(ExecutorDemandRequest request) {
        if(request.getServices().isEmpty()) {
            return new ExecutorDemandFulfillment(Collections.EMPTY_LIST);
        }

        ExecutorDemandFulfillment allSpawned = new ExecutorDemandFulfillment();

        for(DeplEngine engine : edd.getDeploymentEngine().getEngines()) {
            ExecutorDemandFulfillment spawned = engine.demand(request);
            allSpawned.add(spawned);
        }
        return allSpawned;
    }

    public ExecutorDemandFulfillment reuse(ExecutorDemandRequest request) {
        if(request.getServices().isEmpty()) {
            return new ExecutorDemandFulfillment(Collections.EMPTY_LIST);
        }

        ExecutorDemandFulfillment reused = new ExecutorDemandFulfillment(getAllRunning()
            .filter(er ->
                er.getSource().getServiceNamespace().equals(request.getServiceNamespace())
            )
            .filter(er ->
                er.getTarget().getExecutorConfig().getServices().stream().anyMatch(request.getServices()::contains)
            )
            .peek(this::accessRuntime)
            .map(er -> er.getTarget().getRegistration())
            .collect(Collectors.toList()));
        return reused;
    }

    public void addExecutorRuntime(ExecutorRuntime runtime) {
        if(!runtimeList.contains(runtime)) {
            runtimeList.add(runtime);
        }
        accessRuntime(runtime);
    }

    public synchronized void accessRuntime(ExecutorRuntime runtime) {
        if(runtime.getTimeToLive().isExpired()) {
            return;
        } else {
            TimerTask killTask = runtime.getTimeToLive().access();
            if(killTask != null) {
                killTask.cancel();
            }
            runtime.getTimeToLive().prolong();
            long delayMillis = (runtime.getTimeToLive().getExpirationTimeMillis().get() - System.currentTimeMillis()) + 1000;
            TimerTask newKillTask = new TimerTask() {
                @Override
                public void run() {
                    runtime.getExecutorService().shutdownNow();
//                    this.cancel();
                }
            };
            runtime.getTimeToLive().set(newKillTask);
            runtimeKiller.schedule(newKillTask, delayMillis);
        }
    }

    public synchronized List<ExecutorRuntime> getRuntimeList() {
        return runtimeList;
    }



}
