package de.upb.sede.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.io.IOException;

public abstract class TypeDeserializationDelegate<T> extends StdDeserializer<T> {

    protected TypeDeserializationDelegate(Class<?> vc) {
        super(vc);
    }

    protected abstract String getTypeField();

    private String getPackageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    public final T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        /*
         * Retrieve type:
         */
        TreeNode tree = p.getCodec().readTree(p);
        if(! (tree instanceof ObjectNode)) {
            throw new JsonMappingException(p, "Expected Object Node.");
        }
        ObjectNode object = (ObjectNode) tree;
        TreeNode nodeType = object.get(getTypeField());
        if(nodeType == null) {
            throw new JsonMappingException(p, "The field '" + getTypeField() + "' is undefined or null.");
        }
        if(!nodeType.isValueNode() || !((ValueNode)nodeType).isTextual())
            throw new JsonMappingException(p, "Type is not text.");
        String type = ((ValueNode) nodeType).textValue();
        if(type == null || type.isEmpty())
            throw new JsonMappingException(p, "Empty type.");

        /*
         * Retrieve target deserialization class:
         */
        Class<T> targetClass;
        String interfaceName =
            getPackageName() + ".I" +
                type;
        try {
            targetClass = (Class<T>)
                Class.forName(interfaceName);
        } catch (ClassNotFoundException e) {
            throw new JsonMappingException(p, "Type is " +
                "not known: " + interfaceName, e);
        }

        /*
         * Cast to target class:
         */
        return tree
            .traverse(p.getCodec())
            .readValueAs(targetClass);
    }

    public static String stripPrefix(String simpleClassName) {
        String className = simpleClassName;
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
}
