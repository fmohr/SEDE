package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.TypeClass;

public interface FieldTypeResolution {


    TypeClass getFieldType(String fieldname);

    void setFieldType(String fieldname, TypeClass valueType);


}
