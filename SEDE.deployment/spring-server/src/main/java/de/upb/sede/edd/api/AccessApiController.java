package de.upb.sede.edd.api;

import de.upb.sede.edd.EDDInfo;
import de.upb.sede.edd.EDDInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AccessApiController implements AccessApi {

    private EDDInfo eddInfo;

    @Autowired
    public AccessApiController(EDDInfo eddInfo) {
        this.eddInfo = eddInfo;
    }

    @Override
    public ResponseEntity<Void> areYouThere() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EDDInfoModel> getInfo() {
        EDDInfoModel model;
        try {
            model = eddInfo.getData();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(model);
    }

    @Override
    public ResponseEntity<Void> setAddress(String address) {
        try{
            eddInfo.setMachineAddress(address);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<String> getAddress() {
        try{
            Optional<String> address = eddInfo.getMachineAddress();
            if(address.isPresent()) {
                return ResponseEntity.ok(address.get());
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
