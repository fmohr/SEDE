package ai.services.composition.faa;

import ai.services.composition.IFieldAccess;

import java.util.ArrayList;
import java.util.List;

public class IFACOutput {

    private final List<IFieldAccess> fieldAccesses;

    public IFACOutput(ArrayList<IFieldAccess> fieldAccesses) {
        this.fieldAccesses = fieldAccesses;
    }

    IFACOutput() {
        this(new ArrayList<IFieldAccess>());
    }

    public List<IFieldAccess> getFAList() {
        return fieldAccesses;
    }

}
