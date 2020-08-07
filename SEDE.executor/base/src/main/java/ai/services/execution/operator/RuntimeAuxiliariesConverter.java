package ai.services.execution.operator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class RuntimeAuxiliariesConverter {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeAuxiliariesConverter.class);

    private RuntimeAuxiliariesConverter() {

    }

    private static final ThreadLocal<ObjectMapper> THREAD_LOCAL_MAPPER = ThreadLocal.withInitial(() ->
        new ObjectMapper()
            .configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));


    public static <T> Optional<T> convertAuxiliaries(Map<String,Object> runtimeAux, Class<T> targetAux) {
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
}
