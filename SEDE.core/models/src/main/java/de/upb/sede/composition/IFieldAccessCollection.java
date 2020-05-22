package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAccessCollection.Builder.class)
public interface IFieldAccessCollection {

    @Value.Derived
    default boolean isInjected() {
        return !getAssignmentIndex().isPresent();
    }

    @Value.Derived
    default Optional<Long> getAssignmentIndex() {
        Optional<IFieldAccess> firstAssignment = getFieldAccesses()
            .stream()
            .filter(access -> access.getAccessType() == IFieldAccess.AccessType.ASSIGN)
            .findFirst();
        return firstAssignment.map(IFieldAccess::getIndex);
    }

    IFieldType getFieldType();

    List<IFieldAccess> getFieldAccesses();

}

