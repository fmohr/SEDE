package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.upb.sede.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

import java.io.IOException;

/**
 * This interface is inherited by all node interface models.
 * It includes a "nodeType" field which defaults to the generated immutable class name. With help of this field   the find type of the node is determined when deserializing. (See IBaseNode.DeserializeDelegate)
 * For this reason nodeType ought not to be changed.
 */
@JsonDeserialize(using = BaseNode.DeserializeDelegate.class)
public interface BaseNode extends WithExecutorHost, WithAux, WithIndex {

    @Value.Lazy
    default String getNodeType() {
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
            return "nodeType";
        }

    }
}
