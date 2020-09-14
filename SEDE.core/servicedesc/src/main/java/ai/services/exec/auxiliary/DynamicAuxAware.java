package de.upb.sede.exec.auxiliary;

import de.upb.sede.util.DynRecord;

import javax.annotation.Nullable;

public interface DynamicAuxAware {

    @Nullable
    DynRecord getDynAux();

}
