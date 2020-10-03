package ai.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SDLAssembly.Builder.class)
public interface ISDLAssembly {

    List<IServiceCollectionDesc> getCollections();

}
