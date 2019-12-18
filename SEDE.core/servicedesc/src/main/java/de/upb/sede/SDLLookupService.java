package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class exposes aspects of an SDLBase in a standard manner.
 *
 */
public interface SDLLookupService {

    Optional<IServiceCollectionDesc> lookupCollection(IServiceRef serviceRef);

    Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef);

    Optional<IServiceDesc> lookup(IServiceRef serviceRef);

    Optional<IMethodDesc> lookup(IMethodRef methodRef);

    Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef);

    Stream<IServiceCollectionRef> allCollectionRefs();

    Stream<IServiceRef> allServiceRefs();

    Stream<IServiceRef> serviceRefsIn(IServiceCollectionRef collectionRef);

}
