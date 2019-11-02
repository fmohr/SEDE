package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * This class exposes aspects of an SDLBase in a standard manner.
 *
 */
public class SDLBaseLookupService implements ISDLLookupService{

    private final ISDLBase sdlBase;



    public SDLBaseLookupService(ISDLBase sdlBase) {
        this.sdlBase = sdlBase;
    }

    /**
     * This utility function will reduce the given stream to an optional iff the stream contains exactly one element.
     *
     * This function has a linear runtime.
     *
     * @param objStream Object stream which is reduced.
     * @param <T> The type of the stream objects.
     * @return The content of the stream if it contains exactly 1 element or else an empty Optional.
     */
    private <T> Optional<T> pickOneOrNone(Stream<T> objStream) {
        return objStream
            // Limit the stream to only 2 objects
            .limit(2)
            // choose null if more than one obj exists.
            .collect(Collectors.reducing((a, b) -> null));

        // Note: dont use '.reduce( (a,b) -> null)'
        // The Stream::reduce operation will wrap the returned value using Optional::of and a NPException will be thrown.
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

        return pickOneOrNone(methodStream);
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

    private static class QualifierMatcher implements Predicate<IQualifiable> {

        String qualifier;

        QualifierMatcher(String qualifier) {
            qualifier = qualifier;
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
