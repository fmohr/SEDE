package de.upb.sede.composition.faa;

import de.upb.sede.composition.IFieldAccess;

import java.util.ArrayList;
import java.util.List;

public class FAAOutput {

    private final List<IFieldAccess> fieldAccesses;

    public FAAOutput(ArrayList<IFieldAccess> fieldAccesses) {
        this.fieldAccesses = fieldAccesses;
    }

    FAAOutput() {
        this(new ArrayList<IFieldAccess>());
    }

    public List<IFieldAccess> getFAList() {
        return fieldAccesses;
    }

}
