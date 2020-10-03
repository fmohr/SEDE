package ai.services.execution.operator.local;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

public class ReflectiveInvocation {

    static boolean invokeMatch(Class<?> clazz, String methodName,
                               List<Class<?>[]> signatureOptions,
                               List<Object> callers,
                               List<Supplier<Object[]>> parameters,
                               Object[] output) throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < signatureOptions.size(); i++) {
            Class<?>[] signatureOpt = signatureOptions.get(i);
            Method method = MethodUtils.getMatchingMethod(clazz, methodName, signatureOpt);
            if(method != null) {
                Object caller = callers.get(i);
                if(!method.canAccess(caller)) {
                    method.setAccessible(true);
                }
                Object[] parameterValues = parameters.get(i).get();
                Object out = method.invoke(caller, parameterValues);
                output[0] = out;
                return true;
            }
        }
        return false;
    }

}
