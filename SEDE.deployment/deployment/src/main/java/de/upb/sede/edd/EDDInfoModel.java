package de.upb.sede.edd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.util.OptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class EDDInfoModel {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(EDDInfoModel.class);

    private OptionalField<String> identifier;

    private OptionalField<String> machineAddress;

    public EDDInfoModel(OptionalField<String> identifier, OptionalField<String> machineAddress) {
        if(identifier.isAbsent()) {
            throw new IllegalArgumentException("Identifier cannot be empty.");
        }
        this.identifier = identifier;
        this.machineAddress = machineAddress;
    }

    public EDDInfoModel() {
    }

    public OptionalField<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(OptionalField<String> identifier) {
        if(identifier.isAbsent()) {
            throw new IllegalArgumentException("Identifier cannot be empty.");
        }
        this.identifier = identifier;
    }

    public OptionalField<String> getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(OptionalField<String> machineAddress) {
        this.machineAddress = machineAddress;
    }

    public String encode() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static EDDInfoModel decode(String s) {
        try {
            return decodeJsonString(s);
        }
        catch (NullPointerException ignored) {}
        catch (RuntimeException e) {
            logger.warn("Couldn't decode EDD Info: " + s, e);
        }
        String identifier = UUID.randomUUID().toString();
        return new EDDInfoModel(OptionalField.of(identifier), OptionalField.empty());
    }

    public static EDDInfoModel decodeJsonString(String s) {
        try {
            return MAPPER.readValue(s, EDDInfoModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
