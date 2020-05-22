package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = StaticInstAnalysis.Builder.class)
public interface IStaticInstAnalysis {

    IIndexedInstruction getInstruction();

    List<IFieldType> getTypeContext();

    IMethodResolution getMethodResolution();

    List<IFieldAccess> getInstFieldAccesses();

}



