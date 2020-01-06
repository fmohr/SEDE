package de.upb.sede;

import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.IServiceRef;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Optional;
import java.util.stream.Stream;

public class SDLLookupServiceAdapter implements SDLLookupService {


    protected final SDLLookupService lookupServiceDelegate;

    public SDLLookupServiceAdapter(SDLLookupService service) {
        lookupServiceDelegate = service;
    }

    public Optional<IServiceCollectionDesc> lookupCollection(IServiceRef serviceRef) {
        return lookupServiceDelegate.lookupCollection(serviceRef);
    }

    public Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef) {
        return lookupServiceDelegate.lookup(collectionRef);
    }

    public Optional<IServiceDesc> lookup(IServiceRef serviceRef) {
        return lookupServiceDelegate.lookup(serviceRef);
    }

    public Optional<IMethodDesc> lookup(IMethodRef methodRef) {
        return lookupServiceDelegate.lookup(methodRef);
    }

    public Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef) {
        return lookupServiceDelegate.lookup(dataTypeRef);
    }

    public Stream<IServiceCollectionRef> allCollectionRefs() {
        return lookupServiceDelegate.allCollectionRefs();
    }

    public Stream<IServiceRef> allServiceRefs() {
        return lookupServiceDelegate.allServiceRefs();
    }

    public Stream<IServiceRef> serviceRefsIn(IServiceCollectionRef collectionRef) {
        return lookupServiceDelegate.serviceRefsIn(collectionRef);
    }


}
