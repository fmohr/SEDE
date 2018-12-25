package de.upb.sede.dsl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.formatting2.FormatterRequest;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionRewriter;
import org.eclipse.xtext.formatting2.regionaccess.internal.NodeModelBasedRegionAccessBuilder;
import org.eclipse.xtext.parser.ParseException;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Injector;

import de.upb.sede.dsl.seco.Entries;

public class SecoUtil {
	private static final Injector injector = new SecoStandaloneSetup().createInjectorAndDoEMFRegistration();

	
	public static final Entries parseSources(Object... secoSources) throws IOException{
		if(secoSources == null || secoSources.length == 0) {
			throw new IllegalArgumentException();
		}
		for(Object secoSrc : secoSources) {
			if(secoSrc instanceof File) {
				continue;
			} else if(secoSrc instanceof String) {
				continue;
			} else if(secoSrc instanceof InputStream) {
				continue;
			} else {
				throw new IllegalArgumentException("Seco sources can only be strings files or inputstreams."
						+ " Given source is of type: " + secoSources.getClass().getName());
			}
		}
		
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
//		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.createResource(URI.createURI(".seco"));
		Entries result = new Entries();
		for(Object src : secoSources) {
			InputStream in;
			if(src instanceof File) {
				in = new FileInputStream((File) src);
			} else if(src instanceof String) {
				in = new ByteArrayInputStream(((String) src).getBytes());
			} else if(src instanceof InputStream) {
				in = (InputStream) src;
			} else {
				throw new RuntimeException("BUG");
			}
			try( InputStream closeThisPlease = in ) {
				resource.load(in, resourceSet.getLoadOptions());
				Entries parsedEntries = (Entries) resource.getContents().get(0);

				final EList<Resource.Diagnostic> errors = parsedEntries.eResource().getErrors();
				if(!errors.isEmpty()) {
					StringConcatenation _builder_1 = new StringConcatenation();
					_builder_1.append("Unexpected errors while parsing:\n\t ");
					String _join = IterableExtensions.join(errors, "\n\t ");
					_builder_1.append(_join);
					throw new IOException(_builder_1.toString());
				}
				if(secoSources.length > 1) {
					result.getEntities().addAll(parsedEntries.getEntities());
					result.getInstructions().addAll(parsedEntries.getInstructions());
				} else {
					result = parsedEntries;
				}
			}
		}
		try {
			return result;
		} catch(Exception ex) {
			throw new ParseException(ex.getMessage());
		}
	}
	
	public static String serialize(EObject obj) throws IOException {
		if(obj.eResource() == null && obj instanceof SecoObject) {
			XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
			Resource resource = resourceSet.createResource(URI.createURI(".seco"));
			resource.getContents().add(obj);
		}
		Serializer serializer = injector.getInstance(Serializer.class);
		String serialization = serializer.serialize(obj, SaveOptions.newBuilder().format().getOptions());
		
		return serialization;
	}
	
	public static String serializeSilently(EObject eobj) {  
		if (eobj == null) {
			return "null";
		}
		try {
			return serialize(eobj);
		} catch (Exception ex) { // fall back:
			return eobj.getClass().getSimpleName() + '@' + eobj.hashCode();
		}
	}  
	
}
