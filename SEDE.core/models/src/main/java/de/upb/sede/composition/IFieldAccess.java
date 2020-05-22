package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAccess.Builder.class)
public interface IFieldAccess extends Comparable<IFieldAccess> {

    String getField();

    AccessType getAccessType();

    Long getIndex();

    enum AccessType {
        READ, WRITE, ASSIGN;

        public boolean isAssignOrWrite() {
            return this == WRITE || this == ASSIGN;
        }
    }

    @Override
    default int compareTo(IFieldAccess a2) {
        int order = Long.compare(getIndex(), a2.getIndex());
        if(order != 0) {
            return order;
        }
        return getAccessType().compareTo(a2.getAccessType());
    }
}



