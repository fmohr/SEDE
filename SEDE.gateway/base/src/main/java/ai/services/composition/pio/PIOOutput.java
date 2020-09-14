package de.upb.sede.composition.pio;

import java.util.ArrayList;
import java.util.List;

public class PIOOutput {

    private final List<Long> programOrder;

    PIOOutput() {
        programOrder = new ArrayList<>();
    }

    public List<Long> getProgramOrder() {
        return programOrder;
    }



}
