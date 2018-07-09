package de.upb.sede.composition.graphs.nodes;

public class CastTypeNode extends BaseNode {

	private final String fieldname;

	private final String originType, targetType;

	private final boolean castToSemantic;

	private final String casterClasspath;

	public CastTypeNode(String fieldname, String originType, String targetType, boolean castToSemantic,
			String casterClasspath) {
		this.fieldname = fieldname;
		this.originType = originType;
		this.targetType = targetType;
		this.castToSemantic = castToSemantic;
		this.casterClasspath = casterClasspath;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getOriginType() {
		return originType;
	}

	public String getTargetType() {
		return targetType;
	}

	public boolean isCastToSemantic() {
		return castToSemantic;
	}

	public String getCasterClasspath() {
		return casterClasspath;
	}

	public String toString() {
		return "cast \"" + fieldname + "\" from " + originType + " to " + targetType;
	}
}
