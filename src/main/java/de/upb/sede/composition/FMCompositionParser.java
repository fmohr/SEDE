package de.upb.sede.composition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.sede.composition.graphs.InstructionNode;


/**
 * @author aminfaez
 * 
 * Defines a set of regex-patterns that parse fmCompositions.
 *
 */
public final class FMCompositionParser {
	
	/**
	 * Empty private constructor in order to prevent instantiation.
	 */
	private FMCompositionParser() {}
    
	/*
	 * All regex patterns are applied to fmCompositions. 
	 */
	
	/** Regex used to separate instructions */
    public final static String 	REGEX_lineSeperator = "[\\s]*;[\\s]*";

    /** Regex for a field name. */
    public final static String 	REGEX_fieldname = "(?:[_a-zA-Z]\\w*+)";
    public final static Pattern 	PATTERN_fieldname = Pattern.compile(REGEX_fieldname);

    /** Regex for class path. */
    public final static String 	REGEX_classpath = "(?:(?:" 
                                                    + REGEX_fieldname + "\\.)++(?:"
                                                    + REGEX_fieldname + "))";
    public final static Pattern 	PATTERN_classpath = Pattern.compile(REGEX_classpath);
    
    /** Regex for ip address concatenated with port. e.g.: 110.120.130.140:1000 */
    public final static String 	REGEX_ipaddress_port = "(?:(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
                                                    + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
                                                    + ":\\d{1,5})";
    public final static Pattern 	PATTERN_ipaddress_port = Pattern.compile(REGEX_ipaddress_port);

    /** Regex for domain names concatenated with port. e.g.: isys.db.upb.de:8000 */
    public final static String 	REGEX_domainname_port = "(?:(?:[a-zA-Z](?:\\.|\\w|-)++):\\d{1,5})";
    public final static Pattern 	PATTERN_domainname_port = Pattern.compile(REGEX_domainname_port);

    /** 
     *  Regex for a complete instruction. Defines named groups: 'leftside', 'host', 'context', 'method', 'inputs'.
     *  
     *  e.g.:
     *  
     *  		"s1=127.0.0.1:8000/Catalano.Imaging.Filters.Crop::__construct({i1=0,i2=0,i3=10,i4=10})" matches.
     *  
     *  Group values in this example:
     *  		leftside = "s1"
     *  		host = "127.0.0.1:8000"
     *  		context = "Catalano.Imaging.Filters.Crop"
     *  		method = "__construct"
     *  		inputs = "i1=0,i2=0,i3=10,i4=10"
    	 */
    public final static String 	REGEX_instruction = "^"
    													+ "(?:(?<leftside>" + REGEX_fieldname + ")=){0,1}" 
    													+ "(?:(?<host>" + REGEX_ipaddress_port + "|" + REGEX_domainname_port + ")/){0,1}"
    													+ "(?<context>" + REGEX_classpath + "|" + REGEX_fieldname + ")::"
    													+ "(?<method>" + REGEX_fieldname + ")"
    													+ "\\((?:\\{(?<inputs>(?:\\w|,|=)++)\\}){0,1}\\)"
    													+ "$";
    public final static Pattern 	PATTERN_instruction = Pattern.compile(REGEX_instruction);

    /**
     * Returns list of instruction from the given fmComposition.
     * In fmCompositions instructions are separated by ';'.  
     * Each instruction is trimmed and its whitespaces are removed.
     * Empty instructions are ignored. 
     * 
     * Example:
     * 		separateInstructions("a;b;;c;d") -> ["a", "b", "c", "d"]
     */
    public static List<String> separateInstructions(final String fmComposition){
    		List<String> compositionLines = new ArrayList<>();
        /* split by ';' into multiple lines */
        String[] lines = fmComposition.split(REGEX_lineSeperator);
        /* can't create a graph from a empty composition. */
        if(lines.length == 0) {
        		throw new FMCompositionParseException("Empty composition: " + fmComposition + ".");
        }
        /* add every non empty line to the compositionLines list. */
        for(String line : lines) {
        		/* remove whitespaces */
        	 	line = line.trim();
        	 	line = line.replaceAll("\\s", "");
        	 	if(!line.isEmpty()) {
        	 		compositionLines.add(line);
        	 	}
        }
        return compositionLines;
    }
    
    public static InstructionNode parseInstruction(final String instruction) {
    		Matcher instructionMatcher = PATTERN_instruction.matcher(instruction);
    		if(instructionMatcher.matches()) {
        		InstructionNode iNode = new InstructionNode(instruction);
        		String leftsideFieldname = instructionMatcher.group("leftsidefield");
        		iNode.setLeftSideFieldname(leftsideFieldname);
        		return iNode;
    		}
    		else {
    			throw new FMCompositionParseException(instruction, REGEX_instruction);
    		}
    }
}
class FMCompositionParseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	FMCompositionParseException(String text, String regex){
		super(String.format("Parse error in {} with regex={}.", text, regex));	
	}
	FMCompositionParseException(String message){
		super(String.format("Error while parsing fm composition string: ", message));
	}
}