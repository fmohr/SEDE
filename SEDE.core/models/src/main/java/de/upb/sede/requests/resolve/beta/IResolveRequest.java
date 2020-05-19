package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.CommentAware;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ResolveRequest.Builder.class)
public interface IResolveRequest {
}

