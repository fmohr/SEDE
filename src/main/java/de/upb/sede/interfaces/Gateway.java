package de.upb.sede.interfaces;

import de.upb.sede.requests.GatewayResolution;
import de.upb.sede.requests.Registration;
import de.upb.sede.requests.Request;

public interface Gateway {
    public GatewayResolution resolve(Request resolveRequest);
    public boolean register(Registration registry);
}