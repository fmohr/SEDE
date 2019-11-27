package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.IFieldType;

public interface FieldTypeResolution {


    IFieldType getFieldType(String fieldname);

    void setFieldType(String fieldname, IFieldType valueType);


}
