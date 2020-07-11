package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import de.upb.sede.util.FileUtil
import groovy.cli.Option
import groovy.cli.Unparsed
import groovy.cli.picocli.CliBuilder
import groovy.util.logging.Log

@Log
class SDLCli {


    private  static ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    CLIOptions opt
    SDLCompiler compiler

    def run() {
        Objects.requireNonNull(opt)
        readInputs()
        writeOutput()


    }

    def readInputs() {
        def inputFiles = []
        if(opt.inputs() == null || opt.inputs().length == 0) {
            if(opt.remaining().isEmpty())
                throw new IllegalArgumentException("No input was provided.")
            else {
                opt.remaining().each {inputFiles += new File(it.toString())}
            }
        } else {
            inputFiles += Arrays.asList(opt.inputs())
        }

        compiler =  new SDLCompiler(inputFiles)
        compiler.compile()
    }

    def writeOutput() {
        if(opt.targetComponents()) {
            writeOutHASCOComponents();
        } else {
            writeSDLBase();
        }
    }


    File prepareOutput(String defaultOutputPath) {
        def outputFile = opt.output()
        if(outputFile == null) {
            outputFile = new File(defaultOutputPath)
        }
        FileUtil.prepareOutputFile(outputFile)
        return outputFile
    }

    def writeOutHASCOComponents() {
        def outputFile = prepareOutput("out.json")
        def components
        if(opt.services() != null && opt.services().length > 0) {
            def serviceQualifiers = Arrays.asList(opt.services())
            components = compiler.peekHASCOComponentsOfQualifiers(serviceQualifiers)
        } else {
            components = compiler.peekAllHASCOComponents()
        }
        MAPPER.writeValue(outputFile, components)
    }

    def writeSDLBase() {
        def outputFile = prepareOutput("out.servicedesc.json")
        def sdlBase = compiler.peekSDLBase()
        MAPPER.writeValue(outputFile, sdlBase)
    }


    static void main(String[] args) {
//        args= """
//            SEDE.core/servicedesc-dsl/src/main/servicedesc
//        """.trim().split()
//        log.info "Working directory: ${new File(".").absolutePath}"

        def cli = new CliBuilder(usage: 'java -jar sdlc.jar [option]')

        // parse and process parameters
        CLIOptions options = cli.parseFromSpec(CLIOptions, args)
        if (options.help()) {
            cli.usage()
            System.exit(0)
        }
        SDLCli sdlCli = new SDLCli(opt: options)
        try {
            sdlCli.run()
        } catch(Exception ex) {
            ex.printStackTrace()
            System.err.println(ex.message)
            System.exit(1)
        }
    }

    private static interface CLIOptions {

        @Option(shortName='h', description='display usage') Boolean help()

        @Option(numberOfArgumentsString = "+",
            shortName='i',
            valueSeparator = ',',
            description='input service description file paths. Separate by: ,')
        File[] inputs()

        @Option(numberOfArgumentsString = "1",
            shortName='o',
            description = "output service description file path")
        File output()

        @Option(shortName = 'c',
                longName =  "components",
                description = "outputs a HASCO component model")
        Boolean targetComponents();

        @Option(shortName = 's',
                valueSeparator = ',',
                longName = 'services',
                description = "white-list of services that are considered when targeting components. If empty all services are compiled. Separate by: ,")
        String[] services();

        @Unparsed(description = "positional parameters") List remaining()
    }
}
