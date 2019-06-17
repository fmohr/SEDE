package de.upb.sede.edd.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.group.DeploymentGroup;
import de.upb.sede.edd.deploy.group.LocalDeploymentGroup;
import de.upb.sede.edd.deploy.group.transaction.CreateGroupTransaction;
import de.upb.sede.edd.deploy.group.transaction.SetGatewayAddressTransaction;
import de.upb.sede.edd.model.Group;
import de.upb.sede.util.OptionalField;
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
import java.util.Optional;

@Controller
public class GatewayApiController implements GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final EDD edd = EDD.getInstance();

    @org.springframework.beans.factory.annotation.Autowired
    public GatewayApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> gatewayGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        Optional<DeploymentGroup> group = edd.getGroupRepository().getGroup(groupPath);
        if(group.isPresent()) {
            if(((LocalDeploymentGroup)group.get()).getGatewayAddress().isPresent()) {
                String gatewayAddress = ((LocalDeploymentGroup)group.get()).getGatewayAddress().get();
                return ResponseEntity.ok(gatewayAddress);
            } else {
                return new ResponseEntity<String>("No gateway was set.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>("Group was not found..", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Void> gatewayGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        String group = groupPath;
        String gateway = body;
        SetGatewayAddressTransaction setGateway = new SetGatewayAddressTransaction();
        setGateway.setGateway(OptionalField.of(gateway));
        CreateGroupTransaction createGroupTransaction = new CreateGroupTransaction();
        createGroupTransaction.setGroupName(group);
        edd.getGroupRepository().getGroup(group).orElseGet(() -> {
            edd.getGroupRepository().createGroup(createGroupTransaction);
            return edd.getGroupRepository().getGroup(group).get();
        }).commitSetGatewayAddress(setGateway);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> gatewayGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");

        String group = groupPath;
        String gateway = body;
        SetGatewayAddressTransaction setGateway = new SetGatewayAddressTransaction();
        setGateway.setGateway(OptionalField.of(gateway));
        CreateGroupTransaction createGroupTransaction = new CreateGroupTransaction();
        createGroupTransaction.setGroupName(group);
        edd.getGroupRepository().getGroup(group).orElseGet(() -> {
            edd.getGroupRepository().createGroup(createGroupTransaction);
            return edd.getGroupRepository().getGroup(group).get();
        }).commitSetGatewayAddress(setGateway);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
