package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.deplengine.DeplEngineRegistry;
import de.upb.sede.edd.deploy.deplengine.RemoteDeplEngine;
import de.upb.sede.edd.model.Remote;
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
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
@Controller
public class RemoteApiController implements RemoteApi {

    private static final Logger log = LoggerFactory.getLogger(RemoteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RemoteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Remote>> remoteGet() {
        List<Remote> remotes = EDD.getInstance().getDeploymentEngine().getEngines()
                .stream()
                .filter(engine -> engine instanceof RemoteDeplEngine)
                .map(engine -> {
                    return (RemoteDeplEngine) engine;
                })
                .map(remoteEngine -> {
                    Remote remote = new Remote();
                    remote.setName(remoteEngine.getName());
                    remote.connection(remoteEngine.getAddress().buildString());
                    return remote;
                })
                .collect(Collectors.toList());


        return new ResponseEntity<List<Remote>>(remotes, HttpStatus.OK);
    }

    public ResponseEntity<Void> remotePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Remote body) {
        String accept = request.getHeader("Accept");
        DeplEngineRegistry deploymentEngine = EDD.getInstance().getDeploymentEngine();
        if(deploymentEngine.getEngines().stream().filter(engine -> engine.getName().equals(body.getName())).findAny().isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        deploymentEngine.createRemoteEngine(body.getName(), body.getConnection());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<String> remoteRemoteNameDelete(
            @ApiParam(value = "Remote name of a previosuly managed machine with an EDD server.",required=true) @PathVariable("remoteName")
                    String remoteName) {
        boolean deleted;
        try {
            DeplEngineRegistry engReg = EDD.getInstance().getDeploymentEngine();
            deleted = engReg.disconnectEngine(remoteName);
        }
        catch(Exception ex) {
            return ResponseEntity.status(500).body("Internal server error: \n" + Streams.ErrToString(ex));
        }
        if(deleted) {
            return ResponseEntity.ok().body("Removed remote engine " + remoteName);
        } else {
            return ResponseEntity.status(404).body("Remote engine not found.");
        }
    }

}
