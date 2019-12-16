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
public interface IFieldAccess {

    String getField();

    AccessType getAccessType();

    Long getIndex();

    enum AccessType {
        READ, WRITE, ASSIGN;
    }

}



