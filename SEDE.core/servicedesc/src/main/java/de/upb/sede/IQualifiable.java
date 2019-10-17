package de.upb.sede;

import org.immutables.value.Value;

import java.util.List;

public interface IQualifiable {

    String getQualifier();

    @Value.Auxiliary
    List<String> getMetaTags();

    @Value.Default
    @Value.Auxiliary
    default String getSimpleName(){
        return getQualifier();
    }

}
