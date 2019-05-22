package de.upb.sede.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.function.Function;

//@JsonDeserialize(using = KneadableJsonObject.Deserializer.class)
public interface Kneadable {

    <T> T knead(Class<T> form);

    <T> T knead(Function<TypeFactory, JavaType> formType);
}
