package de.upb.sede.composition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.exceptions.FMCompositionSyntaxException;

/**
 * 
 * Defines a set of regex-patterns that parse fmCompositions. Tested
 *
 * @author aminfaez
 * 
 */
public final class FMCompositionParser {

	/**
	 * Empty private constructor in order to prevent instantiation.
	 */
	private FMCompositionParser() {
	}

	/*
	 * All regex patterns are applied to fmCompositions.
	 */

	/** Regex used to separate instructions */
	final static String REGEX_lineSeperator = "[\\s]*;[\\s]*";

	/** Regex used to separate instructions */
	final static String REGEX_inputSeperator = "[\\s]*,[\\s]*";

	/** Regex for a field name. */
	final static String REGEX_fieldname = "(?:[_a-zA-Z]\\w*+)";
	final static Pattern PATTERN_fieldname = Pattern.compile(REGEX_fieldname);

	/** Regex for class path. */
	final static String REGEX_classpath = "(?:(?:" + REGEX_fieldname + "\\.)++(?:" + REGEX_fieldname + "))";
	final static Pattern PATTERN_classpath = Pattern.compile(REGEX_classpath);

	/** Regex for ip address concatenated with port. e.g.: 110.120.130.140:1000 */
	final static String REGEX_ipaddress_port = "(?:(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
			+ "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" + ":\\d{1,5})";
	final static Pattern PATTERN_ipaddress_port = Pattern.compile(REGEX_ipaddress_port);

	/** Regex for domain names concatenated with port. e.g.: isys.db.upb.de:8000 */
	final static String REGEX_domainname_port = "(?:(?:[a-zA-Z](?:\\.|\\w|-)++):\\d{1,5})";
	final static Pattern PATTERN_domainname_port = Pattern.compile(REGEX_domainname_port);

	/**
	 * Regex for a complete instruction. Defines named groups: 'leftside', 'host',
	 * 'context', 'method', 'inputs'.
	 * 
	 * e.g.:
	 * 
	 * "s1=127.0.0.1:8000/Catalano.Imaging.Filters.Crop::__construct({i1=0,i2=0,i3=10,i4=10})"
	 * matches.
	 * 
	 * Group values in this example: leftside = "s1" host = "127.0.0.1:8000" context
	 * = "Catalano.Imaging.Filters.Crop" method = "__construct" inputs =
	 * "i1=0,i2=0,i3=10,i4=10"
	 */
	final static String REGEX_instruction = "^" + "(?:(?<leftside>" + REGEX_fieldname + ")=){0,1}" + "(?:(?<host>"
			+ REGEX_ipaddress_port + "|" + REGEX_domainname_port + ")/){0,1}" + "(?<context>" + REGEX_classpath + "|"
			+ REGEX_fieldname + ")::" + "(?<method>" + REGEX_fieldname + ")"
			+ "\\((?:\\{(?<inputs>[\\S\\s]+)\\}){0,1}\\)" + "$";
	final static Pattern PATTERN_instruction = Pattern.compile(REGEX_instruction);

	/**
	 * Regex for parameters. e.g.: "i1=10" Uses anchors!!
	 */
	final static String REGEX_parameter = "^(?:[iI](?<position>[1-9][0-9]*?)=){0,1}(?<parametervalue>[\\S\\s]++)$";
	final static Pattern PATTERN_parameter = Pattern.compile(REGEX_parameter);

	/**
	 * Regex for numbers. Can be floats and can have exponents. e.g.: 10, 10.1,
	 * 10.1E10 are all valid. Uses anchors!!
	 */
	final static String REGEX_constNumber = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
	final static Pattern PATTERN_constNumber = Pattern.compile(REGEX_constNumber);

	/**
	 * Regex for strings. String ought to be in literal quotation marks: e.g.:
	 * String s = "\"abc\"" is a valid string. Uses anchors!!
	 */
	final static String REGEX_constString = "^\"[\\S\\s]*\"$";
	final static Pattern PATTERN_constString = Pattern.compile(REGEX_constString);

