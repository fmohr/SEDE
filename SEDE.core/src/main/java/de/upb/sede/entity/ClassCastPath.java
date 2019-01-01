package de.upb.sede.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;

import de.upb.sede.dsl.seco.EntityCast;

public class ClassCastPath {
	final String rootEntity;
	final List<EntityCast> path;
	
	private ClassCastPath(String root, List<EntityCast> path) {
		Objects.requireNonNull(root);
		Objects.requireNonNull(path);
		this.rootEntity = root;
		this.path =  Collections.unmodifiableList(path);
	}

	ClassCastPath(String rootEntity) {
		this(rootEntity, Collections.emptyList());
	}

	public boolean isEmpty() {
		return path.isEmpty();
	}
	
	boolean contains(String entity) {
		if(entity.equals(rootEntity)) {
			return true;
		}
		for(EntityCast cast : path) {
			if(cast.getResultingEntity().equals(entity)) {
				return true;
			}
		}
		return false;
	}
	
	String last() {
		if(path.isEmpty()) {
			return rootEntity;
		} else {
			return path.get(path.size()-1).getResultingEntity();
		}
	}
	
	String head() {
		return rootEntity;
	}
	
	Optional<ClassCastPath> tail() {
		if(path.isEmpty()) {
			return Optional.empty();
		} else {
			List<EntityCast> extendedList = new ArrayList<>(path);
			String root = extendedList.remove(0).getResultingEntity();
			return Optional.of(new ClassCastPath(root, extendedList));
		}
	}
	
	ClassCastPath addCast(EntityCast newCast) {
		List<EntityCast> extendedList = new ArrayList<>(path);
		extendedList.add(newCast);
		return new ClassCastPath(rootEntity, extendedList);
	}

	@Override
	public int hashCode() {
		throw new RuntimeException("Castpath is not hashable.");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ClassCastPath))
			return false;
		ClassCastPath other = (ClassCastPath) obj;
		if(Objects.equals(rootEntity, other.rootEntity)) {
			return false;
		}
		if(path.size() != other.path.size()) {
			return false;
		}
		for(int i = 0; i < path.size(); i++) {
			EntityCast casti = path.get(i);
			EntityCast otherCasti = other.path.get(i);
			if(! EcoreUtil.equals(casti, otherCasti)) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		return rootEntity + (tail().isPresent()? " -> " + tail().get().toString(): "");
	}
	
}