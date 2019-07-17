package de.upb.sede.edd.deploy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.upb.sede.util.DynTypeField;
import de.upb.sede.util.Uncheck;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@JsonDeserialize(using = AscribedService.Deserializer.class)
@JsonSerialize(using = AscribedService.Serializer.class)
public class AscribedService {

    private SpecURI uri;

    private String service;

    public AscribedService(String scheme, URI marketServiceURI, String service) {
        this.uri = new SpecURI(scheme, marketServiceURI);
        this.service = service;
    }

    public AscribedService(SpecURI uri, String service) {
        this.uri = uri;
        this.service = service;
    }

    public static AscribedService fromGateway(String gatewayAddress, String service) {
        return new AscribedService(new SpecURI("http", URI.create(gatewayAddress)), service);
    }

    public static AscribedService fromClasspath(String filePath, String service) {
        while(!filePath.startsWith("//")) {
            filePath = "/" + filePath;
        }
        return new AscribedService(new SpecURI("classpath", URI.create(filePath)), service);
    }

    public static AscribedService fromFile(String filePath, String service) {
        while(!filePath.startsWith("//")) {
            filePath = "/" + filePath;
        }
        return new AscribedService(new SpecURI("file", URI.create(filePath)), service);
    }

    public SpecURI getSpecUri() {
        return uri;
    }

    public String getService() {
        return service;
    }

    public static AscribedService parseURI(String uriText) {
        return parseURI(uriText, false);
    }

    private static AscribedService parseURI(String uriText, boolean schemaAppended) {
        try {
            URI uri = new URI(uriText);
            String service = uri.getFragment();
            URI fragmentLessUri = new URI(null, uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), null);
            return new AscribedService(uri.getScheme(), fragmentLessUri, service);
        } catch (URISyntaxException e) {
            if(!schemaAppended)
                return parseURI("http://" + uriText, true);
            else
                throw Uncheck.throwAsUncheckedException(e);
        }
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
            String s = value.getSpecUri().toString() + "#" + value.getService();
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
