package de.upb.sede.util;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@JsonDeserialize(using = KneadableField.Deserializer.class)
@JsonSerialize(using = KneadableField.Serializer.class)
public final class KneadableField extends KneadableObject implements Kneadable{

    private Map<JavaType, Object> forms = new ConcurrentHashMap<>();

    public KneadableField(Map data) {
        super(data);
    }

    @Override
    public <T> T knead(Function<TypeFactory, JavaType> formType) {
        JavaType type = formType.apply(_MAPPER.getTypeFactory());
        return (T) forms.computeIfAbsent(type, t -> super.knead(formType));
    }


    static class Serializer extends StdSerializer<KneadableField> {

        protected Serializer() {
            super(KneadableField.class);
        }

        protected Serializer(Class<KneadableField> t) {
            super(t);
        }

        @Override
        public void serialize(KneadableField value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            Map data = new HashMap();
            data.putAll(value.getData());
            value.forms.values().stream()
                .map(form -> _MAPPER.convertValue(form, HashMap.class))
                .forEach(data::putAll);
            gen.writeObject(data);
        }
    }
    static class Deserializer extends StdDeserializer<KneadableField> {

        protected Deserializer() {
            super(KneadableField.class);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public KneadableField deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            return new KneadableField(map);
        }
    }
}
