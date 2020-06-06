package de.upb.sede.composition.types;

import de.upb.sede.IQualifiable;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.List;

public interface ValueTypeClass extends IQualifiable, TypeClass {

    @Override
    @Value.Lazy
    default String getTypeQualifier() {
        return getQualifier();
    }

    @Override
    @Value.Lazy
    default String getSimpleName() {
        return getQualifier();
    }

    @Override
    @Value.Lazy
    default List<String> getMetaTags() {
        return Collections.EMPTY_LIST;
    }


}
