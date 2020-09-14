package de.upb.sede.gateway;

import de.upb.sede.ISDLAssembly;
import de.upb.sede.SDLBaseLookupService;
import de.upb.sede.SDLCacheLookupService;
import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.CompositionCompileService;
import de.upb.sede.composition.ICCRequest;
import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.composition.choerography.ChoreographyService;
import de.upb.sede.interfaces.ICCService;
import de.upb.sede.interfaces.IChoreographyService;
import de.upb.sede.interfaces.IGateway;
import de.upb.sede.requests.resolve.beta.Choreography;
import de.upb.sede.requests.resolve.beta.IChoreography;
import de.upb.sede.requests.resolve.beta.IResolveRequest;
import de.upb.sede.util.Cache;
import de.upb.sede.util.GraphToDot;
import de.upb.sede.util.StaticCache;
import de.upb.sede.util.TTLCache;
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

    public StdGatewayImpl(SDLLookupService baseLookupService,
                             ExecutorArbiter executorSupplier) {
        this(new TTLCache<>(10, TimeUnit.SECONDS, () ->
                new SDLCacheLookupService(baseLookupService)),
            executorSupplier);
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
