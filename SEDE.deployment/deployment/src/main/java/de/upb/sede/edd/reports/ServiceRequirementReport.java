package de.upb.sede.edd.reports;

import de.upb.sede.edd.deploy.AscribedService;

public class ServiceRequirementReport extends Report {

    private String service;

    private String namespace;

    private ServiceRequirementStatus status = ServiceRequirementStatus.REQUESTED;

    public ServiceRequirementReport() {
    }

    public ServiceRequirementReport(AscribedService service) {
        this.service = service.getService();
        namespace = service.getNamespace().buildString();
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public ServiceRequirementStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequirementStatus status) {
        this.status = status;
    }
}
