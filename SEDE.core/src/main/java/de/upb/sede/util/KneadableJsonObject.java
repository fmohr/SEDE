package de.upb.sede.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.logging.XMLFormatter;

@JsonDeserialize(using = KneadableJsonObject.Deserializer.class)
@JsonSerialize(using = KneadableJsonObject.Serializer.class)
public final class KneadableJsonObject implements Kneadable, JsonKnibble {
    private static final ObjectMapper _BEAN_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final ObjectMapper _MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new SimpleModule(){
                @Override
                public void setupModule(SetupContext setupContext) {
                    super.setupModule(setupContext);
                    setupContext.addBeanDeserializerModifier(new BeanDeserializerModifier() {

                        @Override
                        public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                            Class<?> beanClass = beanDesc.getBeanClass();
                            if (JsonKnibble.class.isAssignableFrom(beanClass)) {
                                return new KnibbleDeserializer(deserializer);
                            }
                            return deserializer;
                        }
                    });
                }
            });

    private final WobblyField<Map> data;

    public KneadableJsonObject(Map data) {
        this.data = WobblyField.of(data);
    }

    public static KneadableJsonObject fromJson(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            return new KneadableJsonObject((Map) parser.parse(jsonString));
        } catch (Exception ex) {
            throw new NotKneadableException(ex);
        }
    }

    private <T> void setSource(T kneaded){
        if(kneaded instanceof KneadForm) {
            ((KneadForm) kneaded).setSource(this);
        }
    }


    @Override
    public <T> T knead(Class<T> form) {
        if(form.isInstance(this)) {
            return (T) this;
        } else {
            return knead(types -> types.constructType(form));
        }
    }

    @Override
    public <T> T knead(Function<TypeFactory, JavaType> formType) {
        T kneaded;
        JavaType type = formType.apply(_MAPPER.getTypeFactory());
        try {
            kneaded = _MAPPER.convertValue(getData(), type);
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot knead into form " + type.toString(), ex);
        }
        setSource(kneaded);
        return kneaded;
    }

    @Override
    public List<Kneadable> knibbleList(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof List)) {
            throw new IllegalArgumentException("Data type of field " + field + " is not a list.");
        }
        List<JSONObject> objectNodes = (List<JSONObject>) getData().get(field);
        List<Kneadable> kneads = new ArrayList<>();
        try{
            for(JSONObject node : objectNodes) {
                KneadableJsonObject kneadable = new KneadableJsonObject(node);
                kneads.add(kneadable);
            }
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot knead into list of objects.", ex);
        }
        return kneads;
    }

    @Override
    public Kneadable knibbleObject(String field) {
        if(! getData().containsKey(field)) {
            throw new IllegalArgumentException("Data does not have field '"
                    + field + "'." );
        }
        if(!(getData().get(field) instanceof Map)) {
            throw new IllegalArgumentException("Data type of field `" + field + "` is not an object.");
        }
        JSONObject jsonObject = (JSONObject) getData().get(field);
        try {
            return new KneadableJsonObject(jsonObject);
        } catch(Exception ex) {
            throw new NotKneadableException("Cannot form into Kneadable.", ex);
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
    public void setSource(JsonKnibble source) {
        throw new IllegalArgumentException("Json Object already has a source");
    }

    @Override
    public Optional<JsonKnibble> getSource() {
        return Optional.of(this);
    }

    protected Map getData() {
        if(data.isPresent()) {
            return data.get();
        } else {
            throw new IllegalStateException("No data present.");
        }
    }

    public static class Deserializer extends StdDeserializer<KneadableJsonObject> {

        public Deserializer() {
            super(KneadableJsonObject.class);
        }

        @Override
        public KneadableJsonObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            return new KneadableJsonObject(map);
        }
    }

    public static class KnibbleDeserializer<T> extends StdDeserializer<T> {

        final JsonDeserializer<?> innderDeserializer;

        public KnibbleDeserializer(JsonDeserializer<?> deserializer) {
            super(deserializer.handledType());
            innderDeserializer = deserializer;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            T t = (T) _BEAN_MAPPER.convertValue(map, handledType());
            ((JsonKnibble) t).setSource(new KneadableJsonObject(map));
            return t;
        }
    }

    public static class Serializer extends StdSerializer<KneadableJsonObject> {

        protected Serializer() {
            super(KneadableJsonObject.class);
        }

        @Override
        public void serialize(KneadableJsonObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.getData());
        }
    }

}
