package de.upb.sede.edd.api;

import de.upb.sede.edd.EDD;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-03T11:35:15.474Z[GMT]")
@Controller
public class DemandUnitApiController implements DemandUnitApi {

    private static final Logger log = LoggerFactory.getLogger(DemandUnitApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DemandUnitApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ExecutorDemandFulfillment> demandUnitPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody ExecutorDemandRequest body) {
        try {
            ExecutorDemandFulfillment fulfillment = EDD.getInstance().getRuntimeRegistry().demand(body);
            return ResponseEntity.ok(fulfillment);
        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<ExecutorDemandFulfillment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
