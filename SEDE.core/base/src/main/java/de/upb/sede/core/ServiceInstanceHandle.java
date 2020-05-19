package de.upb.sede.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

@JsonDeserialize(using = ServiceInstanceHandle.Deserializer.class)
@JsonSerialize(using = ServiceInstanceHandle.Serializer.class)
public class ServiceInstanceHandle implements Serializable, JsonSerializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Optional<String> executorId;
	private Optional<String> classpath;
	private Optional<String> id;

	/**
	 * Standard constructor
	 */
	public ServiceInstanceHandle(final String executorId, final String classpath, final String id) {
		super();
		this.executorId = Optional.of(executorId);
		this.classpath = Optional.of(classpath);
		this.id = Optional.of(id);
	}

	/**
	 * Empty constructor to create service handle with default values.
	 */
	public ServiceInstanceHandle() {
		this.executorId = Optional.empty();
		this.classpath = Optional.empty();
		this.id = Optional.empty();
	}

	/**
	 * Returns the id of the service. Throws Runtime-Exception if wasSerialized()
	 * returns false.
	 */
	public String getId() {
		return this.id.get();
	}

	/**
	 *
	 * @return Host of this service.
	 */
	public String getExecutorId() {
		return this.executorId.get();
	}

	public String getClasspath() {
		return this.classpath.get();
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("executorId", getExecutorId());
		jsonObject.put("classpath", getClasspath());
		jsonObject.put("id", getId());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.executorId = Optional.of((String) data.get("executorId"));
		this.id = Optional.of((String) data.get("id"));
		this.classpath = Optional.of((String) data.get("classpath"));
	}

	public Optional<Object> getServiceInstance() {
		return Optional.empty();
	}

	public String toString() {
		return "{serviceinstance: " + getClasspath() + " " + getId() + "/" + getExecutorId() + "}";
	}



    static class Serializer extends StdSerializer<ServiceInstanceHandle> {

        protected Serializer() {
            super(ServiceInstanceHandle.class);
        }

        protected Serializer(Class<ServiceInstanceHandle> t) {
            super(t);
        }

        @Override
        public void serialize(ServiceInstanceHandle value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(value.toJson());
        }
    }

    static class Deserializer extends StdDeserializer<ServiceInstanceHandle> {

        protected Deserializer() {
            super(ServiceInstanceHandle.class);
        }

        protected Deserializer(Class<ServiceInstanceHandle> vc) {
            super(vc);
        }

        @Override
        public ServiceInstanceHandle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap data = p.getCodec().readValue(p, HashMap.class);

            ServiceInstanceHandle handle =  new ServiceInstanceHandle();
            handle.fromJson(data);
            return handle;
        }
    }

}


