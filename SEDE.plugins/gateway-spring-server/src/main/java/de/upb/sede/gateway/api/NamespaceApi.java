package de.upb.sede.gateway.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface NamespaceApi {

    @ApiOperation(value = "Adds the sent deployment specifiction to the namespace.", nickname = "addDeplSpec", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The deployment spec was added."),
        @ApiResponse(code = 400, message = "Bad deployment spec.") })
    @RequestMapping(value = "/add-conf/deployments",
        method = RequestMethod.POST)
    ResponseEntity<Void> addDeplSpec();



    @ApiOperation(value = "Rerturns the deployment specifications in this namespace.", nickname = "getDeplSpec", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The deployment spec is returned in the body."),
        @ApiResponse(code = 500, message = "Internal error.") })
    @RequestMapping(value = "/get-conf/deployments",
        method = RequestMethod.GET)
    ResponseEntity<String> getDeplSpec();

}
