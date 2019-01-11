package de.upb.sede.entity;

import de.upb.sede.dsl.seco.EntityMethod;
import de.upb.sede.dsl.seco.EntityMethodParam;

import java.util.List;

/**
 * View of EntityMethod.
 */
public interface MethodView {

	/**
	 * The word pure is taken from pure functions in functional programming. In other words invoking it
	 * wont change the state of this entity instance.
	 * @return the pure method flag
	 */
	boolean isPure();

	/**
	 * Just like in java static means that the method can be invoked without supplying entity state.
	 * @return the static method flag
	 */
	boolean isStatic();

	/**
	 * The list of output parameters. Fixed parameters are included.
	 * @return The list of output parameters.
	 */
	List<EntityMethodParam> outputParams();

	/**
	 * The list of input parameters. Fixed parameters are included.
	 * @return The list of input parameters.
	 */
	List<EntityMethodParam> inputParams();

	/**
	 * The list of input parameters that are required. Fixed parameters are therefore excluded.
	 *
	 * @return The list of required parameters.
	 */
	List<EntityMethodParam> requiredParams();

	/**
	 * Returns true if the given signature matches the signature of this method.
	 * A signature matches if all input and output entities are the compatible.
	 * See classView::is for meaning of compatible.
	 *
	 * @param otherSignature other signature
	 * @return true if the given method has a matching signature.
	 */
	boolean matchesSignature(EntityMethod otherSignature);

	/**
	 * Merges the given method into this one.
	 * The returned method is the final merged method view, it isn't however guaranteed that it is a new one.
	 * It could be the given methodInstance or this one or a newly created one.
	 * Consequently, one should dispose this and the otherMethod instance as it this method may change their state.
	 * The attributes of this method instance have precedence over the given method in case of conflicts.
	 *
	 * It is assumes that the signatures match.
	 *
	 * @param otherMethod
	 * @return
	 */
	MethodView mergeMethod(MethodView otherMethod);
}