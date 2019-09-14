package de.upb.sede;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.upb.sede.util.FileUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

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


    public static ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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

    public List<ServiceCollectionDesc> readFromFilePath(String serviceDescFilePath) {
        return this.read(FileUtil.readFileAsString(serviceDescFilePath));
    }


    public synchronized  List<ServiceCollectionDesc> read(String serviceDesc) {
        SDL collector = (SDL) shell.parse(serviceDesc);
        runSDL(collector);
        return collector.getResult();
    }

    public synchronized List<ServiceCollectionDesc> read(File serviceDesc) throws IOException {
        SDL collector = (SDL) shell.parse(serviceDesc);
        runSDL(collector);
        return collector.getResult();
    }

    private synchronized void runSDL(SDL sdl) {
        sdl.setBinding(binding);
        sdl.run();
        binding.getVariables().clear();

    }


}
