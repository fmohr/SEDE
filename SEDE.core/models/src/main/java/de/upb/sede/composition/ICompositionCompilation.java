package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CompositionCompilation.Builder.class)
public interface ICompositionCompilation {

    List<Long> getProgramOrder();

    List<IStaticInstAnalysis> getInstructionAnalysis();

}



