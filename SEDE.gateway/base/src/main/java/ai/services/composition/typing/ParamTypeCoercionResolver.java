package ai.services.composition.typing;

import ai.services.composition.*;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.composition.types.IDataValueType;
import ai.services.composition.types.TypeClass;
import ai.services.core.PrimitiveType;
import ai.services.exec.IMethodDesc;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ParamTypeCoercionResolver extends InstWiseCompileStep<TCInput, TCOutput> {

    ParamTypeCoercionResolver(InstOutputIterator<TCOutput> outputIterator) {
        super(outputIterator);
    }

    @Override
    public void stepInst() {
        IInstructionNode inst = getCurrentInstruction().getInstruction();
        TCOutput output = getInstOutput();
        TCOutput.FieldTC fieldTC = output.getFieldTC();
        IMethodDesc method = output.getMethodInfo().getMethodDesc();
        String typeContext = output.getContext().getServiceQualifier() + "::" + output.getMethodInfo().getMethodQualifier();

        /*
         * Type-check signature by creating type coercions.
         *
         * If there exists a list of TypeCoercion then the parameter signature type checks.
         */
        List<ITypeCoercion> parameterTypeCoercions = new ArrayList<>();
        int inputParamSize = method.getInputs().size();
        for (int i = 0; i < inputParamSize; i++) {
            String expectedInputType = method.getInputs().get(i).getType();
            String instParam = inst.getParameterFields().get(i);

            Objects.requireNonNull(expectedInputType);
            Objects.requireNonNull(instParam);

            ITypeCoercion typeCoercion;
            if(FMCompositionParser.isConstant(instParam)) {
                /*
                 * The given param is a constant, e.g. a number like `5`
                 */
                PrimitiveType givenConstantType = FMCompositionParser.primitiveTypeFor(instParam);

                if(givenConstantType == PrimitiveType.NULL) {
                    /*
                     * null can be plugged into any expected type.
                     *
                     */
                    typeCoercion = nullParam(instParam, expectedInputType);
                } else {
                    /*
                     * Typecheck primitive types: check if the given primitive type matches the declared one:
                     */
                    PrimitiveType expectedConstantType;
                    expectedConstantType = PrimitiveType.insensitiveValueOf(expectedInputType).orElse(null);

                    if (expectedConstantType == null || expectedConstantType == PrimitiveType.NULL) {
                        throw TypeCheckException.unexpectedConstantTypeDeclaration(givenConstantType, expectedInputType);
                    }
                    if(expectedConstantType != givenConstantType) {
                        throw TypeCheckException.unexpectedConstantType(instParam, givenConstantType.name(), expectedInputType,
                            "Expected primitive type doesn't match the given constant.");
                    }
                    typeCoercion = constantParam(instParam, givenConstantType);
                }
            } else {
                /*
                 * The given param is a field, e.g. `a`
                 * We look into the typing context to get the type of the field:
                 */
                TypeClass paramType = fieldTC.getFieldType(instParam);
                if(paramType == null) {
                    throw TypeCheckException.undefinedField(instParam);
                }
                typeCoercion = castValue(paramType.getTypeQualifier(), expectedInputType);
                if(typeCoercion.hasTypeConversion()) {
                    TypeClass resultingTypeClass = TypeUtil.getTypeClassOf(getInput().getLookupService(),
                        typeCoercion.getResultType());
                    if(!(resultingTypeClass instanceof IDataValueType)) {
                        throw new RuntimeException("BUG: the new type class was not data value");
                    }
                    fieldTC.setFieldType(instParam, resultingTypeClass);
                }
            }
            parameterTypeCoercions.add(typeCoercion);
        }
        output.getMethodInfo().setParameterTypeCoercions(parameterTypeCoercions);
    }

    private ITypeCoercion nullParam(String constant, String expectedType) {
        return TypeCoercion.builder()
            .constant(constant)
            .sourceType(PrimitiveType.NULL.name())
            .resultType(expectedType)
            .build();
    }

    private ITypeCoercion constantParam(String constant, PrimitiveType primType) {
        /*
         * The parameter is a constant.
         * Replace the input type by the primitive type:
         */

        return primType(constant, primType);
    }

    private ITypeCoercion castValue(String sourceType, String targetType) {
        if(PrimitiveType.insensitiveValueOf(sourceType).isPresent()) {
            return castPrimitive(sourceType, targetType);
        }

        IDataTypeRef sourceTypeRef = IDataTypeRef.of(sourceType);
        Optional<IDataTypeDesc> sourceTypeDescOpt = getInput().getLookupService().lookup(sourceTypeRef);
        if(!sourceTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(sourceType, "Data Type");
        }
        if(sourceType.equals(targetType)) {
            /*
             * No type coercion is needed:
             */
            return sameType(sourceType, sourceTypeDescOpt.get().getSemanticType());
        }
        IDataTypeRef targetTypeRef = IDataTypeRef.of(targetType);
        Optional<IDataTypeDesc> targetTypeDescOpt = getInput().getLookupService().lookup(targetTypeRef);
        if(!targetTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(targetType, "data type");
        }
        String sourceSemType = sourceTypeDescOpt.get().getSemanticType();
        String targetSemType = targetTypeDescOpt.get().getSemanticType();
        if(!sourceSemType.equals(targetSemType)) {
            throw TypeCheckException.nonMatchingSemanticType(sourceType, sourceSemType, targetType, targetSemType);
        }
        return typeCast(sourceType, targetType, sourceSemType);
    }

    private ITypeCoercion castPrimitive(String sourceType, String targetType) {
        Optional<PrimitiveType> sourcePTime = PrimitiveType.insensitiveValueOf(targetType);
        Optional<PrimitiveType> targetPType = PrimitiveType.insensitiveValueOf(targetType);

        if(sourcePTime.isPresent() && targetPType.isPresent()) {
            if(sourcePTime.get() != targetPType.get()) {
                throw new TypeCheckException(String.format("Cannot cast between types: %s -> %s. Primitive types dont match.",
                    sourceType, targetType));
            }
            return sameType(sourceType, sourceType);
        } else {
            throw new TypeCheckException(String.format("Cannot cast between types: %s -> %s. Casting between primitive type is not allowed.",
                sourceType, targetType));
        }
    }


    static ITypeCoercion primType(String constant, PrimitiveType primT) {
        ITypeCoercion tc = TypeCoercion.builder()
            .constant(constant)
            .sourceType(primT.name())
            .resultType(primT.name())
            .build();
        assert tc.getSemanticType() == null;
        return tc;
    }

    static ITypeCoercion sameType(String sourceType, String semanticType) {
        ITypeCoercion tc = TypeCoercion.builder()
            .sourceType(sourceType)
            .resultType(sourceType)
            .semanticType(semanticType)
            .build();
        assert tc.getConstant() == null;
        return tc;
    }

    static ITypeCoercion typeCast(String source, String result, String semantic) {
        ITypeCoercion tc = TypeCoercion.builder()
            .sourceType(source)
            .resultType(result)
            .semanticType(semantic)
            .build();
        assert tc.getConstant() == null;
        return tc;
    }


}
