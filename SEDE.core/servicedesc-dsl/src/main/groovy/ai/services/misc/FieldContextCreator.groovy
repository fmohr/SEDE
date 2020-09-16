package ai.services.misc


import ai.services.composition.IFieldType
import ai.services.composition.types.TypeClass
import ai.services.core.Primitives

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
        IFieldType newType = ai.services.composition.FieldType.builder()
            .fieldname(fieldName)
            .type(typeClass)
            .build()
        fields.add(newType)
    }

    def data(String fieldName, String dataValueType) {
        addType(fieldName, ai.services.composition.types.DataValueType.builder()
            .typeQualifier(dataValueType)
            .build());
    }

    def serviceInstance(String fieldName, String serviceQualifier) {
        addType(fieldName, ai.services.composition.types.ServiceInstanceType.builder()
            .typeQualifier(serviceQualifier)
            .build());
    }

    def prim(String fieldName, Primitives primType) {
        addType(fieldName, ai.services.composition.types.PrimitiveValueType.builder()
            .primitiveType(primType)
            .build());
    }



    def getResultingContext() {
        return fields;
    }

}
