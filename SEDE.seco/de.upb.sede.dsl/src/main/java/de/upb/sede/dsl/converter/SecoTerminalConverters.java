package de.upb.sede.dsl.converter;

import java.util.Objects;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

public class SecoTerminalConverters extends DefaultTerminalConverters{
	@ValueConverter(rule = "EntityName")
    public IValueConverter<String> EntityName() 
    {
		return new OptionalPrefixNameConverter("$");
    }

	@ValueConverter(rule = "MethodName")
    public IValueConverter<String> MethodName() 
    {
		return new OptionalPrefixNameConverter("!");
    }
	
	static class OptionalPrefixNameConverter implements IValueConverter<String> {
		private final String prefix;
		private final int prefixLength;
		OptionalPrefixNameConverter(String prefix) {
			this.prefix = Objects.requireNonNull(prefix);
			this.prefixLength = prefix.length();
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
			if (Strings.isEmpty(value))
                throw new IllegalArgumentException("Couldn't convert empty string to entity name");
			
			if(value.startsWith(prefix)) {
				return value.substring(prefixLength, value.length());
			} else {
				return value;
			}
		}
	}
}
