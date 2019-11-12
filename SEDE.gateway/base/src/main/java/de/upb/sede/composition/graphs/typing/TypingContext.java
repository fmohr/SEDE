package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.IValueType;

public interface TypingContext {


    FieldType getFieldType(String fieldname);

    void setFieldType(String fieldname, FieldType valueType);

}
