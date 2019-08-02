package de.upb.sede.edd.deploy.specsrc;

import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.UnmodifiableURI;

public class ClasspathSpecSource extends SpecSourceFromURI implements SpecSource {


    public ClasspathSpecSource(UnmodifiableURI specURI) {
        super(specURI);
        String scheme = getSpecUri().getScheme().orElseThrow(() -> new IllegalArgumentException());
        if(!scheme.equals("classpath") && !scheme.equals("cp")){
            throw new IllegalArgumentException("The given url " + getServiceNamespace()  + " has an unknown scheme: " + scheme);
        }
    }

//    @Override
//    public String getServiceNamespace() {
//        return "classpath:///" + super.getServiceNamespace();
//    }

    @Override
    protected DeploymentSpecificationRegistry fetch() {
        String path = getSpecUri().getPath().orElse(null);
        String registryJsonString = FileUtil.readResourceAsString(path);
        return DeploymentSpecificationRegistry.fromString(registryJsonString);
    }
}
