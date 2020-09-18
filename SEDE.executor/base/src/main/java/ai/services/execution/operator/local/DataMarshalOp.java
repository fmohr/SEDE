package ai.services.execution.operator.local;

import ai.services.composition.types.*;
import ai.services.core.*;
import ai.services.exec.auxiliary.IJavaMarshalAux;
import ai.services.exec.auxiliary.IStdTypeAux;
import ai.services.exec.auxiliary.JavaMarshalAux;
import ai.services.exec.auxiliary.StdTypeAux;
import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.services.composition.graphs.nodes.IMarshalNode;
import ai.services.composition.types.serialization.IMarshalling;
import ai.services.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataMarshalOp extends MainTaskOperator {

    private final static Logger logger = LoggerFactory.getLogger(DataMarshalOp.class);

    private final static IStdTypeAux EMPTY_TYPE_AUX = StdTypeAux.builder().build();

    private final static IJavaMarshalAux DEFAULT_MARSHAL = JavaMarshalAux.builder().build();

    private final static ObjectMapper MAPPER = new ObjectMapper().setBase64Variant(Base64Variants.getDefaultVariant());

    public DataMarshalOp() {
        super(IMarshalNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IMarshalNode node = (IMarshalNode) t.getNode();
        String fieldname = node.getFieldName();
        SEDEObject fieldValue = t.getFieldContext().getFieldValue(fieldname);

        IJavaMarshalAux marshalAux = inferMarshalMethod(node);

        TaskTransition tt;
        if(node.getMarshalling().getDirection().equals(IMarshalling.Direction.UNMARSHAL)) {
            tt = unmarshal(node, fieldValue, marshalAux);
        } else {
            tt = marshal(node, fieldValue, marshalAux);
        }
        t.setMainTaskPerformed();
        return tt;
    }

    private TaskTransition marshal(IMarshalNode node, SEDEObject fieldValue, IJavaMarshalAux marshalAux) throws IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        String fieldname = node.getFieldName();
        if((fieldValue instanceof SemanticDataField)) {
            throw new IllegalStateException(String.format("Field %s is of semantic type: %s. Cannot marshal it.", fieldname, fieldValue.toString()));
        }
        IMarshalling marshalling = node.getMarshalling();
        TypeClass srcType = marshalling.getValueType();

        byte[] serialisation;
        if(TypeClass.isServiceHandle(srcType) && ! (fieldValue instanceof ServiceInstanceField)) {
                throw new IllegalArgumentException("Type mismatch. " + StringUtil.unexpectedTypeMsg("ServiceInstanceField", fieldValue));
        }
        if(fieldValue.isServiceInstanceHandle() && !fieldValue.isServiceInstance()) {
            serialisation = ServiceInstanceHandleSerialisation.serialiseServiceInstance(fieldValue.getServiceHandle());
        } else {
            Object toBeSerialised;
            if(fieldValue.isServiceInstance()) {
                toBeSerialised = fieldValue.getServiceInstance();
            } else {
                toBeSerialised = fieldValue.getDataField();
            }
            serialisation = writeObject(toBeSerialised, srcType, marshalAux);
            if(fieldValue.isServiceInstanceHandle()) {
                serialisation = writeServiceInstanceHandle(fieldValue.getServiceHandle(), serialisation);
            }
        }
        SemanticDataField semanticDataField = new SemanticDataField(marshalling.getSemanticName(), serialisation);
        return TaskTransition.fieldAssignment(fieldname, semanticDataField);
    }

    private byte[] writeServiceInstanceHandle(ServiceInstanceHandle serviceInstance, byte[] instanceSerialisation) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator jGenerator = jsonFactory
            .createGenerator(outputStream, JsonEncoding.UTF8);
        jGenerator.writeStringField("classpath", serviceInstance.getClasspath());
        jGenerator.writeStringField("executorId", serviceInstance.getExecutorId());
        jGenerator.writeStringField("id", serviceInstance.getId());
        jGenerator.writeFieldName("instance");
        jGenerator.writeBinary(Base64Variants.getDefaultVariant(), instanceSerialisation, 0, instanceSerialisation.length);
        jGenerator.writeEndObject();
        jGenerator.close();
        return outputStream.toByteArray();
    }

    private byte[] writeObject(Object o, TypeClass supplyType, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if(TypeClass.isPrimitive(supplyType)) {
            return writePrimitiveField(o);
        }

        if(marshalAux.useObjectSerialisation()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectIn  = new ObjectOutputStream(outputStream);
            objectIn.writeObject(o);
            objectIn.close();
            return outputStream.toByteArray();
        }
        if(marshalAux.useJacksonSerialisation()) {
            return MAPPER.writeValueAsBytes(o);
        }
        Class<?> inferredClass = inferClass(supplyType, marshalAux);
        String methodName = inferMethod(marshalAux, supplyType, IMarshalling.Direction.MARSHAL);
        String mappedJavaClass = supplyType.getTypeQualifier();
        if(marshalAux.getMappedJavaClass() != null) {
            mappedJavaClass = marshalAux.getMappedJavaClass();
        }

        return marshalUsingReflection(inferredClass, methodName, supplyType, mappedJavaClass, o);
    }

    private byte[] writePrimitiveField(Object o) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(o);
    }

    private TaskTransition unmarshal(IMarshalNode node, SEDEObject fieldValue, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        String fieldname = node.getFieldName();
        if(!(fieldValue instanceof SemanticDataField)) {
            throw new IllegalStateException(String.format("Field %s is not of semantic type. Instead it is: %s", fieldname, fieldValue.toString()));
        }
        IMarshalling marshalling = node.getMarshalling();
        TypeClass targetType = marshalling.getValueType();
        final Function<Object, SEDEObject> objectWrapper = getObjectWrapper(targetType);

        final SemanticDataField semanticField = (SemanticDataField) fieldValue;
        final byte[] data = semanticField.getDataField();

        Object unmarshalledValue;


        if(TypeClass.isServiceHandle(targetType)) {
            unmarshalledValue = readServiceInstanceHandle(data, targetType, marshalAux);
        } else {
            unmarshalledValue = readObject(data, targetType, marshalAux);
        }
        SEDEObject unmarshalledField = objectWrapper.apply(unmarshalledValue);
        return TaskTransition.fieldAssignment(fieldname, unmarshalledField);
    }

    private Object readServiceInstanceHandle(byte[] data, TypeClass supplyType, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        @SuppressWarnings({"unchecked"})
        Map<String, Object> map = MAPPER.readValue(data, Map.class);
        ServiceInstanceHandle handle = new ServiceInstanceHandle();
        handle.fromJson(map);
        if(map.containsKey("instance") && map.get("instance") != null) {
            String base64Encoded = (String) map.get("instance");
            byte[] serviceInstanceSerialised = MAPPER.readValue(base64Encoded, byte[].class);
            Object serviceInstance = readObject(serviceInstanceSerialised, supplyType, marshalAux);
            return new ServiceInstance(handle, serviceInstance);
        } else {
            return handle;
        }
    }

    private Object readObject(byte[] data, TypeClass supplyType, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if(TypeClass.isPrimitive(supplyType)) {
            return readPrimitive(data, (IPrimitiveValueType) supplyType);
        }
        if(marshalAux.useObjectSerialisation()) {
            ObjectInputStream objectIn  = new ObjectInputStream(new ByteArrayInputStream(data));
            return objectIn.readObject();
        }
        Class<?> inferredClass = inferClass(supplyType, marshalAux);
        if(marshalAux.useJacksonSerialisation()) {
            return MAPPER.readValue(data, inferredClass);
        }
        String methodName = inferMethod(marshalAux, supplyType, IMarshalling.Direction.UNMARSHAL);

        String mappedJavaClass = supplyType.getTypeQualifier();
        if(marshalAux.getMappedJavaClass() != null) {
            mappedJavaClass = marshalAux.getMappedJavaClass();
        }

        return unmarshalUsingReflection(inferredClass, methodName, supplyType, mappedJavaClass, data);
    }

    private Object readPrimitive(byte[] data, IPrimitiveValueType primitiveValueType) throws IOException {
        Class<?> expectedPrimitiveType;
        switch (primitiveValueType.getPrimitiveType()) {
            case Number:
                expectedPrimitiveType = Number.class;
                break;
            case String:
                expectedPrimitiveType = String.class;
                break;
            case Bool:
                expectedPrimitiveType = Boolean.class;
                break;
            case NULL:
                expectedPrimitiveType = Void.class;
                break;
            default:
                throw new IllegalStateException("Unrecognized primitive value type: " + primitiveValueType);
        }
        return MAPPER.readValue(data, expectedPrimitiveType);
    }


    private byte[] marshalUsingReflection(Class<?> clazz, String methodName, TypeClass srcType, String mappedClass, Object o)
            throws InvocationTargetException, IllegalAccessException {
        final List<Class<?>[]> signatureOptions = Arrays.asList(
            new Class<?>[] {},
            new Class<?>[] {TypeClass.class},
            new Class<?>[] {String.class},
            new Class<?>[] {o.getClass()},
            new Class<?>[] {TypeClass.class, o.getClass()},
            new Class<?>[] {String.class, o.getClass()}
        );
        final List<Object> callers = Arrays.asList(
            o,o,o, null, null, null
        );
        final List<Supplier<Object[]>> parameters = Arrays.asList(
            () -> new Object[] {},
            () -> new Object[] {srcType},
            () -> new Object[] {mappedClass},
            () -> new Object[] {o},
            () -> new Object[] {srcType, o},
            () -> new Object[] {mappedClass, o}
        );
        Object[] output = new Object[1];
        boolean invocationSuccess = ReflectiveInvocation.invokeMatch(clazz, methodName, signatureOptions, callers, parameters, output);
        if(!invocationSuccess) {
            throw new IllegalArgumentException("Couldn't find custom handler for object unmarshalling. Inferred class: " + clazz + ", method name: " + methodName);
        } else {
            return toByteArr(output[0]);
        }


    }

    private byte[] toByteArr(Object reflectionResult) {
        if(reflectionResult instanceof byte[]) {
            return (byte[]) reflectionResult;
        } else if(reflectionResult instanceof String) {
            return ((String) reflectionResult).getBytes();
        }
        throw new RuntimeException("Marshal output unrecognized. " + StringUtil.unexpectedTypeMsg("Byte[] or String", reflectionResult));
    }

    private Object unmarshalUsingReflection(Class<?> clazz, String methodName, TypeClass targetType, String targetJavaClass, byte[] data)
            throws InvocationTargetException, IllegalAccessException {
        final List<Class<?>[]> marshalSignatureOptions = Arrays.asList(
            new Class<?>[] {byte[].class},
            new Class<?>[] {TypeClass.class, byte[].class},
            new Class<?>[] {String.class, byte[].class},
            new Class<?>[] {InputStream.class},
            new Class<?>[] {TypeClass.class, InputStream.class},
            new Class<?>[] {String.class, InputStream.class}
        );
        final List<Object> callers = Arrays.asList(null, null, null, null, null, null);
        final List<Supplier<Object[]>> parameters = Arrays.asList(
            () -> new Object[] {data},
            () -> new Object[] {targetType, data},
            () -> new Object[] {targetJavaClass, data},
            () -> new Object[] {new ByteArrayInputStream(data)},
            () -> new Object[] {targetType,  new ByteArrayInputStream(data)},
            () -> new Object[] {targetJavaClass,  new ByteArrayInputStream(data)}
        );
        Object[] output = new Object[1];
        boolean invocationSuccess = ReflectiveInvocation.invokeMatch(clazz, methodName, marshalSignatureOptions, callers, parameters, output);
        if(!invocationSuccess) {
            throw new IllegalArgumentException("Couldn't find custom handler for object unmarshalling. Inferred class: " + clazz + ", method name: " + methodName);
        } else {
            return output[0];
        }
    }

    private IJavaMarshalAux inferMarshalMethod(IMarshalNode node) {
        IStdTypeAux suppliedTypeAux = AuxHelper.convertAuxiliaries(node, IStdTypeAux.class).orElse(EMPTY_TYPE_AUX);
        IJavaMarshalAux suppliedMarshalAux = suppliedTypeAux.getJavaMarshalAux();
        return Objects.requireNonNullElse(suppliedMarshalAux, DEFAULT_MARSHAL);
    }

    private Class<?> inferClass(TypeClass supplyType, IJavaMarshalAux marshalAux) throws ClassNotFoundException {
        if(marshalAux.useJacksonSerialisation()) {
            if(marshalAux.jacksonSerialisationClassTarget() != null) {
                return Class.forName(marshalAux.jacksonSerialisationClassTarget());
            }
        } else if(marshalAux.useCustomHandler()) {
            if (marshalAux.getHandlerClass() != null) {
                return Class.forName(marshalAux.getHandlerClass());
            }
        }
        Class<?> runtimeInference;
        if(marshalAux.getMappedJavaClass() != null) {
            runtimeInference = Class.forName(marshalAux.getMappedJavaClass());
        } else {
            runtimeInference = Class.forName(supplyType.getTypeQualifier());
        }
        if(marshalAux.useLegacyPattern()) {
            return Class.forName("ai.services.typeserializers." + runtimeInference.getName() + "OntologySerializer");
        } else {
            return runtimeInference;
        }
    }

    private String inferMethod(IJavaMarshalAux marshalAux, TypeClass targetType, IMarshalling.Direction direction) {
        if(marshalAux.useCustomHandler()) {
            String marshalMethod = null;
            marshalMethod = direction.isMarshal() ? marshalAux.getHandlerMarshalMethod() : marshalAux.getHandlerUnmarshalMethod();
            if(marshalMethod != null) {
                return marshalMethod;
            }
        }
        if(marshalAux.useLegacyPattern()) {
            return legacyMarshalMethodName(targetType, direction);
        }
        throw new IllegalArgumentException("Cannot infer the method to unmarshal the data from the supplied marshal aux: " + marshalAux);
    }

    private String legacyMarshalMethodName(TypeClass targetType, IMarshalling.Direction direction) {
        String simpleName = SemanticStreamer.getSimpleNameFromClasspath(targetType.getTypeQualifier());
        String methodName;
        if(direction.isMarshal()) {
            methodName = "cts_";
        } else {
            methodName = "cfs_";
        }
        methodName += simpleName;
        return methodName;
    }


    private static final Function<Object, SEDEObject> SERVICE_INSTANCE_HANDLE_WRAPPER = handle -> {
        if(handle instanceof ServiceInstanceHandle) {
            return new ServiceInstanceField((ServiceInstanceHandle) handle);
        } else {
            throw new IllegalArgumentException(StringUtil.unexpectedTypeMsg("ServiceInstanceHandle", handle));
        }
    };

    private static final Function<Object, SEDEObject> PRIMITIVE_WRAPPER = val -> {
        Optional<Primitives> primitiveType = Primitives.inferTypeFromValue(val);
        if(primitiveType.isPresent()) {
            return new PrimitiveDataField(primitiveType.get(), val);
        } else {
            throw new IllegalArgumentException(StringUtil.unexpectedTypeMsg("Primitive", val));
        }
    };

    private Function<Object, SEDEObject> getObjectWrapper(final TypeClass valueType) {
        if(TypeClass.isServiceHandle(valueType)) {
            ValueTypeClass referencedType = TypeClass.tryDeref(valueType);
            if(referencedType instanceof IServiceInstanceType) {
                return SERVICE_INSTANCE_HANDLE_WRAPPER;
            } else {
                throw new IllegalArgumentException("Cannot wrap a ref type of: " + referencedType);
            }
        } else if(valueType instanceof IPrimitiveValueType) {
            logger.warn("Marshalling primitive values is discouraged: {}", valueType);
            return PRIMITIVE_WRAPPER;
        } else if(valueType instanceof IDataValueType) {
            return val -> new ObjectDataField(valueType.getTypeQualifier(), val);
        } else {
            throw new IllegalArgumentException("Unrecognized type class: " + valueType);
        }
    }

}
