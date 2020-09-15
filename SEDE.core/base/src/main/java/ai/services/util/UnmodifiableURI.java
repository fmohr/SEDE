package ai.services.util;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonSerialize(using = UnmodifiableURI.Serializer.class)
@JsonDeserialize(using = UnmodifiableURI.Deserializer.class)
public class UnmodifiableURI implements ResourceIdentifier{

    @Nullable
    protected String scheme;

    @Nullable
    protected String userInfo;

    @Nullable
    protected String host;

    @Nullable
    protected Integer port;

    @Nullable
    protected String path;

    protected Multimap<String, String> queryParams;

    @Nullable
    protected String fragment;

    protected UnmodifiableURI() {
        this.queryParams = ArrayListMultimap.create();
    }

    public UnmodifiableURI(String scheme, String userInfo, String host, Integer port, String path, Multimap<String, String> queryParams, String fragment) {
        this.scheme = scheme;
        this.userInfo = userInfo;
        this.host = host;
        this.port = port;
        this.path = path;
        if(queryParams == null) {
            this.queryParams = MultimapBuilder.hashKeys().arrayListValues().build();
        } else {
            this.queryParams = MultimapBuilder.hashKeys().arrayListValues().build(queryParams);
        }
        this.fragment = fragment;
    }


    public Optional<String> getScheme() {
        return Optional.ofNullable(scheme);
    }

    public Optional<String> getUserInfo() {
        return Optional.ofNullable(userInfo);
    }

    public Optional<String> getHost() {
        return Optional.ofNullable(host);
    }

    public Optional<Integer> getPort() {
        return Optional.ofNullable(port);
    }

    public Optional<String> getPath() {
        return Optional.ofNullable(path);
    }

    public Multimap<String, String> getQueryParams() {
        // TODO return an unmodifiable view instead.
        return queryParams;
    }

    public Optional<String> getFragment() {
        return Optional.ofNullable(fragment);
    }

    @Override
    public String toString() {
        return buildString();
    }

    public ModifiableURI mod() {
        return new ModifiableURI(this);
    }

    public URI buildURI() {
        String uriString = buildString();
        try {
            return new URI(uriString);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Could not create a URI instance in the current state: " + uriString);
        }
    }

    @JsonValue
    public String buildString() {
        String url = "";
        if(getScheme().isPresent()) {
            url += scheme + ":";
        }
        if(getHost().isPresent()) {
            url += "//";
            if(getUserInfo().isPresent()) {
                url += userInfo + "@";
            }
            url += host;
            if(getPort().isPresent()) {
                url += ":" + port;
            }
        }
        if(getPath().isPresent()) {
            if(getHost().isPresent() && !path.startsWith("/")) {
                url += "/";
            }
            url += path;
        }
        if(!queryParams.isEmpty()) {
            url += "?";
            url += queryParams
                .entries()
                .stream()
                .map(queryParam -> {
                    String param = queryParam.getKey();
                    if(queryParam.getValue() != null) {
                        param += "=" + queryParam.getValue();
                    }
                    return param;
                })
                .collect(Collectors.joining("&"));
        }
        if(getFragment().isPresent()) {
            url += "#";
            url += fragment;
        }
        return url;
    }

    static class Deserializer extends JsonDeserializer<UnmodifiableURI> {

        @Override
        public UnmodifiableURI deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
            String uriStr = parser.getCodec().readValue(parser, String.class);
            ModifiableURI modifiableURI = ModifiableURI.fromUriString(uriStr);
            return modifiableURI.unmodifiableCopy();
        }
    }

    static class Serializer extends JsonSerializer<UnmodifiableURI> {

        @Override
        public void serialize(UnmodifiableURI value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.buildString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnmodifiableURI that = (UnmodifiableURI) o;

        if (!Objects.equals(scheme, that.scheme))
            return false;
        if (!Objects.equals(userInfo, that.userInfo))
            return false;
        if (!Objects.equals(host, that.host))
            return false;
        if (!Objects.equals(port, that.port))
            return false;
        if (!Objects.equals(path, that.path))
            return false;
        if (!queryParams.equals(that.queryParams)) return false;
        return Objects.equals(fragment, that.fragment);
    }

    @Override
    public int hashCode() {
        int result = scheme != null ? scheme.hashCode() : 0;
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + queryParams.hashCode();
        result = 31 * result + (fragment != null ? fragment.hashCode() : 0);
        return result;
    }
}
