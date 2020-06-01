package de.upb.sede.composition.choerography;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.*;
import de.upb.sede.composition.faa.FieldAccessUtil;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.graphs.types.TypeClass;
import de.upb.sede.composition.orchestration.DoubleCast;
import de.upb.sede.composition.orchestration.IDoubleCast;
import de.upb.sede.composition.orchestration.ITransmission;
import de.upb.sede.composition.orchestration.Transmission;
import de.upb.sede.composition.typing.TypeCheckException;
import de.upb.sede.exec.IExecutorContactInfo;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.requests.resolve.beta.IResolveRequest;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.types.IDataTypeRef;
import de.upb.sede.util.ResolvePolicyUtil;
import de.upb.sede.util.TypeUtil;

import java.util.*;

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
        }
        transmitOutputs();
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
        MethodResolution mr = getInput().getMR().get(inst.getIndex());
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
                ITransmission serviceTransmission = createServiceTransmission(locationOfContext, executorH, serviceQualifier);
                getOutput().addTransmission(instIndex, serviceTransmission);
            }
        }
        List<String> params = inst.getInstruction().getParameterFields();
        for (int paramIndex = 0; paramIndex < params.size(); paramIndex++) {
            ITypeCoercion typeCoercion = mr.getParamTypeCoercions().get(paramIndex);
            if(typeCoercion.hasConstant()) {
                IParseConstantNode parseC = ParseConstantNode.builder()
                    .constantValue(Objects.requireNonNull(typeCoercion.getConstant()))
                    .hostExecutor(executorH.getQualifier())
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
                        IDoubleCast cast = DoubleCast.builder()
                            .firstCast(CastTypeNode.builder()
                                .fieldName(fieldname)
                                .hostExecutor(executorH.getQualifier())
                                .sourceType(typeCoercion.getSourceType())
                                .targetType(Objects.requireNonNull(typeCoercion.getSemanticType()))
                                .castToSemantic(true)
                                .build())
                            .secondCast(CastTypeNode.builder()
                                .fieldName(fieldname)
                                .hostExecutor(executorH.getQualifier())
                                .sourceType(typeCoercion.getSemanticType())
                                .targetType(typeCoercion.getResultType())
                                .castToSemantic(false)
                                .build())
                            .build();
                        getOutput().castField(instIndex, cast);
                    } else {
                        // no need to cast or do anything.
                    }
                } else {
                    // field is on another executor
                    // create transmission with the correct inplace casts:
                    ITransmission dataTransmission = createDataTransmission(sourceLocation, executorH, fieldname, typeCoercion);
                    getOutput().addTransmission(instIndex, dataTransmission);
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


    private ITransmission createDataTransmission(IExecutorHandle src, IExecutorHandle trg, String fieldname,
                                                 ITypeCoercion typeCoercion) {
        if(typeCoercion.getSemanticType() == null) {
            throw new IllegalArgumentException("Type-coercion has no semantic type: " + typeCoercion);
        }
        CastTypeNode transmitterCast;
        CastTypeNode receiverCast;
        transmitterCast = CastTypeNode.builder()
            .fieldName(fieldname)
            .hostExecutor(src.getQualifier())
            .sourceType(typeCoercion.getSourceType())
            .targetType(Objects.requireNonNull(typeCoercion.getSemanticType()))
            .castToSemantic(true)
            .build();
        ITransmitDataNode transmitDataNode = TransmitDataNode.builder()
            .fieldName(fieldname)
            .hostExecutor(src.getQualifier())
            .contactInfo(trg.getContactInfo())
            .inPlaceCast(transmitterCast)
            .build();
        if(typeCoercion.resultsInSemanticType()) {
            // if semantic type equals result type do convert at accept stage:
            receiverCast = null;
        } else {
            receiverCast = CastTypeNode.builder()
                .fieldName(fieldname)
                .hostExecutor(trg.getQualifier())
                .sourceType(typeCoercion.getSemanticType())
                .targetType(typeCoercion.getResultType())
                .castToSemantic(false)
                .build();
        }
        IAcceptDataNode acceptDataNode = AcceptDataNode.builder()
            .fieldName(fieldname)
            .hostExecutor(trg.getQualifier())
            .inPlaceCast(receiverCast)
            .build();
        ITransmission transmission = Transmission.builder()
            .source(src)
            .target(trg)
            .transmission(transmitDataNode)
            .acceptDataNode(acceptDataNode)
            .build();
        return transmission;
    }

    private ITransmission createServiceTransmission(IExecutorHandle src, IExecutorHandle trg, String fieldname) {
        String srcExecutorId = src.getQualifier();

        ITransmitDataNode transmitDataNode = TransmitDataNode.builder()
            .fieldName(fieldname)
            .hostExecutor(srcExecutorId)
            .contactInfo(trg.getContactInfo())
            // TODO for now we dont include inplace casts for service instance handles:
//            .inPlaceCast(TypeUtil.createCastToServiceHandleNode()
//                .fieldName(fieldname)
//                .hostExecutor(srcExecutorId)
//                .build())
            .build();
        IAcceptDataNode acceptDataNode = AcceptDataNode.builder()
            .fieldName(fieldname)
            .hostExecutor(trg.getQualifier())
//            .inPlaceCast(TypeUtil.createCastToServiceHandleNode()
//                .fieldName(fieldname)
//                .hostExecutor(trg.getQualifier())
//                .build())
            .build();
        ITransmission transmission = Transmission.builder()
            .source(src)
            .target(trg)
            .transmission(transmitDataNode)
            .acceptDataNode(acceptDataNode)
            .build();
        return transmission;
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
                ITransmission serviceTransmission = createServiceTransmission(sourceH, clientH, fieldname);
                getOutput().addOutputTransmission(serviceTransmission);
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
                ITransmission dataTransmission = createDataTransmission(sourceH, clientH, fieldname, typeCoercion);
                getOutput().addOutputTransmission(dataTransmission);
            } else {
                throw new IllegalStateException("Field \n" + field + "\n is being returned to client. But its type \n" + outputType + "\n is not data nor service.");
            }
        }
    }

    private boolean isToBeReturned(IFieldAnalysis field) {
        TypeClass typeClass = getInput().getFieldAccessUtil().resultingFieldType(field);
        return ResolvePolicyUtil.toBeReturned(getInput().getResolveRequest(), field.getFieldname(), TypeUtil.isService(typeClass));
    }

    public static class DTCInput {


        private final SDLLookupService lookupService;

        private InstructionIndexer indexer;

        private Map<Long, IExecutorHandle> instExecutorMap;

        private final Map<Long, MethodResolution> mR;

        private IExecutorHandle clientExecutor;

        private FieldAccessUtil fieldAccessUtil;

        private IResolveRequest resolveRequest;

        public DTCInput(SDLLookupService lookupService, InstructionIndexer indexer, Map<Long, IExecutorHandle> instExecutorMap, Map<Long, MethodResolution> mR, IExecutorHandle clientExecutor, FieldAccessUtil fieldAccessUtil, IResolveRequest resolveRequest) {
            this.lookupService = lookupService;
            this.indexer = indexer;
            this.instExecutorMap = instExecutorMap;
            this.mR = mR;
            this.clientExecutor = clientExecutor;
            this.fieldAccessUtil = fieldAccessUtil;
            this.resolveRequest = resolveRequest;
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

        public Map<Long, MethodResolution> getMR() {
            return mR;
        }
    }

    public static class DTCOutput {

        private final Map<String, IExecutorHandle> fieldLocation = new HashMap<>();

        private final Map<Long, List<ITransmission>> preInstTransmissions = new HashMap<>();

        private final Map<Long, List<IDoubleCast>> preInstCasts = new HashMap<>();

        private final Map<Long, List<IParseConstantNode>> preInstParse = new HashMap<>();

        private final List<ITransmission> outputTransmissions = new ArrayList<>();

        private Optional<IExecutorHandle>  getFieldLocation(String fieldname) {
            return Optional.ofNullable(fieldLocation.get(fieldname));
        }

        private ITransmitDataNode createTransmission(IExecutorContactInfo source, IExecutorContactInfo target,
                                                     String fieldname) {
            return TransmitDataNode.builder()
                .fieldName(fieldname)
                .contactInfo(target)
                .hostExecutor(source.getQualifier())
                .build();
        }

        private void parseConstant(Long index, IParseConstantNode parseC) {
            this.preInstParse.computeIfAbsent(index, i -> new ArrayList<>()).add(parseC);
        }

        private void castField(Long instIndex, IDoubleCast doubleCast) {
            this.preInstCasts.computeIfAbsent(instIndex, i -> new ArrayList<>()).add(doubleCast);
        }

        public void addTransmission(Long instIndex, ITransmission transmission) {
            this.preInstTransmissions.computeIfAbsent(instIndex, i -> new ArrayList<>()).add(transmission);
        }

        public void addOutputTransmission(ITransmission transmission) {
            this.outputTransmissions.add(transmission);
        }

    }


}
