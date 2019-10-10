package de.upb.sede;

import org.immutables.value.Value;

public interface IQualifiable {

    String getQualifier();

    @Value.Default
    @Value.Auxiliary
    default String getSimpleName(){
        return getQualifier();
    }

    static IQualifiable of(String qualifier) {
        return new ConstQualifier(qualifier);
    }

    static IQualifiable of(String qualifier, String simpleName) {
        return new ConstQualifier(qualifier, simpleName);
    }

}
