package ai.services;

import ai.services.exec.IMethodDesc;
import ai.services.exec.IMethodRef;
import ai.services.exec.IServiceDesc;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;

import java.util.List;
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

    List<IMethodDesc> lookup(IMethodRef methodRef);

    Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef);

    Stream<IServiceCollectionRef> allCollectionRefs();

    Stream<IServiceRef> allServiceRefs();

    Stream<IServiceRef> serviceRefsIn(IServiceCollectionRef collectionRef);

}
