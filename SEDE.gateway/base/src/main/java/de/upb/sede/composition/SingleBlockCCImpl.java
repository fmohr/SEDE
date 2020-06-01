package de.upb.sede.composition;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.faa.IFACInput;
import de.upb.sede.composition.faa.IFACOutput;
import de.upb.sede.composition.faa.InstructionFieldAccessCollector;
import de.upb.sede.composition.faa.FieldAnalysisCollector;
import de.upb.sede.composition.pio.PIOInput;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.pio.ProgramInstructionOrderer;
import de.upb.sede.composition.typing.*;

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
        InstructionIndexer instIndexer = new InstructionIndexer(instructions);
        instIndexer.assertNotEmpty();

        /*
         * Type check instructions
         */
        TypeChecker typeChecker = new TypeChecker();
        TCInput tcInput = new TCInput(SDLLookupService, instIndexer, currentTypeContext);
        typeChecker.setInput(tcInput);
        typeChecker.stepToEnd();
        Map<Long, TCOutput> tcOutput = typeChecker.getOutput().getFinalOutput();

        /*
         * Analyse field accesses
         */
        IFACInput IFACInput = new IFACInput(tcOutput, instIndexer);

        InstructionFieldAccessCollector fieldAccessAnalyser = new InstructionFieldAccessCollector();
        fieldAccessAnalyser.setInput(IFACInput);
        fieldAccessAnalyser.stepToEnd();
        Map<Long, IFACOutput> faaOutput = fieldAccessAnalyser.getOutput().getFinalOutput();

        /*
         * Create a collection of field accesses from their point of view.
         */
        FieldAnalysisCollector collector = new FieldAnalysisCollector();
        collector.setInput(new FieldAnalysisCollector.FACInput(instIndexer, currentTypeContext, tcOutput, faaOutput));
        collector.stepToEnd();
        Map<String, IFieldAnalysis> facOutput = collector.getOutput().getFieldAnalysis();

        /*
         * Reorder Instruction and create a program instruction order
         */
        PIOInput pioInput = new PIOInput(instIndexer);
        ProgramInstructionOrderer orderer = new ProgramInstructionOrderer();
        orderer.setInput(pioInput);
        orderer.stepToEnd();
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
        ccBuilder.fieldAccesses(facOutput.values());
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
