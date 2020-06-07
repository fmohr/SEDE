package de.upb.sede.composition.choerography;

import de.upb.sede.composition.*;
import de.upb.sede.composition.faa.FieldAccessUtil;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.exec.IExecutorContactInfo;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.requests.resolve.beta.IResolvePolicy;
import de.upb.sede.util.ResolvePolicyUtil;
import de.upb.sede.composition.typing.TypeUtil;

import java.util.*;

public class ServiceLoadStoreCollector
    extends BlockWiseCompileStep<ServiceLoadStoreCollector.SLSCInput, ServiceLoadStoreCollector.SLSCOutput> {

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
        boolean wasLoaded = false;
        boolean isService = false;
        boolean wasWritten = false;
        Long lastWritten = null;

        if(initialType.isPresent()) {
            fieldType = initialType.get();
            isService = TypeUtil.isService(fieldType);
        }
        for (IFieldAccess fieldAccess : field.getFieldAccesses()) {
            Long index = fieldAccess.getIndex();
            if(fieldAccess.getAccessType().isAssignment()) {
                if(isService && wasWritten && toBeStored) {
                    // the previous service instance needs to be stored:
                    IExecutorContactInfo host = getInput().getInstExecutorMap().get(lastWritten).getContactInfo();
                    getOutput().store(lastWritten, getInput().getIndexFactory().create(),
                        host.getQualifier(), field.getFieldname(), fieldType.getTypeQualifier());
                    wasWritten = false;
                    lastWritten = null;
                }
                fieldType = field.getInstTyping()
                    .get(index);
                if(TypeUtil.isService(fieldType)) {
                    isService = true;
                    wasWritten = true;
                    wasLoaded = true;
                    lastWritten = index;
                }
            } else if(isService) {
                if(fieldAccess.getAccessType().isRead() && !wasLoaded) {
                    IExecutorContactInfo host = getInput().getInstExecutorMap().get(index).getContactInfo();
                    IMethodResolution iMethodResolution = getInput().getMR().get(index);
                    // TODO add method reference
                    getOutput().load(index, getInput().getIndexFactory().create(), host.getQualifier(),
                        fieldAccess.getField(), fieldType.getTypeQualifier());

                    wasLoaded = true;
                }
                if(fieldAccess.getAccessType().isWrite()) {
                    wasWritten = true;
                    lastWritten = index;
                }
            }
        }
        // Add an additional store at the end all instructions:
        if(isService && wasWritten && toBeStored) {
            IExecutorContactInfo host = getInput().getInstExecutorMap().get(lastWritten).getContactInfo();
            getOutput().store(lastWritten, getInput().indexFactory.create(),
                host.getQualifier(), field.getFieldname(), fieldType.getTypeQualifier());
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

        public SLSCInput(IndexFactory indexFactory, Map<Long, IMethodResolution> methodResolution, FieldAccessUtil fieldAccessUtil, Map<Long, IExecutorHandle> instExecutorMap, IExecutorHandle clientExecutor, IResolvePolicy resolvePolicy) {
            this.indexFactory = indexFactory;
            this.mR = methodResolution;
            this.fieldAccessUtil = fieldAccessUtil;
            this.instExecutorMap = instExecutorMap;
            this.clientExecutor = clientExecutor;
            this.resolvePolicy = resolvePolicy;
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
    }

    public static class SLSCOutput {

        Map<Long, List<IServiceInstanceStorageNode>> preInstLoads = new HashMap<>();

        Map<Long, List<IServiceInstanceStorageNode>> postInstStores = new HashMap<>();

        private void load(Long instIndex, Long nodeIndex, String host, String field, String typeQualifier) {
            IServiceInstanceStorageNode loadField = ServiceInstanceStorageNode.builder()
                .fieldName(field)
                .index(nodeIndex)
                .serviceClasspath(typeQualifier)
                .isLoadInstruction(true)
                .hostExecutor(host)
                .build();
            preInstLoads.computeIfAbsent(instIndex, i -> new ArrayList<>()).add(loadField);
        }

        private void store(Long instIndex, Long nodeIndex, String host, String field, String typeQualifier) {
            Objects.requireNonNull(instIndex);
            Objects.requireNonNull(field);
            Objects.requireNonNull(typeQualifier);
            IServiceInstanceStorageNode storeField = ServiceInstanceStorageNode.builder()
                .fieldName(field)
                .index(nodeIndex)
                .serviceClasspath(typeQualifier)
                .isLoadInstruction(false)
                .hostExecutor(host)
                .build();
            postInstStores.computeIfAbsent(instIndex, i -> new ArrayList<>()).add(storeField);
        }

        public Map<Long, List<IServiceInstanceStorageNode>> getPreInstLoads() {
            return preInstLoads;
        }

        public Map<Long, List<IServiceInstanceStorageNode>> getPostInstStores() {
            return postInstStores;
        }
    }
}
