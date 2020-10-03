package ai.services.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@JsonDeserialize(using = DynTypeField.Deserializer.class)
@JsonSerialize(using = DynTypeField.Serializer.class)
public final class DynTypeField extends DynTypeObject implements DynType {

    private Map<JavaType, Object> forms = new ConcurrentHashMap<>();

    public DynTypeField() {
        this(new HashMap<>());
    }


    public DynTypeField(Object data) {
        super(data);
    }

    public static DynTypeField fromJson(String jsonString) {
        try {
            return new DynTypeField(_MAPPER.readValue(jsonString, Object.class));
        }
        catch (Exception ex) {
            throw new CastToDynamicTypeException(ex);
        }
    }

    public static DynTypeField fromYaml(String yamlString) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        try {
            Object knead = yamlMapper.readValue(yamlString, Object.class);
            return new DynTypeField(knead);
        } catch (IOException e) {
            throw new CastToDynamicTypeException(e);
        }
    }

    public <T> T set(Class<T> form, T value) {
        JavaType type = _MAPPER.constructType(form);
        return set(type, value);
    }

    public <T> T set(JavaType type, T value) {
        return (T) forms.put(type, value);
    }


    public <T> T cast(JavaType formType) {
        return (T) forms.computeIfAbsent(formType, t -> super.cast(formType));
    }


    static class Serializer extends StdSerializer<DynTypeField> {

        protected Serializer() {
            super(DynTypeField.class);
        }

        protected Serializer(Class<DynTypeField> t) {
            super(t);
        }

        @Override
        public void serialize(DynTypeField value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            Map data = new HashMap();
            data.putAll((Map) value.getData());
            value.forms.values().stream()
                .map(form -> _MAPPER.convertValue(form, HashMap.class))
                .forEach(data::putAll);
            gen.writeObject(data);
        }
    }
    static class Deserializer extends StdDeserializer<DynTypeField> {

        protected Deserializer() {
            super(DynTypeField.class);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public DynTypeField deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            return new DynTypeField(map);
        }
    }
}
