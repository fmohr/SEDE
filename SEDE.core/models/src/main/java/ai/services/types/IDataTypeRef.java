package ai.services.types;

import ai.services.IQualifiable;
import ai.services.SEDEModelStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.ConstructReference;
import ai.services.IServiceCollectionRef;
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
