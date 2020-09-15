package ai.services.param;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.IQualifiable;
import ai.services.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@JsonDeserialize(using = IParameter.DeserializeDelegate.class)
public interface IParameter extends IQualifiable {

    @Nullable
    Object getDefaultValue();

    @Value.Lazy
    default String getParamKind() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

    class DeserializeDelegate extends TypeDeserializationDelegate<IParameter> {

        protected DeserializeDelegate() {
            super(IParameter.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        protected String getTypeField() {
            return "paramKind";
        }

    }

    @Value.Default
    default boolean isOptional(){
        return false;
    }

}
