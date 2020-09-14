package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SDLCacheLookupService extends SDLLookupServiceAdapter implements SDLLookupService {

    private final Map<ConstructReference, Object> refCache = new HashMap<>();


    public SDLCacheLookupService(SDLLookupService lookupServiceDelegate) {
        super(lookupServiceDelegate);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> lookupInRefCache(ConstructReference ref) {
        return Optional.ofNullable(
            (T) refCache.computeIfAbsent(ref, this::lookupfMiss));
    }

    @Override
    public Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef) {
        return lookupInRefCache(collectionRef);
    }

    @Override
    public Optional<IServiceDesc> lookup(IServiceRef serviceRef) {
        return lookupInRefCache(serviceRef);
    }

    @Override
    public Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef) {
        return lookupInRefCache(dataTypeRef);
    }

    /**
     * Creates a layered Cache object by looking up the content in the lookupServiceDelegate.
     * @param ref Has to be a ConstructReference
     * @return The resulting Construct
     */
    @SuppressWarnings("unchecked")
    private Object lookupfMiss(ConstructReference ref) {
        Optional construct;
        if(ref instanceof  IServiceCollectionRef) {
            IServiceCollectionRef collectionRef = (IServiceCollectionRef) ref;
            construct = lookupServiceDelegate.lookup(collectionRef);
        }
        else if(ref instanceof  IServiceRef) {
            construct = lookupServiceDelegate.lookup((IServiceRef) ref);
        }
        else if(ref instanceof IDataTypeRef) {
            construct = lookupServiceDelegate.lookup((IDataTypeRef)ref);
        } else {
            throw new IllegalArgumentException("Type of construct reference is not known: " + ref.toString() + " of type: " + ref.getClass().getName());
        }
        return construct.orElse(null);
    }
}
