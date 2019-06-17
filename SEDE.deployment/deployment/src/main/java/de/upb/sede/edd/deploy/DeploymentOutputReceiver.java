package de.upb.sede.edd.deploy;

import de.upb.sede.edd.process.ClassPath;

public interface DeploymentOutputReceiver {

    ClassPath cp(String spec);

}