	/**
	 * Regex for boolean. Is case insensitive: e.g.: "true", "True", "TRUE", "TrUe"
	 * all match. Same with false. Uses anchors!!
	 */
	final static String REGEX_constBool = "^(?i)(?:true)|(?:false)(?-i)$";
	final static Pattern PATTERN_constBool = Pattern.compile(REGEX_constBool);

	/**
	 * Regex for null constant. Is case-insensitive: e.g.: "null", "NULL", "NuLL"all
	 * match. Uses anchors!!
	 */
	final static String REGEX_constNull = "^(?i)(?:null)(?-i)$";
	final static Pattern PATTERN_constNull = Pattern.compile(REGEX_constNull);

	/**
	 * Regex that matches any constant value. Uses anchors!!
	 */
	final static String REGEX_const = "(?:" + REGEX_constNumber + ")|(?:" + REGEX_constString + ")|(?:"
			+ REGEX_constBool + ")|(?:" + REGEX_constNull + ")";
	final static Pattern PATTERN_const = Pattern.compile(REGEX_const);

	/**
	 * Returns list of instruction from the given fmComposition. In fmCompositions
	 * instructions are separated by ';'. Each instruction is trimmed and its
	 * whitespaces are removed. Empty instructions are ignored.
	 * 
	 * The list returned by this method is immutable.
	 * 
	 * Example: separateInstructions("a;b;;c;d") -> ["a", "b", "c", "d"]
	 */
	public static List<String> separateInstructions(final String fmComposition) {
		Objects.requireNonNull(fmComposition);
		List<String> compositionLines = new ArrayList<>();
		/* split by ';' into multiple lines */
		String[] lines = fmComposition.split(REGEX_lineSeperator);
		/* can't create a graph from a empty composition. */
		if (lines.length == 0) {
			throw new FMCompositionSyntaxException("Empty composition: " + fmComposition + ".");
		}
		/* add every non empty line to the compositionLines list. */
		for (String line : lines) {
			/* remove whitespaces */
			line = line.trim();
			line = line.replaceAll("\\s", "");
			if (!line.isEmpty()) {
				compositionLines.add(line);
			}
		}
		return Collections.unmodifiableList(compositionLines);
	}

	/**
	 * Returns list of inputs split by ','.
	 * 
	 * The list returned by this method is immutable.
	 * 
	 * Returns an empty list if inputString is null.
	 * 
	 * Example: separateInstructions("a,b,,cd") -> ["a", "b", "cd"]
	 */
	@SuppressWarnings("unchecked")
	public static List<String> separateInputs(final String inputString) {
		if (inputString == null) {
			return Collections.EMPTY_LIST;
		}
		/* split by ';' into multiple lines */
		String[] inputArray = inputString.split(REGEX_inputSeperator);
		/* can't create a graph from a empty composition. */
		if (inputArray.length == 0) {
			return Collections.EMPTY_LIST;
		}
		List<String> inputs = new ArrayList<>();
		/* add every non empty line to the compositionLines list. */
		for (String input : inputArray) {
			/* remove whitespaces */
			input = input.trim();
			input = input.replaceAll("\\s", "");

			if (!input.isEmpty()) {
				inputs.add(input);
			}
		}
		/*
		 * Now the inputs contains values like "i1=2", "i2=someVar", .. Remove the the
		 * positional indicators and the equal sign:
		 */
		List<String> parameters = new ArrayList<>();
		/**
		 * flag indicates that positional indicators were used before.
		 */
		boolean positionalIndicatorUsed = false;
		for (String input : inputs) {
			Matcher inputMatcher = PATTERN_parameter.matcher(input);
			if (inputMatcher.matches()) {
				String positionIndicator = inputMatcher.group("position");
				String parameter = inputMatcher.group("parametervalue");
				if (positionIndicator != null) {
					/* positional indicator included: */
					positionalIndicatorUsed = true;
					int position = Integer.parseInt(positionIndicator);
					/*
					 * fill list with null value until it large enough to fit in the parameter at
					 * the given position
					 */

					if (parameters.size() >= position) {
						/*
						 * position is indicating to overwrite a value from before. e.g. "i1=0,i1=1,.."
						 */
						throw new FMCompositionSyntaxException("A single Position is assigned twice: " + inputString);
					}
					while (parameters.size() < position) {
						parameters.add("null");
					}
					/* positions start with 1. */
					parameters.set(position - 1, parameter);
				} else {
					if (positionalIndicatorUsed) {
						/*
						 * positional indicator used in the last iteration.
						 */
						throw new FMCompositionSyntaxException(
								"Once positonal indicator was used, positional indicator stops being optional: \n\t"
										+ inputString);
					}
					parameters.add(parameter);
				}
			} else {
				throw new FMCompositionSyntaxException(input, REGEX_parameter);
			}
		}
		return Collections.unmodifiableList(parameters);
	}

