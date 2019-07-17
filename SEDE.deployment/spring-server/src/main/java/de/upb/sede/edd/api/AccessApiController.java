package de.upb.sede.edd.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AccessApiController implements AccessApi {
    @Override
    public ResponseEntity<Void> areYouThere() {
        return ResponseEntity.ok().build();
    }
}
