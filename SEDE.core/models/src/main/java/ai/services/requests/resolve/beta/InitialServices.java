package ai.services.requests.resolve.beta;

import ai.services.core.ServiceInstanceHandle;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class InitialServices extends HashMap<String, ServiceInstanceHandle> {

    public InitialServices() {
    }

    public InitialServices(Map<? extends String, ? extends ServiceInstanceHandle> m) {
        super(m);
    }

}
