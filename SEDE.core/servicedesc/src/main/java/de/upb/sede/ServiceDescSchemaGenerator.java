package de.upb.sede;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import de.upb.sede.util.FileUtil;

import java.io.File;

/**
 * Generates the Json Schema v3 of the ServiceCollectionDesc class using JsonSchemaGenerator from jackson-module-jsonSchema.
 */
public class ServiceDescSchemaGenerator {

    public static void main(String[] args) throws JsonProcessingException {
        if(args.length != 1) {
            System.err.println("Provide the output schema file path as the first and only argument.");
            System.exit(1);
        }
        String filePath = args[0];
        File schemaFile = new File(filePath);
        try {
            prepareOutputFile(schemaFile);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema schema = schemaGen.generateSchema(IServiceCollectionDesc.class);
        /*
         *  TODO Json Object properties in the schema are not marked as required.
         *  Fields that aren't annotated as Nullable should be marked required.
         */
        String schemaString = mapper.writeValueAsString(schema);
        FileUtil.writeStringToFile(schemaFile.getPath(), schemaString);
    }

    /**
     * Makes sure the output file is writable by creating its parent directories if necessary and checking access rights.
     *
     * Throws an IllegalArgumentException if:
     *  - Output file or its parent directories doesn't exist and cannot be created.
     *  - Output file exists but isn't writable. (access rights)
     *
     * @param outputFile file which will be prepared to write to
     *
     * @exception IllegalArgumentException if the output file isn't writable..
     */
    public static void prepareOutputFile(File outputFile) throws IllegalArgumentException {
        // TODO extract and rename this utility function into FileUtil.

        if(!outputFile.exists()) {
            /*
             * Output file doesnt exist. make sure its parent directory exists.
             */
            File outputDir = new File(outputFile.getParent());
            if(!outputDir.exists()) {
                boolean success = outputDir.mkdirs();
                if(!success) {
                    throw new IllegalArgumentException("Cannot create the output directory: " + outputDir.getPath());
                }

            }
        } else if(!outputFile.canWrite()) {
            throw new IllegalArgumentException("Cannot write onto " + outputFile);
        }
    }

}
