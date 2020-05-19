package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IMethodRef;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodResolution.Builder.class)
public interface IMethodResolution {

    IMethodRef getMethodRef();

    List<ITypeCoercion> getParamTypeCoercions();

}



