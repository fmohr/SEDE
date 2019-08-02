package de.upb.sede.gateway.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RegistrationApi {

    @ApiOperation(value = "Registers an executor deployment daemon to the gateway.", nickname = "registerEDD", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "EDD successfully registered."),
        @ApiResponse(code = 400, message = "Bad registration.") })
    @RequestMapping(value = "/registerEDD",
        method = RequestMethod.POST)
    ResponseEntity<String> registerEDD(@RequestBody String registration);

    @ApiOperation(value = "Registers an execution unit to the gateway.", nickname = "registerUnit", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Unit successfully registered."),
        @ApiResponse(code = 400, message = "Bad registration.") })
    @RequestMapping(value = "/register",
        method = RequestMethod.POST)
    ResponseEntity<String> registerUnit(@RequestBody String registration);


}
