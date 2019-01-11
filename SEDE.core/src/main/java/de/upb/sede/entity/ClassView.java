package de.upb.sede.entity;

import java.util.List;
import java.util.Optional;

import de.upb.sede.dsl.seco.EntityCast;
import de.upb.sede.dsl.seco.EntityMethod;

/**
 * Class view of entities.
 */
public interface ClassView {

	/**
	 * Return the target entity name which is the name of the wrapped entity if present or else this entity name.
	 * @return target entity name
	 */
	String targetEntityName();

	/**
	 * returns the qualified entity name.
	 * @return the qualified entity name.
	 */
	String entityName();

	/**
	 * Returns true if this entity is a child entity of the entity with given entity name, i.e. the names are equal or this entity extends the given entity.
	 * @param entity other entity name
	 * @return true if this entity is a child entity of the entity with given entity name.
	 */
	boolean is(String entity);

	/**
	 * Checks if this entity is a wrapper.
	 * @return true if this entity is a wrapper.
	 */
	boolean isWrapper();

	/**
	 * List of all declared methods with the given name.
	 * Note that if other methods are realised by a method equal to the given name
	 * @param methodName
	 * @return
	 */
	List<MethodView> allMethodsWithName(String methodName);

	/**
	 * Find a methodview in this entity that matches the given entity method.
	 *
	 * @param requestedMethod requested method
	 * @return method view that matches the given requested method. See matches
	 * @deprecated Use resolveOperation instead.
	 */
	@Deprecated
	Optional<MethodView> resolveMethod(EntityMethod requestedMethod);

	/**
	 * List of all explicit and implicit entity casts.
	 * E.g.
	 * 	"casts to: some.other.Entity;"
	 * is a explicit cast to "some.other.Entity".
	 * Implicit casts include casting to the entity itself and casting to DIRECT parents. (identity)
	 * Note that if:
	 * 		class: A extends B{};
	 * 		class: B extends C{};
	 * 	invoking getDeclaredCasts on the classview of A will NOT return the cast between A -> C
	 * @return List of all entity casts.
	 */
	List<EntityCast> declaredCasts();

	/**
	 * Returns the list of declared parent entities,
	 * i.e. all entities this entity is directly extending from.
	 * @return parent entities.
	 */
	List<ClassView> declaredParents();

	/**
	 * Returns the list of all entities that are the parent of this entity.
	 * The returned list also contains indirect parents that are extended transitively.
	 * @return list of all parents of this entity.
	 */
	List<ClassView> allParents();

}