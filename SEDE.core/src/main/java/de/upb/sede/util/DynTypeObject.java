package de.upb.sede.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.*;

@JsonDeserialize(using = DynTypeObject.Deserializer.class)
@JsonSerialize(using = DynTypeObject.Serializer.class)
public class DynTypeObject implements DynType {
    protected static final ObjectMapper _MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final Object data;

    public DynTypeObject(Object data) {
        this.data = Objects.requireNonNull(data);
    }

    public static DynTypeObject fromJson(String jsonString) {
        try {
            return new DynTypeObject(_MAPPER.readValue(jsonString, Object.class));
        }
        catch (Exception ex) {
            throw new NotKneadableException(ex);
        }
    }

    public static DynTypeObject fromYaml(String yamlString) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        try {
            Object knead = yamlMapper.readValue(yamlString, Object.class);
            return new DynTypeObject(knead);
        } catch (IOException e) {
            throw new NotKneadableException(e);
        }
    }

    protected  <T> void validate(T kneaded) {
        if(kneaded instanceof Validatable) {
            ((Validatable) kneaded).validate();
        }
    }


    @Override
    public <T> T cast(Class<T> form) {
        if(form.isInstance(this)) {
            return (T) this;
        } else {
            return cast(_MAPPER.constructType(form));
        }
    }

    @Override
    public <T> T cast(JavaType type) {
        if(type.getRawClass().isInstance(this)) {
            return (T) this;
        }
        T kneaded;
        try {
            kneaded = _MAPPER.convertValue(getData(), type);
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot knead into form " + type.toString(), ex);
        }
        validate(kneaded);
        return kneaded;
    }


    protected Object getData() {
        return data;
    }

    @Override
    public String toString() {
        try {
            return _MAPPER.writeValueAsString(getData());
        } catch (JsonProcessingException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    static class Deserializer extends StdDeserializer<DynTypeObject> {

        public Deserializer() {
            super(DynTypeObject.class);
        }

        @Override
        public DynTypeObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Object obj = p.getCodec().readValue(p, Object.class);
            return new DynTypeObject(obj);
        }
    }

    static class Serializer extends StdSerializer<DynTypeObject> {

        protected Serializer() {
            super(DynTypeObject.class);
        }

        @Override
        public void serialize(DynTypeObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.getData());
        }
    }


}
