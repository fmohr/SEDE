package ai.services.composition;

import ai.services.composition.faa.FieldAnalysisCollector;
import ai.services.composition.faa.IFACInput;
import ai.services.composition.faa.IFACOutput;
import ai.services.composition.faa.InstructionFieldAccessCollector;
import ai.services.composition.pio.PIOInput;
import ai.services.composition.pio.ProgramInstructionOrderer;
import ai.services.composition.typing.TCInput;
import ai.services.composition.typing.TCOutput;
import ai.services.composition.typing.TypeChecker;
import ai.services.SDLLookupService;
import ai.services.composition.graphs.nodes.IInstructionNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SingleBlockCCImpl {

    private final SDLLookupService SDLLookupService;

    private final List<IFieldType> currentTypeContext = new ArrayList<>();

//    private final CompileStep<TCInput, InstOutputIterator<TCOutput>> typeChecker = new TypeChecker();
//
//    private final CompileStep<FAAInput, FAAOutput> fieldAccessAnalyser = new FieldAccessAnalyser();
//
//    private final CompileStep<PIOInput, PIOOutput> programInstOrderer = new ProgramInstructionOrderer();

    public SingleBlockCCImpl(SDLLookupService SDLLookupService) {
        this.SDLLookupService = SDLLookupService;
    }

    public SingleBlockCCImpl(SDLLookupService SDLLookupService, List<IFieldType> initialFields) {
        this.SDLLookupService = SDLLookupService;
        this.currentTypeContext.addAll(initialFields);
    }

    private List<IFieldType> getCurrentTypeContext() {
        return currentTypeContext;
    }

    public ICompositionCompilation compileCode(String fmCompositionCode) {
        List<IInstructionNode> instructionsCode;
        instructionsCode = FMCompositionParser.separateInstructions(fmCompositionCode).stream()
            .map(FMCompositionParser::parseInstruction)
            .collect(Collectors.toList());
        return compileInstructionBlock(instructionsCode);
    }

    public ICompositionCompilation compileInstructionBlock(List<IInstructionNode> instructions) {
        /*
         * Index all the instructions:
         */
        IndexFactory indexFactory = new IndexFactory();
        InstructionIndexer instIndexer = new InstructionIndexer(indexFactory, instructions);
        instIndexer.assertNotEmpty();

        /*
         * Type check instructions
         */
        TypeChecker typeChecker = new TypeChecker();
        TCInput tcInput = new TCInput(SDLLookupService, instIndexer, currentTypeContext);
        typeChecker.setInput(tcInput);
        typeChecker.run();
        Map<Long, TCOutput> tcOutput = typeChecker.getOutput().getFinalOutput();

        /*
         * Set the field assignment types
         */
        instIndexer = new InstructionIndexer(instIndexer, tcOutput);

        /*
         * Analyse field accesses
         */
        IFACInput IFACInput = new IFACInput(tcOutput, instIndexer);

        InstructionFieldAccessCollector fieldAccessAnalyser = new InstructionFieldAccessCollector();
        fieldAccessAnalyser.setInput(IFACInput);
        fieldAccessAnalyser.run();
        Map<Long, IFACOutput> faaOutput = fieldAccessAnalyser.getOutput().getFinalOutput();

        /*
         * Create a collection of field accesses from their point of view.
         */
        FieldAnalysisCollector collector = new FieldAnalysisCollector();
        collector.setInput(new FieldAnalysisCollector.FACInput(instIndexer, currentTypeContext, tcOutput, faaOutput));
        collector.run();
        Map<String, IFieldAnalysis> facOutput = collector.getOutput().getFieldAnalysis();

        /*
         * Reorder Instruction and create a program instruction order
         */
        PIOInput pioInput = new PIOInput(instIndexer);
        ProgramInstructionOrderer orderer = new ProgramInstructionOrderer();
        orderer.setInput(pioInput);
        orderer.run();
        List<Long> pioOutput = orderer.getOutput().getProgramOrder();

        ICompositionCompilation compose = compose(instIndexer, tcOutput, faaOutput, facOutput, pioOutput);
        setContextToOutput(instIndexer, tcOutput);
        return compose;
    }

    private void setContextToOutput(InstructionIndexer indexer, Map<Long, TCOutput> tcOutput) {
        TCOutput lastInstTC = tcOutput.get(indexer.getLastIndex());
        List<IFieldType> fields = new ArrayList<>();
        lastInstTC.getFieldTC().extractInto(fields);
        currentTypeContext.clear();
        currentTypeContext.addAll(fields);
    }

    private ICompositionCompilation compose(InstructionIndexer instructions,
                                            Map<Long, TCOutput> tcOutput,
                                            Map<Long, IFACOutput> faaOutput,
                                            Map<String, IFieldAnalysis> facOutput,
                                            List<Long> pioOutput) {
        CompositionCompilation.Builder ccBuilder = CompositionCompilation.builder();
        ccBuilder.qualifier("main");
        ccBuilder.instructions(instructions);
        ccBuilder.programOrder(pioOutput);
        ccBuilder.fields(facOutput.values());
        for(IIndexedInstruction ii : instructions) {
            TCOutput instTC = tcOutput.get(ii.getIndex());
            StaticInstAnalysis.Builder siaBuilder = StaticInstAnalysis.builder();
            List<IFieldType> fieldTypes = new ArrayList<>();
            instTC.getFieldTC().extractInto(fieldTypes);
            siaBuilder.instruction(ii);
            siaBuilder.typeContext(fieldTypes);

            MethodResolution.Builder mrBuilder = MethodResolution.builder();
            mrBuilder.addAllParamTypeCoercions(instTC.getMethodInfo().getParameterTypeCoercions());
            mrBuilder.methodRef(instTC.getMethodInfo().getMethodRef());
            siaBuilder.methodResolution(mrBuilder.build());

            IFACOutput instAccesses = faaOutput.get(ii.getIndex());
            siaBuilder.instFieldAccesses(instAccesses.getFAList());

            StaticInstAnalysis sia = siaBuilder.build();
            ccBuilder.addStaticInstAnalysis(sia);
        }
        return ccBuilder.build();
    }

}