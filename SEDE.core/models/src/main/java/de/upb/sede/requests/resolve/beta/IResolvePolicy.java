package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ResolvePolicy.Builder.class)
public interface IResolvePolicy {

    @Value.Default
    default FieldSelection getReturnPolicy(){
        return FieldSelection.ALL;
    }

    @Value.Default
    default FieldSelection getServicePolicy() {
        return FieldSelection.ALL;
    }

    List<String> getReturnFieldnames();

    List<String> getPersistentServices();

    @Value.Default
    default boolean isDotGraphRequested() {
        return false;
    }

    enum FieldSelection {
         ALL, NONE, LISTED
    }
}
