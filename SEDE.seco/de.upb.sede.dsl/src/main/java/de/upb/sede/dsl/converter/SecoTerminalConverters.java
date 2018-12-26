package de.upb.sede.dsl.converter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
}
