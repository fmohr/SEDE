/**
 *
 * $Id$
 */
package de.upb.sede.dsl.seco.validation;

import de.upb.sede.dsl.seco.FieldValue;

import java.util.List;

/**
 * A sample validator interface for {@link de.upb.sede.dsl.seco.Yield}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface YieldValidator {
	boolean validate();

	boolean validateYields(List<FieldValue> value);
	boolean validateMultiYield(boolean value);
}
