package de.upb.sede.composition.graphs.nodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.gateway.ResolveInfo;

public class ParseConstantNode extends BaseNode {

	private final String constantValue;

	private final ConstantType type;

	public static enum ConstantType {
		NULL, String, Number, Bool;

		public static ConstantType TYPE_FOR(String constant) {
			if (FMCompositionParser.isConstant(constant)) {
				if (FMCompositionParser.isConstantString(constant))
					return String;
				if (FMCompositionParser.isConstantBool(constant))
					return Bool;
				if (FMCompositionParser.isConstantNumber(constant))
					return Number;
				if (FMCompositionParser.isConstantNull(constant))
					return NULL;
			}

			/* given constant isn't a constant */
			throw new RuntimeException();
		}
	}

	public ParseConstantNode(String constantValue) {
		this.constantValue = Objects.requireNonNull(constantValue);
		this.type = ConstantType.TYPE_FOR(constantValue);
	}

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return constantValue.equals(fieldname);
	}

	public String getConstant() {
		return constantValue;
	}

	public ConstantType getType() {
		return this.type;
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return Arrays.asList(getConstant());
	}

	public String toString() {
		return "parse " + type + ": " + constantValue;
	}

}
