package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorHandle.Builder.class)
public interface IExecutorHandle {

    IExecutorContactInfo getContactInfo();

    IExecutorCapabilities getCapabilities();
}
