package de.upb.sede.gateway;

import de.upb.sede.ISDLAssembly;
import de.upb.sede.SDLBaseLookupService;
import de.upb.sede.SDLCacheLookupService;
import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.CompositionCompileService;
import de.upb.sede.composition.ICCRequest;
import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.interfaces.ICCService;
import de.upb.sede.interfaces.IGateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;

public class SimpleGatewayImpl implements IGateway {

    private final SDLLookupService lookupService;

    private final ICCService iccService;

    public SimpleGatewayImpl(SDLLookupService lookupService) {
        this.lookupService = lookupService;
        iccService = new CompositionCompileService(lookupService);
    }

    public SimpleGatewayImpl(ISDLAssembly sdlAssembly) {
        this(new SDLCacheLookupService(new SDLBaseLookupService(sdlAssembly)));
    }

    @Override
    public GatewayResolution resolve(ResolveRequest resolveRequest) {
        return null;
    }

    @Override
    public boolean register(ExecutorRegistration registry) {
        return false;
    }

    @Override
    public ICompositionCompilation compileComposition(ICCRequest ccRequest) {
        return iccService.compileComposition(ccRequest);
    }
}
