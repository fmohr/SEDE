package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Optional;

/**
 * This class exposes aspects of an SDLBase in a standard manner.
 *
 * This is done by:
 *      -
 */
public interface ISDLLookupService {

    Optional<IServiceCollectionDesc> lookupCollection(IServiceRef serviceRef);

    Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef);

    Optional<IServiceDesc> lookup(IServiceRef serviceRef);

    Optional<IMethodDesc> lookup(IMethodRef methodRef);

    Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef);
}
