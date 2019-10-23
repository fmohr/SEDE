package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.*;
import de.upb.sede.requests.ExecutorRegistration;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorHandle.Builder.class)
public interface IExecutorHandle extends IQualifiable {

    IExecutorContactInfo getContactInfo();

    IExecutorCapabilities getCapabilities();

    @Value.Derived
    default String getQualifier() {
        return getContactInfo().getQualifier();
    }


    static ExecutorHandle fromRegistration(ExecutorRegistration registration) {
        // TODO migrate to the new ExecutorRegistration
        ExecutorHandle execHandle = ExecutorHandle.builder()
            .contactInfo(
                ExecutorContactInfo.builder()
                    .qualifier(registration.getId())
                    .hostAddress(registration.getContactInfo().get("host-address").toString())
                    .build())
            .capabilities(
                ExecutorCapabilities.builder()
                    .services(registration.getSupportedServices())
                    .features(registration.getCapabilities())
                    .build()) // TODO add capabilities
            .build();
        return execHandle;
    }


}
