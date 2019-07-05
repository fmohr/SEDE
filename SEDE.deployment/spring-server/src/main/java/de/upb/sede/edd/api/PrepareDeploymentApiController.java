package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.deplengine.DeplEngine;
import de.upb.sede.edd.model.Remote;
import de.upb.sede.edd.model.PrepareRequest;
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
public class PrepareDeploymentApiController implements PrepareDeploymentApi {

    private static final Logger log = LoggerFactory.getLogger(PrepareDeploymentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PrepareDeploymentApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> prepareDeploymentPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PrepareRequest body) {
        return prepare(EDD.getInstance().getDeploymentEngine().getDefault(), body.isUpdate());
    }

    public ResponseEntity<String> prepareDeploymentRemoteNamePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PrepareRequest body, @ApiParam(value = "Remote name of a previosuly managed machine with an EDD server.",required=true) @PathVariable("remoteName") String remoteName) {
        Optional<DeplEngine> deplEngine = EDD.getInstance().getDeploymentEngine().getDeplEngine(remoteName);
        if(deplEngine.isPresent())
            return prepare(deplEngine.get(), body.isUpdate());
        else
            return new ResponseEntity<>("Remote name " + remoteName + " is not known.", HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<String> prepare(DeplEngine engine, boolean update) {
        try {
            engine.prepareDeployment(update);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity<String>("Error trying to install services: " + Streams.ErrToString(ex),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
