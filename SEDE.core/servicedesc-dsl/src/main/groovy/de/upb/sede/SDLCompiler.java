package de.upb.sede;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.upb.sede.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SDLCompiler {

    private  static ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        // TODO parse compiler options
        if(args.length!=1) {
            exit("Provide compile file or directory.");
        }

        String input = args[0];
        File inputFile = new File(input);
        if(!inputFile.exists()) {
            exit("Input file or directory doesn't exist.");
        }
        if(!inputFile.canRead()) {
            exit("Input file or directory cannot be read.");
        }

        if(inputFile.isDirectory()) {
            File inputDir = inputFile;
            for(String sDFileName : FileUtil.listAllFilesInDir(inputDir.getAbsolutePath(), "(.*?)\\.servicedesc.groovy$")) {
                String outputFileName = renameEnding(sDFileName, ".groovy", ".json");
                File sDFile = new File(inputDir, sDFileName);
                File outputFile = new File(inputDir, outputFileName);
                try {
                    compileGroovyToJson(sDFile, outputFile);
                } catch(Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }
        } else {
            String inputFileName = inputFile.getName();
            if(!inputFileName.endsWith(".servicedesc.groovy")) {
                exit("Unrecognized input file ending: " + inputFileName);
            }
            String outputFileName = renameEnding(inputFileName, ".groovy", ".json");
            File outputFile = new File(inputFile.getParent(), outputFileName);

            try {
                compileGroovyToJson(inputFile, outputFile);
            } catch(Exception ex) {
                ex.printStackTrace(System.err);
                exit(ex.getMessage());
            }
        }
    }

    private static String renameEnding(String fileName, String oldEnding, String newEnding) {
        return fileName.substring(0, fileName.length() - oldEnding.length()) + newEnding;
    }



    public static void compileGroovyToJson(File sDFile, File outputFile) throws IOException {
        DescriptionReader reader = new DescriptionReader();
        List<ServiceCollectionDesc> collections = reader.read(sDFile);
        MAPPER.writeValue(outputFile, collections);
    }


    public static void exit(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

}
