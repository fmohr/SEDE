package de.upb.sede.edd;

import de.upb.sede.util.KneadForm;
import de.upb.sede.util.Kneadable;

public class JavaBase implements ExecutorDeploymentBase{

    private final JavaExecutorConfiguration configuration;
    private final ExecutorState state;

    public JavaBase(ExecutorState state) {
        this.configuration = state.getConfiguration().knead(JavaExecutorConfiguration.class);
        this.state = state;
    }

    public ExecutorState createState(Kneadable configuration) {
        ExecutorState state = ExecutorState.initialize(configuration, ExecutorState.ExecutorBaseType.JAVA);
        return state;
    }

    private class JavaExecutorConfiguration extends KneadForm {

    }
}
