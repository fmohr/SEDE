package de.upb.sede;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.upb.sede.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SDLCompiler {

    private final static Logger logger = LoggerFactory.getLogger(SDLCompiler.class);

    private  static ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private boolean mergeInputs;

    private List<File> inputFiles;

    private List<File> outputFiles;

    private LayeredCache<Map<String, MutableServiceCollectionDesc>> collections = Cache.nullableDefaultCache(LinkedHashMap::new);

    public SDLCompiler(List<File> inputFiles, List<File> outputFiles, boolean mergeInputs) {
        this.inputFiles = new ArrayList<>(Objects.requireNonNull(inputFiles));
        if(inputFiles.isEmpty()) {
            throw new IllegalArgumentException("empty input list");
        }
        this.outputFiles = new ArrayList<>(Objects.requireNonNull(outputFiles));
        this.mergeInputs = mergeInputs;


        if(mergeInputs){
            if(outputFiles.isEmpty())
                throw new IllegalArgumentException("Merging inputs was requested but no output file was provided.");
            if(outputFiles.size() > 1) {
                throw new IllegalArgumentException("Merging inputs was requested but multiple output files were provided.");
            }
        } else {
            if(outputFiles.size() > 0 && outputFiles.size() != inputFiles.size()){
                throw new IllegalArgumentException("Unequal amount of input and output files were defined");
            }
        }
    }

    public synchronized void compile() {
        while(!inputFiles.isEmpty()) {
            compileNext();
        }
        if(mergeInputs) {
            dump(nextOutput());
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
            if(!mergeInputs)
                dump(nextOutput(inputFile));
        }
    }

    private void compileDir(File inputDir) {
        for(String sDFileName : FileUtil.listAllFilesInDir(inputDir.getAbsolutePath(),
            "(.*?)\\.servicedesc.groovy$")) {
            File sDFile = new File(inputDir, sDFileName);
            read(sDFile);
            if(!mergeInputs) {
                dump(nextOutputInDir(inputDir, sDFile));
            }
        }
    }

    private void read(File inputFile) {
        if(!inputFile.getName().endsWith(".servicedesc.groovy")) {
            throw new IllegalArgumentException("Unknown file ending: " + inputFile);
        }
        SDLReader reader = new SDLReader(collections.get());
        Uncheck.call(() -> {reader.read(inputFile); return null;});

    }

    private File nextInput() {
        return inputFiles.remove(0);
    }

    private File nextOutput() {
        if(outputFiles.isEmpty()) {
            throw new IllegalStateException("No output files defined.");
        }
        return outputFiles.remove(0);
    }

    private File nextOutput(File inputFile) {
        if(outputFiles.isEmpty()) {
            String outputFileName = jsonExtension(inputFile.getName());
            return new File(inputFile.getParent(), outputFileName);
        } else {
            return nextOutput();
        }
    }

    private File nextOutputInDir(File inputDir, File inputFile) {
        File outputDir;
        if(outputFiles.isEmpty()) {
            outputDir = inputDir;
        } else {
            outputDir = nextOutput();
            if(!outputDir.exists()) {
                outputDir.mkdirs();
            } else if(!outputDir.isDirectory()) {
                throw new IllegalArgumentException("Expected output directory, got: " + outputDir.getPath());
            }
        }
        return new File(outputDir, jsonExtension(inputFile.getName()));
    }


    private void dump(File outputFile)  {
        Map<String, MutableServiceCollectionDesc> colMap = collections.get();
        collections.set(null);
        FileUtil.prepareOutputFile(outputFile);
        List<MutableServiceCollectionDesc> colList = new ArrayList<>(colMap.values());
        List<ServiceCollectionDesc> output = colList.stream()
            .map(col -> SDLUtil.toImmutable(col, ServiceCollectionDesc.class))
            .collect(Collectors.toList());
        Uncheck.call(() -> { MAPPER.writeValue(outputFile, output); return null; });
    }

    private static String jsonExtension(String fileName) {
        return FileUtil.withExtension(fileName, ".json");
    }

}
