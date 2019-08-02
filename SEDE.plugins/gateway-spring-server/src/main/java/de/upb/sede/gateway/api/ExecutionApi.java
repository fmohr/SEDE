package de.upb.sede.gateway.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ExecutionApi {
    @ApiOperation(value = "Resolves the given execution request to a execution graph.", nickname = "resolve", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Execution graph was resolved."),
        @ApiResponse(code = 400, message = "Bad execution request.") })
    @RequestMapping(value = "/resolve",
        method = RequestMethod.POST)
    ResponseEntity<String> resolve(@RequestBody String executionRequest);
}
