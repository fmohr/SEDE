package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @org.springframework.beans.factory.annotation.Autowired
    public RequireServiceApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> requireServicePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<String> body) {
        return addServices(body, edd.getDeploymentEngine().getDefault());
    }

    public ResponseEntity<String> requireServiceRemoteNamePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<String> body, @ApiParam(value = "Remote name of a previosuly managed machine with an EDD server.",required=true) @PathVariable("remoteName") String remoteName) {
        Optional<DeplEngine> deplEngine = EDD.getInstance().getDeploymentEngine().getDeplEngine(remoteName);
        if(deplEngine.isPresent())
            return addServices(body, deplEngine.get());
        else
            return new ResponseEntity<>("Remote name " + remoteName + " is not known.", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> addServices(List<String> requistedServices, DeplEngine engine) {
        String accept = request.getHeader("Accept");

        List<AscribedService> services;
        try {
            services = requistedServices.stream()
                    .map(AscribedService::parseURI)
                    .collect(Collectors.toList());
        }catch (Exception ex){
            return new ResponseEntity<String>("The required services are ascribed correctly.. "
                    + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            engine.addServices(services);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Error adding required services:  "
                    + Streams.ErrToString(ex), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}
