package ai.services.exec.auxiliary;

import ai.services.util.DynRecord;

import javax.annotation.Nullable;

public interface DynamicAuxAware {

    @Nullable
    DynRecord getDynAux();

}
