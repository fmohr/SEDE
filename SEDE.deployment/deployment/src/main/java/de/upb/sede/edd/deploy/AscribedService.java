package de.upb.sede.edd.deploy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.upb.sede.util.DynTypeField;
import de.upb.sede.util.ModifiableURI;
import de.upb.sede.util.UnmodifiableURI;

import java.io.IOException;
import java.net.URI;

@JsonDeserialize(using = AscribedService.Deserializer.class)
@JsonSerialize(using = AscribedService.Serializer.class)
public class AscribedService {

    private UnmodifiableURI uri;

    private String service;

    public AscribedService(UnmodifiableURI uri, String service) {
        this.uri = uri;
        this.service = service;
    }

    public static AscribedService fromGateway(String gatewayAddress, String service) {
        return new AscribedService(
            new ModifiableURI().host(gatewayAddress).scheme("http").unmod(),
            service);
    }

    public static AscribedService fromClasspath(String filePath, String service) {
        return new AscribedService(
            new ModifiableURI().host("").path(filePath).scheme("classpath").unmod(),
            service);
    }

    public static AscribedService fromFile(String filePath, String service) {
        return new AscribedService(
            new ModifiableURI().host("").path(filePath).scheme("file").unmod(),
            service);
    }

    public UnmodifiableURI getNamespace() {
        return uri;
    }

    public String getService() {
        return service;
    }

    public static AscribedService parseURI(String uriText) {
        ModifiableURI modifiableURI = ModifiableURI.fromUriString(uriText);
        if(!modifiableURI.getScheme().isPresent())
            modifiableURI.interpretPathAsHost();
        modifiableURI.amendHttpScheme();
        String service = modifiableURI.getFragment().orElseThrow(() -> new IllegalArgumentException("The uri doesn't contain a fragment: " + uriText));
        modifiableURI.fragment(null);

        return new AscribedService(modifiableURI.unmod(), service);
    }


    @Override
    public String toString() {
        return uri + "#" + service;
    }

    static class Serializer extends StdSerializer<AscribedService> {

        protected Serializer() {
            super(AscribedService.class);
        }

        protected Serializer(Class<AscribedService> t) {
            super(t);
        }

        @Override
        public void serialize(AscribedService value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            String s = value.getNamespace().toString() + "#" + value.getService();
            gen.writeString(s);
        }
    }


    static class Deserializer extends StdDeserializer<AscribedService> {

        protected Deserializer() {
            super(DynTypeField.class);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public AscribedService deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String ascribedServiceStr = p.getCodec().readValue(p, String.class);
            return parseURI(ascribedServiceStr);
        }
    }
}
