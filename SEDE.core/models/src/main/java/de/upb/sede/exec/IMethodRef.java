package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ConstructReference;
import de.upb.sede.IQualifiable;
import de.upb.sede.IServiceRef;
import de.upb.sede.SEDEModelStyle;
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
