package ai.services.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataPutRequest.Builder.class)
public interface IDataPutRequest extends IQualifiableRequest, WithField {

    // TODO add SEDEObject data field
    boolean isUnavailable();

}
