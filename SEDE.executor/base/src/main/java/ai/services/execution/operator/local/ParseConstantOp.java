package ai.services.execution.operator.local;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.MainTaskOperator;
import ai.services.composition.graphs.nodes.IParseConstantNode;
import ai.services.core.PrimitiveDataField;
import ai.services.core.Primitives;
import ai.services.core.SEDEObject;

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

    public static SEDEObject parsePrimitive(String constantStr, Primitives primitives) {
        Object data;
        switch (Objects.requireNonNull(primitives)) {
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
                if(!constantStr.startsWith("\"") || !constantStr.endsWith("\"")) {
                    throw new IllegalArgumentException("String primitive is malformed. Expected quotes: " + constantStr);
                }
                data = constantStr.substring(1, constantStr.length() - 1);
                break;
            default:
                throw new RuntimeException("Primitive Type is left unhandled: " + primitives);
        }
        return new PrimitiveDataField(primitives.name(), data);
    }

}
