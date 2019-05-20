package de.upb.sede.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


//@JsonDeserialize(using = KneadForm.Deserializer.class)
//@JsonSerialize(using = KneadForm.Serializer.class)
public class KneadForm implements Kneadable, JsonKnibble {

    @JsonIgnore
    private final MutableWobblyField<JsonKnibble> source = MutableWobblyField.empty();

    public void setSource(JsonKnibble newSource) {
        this.source.set(newSource);
    }

    @Override
    public <T> T knead(Class<T> form) {
        return source.orElseThrow(this::noSourceSet).knead(form);
    }

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

    Optional<JsonKnibble> getSource(){
        return source.opt();
    }

    @Deprecated
    public static class FormDeserializer<T extends KneadForm> extends StdDeserializer<T> {

        FormDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public T deserialize
                (JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            return (T) new KneadableJsonObject(map).knead(handledType());
        }
    }

    @Deprecated
    public static class FormSerializer<T extends KneadForm> extends StdSerializer<T> {

        FormSerializer(Class<T> vc) {
            super(vc);
        }

        @Override
        public void serialize
                (T value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
            if(value.getSource().isPresent())
                gen.writeObject(value.getSource().get());
            else {
                gen.writeObject(value);
            }
        }
    }
}
