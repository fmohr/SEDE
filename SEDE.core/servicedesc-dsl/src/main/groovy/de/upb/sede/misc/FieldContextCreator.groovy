package de.upb.sede.misc

import de.upb.sede.composition.FieldType
import de.upb.sede.composition.IFieldType
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.PrimitiveValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.composition.types.TypeClass
import de.upb.sede.core.PrimitiveType

class FieldContextCreator {

    private final List<IFieldType> fields = new ArrayList<>();

    FieldContextCreator() {
    }

    def addAll(List<IFieldType> fields) {
        if(fields != null && !fields.isEmpty()) {
            this.fields.addAll(fields);
        } else {
            throw new IllegalArgumentException("Added fields cannot be null or empty.")
        }
    }

    def addType(String fieldName, TypeClass typeClass) {
        IFieldType newType = FieldType.builder()
            .fieldname(fieldName)
            .type(typeClass)
            .build()
        fields.add(newType)
    }

    def data(String fieldName, String dataValueType) {
        addType(fieldName, DataValueType.builder()
            .typeQualifier(dataValueType)
            .build());
    }

    def serviceInstance(String fieldName, String serviceQualifier) {
        addType(fieldName, ServiceInstanceType.builder()
            .typeQualifier(serviceQualifier)
            .build());
    }

    def prim(String fieldName, PrimitiveType primType) {
        addType(fieldName, PrimitiveValueType.builder()
            .primitiveType(primType)
            .build());
    }



    def getResultingContext() {
        return fields;
    }

}
