package de.upb.sede;

import ch.ifocusit.plantuml.classdiagram.ClassDiagramBuilder;
import ch.ifocusit.plantuml.classdiagram.model.attribute.ClassAttribute;
import ch.ifocusit.plantuml.classdiagram.model.method.ClassMethod;
import de.upb.sede.exec.*;
import de.upb.sede.types.DataTypeDesc;
import de.upb.sede.types.IDataTypeDesc;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Streams;
import org.apache.commons.io.FileUtils;

import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PlantUMLGen {

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            throw new IllegalArgumentException("Output dir missing..");
        }
        File outputDir = new File(args[0]);
        prepOutDir(outputDir);

        generateServiceDescOverview(outputDir);

    }

    private static void prepOutDir(File outDir) throws IOException {
        if(outDir.exists()) {
            if(!outDir.isDirectory()) {
                throw new RuntimeException("Output dir is a file: " + outDir.getAbsolutePath());
            }
            FileUtils.deleteDirectory(outDir);
        }
        else {
            boolean successMkdirs = outDir.mkdirs();
            if(!successMkdirs) {
                throw new RuntimeException("Couldn't create output directory: " + outDir);
            }
        }
    }

    private static void generateServiceDescOverview(File outDir) throws IOException {
        Class[] umlClasses = {
            ServiceCollectionDesc.class,
            ServiceDesc.class,
            MethodDesc.class,
            SignatureDesc.class,
            MethodParameterDesc.class,
            DataTypeDesc.class,

            IServiceCollectionDesc.class,
            IServiceDesc.class,
            IMethodDesc.class,
            ISignatureDesc.class,
            IMethodParameterDesc.class,
            IDataTypeDesc.class,

            IQualifiable.class,
            CommentAware.class
        };


        String diagram = new ClassDiagramBuilder()
            .addClasse(umlClasses)
            .withDependencies()
            .addMethodPredicate((ClassMethod method) -> {
                return false;
            })
            .addFieldPredicate((ClassAttribute field) -> {
                return true;
            })
            .build();
        File outFile = new File(outDir, "service-descs-overview.plantuml");
        FileUtils.write(outFile, diagram, StandardCharsets.UTF_8, false);
    }

}
