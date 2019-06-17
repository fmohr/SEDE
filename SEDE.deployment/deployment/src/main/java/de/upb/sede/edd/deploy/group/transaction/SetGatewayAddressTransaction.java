package de.upb.sede.edd.deploy.group.transaction;

import de.upb.sede.util.OptionalField;

public class SetGatewayAddressTransaction {

    private OptionalField<String> gateway = OptionalField.empty();

    public OptionalField<String> getGateway() {
        return gateway;
    }

    public void setGateway(OptionalField<String> gateway) {
        this.gateway = gateway;
    }
}
