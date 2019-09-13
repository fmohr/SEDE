package de.upb.sede;


import de.upb.sede.util.FileUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DescriptionReader {

        // TODO use script engine if there are dependencies between the service descriptions
//    private GroovyScriptEngine scriptEngine;
//    public DescriptionReader(String sourceDir) throws IOException {
//        this(Collections.singletonList(sourceDir));
//    }
//
//    public DescriptionReader(List<String> sources) throws IOException {
//        String[] sourcesArray = sources.toArray(new String[0]);
//
//        scriptEngine = new GroovyScriptEngine(sourcesArray);
//        scriptEngine.setConfig(createConfig());
//    }

    private Binding binding;
    private GroovyShell shell;

    public DescriptionReader() {
        binding = new Binding();
        shell = new GroovyShell(this.getClass().getClassLoader(), binding, createConfig());
    }

    public static CompilerConfiguration createConfig() {
        CompilerConfiguration config = new CompilerConfiguration();

        ImportCustomizer importCustomizer = new ImportCustomizer();
        // TODO add implied imports
        config.addCompilationCustomizers(importCustomizer);

        config.setScriptBaseClass(SDL.class.getName());

        return config;
    }

    public Map<String, ServiceCollectionDesc> readFromFilePath(String serviceDescFilePath) {
        return this.read(FileUtil.readFileAsString(serviceDescFilePath));
    }


    public Map<String, ServiceCollectionDesc> read(String serviceDesc) {
        SDL collector = (SDL) shell.parse(serviceDesc);
        return collector.getCols();
    }

    public Map<String, ServiceCollectionDesc> read(File serviceDesc) throws IOException {
        SDL collector = (SDL) shell.parse(serviceDesc);
        collector.run();
        return collector.getCols();
    }
}
