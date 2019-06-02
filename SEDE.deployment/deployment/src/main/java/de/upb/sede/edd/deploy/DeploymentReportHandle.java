package de.upb.sede.edd.deploy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.deploy.model.DeploymentReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DeploymentReportHandle {

    private File reportFile;

    private DeploymentReport model;

    public DeploymentReportHandle(File reportFile, DeploymentReport model) {
        this.reportFile = reportFile;
        this.model = model;
    }

    public static DeploymentReportHandle load(File resultDir) throws IOException {
        File deploymentResultFile = new File(resultDir, "deployment.json");
        if(deploymentResultFile.exists()) {
            DeploymentReport result = new ObjectMapper().readValue(deploymentResultFile, DeploymentReport.class);
            return new DeploymentReportHandle(deploymentResultFile, result);
        } else {
            return new DeploymentReportHandle(deploymentResultFile, new DeploymentReport());
        }
    }


    public boolean isDeployed() {
        return model.isDeployed();
    }

    public void setDeployed(boolean deployed) {
        model.setDeployed(deployed);
        if(deployed) {
            model.setTimestamp(System.currentTimeMillis());
        }
    }

    public void store() throws IOException {
        this.store(reportFile);
    }

    public void store(File reportFile) throws IOException {
        if(reportFile.exists()) {
            reportFile.delete();
        }
        new ObjectMapper().writeValue(reportFile, this);
    }

}
