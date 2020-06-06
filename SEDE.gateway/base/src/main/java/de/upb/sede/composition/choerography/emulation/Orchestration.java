package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.IQualifiable;
import de.upb.sede.composition.*;
import de.upb.sede.composition.choerography.emulation.executors.EmExecutor;
import de.upb.sede.composition.choerography.emulation.executors.ExecutionParticipants;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.orchestration.*;
import de.upb.sede.exec.IExecutorHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class Orchestration
    extends BlockWiseCompileStep<Orchestration.OrchestrationInput, Orchestration.OrchestrationOutput> {

    private final static Logger logger = LoggerFactory.getLogger(Orchestration.class);

    @Override
    protected OrchestrationOutput initializeOutput() {
        return new OrchestrationOutput();
    }

    @Override
    protected void stepBlock() {
        logger.info("Starting to orchestrate the composition \n{}\n with {} many emulated executors.",
            getInput().indexer.recreateComposition(),
            getInput().executionParticipants.size());
        for (IIndexedInstruction inst : getInput().indexer) {

            fetchData(inst);
            loadServices(inst);
            castDataTypes(inst);
            parseConstants(inst);
            executeInstruction(inst);
            storeServices(inst);
        }
        transmitOutputs();
        finishExecution();
    }



    private String instLog(IIndexedInstruction instIndex, String message) {
        return String.format("%s:%s > %s", instIndex.getIndex().toString(), instIndex.getInstruction().getFMInstruction(), message);
    }

    private EmExecutor hostOf(IIndexedInstruction inst) {
        return host(getInput().instExecutorMap.get(inst.getIndex()));
    }

    private EmExecutor hostOf(WithExecutorHost withExecutorHost) {
        return host(requireNonNull(withExecutorHost.getHostExecutor(), "Node " + withExecutorHost + " has no executor set."));
    }

    private EmExecutor host(IExecutorHandle handle) {
        return host(handle.getQualifier());
    }

    private EmExecutor host(String qualifier) {
        return getInput().executionParticipants.getExecutor(qualifier);
    }

    private INotification createNtf(String description) {
        return getInput().notificationFactory.createNotification(description);
    }

//    private void injectInitialFields() {
//        Emulation.Executor clientExecutor = getInput().executorRegistry.getClientExecutor();
//        getInput().fieldAccessUtil.initialFields().forEach(field -> {
//            clientExecutor.injectField(field.getFieldType());
//        });
//    }

    private void executeInstruction(IIndexedInstruction indexedInst) {
        IInstructionNode inst = indexedInst.getInstruction();
        InstructionOp.Builder instOpBuilder = InstructionOp.builder();
        instOpBuilder.addAllDFields(inst.getParameterFields())
            .instructionNode(indexedInst.getInstruction());

        if(inst.getContextIsFieldFlag()) {
            instOpBuilder.addDFields(inst.getContext());
        }
        InstructionOp instructionOp = instOpBuilder.build();
        logger.info(instLog(indexedInst, "Executing instruction."));
        EmExecutor executor = hostOf(indexedInst);
        try {
            executor.execute(instructionOp);
        } catch(Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating execution of instruction %s:%s",
                indexedInst.getIndex().toString(), indexedInst.getInstruction().getFMInstruction()), ex);
        }
    }



    private void castDataTypes(IIndexedInstruction inst) {
        List<IDoubleCast> casts = getInput().preInstCasts.get(inst.getIndex());
        if(casts == null || casts.isEmpty()) {
            logger.info(instLog(inst, "No casts before instruction."));
            return;
        }
        for (IDoubleCast cast : casts) {
            try {
                orchestrateCast(hostOf(inst), cast);
            } catch(Exception ex) {
                throw new OrchestrationException(
                    String.format("Error while orchestrating cast of field '%s' from '%s' to '%s' through semantic type '%s'.",
                        cast.getFirstCast().getFieldName(),
                        cast.getFirstCast().getSourceType(),
                        cast.getSecondCast().getTargetType(),
                        cast.getFirstCast().getTargetType()), ex);
            }
        }
    }


    private void parseConstants(IIndexedInstruction inst) {
        List<IParseConstantNode> parses = getInput().preInstParse.get(inst.getIndex());
        if(parses == null || parses.isEmpty()) {
            logger.info(instLog(inst, "No const to be parsed before instruction."));
            return;
        }
        for (IParseConstantNode parse : parses) {
            try {
                orchestrateParse(hostOf(inst), parse);
            } catch(Exception ex) {
                throw new OrchestrationException(
                    String.format("Error while orchestrating parse of '%s'",
                        parse.getConstantValue()), ex);
            }
        }
    }

    private void storeServices(IIndexedInstruction inst) {
        List<IServiceInstanceStorageNode> stores = getInput().postInstStores.get(inst.getIndex());
        if(stores == null || stores.isEmpty()) {
            logger.info(instLog(inst, "No service store after instruction."));
            return;
        }
        for (IServiceInstanceStorageNode store : stores) {
            try {
                orchestrateStore(store);
            } catch(Exception ex) {
                throw new OrchestrationException(String.format("Error while orchestrating service stores for " +
                        "instruction: %s:%s. " +
                        "store: %s",
                    inst.getIndex(), inst.getInstruction().getFMInstruction(), store.toString()), ex);
            }
        }
    }

    private void loadServices(IIndexedInstruction inst) {
        List<IServiceInstanceStorageNode> loads = getInput().preInstLoads.get(inst.getIndex());
        if(loads == null || loads.isEmpty()) {
            logger.info(instLog(inst, "No service loads before instruction."));
            return;
        }
        for (IServiceInstanceStorageNode load : loads) {
            try {
                orchestrateLoad(load);
            } catch(Exception ex) {
                throw new OrchestrationException(String.format("Error while orchestrating service loads for " +
                    "instruction: %s:%s. " +
                    "load: %s",
                    inst.getIndex(), inst.getInstruction().getFMInstruction(), load.toString()), ex);
            }
        }
    }

    private void fetchData(IIndexedInstruction inst) {
        List<ITransmission> transmissions = getInput().preInstTransmissions.get(inst.getIndex());
        if(transmissions == null || transmissions.isEmpty()) {
            logger.info(instLog(inst, "No transmissions store before instruction."));
            return;
        }
        for (ITransmission t : transmissions) {
            try {
                logger.info(instLog(inst, "Transmitting {} from {} to {}."),
                    t.getAcceptDataNode().getFieldName(),
                    ofNullable(t.getSource()).map(IQualifiable::getQualifier).orElse("null"),
                    t.getTarget().getQualifier());
                orchestrateTransmission(t);
            } catch(Exception ex) {
                throw new OrchestrationException(String.format("Error while orchestrating transmissions for instruction: %s:%s. Transmission: %s",
                    inst.getIndex().toString(), inst.getInstruction().getFMInstruction(), t.toString()), ex);
            }
        }
    }



    private void transmitOutputs() {
        if(getInput().outputTransmissions == null || getInput().outputTransmissions.isEmpty()) {
            logger.info("No output transmissions to be done.");
            return;
        }
        for (ITransmission transmission : getInput().outputTransmissions) {
            try {
                logger.info("Transmitting output {} from {} to client.",
                    transmission.getAcceptDataNode().getFieldName(),
                    transmission.getSource().getQualifier());
                orchestrateTransmission(transmission);
            } catch(Exception ex) {
                throw new OrchestrationException("Error orchestrating transmission of output "
                    + transmission.getAcceptDataNode().getFieldName() + " from "
                    + transmission.getSource().getQualifier(), ex);
            }
        }
    }

    private void finishExecution() {
        EmExecutor clientExecutor = getInput().executionParticipants.getClientExecutor();
        IExecutorHandle clientHandle = clientExecutor.getExecutorHandle();
        String clientId = clientHandle.getQualifier();
        List<IWaitForNotificationNode> clientWaitList = new ArrayList<>();
        for (EmExecutor executor : getInput().executionParticipants) {
            String executorId = executor.getExecutorHandle().getQualifier();
            if(clientId.equals(executorId)) {
                continue;
            }
            /*
				Add notification that indicates that execution is done:
		 	*/
            INotification executionFinishedNtf = createNtf("Execution " + executorId + " is finished");
            IFinishOp finishOp = FinishOp.builder()
                .executionFinishedNtf(NotifyNode.builder()
                    .index(getInput().indexFactory.create())
                    .hostExecutor(executorId)
                    .notification(executionFinishedNtf)
                    .contactInfo(clientHandle.getContactInfo())
                    .build()
                )
                .build();
            try {
                executor.execute(finishOp);
            } catch (Exception ex) {
                throw new OrchestrationException(String.format("Error while orchestrating " +
                    "%s on executor %s", finishOp.toString(), executorId) , ex);
            }

			/*
				Add accept notification to client:
			 */
            clientWaitList.add(WaitForNotificationNode.builder()
                .index(getInput().indexFactory.create())
                .notification(executionFinishedNtf)
                .hostExecutor(clientId)
                .build());
        }
        IWaitForFinishOp waitForFinishOp = WaitForFinishOp.builder()
            .nopNode(NopNode.builder()
                .hostExecutor(clientId)
                .index(getInput().indexFactory.create())
                .build())
            .addAllExFinishedNtf(clientWaitList)
            .build();
        try {
            clientExecutor.execute(waitForFinishOp);
        } catch(Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating " +
                "%s notification' on executor %s", waitForFinishOp.toString(), clientId) , ex);
        }

    }


    // -- orchestration methods

    private void orchestrateCast(EmExecutor executor, IDoubleCast cast) throws EmulationException {
        ICastOp castOp = CastOp.builder()
            .firstCast(cast.getFirstCast())
            .secondCast(cast.getSecondCast())
            .addDFields(cast.getFirstCast().getFieldName())
            .build();

        executor.execute(castOp);

    }

    private void orchestrateParse(EmExecutor executor, IParseConstantNode parse) throws EmulationException {
        IParseOp parseOp = ParseOp.builder()
            .parseConstantNode(parse)
            .addDFields(parse.getConstantValue())
            .build();
        executor.execute(parseOp);
    }

    private void orchestrateStore(IServiceInstanceStorageNode store) throws EmulationException {
        if(store.isLoadInstruction()) {
            throw new IllegalStateException("Expected post inst store opeation but found service load.");
        }
        IServiceLoadStoreOp op = ServiceLoadStoreOp.builder()
            .addDFields(store.getFieldName())
            .serviceInstanceStorageNode(store)
            .build();

        EmExecutor executor = hostOf(store);
        executor.execute(op);
    }

    private void orchestrateLoad(IServiceInstanceStorageNode load) throws EmulationException {
        if(!load.isLoadInstruction()) {
            throw new IllegalStateException("Expected pre inst load operation but found service store.");
        }
        IServiceLoadStoreOp op = ServiceLoadStoreOp.builder()
            .addDFields(load.getFieldName())
            .serviceInstanceStorageNode(load)
            .build();

        EmExecutor executor = hostOf(load);
        executor.execute(op);
    }

    private void orchestrateTransmission(ITransmission t) throws EmulationException {
        String srcQualifier = requireNonNull(t.getSource().getQualifier(),
            "Transmission " + t + " has no host executor contact info (source).");
        String trgQualifier = requireNonNull(t.getTarget().getQualifier(),
            "Transmission " + t + " has no contact info (target).");
        EmExecutor srcEx = host(srcQualifier);
        EmExecutor trgEx = host(trgQualifier);
        String fieldname = t.getAcceptDataNode().getFieldName();

        INotification srcReadyNtf = createNtf("Source executor is ready to transmit " + fieldname);
        INotification trgReadyNtf = createNtf("Target executor is ready to accept " + fieldname);
        INotification receivedNtf = createNtf("Target executor received " + fieldname);

        ITransmissionOp transOp = TransmissionOp.builder()
            // transmission node with 3 notification for the handshake
            .transmitDataNode(t.getTransmission())
            .sourceReadyNtf(NotifyNode.builder()
                .index(getInput().indexFactory.create())
                .contactInfo(t.getTarget().getContactInfo())
                .notification(srcReadyNtf)
                .hostExecutor(srcQualifier)
                .build())
            .targetReadyNtf(WaitForNotificationNode.builder()
                .index(getInput().indexFactory.create())
                .notification(trgReadyNtf)
                .hostExecutor(srcQualifier)
                .build())
            .targetReceivedNtf(WaitForNotificationNode.builder()
                .index(getInput().indexFactory.create())
                .notification(receivedNtf)
                .hostExecutor(srcQualifier)
                .build())
            // A transmission produces and consumes the transmitted field:
            .addDFields(fieldname)
            .deleteFieldNode(DeleteFieldNode.builder()
                .fieldName(fieldname)
                .index(getInput().indexFactory.create())
                .hostExecutor(srcQualifier)
                .build())
            .build();
        srcEx.execute(transOp);

        IAcceptOp acceptOp = AcceptOp.builder()
            .acceptDataNode(t.getAcceptDataNode())
            .sourceReadyNtf(WaitForNotificationNode.builder()
                .index(getInput().indexFactory.create())
                .notification(srcReadyNtf)
                .hostExecutor(trgQualifier)
                .build())
            .targetReadyNtf(NotifyNode.builder()
                .contactInfo(t.getSource().getContactInfo())
                .index(getInput().indexFactory.create())
                .notification(trgReadyNtf)
                .hostExecutor(trgQualifier)
                .build())
            .targetReceivedNtf(NotifyNode.builder()
                .contactInfo(t.getSource().getContactInfo())
                .index(getInput().indexFactory.create())
                .notification(receivedNtf)
                .hostExecutor(trgQualifier)
                .build())
            .addDFields(fieldname)
            .build();

        trgEx.execute(acceptOp);

        // delete field on src:
//        IDeleteFieldOp deleteFieldOp = DeleteFieldOp.builder()
//            .addDFields(fieldname)
//            .deleteFieldNode(DeleteFieldNode.builder()
//                .fieldName(fieldname)
//                .index(getInput().indexFactory.create())
//                .hostExecutor(srcQualifier)
//                .build())
//            .build();
    }

    public static class OrchestrationInput {


        // Services used in orchestration

        private final NotificationFactory notificationFactory;

        private final IndexFactory indexFactory;

        // The results of the static pipeline

        private final InstructionIndexer indexer;

        // The results of the runtime pipelines

        private final Map<Long, IExecutorHandle> instExecutorMap;

        private final ExecutionParticipants executionParticipants;

        private final Map<Long, List<ITransmission>> preInstTransmissions;

        private final Map<Long, List<IDoubleCast>> preInstCasts;

        private final Map<Long, List<IParseConstantNode>> preInstParse;

        private final List<ITransmission> outputTransmissions;

        private final Map<Long, List<IServiceInstanceStorageNode>> preInstLoads;

        private final Map<Long, List<IServiceInstanceStorageNode>> postInstStores;

        public OrchestrationInput(NotificationFactory notificationFactory, IndexFactory indexFactory, InstructionIndexer indexer, Map<Long, IExecutorHandle> instExecutorMap, ExecutionParticipants executionParticipants, Map<Long, List<ITransmission>> preInstTransmissions, Map<Long, List<IDoubleCast>> preInstCasts, Map<Long, List<IParseConstantNode>> preInstParse, List<ITransmission> outputTransmissions, Map<Long, List<IServiceInstanceStorageNode>> preInstLoads, Map<Long, List<IServiceInstanceStorageNode>> postInstStores) {
            this.notificationFactory = notificationFactory;
            this.indexFactory = indexFactory;
            this.indexer = indexer;
            this.instExecutorMap = instExecutorMap;
            this.executionParticipants = executionParticipants;
            this.preInstTransmissions = preInstTransmissions;
            this.preInstCasts = preInstCasts;
            this.preInstParse = preInstParse;
            this.outputTransmissions = outputTransmissions;
            this.preInstLoads = preInstLoads;
            this.postInstStores = postInstStores;
        }

    }

    static class OrchestrationOutput {

    }

}
