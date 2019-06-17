package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.group.DeploymentGroup;
import de.upb.sede.edd.deploy.group.LocalDeploymentGroup;
import de.upb.sede.edd.deploy.group.transaction.AddServicesTransaction;
import de.upb.sede.edd.deploy.group.transaction.CreateGroupTransaction;
import de.upb.sede.edd.deploy.group.transaction.FixedServicesTransaction;
import de.upb.sede.edd.model.Executor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExecutorsApiController implements ExecutorsApi {

    private static final Logger log = LoggerFactory.getLogger(ExecutorsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final EDD edd = EDD.getInstance();

    @org.springframework.beans.factory.annotation.Autowired
    public ExecutorsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> executorsGroupPathExecutorIdDelete(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath,@ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId) {
        String accept = request.getHeader("Accept");

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Executor> executorsGroupPathExecutorIdGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath,@ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Executor>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<String>> executorsGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        Optional<DeploymentGroup> group = edd.getGroupRepository().getGroup(groupPath);
        if(!group.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LocalDeploymentGroup  localDeploymentGroup = (LocalDeploymentGroup) group.get();
        return new ResponseEntity<List<String>>(new ArrayList<>(localDeploymentGroup.getRequestedServices()), HttpStatus.OK);
    }

    public ResponseEntity<String> executorsGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body, @ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        Optional<DeploymentGroup> group = edd.getGroupRepository().getGroup(groupPath);
        if(!group.isPresent()) {
            CreateGroupTransaction createGroupTransaction = new CreateGroupTransaction();
            createGroupTransaction.setGroupName(groupPath);
            edd.getGroupRepository().createGroup(createGroupTransaction);
            group = edd.getGroupRepository().getGroup(groupPath);
            if(!group.isPresent()) {
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        LocalDeploymentGroup  localDeploymentGroup = (LocalDeploymentGroup) group.get();
        AddServicesTransaction addSer = new AddServicesTransaction();
        addSer.setServices(new ArrayList<>(body.getEntities()));
        try{
            localDeploymentGroup.commitAdditionalServices(addSer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(Streams.ErrToString(ex), HttpStatus.BAD_REQUEST);
        }}

    public ResponseEntity<String> executorsGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body, @ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        Optional<DeploymentGroup> group = edd.getGroupRepository().getGroup(groupPath);
        if(!group.isPresent()) {
            CreateGroupTransaction createGroupTransaction = new CreateGroupTransaction();
            createGroupTransaction.setGroupName(groupPath);
            edd.getGroupRepository().createGroup(createGroupTransaction);
            group = edd.getGroupRepository().getGroup(groupPath);
            if(!group.isPresent()) {
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        LocalDeploymentGroup  localDeploymentGroup = (LocalDeploymentGroup) group.get();
        FixedServicesTransaction fixedServicesTransaction = new FixedServicesTransaction();
        fixedServicesTransaction.setServices(new ArrayList<>(body.getEntities()));
        try{
            localDeploymentGroup.commitFixServices(fixedServicesTransaction);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(Streams.ErrToString(ex), HttpStatus.BAD_REQUEST);
        }
    }

}
