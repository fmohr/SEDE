package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodCognition.Builder.class)
public interface IMethodCognition {

    IMethodRef getMethodRef();

    ISignatureDesc getSignatureDesc();

    IMethodDesc getMethodDesc();

    IServiceDesc getServiceDesc();

    List<ITypeCoercion> getParameterTypeCoersion();

}



