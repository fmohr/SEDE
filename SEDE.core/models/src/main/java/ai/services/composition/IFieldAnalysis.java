package ai.services.composition;

import ai.services.composition.types.TypeClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAnalysis.Builder.class)
public interface IFieldAnalysis {

    @Value.Derived
    default boolean isInjected() {
        return getInitialType() != null;
    }

    String getFieldname();

    @Nullable
    TypeClass getInitialType();

    Map<Long, TypeClass> getInstTyping();

    List<IFieldAccess> getFieldAccesses();

}

