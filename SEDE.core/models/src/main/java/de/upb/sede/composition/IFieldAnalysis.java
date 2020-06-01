package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.types.TypeClass;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAnalysis.Builder.class)
public interface IFieldAnalysis {

    @Value.Derived
    default boolean isInjected() {
        return getInitialType().isPresent();
    }

//    @Value.Derived
//    default Optional<Long> getAssignmentIndex() {
//        Optional<IFieldAccess> firstAssignment = getFieldAccesses()
//            .stream()
//            .filter(access -> access.getAccessType() == IFieldAccess.AccessType.ASSIGN)
//            .findFirst();
//        return firstAssignment.map(IFieldAccess::getIndex);
//    }

    String getFieldname();

    Optional<TypeClass> getInitialType();

    Map<Long, TypeClass> getInstTyping();

    IFieldType getFieldType();

    List<IFieldAccess> getFieldAccesses();

}

