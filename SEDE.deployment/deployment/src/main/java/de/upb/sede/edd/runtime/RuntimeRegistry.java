package de.upb.sede.edd.runtime;

import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;

public interface RuntimeRegistry {

    ExecutorDemandFulfillment demand(ExecutorDemandRequest request);

}
