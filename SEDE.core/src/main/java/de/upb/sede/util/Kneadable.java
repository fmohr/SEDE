package de.upb.sede.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = KneadableJsonObject.Deserializer.class)
public interface Kneadable {

    <T> T knead(Class<T> form);

}
