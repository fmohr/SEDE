package de.upb.sede.edd;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.upb.sede.util.DynType;

public class ExecutorState {

    private final DynType configuration;

    private ExecutorBaseType base;

    private ExecutorLifecycle lifecycle;

    @JsonCreator
    public ExecutorState(DynType configuration, ExecutorBaseType base, ExecutorLifecycle lifecycle) {
        this.configuration = configuration;
        this.base = base;
        this.lifecycle = lifecycle;
    }

    public static ExecutorState initialize(DynType configuration, ExecutorBaseType base) {
        return new ExecutorState(configuration, base, ExecutorLifecycle.INITIALIZED);
    }

    public enum ExecutorBaseType {
        JAVA,
        PYTHON
    }

    public DynType getConfiguration() {
        return configuration;
    }

    public ExecutorBaseType getBase() {
        return base;
    }

    public ExecutorLifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(ExecutorLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }


}
