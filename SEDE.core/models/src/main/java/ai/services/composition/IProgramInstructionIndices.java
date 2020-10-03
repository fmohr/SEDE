package ai.services.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ProgramInstructionIndices.Builder.class)
public interface IProgramInstructionIndices {

    List<Long> getIndices();

}



