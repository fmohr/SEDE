package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.DataTypeDesc;
import de.upb.sede.types.DataTypeRef;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SDLCacheLookupService implements ISDLLookupService,
    Function<ConstructReference, Object> {

    private final Map<ConstructReference, Object> refCache = new HashMap<>();

    private final ISDLLookupService lookupServiceDelegate;

    public SDLCacheLookupService(ISDLLookupService lookupServiceDelegate) {
        this.lookupServiceDelegate = lookupServiceDelegate;
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> lookupInCache(ConstructReference ref) {
        return Optional.ofNullable(
            (T) refCache.computeIfAbsent(ref, this));
    }


    @Override
    public Optional<IServiceCollectionDesc> lookupCollection(IServiceRef serviceRef) {
        return lookupServiceDelegate.lookupCollection(serviceRef); // TODO cache this result somehow..
    }

    @Override
    public Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef) {
        return lookupInCache(collectionRef);
    }

    @Override
    public Optional<IServiceDesc> lookup(IServiceRef serviceRef) {
        return lookupInCache(serviceRef);
    }

    @Override
    public Optional<IMethodDesc> lookup(IMethodRef methodRef) {
        return lookupInCache(methodRef);
    }

    @Override
    public Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef) {
        return lookupInCache(dataTypeRef);
    }

    /**
     * Creates a layered Cache object by looking up the content in the lookupServiceDelegate.
     * @param ref Has to be a ConstructReference
     * @return The resulting Construct
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object apply(ConstructReference ref) {
        Optional construct;
        if(ref instanceof  IServiceCollectionRef) {
            IServiceCollectionRef collectionRef = (IServiceCollectionRef) ref;
            construct = lookupServiceDelegate.lookup(collectionRef);
        }
        else if(ref instanceof  IServiceRef) {
            construct = lookupServiceDelegate.lookup((IServiceRef) ref);
        }
        else if(ref instanceof IMethodRef) {
            construct = lookupServiceDelegate.lookup((IMethodRef) ref);
        }
        else if(ref instanceof IDataTypeRef) {
            construct = lookupServiceDelegate.lookup((IDataTypeRef)ref);
        } else {
            throw new IllegalArgumentException("Type of construct reference is not known: " + ref.toString() + " of type: " + ref.getClass().getName());
        }
        return construct.orElse(null);
    }
}
