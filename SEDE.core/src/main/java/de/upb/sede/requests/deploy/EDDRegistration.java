package de.upb.sede.requests.deploy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EDDRegistration {

    private String eddId;

    private String address;

    private List<String> offeredServices;

    public EDDRegistration(String eddId, String address, List<String> offeredServices) {
        this.eddId = eddId;
        this.address = address;
        this.offeredServices = offeredServices;
    }

    public EDDRegistration copy() {
        return new EDDRegistration(eddId, address, new ArrayList<>(offeredServices));
    }

    public String getAddress() {
        return address;
    }

    public List<String> getOfferedServices() {
        return offeredServices;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOfferedServices(List<String> offeredServices) {
        this.offeredServices = offeredServices;
    }

    public String getEddId() {
        return eddId;
    }

    public void setEddId(String eddId) {
        this.eddId = eddId;
    }

    @Override
    public String toString() {
        return "EDDRegistration{" +
            "eddId='" + eddId + '\'' +
            ", address='" + address + '\'' +
            ", #services=" + offeredServices.size() +
            '}';
    }
}
