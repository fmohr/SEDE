package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.sun.tools.doclets.internal.toolkit.util.CommentedMethodFinder;
import de.upb.sede.util.DynTypeField;
import org.immutables.value.Value;

import java.io.IOException;
import java.util.HashMap;

@JsonDeserialize(using = IBaseNode.DeserializeDelegate.class)
public interface IBaseNode {

    @Value.Default
    default String getNodeType() {
        String className = this.getClass().getSimpleName();
        if(className.startsWith("Mutable")) {
            // Class is Mutable:
            className = className.substring("Mutable".length());
        }
        else if(className.startsWith("I") &&
            (Character.isUpperCase(className.charAt(1)))) {
            // Class is an interface:
            className = className.substring(1);
        }
        return className;
    }

    class DeserializeDelegate extends StdDeserializer<IBaseNode> {

        protected DeserializeDelegate() {
            super(IBaseNode.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        public IBaseNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

            /*
             * Retrieve node type:
             */
            TreeNode tree = p.getCodec().readTree(p);
            if(! (tree instanceof ObjectNode)) {
                throw new InvalidFormatException(p, "Expected Base Node Object", tree, IBaseNode.class);
            }
            ObjectNode object = (ObjectNode) tree;
            TreeNode nodeType = object.get("nodeType");
            if(nodeType == null) {
                throw new JsonMappingException(p, "No node type was provided.");
            }
            if(!nodeType.isValueNode() || !((ValueNode)nodeType).isTextual())
                throw new JsonMappingException(p, "Nodetype is not text.");
            String nodeTypeField = ((ValueNode) nodeType).textValue();
            if(nodeTypeField == null || nodeTypeField.isEmpty())
                throw new JsonMappingException(p, "Empty nodetype.");

            /*
             * Retrieve target deserialization class:
             */
            Class<IBaseNode> targetNodeClass;
            String interfaceName =
                "de.upb.sede.composition.graphs.nodes.I" +
                nodeTypeField;
            try {
                targetNodeClass = (Class<IBaseNode>)
                    Class.forName(interfaceName);
            } catch (ClassNotFoundException e) {
                throw new JsonMappingException(p, "Node type is " +
                    "not known: " + interfaceName, e);
            }

            /*
             * Cast to target class:
             */
            return tree
                .traverse(p.getCodec())
                .readValueAs(targetNodeClass);
        }
    }
}
