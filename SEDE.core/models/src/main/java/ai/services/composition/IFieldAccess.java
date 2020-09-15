package ai.services.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAccess.Builder.class)
public interface IFieldAccess {

    String getField();

    AccessType getAccessType();

    Long getIndex();

    enum AccessType {
        READ, WRITE, ASSIGN;

        public boolean isAssignment() {
            return this == ASSIGN;
        }

        public boolean isWrite() {
            return this == WRITE;
        }

        public boolean isAssignOrWrite() {
            return this == WRITE || this == ASSIGN;
        }

        public boolean isRead() {
            return this == READ;
        }
    }

    default int compareTo(IFieldAccess a2) {
        int order = Long.compare(getIndex(), a2.getIndex());
        if(order != 0) {
            return order;
        }
        return getAccessType().compareTo(a2.getAccessType());
    }
}



