package de.upb.sede.exec;

import de.upb.sede.IQualifiable;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorContactInfo.Builder.class)
public interface IExecutorContactInfo extends IQualifiable {

}
