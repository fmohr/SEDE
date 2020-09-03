package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.composition.graphs.nodes.IMarshalNode;
import de.upb.sede.composition.types.*;
import de.upb.sede.composition.types.serialization.IMarshalling;
import de.upb.sede.core.*;
import de.upb.sede.exec.auxiliary.*;
import de.upb.sede.util.StringUtil;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;

public class MarshalOp extends MainTaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(MarshalOp.class);

    private final static IJavaTypeAux EMPTY_TYPE_AUX = JavaTypeAux.builder().build();

    private final static IJavaMarshalAux DEFAULT_MARSHAL = JavaMarshalAux.builder().build();

    private final static ObjectMapper MAPPER = new ObjectMapper();

    public MarshalOp() {
        super(IMarshalNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IMarshalNode node = (IMarshalNode) t.getNode();
        String fieldname = node.getFieldName();
        SEDEObject fieldValue = t.getFieldContext().getFieldValue(fieldname);

        IJavaMarshalAux marshalAux = inferMarshalMethod(node);

        if(node.getMarshalling().getDirection().equals(IMarshalling.Direction.UNMARSHAL)) {
            return unmarshal(node, fieldValue, marshalAux);
        } else {
            return marshal(node, fieldValue);
        }
    }

    private IJavaMarshalAux inferMarshalMethod(IMarshalNode node) {
        IJavaTypeAux suppliedTypeAux = AuxHelper.convertAuxiliaries(node, IJavaTypeAux.class).orElse(EMPTY_TYPE_AUX);

        IJavaMarshalAux suppliedMarshalAux = suppliedTypeAux.getJavaMarshalAux();
        if(suppliedMarshalAux == null) {
            return DEFAULT_MARSHAL;
        }
        return suppliedMarshalAux;
    }

    private TaskTransition marshal(IMarshalNode node, SEDEObject fieldValue) {
        String fieldName = node.getFieldName();
        if(!(fieldValue instanceof ObjectDataField)) {
            throw new IllegalStateException(String.format("Field %s is not of object type. Instead it reis: %s", fieldName, fieldValue.toString()));
        }
        return null;
    }

    private TaskTransition unmarshal(IMarshalNode node, SEDEObject fieldValue, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException {
        String fieldname = node.getFieldName();
        if(!(fieldValue instanceof SemanticDataField)) {
            throw new IllegalStateException(String.format("Field %s is not of semantic type. Instead it is: %s", fieldname, fieldValue.toString()));
        }
        IMarshalling marshalling = node.getMarshalling();
        TypeClass suppliedType = marshalling.getValueType();
        final Function<Object, SEDEObject> objectWrapper = getObjectWrapper(suppliedType);

        final SemanticDataField semanticField = (SemanticDataField) fieldValue;
        final byte[] data = semanticField.getDataField();

        Object unmarshalledValue;


        if(TypeClass.isServiceHandle(suppliedType)) {
            unmarshalledValue = readServiceInstanceHandle(data, suppliedType, marshalAux);
        } else {
            unmarshalledValue = readObject(data, suppliedType, marshalAux);
        }
        SEDEObject unmarshalledField = objectWrapper.apply(unmarshalledValue);


        /*
         * By default try to build an object:
         */

        return null;
    }

    private Object readServiceInstanceHandle(byte[] data, TypeClass supplyType, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException {
        @SuppressWarnings({"unchecked"})
        Map<String, Object> map = MAPPER.readValue(data, Map.class);
        ServiceInstanceHandle handle = new ServiceInstanceHandle();
        handle.fromJson(map);
        if(map.containsKey("instance")) {
            String base64Encoded = (String) map.get("instance");
            JsonFactory jFactory = new JsonFactory();
            JsonParser parser = jFactory.createParser(base64Encoded);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            parser.readBinaryValue(Base64Variants.getDefaultVariant(), outputStream);
            parser.close();
            byte[] serviceInstanceSerialised = outputStream.toByteArray();
            Object serviceInstance = readObject(serviceInstanceSerialised, supplyType, marshalAux);
            return new ServiceInstance(handle, serviceInstance);
        } else {
            return handle;
        }
    }

    private Object readObject(byte[] data, TypeClass supplyType, IJavaMarshalAux marshalAux) throws IOException, ClassNotFoundException {
        if(marshalAux.useObjectSerialisation()) {
            ObjectInputStream objectIn  = new ObjectInputStream(new ByteArrayInputStream(data));
            return objectIn.readObject();
        }
        Class<?> inferredClass = inferClass(supplyType, marshalAux);
        if(marshalAux.useJacksonSerialisation()) {
            return MAPPER.readValue(data, inferredClass);
        }
        String methodName = inferMethod(marshalAux, supplyType, IMarshalling.Direction.UNMARSHAL);

        throw new IllegalArgumentException("Cannot infer the class to unmarshal the data from the supplied marshal aux: " + marshalAux);
    }

    private String inferMethod(IJavaMarshalAux marshalAux, TypeClass targetType, IMarshalling.Direction direction) {
        if(marshalAux.useCustomHandler()) {
            if(marshalAux.getHandlerMethod()!=null) {
                return marshalAux.getHandlerMethod();
            }

        } else if(marshalAux.useLegacyPattern()) {
            return legacyMarshalMethodName(targetType, direction);
        }
        throw new IllegalArgumentException("Cannot infer the method to unmarshal the data from the supplied marshal aux: " + marshalAux);
    }

    private String legacyMarshalMethodName(TypeClass targetType, IMarshalling.Direction direction) {
        String simpleName = SemanticStreamer.getSimpleNameFromClasspath(targetType.getTypeQualifier());
        String methodName;
        if(direction == IMarshalling.Direction.MARSHAL) {
            methodName = "cts_";
        } else {
            methodName = "cfs_";
        }
        methodName += simpleName;
        return methodName;
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

    private static final Function<Object, SEDEObject> SERVICE_INSTANCE_HANDLE_WRAPPER = handle -> {
        if(handle instanceof ServiceInstanceHandle) {
            return new ServiceInstanceField((ServiceInstanceHandle) handle);
        } else {
            throw new IllegalArgumentException(StringUtil.unexpectedTypeMsg("ServiceInstanceHandle", handle));
        }
    };

    private static final Function<Object, SEDEObject> PRIMITIVE_WRAPPER = val -> {
        Optional<PrimitiveType> primitiveType = PrimitiveType.inferTypeFromValue(val);
        if(primitiveType.isPresent()) {
            return new PrimitiveDataField(primitiveType.get(), val);
        } else {
            throw new IllegalArgumentException(StringUtil.unexpectedTypeMsg("Primitive", val));
        }
    };

    private Function<Object, SEDEObject> getObjectWrapper(final TypeClass valueType) {
        if(TypeClass.isServiceHandle(valueType)) {
            ValueTypeClass referencedType = ((IRefType) (TypeClass.tryDeref(valueType))).getTypeOfRef();
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
