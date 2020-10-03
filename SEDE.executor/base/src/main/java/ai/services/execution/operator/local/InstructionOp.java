package ai.services.execution.operator.local;

import ai.services.core.*;
import ai.services.execution.FieldContext;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.execution.operator.OpException;
import ai.services.execution.operator.ServiceInstanceFactory;
import ai.services.composition.graphs.nodes.BaseNode;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.composition.types.IDataValueType;
import ai.services.composition.types.IPrimitiveValueType;
import ai.services.composition.types.IServiceInstanceType;
import ai.services.composition.types.TypeClass;
import ai.services.exec.auxiliary.IJavaDispatchAux;
import ai.services.exec.auxiliary.JavaDispatchAux;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Handles instruction tasks, by finding and invoking java methods.
 */
public class InstructionOp extends MainTaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(InstructionOp.class);

    private final ServiceInstanceFactory serviceInstanceFactory;

    public InstructionOp(ServiceInstanceFactory serviceInstanceFactory) {
        super(IInstructionNode.class);
        this.serviceInstanceFactory = serviceInstanceFactory;
    }

    @Override
    public TaskTransition runTask(Task task) throws Exception {
        BaseNode node = task.getNode();
        if (!(node instanceof IInstructionNode)) {
            throw new OpException("Task type mismatch: " + task);
        }
        IInstructionNode instNode = (IInstructionNode) node;

        if (instNode.isAssignment() && instNode.getFieldType() == null) {
            throw new OpException("Assignment has no type specified: " + instNode);
        }
        /*
         * First fetch the context and parameters using the execution context and the instruction line itself.
         */
        Optional<Object> context = fetchContextValue(instNode, task.getFieldContext());
        Object[] paramValues = fetchParamValues(instNode, task.getFieldContext());
        /*
         * Next create a dispatch aux which is used to find the handle that runs the instruction.
         * Dispatch aux can be defined by the service description.
         * If it is undefined, we default to the inspection of the collected types:
         */
        IJavaDispatchAux javaDispatch = constructDispatchAux(instNode, context, paramValues);


        castNumbers(javaDispatch, paramValues);
        Object returnVal = invokeQuietly(instNode, javaDispatch, context, paramValues);

        TaskTransition taskTransition;
        if (instNode.isAssignment()) {
            SEDEObject newField = createOutputSEDEObject(instNode, javaDispatch, returnVal, paramValues);
            taskTransition = TaskTransition.fieldAssignment(instNode.getFieldName(), newField);
        } else {
            taskTransition = TaskTransition.pass();
        }
        task.setMainTaskPerformed();
        return taskTransition;
    }

    private Optional<Object> fetchContextValue(IInstructionNode inst, FieldContext fieldContext) {
        if (inst.getContextIsFieldFlag()) {
            String context = inst.getContext();
            SEDEObject fieldValue = fieldContext.hasField(context) ? fieldContext.getFieldValue(context) : null;
            if (fieldValue == null || !fieldValue.isServiceInstance()) {
                String contextStrMessage;
                if (!fieldContext.hasField(context)) {
                    contextStrMessage = "not-present";
                } else if (fieldValue == null) {
                    contextStrMessage = "null";
                } else {
                    contextStrMessage = fieldValue.toString();
                }
                throw new OpException("Instruction node " + inst + " has a service instance context, " +
                    "but the execution field context maps to: " + contextStrMessage);
            }
            return Optional.of(fieldValue.getServiceInstance());
        } else {
            return Optional.empty();
        }
    }

    private Object invokeQuietly(IInstructionNode node, IJavaDispatchAux javaDispatch, Optional<Object> contextValue, Object[] paramValues){
        try {
            return invoke(javaDispatch, contextValue, paramValues);
        } catch(Exception ex) {
            throw constructErrorMsg(node, javaDispatch, contextValue, paramValues, ex);
        }
    }

    private RuntimeException constructErrorMsg(IInstructionNode node, IJavaDispatchAux javaDispatch,
                                               Optional<Object> contextValue, Object[] paramValues,
                                               Exception cause) {
        StringBuilder builder = new StringBuilder();
        builder.append("Error trying to handle instruction: ").append(node.getText()).append("\n");
        builder.append("\tJava dispatch that was used: ").append(javaDispatch).append("\n");
        Function<Object, String> typologist =  obj -> {
          if(obj == null) {
              return "null";
          } else {
              return "class: " + obj.getClass().getSimpleName() + ", toString(): " + obj.toString().subSequence(0, 64);
          }
        };
        builder.append("\tInstance:\n\t\t").append(typologist.apply(contextValue.orElse(null))).append("\n");
        builder.append("\tParameters:\n");
        for (Object paramValue : paramValues) {
            builder.append("\t\t").append(typologist.apply(paramValue)).append("\n");
        }
        builder.append("Cause exception message: ").append(cause.getMessage());
        return new RuntimeException(builder.toString(), cause);
    }

    private Object invoke(IJavaDispatchAux javaDispatch, Optional<Object> contextValue, Object[] paramValues)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Object outputValue;
        String methodname = javaDispatch.methodName();
        final Class<?> contextClass = Class.forName(javaDispatch.className());
        if (javaDispatch.constructorInvocation()) {
            /*
                call constructor and create the service:
             */
            outputValue = ConstructorUtils.invokeConstructor(contextClass, paramValues);
        } else if(javaDispatch.staticInvocation()) {
            /*
                call a static method:
             */
            outputValue = MethodUtils.invokeStaticMethod(contextClass, methodname, paramValues);
        } else {
            /*
                call object method:
             */
            assert contextValue.isPresent();
            final Object contextInstance = contextValue.get();
            outputValue = invokeInstanceMethod(javaDispatch, contextInstance, contextClass, methodname, paramValues);
        }
        return outputValue;
    }

    private Object invokeInstanceMethod(IJavaDispatchAux javaDispatch, final Object contextInstance, final Class<?> contextClass, final String methodname, Object[] paramValues) throws InvocationTargetException, IllegalAccessException {
        final Class<?> [] paramClasses = new Class<?>[paramValues.length];
        final Class<?> [] paramClassesWithInstance = new Class<?>[1 + paramValues.length];
        final Object[] paramValuesWithInstance = new Object[1 + paramValues.length];
        paramClassesWithInstance[0] = contextClass;
        paramValuesWithInstance[0] = contextInstance;
        for (int i = 0; i < paramValues.length; i++) {
            Object param = paramValues[i];
            Class<?> paramClass = param.getClass();
            paramClasses[i] = paramClass;
            paramClassesWithInstance[i+1] = paramClass;
            paramValuesWithInstance[i+1] = param;
        }
        final List<Class<?>[]> signOpts = Arrays.asList(
            paramClasses, paramClassesWithInstance
        );
        final List<Object> callers = Arrays.asList(contextInstance, null);
        final List<Supplier<Object[]>> parameters = Arrays.asList(
            () -> paramValues, () -> paramValuesWithInstance
        );
        Object[] output = new Object[1];
        boolean matchFound = ReflectiveInvocation.invokeMatch(contextClass, methodname, signOpts, callers, parameters, output);
        if(!matchFound) {
            throw new IllegalStateException("No matching method found for java dispatch: " + javaDispatch);
        }
        return output[0];
    }

    private SEDEObject createOutputSEDEObject(IInstructionNode instNode, IJavaDispatchAux javaDispatch,
                                              Object returnVal, Object[] paramValues) {
        int redirectArg = javaDispatch.redirectArg();
        Object fieldValue;
        if (redirectArg == -1) {
            fieldValue = returnVal;
        } else if (redirectArg < paramValues.length) {
            fieldValue = paramValues[redirectArg];
        } else {
            throw new RuntimeException("BUG, this should have been caught before.");
        }
        return createOutputSEDEObject(instNode.getFieldType(), fieldValue);
    }

    private SEDEObject createOutputSEDEObject(TypeClass fieldType, Object fieldValue) {
        SEDEObject returnObject;
        if (fieldType instanceof IServiceInstanceType) {
            ServiceInstance serviceInstance = serviceInstanceFactory
                .createServiceInstanceHandle((IServiceInstanceType) fieldType, fieldValue);
            returnObject = new ServiceInstanceField(serviceInstance);
        } else if (fieldType instanceof IPrimitiveValueType) {
            Primitives primitives = ((IPrimitiveValueType) fieldType).getPrimitiveType();
            returnObject = new PrimitiveDataField(primitives, fieldValue);
        } else if (fieldType instanceof IDataValueType) {
            String typeQualifier = fieldType.getTypeQualifier();
            returnObject = new ObjectDataField(typeQualifier, fieldValue);
        } else {
            throw new OpException("Field assignment type is unrecognized: " + fieldType);
        }
        return returnObject;
    }

    private Object[] fetchParamValues(IInstructionNode inst, FieldContext fieldContext) {
        int paramSize = inst.getParameterFields().size();
        Object[] paramValues = new Object[paramSize];
        for (int paramIndex = 0; paramIndex < paramSize; paramIndex++) {
            String fieldname = inst.getParameterFields().get(paramIndex);
            if (!fieldContext.hasField(fieldname)) {
                throw new OpException("Field context doesn't have parameter " + fieldname +
                    " that is used by instruction: " + inst);
            }
            SEDEObject field = fieldContext.getFieldValue(fieldname);
            Object fieldValue;
            if (field.isServiceInstance()) {
                fieldValue = field.getServiceInstance();
            } else {
                fieldValue = field.getDataField();
            }
            paramValues[paramIndex] = fieldValue;
        }
        return paramValues;
    }

    private IJavaDispatchAux constructDispatchAux(IInstructionNode inst, Optional<Object> serviceContext, Object[] paramValues) {
        IJavaDispatchAux javaDispatch;
        IJavaDispatchAux inferredDispatch = inferDispatchFromNode(inst, serviceContext, paramValues);
        Optional<IJavaDispatchAux> runtimeAux = AuxHelper.convertAuxiliaries(inst, IJavaDispatchAux.class);
        javaDispatch = runtimeAux
            .map(aux -> mergeJavaDispatch(inferredDispatch, aux))
            .orElse(inferredDispatch);
        if (javaDispatch.redirectArg() != -1 && javaDispatch.redirectArg() >= paramValues.length) {
            throw new RuntimeException(String.format("Java dispatch has redirectArg %d but only %d many parameters.",
                javaDispatch.redirectArg(), paramValues.length));
        }
        return javaDispatch;
    }

    private IJavaDispatchAux inferDispatchFromNode(IInstructionNode inst, Optional<Object> serviceContext, Object[] paramValues) {
        JavaDispatchAux.Builder builder = JavaDispatchAux.builder();
        if (serviceContext.isPresent()) {
            builder.className(serviceContext.get().getClass().getName());
            builder.staticInvocation(false);
            builder.constructorInvocation(false);
        } else {
            builder.className(inst.getContext());
            builder.staticInvocation(true);
            builder.constructorInvocation(inst.getMethod().equals(IJavaDispatchAux.CONSTRUCTOR_METHOD_NAME));
        }
        builder.methodName(inst.getMethod());
        for (int i = 0; i < paramValues.length; i++) {
            builder.addParamTypes(paramValues.getClass().getName());
        }
        return builder.build();
    }

    private IJavaDispatchAux mergeJavaDispatch(IJavaDispatchAux inferred, IJavaDispatchAux aux) {
        JavaDispatchAux.Builder builder = JavaDispatchAux.builder();
        builder.from(inferred);
        if (aux.staticInvocation() != null) {
            builder.staticInvocation(aux.staticInvocation());
        }
        if (aux.constructorInvocation() != null) {
            builder.constructorInvocation(aux.constructorInvocation());
        }
        if (aux.redirectArg() != -1) {
            builder.redirectArg(aux.redirectArg());
        }
        if (aux.methodName() != null) {
            builder.methodName(aux.methodName());
        }
        if (aux.className() != null) {
            builder.className(aux.className());
        }
        if (aux.paramTypes() != null) {
            builder.paramTypes(aux.paramTypes());
        }
        return builder.build();
    }

    private void castNumbers(IJavaDispatchAux javaDispatch,
                             Object[] parameterValues) throws ClassNotFoundException, NoSuchMethodException {
        Executable executable = reflectExecutable(javaDispatch, parameterValues);
        castNumbers(executable.getParameterTypes(), parameterValues);
    }

    private Executable reflectExecutable(IJavaDispatchAux javaDispatch,
                                         Object[] parameterValues) throws ClassNotFoundException, NoSuchMethodException {
        Optional<Executable> matching = AuxHelper.matchingExecutable(javaDispatch, exec -> matchesParams(exec, parameterValues));
        if(matching.isPresent()) {
            return matching.get();
        }
        throw new NoSuchMethodException("No matching method found for signature:\n\t"
            + javaDispatch.className() + "::" + javaDispatch.methodName()
            + "(" + Arrays.toString(ClassUtils.toClass(parameterValues)) + ")");
    }

    private boolean matchesParams(Executable executable, Object[] parameterValues) {
        if (executable.getParameterCount() != parameterValues.length) {
            return false;
        }
        boolean fail = false;
        for (int i = 0; i < parameterValues.length; i++) {
            Object param = parameterValues[i];
            if (param == null) {
                continue;
            }
            if (!(param instanceof Number) && !(param instanceof Boolean) && !executable.getParameterTypes()[i].isAssignableFrom(param.getClass())) {
                fail = true;
                break;
            }
        }
        if (fail) {
            return false;
        }
        return true;
    }

    private void castNumbers(Class<?>[] expectedClasses, Object[] parameterValues) {
        for (int i = 0, size = parameterValues.length; i < size; i++) {
            if (parameterValues[i] instanceof Number) {
                Class<?> exptectedClass = expectedClasses[i];
                Object numberValue = parameterValues[i];
                Number givenNmber = (Number) numberValue;
                Number castedNumber;
                if (exptectedClass == int.class || exptectedClass == Integer.class) {
                    castedNumber = givenNmber.intValue();
                } else if (exptectedClass == byte.class || exptectedClass == Byte.class) {
                    castedNumber = givenNmber.byteValue();
                } else if (exptectedClass == short.class || exptectedClass == Short.class) {
                    castedNumber = givenNmber.shortValue();
                } else if (exptectedClass == long.class || exptectedClass == Long.class) {
                    castedNumber = givenNmber.longValue();
                } else if (exptectedClass == float.class || exptectedClass == Float.class) {
                    castedNumber = givenNmber.floatValue();
                } else if (exptectedClass == double.class || exptectedClass == Double.class) {
                    castedNumber = givenNmber.doubleValue();
                } else {
                    throw new RuntimeException("Cannot cast number to the expected class: " + exptectedClass);
                }
                parameterValues[i] = castedNumber;
            }
        }
    }
}
