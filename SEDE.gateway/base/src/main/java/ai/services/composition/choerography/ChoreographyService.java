package ai.services.composition.choerography;

import ai.services.composition.*;
import ai.services.composition.choerography.emulation.ExecutorBootstrapper;
import ai.services.composition.choerography.emulation.NotificationFactory;
import ai.services.composition.choerography.emulation.OpsSchedule;
import ai.services.composition.choerography.emulation.Orchestration;
import ai.services.composition.choerography.emulation.executors.ChoreographyFinalizer;
import ai.services.composition.choerography.emulation.executors.ExecutionParticipants;
import ai.services.composition.choerography.emulation.executors.ExecutorFactory;
import ai.services.exec.IExecutorContactInfo;
import ai.services.requests.resolve.beta.Choreography;
import ai.services.SDLLookupService;
import ai.services.composition.faa.FieldAccessUtil;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.core.ServiceInstanceHandle;
import ai.services.exec.IExecutorHandle;
import ai.services.gateway.ExecutorArbiter;
import ai.services.gateway.OnDemandExecutorSupplier;
import ai.services.gateway.OnDemandExecutorSupplierChain;
import ai.services.gateway.StandaloneExecutor;
import ai.services.interfaces.ICCService;
import ai.services.interfaces.IChoreographyService;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import ai.services.util.Cache;
import ai.services.util.MappedListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ChoreographyService implements IChoreographyService {

    private static final Logger logger = LoggerFactory.getLogger(ChoreographyService.class);

    private final ICCService iccService;

    private final Cache<SDLLookupService> lookupServiceCache;

    private final ExecutorArbiter executorSupplyCoordinator;

    public ChoreographyService(ICCService iccService, Cache<SDLLookupService> lookupService, ExecutorArbiter executorSupplyCoordinator) {
        this.iccService = iccService;
        this.lookupServiceCache = lookupService;
        this.executorSupplyCoordinator = executorSupplyCoordinator;
    }

    private ICompositionCompilation compile(IResolveRequest resolveRequest) {
        ICCRequest iccRequest = CCRequest.builder()
            .composition(resolveRequest.getComposition())
            .initialContext(resolveRequest.getInitialContext())
            .build();
        return iccService.compileComposition(iccRequest);
    }

    @Override
    public IChoreography resolve(IResolveRequest resolveRequest) {
        ICompositionCompilation cc;
        if(resolveRequest.getCC() != null) {
            logger.debug("Composition compilation is present in the resolve request. Skipping compilation.");
            cc = resolveRequest.getCC();
        } else {
            logger.debug("Compiling composition before resolving it.");
            cc = compile(resolveRequest);
        }

        SDLLookupService lookupService = lookupServiceCache.get();

        Map<String, ServiceInstanceHandle> initialServices = resolveRequest.getInitialServices();
        InstructionIndexer indexer = new InstructionIndexer(cc.getInstructions());
        List<IStaticInstAnalysis> staticInstAnalysis = cc.getStaticInstAnalysis();
        Map<Long, IMethodResolution> mrMap = new MappedListView<>(staticInstAnalysis,
            sia -> sia.getInstruction().getIndex(),
            IStaticInstAnalysis::getMethodResolution);
        FieldAccessUtil fieldAccessUtil = new FieldAccessUtil(indexer, cc);
        IExecutorHandle clientEH = resolveRequest.getClientExecutorRegistration().getExecutorHandle();

        IndexFactory indexFactory = new IndexFactory();
        cc.getInstructions().stream()
            .map(IIndexedInstruction::getIndex)
            .forEach(indexFactory::setOccupiedIndex);

        OnDemandExecutorSupplier registeredExecutors = executorSupplyCoordinator.supplier();
        OnDemandExecutorSupplier clientExecutor = new StandaloneExecutor(resolveRequest.getClientExecutorRegistration().getExecutorHandle());
        OnDemandExecutorSupplier allExecutors = new OnDemandExecutorSupplierChain(registeredExecutors, clientExecutor);

        ExecutorCandidatesCollector ecc = new ExecutorCandidatesCollector();
        ecc.setInput(new ExecutorCandidatesCollector.ECCInput(initialServices, indexer, mrMap, allExecutors, fieldAccessUtil));
        ecc.run();
        MappedListView<Long, List<IExecutorHandle>, Map.Entry<Long, ExecutorCandidatesCollector.ECCOutput>> candidates;
        candidates = new MappedListView<>(ecc.getOutput().getFinalOutput().entrySet(), Map.Entry::getKey, entry -> entry.getValue().getCandidates());

        ExecutionParticipantCollector epc = new ExecutionParticipantCollector();
        epc.setInput(new ExecutionParticipantCollector.EPCInput(candidates, clientEH));
        epc.run();
        List<IExecutorHandle> participants = epc.getOutput().getParticipants();

        InstExecutorLookaheadRewards ielr = new InstExecutorLookaheadRewards();
        ielr.setInput(new InstExecutorLookaheadRewards.IEAInput(indexer, candidates, fieldAccessUtil));
        ielr.run();
        Map<Long, IExecutorHandle> candidateSelection = ielr.getOutput().getCandidateSelection();

        InstructionHostSetter ihs = new InstructionHostSetter();
        ihs.setInput(new InstructionHostSetter.IHSInput(indexer, candidateSelection));
        ihs.run();
        List<IIndexedInstruction> instructions = ihs.getOutput().getInstructions();

        indexer = new InstructionIndexer(instructions);
        OpsSchedule operationSchedule = new OpsSchedule(indexer);

        InstInputCollector iic = new InstInputCollector();
        iic.setInput(new InstInputCollector.DTCInput(indexFactory, lookupService, indexer,
            candidateSelection, mrMap, clientEH,
            fieldAccessUtil, resolveRequest, operationSchedule));
        iic.run();
        Map<String, IExecutorContactInfo> initialFields = iic.getOutput().getInitialFields();
        Map<String, IExecutorContactInfo> returnFields = iic.getOutput().getReturnFields();

        ServiceLoadStoreCollector slsc = new ServiceLoadStoreCollector();
        slsc.setInput(new ServiceLoadStoreCollector.SLSCInput(indexFactory, mrMap, fieldAccessUtil, candidateSelection,
            clientEH, resolveRequest.getResolvePolicy(), operationSchedule, initialServices));
        slsc.run();

        ExecutorFactory ef = new ExecutorFactory(lookupService);
        ExecutorBootstrapper eb = new ExecutorBootstrapper();
        eb.setInput(new ExecutorBootstrapper.EBInput(ef, participants, clientEH));
        eb.run();
        ExecutionParticipants participants1 = eb.getOutput().getFinalOutput();

        NotificationFactory nf = new NotificationFactory();

        Orchestration orchestration = new Orchestration();
        orchestration.setInput(new Orchestration.OrchestrationInput(nf, indexFactory, indexer, mrMap, candidateSelection, participants1,
            operationSchedule));
        logger.info("Simulation of the orchestration is ready. Starting to build a choerography");
        orchestration.run();
        // no formal output
        logger.info("Orchestration simulation is finished. Finalizing choreography by building composition graphs.");
        ChoreographyFinalizer finalizer = new ChoreographyFinalizer();
        finalizer.setInput(new ChoreographyFinalizer.CFInput(participants1));
        finalizer.run();
        List<ICompositionGraph> graphs = finalizer.getOutput().getGraph();

        IChoreography choreography = Choreography.builder()
            .putAllInitialFieldLocation(initialFields)
            .putAllReturnFieldLocation(returnFields)
            .compositionGraph(graphs)
            .build();

        logger.info("Choreography finished building.");
        return choreography;
    }

}
