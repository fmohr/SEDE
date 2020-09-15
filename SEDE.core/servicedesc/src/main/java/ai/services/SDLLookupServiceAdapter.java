package ai.services;

import ai.services.exec.IMethodDesc;
import ai.services.exec.IMethodRef;
import ai.services.exec.IServiceDesc;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;

import java.util.List;
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

    public List<IMethodDesc> lookup(IMethodRef methodRef) {
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
