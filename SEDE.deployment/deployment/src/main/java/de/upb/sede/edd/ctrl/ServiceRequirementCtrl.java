package de.upb.sede.edd.ctrl;

import de.upb.sede.edd.reports.ServiceRequirementReport;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import de.upb.sede.edd.reports.ServiceRequirementStatus;

import java.util.ArrayList;
import java.util.List;


public class ServiceRequirementCtrl {

    public ServiceRequirementCtrl() {

    }

    public List<ServiceRequirementReport> requireServices(DeplEngine deplEngine, List<String> services) {
        List<ServiceRequirementReport> reports = new ArrayList<>();
        List<AscribedService> ascribedServices = new ArrayList<>();
        for(String service: services) {
            ServiceRequirementReport report = new ServiceRequirementReport();
            report.setService(service);
            AscribedService ascribedService;
            try {
                ascribedService = AscribedService.parseURI(service);
                ascribedServices.add(ascribedService);
            } catch (IllegalArgumentException exception) {
                String errorMessage =  String.format("Requested service cannot be parsed: %s", exception.getMessage());
                report.addMessage(errorMessage);
                report.setStatus(ServiceRequirementStatus.FAILED);
                reports.add(report);
            }
        }
        reports.addAll(deplEngine.addServices(ascribedServices));
        return reports;
    }
}
