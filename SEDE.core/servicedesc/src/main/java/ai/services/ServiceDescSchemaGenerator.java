package ai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import ai.services.util.FileUtil;

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
            FileUtil.prepareOutputFile(schemaFile);
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

}
