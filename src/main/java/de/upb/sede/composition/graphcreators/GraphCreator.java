package de.upb.sede.composition.graphcreators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.sede.composition.FMComposition;
import de.upb.sede.composition.GraphComposition;

public class GraphCreator {
    private final FMComposition sourceComposition;
    private GraphComposition targetComposition;
    
    public final static String REGEX_lineSeperator = "[\\s]*;[\\s]*";

    public final static String REGEX_fieldname = "\\b[_a-zA-Z]\\w*?\\b";

    public final static String REGEX_classpath = "\\b(?:" 
                                                    + REGEX_fieldname + "\\.)++(?:"
                                                    + REGEX_fieldname + ")\\b";
    
    public final static String REGEX_ipaddress_port = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
                                                    + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
                                                    + ":\\d{1,5}\\b";


    public final static String REGEX_domainname_port = "\\b(?:[a-zA-Z](?:\\.|\\w)*):\\d{1,5}\\b";

    public final static Pattern PATTERN_whole = Pattern.compile("^([\\S\\s]+?;)+?$");
    // private final static Pattern PATTERN_splitInstructions = Pattern.compile("([\\S\\s]+?);");

    public GraphCreator(String fmComposition) {
        sourceComposition = new FMComposition(Objects.requireNonNull(fmComposition));
    }


    public void createRawGraph() {
        List<String> compositionLines = new ArrayList<>();
        /* split by ';' into multiple lines */
        String[] lines = sourceComposition.getComposition().split(REGEX_lineSeperator);
        /* can't create a graph from a empty composition. */
        if(lines.length == 0) {
        		throw new RuntimeException("Empty composition.");
        }
        /* add every non empty line to the compositionLines list. */
        for(String line : lines) {
        	 	line = line.trim();
        	 	if(line.length()>0) {
        	 		compositionLines.add(line);
        	 	}
        }
        
        
        Matcher matchesInstructions = PATTERN_instruction.matcher(sourceComposition.getComposition());
        while(matchesInstructions.matches()){
            compositionLines.add(matchesInstructions.group(1));
            matchesInstructions.find();
        }
        System.out.println(compositionLines);
        if(compositionLines.isEmpty()){
            throw new RuntimeException("FMComposition has a wrong format.");
        }
    }
}