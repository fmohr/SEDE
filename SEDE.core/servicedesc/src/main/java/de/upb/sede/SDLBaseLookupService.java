package de.upb.sede;

import de.upb.sede.exec.*;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * This class exposes aspects of an SDLBase in a standard manner.
 *
 * This is done by:
 *      -
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
    public Optional<IServiceCollectionDesc> lookup(IServiceCollectionRef collectionRef) {
        requireNonNull(collectionRef, "Collection reference is null");
        Stream<IServiceCollectionDesc> collStream = sdlBase.getCollections()
            .stream()
            .filter(col ->
                col.getQualifier()
                    .equals(collectionRef
                        .getRef()
                        .getQualifier()));
        return pickOneOrNone(collStream);
    }

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

        Stream<IServiceDesc> serviceStream = searchscope.stream()
            .map(col ->
                col.getServices().stream()
                    .filter(service ->
                        service.getQualifier()
                            .equals(serviceRef.getRef().getQualifier()))
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

        Stream<IMethodDesc> methodStream = serviceDesc.getMethods().stream()
            .filter(method ->
                method.getQualifier().equals(methodRef.getRef().getQualifier()));

        return pickOneOrNone(methodStream);
    }

    @Override
    public Optional<IDataTypeDesc> lookup(IDataTypeRef dataTypeRef) {
        requireNonNull(dataTypeRef, "DataType reference is null");

        List<IServiceCollectionDesc> searchscope = getSearchScope(dataTypeRef.getServiceCollectionRef());

        Stream<IDataTypeDesc> typeStream = searchscope.stream()
            .map(col ->
                col.getDataTypes().stream()
                    .filter(dataType ->
                        dataType.getQualifier()
                            .equals(dataTypeRef.getRef().getQualifier()))
                    .findFirst())
            .filter(Optional::isPresent)
            .map(Optional::get);

        return pickOneOrNone(typeStream);
    }
}
