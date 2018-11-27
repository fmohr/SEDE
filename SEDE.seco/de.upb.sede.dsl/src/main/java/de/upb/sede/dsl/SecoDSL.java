package de.upb.sede.dsl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import de.upb.sede.dsl.seco.Entries;

public class SecoDSL {
	private static final Injector injector = new SecoStandaloneSetup().createInjectorAndDoEMFRegistration();
	
	public static final Entries parseString(String seco) throws IOException{
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
//		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.createResource(URI.createURI(".seco"));
		InputStream in = new ByteArrayInputStream(seco.getBytes());
		resource.load(in, Collections.EMPTY_MAP);
		final Entries result = (Entries) resource.getContents().get(0);
		return result;
	}
}
