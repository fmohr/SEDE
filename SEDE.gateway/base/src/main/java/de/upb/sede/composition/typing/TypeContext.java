package de.upb.sede.composition.typing;

import de.upb.sede.composition.graphs.types.TypeClass;

public interface TypeContext {

    TypeClass getFieldType(String fieldname);

    void setFieldType(String fieldname, TypeClass valueType);

}
