package ai.services.composition.choerography;

import ai.services.composition.*;
import ai.services.composition.graphs.nodes.*;
import ai.services.composition.orchestration.scheduled.*;
import ai.services.composition.types.*;
import ai.services.composition.types.serialization.Marshalling;
import ai.services.SDLLookupService;
import ai.services.composition.choerography.emulation.OpsSchedule;
import ai.services.composition.faa.FieldAccessUtil;
import ai.services.composition.types.serialization.IMarshalling;
import ai.services.composition.typing.TypeCheckException;
import ai.services.core.Primitives;
import ai.services.exec.IExecutorHandle;
import ai.services.requests.resolve.beta.IResolveRequest;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;
import ai.services.util.ResolvePolicyUtil;
import ai.services.composition.typing.TypeUtil;

import java.util.*;
import java.util.stream.Collectors;

public class InstInputCollector
    extends BlockWiseCompileStep<InstInputCollector.DTCInput, InstInputCollector.DTCOutput> {


    public InstInputCollector() {
    }


    @Override
    protected DTCOutput initializeOutput() {
        return new DTCOutput();
    }

    @Override
    protected void stepBlock() {
        initializeInitialFields();
        for (IIndexedInstruction inst : getInput().getIndexer()) {
            prepareInputFields(inst);
            setOutputFieldLocation(inst);
        }
        transmitOutputs();
        setReturnFields();
    }

    private void setReturnFields() {
        getOutput().returnFields = getInput().operationSchedule.getFinalOps().stream()
            .filter(op -> op instanceof ITransmission)
            .map(trans -> ((ITransmission)trans).getAcceptDataNode().getFieldName())
            .distinct()
            .collect(Collectors.toList());
    }

    private void setOutputFieldLocation(IIndexedInstruction inst) {
        if(inst.getInstruction().isAssignment()) {
            Long instIndex = inst.getIndex();
            IExecutorHandle executorH = getInput().instExecutorMap.get(instIndex);
            String outputField = inst.getInstruction().getFieldName();
            getOutput().setFieldLocation(outputField, executorH);
        }
    }

    private void initializeInitialFields() {
        getInput().getFieldAccessUtil()
            .initialFields()
            .forEach(field -> {
                getOutput().fieldLocation.put(field.getFieldname(), getInput().getClientExecutor());
            });
    }

    private void prepareInputFields(IIndexedInstruction inst) {
        Long instIndex = inst.getIndex();
        IExecutorHandle executorH = getInput().instExecutorMap.get(instIndex);

        // make sure the context service instance is on the executor
        IMethodResolution mr = getInput().getMR().get(inst.getIndex());
        if(inst.getInstruction().getContextIsFieldFlag()) {
            String contextFieldname = inst.getInstruction().getContext();
            Optional<IExecutorHandle> optFL = getOutput().getFieldLocation(contextFieldname);
            if(!optFL.isPresent()) {
                throw ChoreographyException.missingFieldException(contextFieldname,
                    inst.getInstruction().getFMInstruction(), executorH.getQualifier());
            }
            IExecutorHandle locationOfContext = optFL.get();
            String srcExecutorId = locationOfContext.getQualifier();
            if(!srcExecutorId.equals(executorH.getQualifier())) {
                // if the context is on another executor:
                String serviceQualifier = mr.getMethodRef().getServiceRef().getRef().getQualifier();
                IFieldCast serviceInstanceHandleCast = createServiceInstanceHandleTransmissionSerialisation(locationOfContext, executorH, contextFieldname,
                    RefType.builder().typeOfRef(ServiceInstanceType.builder().typeQualifier(serviceQualifier).build()).build());
                ITransmission serviceTransmission = createTransmission(locationOfContext, executorH, contextFieldname, serviceInstanceHandleCast);
                getOutput().serializeAndTransmit(instIndex, serviceInstanceHandleCast, serviceTransmission);
                getOutput().setFieldLocation(contextFieldname, executorH);
            }
        }
        List<String> params = inst.getInstruction().getParameterFields();
        for (int paramIndex = 0; paramIndex < params.size(); paramIndex++) {
            ITypeCoercion typeCoercion = mr.getParamTypeCoercions().get(paramIndex);
            if(typeCoercion.isNullPlug()) {
                // no need to type cast a null plug.
            } else if(typeCoercion.hasConstant()) {
                IParseConstantNode parseC = ParseConstantNode.builder()
                    .constantValue(Objects.requireNonNull(typeCoercion.getConstant()))
                    .constantType(Primitives.valueOf(typeCoercion.getSourceType()))
                    .hostExecutor(executorH.getQualifier())
                    .index(getInput().getIndexFactory().create())
                    .build();
                getOutput().parseConstant(inst.getIndex(), parseC);
            } else {
                // parameter is a field:
                String fieldname = params.get(paramIndex);
                Optional<IExecutorHandle> fieldLocation = getOutput().getFieldLocation(fieldname);
                if (!fieldLocation.isPresent()) {
                    throw ChoreographyException.missingFieldException(fieldname,
                        inst.getInstruction().getFMInstruction(), executorH.getQualifier());
                }
                IExecutorHandle sourceLocation = fieldLocation.get();
                if(sourceLocation.getQualifier().equals(executorH.getQualifier())) {
                    // field is already at host executor.
                    if(typeCoercion.hasTypeConversion()) {
                        // We need to cast field:
                        IFieldCast cast = FieldCast.builder()
                            .firstCast(MarshalNode.builder()
                                .fieldName(fieldname)
                                .index(getInput().getIndexFactory().create())
                                .hostExecutor(executorH.getQualifier())
                                .marshalling(Marshalling.builder()
                                    .direction(IMarshalling.Direction.MARSHAL)
                                    .valueType(DataValueType.builder()
                                        .typeQualifier(typeCoercion.getSourceType())
                                        .build())
                                    .semanticName(Objects.requireNonNull(typeCoercion.getSemanticType()))
                                    .build())
                                .build())
                            .secondCast(MarshalNode.builder()
                                .fieldName(fieldname)
                                .index(getInput().getIndexFactory().create())
                                .hostExecutor(executorH.getQualifier())
                                .marshalling(Marshalling.builder()
                                    .direction(IMarshalling.Direction.UNMARSHAL)
                                    .valueType(DataValueType.builder()
                                        .typeQualifier(typeCoercion.getResultType())
                                        .build())
                                    .semanticName(Objects.requireNonNull(typeCoercion.getSemanticType()))
                                    .build())
                                .build())
                            .build();
                        getOutput().castField(instIndex, cast);
                    } else {
                        // no need to cast or do anything.
                    }
                } else {
                    // field is on another executor
                    // create transmission with the correct inplace casts:
                    IFieldCast fieldCast;
                    if(typeCoercion.isPrimitive()) {
                        fieldCast = createPrimitiveCast(sourceLocation, executorH,
                            fieldname, PrimitiveValueType.builder()
                            .primitiveType(Primitives.insensitiveValueOf(typeCoercion.getSemanticType()).get())
                            .build());
                    } else {
                        fieldCast = createDataValueTransmissionSerialisation(sourceLocation, executorH,
                            fieldname, typeCoercion);
                    }
                    ITransmission dataTransmission = createTransmission(sourceLocation, executorH, fieldname, fieldCast);
                    getOutput().serializeAndTransmit(instIndex, fieldCast, dataTransmission);
                    getOutput().setFieldLocation(fieldname, executorH);
                }
            }
        }
    }


    private void transmitOutputs() {
        getInput().getFieldAccessUtil()
            .allFields()
            .filter(field -> field.getFieldAccesses().size() > 0)
            .filter(field -> getInput().getFieldAccessUtil().hasWriteAccess(field.getFieldname()))
            .filter(this::isToBeReturned)
            .forEach(this::transmitOutputToClient);
    }

    private void transmitOutputToClient(IFieldAnalysis field) {
        String fieldname = field.getFieldname();
        IExecutorHandle clientH = getInput().getClientExecutor();
        Optional<IExecutorHandle> fieldLocation = getOutput().getFieldLocation(fieldname);
        if(!fieldLocation.isPresent()) {
            throw ChoreographyException.missingFieldException(fieldname,
                "collectOutputs();", getInput().clientExecutor.getQualifier());
        }
        IExecutorHandle sourceH = fieldLocation.get();
        if(!sourceH.getQualifier().equals(clientH.getQualifier())) {
            // get the semantic type:

            TypeClass outputType = getInput().fieldAccessUtil.resultingFieldType(field);
            if(TypeUtil.isService(outputType)) {
                IFieldCast serviceHandleCast = createServiceInstanceHandleTransmissionSerialisation(sourceH, clientH, fieldname, outputType);
                ITransmission serviceTransmission = createTransmission(sourceH, clientH, fieldname, serviceHandleCast);
                getOutput().serializeAndTransmitOutput(serviceHandleCast, serviceTransmission);
            } else if(TypeUtil.isDataValueType(outputType) && !TypeUtil.isRefType(outputType)) {
                String outputTypeQualifier = outputType.getTypeQualifier();
                Optional<IDataTypeDesc> dataTypeDesc = getInput().getLookupService()
                    .lookup(IDataTypeRef.of(outputTypeQualifier));
                if(!dataTypeDesc.isPresent()) {
                    throw TypeCheckException.unknownType(fieldname, outputType);
                }
                IDataTypeDesc outputTypeDesc = dataTypeDesc.get();
                String semanticType = outputTypeDesc.getSemanticType();
                ITypeCoercion typeCoercion = TypeCoercion.builder()
                    .sourceType(outputTypeQualifier)
                    .semanticType(semanticType)
                    .resultType(semanticType)
                    .build();
                IFieldCast transmissionSerialisation = createDataValueTransmissionSerialisation(sourceH, clientH, fieldname, typeCoercion);
                ITransmission dataTransmission = createTransmission(sourceH, clientH, fieldname, transmissionSerialisation);
                getOutput().serializeAndTransmitOutput(transmissionSerialisation, dataTransmission);
            } else if(TypeUtil.isPrimitiveValueType(outputType) && !TypeUtil.isRefType(outputType)) {
                IFieldCast primitiveFieldCast = createPrimitiveCast(sourceH, clientH, fieldname, (IPrimitiveValueType) outputType);
                ITransmission transmittion = createTransmission(sourceH, clientH, fieldname, primitiveFieldCast);
                getOutput().serializeAndTransmitOutput(primitiveFieldCast, transmittion);
            }
            else {
                throw new IllegalStateException("Field \n" + field + "\n is being returned to client. But its type \n" + outputType + "\n is not data nor service.");
            }
        }
    }


    private ITransmission createTransmission(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                             IFieldCast serialisation) {
        ITransmitDataNode transmitDataNode = TransmitDataNode.builder()
            .fieldName(fieldname)
            .index(getInput().getIndexFactory().create())
            .hostExecutor(src.getQualifier())
            .contactInfo(trg.getContactInfo())
            .marshalling(serialisation.getFirstCast().getMarshalling())
            .build();
        IMarshalling receiverMarshal = null;
        if(serialisation.getSecondCast() != null) {
            receiverMarshal = serialisation.getSecondCast().getMarshalling();
        }
        IAcceptDataNode acceptDataNode = AcceptDataNode.builder()
            .fieldName(fieldname)
            .index(getInput().getIndexFactory().create())
            .hostExecutor(trg.getQualifier())
            .marshalling(receiverMarshal)
            .build();
        ITransmission transmission = Transmission.builder()
            .source(src)
            .target(trg)
            .transmission(transmitDataNode)
            .acceptDataNode(acceptDataNode)
            .build();
        return transmission;
    }

    private IFieldCast createServiceInstanceHandleTransmissionSerialisation(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                                                            TypeClass serviceType) {
        if(!TypeUtil.isRefType(serviceType) && !TypeUtil.isService(serviceType)) {
            throw new IllegalArgumentException("Expected service type. Got: " + serviceType);
        }
        if(!TypeUtil.isRefType(serviceType)) {
            // TODO manually change the type of the service handle to a ref type.
            // What we need to do is another type checking after the executors have been selected and transmission and service load store nodes has been added.
            // Because after a load or a store the type of the service field transition from (and to) IRefType
            serviceType = RefType.builder().typeOfRef((ValueTypeClass) serviceType).build();
        }
        return createTransmissionSerialisation(src, trg, fieldname, serviceType, serviceType, IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE);
    }

    private IFieldCast createPrimitiveCast(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                           IPrimitiveValueType primitiveValueType) {
        return createTransmissionSerialisation(src, trg, fieldname, primitiveValueType, primitiveValueType, primitiveValueType.getTypeQualifier());
    }

    private IFieldCast createDataValueTransmissionSerialisation(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                                                ITypeCoercion typeCoercion) {
        if(typeCoercion.getSemanticType() == null) {
            throw new IllegalArgumentException("Type-coercion has no semantic type: " + typeCoercion);
        }
        TypeClass srcType = DataValueType.builder().typeQualifier(typeCoercion.getSourceType()).build();
        TypeClass trgType = null;
        if(!typeCoercion.resultsInSemanticType()) {
            trgType = DataValueType.builder().typeQualifier(typeCoercion.getResultType()).build();
        }
        return createTransmissionSerialisation(src, trg, fieldname, srcType, trgType, typeCoercion.getSemanticType());
    }

    private IFieldCast createTransmissionSerialisation(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                                       TypeClass srcType, TypeClass trgType, String semanticTypeName) {
        IMarshalNode firstMarshal = null;
        firstMarshal = MarshalNode.builder()
            .index(getInput().getIndexFactory().create())
            .hostExecutor(src.getQualifier())
            .marshalling(Marshalling.builder()
                .valueType(srcType)
                .direction(IMarshalling.Direction.MARSHAL)
                .semanticName(semanticTypeName)
                .build())
            .fieldName(fieldname)
            .build();
        IMarshalling receiverMarshal;
        if(trgType == null) {
            // if semantic type equals result type do convert at accept stage:
            receiverMarshal = null;
        } else {
            receiverMarshal = Marshalling.builder()
                .valueType(trgType)
                .direction(IMarshalling.Direction.UNMARSHAL)
                .semanticName(semanticTypeName)
                .build();
        }
        IMarshalNode secondMarshal = null;
        if(receiverMarshal != null) {
            secondMarshal = MarshalNode.builder()
                .index(getInput().getIndexFactory().create())
                .fieldName(fieldname)
                .hostExecutor(trg.getQualifier())
                .marshalling(receiverMarshal)
                .build();
        }

        return FieldCast.builder()
            .firstCast(firstMarshal)
            .secondCast(secondMarshal)
            .build();
    }

    private boolean isToBeReturned(IFieldAnalysis field) {
        TypeClass typeClass = getInput().getFieldAccessUtil().resultingFieldType(field);
        return ResolvePolicyUtil.toBeReturned(getInput().getResolveRequest(), field.getFieldname(), TypeUtil.isService(typeClass));
    }

    public static class DTCInput {

        private final IndexFactory indexFactory;

        private final SDLLookupService lookupService;

        private InstructionIndexer indexer;

        private Map<Long, IExecutorHandle> instExecutorMap;

        private final Map<Long, IMethodResolution> mR;

        private IExecutorHandle clientExecutor;

        private FieldAccessUtil fieldAccessUtil;

        private IResolveRequest resolveRequest;

        private OpsSchedule operationSchedule;

        public DTCInput(IndexFactory indexFactory, SDLLookupService lookupService, InstructionIndexer indexer,
                        Map<Long, IExecutorHandle> instExecutorMap, Map<Long, IMethodResolution> mR,
                        IExecutorHandle clientExecutor, FieldAccessUtil fieldAccessUtil,
                        IResolveRequest resolveRequest, OpsSchedule operationSchedule) {
            this.indexFactory = indexFactory;
            this.lookupService = lookupService;
            this.indexer = indexer;
            this.instExecutorMap = instExecutorMap;
            this.mR = mR;
            this.clientExecutor = clientExecutor;
            this.fieldAccessUtil = fieldAccessUtil;
            this.resolveRequest = resolveRequest;
            this.operationSchedule = operationSchedule;
        }

        public SDLLookupService getLookupService() {
            return lookupService;
        }

        public Map<Long, IExecutorHandle> getInstExecutorMap() {
            return instExecutorMap;
        }

        public FieldAccessUtil getFieldAccessUtil() {
            return fieldAccessUtil;
        }

        public IExecutorHandle getClientExecutor() {
            return clientExecutor;
        }

        public InstructionIndexer getIndexer() {
            return indexer;
        }

        public IResolveRequest getResolveRequest() {
            return resolveRequest;
        }

        public Map<Long, IMethodResolution> getMR() {
            return mR;
        }

        public IndexFactory getIndexFactory() {
            return indexFactory;
        }
    }

    public class DTCOutput {

        private final Map<String, IExecutorHandle> fieldLocation = new HashMap<>();

        private List<String> returnFields;

        private void addPreOp(Long index, ScheduledOperation op) {
            getInput().operationSchedule.getInstOps(index).addPreOp(op);
        }

        private void addOutputOp(ScheduledOperation op) {
            getInput().operationSchedule.getFinalOps().add(op);
        }

        private Optional<IExecutorHandle>  getFieldLocation(String fieldname) {
            return Optional.ofNullable(fieldLocation.get(fieldname));
        }

        private void parseConstant(Long index, IParseConstantNode parseC) {
            addPreOp(index, ParseConstant.builder().parseConstantNode(parseC).build());
        }

        private void castField(Long instIndex, IFieldCast doubleCast) {
            addPreOp(instIndex, doubleCast);
        }

        private void serializeAndTransmit(Long instIndex, IFieldCast fieldCast, ITransmission transmission) {
            addPreOp(instIndex, FieldMarshal.builder().marshal(fieldCast.getFirstCast()).build());
            addPreOp(instIndex, transmission);
            if(fieldCast.getSecondCast() != null) {
                addPreOp(instIndex, FieldMarshal.builder().marshal(fieldCast.getSecondCast()).build());
            }
        }

        private void serializeAndTransmitOutput(IFieldCast fieldCast, ITransmission transmission) {
            addOutputOp(FieldMarshal.builder().marshal(fieldCast.getFirstCast()).build());
            addOutputOp(transmission);
            if(fieldCast.getSecondCast() != null) {
                addOutputOp(FieldMarshal.builder().marshal(fieldCast.getSecondCast()).build());
            }
        }

        public List<String> getReturnFields() {
            return returnFields;
        }

        public void setFieldLocation(String fieldname, IExecutorHandle executorH) {
            this.fieldLocation.put(fieldname, executorH);
        }
    }


}
