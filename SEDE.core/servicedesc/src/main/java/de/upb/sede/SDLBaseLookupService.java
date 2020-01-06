package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;
import de.upb.sede.util.Streams;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static de.upb.sede.util.Streams.pickOneOrNone;

import static java.util.Objects.requireNonNull;

/**
 * This class exposes aspects of an SDLBase in a standard manner.
 *
 */
public class SDLBaseLookupService implements SDLLookupService {

    private final ISDLAssembly sdlBase;


    public SDLBaseLookupService(ISDLAssembly sdlBase) {
        this.sdlBase = sdlBase;
    }


    @Override
    public Optional<IServiceCollectionDesc> lookupCollection(IServiceRef serviceRef) {
        requireNonNull(serviceRef, "Service reference is null");

        List<IServiceCollectionDesc> searchscope = getSearchScope(serviceRef.getServiceCollectionRef());

        Predicate<IQualifiable> matcher = new QualifierMatcher(serviceRef);

        Stream<IServiceCollectionDesc> serviceStream = searchscope.stream()
            .filter(col -> col.getServices().stream().anyMatch(matcher));
        return pickOneOrNone(serviceStream);
    }

    @Override
    public Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef) {
        requireNonNull(collectionRef, "Collection reference is null");
        Predicate<IQualifiable> matcher = new QualifierMatcher(collectionRef);
        Stream<IServiceCollectionDesc> collStream = sdlBase.getCollections()
            .stream()
            .filter(matcher);
        return pickOneOrNone(collStream);
    }

    @SuppressWarnings("unchecked")
    private List<IServiceCollectionDesc> getSearchScope(IServiceCollectionRef collectionRef) {
        List<IServiceCollectionDesc> scope;
        if(collectionRef != null) {
            Optional<IServiceCollectionDesc> collectionDesc = lookup(collectionRef);
            if(collectionDesc.isPresent()) {
                scope = Collections.singletonList(collectionDesc.get());
            } else {
                scope = Collections.EMPTY_LIST;
            }
        } else {
            scope = sdlBase.getCollections();
        }
        return scope;
    }

    @Override
    public Optional<IServiceDesc> lookup(IServiceRef serviceRef) {
        requireNonNull(serviceRef, "Service reference is null");

        List<IServiceCollectionDesc> searchscope = getSearchScope(serviceRef.getServiceCollectionRef());

        Predicate<IQualifiable> matcher = new QualifierMatcher(serviceRef);

        Stream<IServiceDesc> serviceStream = searchscope.stream()
            .map(col ->
                col.getServices().stream()
                    .filter(matcher)
                    .findFirst())
            .filter(Optional::isPresent)
            .map(Optional::get);

        return pickOneOrNone(serviceStream);
    }

    @Override
    public Optional<IMethodDesc> lookup(IMethodRef methodRef) {
        requireNonNull(methodRef, "Method reference is null");

        Optional<IServiceDesc> optServiceDesc = lookup(methodRef.getServiceRef());
        if (!optServiceDesc.isPresent()) {
            return Optional.empty();
        }

        IServiceDesc serviceDesc = optServiceDesc.get();

        Predicate<IQualifiable> matcher = new QualifierMatcher(methodRef);

        Stream<IMethodDesc> methodStream = serviceDesc.getMethods()
            .stream()
            .filter(matcher);

        return pickOneOrNone(methodStream); // TODO method overloading
    }

    @Override
    public Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef) {
        requireNonNull(dataTypeRef, "DataType reference is null");

        List<IServiceCollectionDesc> searchscope = getSearchScope(dataTypeRef.getServiceCollectionRef());

        Predicate<IQualifiable> matcher = new QualifierMatcher(dataTypeRef);

        Stream<IDataTypeDesc> typeStream = searchscope.stream()
            .map(col ->
                col.getDataTypes().stream()
                    .filter(matcher)
                    .findFirst())
            .filter(Optional::isPresent)
            .map(Optional::get);

        return pickOneOrNone(typeStream);
    }

    @Override
    public Stream<IServiceCollectionRef> allCollectionRefs() {
        return this.sdlBase.getCollections().stream()
            .map(IServiceCollectionDesc::getQualifier)
            .map(IServiceCollectionRef::of);
    }

    @Override
    public Stream<IServiceRef> allServiceRefs() {
        Stream<Stream<IServiceRef>> serviceRefStreams = allCollectionRefs()
            .map(this::lookup)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(SDLBaseLookupService::extractServiceRefs);
        return Streams.flatten(serviceRefStreams);
    }

    @Override
    public Stream<IServiceRef> serviceRefsIn(IServiceCollectionRef collectionRef) {
        Optional<IServiceCollectionDesc> optCollection = lookup(collectionRef);
        if (!optCollection.isPresent()) {
            return Stream.empty();
        } else {
            IServiceCollectionDesc collection = optCollection.get();
            return extractServiceRefs(collection);
        }
    }

    private static Stream<IServiceRef> extractServiceRefs(IServiceCollectionDesc collection) {
        return collection.getServices()
            .stream()
            .map(service ->
                IServiceRef.of(collection.getQualifier(), service.getQualifier()));
    }


    private static class QualifierMatcher implements Predicate<IQualifiable> {

        String qualifier;

        QualifierMatcher(String qualifier) {
            this.qualifier = qualifier;
        }

        QualifierMatcher(IQualifiable qualifiable) {
            this(qualifiable.getQualifier());
        }

        QualifierMatcher(IServiceRef serviceRef) {
            this(serviceRef.getRef());
        }

        public QualifierMatcher(IServiceCollectionRef collectionRef) {
            this(collectionRef.getRef());
        }

        public QualifierMatcher(IMethodRef methodRef) {
            this(methodRef.getRef());
        }

        public QualifierMatcher(IDataTypeRef dataTypeRef) {
            this(dataTypeRef.getRef());
        }


        @Override
        public boolean test(IQualifiable iQualifiable) {
            return qualifier.equals(iQualifiable.getQualifier());
        }

    }
}
