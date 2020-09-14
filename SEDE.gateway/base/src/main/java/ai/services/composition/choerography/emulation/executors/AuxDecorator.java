package de.upb.sede.composition.choerography.emulation.executors;

import de.upb.sede.*;
import de.upb.sede.composition.choerography.emulation.EmulationException;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.orchestration.emulated.*;
import de.upb.sede.composition.types.IDataValueType;
import de.upb.sede.composition.types.IServiceInstanceType;
import de.upb.sede.composition.types.serialization.IMarshalling;
import de.upb.sede.composition.typing.TypeUtil;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import de.upb.sede.types.DataTypeRef;
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
    public EmulatedOp handleOperation(EmulatedOp op) throws EmulationException {
        assertNotHandled(op);
        if(op instanceof IServiceLoadStoreOp) {
            op = injectServiceSerializationAuxData((IServiceLoadStoreOp) op);
        } else if(op instanceof ICastOp){
            op = injectMarshallingAuxData((ICastOp) op);
        } else if(op instanceof IMarshalOp) {
            op = injectMarshallingAuxData((IMarshalOp)op);
        } else if(op instanceof ITransmissionOp) {
            op = injectMarshallingAuxData((ITransmissionOp)op);
        }else if(op instanceof IInstructionOp) {
            op = injectInstructionAuxData((IInstructionOp) op);
        } else {
            logger.trace("No aux data added to operation {}.", op);
        }
        return op;
    }


    private EmulatedOp injectServiceSerializationAuxData(IServiceLoadStoreOp loadStoreOp) {
        IServiceInstanceStorageNode node = loadStoreOp.getServiceInstanceStorageNode();
        Map auxiliaries = getServiceTypeAux(node.getServiceClasspath());
        return ServiceLoadStoreOp.builder()
            .from(loadStoreOp)
            .serviceInstanceStorageNode(ServiceInstanceStorageNode.builder()
                .from(node)
                .putAllRuntimeAuxiliaries(auxiliaries)
                .build())
            .build();
    }

    private EmulatedOp injectMarshallingAuxData(ICastOp castOp) {
        IMarshalNode firstMarshal = injectMarshallingAuxData(castOp.getFirstCast());
        IMarshalNode secondMarshal = injectMarshallingAuxData(castOp.getSecondCast());
        return CastOp.builder()
            .from(castOp)
            .firstCast(firstMarshal)
            .secondCast(secondMarshal)
            .build();
    }

    private EmulatedOp injectMarshallingAuxData(IMarshalOp op) {
        IMarshalNode marshal = injectMarshallingAuxData(op.getMarshalNode());
        return MarshalOp.builder().from(op).marshalNode(marshal).build();
    }

    private EmulatedOp injectMarshallingAuxData(ITransmissionOp op) {
        Map typeAux = getAuxFromMarshalling(op.getTransmitDataNode().getMarshalling());
        return TransmissionOp.builder()
            .from(op)
            .transmitDataNode(TransmitDataNode.builder()
                .from(op.getTransmitDataNode())
                .putAllRuntimeAuxiliaries(typeAux)
                .build())
            .build();
    }

    private IMarshalNode injectMarshallingAuxData(IMarshalNode marshalNode) {
        if(marshalNode == null) {
            return null;
        }
        IMarshalling marshalling = marshalNode.getMarshalling();

        Map typeAux = getAuxFromMarshalling(marshalling);
        return MarshalNode.builder()
            .from(marshalNode)
            .putAllRuntimeAuxiliaries(typeAux)
            .build();
    }

    @SuppressWarnings({"rawtypes"})
    private Map getAuxFromMarshalling(IMarshalling marshalling) {
        if(marshalling == null) {
            return Collections.emptyMap();
        } else if(marshalling.getValueType() instanceof IDataValueType){
            IDataValueType dataValueType = (IDataValueType) marshalling.getValueType();
            return getDataTypeAux(dataValueType.getTypeQualifier());
        } else if(TypeUtil.isService(marshalling.getValueType())) {
            IServiceInstanceType serviceType = TypeUtil.getServiceType(marshalling.getValueType());
            return getServiceTypeAux(serviceType.getTypeQualifier());
        } else {
            logger.warn("Unrecognized type marshalling: {}", marshalling);
            return Collections.emptyMap();
        }
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

    private Map getServiceTypeAux(String serviceClasspath) {
        IServiceRef ref = IServiceRef.of(null, serviceClasspath);
        return getServiceTypeAux(ref);
    }

    private Map getServiceTypeAux(IServiceRef ref) {
        return cachedAuxiliaries.computeIfAbsent(ref, this::gatherServiceTypeAux);
    }

    private Map gatherServiceTypeAux(ConstructReference ref) {
        IServiceRef serviceRef = (IServiceRef) ref;
        List<DynamicAuxAware> auxAwares = new ArrayList<>();

        Optional<IServiceDesc> lookup = lookupService.lookup(serviceRef);
        if(!lookup.isPresent()) {
            logger.warn("No service desc found for service {}.", serviceRef);
        } else {
            auxAwares.add(lookup.get());
        }


        IDataTypeRef typeRef = DataTypeRef.builder()
            .serviceCollectionRef(serviceRef.getServiceCollectionRef())
            .ref(serviceRef.getRef())
            .build();
        Optional<IDataTypeDesc> dtLookup = lookupService.lookup(typeRef);
        if(!dtLookup.isPresent()) {
            logger.debug("No data type defined for service {}.", serviceRef);
        } else {
            auxAwares.add(dtLookup.get());
        }

        if(auxAwares.isEmpty()) {
            return Collections.emptyMap();
        }

        return gatherAux(auxAwares.toArray(new DynamicAuxAware[0]));
    }

    private Map getDataTypeAux(String typeQualifier) {
        return cachedAuxiliaries.computeIfAbsent(
            IDataTypeRef.of(typeQualifier),
            this::gatherTypeAux);
    }

    private Map gatherTypeAux(ConstructReference cref) {
        IDataTypeRef ref = (IDataTypeRef) cref;
        Optional<IDataTypeDesc> lookup = lookupService.lookup(ref);
        if(!lookup.isPresent()) {
            logger.warn("No type desc found for type {}.", ref);
            return Collections.emptyMap();
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
            return Collections.emptyMap();
        }

        Optional<IServiceDesc> serviceLookup = lookupService.lookup(methodRef.getServiceRef());
        if(!serviceLookup.isPresent()) {
            throw new RuntimeException("BUG: Couldn't find service for the already lookedup method ref: " + methodRef);
        }

        return gatherAux(serviceLookup.get(), optMethod.get());
    }


}
