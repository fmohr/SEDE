package de.upb.sede.edd.deploy;

public interface EDDSource {

    boolean retrieve(boolean update, ServiceDeployment currentDeployment);

}
