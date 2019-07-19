package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import de.upb.sede.edd.deploy.deplengine.InstallationReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
@Controller
public class PreparationsApiController implements PreparationsApi {

    private static final Logger log = LoggerFactory.getLogger(PreparationsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PreparationsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<InstallationReport>> preparationsGet() {
        return reports();
    }

    private ResponseEntity<List<InstallationReport>> reports() {
        try {
            List<InstallationReport> reports = new ArrayList<>();
            for (DeplEngine engine : EDD.getInstance().getDeploymentEngine().getEngines()) {

                List<InstallationReport> states;
                try {
                    states = engine.getCurrentState();
                } catch (Exception ex) {
                    log.error("Error current retriving state of engine: " + engine);
                    continue;
                }

                reports.addAll(states);
            }
            return new ResponseEntity<List<InstallationReport>>(reports, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't calculate preparations", e);
            return new ResponseEntity<List<InstallationReport>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
