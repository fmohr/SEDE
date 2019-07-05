package de.upb.sede.edd;

import de.upb.sede.util.DynType;

public class JavaExecutorHandle  {

    private final JavaExecutorConfiguration configuration;
    private final ExecutorState state;

    public JavaExecutorHandle(ExecutorState state) {
        this.configuration = state.getConfiguration().cast(JavaExecutorConfiguration.class);
        this.state = state;
    }

    public ExecutorState createState(DynType configuration) {
        ExecutorState state = ExecutorState.initialize(configuration, ExecutorState.ExecutorBaseType.JAVA);
        return state;
    }

    private class JavaExecutorConfiguration {

    }
}
