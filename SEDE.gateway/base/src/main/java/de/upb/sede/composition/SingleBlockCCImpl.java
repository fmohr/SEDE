package de.upb.sede.composition;

import de.upb.sede.SDLLookupService;
import de.upb.sede.composition.faa.FAAInput;
import de.upb.sede.composition.faa.FAAOutput;
import de.upb.sede.composition.faa.FieldAccessAnalyser;
import de.upb.sede.composition.pio.PIOInput;
import de.upb.sede.composition.pio.PIOOutput;
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

    public List<IFieldType> getCurrentTypeContext() {
        return currentTypeContext;
    }

    public CompositionCompilation compileCode(String fmCompositionCode) {
        List<IInstructionNode> instructionsCode;
        instructionsCode = FMCompositionParser.separateInstructions(fmCompositionCode).stream()
            .map(FMCompositionParser::parseInstruction)
            .collect(Collectors.toList());
        return compileInstructionBlock(instructionsCode);
    }

    public CompositionCompilation compileInstructionBlock(List<IInstructionNode> instructions) {
        /*
         * Index all the instructions:
         */
        InstructionIndexer instIndexer = new InstructionIndexer(instructions);

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
        FAAInput faaInput = new FAAInput(tcOutput, instIndexer);

        FieldAccessAnalyser fieldAccessAnalyser = new FieldAccessAnalyser();
        fieldAccessAnalyser.setInput(faaInput);
        fieldAccessAnalyser.stepToEnd();
        Map<Long, FAAOutput> faaOutput = fieldAccessAnalyser.getOutput().getFinalOutput();

        /*
         * Reorder Instruction and create a program instruction order
         */
        PIOInput pioInput = new PIOInput(instIndexer);
        ProgramInstructionOrderer orderer = new ProgramInstructionOrderer();
        orderer.setInput(pioInput);
        orderer.stepToEnd();
        PIOOutput pioOutput = orderer.getOutput();

        CompositionCompilation compose = compose(instIndexer, tcOutput, faaOutput, pioOutput);
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

    private CompositionCompilation compose(InstructionIndexer instructions,
                                           Map<Long, TCOutput> tcOutput,
                                           Map<Long, FAAOutput> faaOutput,
                                           PIOOutput pioOutput) {
        CompositionCompilation.Builder ccBuilder = CompositionCompilation.builder();
        ccBuilder.programOrder(pioOutput.getProgramOrder());
        for(IIndexedInstruction ii : instructions) {
            StaticInstAnalysis.Builder siaBuilder = StaticInstAnalysis.builder();
            siaBuilder.instruction(ii);
            TCOutput instTC = tcOutput.get(ii.getIndex());
            List<IFieldType> fieldTypes = new ArrayList<>();
            instTC.getFieldTC().extractInto(fieldTypes);
            siaBuilder.typeContext(fieldTypes);

            MethodResolution.Builder mrBuilder = MethodResolution.builder();
            mrBuilder.addAllParamTypeCoercions(instTC.getMethodInfo().getParameterTypeCoercions());
            mrBuilder.methodRef(instTC.getMethodInfo().getMethodRef());

            // TODO set method resoltuion
            siaBuilder.methodResolution(mrBuilder.build());
            FAAOutput instAccesses = faaOutput.get(ii.getIndex());
            siaBuilder.fieldAccesses(instAccesses.getFAList());

            StaticInstAnalysis sia = siaBuilder.build();
            ccBuilder.addInstructionAnalysis(sia);
        }
        return ccBuilder.build();
    }

}
