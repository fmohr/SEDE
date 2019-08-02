package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.ctrl.ServiceRequirementCtrl;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.reports.ServiceRequirementReport;
import de.upb.sede.util.Streams;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
@Controller
public class RequireServiceApiController implements RequireServiceApi {

    private static final Logger log = LoggerFactory.getLogger(RequireServiceApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private EDD edd = EDD.getInstance();

    private ServiceRequirementCtrl ctrl;

    @org.springframework.beans.factory.annotation.Autowired
    public RequireServiceApiController(ObjectMapper objectMapper, HttpServletRequest request, ServiceRequirementCtrl serviceRequirementCtrl) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.ctrl = serviceRequirementCtrl;
    }

    public ResponseEntity<List<ServiceRequirementReport>> requireServicePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<String> body) {
        return addServices(body, edd.getDeploymentEngine().getDefault());
    }

    public ResponseEntity<List<ServiceRequirementReport>> requireServiceRemoteNamePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<String> body, @ApiParam(value = "Remote name of a previosuly managed machine with an EDD server.",required=true) @PathVariable("remoteName") String remoteName) {
        Optional<DeplEngine> deplEngine = EDD.getInstance().getDeploymentEngine().getDeplEngine(remoteName);
        if(deplEngine.isPresent())
            return addServices(body, deplEngine.get());
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<List<ServiceRequirementReport>> addServices(List<String> requistedServices, DeplEngine engine) {
        List<ServiceRequirementReport> reports = ctrl.requireServices(engine, requistedServices);
        List<AscribedService> services;

        return ResponseEntity.status(201).body(reports);
    }

}
