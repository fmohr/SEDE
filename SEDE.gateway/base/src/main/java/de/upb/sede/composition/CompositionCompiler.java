package de.upb.sede.composition;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.composition.faa.FAAInput;
import de.upb.sede.composition.faa.FAAOutput;
import de.upb.sede.composition.faa.FieldAccessAnalyser;
import de.upb.sede.composition.pio.PIOInput;
import de.upb.sede.composition.pio.PIOOutput;
import de.upb.sede.composition.pio.ProgramInstructionOrderer;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import de.upb.sede.composition.typing.TCInput;
import de.upb.sede.composition.typing.TCOutput;
import de.upb.sede.composition.typing.TypeChecker;

import java.util.List;
import java.util.stream.Collectors;

public class CompositionCompiler {

    private final ISDLLookupService isdlLookupService;

    private final StaticCompositionAnalysis.Builder scaBuilder = StaticCompositionAnalysis.builder();

    private static final CompileStep<TCInput, TCOutput> typeChecker = new TypeChecker();

    private static final CompileStep<FAAInput, FAAOutput> fieldAccessAnalyser = new FieldAccessAnalyser();

    private static final CompileStep<PIOInput, PIOOutput> programInstOrderer = new ProgramInstructionOrderer();

    public CompositionCompiler(ISDLLookupService isdlLookupService) {
        this.isdlLookupService = isdlLookupService;
    }

    public void compileCode(String fmCompositionCode) {
        List<IInstructionNode> instructionsCode;
        instructionsCode = FMCompositionParser.separateInstructions(fmCompositionCode).stream()
            .map(FMCompositionParser::parseInstruction)
            .collect(Collectors.toList());
        compileInstructionList(instructionsCode);
    }

    public void compileInstructionList(List<IInstructionNode> instructions) {
        /*
         * Index all the instructions:
         */
        InstructionIndexer instIndexer = new InstructionIndexer(instructions);

        /*
         * Type check instructions
         */
        TCInput tcInput = new TCInput(isdlLookupService, instIndexer);
        TCOutput tcOutput = typeChecker.step(tcInput);

        /*
         * Analyse field accesses
         */
        FAAInput faaInput = new FAAInput(tcOutput, instIndexer);
        FAAOutput faaOutput = fieldAccessAnalyser.step(faaInput);

        /*
         * Reorder Instruction and create a program instruction order
         */
        PIOInput pioInput = new PIOInput(instIndexer);
        PIOOutput pioOutput = programInstOrderer.step(pioInput);

        compose(instIndexer, tcOutput, faaOutput, pioOutput);
    }

    private void compose(InstructionIndexer instructions,
                                     TCOutput tcOutput,
                                     FAAOutput faaOutput,
                                     PIOOutput pioOutput) {
        scaBuilder.programOrder(pioOutput.getProgramOrder());

        for(IIndexedInstruction ii : instructions) {
            StaticInstAnalysis.Builder siaBuilder = StaticInstAnalysis.builder();
            siaBuilder.instruction(ii);

            List<TypeJournalPageModel> typeJournalPageModel = tcOutput
                .getJournal()
                .getPageAfterInst(ii.getIndex())
                .getModel();
            siaBuilder.typeContext(typeJournalPageModel);
            siaBuilder.methodCognition(tcOutput.getMethodCognitionMap().get(ii.getIndex()));

            siaBuilder.addAllFieldAccesses(faaOutput.getFAList());

            StaticInstAnalysis sia = siaBuilder.build();
            scaBuilder.addInstructionAnalysis(sia);
        }
    }

    public StaticCompositionAnalysis getStaticCompositionAnalysis() {
        return scaBuilder.build();
    }

}
