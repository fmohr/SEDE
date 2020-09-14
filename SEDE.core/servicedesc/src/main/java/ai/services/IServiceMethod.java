package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IServiceDesc;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = de.upb.sede.ServiceMethod.Builder.class)
public interface IServiceMethod {

    IServiceCollectionDesc getCollection();

    IServiceDesc getService();

    IMethodDesc getMethod();

}

