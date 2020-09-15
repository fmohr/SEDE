package ai.services.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.CommentAware;
import ai.services.QualifierDefinition;
import ai.services.SEDEModelStyle;
import ai.services.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataTypeDesc.Builder.class)
public interface IDataTypeDesc extends QualifierDefinition, CommentAware, DynamicAuxAware {

    String getSemanticType();

}
