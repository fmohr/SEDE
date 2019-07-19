package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.FileUtil;

import java.util.Objects;

public class FileSpecSource extends SpecSourceFromURI implements SpecSource {


    public FileSpecSource(SpecURI specURI) {
        super(specURI);
        String scheme = Objects.requireNonNull(getSpecUri().getScheme());
        if(!scheme.equals("file")){
            throw new IllegalArgumentException("The given url " + getServiceNamespace()  + " has an unknown scheme: " + scheme);
        }
    }

    @Override
    public String getServiceNamespace() {
        return "file:///" + super.getServiceNamespace();
    }

    @Override
    protected DeploymentSpecificationRegistry fetch() {
        String registryJsonString = FileUtil.readFileAsString(super.getSpecUri().getAddress());
        return DeploymentSpecificationRegistry.fromString(registryJsonString);
    }
}
