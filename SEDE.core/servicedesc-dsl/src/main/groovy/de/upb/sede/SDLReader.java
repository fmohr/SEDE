package de.upb.sede;


import de.upb.sede.util.*;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SDLReader {

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

    private GroovyShell shell;

    private final Map<String, MutableServiceCollectionDesc> database;

    public SDLReader() {
        this(new HashMap<>());
    }
    public SDLReader(Map<String, MutableServiceCollectionDesc> database) {
        shell = new GroovyShell(this.getClass().getClassLoader(), createConfig());
        this.database = database;
    }

    public static CompilerConfiguration createConfig() {
        CompilerConfiguration config = new CompilerConfiguration();

        ImportCustomizer importCustomizer = new ImportCustomizer();
        // TODO add implied imports
        importCustomizer.addStaticStars(Helpers.class.getName());
        config.addCompilationCustomizers(importCustomizer);

        config.setScriptBaseClass(SDL.class.getName());

        return config;
    }

    public void readFromFilePath(String serviceDescFilePath) {
        this.read(FileUtil.readFileAsString(serviceDescFilePath));
    }


    public void read(String serviceDesc, String fileName) {
        SDL collector = (SDL) shell.parse(serviceDesc, fileName);
        runSDL(collector);
    }

    public void read(String serviceDesc) {
        SDL collector = (SDL) shell.parse(serviceDesc);
        runSDL(collector);
    }

    public void read(File serviceDesc) throws IOException {
        SDL collector = (SDL) shell.parse(serviceDesc);
        runSDL(collector);
    }

    public void readClosure(@DelegatesTo(SDL.class) Closure closure)  {
        SDL sdl = new ClosureSDL(closure);
        runSDL(sdl);
    }

    private synchronized void runSDL(SDL sdl) {
        if(database != null)
            sdl.setCols(database);

        sdl.run();
    }

    public void clearDatabase() {
        database.clear();
    }

    public List<ServiceCollectionDesc> getCollections() {
        return database.values()
            .stream()
            .map( it ->  SDLUtil.toImmutable(it, ServiceCollectionDesc.class))
            .collect(Collectors.toList());
    }

    public  List<MutableServiceCollectionDesc> getMutableCollections() {
        return new ArrayList<>(database.values());
    }

}
