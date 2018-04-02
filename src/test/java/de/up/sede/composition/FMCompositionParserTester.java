package de.up.sede.composition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import de.upb.sede.exceptions.FMCompositionSyntaxException;

/**
 * 
 * @author aminfaez
 *
 * Tests regex patterns in FMCompositionParser.
 */
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
    
    @Test public void test_separateInputs() {
		String inputs = "i1=1,i2=\"a\",i3=true,i4=field1";
		assertSeparatParamsEqual(inputs, false, "1", "\"a\"", "true", "field1");
		

		inputs = "1,\"a\",true,field1";
		assertSeparatParamsEqual(inputs, false, "1", "\"a\"", "true", "field1");

		inputs = "1,\"a\",i3=true,i4=field1";
		assertSeparatParamsEqual(inputs, false, "1", "\"a\"", "true", "field1");

		inputs = "i1=true,i1=field1";
		assertSeparatParamsEqual(inputs, true);

		inputs = "i1=a,b";
		assertSeparatParamsEqual(inputs, true);

		inputs = "i1=a,b";
		assertSeparatParamsEqual(inputs, true);

		inputs = "";
		assertSeparatParamsEqual(inputs, false);
		
		inputs = null;
		assertSeparatParamsEqual(inputs, false);
		
    }
    private void assertSeparatParamsEqual(String inputString, boolean expectedException, String... expectedParam) {
    		List<String> params;
    		try {
    			params = FMCompositionParser.separateInputs(inputString);
    		} catch (FMCompositionSyntaxException e){
    			Assert.assertEquals(true, expectedException);
    			return;
    		}
		Assert.assertEquals(false, expectedException);
		Assert.assertEquals(expectedParam.length, params.size());
		for(int i =0; i < expectedParam.length; i ++) {
			Assert.assertEquals(expectedParam[i], params.get(i));
		}
		
    }


    @Test public void testRegexFieldname() {
    		Pattern p = FMCompositionParser.PATTERN_fieldname;
    		assertMatches("a", p, true);
    		assertMatches("abc", p, true);
    		assertMatches("a10", p, true);
    		assertMatches("a1a", p, true);
    		assertMatches("_", p, true);
    		assertMatches("_a", p, true);
    		assertMatches("_1", p, true);


    		assertMatches("1", p, false);
    		assertMatches("1a", p, false);
    		assertMatches("1_", p, false);

    		assertMatches("a.", p, false);
    		assertMatches("_;", p, false);
    		assertMatches("", p, false);
    }
    
    @Test public void testRegexIpaddress() {
		Pattern p = FMCompositionParser.PATTERN_ipaddress_port;
		assertMatches("1.1.1.1:1", p, true);
		assertMatches("255.255.255.255:" + (1 << 16), p, true); // port has 16 bit.
		assertMatches("0.0.0.0:0013", p, true);

		assertMatches("1.1.1:1", p, false);
		assertMatches("1.1.1.1:", p, false);
		assertMatches("a.b.c.d:1", p, false);
		assertMatches("abcd:1", p, false);
		assertMatches("1.1.1.1:a", p, false);
		assertMatches("example.com:1", p, false);
		assertMatches("", p, false);
		assertMatches("1.1,1.1:10", p, false);
		assertMatches("1.1.1.1:100000", p, false);
    }
    
    @Test public void testRegexDomainname() {
		Pattern p = FMCompositionParser.PATTERN_domainname_port;
		assertMatches("example.com:1", p, true);
		assertMatches("abc.de:" + (1 << 16), p, true); // port has 16 bit.
		assertMatches("aa:1", p, true);
		assertMatches("a-a.com:1", p, true);
		assertMatches("a.a.com:1", p, true);
		assertMatches("a1.com:1", p, true);
		assertMatches("localhost:5000", p, true);

		assertMatches("-a.com:1", p, false);
		assertMatches("1a.com:1", p, false);

		assertMatches("", p, false);
		assertMatches("a:", p, false);
		assertMatches("a:1", p, false);
		assertMatches(":1", p, false);
		assertMatches("a,com:1", p, false);
		assertMatches("l:a", p, false);
    }
    
    @Test public void testRegexClasspath() {
    		Pattern p = FMCompositionParser.PATTERN_classpath;
    		assertMatches("java.util.Map", p, true);
    		assertMatches("a.b", p, true);
    		assertMatches("a.b1._c.d20.e_12._f.g1.a1h.a13i.Abc", p, true);
    		assertMatches("ABC.ED.ae._1", p, true);
    		assertMatches("_._._", p, true);
    		

    		assertMatches("ABC", p, false);
    		assertMatches("abc.1", p, false);
    		assertMatches("abcdef:g", p, false);
    		assertMatches("abcdefg:", p, false);
    		assertMatches(":abc.defg", p, false);
    		assertMatches("abc.defg-", p, false);
    		assertMatches("abc:a1", p, false);
    }
    
    @Test public void testRegexConstsBool() {
		Pattern p = FMCompositionParser.PATTERN_constBool;
		assertMatches("true", 	p, true);
		assertMatches("True", 	p, true);
		assertMatches("TRUE", 	p, true);
		assertMatches("TrUe", 	p, true);
		assertMatches("false", 	p, true);
		assertMatches("False",	p, true);
		assertMatches("FALSE", 	p, true);
		assertMatches("FaLsE", 	p, true);
		

		assertMatches("", 		p, false);
		assertMatches("0", 		p, false);
		assertMatches("abc", 		p, false);
		assertMatches("tru", 		p, false);
		assertMatches("fals", 		p, false);
		assertMatches("ABCD", 		p, false);
		assertMatches("truefalse", 		p, false);
		assertMatches("_true", 		p, false);
		assertMatches("false_", 		p, false);
    }

    @Test public void testRegexConstsNumber() {
		Pattern p = FMCompositionParser.PATTERN_constNumber;
		assertMatches("+1", 				p, true);
		assertMatches("-1", 				p, true);
		assertMatches("1", 				p, true);
		assertMatches("01", 				p, true);
		assertMatches("1234567890", 		p, true);
		assertMatches("1.11", 			p, true);
		assertMatches("1.0", 			p, true);
		assertMatches(".2", 				p, true);
		assertMatches("0.0123456789",	p, true);
		assertMatches("1.1e01", 		p, true);
		assertMatches("1.51e10", 		p, true);
		assertMatches("1.01E3", 			p, true);
		assertMatches("+1.01E-3", 			p, true);
		

		assertMatches("", 		p, false);
		assertMatches("+", 		p, false);
		assertMatches("_", 		p, false);
		assertMatches(",", 		p, false);
		assertMatches("1.", 		p, false);
		assertMatches("1,0", 		p, false);
		assertMatches("1.0.", 		p, false);
		assertMatches(".1.", 		p, false);
		assertMatches("abc1def", 		p, false);
		assertMatches("1e", 		p, false);
		assertMatches("1e1e", 		p, false);
    }
    

    @Test public void testRegexConstsString() {
		Pattern p = FMCompositionParser.PATTERN_constString;
		assertMatches("\"hallo\"", p, true);
		assertMatches("\"one space\"", p, true);
		assertMatches("\" spacestarting\"", p, true);
		assertMatches("\"spaceending \"", p, true);
		assertMatches("\"\"", p, true);
		assertMatches("\"123\"", p, true);
		assertMatches("\".;'[#£!({>-_,\"\"", p, true);
		assertMatches("\"quote \"in\" quote\"", p, true);
		assertMatches("\"\"\"", p, true);
		

		assertMatches("", 		p, false);
		assertMatches("\"", 		p, false);
		assertMatches("\"abc", 		p, false);
		assertMatches("\"abc\"a", 		p, false);
    }


    @Test public void testRegexConstsNull() {
		Pattern p = FMCompositionParser.PATTERN_constNull;
		assertMatches("null", p, true);
		assertMatches("NULL", p, true);
		assertMatches("NuLL", p, true);
		

		assertMatches("", 		p, false);
		assertMatches("_", 		p, false);
		assertMatches("nul", 		p, false);
		assertMatches("nulll", 		p, false);
		assertMatches("0", 		p, false);
		assertMatches("1", 		p, false);
    }

    @Test public void testRegexConsts() {
		Pattern p = FMCompositionParser.PATTERN_const;
		assertMatches("null", p, true);
		assertMatches("1", p, true);
		assertMatches("\"a\"", p, true);
		assertMatches("true", p, true);
		

		assertMatches("", 		p, false);
		assertMatches("_", 		p, false);
		assertMatches("1a", 		p, false);
		assertMatches("\"1", 		p, false);
		assertMatches("null1", 		p, false);
		assertMatches("truenull", 		p, false);
    }
    
    
    @Test public void testRegexInstruction() {
//    		System.out.println(FMCompositionParser.REGEX_instruction);
    		/* positive tests */
    		assertMatchEquals("a=localhost:10/s1::method1({i1=i1})", true, "a", "localhost:10", "s1", "method1", "i1=i1");
		assertMatchEquals("localhost:10/s1::method1({i1=i1})", true, null, "localhost:10", "s1", "method1", "i1=i1");
		
    		assertMatchEquals("a=s1::method1({i1=i1})", true, "a", null, "s1", "method1", "i1=i1");
    		assertMatchEquals("s1::method1({i1=i1})", true, null, null, "s1", "method1", "i1=i1");
    	
		assertMatchEquals("localhost:10/s1::method1()", true, null, "localhost:10", "s1", "method1", null);
    		assertMatchEquals("a=s1::method1()", true, "a", null, "s1", "method1", null);
    		assertMatchEquals("s1::method1()", true, null, null, "s1", "method1", null);
    	
    		assertMatchEquals("a=localhost:10/package1.Class42::method1({i1=i1})", true, "a", "localhost:10", "package1.Class42", "method1", "i1=i1");
    		assertMatchEquals("localhost:10/package1.Class42::method1({i1=i1})", true, null, "localhost:10", "package1.Class42", "method1", "i1=i1");
    		
        	assertMatchEquals("a=package1.Class42::method1({i1=i1})", true, "a", null, "package1.Class42", "method1", "i1=i1");
        	assertMatchEquals("package1.Class42::method1({i1=i1})", true, null, null, "package1.Class42", "method1", "i1=i1");
        	
    		assertMatchEquals("localhost:10/package1.Class42::method1()", true, null, "localhost:10", "package1.Class42", "method1", null);
        	assertMatchEquals("a=package1.Class42::method1()", true, "a", null, "package1.Class42", "method1", null);
        	assertMatchEquals("package1.Class42::method1()", true, null, null, "package1.Class42", "method1", null);
        	
        	
        	/* negative tests */
    		assertMatchEquals("", false, null, null, null, null, null);
    		assertMatchEquals("abcdefg", false, null, null, null, null, null);
    		assertMatchEquals("01234", false, null, null, null, null, null);
    		
    		assertMatchEquals("01234", false, null, null, null, null, null);

    		assertMatchEquals("a=localhost:10/s1:method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("a=localhost:10/method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("=localhost:10/s1::method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("a=localhost/s1::method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("a=localhost:10s1::method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("method1({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("s1::({i1=i1})", false, null, null, null, null, null);
    		assertMatchEquals("s1::method({)", false, null, null, null, null, null);
    		assertMatchEquals("s1::method({})", false, null, null, null, null, null);
    		assertMatchEquals("s1::method(})", false, null, null, null, null, null);
    		assertMatchEquals("s1::method({i1=1;w=1})", false, null, null, null, null, null);
    }
    

    
    
    private void assertMatches(String text, Pattern pattern, boolean expectedToMatch) {
    		Assert.assertEquals(expectedToMatch, pattern.matcher(text).matches());
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
    /**
     * For debugging.
     */
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
