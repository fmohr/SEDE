package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.nodes.IIndexedInstruction;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.TypeClass;
import de.upb.sede.exec.IMethodDesc;

public class TypeCheckException extends RuntimeException {

    public TypeCheckException() {
    }

    public TypeCheckException(String message) {
        super(message);
    }

    public TypeCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeCheckException(Throwable cause) {
        super(cause);
    }

    public TypeCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public TypeCheckException(IIndexedInstruction indexedInstruction, Throwable cause) {
        super(
            String.format("Type Check error at Instruction: `%s` (main: %d)", // TODO `main` context
                indexedInstruction.getInstruction().getFMInstruction(),
                indexedInstruction.getIndex()),
            cause);
    }

    public static TypeCheckException unexpectedType(String givenType, String expectedType, String message) {
        String errText = String.format("Unexpected type: %s was expected to be of type `%s`.",
            givenType, expectedType);
        if(message != null) {
            errText = String.format("%s Err Message: %s", errText, message);
        }
        return new TypeCheckException(errText);
    }

    public static TypeCheckException unexpectedConstantType(String constant, String assumedConstantType,
                                                            String expectedType, String message) {
        String typeText = String.format("Constant `%s` of  type `%s`", constant, assumedConstantType);
        return unexpectedType(typeText, expectedType, message);
    }

    public static TypeCheckException unexpectedFieldType(String fieldName, TypeClass fieldType, String expectedType, String message) {
        String typeText = String.format("Field `%s` of  type `%s`", fieldName, Locals.typeToText(fieldType));
        return unexpectedType(typeText, expectedType, message);
    }

    public static TypeCheckException unexpectedType(String fieldName, TypeClass fieldType, String expectedType) {
        return unexpectedFieldType(fieldName, fieldType, expectedType, null);
    }



    public static TypeCheckException unknownType(String qualifier, String kind) {
        String errText = String.format("Unknown %s: `%s`", kind, qualifier);
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException unknownType(String fieldName, TypeClass fieldType) {
        String errText = String.format("Field %s Unknown type `%s`", fieldName, fieldType.getTypeQualifier());
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException unknownMethodSignature(String serviceQualifier, String methodQualifier, IInstructionNode inst) {
        int expectedOutput = inst.isAssignment()? 1 : 0;
        int expectedInput = inst.getParameterFields().size();

        String errText = "No one signature of method %s::%s matches the requested parameter count: " +
            "Instruction exptexts %d -> %d" +
            "\nNote that currently method resolution works simply matching the input and output size. Parameter types are ignored." +
            "\nAdditionally note that this exception is also thrown if more than one method matches the instruction.";
        errText = String.format(errText, serviceQualifier, methodQualifier, expectedInput, expectedOutput);
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException undefinedField(String fieldName) {
        String errText = String.format("Field is not defined: %s", fieldName);
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException nonMatchingSemanticType(String sourceType, String sourceSemType,
                                                             String targetType, String targetSemType) {
        String errText = String.format("Cannot coerce type %s -> %s " +
                "because their semantic types doesn't match: %s ≠ %s",
            sourceType, targetType, sourceSemType, targetSemType);
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException methodNoReturnValue(String method, String assignedField) {
        String errText = String.format("Cannot assign `%s`, " +
            "method `%s` doesn't return any value", method, assignedField);
        throw new TypeCheckException(errText);
    }

    public static TypeCheckException methodReturnsNullClass() {
        String errText = "Returns null class";
        throw new TypeCheckException(errText);
    }

}
