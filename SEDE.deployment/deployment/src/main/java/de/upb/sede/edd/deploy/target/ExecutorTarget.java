package de.upb.sede.edd.deploy.target;

import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.edd.deploy.target.components.ExecutorConfigComponent;
import de.upb.sede.edd.deploy.target.components.HostAddressInfoComponent;
import de.upb.sede.edd.deploy.target.components.TCPPortComponent;
import de.upb.sede.requests.ExecutorRegistration;

import java.util.List;

public interface ExecutorTarget extends Target {

    public List<String> getServiceCollections();

    public ExecutorRegistration getRegistration();

    ExecutorConfigComponent getExecutorConfig();

    TCPPortComponent getPort();

    HostAddressInfoComponent getHostAddress();

}
