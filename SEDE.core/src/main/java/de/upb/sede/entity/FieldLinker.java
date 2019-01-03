package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FieldLinker<K> implements Serializable {

	private Map<String, FieldLinker<K>> children = new HashMap<>();
	private K k;


	public FieldLinker(K k) {
		this.k = k;
	}


	public FieldLinker() {
	}

	public Optional<K> set(K newK) {
		K oldK = this.k;
		this.k = newK;
		return Optional.ofNullable(oldK);
	}

	public Optional<FieldLinker<K>> setLink(String fieldname, FieldLinker<K> newLink) {
		return Optional.ofNullable(this.children.put(fieldname, newLink));
	}

	public Optional<K> get(Field field) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null.");
		FieldLinker<K> child = children.get(fieldName);
		if (child == null) {
			return Optional.empty();
		}
		if (field.isDereference()) {
			return child.get(field.getMember());
		} else {
			return Optional.ofNullable(child.k);
		}
	}

	public Optional<FieldLinker<K>> getLink(Field field) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null.");
		FieldLinker<K> child = children.get(fieldName);
		if (child == null) {
			return Optional.empty();
		}
		if (field.isDereference()) {
			return child.getLink(field.getMember());
		} else {
			return Optional.ofNullable(child);
		}
	}

	public Optional<K> set(Field field, K newK) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null.");
		FieldLinker<K> child = children.get(fieldName);
		if (child == null) {
			child = new FieldLinker<>(null);
			children.put(fieldName, child);
		}
		if (field.isDereference()) {
			return child.set(field.getMember(), newK);
		} else {
			return child.set(newK);
		}
	}

	public Optional<FieldLinker<K>> setLink(Field field, FieldLinker<K> newChild) {
		Objects.requireNonNull(field, "Field was null.");
		String fieldName = Objects.requireNonNull(field.getName(), "Fieldname was null.");
		FieldLinker<K> child = children.get(fieldName);


		if(field.isDereference()) {
			if (child == null) {
				child = new FieldLinker<>(null);
				children.put(fieldName, child);
			}
			return child.setLink(field.getMember(), newChild);
		}
		 else {
			return this.setLink(fieldName, newChild);
		}
	}
}
