package de.upb.sede.composition;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.faa.FieldAccessAnalysisException;
import de.upb.sede.composition.typing.TypeCheckException;
import de.upb.sede.interfaces.ICCService;
import de.upb.sede.util.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CompositionCompileService implements ICCService {

    private final Cache<SDLLookupService> lookupService;

    private final static Logger logger = LoggerFactory.getLogger(CompositionCompileService.class);

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
