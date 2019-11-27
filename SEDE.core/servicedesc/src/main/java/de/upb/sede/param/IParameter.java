package de.upb.sede.param;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.param.auxiliary.IJavaParameterizationAux;
import de.upb.sede.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@JsonDeserialize(using = IParameter.DeserializeDelegate.class)
public interface IParameter extends IQualifiable {

    @Nullable
    Object getDefaultValue();

    @Value.Lazy
    default String getParamType() {
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
            return "paramType";
        }

    }

    @Value.Default
    default boolean isOptional(){
        return false;
    }

}
