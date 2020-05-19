package de.upb.sede;

import de.upb.sede.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SDLCompiler {

    private final static Logger logger = LoggerFactory.getLogger(SDLCompiler.class);

    private List<File> inputFiles;

    private LayeredCache<Map<String, MutableServiceCollectionDesc>> collections = Cache.nullableDefaultCache(LinkedHashMap::new);

    public SDLCompiler(List<File> inputFiles) {
        this.inputFiles = new ArrayList<>(Objects.requireNonNull(inputFiles));
        if(inputFiles.isEmpty()) {
            throw new IllegalArgumentException("empty input list");
        }
    }

    public synchronized void compile() {
        while(!inputFiles.isEmpty()) {
            compileNext();
        }
    }

    private void compileNext() {
        File inputFile = nextInput();

        if(!inputFile.exists()) {
            throw new IllegalArgumentException("Input file or directory doesn't exist: " + inputFile);
        }
        if(!inputFile.canRead()) {
            throw new IllegalArgumentException("Input file or directory cannot be read."  + inputFile);
        }

        if(inputFile.isDirectory()) {
            compileDir(inputFile);
        } else {
            read(inputFile);
        }
    }

    private void compileDir(File inputDir) {
        for(String sDFileName : FileUtil.listAllFilesInDir(inputDir.getAbsolutePath(),
            "(.*?)\\.servicedesc.groovy$")) {
            File sDFile = new File(inputDir, sDFileName);
            read(sDFile);
        }
    }

    private void read(File inputFile) {
        if(!inputFile.getName().endsWith(".servicedesc.groovy")) {
            throw new IllegalArgumentException("Cant handle input-file: " + inputFile);
        }
        SDLReader reader = new SDLReader(collections.get());
        Uncheck.call(() -> {reader.read(inputFile); return null;});

    }

    private File nextInput() {
        return inputFiles.remove(0);
    }


    public ISDLAssembly popSDLBase()  {
        ISDLAssembly isdlAssembly = peekSDLBase();
        collections.set(null);
        return isdlAssembly;
    }

    public ISDLAssembly peekSDLBase() {
        Map<String, MutableServiceCollectionDesc> colMap = collections.get();
        List<MutableServiceCollectionDesc> colList = new ArrayList<>(colMap.values());
        SDLAssembly.Builder output = SDLAssembly.builder();

        colList.stream()
            .map(col -> DeepImmutableCopier.copyAsImmutable(col, ServiceCollectionDesc.class))
            .forEachOrdered(output::addCollections);

        return output.build();
    }

    // TODO maybe there is a better place for these:

    private SDLBaseLookupService currentLookupService() {
        ISDLAssembly isdlAssembly = peekSDLBase();
        SDLBaseLookupService lookupService = new SDLBaseLookupService(isdlAssembly);
        return lookupService;
    }

    public List<Map> peekHASCOComponentsOfQualifiers(List<String> serviceQualifiers) {
        SDLBaseLookupService lookupService = currentLookupService();
        List<Map> components = ToHASCOComponentsTranslator.componentsOfServiceQualifiers(lookupService, serviceQualifiers);
        return components;
    }

    public List<Map> peekAllHASCOComponents() {
        SDLBaseLookupService lookupService = currentLookupService();
        List<IServiceRef> serviceRefs = lookupService.allServiceRefs().collect(Collectors.toList());
        return ToHASCOComponentsTranslator.componentsOfServiceRefs(lookupService, serviceRefs);
    }

    private static String jsonExtension(String fileName) {
        return FileUtil.withExtension(fileName, ".json");
    }

}
