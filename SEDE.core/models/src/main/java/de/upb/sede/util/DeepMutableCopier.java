package de.upb.sede.util;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DeepMutableCopier {

    private static final Logger logger = LoggerFactory.getLogger(DeepMutableCopier.class);

    private final static Set<String> WARNED_MESSAGES = Collections.synchronizedSet(new HashSet<>());

    public static <T> T copyAsMutable(Object immutableObject) {
        return (T) deepCopy(immutableObject);
    }

    private static Object deepCopy(Object source) {
        if(source == null) {
            return null;
        }
        if(source instanceof Optional) {
            if(((Optional) source).isPresent()) {
                return Optional.of(deepCopy(((Optional) source).get()));
            } else {
                return Optional.empty();
            }
        }
        if(isValueObject(source)) {
            return copyValueObject(source);
        } else if (isCollection(source)) {
            if(source instanceof Set) {
                Set sourceSet = (Set) source;
                Set sinkSet = new HashSet(sourceSet);
                sourceSet.stream().map(DeepMutableCopier::deepCopy).forEachOrdered(sinkSet::add);
                return sinkSet;
            } else {
                List sourceList = (List) source;
                List sinkList = new ArrayList(sourceList.size());
                sourceList.stream().map(DeepMutableCopier::deepCopy).forEachOrdered(sinkList::add);
                return sinkList;
            }
        } else if(isMap(source)) {
            Map sourceMap = (Map) source;
            Map sinkMap = new HashMap();
            sourceMap.forEach((k, v) -> sinkMap.put(deepCopy(k), deepCopy(v)));
            return sinkMap;
        } else if(isArr(source)) {
            Class<?> arrClass = source.getClass();
            int length = Array.getLength(source);
            Object sinkArr = Arrays.copyOf((Object[])source, length);
            for (int i = 0; i < length; i++) {
                Object mutableElement = deepCopy(Array.get(source, i));
                Array.set(sinkArr, i, mutableElement);
            }
            return sinkArr;
        }
        else {
            // In any other case (Like numbers and string) assume its getA immutable object:
            return source;
        }
    }

    private static Object copyValueObject(Object source) {
        // create getA mutable instance of the value object:
        String mutableClassName = getMutableClassName(source);
        Class<?> mutableClass;
        Method createMethod;
        try {
            mutableClass = Class.forName(mutableClassName);
            createMethod = mutableClass.getMethod("create");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No mutable class `" +  mutableClassName + "` found: " + source.getClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Mutable class doesn't have create method: " + mutableClassName, e);
        }
        Object mutableSink;
        try {
            mutableSink = createMethod.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't create mutable object of type: " + mutableClassName, e);
        }
        // copy every value where there is a setter in
        Class<?> sourceClass = source.getClass();
        Method[] methods = sourceClass.getMethods();
        for (Method getter : methods) {
            Optional<String> optProp = getSettableProperty(getter);
            if(!optProp.isPresent()) {
                continue;
            }
            String prop = optProp.get();
            String setterName = "set" + prop.substring(0, 1).toUpperCase(Locale.ENGLISH) + prop.substring(1);
            Method setter;
            Class<?> propType = getter.getReturnType();
            if(Iterable.class.isAssignableFrom(propType)) {
                propType = Iterable.class;
            }
            try {
                setter = mutableClass.getMethod(setterName, propType);
            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(String.format("No setter `%s` with type `%s` found for property `%s`.", setterName, propType.getSimpleName(), prop));
                // If setter was not found assume its derived.
                String warnMessage = String.format("No_Setter_%s_%s_%s", setterName, propType.getSimpleName(), prop);
                boolean added = WARNED_MESSAGES.add(warnMessage);
                if(added) {
                    logger.warn("No setter `{}` with type `{}` found for property `{}`. " +
                            "Assuming its lazy or derived property.",
                        setterName, propType.getSimpleName(), prop);
                }
                continue;
            }
            Object property;
            try {
                property = getter.invoke(source);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot invoke getter " + getter.getName() + " from " + sourceClass.getSimpleName());
            }
            Object mutableProp = deepCopy(property);
            try {
                setter.invoke(mutableSink, mutableProp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot invoke setter " + setter.getName() + " from " + mutableClassName);
            }
        }
        return mutableSink;
    }

    private static Optional<String> getSettableProperty(Method method) {
        boolean annotationPresent = method.isAnnotationPresent(JsonProperty.class);
        if(annotationPresent) {
            JsonProperty annotation = method.getAnnotation(JsonProperty.class);
            return Optional.of(annotation.value());
        }
        return Optional.empty();
    }

    private static String getMutableClassName(Object valueObject) {
        String className = valueObject.getClass().getSimpleName();
        if(className.startsWith("Mutable")) {
            return valueObject.getClass().getName();
        } else {
            // Inject mutable into the path:
            Package packageObject = valueObject.getClass().getPackage();
            String packageName;
            if(packageObject == null) {
                // in java 8 packageObject is null when a class is in an unnamed package.
                packageName = "";
            } else {
                packageName = packageObject.getName();
            }
            if(packageName.isEmpty()) {
                return "Mutable" + className;
            } else {
                return packageName + ".Mutable" + className;
            }
        }
    }

    static boolean isValueObject(Object o) {
        return DeepImmutableCopier.implementsSMSStyleInterface(o.getClass());
    }

    static boolean isCollection(Object o) {
        return o instanceof Collection;
    }

    static boolean isMap(Object o) {
        return o instanceof Map;
    }

    private static boolean isArr(Object o) {
        return (o.getClass().isArray());
    }

}
