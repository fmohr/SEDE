package ai.services.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.IQualifiable;
import ai.services.SEDEModelStyle;
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



