package ai.services;

import ai.services.exec.IMethodDesc;
import ai.services.exec.IServiceDesc;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceMethod.Builder.class)
public interface IServiceMethod {

    IServiceCollectionDesc getCollection();

    IServiceDesc getService();

    IMethodDesc getMethod();

}

