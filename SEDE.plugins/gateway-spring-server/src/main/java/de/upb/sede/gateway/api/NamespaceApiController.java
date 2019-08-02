package de.upb.sede.gateway.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.gateway.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NamespaceApiController implements NamespaceApi {

    private static final Logger logger = LoggerFactory.getLogger(NamespaceApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final Gateway gateway;


    @org.springframework.beans.factory.annotation.Autowired
    public NamespaceApiController(ObjectMapper objectMapper, HttpServletRequest request, Gateway gateway) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.gateway = gateway;
    }

    public ResponseEntity<String> getDeplSpec() {
        logger.info("Received GET request for deployment configuration.");
        try{
            String deplString = gateway.getDeploymentConfig().serialize();
            return ResponseEntity.status(200)
                .body(deplString);
        } catch (Exception ex){
            return ResponseEntity.status(500).build();
        }
    }


    public ResponseEntity<Void> addDeplSpec() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
}
