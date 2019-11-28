package de.upb.sede.composition.graphs.types;

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

@JsonDeserialize(using = TypeClass.DeserializeDelegate.class)
public interface TypeClass {

    @Value.Lazy
    default String getTypeClass() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

    @Value.Lazy
    String getTypeQualifier();

    class DeserializeDelegate extends TypeDeserializationDelegate<TypeClass> {

        protected DeserializeDelegate() {
            super(TypeClass.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        protected String getTypeField() {
            return "typeClass";
        }
    }
}
