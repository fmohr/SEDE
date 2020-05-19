package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceCollectionRef.Builder.class)
public interface IServiceCollectionRef extends ConstructReference {

    static IServiceCollectionRef of(String collectionQualifier) {
        return ServiceCollectionRef.builder()
            .ref(IQualifiable.of(collectionQualifier))
            .build();
    }

}
