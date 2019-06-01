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
import de.upb.sede.util.OptionalField;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Acronym Path.
 * A Path that starts off with a acronym.
 */
@Immutable
@JsonDeserialize(using = AcrPath.JsonDeserializer.class)
@JsonSerialize  (using = AcrPath.JsonSerializer.class)
public class AcrPath {

    private final static String REGEX_GROUP_NAME_REF = "ref";
    private final static String REGEX_GROUP_NAME_ACR = "acr";
    private final static String REGEX_GROUP_NAME_PATH = "path";

    /**
     * e.g.
     * Matches:
     *      [0]/path/to/file
     * Groups:
     *      acr = 0
     *      path = path/to/file
     *
     *
     * Matches:
     *      [a:0]/path/to/file
     * Groups:
     *      ref = a
     *      acr = 0
     *      path = path/to/file
     *
     *
     */
    private final static String REGEX_EXPRESSION_ACRPATH =
        "^" + // matches start
            "(\\[" +
                "((?<"+REGEX_GROUP_NAME_REF  + ">[a-zA-Z0-9._\\-]+):)?" +
                "(?<"+ REGEX_GROUP_NAME_ACR  + ">[a-zA-Z0-9._\\-]+)\\]/)?" + // matches optional base acronym. Defaults to "[0]"
                "(?<"+ REGEX_GROUP_NAME_PATH + ">[a-zA-Z0-9._\\-]+(/[a-zA-Z0-9._-]+)*)" + // matches the path to the file.
            "/?" + // optionally end with slash. (Is not captured)
        "$" // matches end
    ;

    private final static Pattern PATTERN_ACRPATH = Pattern.compile(REGEX_EXPRESSION_ACRPATH);


    private final OptionalField<String> ref;
    private final OptionalField<String> acr;
    private final String path;

    private AcrPath(@Nullable String ref, @Nullable String acr, String path, boolean nullable) {
        this.acr = OptionalField.ofNullable(acr);
        this.ref = OptionalField.ofNullable(ref);
        this.path = path;
    }

    public AcrPath(String ref, String acr, String path) {
        if (acr == null || acr.isEmpty()) {
            throw new IllegalArgumentException("acronym is empty.");
        }
        if (ref == null || ref.isEmpty()) {
            throw new IllegalArgumentException("reference is empty.");
        }
        this.acr = OptionalField.of(acr);
        this.ref = OptionalField.of(ref);
        this.path = path;
    }

    public AcrPath(String acr, String path) {
        if (acr == null || acr.isEmpty()) {
            throw new IllegalArgumentException("acronym is empty.");
        }
        this.acr = OptionalField.of(acr);
        this.ref = OptionalField.empty();
        this.path = path;
    }

    public AcrPath(String path) {
        this.acr = OptionalField.empty();
        this.ref = OptionalField.empty();
        this.path = path;
    }

    public static AcrPath parse(String acrPath) {
        Matcher matcher = PATTERN_ACRPATH.matcher(acrPath);
        boolean match = matcher.matches();
        if(match) {
            String ref = matcher.group(REGEX_GROUP_NAME_REF);
            if(ref == null || ref.isEmpty()) {
                ref = null;
            }
            String acr = matcher.group(REGEX_GROUP_NAME_ACR);
            if(acr == null || acr.isEmpty()) {
                acr = null;
            }
            String path = matcher.group(REGEX_GROUP_NAME_PATH);
            if(path == null || path.isEmpty()) {
                throw new IllegalArgumentException("The given acr-path " +
                    "doesn't contain any path: " + acrPath);
            }
            return new AcrPath(ref, acr, path, true);
        } else {
            throw new IllegalArgumentException("The given acr-path cannot be parsed: " + acrPath);
        }
    }

    public String getPath() {
        return path;
    }

    public Optional<String> getAcr() {
        return acr.opt();
    }

    public Optional<String> getRef() {
        return ref.opt();
    }

    @Override
    public String toString() {
        if(getAcr().isPresent()) {
            return
                "[" +
                    getRef().map(s -> s + ":").orElse("") + getAcr().orElse("") +
                "]/" + getPath();
        } else{
            return getPath();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcrPath acrPath = (AcrPath) o;

        if (!ref.equals(acrPath.ref)) return false;
        if (!acr.equals(acrPath.acr)) return false;
        return path.equals(acrPath.path);
    }

    @Override
    public int hashCode() {
        int result = ref.hashCode();
        result = 31 * result + acr.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }

    static class JsonDeserializer extends StdDeserializer<AcrPath> {

        protected JsonDeserializer() {
            super(AcrPath.class);
        }

        @Override
        public AcrPath deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String path = p.readValueAs(String.class);
            return parse(path);
        }
    }

    static class JsonSerializer extends StdSerializer<AcrPath> {

        protected JsonSerializer() {
            super(AcrPath.class);
        }

        @Override
        public void serialize(AcrPath value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.toString());
        }
    }
}
