package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import de.upb.sede.util.TTLCache;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class SpecSourceFromURI implements SpecSource{


    private SpecURI specURI;

    protected Cache<DeploymentSpecificationRegistry> deploymentRegistry;

    public SpecSourceFromURI(SpecURI specURI) {
        this.specURI = Objects.requireNonNull(specURI);
        this.deploymentRegistry = new TTLCache<>(60, TimeUnit.SECONDS, this::fetch);
    }

    protected abstract DeploymentSpecificationRegistry fetch();

    @Override
    public DeploymentSpecificationRegistry access() {
        return deploymentRegistry.access();
    }

    @Override
    public String getServiceNamespace() {
        return specURI.getEncodedAddress();
    }

    @Override
    public String toString() {
        return getServiceNamespace();
    }


    public SpecURI getSpecUri() {
        return specURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecSourceFromURI that = (SpecSourceFromURI) o;


        return specURI.equals(that.specURI);
    }

    @Override
    public int hashCode() {
        return specURI.hashCode();
    }
}
