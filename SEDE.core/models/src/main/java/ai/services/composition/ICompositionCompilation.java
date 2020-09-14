package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CompositionCompilation.Builder.class)
public interface  ICompositionCompilation extends IQualifiable {

    @Value.Lazy
    default String getCompositionName() {
        return getQualifier();
    }

    List<IIndexedInstruction> getInstructions();

    List<Long> getProgramOrder();

    List<IFieldAnalysis> getFields();

    List<IStaticInstAnalysis> getStaticInstAnalysis();

}



