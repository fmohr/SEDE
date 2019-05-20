package de.upb.sede.edd.api;

import de.upb.sede.edd.model.Executor;
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
import java.util.List;

@Controller
public class ExecutorsApiController implements ExecutorsApi {

    private static final Logger log = LoggerFactory.getLogger(ExecutorsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ExecutorsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> executorsGroupPathExecutorIdDelete(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath,@ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Executor> executorsGroupPathExecutorIdGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath,@ApiParam(value = "The id of an executor",required=true) @PathVariable("executorId") String executorId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Executor>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<String>> executorsGroupPathGet(@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<String>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> executorsGroupPathPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> executorsGroupPathPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Executor body,@ApiParam(value = "Group of executors. Group path consists of at least one group name. Sub groups are specified by joining with `-`.",required=true) @PathVariable("groupPath") String groupPath) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
