package de.upb.sede.composition.graphs.nodes;

import java.util.Objects;
import java.util.Optional;

public class AcceptDataNode extends BaseNode {

	private final String fieldname;

	private Optional<CastTypeNode> castInPlace = Optional.empty();

	public AcceptDataNode(String fieldname) {
		this.fieldname = Objects.requireNonNull(fieldname);
	}

	public String getReceivingFieldname() {
		return fieldname;
	}

	public Optional<CastTypeNode> getCastInPlace() {
		return castInPlace;
	}

	public void setCastInPlace(CastTypeNode castInPlace) {
		this.castInPlace = Optional.of(castInPlace);
		/*
		 * check if caster matches:
		 */
		assert castInPlace.getFieldname().equals(getReceivingFieldname());
		assert !castInPlace.isCastToSemantic();
	}

	public String toString() {
		return "accept \"" + getReceivingFieldname() + "\" " + (castInPlace.isPresent()? castInPlace.get().toString() : " store bytes" );
	}

}