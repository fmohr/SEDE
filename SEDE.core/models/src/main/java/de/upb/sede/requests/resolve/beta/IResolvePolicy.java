package de.upb.sede.requests.resolve.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ResolvePolicy.Builder.class)
public interface IResolvePolicy {
    ReturnPolicy getReturnPolicy();

    ReturnPolicy getServicePolicy();

    List<String> getReturnFieldnames();

    List<String> getPersistentServices();

    boolean isBlockExecRequested();

    boolean isDotGraphRequested();

    enum ReturnPolicy {
         ALL, NONE, LISTED
    }
}
