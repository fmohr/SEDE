package ai.services.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

/**
 * Static Code Analysis Request
 */
@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CCRequest.Builder.class)
public interface ICCRequest {

    String getComposition();

    List<IFieldType> getInitialContext();

}



