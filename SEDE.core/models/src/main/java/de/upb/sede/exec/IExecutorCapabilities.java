package de.upb.sede.exec;

import java.util.List;

import de.upb.sede.IQualifiable;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorCapabilities.Builder.class)
public interface IExecutorCapabilities {

    List<String> getFeatures();

    List<String> getServices();

}
