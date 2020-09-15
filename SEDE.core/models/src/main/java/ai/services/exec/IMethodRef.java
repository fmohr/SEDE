package ai.services.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.ConstructReference;
import ai.services.IQualifiable;
import ai.services.IServiceRef;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodRef.Builder.class)
public interface IMethodRef extends ConstructReference {

    IServiceRef getServiceRef();

    static IMethodRef of(IServiceRef serviceRef, String methodQualifier) {
        return MethodRef.builder().serviceRef(serviceRef).ref(IQualifiable.of(methodQualifier)).build();
    }

}
