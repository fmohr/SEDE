package de.upb.sede.exec;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IComment;
import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SModelStyle
@Value.Immutable
@JsonDeserialize(builder = MethodDesc.Builder.class)
public interface IMethodDesc extends IQualifiable, IComment {

    List<ISignatureDesc> getSignatures();

    @Value.Default
    default boolean isStatic() {
        return false;
    }

}
