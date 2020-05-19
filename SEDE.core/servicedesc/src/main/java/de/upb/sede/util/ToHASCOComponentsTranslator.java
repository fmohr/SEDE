package de.upb.sede.util;

import de.upb.sede.IQualifiable;
import de.upb.sede.SDLLookupService;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.IServiceRef;
import de.upb.sede.param.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Translates a set of services using an sdl service into component definition.
 */
@SuppressWarnings("unchecked")
public class ToHASCOComponentsTranslator {

    private final static Logger logger = LoggerFactory.getLogger(ToHASCOComponentsTranslator.class);

    private SDLLookupService sdlService;

    private List<IServiceRef> serviceRefs;

    private List<IServiceRef> servicesNotFound;

    private List<Map> components;

    private boolean skipOptParams = false;

    static final String
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
        KEY_MIN_INTERVAL = "minInterval",
        PARAM_TYPE_BOOLEAN = "boolean",
        PARAM_TYPE_CATEGORY= "cat",
        PARAM_TYPE_DOUBLE = "double",
        PARAM_TYPE_INT = "int"
    ;

    private ToHASCOComponentsTranslator(SDLLookupService sdlService,
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
                if(service.getServiceParameters() == null) {
                    logger.debug("Skipping Service `{}`, because it doesn't define service parameters.", service.getQualifier());
                    continue;
                }
                logger.debug("Creating the HASCO component for service: " + service.getQualifier());
                /*
                 * Retrieve the list component of this service collection.
                 * The repository field and components array has already been initialised.
                 */
                List comps = (List) getComponentRepo(serviceRef).get(KEY_COMPONENTS);
                Map component = translate(service);
                comps.add(component);
            }
        }
    }

    private Map translate(IServiceDesc service) {
        Map sComp = new HashMap();
        sComp.put(KEY_NAME, service.getQualifier());
        sComp.put(KEY_REQ_INTFACE, translateRequiredInterfaces(service));
        sComp.put(KEY_PVD_INTFACE, translateProvidedInterfaces(service));
        sComp.put(KEY_PARAMETER, translateParams(service));
        sComp.put(KEY_DEPENDENCIES, translateDependencies(service));
        return sComp;
    }

    private List<String> translateProvidedInterfaces(IServiceDesc service) {
        List<String> providedInterfaces = new ArrayList(service.getInterfaces());
        return providedInterfaces;
    }

    private List<String> translateRequiredInterfaces(IServiceDesc service) {
        List<String> requiredInterfaces = new ArrayList();
        if(service.getServiceParameters() == null) {
            return requiredInterfaces;
        }
        service.getServiceParameters()
            .getParameters()
            .stream()
            .filter(IInterfaceParameter.class::isInstance)
            .map(param ->  ((IInterfaceParameter) param).getInterfaceQualifier())
            .forEachOrdered(requiredInterfaces::add);
        return requiredInterfaces;
    }

    private List translateParams(IServiceDesc service) {
        List params = new ArrayList();
        if(service.getServiceParameters() == null) {
            return params;
        }
        for (IParameter serviceParam : service.getServiceParameters().getParameters()) {
            if(serviceParam.isOptional() && skipOptParams) {
                logger.debug("Skipping parameter `{}` because its optional.", serviceParam.getQualifier());
                continue;
            }
            Map param = translateParam(serviceParam);
//            if(param!=null) // TODO decide whether services are included even if they dont specify param options
            params.add(param);
        }
        return params;
    }

    private Map translateParam(IParameter serviceParam) {
        Map param = new HashMap();
        param.put(KEY_NAME, serviceParam.getQualifier());
        param.put(KEY_DEFAULT, serviceParam.getDefaultValue());
        if(serviceParam instanceof IBooleanParameter) {
            param.put(KEY_TYPE, PARAM_TYPE_BOOLEAN);
        } else if(serviceParam instanceof ICategoryParameter) {
            param.put(KEY_TYPE, PARAM_TYPE_CATEGORY);
            List<String> categories = new ArrayList<>(((ICategoryParameter) serviceParam).getCategories());
            param.put(KEY_VALUES, categories);
        }
        else if(serviceParam instanceof INumericParameter) {
            INumericParameter numericParameter = (INumericParameter) serviceParam;
            boolean isInteger = numericParameter.isInteger();

            /*
             * Transform to integer if needed.
             */
            Number min = numericParameter.getMin();
            Number max = numericParameter.getMax();
            Number defaultValue = numericParameter.getDefaultValue();
            if(isInteger) {
                if(min != null) {
                    min = min.intValue();
                }
                if(max != null) {
                    max = max.intValue();
                }
                if(defaultValue != null) {
                    defaultValue = defaultValue.intValue();
                }
            }

            param.put(KEY_TYPE,  isInteger? PARAM_TYPE_INT : PARAM_TYPE_DOUBLE);
            param.put(KEY_MIN, min);
            param.put(KEY_MAX, max);
            param.put(KEY_DEFAULT, defaultValue);
            param.put(KEY_REFINE_SPLITS, numericParameter.getSplitsRefined());
            param.put(KEY_MIN_INTERVAL, numericParameter.getMinInterval());
        }
        else if((serviceParam instanceof IInterfaceParameter)) {
            return null;
        } else {
            logger.warn("Parameter unrecognized. No translation for param type: {}", serviceParam.getParamType());
            return null;
        }
        return param;
    }

    private List translateDependencies(IServiceDesc service) {
        List dependencies = new ArrayList();
        if(service.getServiceParameters() == null) {
            return dependencies;
        }
        service.getServiceParameters()
            .getParameterDependencies()
            .stream()
            .map(paramDependency -> {
                Map dependency = new HashMap();
                dependency.put(KEY_DEPENDENCY_PRE, paramDependency.getPremise());
                dependency.put(KEY_DEPENDENCY_POST, paramDependency.getConclusion());
                return dependency;
            })
            .forEachOrdered(dependencies::add);
        return dependencies;
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

    public static List<Map> componentsOfServiceQualifiers(SDLLookupService sdlService,
                                                          List<String> serviceNames) {
        List<IServiceRef> refs = serviceNames.stream()
            .map(qualifier -> IServiceRef.of(null, qualifier))
            .collect(Collectors.toList());
        return componentsOfServiceRefs(sdlService, refs);
    }

    public static List<Map> componentsOfServiceRefs(SDLLookupService sdlService,
                                                    List<IServiceRef> serviceRefs) {
        return componentsOfServiceRefs(sdlService, serviceRefs, false);
    }

    public static List<Map> componentsOfServiceRefs(SDLLookupService sdlService,
                                                    List<IServiceRef> serviceRefs, boolean skipOptParams) {
        ToHASCOComponentsTranslator translator = new ToHASCOComponentsTranslator(sdlService, serviceRefs);
        translator.skipOptParams = skipOptParams;
        translator.translateAll();
        return Objects.requireNonNull(translator.getComponents());
    }

}
