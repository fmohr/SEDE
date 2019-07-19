package de.upb.sede.edd.deploy.target;

import de.upb.sede.edd.deploy.target.components.JavaAppComponent;
import de.upb.sede.edd.process.ProcessHandleState;

public interface Target {

    void start();

    void stop();

    void reset();

    JavaAppComponent getApplication();

}
