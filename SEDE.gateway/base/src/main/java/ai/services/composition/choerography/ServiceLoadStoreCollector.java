package ai.services.composition.choerography;

import ai.services.composition.*;
import ai.services.composition.graphs.nodes.ServiceInstanceStorageNode;
import ai.services.composition.choerography.emulation.OpsSchedule;
import ai.services.composition.choerography.emulation.OrchestrationException;
import ai.services.composition.faa.FieldAccessUtil;
import ai.services.composition.graphs.nodes.IServiceInstanceStorageNode;
import ai.services.composition.orchestration.scheduled.ServiceLoadStore;
import ai.services.composition.types.TypeClass;
import ai.services.core.ServiceInstanceHandle;
import ai.services.exec.IExecutorContactInfo;
import ai.services.exec.IExecutorHandle;
import ai.services.requests.resolve.beta.IResolvePolicy;
import ai.services.util.ResolvePolicyUtil;
import ai.services.composition.typing.TypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ServiceLoadStoreCollector
    extends BlockWiseCompileStep<ServiceLoadStoreCollector.SLSCInput, ServiceLoadStoreCollector.SLSCOutput> {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoadStoreCollector.class);

    @Override
    protected SLSCOutput initializeOutput() {
        return new SLSCOutput();
    }

    @Override
    protected void stepBlock() {
        getInput().getFieldAccessUtil().allFields().forEach(this::processField);
    }

    private void processField(IFieldAnalysis field) {
        TypeClass fieldType = null;

        Optional<TypeClass> initialType = Optional.ofNullable(field.getInitialType());
        boolean toBeStored = toBeStored(field.getFieldname());
        // isPresent flag indicates that the service is present on the executor
        // if it is false it means that it needs to be loaded before it can be used
        boolean isPresent = false;
        // this flag indicates if the current field is a service
        boolean isService = false;
        // this flag indicates if the field has been written onto
        // if it is true, it needs to be stored at the end
        boolean wasWritten = false;
        //This indicated the last index where there was a write to the field
        // Used to create a store after the last write
        Long lastAccess = null;
        // Indicates if the service was loaded and not created
        boolean wasLoaded = false;

        if(initialType.isPresent()) {
            fieldType = initialType.get();
            isService = TypeUtil.isService(fieldType);
        }
        String fieldName = field.getFieldname();
        for (IFieldAccess fieldAccess : field.getFieldAccesses()) {
            Long index = fieldAccess.getIndex();
            if(fieldAccess.getAccessType().isAssignment()) {
                if (isService && wasWritten && toBeStored) {
                    if (field.isInjected()) {
                        // store the service instance on this field before replacing its field with a new value.
                        IExecutorContactInfo host = getInput().getInstExecutorMap().get(lastAccess).getContactInfo();
                        String serviceId = null;
                        if (wasLoaded) {
                            serviceId = getInput().getInitialServices().get(fieldName).getId();
                        }
                        getOutput().store(lastAccess, getInput().getIndexFactory().create(),
                            host.getQualifier(), field.getFieldname(), fieldType.getTypeQualifier(), serviceId);
                        wasWritten = false;
                        lastAccess = null;
                    } else {
                        // if a service is not injected (isInjected returns false) then it is not stored.
                        // The reason for this is that the client does not have a handle for the service and the stored service couldn't be accessed anyway.
                        logger.info("Not storing service with field '{}' and type '{}' as it is not injected by the client and no client can receive a handle for it.",
                            fieldName, fieldType
                        );
                    }
                }
                fieldType = field.getInstTyping()
                    .get(index);
                if (TypeUtil.isService(fieldType)) {
                    wasLoaded = false;
                    isService = true;
                    wasWritten = true;
                    isPresent = true;
                    lastAccess = index;
                }
            } else if(isService) {
                if(fieldAccess.getAccessType().isRead() && !isPresent) {
                    if(!field.isInjected()) {
                        throw new OrchestrationException("Cannot load a non injected (initial) service:  " + fieldName + ", with type: " + fieldType.getTypeQualifier());
                    }
                    IExecutorContactInfo host = getInput().getInstExecutorMap().get(index).getContactInfo();
                    ServiceInstanceHandle serviceInstanceHandle = getInput().getInitialServices().get(fieldName);
                    if(serviceInstanceHandle == null) {
                        throw new OrchestrationException("Among initial services, no handle found for service: " + fieldName + ", with type: " + fieldType.getTypeQualifier());
                    }
                    getOutput().load(index, getInput().getIndexFactory().create(), host.getQualifier(),
                        fieldName, fieldType.getTypeQualifier(), serviceInstanceHandle.getId());
                    wasLoaded = true;
                    isPresent = true;
                }
                if(fieldAccess.getAccessType().isWrite()) {
                    wasWritten = true;
                }
                lastAccess = index;
            }
        }
        // Add an additional store at the end all instructions:
        if(isService && wasWritten && toBeStored) {
            IExecutorContactInfo host = getInput().getInstExecutorMap().get(lastAccess).getContactInfo();
            String serviceId = null;
            if(wasLoaded) {
                serviceId = getInput().getInitialServices().get(fieldName).getId();
            }
            getOutput().store(lastAccess, getInput().indexFactory.create(),
                host.getQualifier(), field.getFieldname(), fieldType.getTypeQualifier(), serviceId);
        }
    }

    private boolean toBeStored(String fieldname) {
        return ResolvePolicyUtil.toBeStored(getInput().getResolvePolicy(), fieldname);
    }


    public static class SLSCInput {

        private final IndexFactory indexFactory;

        private final Map<Long, IMethodResolution> mR;

        private final FieldAccessUtil fieldAccessUtil;

        private final Map<Long, IExecutorHandle> instExecutorMap;

        private final IExecutorHandle clientExecutor;

        private final IResolvePolicy resolvePolicy;

        private final OpsSchedule opsSchedule;

        private final Map<String,ServiceInstanceHandle> initialServices;

        public SLSCInput(IndexFactory indexFactory, Map<Long, IMethodResolution> methodResolution, FieldAccessUtil fieldAccessUtil,
                         Map<Long, IExecutorHandle> instExecutorMap, IExecutorHandle clientExecutor, IResolvePolicy resolvePolicy, OpsSchedule opsSchedule,
                         Map<String, ServiceInstanceHandle> initialServices) {
            this.indexFactory = indexFactory;
            this.mR = methodResolution;
            this.fieldAccessUtil = fieldAccessUtil;
            this.instExecutorMap = instExecutorMap;
            this.clientExecutor = clientExecutor;
            this.resolvePolicy = resolvePolicy;
            this.opsSchedule = opsSchedule;
            this.initialServices = initialServices;
        }

        public IndexFactory getIndexFactory() {
            return indexFactory;
        }

        public Map<Long, IMethodResolution> getMR() {
            return mR;
        }

        public FieldAccessUtil getFieldAccessUtil() {
            return fieldAccessUtil;
        }

        public Map<Long, IExecutorHandle> getInstExecutorMap() {
            return instExecutorMap;
        }

        public IExecutorHandle getClientExecutor() {
            return clientExecutor;
        }

        public IResolvePolicy getResolvePolicy() {
            return resolvePolicy;
        }

        public OpsSchedule getOpsSchedule() {
            return opsSchedule;
        }

        public Map<String, ServiceInstanceHandle> getInitialServices() {
            return initialServices;
        }

    }

    public class SLSCOutput {

        private void load(Long instIndex, Long nodeIndex, String host, String field, String typeQualifier, String instanceId) {
            IServiceInstanceStorageNode loadField = ServiceInstanceStorageNode.builder()
                .fieldName(field)
                .index(nodeIndex)
                .instanceIdentifier(Objects.requireNonNull(instanceId))
                .serviceClasspath(typeQualifier)
                .isLoadInstruction(true)
                .hostExecutor(host)
                .build();
            getInput().getOpsSchedule().getInstSchedule(instIndex).addPreOp(ServiceLoadStore.builder()
                .serviceInstanceStorageNode(loadField)
                .build());
        }

        private void store(Long instIndex, Long nodeIndex, String host, String field, String typeQualifier, String instanceId) {
            Objects.requireNonNull(instIndex);
            Objects.requireNonNull(field);
            Objects.requireNonNull(typeQualifier);
            IServiceInstanceStorageNode storeField = ServiceInstanceStorageNode.builder()
                .fieldName(field)
                .index(nodeIndex)
                .instanceIdentifier(instanceId)
                .serviceClasspath(typeQualifier)
                .isLoadInstruction(false)
                .hostExecutor(host)
                .build();
            getInput().getOpsSchedule().getInstSchedule(instIndex).addPostOp(ServiceLoadStore.builder()
                .serviceInstanceStorageNode(storeField)
                .build());
        }

    }
}
