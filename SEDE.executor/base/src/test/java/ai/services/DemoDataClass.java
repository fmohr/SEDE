package ai.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@JsonDeserialize(using = DemoDataClass.StdTestDeserializer.class)
@JsonSerialize(using = DemoDataClass.StdTestSerializer.class)
public class DemoDataClass implements Serializable {

    String value;

    public DemoDataClass(String value) {
        this.value = value;
    }

    private String serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Collections.singletonMap("customObjectSerialisation", value));
    }

    private static DemoDataClass deserialize(byte[] serialisation) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(serialisation, Map.class);
        return new DemoDataClass((String)Objects.requireNonNull(map.get("customObjectSerialisation")));
    }

    private void writeObject(ObjectOutputStream oos) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue((OutputStream)oos,
            Collections.singletonMap("javaObjectSerialisation", value));
    }

    private void readObject(ObjectInputStream ois) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue((InputStream) ois, Map.class);
        value = (String) Objects.requireNonNull(map.get("javaObjectSerialisation"));
    }

    public static class StdTestDeserializer extends StdDeserializer<DemoDataClass> {

        protected StdTestDeserializer() {
            super(DemoDataClass.class);
        }

        @Override
        public DemoDataClass deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            return new DemoDataClass(node.get("json").textValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DemoDataClass that = (DemoDataClass) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public static class StdTestSerializer extends StdSerializer<DemoDataClass> {

        protected StdTestSerializer() {
            super(DemoDataClass.class);
        }

        @Override
        public void serialize(DemoDataClass value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("json", value.value);
            gen.writeEndObject();
        }
    }
}
