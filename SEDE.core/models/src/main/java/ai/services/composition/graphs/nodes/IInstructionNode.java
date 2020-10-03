package ai.services.composition.graphs.nodes;

import ai.services.composition.types.TypeClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = InstructionNode.Builder.class)
public interface IInstructionNode extends BaseNode, WithField {

    String UNASSIGNED_VALUE = "UNDEFINED";


    String getFMInstruction();

    @Nullable
    String getFieldName();

    @Nullable
    TypeClass getFieldType();

    @Deprecated
    @Value.Default
    @Nullable
    default String getHost() {
        return null;
    }

    String getContext();

    boolean getContextIsFieldFlag();

    String getMethod();

    /**
     * Index of instruction output.
     * -1 means that the return value of the instruction is the output.
     * Any other value >= 0 will have the instruction return the ith parameter as output.
     */
    @Value.Default
    default int getOutputIndex() {
        return -1;
    }

    /**
     * Parameters for method or constructor invocation. The order of the parameters
     * has to be kept unchanged. May either contain field-names referencing to data
     * or else constants like numbers or strings. e.g.: ["a1", "b1", "10", "\"a\""]
     * The first two are fieldnames. The third is a constant number. The fourth is a
     * constant string.
     * <p>
     * <p>
     * The list itself is read-only.
     */
    List<String> getParameterFields();

    @Value.Lazy
    default boolean isAssignment() {
        return getFieldName() != null;
    }

    @Override
    default String getText() {
        return String.format("%s", getFMInstruction());
    }

    @Value.Default
    default boolean isEssential() {
        return true;
    }
}
