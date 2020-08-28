package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.*;
import de.upb.sede.composition.choerography.emulation.OrchestrationException;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.orchestration.emulated.*;
import de.upb.sede.composition.types.IDataValueType;
import de.upb.sede.composition.types.serialization.IMarshalling;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;
import de.upb.sede.util.SDLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static de.upb.sede.util.SDLUtil.gatherAux;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AuxDecorator extends AbstractExecutorDecorator<EmulatedOp> {

    private static final Logger logger = LoggerFactory.getLogger(AuxDecorator.class);

    private final SDLLookupService lookupService;

    private final Map<ConstructReference, Map> cachedAuxiliaries;

    public AuxDecorator(GraphCreatingExecutor delegate, SDLLookupService lookupService, Map<ConstructReference, Map> cachedAuxiliaries) {
        super(delegate, op -> true);
        this.lookupService = lookupService;
        this.cachedAuxiliaries = cachedAuxiliaries;
    }

    @Override
    public EmulatedOp handleOperation(EmulatedOp op) {
        assertNotHandled(op);
        if(op instanceof IServiceLoadStoreOp) {
            op = injectServiceSerializationAuxData((IServiceLoadStoreOp) op);
        } else if(op instanceof ICastOp){
            op = injectCastingAuxData((ICastOp) op);
        } else if(op instanceof IInstructionOp) {
            op = injectInstructionAuxData((IInstructionOp) op);
        }
        return op;
    }

    private EmulatedOp injectServiceSerializationAuxData(IServiceLoadStoreOp loadStoreOp) {
        IServiceInstanceStorageNode node = loadStoreOp.getServiceInstanceStorageNode();
        Map auxiliaries = getServiceAux(node.getServiceClasspath());
        return ServiceLoadStoreOp.builder()
            .from(loadStoreOp)
            .serviceInstanceStorageNode(ServiceInstanceStorageNode.builder()
                .from(node)
                .putAllRuntimeAuxiliaries(auxiliaries)
                .build())
            .build();
    }

    private EmulatedOp injectCastingAuxData(ICastOp castOp) {
        IMarshalNode firstCast = injectCastingAuxData(castOp.getFirstCast());
        IMarshalNode secondCast = injectCastingAuxData(castOp.getSecondCast());
        return CastOp.builder()
            .from(castOp)
            .firstCast(firstCast)
            .secondCast(secondCast)
            .build();
    }

    private IMarshalNode injectCastingAuxData(IMarshalNode castTypeNode) {
        if(castTypeNode == null) {
            return null;
        }
        IMarshalling marshalling = castTypeNode.getMarshalling();
        if(!(marshalling.getValueType() instanceof IDataValueType)) {
            logger.error("CastTypeNode with a marshalling that is not of type IDataValue: {}", marshalling);
            throw new OrchestrationException("Malformed Marshalling in CastTypeNode: " + castTypeNode);
        }
        IDataValueType dataValueType = (IDataValueType) marshalling.getValueType();

        Map typeAux = getTypeAux(dataValueType.getTypeQualifier());
        return MarshalNode.builder()
            .from(castTypeNode)
            .putAllRuntimeAuxiliaries(typeAux)
            .build();
    }

    private IInstructionOp injectInstructionAuxData(IInstructionOp instOp) {
        Map methodAux = getMethodAux(instOp.getMR().getMethodRef(), instOp.getInstructionNode());
        return InstructionOp.builder()
            .from(instOp)
            .instructionNode(InstructionNode.builder()
                .from(instOp.getInstructionNode())
                .putAllRuntimeAuxiliaries(methodAux)
                .build())
            .build();
    }

    private Map getServiceAux(String serviceClasspath) {
        IServiceRef ref = IServiceRef.of(null, serviceClasspath);
        return cachedAuxiliaries.computeIfAbsent(ref, this::gatherServiceAux);
    }

    private Map gatherServiceAux(ConstructReference ref) {
        IServiceRef serviceRef = (IServiceRef) ref;
        Optional<IServiceDesc> lookup = lookupService.lookup(serviceRef);
        if(!lookup.isPresent()) {
            logger.warn("No service desc found for service {}.", serviceRef);
            return Collections.EMPTY_MAP;
        }
        return gatherAux(lookup.get());
    }

    private Map getTypeAux(String typeQualifier) {
        return cachedAuxiliaries.computeIfAbsent(
            IDataTypeRef.of(typeQualifier),
            this::gatherTypeAux);
    }

    private Map gatherTypeAux(ConstructReference cref) {
        IDataTypeRef ref = (IDataTypeRef) cref;
        Optional<IDataTypeDesc> lookup = lookupService.lookup(ref);
        if(!lookup.isPresent()) {
            logger.warn("No type desc found for type {}.", ref);
            return Collections.EMPTY_MAP;
        }
        return gatherAux(lookup.get());
    }

    private Map getMethodAux(IMethodRef methodRef, final IInstructionNode node) {
        return cachedAuxiliaries.computeIfAbsent(methodRef, ref -> gatherMethodAux(methodRef, node));
    }

    private Map gatherMethodAux(ConstructReference ref, IInstructionNode node) {
        IMethodRef methodRef = (IMethodRef) ref;
        List<IMethodDesc> methods = lookupService.lookup(methodRef);
        Optional<IMethodDesc> optMethod = SDLUtil.matchSignature(methods, node);
        if(!optMethod.isPresent()) {
            logger.warn("No method desc found for method {} used by instruction:\n{}", methodRef, node.getFMInstruction());
            return Collections.EMPTY_MAP;
        }

        Optional<IServiceDesc> serviceLookup = lookupService.lookup(methodRef.getServiceRef());
        if(!serviceLookup.isPresent()) {
            throw new RuntimeException("BUG: Couldn't find service for the already lookedup method ref: " + methodRef);
        }

        return gatherAux(serviceLookup.get(), optMethod.get());
    }


}
