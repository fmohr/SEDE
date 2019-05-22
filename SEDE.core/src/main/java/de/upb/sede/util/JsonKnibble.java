package de.upb.sede.util;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

//@JsonDeserialize(using = KneadableJsonObject.Deserializer.class, converter = JsonKnibble.ToForm.class)
//@JsonSerialize(using = KneadableJsonObject.Serializer.class, converter = JsonKnibble.FromForm.class)
public interface JsonKnibble {

    List<Kneadable> knibbleList(String field);

    Kneadable knibbleObject(String field);

    Number knibbleNumber(String field);

    String knibbleString(String field);

    Boolean knibbleBoolean(String field);

    <T> void setField(String fieldName, T value);

    void setSource(JsonKnibble source);

    Optional<JsonKnibble> getSource();
}

