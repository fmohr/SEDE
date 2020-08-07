package de.upb.sede.edd;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public abstract class EDDInfo {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(EDDInfo.class);

    private Cache<EDDInfoModel> sourceData;

    private SettableCache<EDDInfoModel> data;

    public EDDInfo(Cache<EDDInfoModel> sourceData) {
        this.sourceData = sourceData;
        data = new LayeredCache<>(new NullableCache<EDDInfoModel>(), sourceData);
    }

    public EDDInfoModel getData() {
        return data.access();
    }

    public String getIdentifier() {
        return getData().getIdentifier().get();
    }

    public void setIdentifier(String identifier) {
        EDDInfoModel d = getData();
        d.setIdentifier(OptionalField.of(identifier));
        data.set(d);
    }

    public Optional<String> getMachineAddress() {
        return getData().getMachineAddress().opt();
    }

    public void setMachineAddress(String machineAddress) {
        EDDInfoModel d = getData();
        d.setMachineAddress(OptionalField.of(machineAddress));
        data.set(d);
    }

}
