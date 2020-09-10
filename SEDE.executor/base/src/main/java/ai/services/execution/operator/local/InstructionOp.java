package ai.services.execution.operator.local;

import ai.services.execution.FieldContext;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.execution.operator.OpException;
import ai.services.execution.operator.ServiceInstanceFactory;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.types.IDataValueType;
import de.upb.sede.composition.types.IPrimitiveValueType;
import de.upb.sede.composition.types.IServiceInstanceType;
import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.core.*;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import de.upb.sede.exec.auxiliary.JavaDispatchAux;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
        Object returnVal = invoke(javaDispatch, context, paramValues);

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
            PrimitiveType primitiveType = ((IPrimitiveValueType) fieldType).getPrimitiveType();
            returnObject = new PrimitiveDataField(primitiveType, fieldValue);
        } else if (fieldType instanceof IDataValueType) {
            String typeQualifier = fieldType.getTypeQualifier();
            returnObject = new ObjectDataField(typeQualifier, fieldValue);
        } else {
            throw new OpException("Field assignment type is unrecognized: " + fieldType);
        }
        return returnObject;
    }

    private Object invoke(IJavaDispatchAux javaDispatch, Optional<Object> contextValue, Object[] paramValues)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        Object outputValue;
        String methodname = javaDispatch.methodName();
        if (javaDispatch.constructorInvocation() || javaDispatch.staticInvocation()) {
            /*
                call constructor and create the service:
             */
            Class<?> contextCls = Class.forName(javaDispatch.className());
            if (javaDispatch.constructorInvocation()) {
                outputValue = ConstructorUtils.invokeConstructor(contextCls, paramValues);
            } else {
            /*
                call a static method:
             */
                outputValue = MethodUtils.invokeStaticMethod(contextCls, methodname, paramValues);
            }
        } else {
            /*
                call object method:
             */
            assert contextValue.isPresent();
            outputValue = MethodUtils.invokeMethod(contextValue.get(), methodname, paramValues);
        }
        return outputValue;
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
        Optional<IJavaDispatchAux> runtimeAux = AuxHelper.convertAuxiliaries(inst.getRuntimeAuxiliaries(), IJavaDispatchAux.class);
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
