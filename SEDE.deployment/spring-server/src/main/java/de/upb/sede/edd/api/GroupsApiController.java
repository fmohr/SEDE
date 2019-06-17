package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.deploy.group.transaction.CreateGroupTransaction;
import de.upb.sede.edd.model.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Controller
public class GroupsApiController implements GroupsApi {

    private static final Logger log = LoggerFactory.getLogger(GroupsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final EDD edd = EDD.getInstance();

    @org.springframework.beans.factory.annotation.Autowired
    public GroupsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Group>> groupsGet() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Group>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Group> groupsGroupPathDisconnectPost(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Group>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> groupsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Group body) {
        String accept = request.getHeader("Accept");
        String group = body.getPath();
        if(edd.getGroupRepository().getGroup(group).isPresent()) {
            return new ResponseEntity<String>("Group already exists: " + group, HttpStatus.BAD_REQUEST);
        }
        CreateGroupTransaction createGroupTransaction = new CreateGroupTransaction();
        createGroupTransaction.setGroupName(group);
        edd.getGroupRepository().createGroup(createGroupTransaction);
        return new ResponseEntity<String>("Group created.", HttpStatus.CREATED);
    }

}
