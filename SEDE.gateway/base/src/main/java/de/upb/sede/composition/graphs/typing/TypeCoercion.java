package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.core.PrimitiveType;

import javax.annotation.Nullable;
import java.util.Objects;

public class TypeCoercion {

    @Nullable
    private String constant;

    private String sourceType;

    @Nullable
    private String semanticType;

    private String resultType;

    private TypeCoercion() {
    }


    public static TypeCoercion primType(String constant) {
        PrimitiveType primT = FMCompositionParser.primitiveTypeFor(constant);
        TypeCoercion tc = new TypeCoercion();
        tc.setConstant(constant);
        tc.setSourceType(primT.name());
        tc.setResultType(primT.name());
        assert tc.getSemanticType() == null;
        return tc;
    }

    public static TypeCoercion sameType(String type) {
        TypeCoercion tc = new TypeCoercion();
        tc.setSourceType(type);
        tc.setResultType(type);
        assert tc.getConstant() == null;
        assert tc.getSemanticType() == null;
        return tc;
    }

    public static TypeCoercion typeCast(String source, String result, String semantic) {
        TypeCoercion tc = new TypeCoercion();
        tc.setSourceType(source);
        tc.setResultType(result);
        tc.setSemanticType(semantic);
        assert tc.getConstant() == null;
        return tc;
    }

    public String getResultType() {
        return Objects.requireNonNull(resultType);
    }

    public String getConstant() {
        return Objects.requireNonNull(constant);
    }

    public void setConstant(@Nullable String constant) {
        this.constant = constant;
    }

    public String getSourceType() {
        return Objects.requireNonNull(sourceType);
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSemanticType() {
        return Objects.requireNonNull(semanticType);
    }

    public void setSemanticType(@Nullable String semanticType) {
        this.semanticType = semanticType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public boolean hasConstant() {
        return constant != null;
    }

    public boolean hasTypeConversion() {
        return semanticType != null;
    }
}
