package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IFieldContainer;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IExecutorContactInfo;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TransmitDataNode.Builder.class)
public interface ITransmitDataNode extends BaseNode, IFieldContainer {

    IExecutorContactInfo getContactInfo();

    @Nullable
    String getCaster();

    @Nullable
    String getSemantiveType();


}
