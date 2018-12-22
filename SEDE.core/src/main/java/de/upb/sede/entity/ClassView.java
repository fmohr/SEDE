package de.upb.sede.entity;

import java.util.List;
import java.util.Optional;

import de.upb.sede.dsl.seco.EntityCast;
import de.upb.sede.dsl.seco.EntityMethod;

public interface ClassView {

	String targetEntityName();

	String entityName();

	boolean is(String entity);

	List<MethodView> allMethodsWithName(String methodName);

	Optional<MethodView> resolveMethod(EntityMethod requestedMethod);

	List<EntityCast> getAllCasts();

}
