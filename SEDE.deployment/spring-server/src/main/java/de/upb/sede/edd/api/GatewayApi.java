package de.upb.sede.edd.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@Api(value = "gateway", description = "the gateway API")
public interface GatewayApi {

    @ApiOperation(value = "Returns the url of the current SEDE Gateway", nickname = "gatewayGroupPathGet", notes = "", response = String.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Gateway url.", response = String.class) })
    @RequestMapping(value = "/gateway/{groupPath}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<String> gatewayGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);


    @ApiOperation(value = "Sets the url of the current SEDE Gateway", nickname = "gatewayGroupPathPost", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Gateway url was set and every executor was.") })
    @RequestMapping(value = "/gateway/{groupPath}",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> gatewayGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);


    @ApiOperation(value = "Sets the url of the current SEDE Gateway", nickname = "gatewayGroupPathPut", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Gateway url was set and every executor was.") })
    @RequestMapping(value = "/gateway/{groupPath}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> gatewayGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath);

}
