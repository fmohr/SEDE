package de.upb.sede.util;

import de.upb.sede.IQualifiable;
import de.upb.sede.ISDLLookupService;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.IServiceRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Translates a set of services using an sdl service into component definition.
 */
@SuppressWarnings("unchecked")
public class HASCOComponentsTranslator {

    private final static Logger logger = LoggerFactory.getLogger(HASCOComponentsTranslator.class);

    private ISDLLookupService sdlService;

    private List<IServiceRef> serviceRefs;

    private List<IServiceRef> servicesNotFound;

    private List<Map> components;

    public static final String
        KEY_REPO = "repository",
        KEY_COMPONENTS = "components",
        KEY_NAME = "name",
        KEY_REQ_INTFACE = "requiredInterface",
        KEY_PVD_INTFACE = "providedInterface",
        KEY_PARAMETER = "parameter",
        KEY_DEPENDENCIES = "dependencies",
        KEY_DEPENDENCY_PRE = "pre",
        KEY_DEPENDENCY_POST = "post",
        KEY_VALUES = "values",
        KEY_DEFAULT = "default",
        KEY_TYPE = "type",
        KEY_MIN = "min",
        KEY_MAX = "max",
        KEY_REFINE_SPLITS = "refineSplits",
        KEY_MIN_INTERVAL = "minInterval"
            ;

    private HASCOComponentsTranslator(ISDLLookupService sdlService,
                                     List<IServiceRef> serviceRefs) {
        this.sdlService = sdlService;
        this.serviceRefs = serviceRefs;
        this.servicesNotFound = new ArrayList<>();
        this.components = new ArrayList<>();
    }

    private void translateAll() {
        for (IServiceRef serviceRef : serviceRefs) {
            Optional<IServiceDesc> optionalServiceDesc = sdlService.lookup(serviceRef);
            if(!optionalServiceDesc.isPresent()) {
                logger.error("Couldn't find in sdl base: " + serviceRef.toString() );
                servicesNotFound.add(serviceRef);
            } else {
                IServiceDesc service = optionalServiceDesc.get();
                translate(serviceRef, service);
            }
        }
    }

    private void translate(IServiceRef serviceRef, IServiceDesc service) {
        logger.debug("Creating the HASCO component for service: " + service.getQualifier());
        List comps = (List) getComponentRepo(serviceRef).get(KEY_COMPONENTS);
        Map sComp = new HashMap();
        sComp.put(KEY_NAME, service.getQualifier());

    }

    private Map getComponentRepo(IServiceRef service) {
        String collectionQualifier =
            sdlService.lookupCollection(service)
                .map(IQualifiable::getQualifier)
                .orElse(null);

        Optional<Map> optComponentRepo = components.stream()
            .filter(comp -> comp.get(KEY_REPO).equals(collectionQualifier))
            .findFirst();

        if(!optComponentRepo.isPresent()) {
            Map componentRepo = new HashMap();
            componentRepo.put(KEY_REPO, collectionQualifier);
            componentRepo.put(KEY_COMPONENTS, new ArrayList<>());
            components.add(componentRepo);
            return componentRepo;
        } else {
            return optComponentRepo.get();
        }
    }

    private List<Map> getComponents() {
        return components;
    }

    public static List<Map> componentsOfServiceQualifiers(ISDLLookupService sdlService,
                                                          List<String> serviceNames) {
        List<IServiceRef> refs = serviceNames.stream()
            .map(qualifier -> IServiceRef.of(null, qualifier))
            .collect(Collectors.toList());
        return componentsOfServiceRefs(sdlService, refs);
    }

    public static List<Map> componentsOfServiceRefs(ISDLLookupService sdlService,
                                                    List<IServiceRef> serviceRefs) {
        HASCOComponentsTranslator translator = new HASCOComponentsTranslator(sdlService, serviceRefs);
        translator.translateAll();
        return Objects.requireNonNull(translator.getComponents());
    }

}
