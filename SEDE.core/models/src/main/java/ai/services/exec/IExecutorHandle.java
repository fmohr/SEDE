package ai.services.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.IQualifiable;
import ai.services.SEDEModelStyle;
import ai.services.requests.ExecutorRegistration;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorHandle.Builder.class)
public interface IExecutorHandle extends IQualifiable {

    IExecutorContactInfo getContactInfo();

    IExecutorCapabilities getCapabilities();

    @Value.Default
    default String getQualifier() {
        return getContactInfo().getQualifier();
    }
    /**
     * @deprecated
     */
    static ExecutorHandle fromRegistration(ExecutorRegistration registration) {
        return ExecutorHandle.builder()
            .contactInfo(
                ExecutorContactInfo.builder()
                    .qualifier(registration.getId())
                    .uRL(registration.getContactInfo().get("host-address").toString())
                    .build())
            .capabilities(
                ExecutorCapabilities.builder()
                    .services(registration.getSupportedServices())
                    .features(registration.getCapabilities())
                    .build())
            .build();
    }


}
