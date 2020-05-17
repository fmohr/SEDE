package de.upb.sede.composition.typing;

import de.upb.sede.composition.*;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.graphs.types.TypeClass;
import de.upb.sede.core.PrimitiveType;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

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
                typeCoercion = constantParam(instParam, expectedInputType);
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
            }
            parameterTypeCoercions.add(typeCoercion);
        }
        output.getMethodInfo().setParameterTypeCoercions(parameterTypeCoercions);
    }



    private ITypeCoercion constantParam(String constant, String targetPrimType) {
        /*
         * The parameter is a constant.
         * Replace the input type by the primitive type:
         */
        ITypeCoercion typeCoercion = primType(constant);
        if(!typeCoercion.getResultType().equals(targetPrimType)) {
            throw TypeCheckException.unexpectedConstantType(constant, typeCoercion.getSourceType(), targetPrimType,
                "The expected type");
        }
        return typeCoercion;
    }

    private ITypeCoercion castValue(String sourceType, String targetType) {
        IDataTypeRef sourceTypeRef = IDataTypeRef.of(sourceType);
        Optional<IDataTypeDesc> sourceTypeDescOpt = getInput().getLookupService().lookup(sourceTypeRef);
        if(!sourceTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(sourceType, "Data Type");
        }
        if(sourceType.equals(targetType)) {
            /*
             * No type coercion is needed:
             */
            return sameType(sourceType);
        }
        IDataTypeRef targetTypeRef = IDataTypeRef.of(targetType);
        Optional<IDataTypeDesc> targetTypeDescOpt = getInput().getLookupService().lookup(targetTypeRef);
        if(!targetTypeDescOpt.isPresent()) {
            throw TypeCheckException.unknownType(targetType, "data type");
        }
        String sourceSemType = sourceTypeDescOpt.get().getSemanticType();
        String targetSemType = sourceTypeDescOpt.get().getSemanticType();
        if(!sourceSemType.equals(targetSemType)) {
            throw TypeCheckException.nonMatchingSemanticType(sourceType, sourceSemType, targetType, targetSemType);
        }
        return typeCast(sourceType, targetType, sourceSemType);
    }


    static ITypeCoercion primType(String constant) {
        PrimitiveType primT = FMCompositionParser.primitiveTypeFor(constant);
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .constant(constant)
            .sourceType(primT.name())
            .resultType(primT.name())
            .build();
        assert tc.getSemanticType() == null;
        return tc;
    }

    static ITypeCoercion sameType(String type) {
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .sourceType(type)
            .resultType(type)
            .build();
        assert tc.getConstant() == null;
        assert tc.getSemanticType() == null;
        return tc;
    }

    static ITypeCoercion typeCast(String source, String result, String semantic) {
        ITypeCoercion tc = de.upb.sede.composition.TypeCoercion.builder()
            .sourceType(source)
            .resultType(result)
            .semanticType(semantic)
            .build();
        assert tc.getConstant() == null;
        return tc;
    }


}
