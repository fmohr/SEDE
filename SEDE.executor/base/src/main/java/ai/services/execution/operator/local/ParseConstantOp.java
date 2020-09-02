package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import de.upb.sede.composition.graphs.nodes.IParseConstantNode;
import de.upb.sede.core.PrimitiveDataField;
import de.upb.sede.core.PrimitiveType;
import de.upb.sede.core.SEDEObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class ParseConstantOp extends MainTaskOperator {

    public ParseConstantOp() {
        super(IParseConstantNode.class);
    }

    @Override
    public TaskTransition runTask(Task t) throws Exception {
        IParseConstantNode node = (IParseConstantNode) t.getNode();
        SEDEObject parsedVal = parsePrimitive(node.getConstantValue(), node.getConstantType());
        t.setMainTaskPerformed();
        return TaskTransition.fieldAssignment(node.getConstantValue(), parsedVal);
    }

    public static SEDEObject parsePrimitive(String constantStr, PrimitiveType primitiveType) {
        Object data;
        switch (Objects.requireNonNull(primitiveType)) {
            case NULL:
                data = null;
                break;
            case Bool:
                data = Boolean.valueOf(constantStr);
                break;
            case Number:
                try {
                    data = NumberFormat.getInstance().parse(constantStr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case String:
                data = constantStr.substring(1, constantStr.length() - 1);
                break;
            default:
                throw new RuntimeException("Primitive Type is left unhandled: " + primitiveType);
        }
        return new PrimitiveDataField(primitiveType.name(), data);
    }

}
