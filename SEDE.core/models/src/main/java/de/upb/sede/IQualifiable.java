package de.upb.sede;

import org.immutables.value.Value;

public interface IQualifiable {

    String getQualifier();

    @Value.Default
    default String getSimpleName(){
        return getQualifier();
    }

}
