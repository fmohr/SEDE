package ai.services;

import org.immutables.value.Value;

import java.util.List;

public interface QualifierDefinition extends IQualifiable {

    @Value.Auxiliary
    List<String> getMetaTags();

    @Value.Default
    @Value.Auxiliary
    default String getSimpleName(){
        return getQualifier();
    }

}