	/**
	 * Parse a fmComposition instruction and transforms it into an InstructionNode.
	 */
	public static InstructionNode parseInstruction(final String instruction) {
		Matcher instructionMatcher = PATTERN_instruction.matcher(instruction);
		if (instructionMatcher.matches()) {

			/*
			 * Extract values from instruction.
			 * 
			 * e.g.: instruction :
			 * s1=127.0.0.1:8000/Catalano.Imaging.Filters.Crop::__construct({i1=0,i2=0,i3=10
			 * ,i4=10})
			 */
			String leftside = instructionMatcher.group("leftside");
			String host = instructionMatcher.group("host");
			String context = instructionMatcher.group("context");
			String method = instructionMatcher.group("method");
			String inputs = instructionMatcher.group("inputs");
			/*
			 * Values would be:
			 * 
			 * leftside = "s1" (might be null) host = "127.0.0.1:8000" (might be null)
			 * context = "Catalano.Imaging.Filters.Crop" method = "__construct" inputs =
			 * "i1=0,i2=0,i3=10,i4=10" (might be null)
			 */

			/*
			 * Distinguish between service creation and service invocation;
			 */
			InstructionNode instNode = new InstructionNode(instruction, context, method);
			if (PATTERN_classpath.matcher(context).matches()) {
				instNode.setContextIsField(false);
			} else {
				instNode.setContextIsField(true);
			}
			/* populate fields */
			if (leftside != null) {
				instNode.setLeftSideFieldname(leftside);
			}
			if (host != null) {
				instNode.setHost(host);
			}

			/* parse input parameters */
			// inputs may be null, but the method returns an empty list in that case.
			List<String> inputList = separateInputs(inputs);
			instNode.setParameterFields(inputList);

			return instNode;
		} else {
			throw new FMCompositionSyntaxException(instruction, REGEX_instruction);
		}
	}

	/**
	 * Returns true if the given text is a constant. That means its either boolean
	 * number or string or 'null'
	 */
	public static boolean isConstant(String text) {
		if (text == null) {
			return false;
		}
		return PATTERN_const.matcher(text).matches();
	}

	public static boolean isConstantString(String text) {
		if (text == null) {
			return false;
		}
		return PATTERN_constString.matcher(text).matches();
	}

	public static boolean isConstantBool(String text) {
		if (text == null) {
			return false;
		}
		return PATTERN_constBool.matcher(text).matches();
	}

	public static boolean isConstantNumber(String text) {
		if (text == null) {
			return false;
		}
		return PATTERN_constNumber.matcher(text).matches();
	}

	public static boolean isConstantNull(String text) {
		if (text == null) {
			return false;
		}
		return PATTERN_constNull.matcher(text).matches();
	}

}