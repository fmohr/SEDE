package de.upb.sede.config;

public interface ClassSpec {

    boolean hasMethod(String methodName);

    String methodNames();

    MethodSpec construction();

    boolean isAbstract();

    boolean isWrapped();

    ClassSpec unwrap();
}
