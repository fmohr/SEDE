package de.upb.sede.edd.deploy.group;

import de.upb.sede.edd.deploy.group.transaction.AddServicesTransaction;
import de.upb.sede.edd.deploy.group.transaction.FixedServicesTransaction;
import de.upb.sede.edd.deploy.group.transaction.SetGatewayAddressTransaction;

public interface DeploymentGroup {

    public String getGroupName();

    public void commitAdditionalServices(AddServicesTransaction transaction);

    public void commitFixServices(FixedServicesTransaction transaction);

    void commitStop();

    void commitStart();

    void commitSetGatewayAddress(SetGatewayAddressTransaction transaction);
}
