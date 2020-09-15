package ai.services.composition;

import ai.services.composition.faa.FieldAccessAnalysisException;
import ai.services.composition.typing.TypeCheckException;
import ai.services.SDLLookupService;
import ai.services.interfaces.ICCService;
import ai.services.util.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CompositionCompileService implements ICCService {

    private final Cache<SDLLookupService> lookupService;

    private static final Logger logger = LoggerFactory.getLogger(CompositionCompileService.class);

    public CompositionCompileService(Cache<SDLLookupService> lookupService) {
        this.lookupService = lookupService;
    }

    @Override
    public ICompositionCompilation compileComposition(ICCRequest ccRequest) throws TypeCheckException, FieldAccessAnalysisException {
        SingleBlockCCImpl ccImpl = new SingleBlockCCImpl(lookupService.get(),
            ccRequest.getInitialContext());
        ICompositionCompilation compositionCompilation;
        try {
            compositionCompilation = ccImpl.compileCode(ccRequest.getComposition());
        } catch (TypeCheckException ex) {
//            logger.error("Error during Type Checking:\n{}", ccRequest.getComposition(), ex);
            throw ex;
        } catch(FieldAccessAnalysisException ex) {
//            logger.error("Error during Field access analysis:\n{}", ccRequest.getComposition(), ex);
            throw ex;
        }
        return compositionCompilation;
    }

}
