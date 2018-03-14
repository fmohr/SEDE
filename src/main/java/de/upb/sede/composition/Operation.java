package de.upb.sede.composition;

public abstract class Operation {
    public abstract String getLeftSideField();
    public abstract String getHost();
    public abstract String getService();
    public abstract String getMethod();
    public abstract String[] getInputsFields();
}