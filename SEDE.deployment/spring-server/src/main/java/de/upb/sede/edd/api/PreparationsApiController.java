package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import de.upb.sede.edd.deploy.deplengine.InstallationState;
import de.upb.sede.edd.model.Installation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEntity<List<Installation>> preparationsGet() {
        return preparations();
    }

    private ResponseEntity<List<Installation>> preparations() {
        try {
            List<Installation> installations = new ArrayList<>();
            for(DeplEngine engine : EDD.getInstance().getDeploymentEngine().getEngines()) {

                List<InstallationState> states;
                try {
                    states = engine.getCurrentState();
                }catch(Exception ex) {
                    log.error("Error current retriving state of engine: " + engine);
                    continue;
                }

                installations.addAll(states.stream()
                        .map(state -> {

                            Installation inst = new Installation();
                            inst.setOut(state.getOut());
                            inst.setErrOut(state.getErr());
                            inst.setIncludedServices(state.getIncludedServices());
                            inst.setRequestedServices(state.getRequestedServices());
                            inst.setServiceCollectionName(state.getServiceCollectionName());
                            inst.setSuccess(state.getState() == InstallationState.State.Success);
                            inst.setMachine(engine.getName());
                            return inst;
                        })
                        .collect(Collectors.toList()));
            }
            return new ResponseEntity<List<Installation>>(installations, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't calculate preparations", e);
            return new ResponseEntity<List<Installation>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
