package de.upb.sede.composition.graphs.nodes;

import java.util.Arrays;
import java.util.Collection;

import de.upb.sede.gateway.ResolveInfo;

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

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return fieldname.equals(getFieldname());
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Arrays.asList(getFieldname());
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return Arrays.asList(getFieldname());
	}

	public String toString() {
		return "cast-" + fieldname + " " + originType + "->" + targetType;
	}
}
