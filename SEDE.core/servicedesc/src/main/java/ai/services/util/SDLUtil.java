package ai.services.util;

import ai.services.exec.IMethodDesc;
import ai.services.ISDLAssembly;
import ai.services.IServiceCollectionDesc;
import ai.services.SDLLookupService;
import ai.services.composition.graphs.nodes.IInstructionNode;
import ai.services.exec.IServiceDesc;
import ai.services.IServiceRef;
import ai.services.exec.auxiliary.DynamicAuxAware;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SDLUtil {

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

    public static Map gatherAux(DynamicAuxAware... auxAwares) {
        return gatherAux(Arrays.asList(auxAwares));
    }

    public static Map gatherAux(List<DynamicAuxAware> auxStack) {
        Map mergedAuxData = new HashMap();
        for (DynamicAuxAware dynamicAuxAware : auxStack) {
            DynRecord aux = dynamicAuxAware.getDynAux();
            if(aux!=null) {
                mergeInto(mergedAuxData, aux);
            }
        }
        return mergedAuxData;
    }

    private static boolean mergeUntypedInto(Object target, Object source) {
        if(source instanceof Map && target instanceof Map) {
            mergeInto((Map)target, (Map)source);
            return true;
        }
        if(source instanceof Collection && target instanceof Collection) {
            ((List<?>) target).addAll((Collection)source);
            return true;
        }
        return false;
    }

    private static void mergeInto(Map target, DynRecord source) {
        mergeInto(target, source.getData());
    }

    private static void mergeInto(Map target, Map source) {
        for (Object key : source.keySet()) {
            if(target.containsKey(key)) {
                boolean merged = mergeUntypedInto(target.get(key), source.get(key));
                if(!merged) {
                    target.put(key, source.get(key));
                }
            } else {
                target.put(key, source.get(key));
            }
        }
    }

    public static Optional<IMethodDesc> matchSignature(List<IMethodDesc> methodList, IInstructionNode inst) {
        boolean instructionIsAssignment = inst.getFieldName() != null;
        Stream<IMethodDesc> matchingSignatures = methodList.stream()
            // method input must match in size:
            .filter(signature -> signature.getInputs().size() == inst.getParameterFields().size())
            // method must have at least one output if instruction is an assingment to a field:
            .filter(signature -> !instructionIsAssignment || !signature.getOutputs().isEmpty());
        // Only a single method has to match:
        Optional<IMethodDesc> matchingSignature = Streams.pickOneOrNone(matchingSignatures);

        return matchingSignature;
    }

    public static List<IServiceDesc> getAllServices(ISDLAssembly assembly) {
        return assembly.getCollections().stream()
            .map(IServiceCollectionDesc::getServices)
            .reduce(new ArrayList<>(), (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            });
    }


}
