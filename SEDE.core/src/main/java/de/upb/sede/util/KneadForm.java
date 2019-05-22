package de.upb.sede.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


//@JsonDeserialize(using = KneadForm.Deserializer.class)
//@JsonSerialize(using = KneadForm.Serializer.class)
public class KneadForm implements JsonKnibble {

    @JsonIgnore
    private final MutableWobblyField<JsonKnibble> source = MutableWobblyField.empty();

    public void setSource(JsonKnibble newSource) {
        this.source.set(newSource);
    }

//    @Override
//    public <T> T knead(Class<T> form) {
//        return source.orElseThrow(this::noSourceSet).knead(form);
//    }
//
//    @Override
//    public <T> T knead(Function<TypeFactory, JavaType> formType) {
//        return source.orElseThrow(this::noSourceSet).knead(formType);
//    }

    @Override
    public List<Kneadable> knibbleList(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleList(field);
    }

    @Override
    public Kneadable knibbleObject(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleObject(field);
    }

    @Override
    public Number knibbleNumber(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleNumber(field);
    }

    @Override
    public String knibbleString(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleString(field);
    }

    @Override
    public Boolean knibbleBoolean(String field) {
        return source.orElseThrow(this::noSourceSet).knibbleBoolean(field);
    }

    private IllegalStateException noSourceSet() {
        return new IllegalStateException(this.getClass().getName() + " was not correctly knibbled: No source was set.");
    }

    public <T> void setField(String fieldName, T value) {
        if(source.isPresent()) {
            source.get().setField(fieldName, value);
        }
    }

    public Optional<JsonKnibble> getSource(){
        return source.opt();
    }

//    public static class ToFormConverter<T> extends StdConverter<KneadableJsonObject, T> {
//
//        @Override
//        public T convert(KneadableJsonObject value) {
//            return value.knead(this::getOutputType);
//        }
//    }
//
//    public static class FromFormConverter<T> extends StdConverter<T, KneadableJsonObject> {
//
//        @Override
//        public KneadableJsonObject convert(T value) {
//            if(value instanceof JsonKnibble) {
//                return  ((JsonKnibble) value).knead(KneadableJsonObject.class);
//            } else {
//                throw new NotKneadableException("Value " + value + " is no kneadable");
//            }
//        }
//    }
}
