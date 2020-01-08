package de.upb.sede.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.IQualifiable;
import de.upb.sede.SDLLookupService;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.IServiceRef;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SDLUtil {

    private static ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static String serviceQualifier;

    public static <I, T> T toImmutable(I input, Class<T> tClass) {
        if(!isSMStyleAnnotated(input.getClass())) {
            throw new IllegalArgumentException("Cannot convert object of unkown type: " + input.getClass().getName());
        }
        if(!isSMStyleAnnotated(tClass)) {
            throw new IllegalArgumentException("Cannot convert to unkown immutable class: " + tClass);
        }
        return MAPPER.convertValue(input, tClass);
    }

    private static <T> boolean isSMStyleAnnotated(Class<T> tClass) {
        return Stream.of(tClass.getInterfaces()).anyMatch(i -> i.isAnnotationPresent(SEDEModelStyle.class));
    }

    /**
     * Linearizes the interfaces of the given service reference:
     *
     * @param lookupService lookup service
     * @param serviceRef the base service
     * @return c3 linearization of the service inheritance hierarchy.
     */
    public static List<IServiceDesc> c3Linearization(SDLLookupService lookupService, IServiceRef serviceRef) {
        return c3Linearization(lookupService, serviceRef, new HashSet<>());
    }

    /**
     * SEE: https://en.wikipedia.org/wiki/C3_linearization
     * TODO test this implementation
     */
    private static List<IServiceDesc> c3Linearization(SDLLookupService lookupService, IServiceRef baseServiceRef, Set<String> cyclicGuard) {
        if(cyclicGuard.contains(baseServiceRef.getRef().getQualifier())) {
            throw new IllegalArgumentException("There is a cycle in the linearization of the services: " + cyclicGuard);
        }
        cyclicGuard.add(baseServiceRef.getRef().getQualifier());
        Optional<IServiceDesc> optService = lookupService.lookup(baseServiceRef);
        if(!optService.isPresent()) {
            throw new IllegalArgumentException("Cannot resolve service reference: " + baseServiceRef);
        }
        IServiceDesc baseService = optService.get();

        // Create merge list:
        List<List<IServiceDesc>> mergeList =
                baseService.getInterfaces().stream()
                    .distinct() // no duplicate parents
                    .map(interfaceQualifier -> IServiceRef.of(null, interfaceQualifier))
                    .map(interfaceRef -> SDLUtil.c3Linearization(lookupService, interfaceRef, cyclicGuard))
            .collect(Collectors.toList());

        // Add the direct parents:
        List<IServiceDesc> parentList = baseService.getInterfaces().stream()
            .distinct() // no duplicate parents
            .map(parentQualifier -> IServiceRef.of(null, parentQualifier))
            .map(parentRef -> lookupService.lookup(parentRef).orElseThrow(() ->
                new IllegalArgumentException("Cannot resolve parent of service "
                    + baseServiceRef + " qualified as " + parentRef.getRef())))
            .collect(Collectors.toList());
        mergeList.add(parentList);


        // we are done calling this method recursively in this method. So remove the guard:
        cyclicGuard.remove(baseServiceRef.getRef().getQualifier());

        List<IServiceDesc> output = new ArrayList<>();
        output.add(baseService);
        while(! mergeList.stream().allMatch(List::isEmpty)) {
            boolean candidateFound = false;
            for (List<IServiceDesc> list : mergeList) {
                if(list.isEmpty()) {
                    continue;
                }
                IServiceDesc candidate = list.get(0);
                // find first index of candidate in each list.
                Optional<Integer> maxPlace = mergeList.stream()
                    .map(otherList -> otherList.indexOf(candidate))
                    .max(Integer::compareTo);
                if(maxPlace.orElse(0) > 0) {
                    // the current candidate appears in at least on tail.
                    // so it is not a good candidate.
                    continue;
                }
                output.add(candidate);
                // the candidate only occurs in the first place.
                candidateFound = true;
                // remove candidate from all merge lists:
                mergeList.forEach(otherLists ->
                    otherLists.removeIf(parent ->
                        parent.getQualifier().equals(candidate.getQualifier())));
                break;
            }
            if(!candidateFound) {
                throw new IllegalArgumentException("Cannot linearize the service: " + baseServiceRef + ". The remaining merge lists are: " + mergeList);
            }
        }
        return output;
    }



}
