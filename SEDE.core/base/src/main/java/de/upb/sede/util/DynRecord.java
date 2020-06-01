package de.upb.sede.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
@JsonDeserialize(using = DynRecord.Deserializer.class)
@JsonSerialize(using = DynRecord.Serializer.class)
public class DynRecord extends DynTypeObject implements RecordForm {


    private static final ObjectMapper _BEAN_MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    protected static final ObjectMapper _MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .registerModule(new SimpleModule(){
            @Override
            public void setupModule(SetupContext setupContext) {
                super.setupModule(setupContext);
                setupContext.addBeanDeserializerModifier(new BeanDeserializerModifier() {

                    @Override
                    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                        Class<?> beanClass = beanDesc.getBeanClass();
                        if (RecordForm.class.isAssignableFrom(beanClass)) {
                            return new InnerDeserializer(deserializer);
                        }
                        return deserializer;
                    }
                });
            }
        });

    public DynRecord(Map data) {
        super(data);
    }

    @Override
    public <T> T cast(JavaType type) {
        T kneaded;
        try {
            kneaded = _MAPPER.convertValue(getData(), type);
        } catch(Exception ex) {
            throw new CastToDynamicTypeException("Cannot knead into form " + type.toString(), ex);
        }
        super.validate(kneaded);
        setSource(kneaded);
        return kneaded;
    }

    private <T> void setSource(T kneaded){
        if(kneaded instanceof TypeForm) {
            ((TypeForm) kneaded).setSource(this);
        }
    }

    @Override
    public Map getData() {
        return (Map) super.getData();
    }


    public static DynRecord fromJson(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            return new DynRecord((Map) parser.parse(jsonString));
        } catch (Exception ex) {
            throw new CastToDynamicTypeException(ex);
        }
    }

    public static DynRecord fromYaml(String yamlString) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        try {
            Map knead = yamlMapper.readValue(yamlString, Map.class);
            return new DynRecord(knead);
        } catch (IOException e) {
            throw new CastToDynamicTypeException(e);
        }
    }

    @Override
    public List<DynType> knibbleList(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                + field + "'." );
        }
        if(!(getData().get(field) instanceof List)) {
            throw new IllegalArgumentException("Data type of field " + field + " is not a list.");
        }
        List<JSONObject> objectNodes = (List<JSONObject>) getData().get(field);
        List<DynType> kneads = new ArrayList<>();
        try{
            for(JSONObject node : objectNodes) {
                DynRecord kneadable = new DynRecord(node);
                kneads.add(kneadable);
            }
        } catch(Exception ex) {
            throw new CastToDynamicTypeException("Cannot knead into list of objects.", ex);
        }
        return kneads;
    }

    @Override
    public DynType knibbleObject(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                + field + "'." );
        }
        if(!(getData().get(field) instanceof Map)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not an object.");
        }
        JSONObject jsonObject = (JSONObject) getData().get(field);
        try {
            return new DynRecord(jsonObject);
        } catch(Exception ex) {
            throw new CastToDynamicTypeException("Cannot form into Kneadable.", ex);
        }
    }

    @Override
    public Number knibbleNumber(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                + field + "'." );
        }
        if(!(getData().get(field) instanceof Number)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not an number.");
        }
        return (Number) getData().get(field);
    }

    @Override
    public String knibbleString(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                + field + "'." );
        }
        if(!(getData().get(field) instanceof String)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not a string.");
        }
        return (String) getData().get(field);
    }

    @Override
    public Boolean knibbleBoolean(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                + field + "'." );
        }
        if(!(getData().get(field) instanceof Boolean)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not a boolean.");
        }
        return (Boolean) getData().get(field);
    }

    @Override
    public <T> void setField(String fieldName, T value) {
        getData().put(fieldName, value);
    }

    @Override
    public void setSource(RecordForm source) {
    }

    @Override
    public Optional<RecordForm> getSource() {
        return Optional.empty();
    }

    public void set(Object o) {
        Map newFields = _MAPPER.convertValue(o, Map.class);
        getData().putAll(newFields);
    }


    static class InnerDeserializer<T> extends StdDeserializer<T> {

        final JsonDeserializer<?> innderDeserializer;

        public InnerDeserializer(JsonDeserializer<?> deserializer) {
            super(deserializer.handledType());
            innderDeserializer = deserializer;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            T t = (T) _BEAN_MAPPER.convertValue(map, handledType());
            ((RecordForm) t).setSource(new DynRecord(map));
            return t;
        }
    }
    static class Deserializer extends StdDeserializer<DynRecord> {

        public Deserializer() {
            super(DynTypeObject.class);
        }

        @Override
        public DynRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            return new DynRecord(map);
        }
    }

    static class Serializer extends StdSerializer<DynRecord> {

        protected Serializer() {
            super(DynRecord.class);
        }

        @Override
        public void serialize(DynRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.getData());
        }
    }


}
