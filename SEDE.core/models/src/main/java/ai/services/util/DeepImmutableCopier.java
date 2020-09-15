package ai.services.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.services.SEDEModelStyle;

import java.util.stream.Stream;

public class DeepImmutableCopier {

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



    public static <T> T copyAsImmutable(Object input) {
        if(!implementsSMSStyleInterface(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unknown type: " + input.getClass().getName());
        }
        String immutableClassName = getInterfaceClassName(input);
        Class<?> immutableClass;
        try {
            immutableClass = input.getClass().forName(immutableClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                String.format("Value object %s doesn't have an immutable class with %s.",
                    input.getClass().getSimpleName(), immutableClassName),
                e);
        }
        return (T) copyAsImmutable(input, immutableClass);
    }

    public static <T> T copyAsImmutable(Object input, Class<T> tClass) {
        if(!implementsSMSStyleInterface(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unknown type: " + input.getClass().getName());
        }
        if(!isSMStyleAnnotated(tClass)) {
            throw new IllegalArgumentException("Cannot convert to unknown class: " + tClass);
        }
        return MAPPER.convertValue(input, tClass);
    }

    static <T> boolean isSMStyleAnnotated(Class<T> tClass) {
        return tClass.isAnnotationPresent(SEDEModelStyle.class);
    }

    static <T> boolean implementsSMSStyleInterface(Class<T> tClass) {
        return Stream.of(tClass.getInterfaces()).anyMatch(DeepImmutableCopier::isSMStyleAnnotated);
    }


    private static String getInterfaceClassName(Object valueObject) {
        String className = valueObject.getClass().getSimpleName();
        String prefix;
        if(className.startsWith("Mutable"))
            prefix = "Mutable";
        else
            prefix = "";
        // Remove Mutable from the path:
        Package packageObject = valueObject.getClass().getPackage();
        String packageName;
        if(packageObject == null) {
            // in java 8 packageObject is null when a class is in an unnamed package.
            packageName = "";
        } else {
            packageName = packageObject.getName();
        }
        String interfaceClassname = "I" + className.substring(prefix.length());
        if(packageName.isEmpty()) {
            return interfaceClassname;
        } else {
            return packageName + "." + interfaceClassname;
        }
    }

}
