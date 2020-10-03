import ai.services.SDL
import groovy.transform.BaseScript
import groovy.transform.Field

import java.util.stream.Collectors

@BaseScript SDL description


import static ai.services.StandardDefs.*;

@Field Integer collectionCount  = 1
@Field Integer serviceCount = 3
@Field Integer typeCount = 1
@Field Integer signatureCount = 2

def getColQualifier(int colId) {
    return "c" + colId
}

def getServiceQualifier(int colId, int serviceId) {
    return getColQualifier(colId) + ".S" + serviceId
}

def getMethodDef(List<String> inputs, List<String> outputs) {
    def splitter = {String it ->
        if(it.contains(".")) {
            String[] splits = it.split "\\."
            return splits[splits.length-1]
        } else {
            return it
        }
    }
    def inputsName = inputs.stream().map(splitter).collect(Collectors.toList())
    def outputsName = outputs.stream().map( splitter).collect(Collectors.toList())

    def methodName = "m" + inputsName.join("_")
    if(!outputs.isEmpty()) {
        methodName += "__" + outputsName.join("_")
    }
    return [
        name: methodName,
        inputs: inputs,
        outputs: outputs
    ]
}


def getConstMethodDef(String serviceQualifier, List<String> inputs) {
    def methodDef = getMethodDef(inputs, [])
    methodDef.name = "const" + methodDef.name.substring(1)
    methodDef.outputs = [serviceQualifier]
    methodDef["static"] = true
    methodDef
}

def getTypeQualifier(Integer colId, Integer typeId) {
    return getColQualifier(colId) + ".T" + typeId
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

        def types = [number]
        (0..typeCount).each {typeId ->
            def typeDesc = type(getTypeQualifier(collectionId, typeId)) {
                semanticType = getTypeSemanticType(typeId)
            }
            types.add(typeDesc.getQualifier())
        }

        (0..serviceCount).each { serviceId ->
            def serviceQ = getServiceQualifier(collectionId, serviceId)
            service(serviceQ) {
                constructor()
                (0..signatureCount).each { inputNr ->
                    permuteTypes(types, inputNr).each { List inputs ->
                        method (getConstMethodDef(serviceQ, inputs))
                        (0..1).each { outputNr ->
                            permuteTypes(types, outputNr).each { List outputs ->
                                method getMethodDef(inputs, outputs)
                            }
                        }
                    }
                }
            }
        }
    }
}


collection("ServiceLoadStoreTests") {

    service("ai.service.ServiceWithPureMethods") {

        setStateful()

        constructor()

        method(name: "impureMethod") {
            isPure = false // default
        }

        method(name: "pureMethod") {
            isPure = true
        }
    }
}
