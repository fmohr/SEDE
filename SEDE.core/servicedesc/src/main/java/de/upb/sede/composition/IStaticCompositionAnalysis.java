package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = StaticCompositionAnalysis.Builder.class)
public interface IStaticCompositionAnalysis {

    List<Long> getProgramOrder();

    List<IStaticInstAnalysis> getInstructionAnalysis();

}



