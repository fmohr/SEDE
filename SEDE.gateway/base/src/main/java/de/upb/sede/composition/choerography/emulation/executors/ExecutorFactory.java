package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.ConstructReference;
import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.choerography.emulation.Emulation;
import de.upb.sede.exec.IExecutorHandle;

import java.util.HashMap;
import java.util.Map;

public class ExecutorFactory {

    private final SDLLookupService sdlLookupService;

    /**
     *  A shared auxiliary cache used by all aux decorators to fasted the gathering of auxiliaries.
     *  Use a thread-safe map if the orchestration is later changed to be parallelized;
     */
    @SuppressWarnings({"rawtypes"})
    private final Map<ConstructReference, Map> cachedAuxiliaries = new HashMap<>();

    public ExecutorFactory(SDLLookupService sdlLookupService) {
        this.sdlLookupService = sdlLookupService;
    }

    public Emulation.Executor createExecutor(IExecutorHandle handle) {
        GraphCreatingExecutor executor = new BaseExecutor(handle);
        executor = DefaultExecutorDecorations.addDefaultDecorators(executor);
        executor = new AuxDecorator(executor, sdlLookupService, cachedAuxiliaries);
        return executor;
    }

}
