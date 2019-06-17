package de.upb.sede.edd.deploy.target;

import java.util.concurrent.Executor;

public abstract class DefaultTarget implements Target{

    private String displayName;

    private Executor executor;

    private StringBuilder out = new StringBuilder(), err = new StringBuilder();

    public DefaultTarget(String displayName, Executor executor) {
        this.executor = executor;
        this.displayName = displayName;
        if(displayName == null) {
            displayName = "Target";
        }
    }

    public DefaultTarget(Executor executor) {
        this(null,executor);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Executor getExecutor() {
        return executor;
    }

    public StringBuilder getOut() {
        return out;
    }

    public StringBuilder getErr() {
        return err;
    }
}
