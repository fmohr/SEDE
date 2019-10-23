package de.upb.sede.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IExecutorHandle;
import org.immutables.value.Value;

import javax.validation.constraints.Null;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorRegistration.Builder.class)
public interface IExecutorRegistration {

    IExecutorHandle getExecutorHandle();

}
