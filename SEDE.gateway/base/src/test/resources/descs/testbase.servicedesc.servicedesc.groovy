import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.jdi.PrimitiveValue
import de.upb.sede.SDL
import de.upb.sede.beta.ExecutorRegistration
import de.upb.sede.composition.FieldType
import de.upb.sede.composition.IFieldType
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.PrimitiveValueType
import de.upb.sede.composition.types.ServiceInstanceType
import de.upb.sede.composition.types.ValueTypeClass
import de.upb.sede.core.PrimitiveType
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.exec.ExecutorCapabilities
import de.upb.sede.exec.ExecutorContactInfo
import de.upb.sede.exec.ExecutorHandle
import de.upb.sede.param.CategoryParameter
import de.upb.sede.requests.resolve.beta.IResolveRequest
import de.upb.sede.requests.resolve.beta.ResolvePolicy
import de.upb.sede.requests.resolve.beta.ResolveRequest
import groovy.transform.BaseScript
import groovy.transform.Field

@BaseScript SDL description

import static de.upb.sede.Helpers.*;
import static de.upb.sede.StandardDefs.*;

@Field Integer collectionCount  = 2
@Field Integer serviceCount = 4
@Field Integer typeCount = 3
@Field Integer signatureCount = 2

def getColQualifier(int colId) {
    return "col" + colId
}

def getServiceQualifier(int colId, int serviceId) {
    return getColQualifier(colId) + ".S" + serviceId
}

def getMethodDef(int colId, List<String> inputs, List<String> outputs) {
    def methodName = "m__" + inputs.join("_")
    if(!outputs.isEmpty()) {
        methodName += "__" + outputs.join("_")
    }
    return [
        name: methodName,
        inputs: inputs,
        outputs: outputs
    ]
}

def getTypeQualifier(Integer colId, Integer typeId) {
    return getColQualifier(colId) + "T" + typeId
}

def getTypeSemanticType(Integer typeId) {
    return "sem" + (typeId % 2)
}

def permuteTypes(List<String> types, Integer signatureNr) {
    def inputs = []
    def indices = []
    signatureNr.times {indices.add(0)}

    boolean finished = false;


    while(!finished) {
        def input = []
        for (int i = 0; i < signatureNr; i++) {
            input.add(types.get(indices[i]))
        }
        println("Adding type: " + input)
        inputs.add(input)

        boolean carry = true
        int cursor = 0
        while(carry && cursor < signatureNr) {
            indices[cursor] = indices[cursor] + 1
            if(indices[cursor] >= types.size()) {
                indices[cursor] = 0
                cursor ++;
                carry = true
            } else {
                carry = false
            }
        }
        if(carry) {
            finished = true
        }
    }
    return inputs;
}

(0..collectionCount).each { collectionId ->
    collection (getColQualifier(collectionId)) {

        def types = []
        (0..typeCount).each {typeId ->
            types.add type(getTypeQualifier(collectionId, typeId)) {
                semanticType = getTypeSemanticType(typeId)
            }.qualifier
        }

        (0..serviceCount).each { serviceId ->
            service(getServiceQualifier(collectionId, serviceId)) {
                (0..signatureCount).each { inputNr ->
                    permuteTypes(types, inputNr).each { List inputs ->
                        constructor(inputs: inputs)
                        (0..1).each { outputNr ->
                            permuteTypes(types, outputNr).each { List outputs ->
                                method getMethodDef(collectionId, inputs, outputs)
                            }
                        }
                    }
                }
            }
        }
    }
}

// generate test cases:
import java.util.Random
//@Field Random r = new Random(0)
//
//@Field List<IFieldType> inputFields = new ArrayList<>()
//@Field List<IFieldType> fields = new ArrayList<>()
//@Field List<String> compositions = new ArrayList<>()
//
//def next_type() {
//    def colId = r.nextInt(collectionCount as int)
//}
//
//def generateInputs() {
//    def numData = r.nextInt(5)
//    numData.times {
//        inputFields.add FieldType.builder()
//            .fieldname()
//            .type()
//            .build()
//    }
//    def numServices = r.nextInt(5)
//    numServices.times {
//
//    }
//}
//
//def last_service = [0,0]
//
//def next_service(int last_service) {
//    def changesCollection = r.nextBoolean()
//}
//
//composition = []
