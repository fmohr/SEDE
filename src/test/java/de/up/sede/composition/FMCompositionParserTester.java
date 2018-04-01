package de.up.sede.composition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * Tests the functionality to convert FMCompositions to GraphCompositions.
 */
import org.junit.Test;

import de.upb.sede.composition.FMComposition;
import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.GraphComposition;


public class FMCompositionParserTester {
    
    private static String compositionString;

    @BeforeClass public static void setupComposition() throws IOException{
        
        String compositionFile = "testrsc/composition.txt";
        compositionString = "";
        try (Stream<String> stream = Files.lines(Paths.get(compositionFile))) {
            compositionString = stream.collect(Collectors.joining());
        }
    }
    
    @Test public void test_separateInstructions() {
    		List<String> lines= FMCompositionParser.separateInstructions(compositionString);
    		Assert.assertEquals(4, lines.size());
    		String firstLine = "s1=127.0.0.1:8000/"
    				+ "Catalano.Imaging.Filters.Crop::"
    				+ "__construct({i1=0,i2=0,i3=10,i4=10})";
    		Assert.assertEquals(firstLine, lines.get(0));
    }

    @Test public void testRegexFieldname() {
    		
    }
    
    @Test public void testRegexInstruction() {
    		String instruction = "sa/s1::ApplyInPlace({i1=i1})";
    		
        	assertMatchEquals("sa/s1::ApplyInPlace({i1=i1})", false, null, null, null, null, null);

    }
    
    private void assertMatchEquals(String instruction, 
	    		boolean exptectedToMatch, String expectedLeftside, 
	    		String expectedHost, String expectedContext,
	    		String expectedMethod, String expectedInputs) {
    		
    		Matcher iMatcher = FMCompositionParser.PATTERN_instruction.matcher(instruction);
		Assert.assertEquals(exptectedToMatch, iMatcher.matches());
    		
		if(exptectedToMatch) {
			assertEquals(expectedLeftside, iMatcher.group("leftside"));
			assertEquals(expectedHost, iMatcher.group("host"));
			assertEquals(expectedContext, iMatcher.group("context"));
			assertEquals(expectedMethod, iMatcher.group("method"));
			assertEquals(expectedInputs, iMatcher.group("inputs"));
		}
    }	
    private void printMatch(String instruction) {
    	Matcher iMatcher = FMCompositionParser.PATTERN_instruction.matcher(instruction);
		if(iMatcher.matches()) {
			System.out.println("Matched " + instruction +":");
			System.out.println("\tleftside: " 	+ iMatcher.group("leftside"));
			System.out.println("\thost: " 		+ iMatcher.group("host"));
			System.out.println("\tcontext: " 	+ iMatcher.group("context"));
			System.out.println("\tmethod: " 	+ iMatcher.group("method"));
			System.out.println("\tinputs: " 	+ iMatcher.group("inputs"));
		} else {
			System.out.println("No match " + instruction);
		}
    }
}
