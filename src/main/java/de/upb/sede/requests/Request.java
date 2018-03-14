package de.upb.sede.requests;

public abstract class Request {
    public abstract String getRequestId();
    public abstract String getRequestingClientId();
    public abstract String[] getClientHosts();
}