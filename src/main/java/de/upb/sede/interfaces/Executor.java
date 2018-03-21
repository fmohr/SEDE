package de.upb.sede.interfaces;

import de.upb.sede.requests.Request;

public interface Executor {
    public void put(Request dataPutRequest);
    public void exec(Request execRequest);

    public void loadServices(Object Services);
}