package ai.services.execution.operator.local;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.composition.graphs.nodes.WithAux;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

public class AuxHelper {


    private static final Logger logger = LoggerFactory.getLogger(AuxHelper.class);

    private AuxHelper() {

    }

    private static final ThreadLocal<ObjectMapper> THREAD_LOCAL_MAPPER = ThreadLocal.withInitial(() ->
        new ObjectMapper()
            .configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));


    static <T> Optional<T> convertAuxiliaries(WithAux withAuxObj, Class<T> targetAux) {
        return convertAuxiliaries(withAuxObj.getRuntimeAuxiliaries(), targetAux);
    }

    static <T> Optional<T> convertAuxiliaries(Map<String,Object> runtimeAux, Class<T> targetAux) {
        if(runtimeAux == null || runtimeAux.isEmpty()) {
            return Optional.empty();
        }
        ObjectMapper mapper = THREAD_LOCAL_MAPPER.get();
        try {
            T dispatch = mapper.convertValue(runtimeAux, targetAux);
            return Optional.of(dispatch);
        } catch (Exception ex) {
            logger.warn("Couldn't convert aux map to {}. Map: {} \n", targetAux.getSimpleName(), runtimeAux, ex);
            return Optional.empty();
        }
    }

    static List<Executable> resolveExecutables(IJavaDispatchAux javaDispatch) throws ClassNotFoundException {
        Boolean isConstructor = javaDispatch.constructorInvocation();
        isConstructor = isConstructor != null && isConstructor;
        String methodname = javaDispatch.methodName();
        Class<?> context = Class.forName(javaDispatch.className());
        List<Executable> executableList = new ArrayList<>();
        if(isConstructor) {
            Collections.addAll(executableList, context.getConstructors());
        } else {
            for(Method method : context.getMethods()){
                if(method.getName().equals(methodname)) {
                    executableList.add(method);
                }
            }
        }
        return executableList;
    }

    static Optional<Executable> matchingExecutable(IJavaDispatchAux javaDispatch, Predicate<Executable> matcher) throws ClassNotFoundException {
        List<Executable> executables = resolveExecutables(javaDispatch);
        for (Executable exec : executables) {
            if (matcher.test(exec)) {
                return Optional.ofNullable(exec);
            }
        }
        return Optional.empty();
    }

}
