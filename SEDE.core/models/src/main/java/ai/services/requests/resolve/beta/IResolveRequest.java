package ai.services.requests.resolve.beta;

import ai.services.composition.ICompositionCompilation;
import ai.services.composition.IFieldType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.beta.IExecutorRegistration;
import ai.services.core.ServiceInstanceHandle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ResolveRequest.Builder.class)
public interface IResolveRequest {

    String getComposition();

    @Nullable
    ICompositionCompilation getCC();

    IResolvePolicy getResolvePolicy();

    IExecutorRegistration getClientExecutorRegistration();

    List<IFieldType> getInitialContext();

    Map<String, ServiceInstanceHandle> getInitialServices();

}

