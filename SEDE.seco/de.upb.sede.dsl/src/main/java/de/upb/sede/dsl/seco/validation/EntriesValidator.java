/**
 *
 * $Id$
 */
package de.upb.sede.dsl.seco.validation;

import de.upb.sede.dsl.seco.EntityClassDefinition;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * A sample validator interface for {@link de.upb.sede.dsl.seco.Entries}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EntriesValidator {
	boolean validate();

	boolean validateInstructions(List<EObject> value);
	boolean validateEntities(List<EntityClassDefinition> value);
}
