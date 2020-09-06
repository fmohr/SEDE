package ai.services.execution.operator.local;

import com.fasterxml.jackson.core.*;
import de.upb.sede.core.ServiceInstance;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.util.ExtendedByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class ServiceInstanceHandleSerialisation {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceHandleSerialisation.class);

    public static byte[] serialiseServiceInstance(ServiceInstanceHandle serviceInstance) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        writeServiceInstance(serviceInstance, outStream);
        return outStream.toByteArray();
    }

    public static ServiceInstanceHandle unSerialiseServiceInstance(byte[] serialisation) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(serialisation);
        return readServiceInstance(inputStream);
    }


    public static void writeServiceInstance(ServiceInstanceHandle serviceInstance, OutputStream stream) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jGenerator = jsonFactory
            .createGenerator(stream, JsonEncoding.UTF8);
        jGenerator.writeStartObject();
        jGenerator.writeStringField("classpath", serviceInstance.getClasspath());
        jGenerator.writeStringField("executorId", serviceInstance.getExecutorId());
        jGenerator.writeStringField("id", serviceInstance.getId());
        jGenerator.writeFieldName("instance");

        if(serviceInstance.getServiceInstance().isPresent()) {
            ExtendedByteArrayOutputStream byteOut = new ExtendedByteArrayOutputStream(128);
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(serviceInstance.getServiceInstance().get());
            objectOut.flush();
            objectOut.close();
            InputStream inputStream = byteOut.toInputStream();
            jGenerator.writeBinary(Base64Variants.getDefaultVariant(), inputStream, byteOut.size());
        } else {
            jGenerator.writeNull();
        }
        jGenerator.writeEndObject();
        jGenerator.close();
    }


    public static ServiceInstanceHandle readServiceInstance(InputStream stream) throws IOException, ClassNotFoundException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(stream);

        String classpath = null;
        String executorId = null;
        String id = null;
        Object instance = null;
        boolean instanceWasRead = false;
        if(jsonParser.nextToken() != JsonToken.START_OBJECT) {
            throw new IllegalArgumentException("Malformed input data. No start object was recognized.");
        }
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String field = jsonParser.getCurrentName();
            jsonParser.nextToken();
            switch (field) {
                case "classpath":
                    classpath = jsonParser.getValueAsString();
                    break;
                case "executorId":
                    executorId = jsonParser.getValueAsString();
                    break;
                case "id":
                    id = jsonParser.getValueAsString();
                    break;
                case "instance": {
                    byte[] binaryValue = jsonParser.getBinaryValue(Base64Variants.getDefaultVariant());
                    ByteArrayInputStream binInput = new ByteArrayInputStream(binaryValue);
                    ObjectInputStream objectIn = new ObjectInputStream(binInput);
                    instance = objectIn.readObject();
                    instanceWasRead = true;
                    break;
                }
                default:
                    logger.warn("Unrecognized field in service instance serialization: {}", field);
            }
        }
        jsonParser.close();
        Objects.requireNonNull(classpath, "Service instance serialisation did not provide the classpath.");
        Objects.requireNonNull(executorId, "Service instance serialisation did not provide the executorId.");
        Objects.requireNonNull(id, "Service instance serialisation did not provide the id.");
        if(!instanceWasRead) {
            throw new IllegalStateException("Service instance serialisation did not provide the service instance.");
        }
        return new ServiceInstance(executorId, classpath, id, instance);
    }

}
