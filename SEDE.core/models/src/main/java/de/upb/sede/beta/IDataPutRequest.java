package de.upb.sede.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataPutRequest.Builder.class)
public interface IDataPutRequest extends IQualifiableRequest, WithField {

    // TODO add SEDEObject data field
    boolean isUnavailable();

}
