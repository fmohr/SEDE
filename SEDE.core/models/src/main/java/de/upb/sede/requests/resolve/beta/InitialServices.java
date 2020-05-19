package de.upb.sede.requests.resolve;

import de.upb.sede.core.ServiceInstanceHandle;
import java.util.HashMap;
import java.util.Map;

public class InitialServices extends HashMap<String, ServiceInstanceHandle> {

    public InitialServices() {
    }

    public InitialServices(Map<? extends String, ? extends ServiceInstanceHandle> m) {
        super(m);
    }

}
