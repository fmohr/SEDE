package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SDLBase.Builder.class)
public interface ISDLBase {

    List<IServiceCollectionDesc> getCollections();

}
