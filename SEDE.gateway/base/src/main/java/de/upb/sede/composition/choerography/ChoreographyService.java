package de.upb.sede.composition.choerography;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.CCRequest;
import de.upb.sede.composition.ICCRequest;
import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.gateway.ExecutorSupplyCoordinator;
import de.upb.sede.interfaces.ICCService;
import de.upb.sede.interfaces.IChoreographyService;
import de.upb.sede.requests.resolve.beta.IChoreography;
import de.upb.sede.requests.resolve.beta.IResolveRequest;

public class ChoreographyService implements IChoreographyService {

    private final ICCService iccService;

    private final SDLLookupService lookupService;

    private final ExecutorSupplyCoordinator executorSupplyCoordinator;

    public ChoreographyService(ICCService iccService, SDLLookupService lookupService, ExecutorSupplyCoordinator executorSupplyCoordinator) {
        this.iccService = iccService;
        this.lookupService = lookupService;
        this.executorSupplyCoordinator = executorSupplyCoordinator;
    }

    private ICompositionCompilation compile(IResolveRequest resolveRequest) {
        ICCRequest iccRequest = CCRequest.builder()
            .composition(resolveRequest.getComposition())
            .initialContext(resolveRequest.getInitialContext())
            .build();
        return iccService.compileComposition(iccRequest);
    }

    @Override
    public IChoreography resolve(IResolveRequest resolveRequest) {
        ICompositionCompilation compCompilation = compile(resolveRequest);


        return null;
    }
}
