package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import de.upb.sede.util.TTLCache;
import de.upb.sede.util.Uncheck;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
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
    public String getName() {
        return specURI.getEncodedAddress();
    }

    @Override
    public String toString() {
        return getName();
    }


    protected SpecURI getSpecUri() {
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
