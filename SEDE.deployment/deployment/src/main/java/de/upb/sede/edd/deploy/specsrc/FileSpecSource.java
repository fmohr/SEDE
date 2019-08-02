package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.UnmodifiableURI;

import java.util.Objects;

public class FileSpecSource extends SpecSourceFromURI implements SpecSource {


    public FileSpecSource(UnmodifiableURI specURI) {
        super(specURI);
        String scheme = Objects.requireNonNull(getSpecUri().getScheme().orElse(null));
        if(!scheme.equals("file")){
            throw new IllegalArgumentException("The given url " + getServiceNamespace()  + " has an unknown scheme: " + scheme);
        }
    }

//    @Override
//    public String getServiceNamespace() {
//        return "file:///" + super.getServiceNamespace();
//    }

    @Override
    protected DeploymentSpecificationRegistry fetch() {
        String registryJsonString = FileUtil.readFileAsString(super.getSpecUri().getPath().orElse(null));
        return DeploymentSpecificationRegistry.fromString(registryJsonString);
    }
}
