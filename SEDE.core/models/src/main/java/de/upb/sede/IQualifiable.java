package de.upb.sede;

public interface IQualifiable {

    String getQualifier();

    static IQualifiable of(String qualifier) {
        return new ConstQualifier(qualifier);
    }

}
