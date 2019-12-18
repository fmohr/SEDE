package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

/**
 * Static Code Analysis Request
 */
@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SCARequest.Builder.class)
public interface ICCRequest {

    String getComposition();

    List<IFieldType> getInitialContext();

}



