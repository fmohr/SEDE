package de.upb.sede.composition.graphs.nodes;

import java.util.Objects;

import de.upb.sede.composition.FMCompositionParser;

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

	public String getConstant() {
		return constantValue;
	}

	public ConstantType getType() {
		return this.type;
	}

	public String toString() {
		return "parse " + type + ": " + constantValue;
	}

}
