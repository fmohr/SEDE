package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

/**
 * This interface is inherited by all node interface models.
 * It includes a "nodeType" field which defaults to the generated immutable class name. With help of this field   the find type of the node is determined when deserializing. (See IBaseNode.DeserializeDelegate)
 * For this reason nodeType ought not to be changed.
 */
@JsonDeserialize(using = BaseNode.DeserializeDelegate.class)
public interface BaseNode extends WithExecutorHost, WithAux, WithIndex, WithEssentialFlag {

    @Value.Auxiliary
    default String getText() {
        return this.toString();
    }

    @Value.Lazy
    default String getNodeKind() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

    class DeserializeDelegate extends TypeDeserializationDelegate<BaseNode> {

        protected DeserializeDelegate() {
            super(BaseNode.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        protected String getTypeField() {
            return "nodeKind";
        }

    }
}
