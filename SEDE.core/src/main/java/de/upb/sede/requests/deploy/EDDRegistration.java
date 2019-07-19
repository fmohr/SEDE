package de.upb.sede.requests.deploy;

import java.util.ArrayList;
import java.util.List;

public class EDDRegistration {

    private String eddId;

    private String address;

    private String serviceNamespace;

    private List<String> offeredServices;

    public EDDRegistration(String eddId, String address, String serviceNamespace, List<String> offeredServices) {
        this.eddId = eddId;
        this.address = address;
        this.offeredServices = offeredServices;
        this.serviceNamespace = serviceNamespace;
    }

    public EDDRegistration() {
    }

    public EDDRegistration copy() {
        return new EDDRegistration(eddId, address, serviceNamespace, new ArrayList<>(offeredServices));
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

    public String getServiceNamespace() {
        return serviceNamespace;
    }

    public void setServiceNamespace(String serviceNamespace) {
        this.serviceNamespace = serviceNamespace;
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
