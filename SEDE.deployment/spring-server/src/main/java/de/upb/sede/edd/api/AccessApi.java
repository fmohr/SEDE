package de.upb.sede.edd.api;

import de.upb.sede.edd.model.Body;
import de.upb.sede.edd.model.ServiceFulfillment;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Accessibility")
public interface AccessApi {

    @ApiOperation(value = "Checks availability", nickname = "areYouThere", notes = "", response = Void.class, responseContainer = "List", tags={  })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Yes, I am."),
        @ApiResponse(code = 400, message = "Apparently, I am not?") })
    @RequestMapping(value = "/areYouThere",
        method = RequestMethod.GET)
    ResponseEntity<Void> areYouThere();

}
