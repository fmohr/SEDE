package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;

public interface SpecSource extends Cache<DeploymentSpecificationRegistry> {

    String getServiceNamespace();

    boolean equals(Object o);
}
