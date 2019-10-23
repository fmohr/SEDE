package de.upb.sede.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.*;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataTypeRef.Builder.class)
public interface IDataTypeRef extends ConstructReference {

    @Nullable
    IServiceCollectionRef getServiceCollectionRef();

}
