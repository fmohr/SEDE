package de.upb.sede;

import org.immutables.value.Value;

import java.util.List;

public interface IQualifiable {

    String getQualifier();

    static IQualifiable of(String qualifier) {
        return new ConstQualifier(qualifier);
    }


}
