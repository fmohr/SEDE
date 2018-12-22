/**
 *
 * $Id$
 */
package de.upb.sede.dsl.seco.validation;

import de.upb.sede.dsl.seco.EntityMethodParamSignature;
import de.upb.sede.dsl.seco.EntityMethodProp;

/**
 * A sample validator interface for {@link de.upb.sede.dsl.seco.EntityMethod}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EntityMethodValidator {
	boolean validate();

	boolean validateProperty(EntityMethodProp value);
	boolean validateMethodName(String value);
	boolean validateParamSignature(EntityMethodParamSignature value);
	boolean validateRealization(boolean value);
	boolean validateMethodRealization(String value);
	boolean validateAdditionalData(String value);
}
