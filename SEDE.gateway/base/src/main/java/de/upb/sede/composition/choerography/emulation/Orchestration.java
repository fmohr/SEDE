package de.upb.sede.composition.choerography.emulation;

import de.upb.sede.IQualifiable;
import de.upb.sede.composition.*;
import de.upb.sede.composition.choerography.emulation.executors.EmExecutor;
import de.upb.sede.composition.choerography.emulation.executors.ExecutionParticipants;
import de.upb.sede.composition.graphs.nodes.*;
import de.upb.sede.composition.orchestration.emulated.*;
import de.upb.sede.composition.orchestration.scheduled.*;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class Orchestration
    extends BlockWiseCompileStep<Orchestration.OrchestrationInput, Orchestration.OrchestrationOutput> {

    private static final Logger logger = LoggerFactory.getLogger(Orchestration.class);

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
            logger.info(instLog(inst, "Starting to orchestrate on executor {}."),
                inst.getInstruction().getHostExecutor());
            Long index = inst.getIndex();
            performScheduledOperations(inst, getInput().getOpsSchedule().getInstOps(index).getPreInstOps());
            executeInstruction(inst);
            performScheduledOperations(inst, getInput().getOpsSchedule().getInstOps(index).getPostInstOps());
        }
        transmitOutputs();
        letClientWaitForOthers();
    }

    private void performScheduledOperations(IIndexedInstruction inst, List<ScheduledOperation> operations) {
        for (ScheduledOperation operation : operations) {
            if (operation instanceof ITransmission) {
                transmitData(inst, (ITransmission) operation);
            } else if (operation instanceof IFieldCast) {
                castDataType(inst, (IFieldCast) operation);
            } else if (operation instanceof IFieldMarshal) {
                marshalField(inst, ((IFieldMarshal) operation).getMarshall());
            } else if (operation instanceof IServiceLoadStore) {
                IServiceLoadStore serviceLoadStore = (IServiceLoadStore) operation;
                if (serviceLoadStore.getServiceInstanceStorageNode().isLoadInstruction()) {
                    loadService(inst, serviceLoadStore.getServiceInstanceStorageNode());
                } else {
                    storeService(inst, serviceLoadStore.getServiceInstanceStorageNode());
                }
            } else if (operation instanceof IParseConstant) {
                parseConstant(inst, ((IParseConstant) operation).getParseConstantNode());
            } else {
                throw new OrchestrationException(StringUtil.unexpectedTypeMsg("ScheduledOperation", operation));
            }
        }
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
            .instructionNode(indexedInst.getInstruction())
            .mR(getInput().mr.get(indexedInst.getIndex()));

        if (inst.getContextIsFieldFlag()) {
            instOpBuilder.addDFields(inst.getContext());
        }
        if (inst.isAssignment()) {
            instOpBuilder.addDFields(inst.getFieldName());
        }
        InstructionOp instructionOp = instOpBuilder.build();
        logger.info(instLog(indexedInst, "Executing instruction."));
        EmExecutor executor = hostOf(indexedInst);
        try {
            executor.execute(instructionOp);
        } catch (Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating execution of instruction %s:%s",
                indexedInst.getIndex().toString(), indexedInst.getInstruction().getFMInstruction()), ex);
        }
    }

    private void castDataType(IIndexedInstruction inst, IFieldCast cast) {
        try {
            logger.info(instLog(inst, "Casting field {} using:\n{}\nand:\n{}"), cast.getFirstCast().getFieldName(), cast.getFirstCast(), cast.getSecondCast());
            orchestrateCast(hostOf(inst), cast);
        } catch (Exception ex) {
            throw new OrchestrationException(
                String.format("Error while orchestrating double casting: \n - %s \n - %s",
                    cast.getFirstCast().getText(),
                    Optional.ofNullable(cast.getSecondCast()).map(IMarshalNode::getText).orElse("Nop")), ex);
        }
    }

    private void marshalField(IIndexedInstruction inst, IMarshalNode marshal) {
        String taskDescription = instLog(inst, marshal.getText());
        try {
            logger.info(taskDescription);
            orchestrateMarshal(marshal);
        } catch (Exception ex) {
            throw new OrchestrationException("Error while marshalling field: \n - " + taskDescription, ex);
        }
    }


    private void parseConstant(IIndexedInstruction inst, IParseConstantNode parse) {
        try {
            logger.info(instLog(inst, "Parsing constant {}."), parse.getConstantValue());
            orchestrateParse(hostOf(inst), parse);
        } catch (Exception ex) {
            throw new OrchestrationException(
                String.format("Error while orchestrating parse of '%s'",
                    parse.getConstantValue()), ex);
        }
    }

    private void storeService(IIndexedInstruction inst, IServiceInstanceStorageNode store) {
        try {
            logger.info(instLog(inst, "Storing service: {}"), store);
            orchestrateStore(store);
        } catch (Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating service stores for " +
                    "instruction: %s:%s. " +
                    "store: %s",
                inst.getIndex(), inst.getInstruction().getFMInstruction(), store.toString()), ex);
        }
    }

    private void loadService(IIndexedInstruction inst, IServiceInstanceStorageNode load) {
        try {
            logger.info(instLog(inst, "Loading service: {}"), load);
            orchestrateLoad(load);
        } catch (Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating service loads for " +
                    "instruction: %s:%s. " +
                    "load: %s",
                inst.getIndex(), inst.getInstruction().getFMInstruction(), load.toString()), ex);
        }
    }

    private void transmitData(IIndexedInstruction inst, ITransmission t) {
        try {
            logger.info(instLog(inst, "Transmitting {} from {} to {}."),
                t.getAcceptDataNode().getFieldName(),
                ofNullable(t.getSource()).map(IQualifiable::getQualifier).orElse("null"),
                t.getTarget().getQualifier());
            orchestrateTransmission(t);
        } catch (Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating transmissions for instruction: %s:%s. Transmission: %s",
                inst.getIndex().toString(), inst.getInstruction().getFMInstruction(), t.toString()), ex);
        }
    }

    private void transmitOutputs() {
        List<ScheduledOperation> finalOps = getInput().getOpsSchedule().getFinalOps();
        // we use a dummy instruction for now to be able to use the logging function.
        IIndexedInstruction dummyReturnInstruction = IndexedInstruction.builder()
            .instruction(InstructionNode.builder()
                .fMInstruction("ReturnOutputs;")
                .context("return")
                .contextIsFieldFlag(false)
                .method("return")
                .index(-1L)
                .build())
            .build();
        performScheduledOperations(dummyReturnInstruction, finalOps);
    }

    private void letClientWaitForOthers() {
        EmExecutor clientExecutor = getInput().executionParticipants.getClientExecutor();
        IExecutorHandle clientHandle = clientExecutor.getExecutorHandle();
        String clientId = clientHandle.getQualifier();
        List<IWaitForNotificationNode> clientWaitList = new ArrayList<>();
        for (EmExecutor executor : getInput().executionParticipants) {
            String executorId = executor.getExecutorHandle().getQualifier();
            if (clientId.equals(executorId)) {
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
                    "%s on executor %s", finishOp.toString(), executorId), ex);
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
        if (clientWaitList.isEmpty()) {
            logger.info("Execution finishes on the client without waiting for other executors.");
            return;
        }
        IWaitForFinishOp waitForFinishOp = WaitForFinishOp.builder()
            .nopNode(NopNode.builder()
                .hostExecutor(clientId)
                .index(getInput().indexFactory.create())
                .build())
            .addAllExFinishedNtf(clientWaitList)
            .build();
        try {
            logger.info("Let the client executor wait for {} other executors to notify it that their execution is finished.", clientWaitList.size());
            clientExecutor.execute(waitForFinishOp);
        } catch (Exception ex) {
            throw new OrchestrationException(String.format("Error while orchestrating " +
                "%s notification' on executor %s", waitForFinishOp.toString(), clientId), ex);
        }

    }


    // -- orchestration methods

    private void orchestrateCast(EmExecutor executor, IFieldCast cast) throws EmulationException {
        ICastOp castOp = CastOp.builder()
            .firstCast(cast.getFirstCast())
            .secondCast(cast.getSecondCast())
            .addDFields(cast.getFirstCast().getFieldName())
            .build();

        executor.execute(castOp);

    }

    private void orchestrateMarshal(IMarshalNode marshal) throws EmulationException {
        IMarshalOp marshalOp = MarshalOp.builder()
            .marshalNode(marshal)
            .addDFields(marshal.getFieldName())
            .build();

        EmExecutor executor = host(requireNonNull(marshal.getHostExecutor()));
        executor.execute(marshalOp);
    }

    private void orchestrateParse(EmExecutor executor, IParseConstantNode parse) throws EmulationException {
        IParseOp parseOp = ParseOp.builder()
            .parseConstantNode(parse)
            .addDFields(parse.getConstantValue())
            .build();
        executor.execute(parseOp);
    }

    private void orchestrateStore(IServiceInstanceStorageNode store) throws EmulationException {
        if (store.isLoadInstruction()) {
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
        if (!load.isLoadInstruction()) {
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

        private final Map<Long, IMethodResolution> mr;

        // The results of the runtime pipelines

        private final Map<Long, IExecutorHandle> instExecutorMap;

        private final ExecutionParticipants executionParticipants;

        private final OpsSchedule opsSchedule;

        public OrchestrationInput(NotificationFactory notificationFactory, IndexFactory indexFactory, InstructionIndexer indexer,
                                  Map<Long, IMethodResolution> mr, Map<Long, IExecutorHandle> instExecutorMap, ExecutionParticipants executionParticipants,
                                  OpsSchedule scheduledOperation) {
            this.notificationFactory = notificationFactory;
            this.indexFactory = indexFactory;
            this.indexer = indexer;
            this.mr = mr;
            this.instExecutorMap = instExecutorMap;
            this.executionParticipants = executionParticipants;
            this.opsSchedule = scheduledOperation;
        }

        public OpsSchedule getOpsSchedule() {
            return opsSchedule;
        }
    }

    static class OrchestrationOutput {

    }

}
