package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

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

    List<IFieldAccessCollection> getFieldAccesses();

    List<IStaticInstAnalysis> getStaticInstAnalysis();

}



