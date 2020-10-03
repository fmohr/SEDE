package ai.services.gateway;

import ai.services.composition.CompositionCompileService;
import ai.services.composition.choerography.ChoreographyService;
import ai.services.requests.resolve.beta.Choreography;
import ai.services.ISDLAssembly;
import ai.services.SDLBaseLookupService;
import ai.services.SDLCacheLookupService;
import ai.services.SDLLookupService;
import ai.services.composition.ICCRequest;
import ai.services.composition.ICompositionCompilation;
import ai.services.interfaces.ICCService;
import ai.services.interfaces.IChoreographyService;
import ai.services.interfaces.IGateway;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import ai.services.util.Cache;
import ai.services.util.GraphToDot;
import ai.services.util.StaticCache;
import ai.services.util.TTLCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class StdGatewayImpl implements IGateway {

    private static final Logger logger = LoggerFactory.getLogger(StdGatewayImpl.class);

    private final Cache<SDLLookupService> lookupServiceCache;

    private final ExecutorArbiter executorSupplier;

    private final ICCService iccService;

    private final IChoreographyService choreographyService;

    public StdGatewayImpl(Cache<SDLLookupService> lookupServiceCache, ExecutorArbiter executorSupplier) {
        this.lookupServiceCache = lookupServiceCache;
        this.executorSupplier = executorSupplier;
        iccService = new CompositionCompileService(lookupServiceCache);
        choreographyService = new ChoreographyService(iccService, lookupServiceCache, this.executorSupplier);
    }

    public StdGatewayImpl(ISDLAssembly sdlAssembly,
                             ExecutorArbiter executorSupplier) {
        this(
            new StaticCache<>(
                new SDLCacheLookupService(new SDLBaseLookupService(sdlAssembly))),
            executorSupplier);
    }

    @Override
    public IChoreography resolve(IResolveRequest resolveRequest) {
        IChoreography choreography = choreographyService.resolve(resolveRequest);
        /* If the client needs the visualisation of the graph appen it to the resolution */
		if(resolveRequest.getResolvePolicy().isDotGraphRequested()) {
			try{
			    logger.info("Dot graph was requested. Creating the dot graph from the composition graphs.");
				String svg = GraphToDot.choreographyToSag(choreography);
                choreography = Choreography.builder()
                    .from(choreography)
                    .dotSVG(svg)
                    .build();
			} catch(Exception ex) {
				logger.error("Error trying to calculate the dot from graph: ", ex);
			}
		}
        return choreography;
    }


    @Override
    public ICompositionCompilation compileComposition(ICCRequest ccRequest) {
        return iccService.compileComposition(ccRequest);
    }

}
