package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.beta.IExecutorRegistration;
import de.upb.sede.composition.IFieldType;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.requests.resolve.InputFields;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ResolveRequest.Builder.class)
public interface IResolveRequest {

    String getComposition();

    IResolvePolicy getResolvePolicy();

    IExecutorRegistration getClientExecutorRegistration();

    List<IFieldType> getInitialContext();

    Map<String, ServiceInstanceHandle> getInitialServices();

}

