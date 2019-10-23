package de.upb.sede.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.SEDEModelStyle;

import java.util.stream.Stream;

public class SDLUtil {

    private static ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <I, T> T toImmutable(I input, Class<T> tClass) {
        if(!isSMStyleAnnotated(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unkown type: " + input.getClass().getName());
        }
        if(!isSMStyleAnnotated(tClass)) {
            throw new IllegalArgumentException("Cannot convert to unkown immutable class: " + tClass);
        }
        return MAPPER.convertValue(input, tClass);
    }

    private static <T> boolean isSMStyleAnnotated(Class<T> tClass) {
        return Stream.of(tClass.getInterfaces()).anyMatch(i -> i.isAnnotationPresent(SEDEModelStyle.class));
    }
}
