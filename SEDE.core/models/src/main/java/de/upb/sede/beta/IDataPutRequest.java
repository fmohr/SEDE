package de.upb.sede.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IFieldContainer;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataPutRequest.Builder.class)
public interface IDataPutRequest extends IQualifiableRequest, IFieldContainer {

    // TODO add SEDEObject data field
    boolean isUnavailable();

}
