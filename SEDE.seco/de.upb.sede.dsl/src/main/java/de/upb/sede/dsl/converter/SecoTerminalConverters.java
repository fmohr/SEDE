package de.upb.sede.dsl.converter;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SecoTerminalConverters extends DefaultTerminalConverters{

	public final static int PRE_FETCH = -100;

	public final static int PRE_RUN = -10;
	
	public final static int POST_RUN = 10;
	
	public final static int INSTEAD = 0;

	public final static int BEFORE = -10;
	
	public final static int AFTER = 10;
	
	@ValueConverter(rule = "EntityName")
    public IValueConverter<String> EntityName() 
    {
		return new OptionalPrefixNameConverter("$", s -> !s.contains(".") && !s.startsWith("$"));
    }

	@ValueConverter(rule = "MethodName")
    public IValueConverter<String> MethodName() 
    {
		return new OptionalPrefixNameConverter("!", s -> false);
    }

	@ValueConverter(rule = "DependencyOrder")
    public IValueConverter<Integer> DependencyOrder() 
    {
		return new DependencyOrderConverter();
    }

	@ValueConverter(rule = "InstructionOrder")
    public IValueConverter<Integer> InstructionOrder() 
    {
		return new InstructionOrderConverter();
    }
	
	@ValueConverter(rule = "NumberConst")
    public IValueConverter<String> NumberConst() 
    {
		return new NumberConstConverter();
    }
	
//	@ValueConverter(rule = "Json")
//    public IValueConverter<JSONObject> Json() 
//    {
//		return new JsonConverter();
//    }
	
	static class OptionalPrefixNameConverter implements IValueConverter<String> {
		private final String prefix;
		private final int prefixLength;
		private final Function<String, Boolean> mandatoryCondition;
		OptionalPrefixNameConverter(String prefix, Function<String, Boolean> mandatoryCondition) {
			this.prefix = Objects.requireNonNull(prefix);
			this.prefixLength = prefix.length();
			this.mandatoryCondition = mandatoryCondition;
		}
		
		@Override
		public String toValue(String string, INode node) throws ValueConverterException {
			if (Strings.isEmpty(string))
                throw new ValueConverterException("Couldn't convert empty string to entity name", node, null);
			if(string.startsWith(prefix)) {
				return string.substring(prefixLength, string.length());
			} else {
				return string;
			}
		}
		@Override
		public String toString(String value) throws ValueConverterException {
			Objects.requireNonNull(value);
			if(mandatoryCondition.apply(value)) {
				return prefix + value;
			} else {
				return value;
			}
		}
	}
	
	static class JsonConverter implements IValueConverter<JSONObject> {
		JSONParser parser = new JSONParser();
		

		@Override
		public JSONObject toValue(String string, INode node) throws ValueConverterException {
			try {
				return (JSONObject) parser.parse(string);
			} catch (ParseException e) {
                throw new ValueConverterException("Couldn't convert " + string + " to a json object.", node, null);
			}
		}

		@Override
		public String toString(JSONObject value) throws ValueConverterException {
			return value.toJSONString();
		}
	}
	
	static class DependencyOrderConverter implements IValueConverter<Integer> {

		
		@Override
		public Integer toValue(String string, INode node) throws ValueConverterException {
			// string can be: "PRE-FETCH" | "PRE-RUN" | "POST-RUN" | INT
			switch (string) {
			case "PRE-FETCH":
				return PRE_FETCH;
			case "PRE-RUN":
				return PRE_RUN;
			case "POST-RUN":
				return POST_RUN;
			default:
				return Integer.valueOf(string.replaceAll("\\s", ""));
			}
		}

		@Override
		public String toString(Integer value) throws ValueConverterException {
			switch (value) {
			case PRE_FETCH:
				return "PRE-FETCH";
			case PRE_RUN:
				return "PRE-RUN";
			case POST_RUN:
				return "POST-RUN";
			default:
				return value.toString();
			}
		}
	}
	static class InstructionOrderConverter implements IValueConverter<Integer> {
	
		
		@Override
		public Integer toValue(String string, INode node) throws ValueConverterException {
			// string can be: 	"before" | "instead" | "after" | (("-" | "+")?INT)
			switch (string) {
			case "before":
				return BEFORE;
			case "instead":
				return INSTEAD;
			case "after":
				return AFTER;
			default:
				return Integer.valueOf(string.replaceAll("\\s", ""));
			}
		}
	
		@Override
		public String toString(Integer value) throws ValueConverterException {
			switch (value) {
			case BEFORE:
				return "before";
			case INSTEAD:
				return "instead";
			case AFTER:
				return "after";
			default:
				return value.toString();
			}
		}
	}
	static class NumberConstConverter implements IValueConverter<String> {

		@Override
		public String toValue(String string, INode node) throws ValueConverterException {
			return string.replaceAll("\\s", "");
		}

		@Override
		public String toString(String value) throws ValueConverterException {
			return value.replaceAll("\\s", "");
		}

	
	}
}
