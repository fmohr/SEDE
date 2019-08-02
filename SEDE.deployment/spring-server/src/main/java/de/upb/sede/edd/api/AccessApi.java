package de.upb.sede.edd.api;

import de.upb.sede.edd.EDDInfoModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Api(value = "Accessibility")
public interface AccessApi {

    @ApiOperation(value = "Checks availability", nickname = "areYouThere", notes = "", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Yes, I am."),
        @ApiResponse(code = 400, message = "Apparently, I am not?") })
    @RequestMapping(value = "/areYouThere",
        method = RequestMethod.GET)
    ResponseEntity<Void> areYouThere();


    @ApiOperation(value = "Returns information about current EDD.", nickname = "identifier", notes = "", response = EDDInfoModel.class, tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Information returned."),
        @ApiResponse(code = 500, message = "Error accessing information.") })
    @RequestMapping(value = "/info",
        method = RequestMethod.GET)
    ResponseEntity<EDDInfoModel> getInfo();


    @ApiOperation(value = "Sets the address of the current EDD.", nickname = "address", notes = "")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Address changed."),
        @ApiResponse(code = 400, message = "Illegal address supplied.") })
    @RequestMapping(value = "/host-address",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> setAddress(@ApiParam(required = true) @Valid @RequestBody String body);

    @ApiOperation(value = "Returns the set address of the current EDD.", nickname = "address", notes = "",
        response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Address returned."),
        @ApiResponse(code = 400, message = "No address was set.") })
    @RequestMapping(value = "/host-address",
        method = RequestMethod.GET)
    ResponseEntity<String> getAddress();
}
