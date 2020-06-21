package de.upb.sede.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.CommentAware;
import de.upb.sede.IQualifiable;
import de.upb.sede.QualifierDefinition;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import de.upb.sede.util.DynTypeField;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataTypeDesc.Builder.class)
public interface IDataTypeDesc extends QualifierDefinition, CommentAware, DynamicAuxAware {

    String getSemanticType();

}
