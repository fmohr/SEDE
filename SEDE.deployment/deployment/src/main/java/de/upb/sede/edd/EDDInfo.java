package de.upb.sede.edd;

import de.upb.sede.util.OptionalField;

import static java.util.Objects.requireNonNull;
public class EDDInfo {

    private OptionalField<String> identifier;

    private OptionalField<String> machineAddress;

    public EDDInfo(String identifier, String machineAddress) {
        requireNonNull(identifier);
        requireNonNull(machineAddress);
        this.identifier = OptionalField.of(identifier);
        this.machineAddress = OptionalField.of(machineAddress);
    }

    public EDDInfo() {

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        this.machineAddress = machineAddress;
    }
}
