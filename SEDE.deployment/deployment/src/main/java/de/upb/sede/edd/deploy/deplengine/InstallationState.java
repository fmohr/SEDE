package de.upb.sede.edd.deploy.deplengine;

public enum InstallationState {
    Init(false), Success(true), Error(true);

    boolean finished;

    InstallationState(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
