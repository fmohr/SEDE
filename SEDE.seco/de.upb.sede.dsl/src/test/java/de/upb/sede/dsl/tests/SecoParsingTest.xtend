/*2.15.0
 */
package de.upb.sede.dsl.tests

import com.google.inject.Inject
import de.upb.sede.dsl.seco.Entries
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import de.upb.sede.dsl.tests.SecoInjectorProvider

@ExtendWith(InjectionExtension)
@InjectWith(SecoInjectorProvider)
class SecoParsingTest {
	@Inject
	ParseHelper<Entries> parseHelper
	
	@Test
	def void loadModel() {
		val result = parseHelper.parse('''
		''')
		Assertions.assertNotNull(result)
		val errors = result.eResource.errors
		Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
	}
}