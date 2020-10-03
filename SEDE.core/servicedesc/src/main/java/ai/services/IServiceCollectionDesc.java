package ai.services;

import ai.services.exec.IServiceDesc;
import ai.services.types.IDataTypeDesc;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceCollectionDesc.Builder.class)
public interface IServiceCollectionDesc extends CommentAware, IQualifiable {

    List<IServiceDesc> getServices();

    List<IDataTypeDesc> getDataTypes();

    // TODO add deployments
}
