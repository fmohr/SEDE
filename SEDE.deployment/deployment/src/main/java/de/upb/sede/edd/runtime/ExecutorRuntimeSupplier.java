package de.upb.sede.edd.runtime;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.edd.deploy.target.JExecutorTarget;

import java.util.HashMap;
import java.util.Map;

public class ExecutorRuntimeSupplier {

    private Map<String, ExecutorRuntime> runtimes = new HashMap<>();

    private EDD edd;
    private SpecSource specSource;

    public ExecutorRuntimeSupplier(EDD edd, SpecSource source) {
        this.edd = edd;
        this.specSource = source;
    }

    public ExecutorRuntime getRuntime(String name) {
        return runtimes.computeIfAbsent(name, n -> new ExecutorRuntime(edd, specSource));
    }

    public JExecutorTarget getJavaExecutor(String name) {
        return getRuntime(name).getTarget();
    }

    public Map<String, ExecutorRuntime> getRuntimes() {
        return runtimes;
    }
}
