package de.upb.sede.requests.deploy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.upb.sede.util.ModifiableURI;
import de.upb.sede.util.UnmodifiableURI;

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
        this.getNamespaceURI(); // check if the uri is correct.
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
        getNamespaceURI();
    }

    @JsonIgnore
    public UnmodifiableURI getNamespaceURI() {
        return ModifiableURI.fromUriString(serviceNamespace).unmodifiableCopy();
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
