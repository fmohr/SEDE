package de.upb.sede.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.*;
import de.upb.sede.ConstructReference;
import de.upb.sede.IServiceCollectionRef;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataTypeRef.Builder.class)
public interface IDataTypeRef extends ConstructReference {

    public static IDataTypeRef of(String typeQualifier) {
        return DataTypeRef.builder().ref(IQualifiable.of(typeQualifier)).build();
    }

    @Nullable
    IServiceCollectionRef getServiceCollectionRef();

}
