package de.upb.sede.edd.api;

import de.upb.sede.edd.model.Executor;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

public interface ExecutorsApi {

    @RequestMapping(value = "/executors/{groupPath}/{executorId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> executorsGroupPathExecutorIdDelete(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath, @ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId);


    @ApiOperation(value = "Returns the executor handle of the requested executor.", nickname = "executorsGroupPathExecutorIdGet", notes = "", response = Executor.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "An executor handle.", response = Executor.class) })
    @RequestMapping(value = "/executors/{groupPath}/{executorId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Executor> executorsGroupPathExecutorIdGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath,@ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId);


    @ApiOperation(value = "Returns a list of executor ids.", nickname = "executorsGroupPathGet", notes = "", response = String.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A JSON array of executor ids.", response = String.class, responseContainer = "List") })
    @RequestMapping(value = "/executors/{groupPath}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> executorsGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);


    @ApiOperation(value = "Creates a new excutor.", nickname = "executorsGroupPathPost", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 404, message = "Could not create executor. Requested entities were not found.") })
    @RequestMapping(value = "/executors/{groupPath}",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> executorsGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);


    @ApiOperation(value = "Alters a running executor. If additional entities need to be resolved the executor will be restarted.", nickname = "executorsGroupPathPut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Altered"),
        @ApiResponse(code = 404, message = "Could not alter executor. Requested entities were not found. The executor is not interrupted and is still running.") })
    @RequestMapping(value = "/executors/{groupPath}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> executorsGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);

}
