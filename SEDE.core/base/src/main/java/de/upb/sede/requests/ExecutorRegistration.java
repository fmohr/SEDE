package de.upb.sede.requests;

import java.io.IOException;
import java.util.*;

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

@JsonSerialize(using = ExecutorRegistration.Serializer.class)
@JsonDeserialize(using = ExecutorRegistration.Deserializer.class)
public class ExecutorRegistration implements JsonSerializable {

	private Optional<Map<String, Object>> contactInformation = Optional.empty();


	private Optional<List<String>> capabilities = Optional.empty();

	private Optional<List<String>> supportedServices = Optional.empty();

	public ExecutorRegistration() {
	}

	public ExecutorRegistration(Map<String, Object> contactInfo, List<String> capabilities, List<String> supportedServices) {
		super();
		setContactInformation(contactInfo);
		setCapabilities(capabilities);
		setSupportedServices(supportedServices);
	}

	public static ExecutorRegistration client_registration(String clientId) {
		Map<String, Object> contactInfo = new HashMap<>();
		contactInfo.put("id", clientId);
		return new ExecutorRegistration(contactInfo, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
	}

	/**
	 * @return the executor host address
	 */
	public String getId() {
		return (String) contactInformation.get().get("id");
	}

	/**
	 * @return the executor contact information.
	 */
	public Map<String, Object> getContactInfo() {
		return contactInformation.get();
	}

	/**
	 * @return the capabilities
	 */
	public List<String> getCapabilities() {
		return capabilities.get();
	}

	/**
	 * @return the supportedServices
	 */
	public List<String> getSupportedServices() {
		return supportedServices.get();
	}



	public void setContactInformation(Map<String, Object> contactInformation) {
		this.contactInformation = Optional.of(contactInformation);
	}

	public void setCapabilities(List<String> capabilities) {
		this.capabilities = Optional.of(Optional.ofNullable(capabilities).orElse(Collections.EMPTY_LIST));
	}

	public void setSupportedServices(List<String> supportedServices) {
		this.supportedServices = Optional.of(Optional.ofNullable(supportedServices).orElse(Collections.EMPTY_LIST));
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contact-info", getContactInfo());
		jsonObject.put("capabilities", getCapabilities());
		jsonObject.put("supported-services", getSupportedServices());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		setContactInformation((Map<String, Object>)data.get("contact-info"));
		setCapabilities((List<String>) data.get("capabilities"));
		setSupportedServices((List<String>) data.get("supported-services"));
	}

    static class Serializer extends StdSerializer<ExecutorRegistration> {

        protected Serializer() {
            super(ExecutorRegistration.class);
        }

        protected Serializer(Class<ExecutorRegistration> t) {
            super(t);
        }

        @Override
        public void serialize(ExecutorRegistration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(new HashMap<>(value.toJson()));
        }
    }
    static class Deserializer extends StdDeserializer<ExecutorRegistration> {

        protected Deserializer() {
            super(ExecutorRegistration.class);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public ExecutorRegistration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            HashMap map = p.getCodec().readValue(p, HashMap.class);
            ExecutorRegistration reg =  new ExecutorRegistration();
            reg.fromJson(map);
            return reg;
        }
    }
}
