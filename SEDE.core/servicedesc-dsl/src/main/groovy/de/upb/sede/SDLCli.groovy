package de.upb.sede

import groovy.cli.Option
import groovy.cli.Unparsed
import groovy.cli.commons.CliBuilder
import groovy.util.logging.Log

@Log
class SDLCli {


    CLIOptions opt

    def run() {
        Objects.requireNonNull(opt)
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
        def outputFiles = []
        if(opt.outputs() != null) {
            outputFiles += Arrays.asList(opt.outputs())
        }
        def compiler =  new SDLCompiler(inputFiles, outputFiles, opt.merge())
        compiler.compile()
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
            description='input service description file paths')
        File[] inputs()

        @Option(shortName='m',
            description= "merges the inputs into the a single output")
        Boolean merge()

        @Option(numberOfArgumentsString = "+",
            valueSeparator = ',',
            shortName='o',
            description = "output service description file paths")
        File[] outputs()

        @Unparsed(description = "positional parameters") List remaining()
    }
}
