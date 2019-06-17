package de.upb.sede.edd.deploy.target;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.MutableOptionalField;
import de.upb.sede.util.OptionalField;
import de.upb.sede.util.WebUtil;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ExecutorSecretaryComponent extends TargetComponent{

    private final static Logger logger = LoggerFactory.getLogger(ExecutorConfigComponent.class);

    private MutableOptionalField<String> gatewayAddress = MutableOptionalField.empty();


    public ExecutorSecretaryComponent(String displayName) {
        this.setDisplayName(displayName);
    }

    public Optional<String> getGatewayAddress() {
        return gatewayAddress.opt();

    }

    public void setGatewayAddress(String gatewayAddress) {
        this.gatewayAddress.setNullable(gatewayAddress);
    }

    private Map<String, Object> contactInfo(String id, String address) {
        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("id", id);
        contactInfo.put("host-address", address);
        return contactInfo;

    }

    public void registerToGateway(String id, List<String> services, String port) {
        if(gatewayAddress.isAbsent()) {
            logger.warn("{}: cannot register executor. No gateway defined.", getDisplayName());
            return;
        }
        logger.info("{}: registering to gateway: {}", getDisplayName(), gatewayAddress.get());
        String address = WebUtil.HostPublicIpAddress() + ":" + port;

        ExecutorRegistration registration = new ExecutorRegistration(contactInfo(id, address), Collections.emptyList(), services);

        HttpURLConnectionClientRequest httpRegistration = new HttpURLConnectionClientRequest(gatewayAddress.get() + "/register");

        String registrationAnswer = httpRegistration.send(registration.toJsonString());
        if (!registrationAnswer.isEmpty()) {
            throw new RuntimeException("Registration to gateway \"" + gatewayAddress.get()
                + "\" failed with non empty return message:\n" + registrationAnswer);
        }
        logger.debug("{} registered to gateway: {}", getDisplayName(), getGatewayAddress().get());
    }
}
