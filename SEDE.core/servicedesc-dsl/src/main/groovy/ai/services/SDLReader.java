package ai.services;


import ai.services.util.DeepImmutableCopier;
import ai.services.util.FileUtil;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    static CompilerConfiguration createConfig() {
        CompilerConfiguration config = new CompilerConfiguration();

        ImportCustomizer importCustomizer = new ImportCustomizer();
        // TODO add implied imports
        importCustomizer.addStaticStars(Helpers.class.getName());
        config.addCompilationCustomizers(importCustomizer);

        config.setScriptBaseClass(SDL.class.getName());

        return config;
    }

    public static ISDLAssembly assemble(@DelegatesTo(SDL.class) Closure sdlScript) {
        SDLReader reader = new SDLReader();
        reader.readClosure(sdlScript);
        return reader.getSDLAssembly();
    }

    public void readFromFilePath(String serviceDescFilePath) {
        this.read(FileUtil.readFileAsString(serviceDescFilePath));
    }


    public void read(String serviceDesc, String fileName) {
        SDL collector = (SDL) shell.parse(serviceDesc, fileName);
        runSDL(collector);
    }

    public void readResource(String resourcePath) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if(resource == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        URI uri;
        try {
            uri = resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't create URI from resource path url: " + resource.toString(), e);
        }
        SDL collector = (SDL)  shell.parse(uri);
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
        sdl.setWriteCollectionAsJson(false);
        sdl.run();
    }

    public void clearDatabase() {
        database.clear();
    }

    public List<IServiceCollectionDesc> getCollections() {
        return database.values()
            .stream()
            .map( it -> DeepImmutableCopier.copyAsImmutable(it, IServiceCollectionDesc.class))
            .collect(Collectors.toList());
    }

    public ISDLAssembly getSDLAssembly() {
        return SDLAssembly.builder().collections(getCollections()).build();
    }

    public ISDLAssembly getMutableSDLBase() {
        return MutableSDLAssembly.create().setCollections(getMutableCollections());
    }

    public  List<MutableServiceCollectionDesc> getMutableCollections() {
        return new ArrayList<>(database.values());
    }

}
