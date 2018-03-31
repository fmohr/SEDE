package de.up.sede.composition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import static org.junit.Assert.assertTrue;

/*
 * Tests the functionality to convert FMCompositions to GraphCompositions.
 */
import org.junit.Test;

import de.upb.sede.composition.FMComposition;
import de.upb.sede.composition.GraphComposition;
import de.upb.sede.composition.graphcreators.GraphCreator;


public class GraphCreationTester {
    
    private static String compositionString;

    @BeforeClass public static void setupComposition() throws IOException{
        
        String compositionFile = "testrsc/composition.txt";
        compositionString = "";
        try (Stream<String> stream = Files.lines(Paths.get(compositionFile))) {
            compositionString = stream.collect(Collectors.joining());
        }
    }

    @Test public void testCreation() {
        GraphCreator creator = new GraphCreator(compositionString);
        creator.createRawGraph();
    }
}
