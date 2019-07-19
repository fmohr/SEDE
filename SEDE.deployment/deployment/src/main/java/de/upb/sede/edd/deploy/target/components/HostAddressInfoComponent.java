package de.upb.sede.edd.deploy.target.components;

import de.upb.sede.util.MutableOptionalField;

public class HostAddressInfoComponent extends TargetComponent {

    private String address;

    public HostAddressInfoComponent(String displayName, String address) {
        super.setDisplayName(displayName);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

}
