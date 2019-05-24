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
@Api(value = "state", description = "the state API")
public interface StateApi {

    @ApiOperation(value = "Returns the current state. The returned body serves as a backup and reposting it will restore this state.", nickname = "stateGet", notes = "", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The current state.") })
    @RequestMapping(value = "/state",
        method = RequestMethod.GET)
    ResponseEntity<Void> stateGet();


    @ApiOperation(value = "Restores to the given state. This process is recursive, i.e. all components in every group will be killed and rebooted with the given configuration.", nickname = "statePut", notes = "", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The state was (partially) restored."),
        @ApiResponse(code = 402, message = "Restoring the state was disabled."),
        @ApiResponse(code = 405, message = "The state was not restored. Another restoration may have been in progess.") })
    @RequestMapping(value = "/state",
        method = RequestMethod.PUT)
    ResponseEntity<Void> statePut();

}
