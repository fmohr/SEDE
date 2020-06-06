package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.ICompositionGraph;
import org.immutables.value.Value;

import javax.validation.constraints.Null;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Choreography.Builder.class)
public interface IChoreography {

    List<ICompositionGraph> getCompositionGraph();

    List<String> getReturnFields();

    @Null
    String getDotSVG();

}

