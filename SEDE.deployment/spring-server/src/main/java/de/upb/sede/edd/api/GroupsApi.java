package de.upb.sede.edd.api;

import de.upb.sede.edd.model.Group;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Api(value = "groups", description = "the groups API")
public interface GroupsApi {

    @ApiOperation(value = "Returns all created groups.", nickname = "groupsGet", notes = "", response = Group.class, responseContainer = "List", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "All created groups.", response = Group.class, responseContainer = "List") })
    @RequestMapping(value = "/groups",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Group>> groupsGet();


    @ApiOperation(value = "Disconnects the given group recursively. Disconnecting lets running components in this group remain.", nickname = "groupsGroupPathDisconnectPost", notes = "", response = Group.class, tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The disconnected group. Connect by posting this object to `/groups`.", response = Group.class) })
    @RequestMapping(value = "/groups/{groupPath}/disconnect",
        produces = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Group> groupsGroupPathDisconnectPost(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);


    @ApiOperation(value = "Creates the specified group. A group can only be created when its parent group exist.", nickname = "groupsPost", notes = "", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group was created."),
        @ApiResponse(code = 405, message = "Parent group doesn't exist.") })
    @RequestMapping(value = "/groups",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> groupsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Group body);

}
