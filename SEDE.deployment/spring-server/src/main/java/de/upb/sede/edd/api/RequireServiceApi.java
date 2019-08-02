/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.9).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package de.upb.sede.edd.api;

import de.upb.sede.edd.reports.ServiceRequirementReport;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
@Api(value = "requireService", description = "the requireService API")
public interface RequireServiceApi {

    @ApiOperation(value = "Require the given services. It will be added to the required services and deployed the next time prepare deployment is called.", nickname = "requireServicePost", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 404, message = "Requested services were not found.") })
    @RequestMapping(value = "/requireService",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<List<ServiceRequirementReport>> requireServicePost(@ApiParam(value = "", required = true) @Valid @RequestBody List<String> body);


    @ApiOperation(value = "Require the given services from the given remote. It will be added to the required services and deployed the next time prepare deployment is called.", nickname = "requireServiceRemoteNamePost", notes = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 404, message = "Requested services were not found.") })
    @RequestMapping(value = "/requireService/{remoteName}",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<List<ServiceRequirementReport>> requireServiceRemoteNamePost(@ApiParam(value = "", required = true) @Valid @RequestBody List<String> body, @ApiParam(value = "Remote name of a previosuly managed machine with an EDD server.", required = true) @PathVariable("remoteName") String remoteName);

}