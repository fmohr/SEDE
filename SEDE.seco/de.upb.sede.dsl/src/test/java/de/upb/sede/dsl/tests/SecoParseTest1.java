package de.upb.sede.dsl.tests;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.upb.sede.dsl.SecoDSL;
import de.upb.sede.dsl.SecoStandaloneSetup;
import de.upb.sede.dsl.parser.antlr.SecoParser;
import de.upb.sede.dsl.seco.Entries;
import de.upb.sede.dsl.seco.Instruction;
import de.upb.sede.dsl.tests.SecoInjectorProvider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("all")
public class SecoParseTest1 {
  
  @Test
  public void loadModel() {
    try {
		 String seco = "fieldName = path1.class2::method1({\"b\", 1.2e-1});" +
				 "field2 = path1.class3::method3({\"b\", 1.2e-1});";
		final Entries result =  SecoDSL.parseString(seco);

		Instruction i = result.getElements().get(1).getInstruction();
	  Assertions.assertNotNull(i);
	  Assertions.assertEquals("field2", i.getField());
	  Assertions.assertEquals("method3", i.getMethod());

	  Assertions.assertTrue(i.getInputs().get(0).isString());
	  Assertions.assertTrue(i.getInputs().get(1).isNumber());
	  
	  final EList<Resource.Diagnostic> errors = result.eResource().getErrors();
	  boolean _isEmpty = errors.isEmpty();
	  StringConcatenation _builder_1 = new StringConcatenation();
	  _builder_1.append("Unexpected errors: ");
	  String _join = IterableExtensions.join(errors, ", ");
	  _builder_1.append(_join);
	  Assertions.assertTrue(_isEmpty, _builder_1.toString());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
