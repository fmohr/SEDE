package de.upb.sede.gateway.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class ExecutionApiController implements ExecutionApi{

    private static final Logger logger = LoggerFactory.getLogger(ExecutionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final Gateway gateway;

    @org.springframework.beans.factory.annotation.Autowired
    public ExecutionApiController(ObjectMapper objectMapper, HttpServletRequest request, Gateway gateway) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.gateway = gateway;
    }

    public ResponseEntity<String> resolve(String jsonResolveRequest) {
        logger.debug("Received resolve request.");
        if(jsonResolveRequest.contains("%22")) {
            try {
                jsonResolveRequest = URLDecoder.decode(jsonResolveRequest, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error decoding:", e);
            }
        }
        /*
         * TODO: Do validation before parsing.
         */
        try{
            /*
             * parse request:
             */
            ResolveRequest resolveRequest = new ResolveRequest();
            resolveRequest.fromJsonString(jsonResolveRequest);
            /*
             * calculate the resolved graph:
             */
            GatewayResolution resolution = gateway.resolve(resolveRequest);
            return ResponseEntity.ok(resolution.toJson().toJSONString());
        } catch (Exception ex){
            return  ResponseEntity.status(400).body(Streams.ErrToString(ex));
        }
    }
}
