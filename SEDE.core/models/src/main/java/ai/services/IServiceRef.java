package ai.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceRef.Builder.class)
public interface IServiceRef extends ConstructReference {

    @Nullable
    IServiceCollectionRef getServiceCollectionRef();

    static IServiceRef of(@Nullable  String collectionQualifier, String serviceQualifier) {
        IServiceCollectionRef collectionRef = null;
        if(collectionQualifier != null)
            collectionRef = IServiceCollectionRef.of(collectionQualifier);

        return ServiceRef.builder()
            .serviceCollectionRef(collectionRef) // can be null
            .ref(IQualifiable.of(serviceQualifier))
            .build();
    }

}
