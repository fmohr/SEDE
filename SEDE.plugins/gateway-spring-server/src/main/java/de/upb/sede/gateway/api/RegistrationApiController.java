package de.upb.sede.gateway.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.deploy.EDDRegistration;
import de.upb.sede.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class RegistrationApiController implements RegistrationApi{

    private static final Logger logger = LoggerFactory.getLogger(RegistrationApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final Gateway gateway;

    @org.springframework.beans.factory.annotation.Autowired
    public RegistrationApiController(ObjectMapper objectMapper, HttpServletRequest request, Gateway gateway) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.gateway =gateway;
    }

    public ResponseEntity<Void> registerUnit() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<String> registerUnit(String jsonRegistration) {
        logger.debug("Received executor registration.");


        if(jsonRegistration.contains("%22")) {
            try {
                jsonRegistration = URLDecoder.decode(jsonRegistration, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error decoding:", e);
            }
        }

        /*
         * TODO: Do validation before parsing.
         */
        try{
            ExecutorRegistration execRegister = new ExecutorRegistration();
            execRegister.fromJsonString(jsonRegistration);
            gateway.register(execRegister);
            return ResponseEntity.ok().build();
        } catch (Exception ex){
            return ResponseEntity.status(400).body(Streams.ErrToString(ex));
        }
    }

    public ResponseEntity<String> registerEDD(String jsonRegistration) {
        logger.debug("Received edd registration.");


        if(jsonRegistration.contains("%22")) {
            try {
                jsonRegistration = URLDecoder.decode(jsonRegistration, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error decoding:", e);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        EDDRegistration eddRegistration;
        try {
            eddRegistration = mapper.readValue(jsonRegistration, EDDRegistration.class);

        } catch (IOException e) {
            logger.error("EDD registration body cannot be parsed.", e);
            return ResponseEntity.status(400).body(Streams.ErrToString(e));
        }
        boolean success = gateway.registerEDD(eddRegistration);
        if(success) {
            return ResponseEntity.ok("");
        }
        else {
            return ResponseEntity.status(400).body("Registration was unsuccessful");
        }
    }
}
