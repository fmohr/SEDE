package ai.services.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.exec.IMethodRef;
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



