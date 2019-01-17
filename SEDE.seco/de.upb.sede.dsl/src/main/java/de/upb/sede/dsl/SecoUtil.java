package de.upb.sede.dsl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import de.upb.sede.dsl.seco.Argument;
import de.upb.sede.dsl.seco.Assignment;
import de.upb.sede.dsl.seco.EntityClassDefinition;
import de.upb.sede.dsl.seco.EntityMethod;
import de.upb.sede.dsl.seco.EntityMethodParam;
import de.upb.sede.dsl.seco.EntityMethodParamSignature;
import de.upb.sede.dsl.seco.Entries;
import de.upb.sede.dsl.seco.Field;
import de.upb.sede.dsl.seco.FieldValue;
import de.upb.sede.dsl.seco.Operation;
import de.upb.sede.dsl.seco.Yield;

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
	
	public static Field createField(String...fieldnames) {
		if(fieldnames == null || fieldnames.length == 0) {
			throw new IllegalArgumentException("Provide at least one fieldname.");
		}
		if(Arrays.stream(fieldnames).anyMatch(s -> s == null || s.isEmpty())) {
			throw new IllegalArgumentException("Provide only non null and non empty field names.");
		}
		Field head = new Field();
		Field current = head;
		for(int i = 0; i < fieldnames.length; i++) {
			current.setName(fieldnames[i]);
			if(fieldnames.length > i + 1) {
				current.setDereference(true);
				Field tail  = new Field();
				current.setMember(tail);
				current = tail;
			}
		}
		return head;
	}
	
	public static Argument createArgument(Object value) {
		FieldValue fieldValue;
		if(value instanceof FieldValue) {
			fieldValue = (FieldValue) value;
		} else {
			fieldValue = createFieldValue(value);
		}
		Argument arg = new Argument();
		arg.setValue(fieldValue);
		return arg;
	}

	public static FieldValue createFieldValue(String castTarget, FieldValue value) {
		FieldValue fieldValue = new FieldValue(); 
		fieldValue.setCast(true);
		fieldValue.setCastTarget(castTarget);
		fieldValue.setCastValue(value);
		return fieldValue;
	}
	
	public static FieldValue createFieldValue(Object value) {
		FieldValue fieldValue = new FieldValue(); 
		if(value instanceof String) {
			fieldValue.setString((String) value);
		} else if(value instanceof Boolean) {
			fieldValue.setBool(((Boolean)value)? "True" : "False");
		} else if(value instanceof Number) {
			fieldValue.setNumber(value.toString());
		} else if (value == null) {
			fieldValue.setNull(true);
		} else if(value instanceof Field) {
			fieldValue.setField((Field) value);
		} else if(value instanceof Operation) {
			fieldValue.setOperation((Operation) value);
		}  else {
			throw new IllegalArgumentException("Cannot create field value with the given value. "
					+ "Value type is: " + value.getClass().getName());
		}
		return fieldValue;
	}

	public static Entries createEntries(EObject... assignmentsYieldsDefs) {
		Entries entries = new Entries();
		for(EObject entry : assignmentsYieldsDefs) {
			if(entry instanceof Assignment || entry instanceof Yield) {
				entries.getInstructions().add(entry);
			} else if(entry instanceof EntityClassDefinition) {
				entries.getEntities().add((EntityClassDefinition) entry);
			}else {
				throw new IllegalArgumentException("Entities cannot contain: " + entry.getClass().getSimpleName());
			}
		}
		return entries;
	}

	
	public static Operation createOperation(String entityContext, String method, Argument... args) {
		Operation op = new Operation();
		op.setEntityName(entityContext);
		op.setMethod(method);
		for(Argument arg : args) {
			op.getArgs().add(arg);
		}
		return op;
	}
	
	public static Operation createOperation(Field context, String method, Argument... args) {
		Operation op = new Operation();
		op.setContextField(context);
		op.setMethod(method);
		for(Argument arg : args) {
			op.getArgs().add(arg);
		}
		return op;
	}
	
	public static boolean checkSignatureMatch(EntityMethodParamSignature signature, EntityMethodParamSignature other, boolean silently) {
		if(signature == other) {
			return true;
		}
		if(signature == null || other == null) {
			return false;
		}
		if(signature.getParameters().size() != other.getParameters().size()) {
			return false;
		}
		for(int i = 0; i < signature.getParameters().size(); i++) {
			EntityMethodParam param = signature.getParameters().get(i);
			EntityMethodParam otherParam = other.getParameters().get(i);
			if(!param.getParameterType().equals(otherParam.getParameterType())) {
				return false;
			}
			
		}
		if(silently) {
			return true;
		}
		/* 
		 * Throw an exception if the two signature are in conflicted.
		 */
		checkForSignatureConflict(signature.getParameters(), other.getParameters());
		checkForSignatureConflict(signature.getOutputs(), other.getOutputs());
		/*
		 * No conflict detected. Signature matches!
		 */
		return true;
	}
	
	private static void checkForSignatureConflict(List<EntityMethodParam> signature, List<EntityMethodParam> other) {
		RuntimeException conflict = new IllegalArgumentException("The two method signatures are in conflict. Were: " + signature.toString() + " and " + other.toString());
		for(int i = 0; i < signature.size(); i++) {
			EntityMethodParam param = signature.get(i);
			EntityMethodParam otherParam = other.get(i);
			
			if(param.isFinal() != otherParam.isFinal()) {
				throw conflict;
			} 
			if(param.isValueFixed() != otherParam.isValueFixed()) {
				throw conflict;
			}else if(param.isValueFixed() && !EcoreUtil.equals(param.getFixedValue(), otherParam.getFixedValue())) {
				throw conflict;
			}
			String name = param.getParameterName();
			String otherName = otherParam.getParameterName();
			if((name == null)  != (otherName == null)) {
				throw conflict;
			}
			else if(name != null) {
				if(!name.equals(otherName)) {
					throw conflict;
				}
			}
		}
	}
	
}
