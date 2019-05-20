package de.upb.sede.edd.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GatewayApiController implements GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public GatewayApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> gatewayGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> gatewayGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> gatewayGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
