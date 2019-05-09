package de.upb.sede.config;

import de.upb.sede.util.WobblyField;

import java.io.Serializable;
import java.util.*;

public final class ClassSpecification implements Serializable {

    private final Map<String, Object> configuration;
    private final WobblyField<ClassSpecification> wrapper;
    private final String cp;

    /**
     * Creates a classinfo without a wrapper.
     */
    private ClassSpecification(String classpath, Map<String, Object> config) {
        this(classpath, config, null);
    }

    /**
     * Creates a classinfo with the given wrapper. The given wrapper is itself another ClassInfo instance.
     * @param classpath
     * @param jsonMap
     * @param wrapper
     */
    private ClassSpecification(String classpath, Map<String, Object> jsonMap, ClassesConfig.ClassInfo wrapper) {
        cp = classpath;
        this.configuration = jsonMap;
        this.wrapper = WobblyField.ofNullable(null);
    }

    /**
     * 	Returns the "methods" json object from the classinfo root.
     */
    private Map<String, Object> getInternalMethods() {
        if (configuration.containsKey("methods")) {
            return (Map<String, Object>) configuration.get("methods");
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    /**
     * Returns list of method names that are accessible.
     */
    public Set<String> getAccessibleMethods() {
        Set<String> methods = new HashSet<>();
        methods.addAll(getInternalMethods().keySet());
        if(wrapper.isPresent()) {
            methods.addAll(wrapper.get().getAccessibleMethods());
        }
        return methods;
    }

    /**
     * @return true if this class offers the given method by name.
     */
    private boolean hasMethod(String methodname) {
        return getInternalMethods().containsKey(methodname);
    }

//    /**
//     * @return constructor method info
//     */
//    public ClassesConfig.MethodInfo constructInfo() {
//        if (hasMethod("$construct")) {
//            Map<String, Object> constructMap = ClassesConfig.MethodInfo.emptyConstructor(cp).configuration;
//            constructMap.putAll((Map<String, Object>) getInternalMethods().get("$construct"));
//            return new ClassesConfig.MethodInfo(cp, "$construct", constructMap);
//        } else if(wrapper.isPresent()){
//            return wrapper.get().constructInfo();
//        } else {
//            return ClassesConfig.MethodInfo.emptyConstructor(cp);
//        }
//    }
//
//    /**
//     *
//     * @return true if the class is marked abstract. (cannot be instantiated)
//     */
//    public boolean isAbstract() {
//        if(configuration.containsKey("abstract")){
//            return (Boolean) configuration.get("abstract");
//        }
//        if(className().startsWith("$") && className().endsWith("$")) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @return true if a wrapper was defined.
//     * If a class is wrapped it is the wrapper class that is instantiated instead of the class itself.
//     * If true the class might not even exist.
//     */
//    public boolean isWrapped() {
//        return wrapper.isPresent();
//    }
//
//    /**
//     * @return class path of this class that will be instantiated.
//     */
//    public String actualClasspath() {
//        if(isWrapped()) {
//            return wrapper.get().actualClasspath();
//        } else {
//            return cp;
//        }
//    }
//
//    /**
//     * Given a method name this function returns the name of the service method that actually realises the functionality.
//     * By default methods are realised by themselves.
//     * If the method configuration contains the field 'realisedby' it is realised by the method name of the fields value.
//     * Conducts deep search.
//     *
//     * @param methodname method name whose realisation method is searched.
//     * @return name of the method realisation
//     */
//    public String actualMethoname(String methodname) {
//        ClassesConfig.MethodInfo methodInfo = methodInfo(methodname);
//        String realisedMethodName = methodInfo.getRealisation();
//        if(realisedMethodName.equals(methodname)||
//                (methodname.equals("__construct") && realisedMethodName.equals("$construct"))) {
//            /*
//             * Method is realised by itself:
//             */
//            return methodname;
//        } else {
//            return actualMethoname(realisedMethodName);
//        }
//    }
//
//    /**
//     * Returns the corresponding method info to the given name.
//     */
//    public ClassesConfig.MethodInfo methodInfo(String methodname) {
//        if(methodname.equals("$construct") || methodname.equals("__construct")) {
//            return constructInfo();
//        }else if (hasMethod(methodname)) {
//            Map methodConfig = (Map) getInternalMethods().get(methodname);
//            ClassesConfig.MethodInfo method = new ClassesConfig.MethodInfo(cp, methodname, methodConfig);
//            if(!method.overloadRealisation()) {
//                return method;
//            } else {
//                /*
//                 * overload the config map of the realised method:
//                 */
//                ClassesConfig.MethodInfo realisedMethod = methodInfo(method.getRealisation());
//                Map<String,Object> overloadedConfig = new HashMap<>(realisedMethod.configuration);
//                overloadedConfig.putAll(method.configuration);
//                return new ClassesConfig.MethodInfo(cp, methodname, overloadedConfig);
//            }
//        } else if(isWrapped()) {
//            try{
//                return wrapper.get().methodInfo(methodname);
//            } catch(RuntimeException ex) {
//                throw new RuntimeException("Method " + methodname + " not found in: "  + cp);
//            }
//        }else{
//            throw new RuntimeException("Method " + methodname + " not found in: "  + cp);
//        }
//    }
//
//    public String className() {
//        return cp;
//    }

}
