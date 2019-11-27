package de.upb.sede.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.Streams;

public final class SemanticStreamer {

	private static final Pattern REALTYPE_PATTERN = Pattern.compile("([a-zA-Z_0-9\\.]+\\.)*(?<classpath>[a-zA-Z_]\\w*)$");

	private final static Logger logger = LoggerFactory.getLogger(SemanticStreamer.class);

	public static SEDEObject readFrom(InputStream is, String type) {
		SEDEObject parsedObject;
		if(SEDEObject.isPrimitive(type)){
			/*
			 * parse the primitive data:
			 * data could be: Integer Double String Boolean or null
			 */
			String primitiveStr = Streams.InReadString(is);
			Object data = castStringToPrimitive(primitiveStr, type).getDataField();
			parsedObject = new PrimitiveDataField(type, data);
		} else if(SEDEObject.isServiceInstanceHandle(type)){
			/*
			 * Parse service instance handle from json string:
			 * data is a serviceinstancehandle.
			 */
			String jsonString = Streams.InReadString(is);
			ServiceInstanceHandle serviceInstanceHandle = new ServiceInstanceHandle();
			serviceInstanceHandle.fromJsonString(jsonString);
			parsedObject = new ServiceInstanceField(serviceInstanceHandle);
		} else {
			/*
			 * We don't know if the stream is persistent or not.
			 * So we assume the worst case.
			 */
			parsedObject = new SemanticDataField(type, Streams.InReadChunked(is).toInputStream(), true);
		}
		return parsedObject;
	}

	private static SEDEObject castStringToPrimitive(String data, String type){
		PrimitiveType enumType = PrimitiveType.insensitiveValueOf(type);
		return parsePrimitive(data, enumType);
	}

	public static SEDEObject readObjectFrom(SEDEObject semanticObject, String caster, String sourceSemanticType, String targetRealTypeCp) {
		if(semanticObject.isSemantic()){
			InputStream inputStream = semanticObject.getDataField();
			return readObjectFrom(inputStream, caster, sourceSemanticType, targetRealTypeCp);
		} else{
			throw new RuntimeException("The given SEDEObject " + semanticObject.toString() + " is not semantic.");
		}
	}

	public static SEDEObject readObjectFrom(InputStream is, String caster, String sourceSemanticType, String targetRealTypeCp) {
		logger.trace("Casting from semantic type '{}' to '{}' using caster class: {}.", sourceSemanticType, targetRealTypeCp, caster);
		String targetRealType = getSimpleNameFromClasspath(targetRealTypeCp);
		String casterMethod = getCastMethod(targetRealType, false);
		Method method = getMethodFor(caster, casterMethod);
		try {
			Object casterInstance = Class.forName(caster).getConstructor().newInstance();
			try {
				is.reset();
			} catch (IOException e) {
				logger.warn("Couldn't reset the input stream before casting: " + is.toString() + ", type: " + is.getClass().getName(), e);
			}
			Object castedObject = method.invoke(casterInstance, is);
			SEDEObject sedeObject = new ObjectDataField(targetRealTypeCp, castedObject);
			return sedeObject;
		} catch (ReflectiveOperationException ex){
			throw new RuntimeException(ex);
		}
	}

	public static void streamInto(OutputStream os, SEDEObject content) {
		try {
			if(content.isPrimitive()){
				/*
				 * semnatic data can be written as string:
				 */
				String stringEncoded = castPrimitiveToString(content.getDataField());
				byte [] encodedData = stringEncoded.getBytes();
				os.write(encodedData);
			} else if(content.isSemantic()){
				/**
				 * Semnatic objects hold byte arrray as object:
				 */
				InputStream encodedData = content.getDataField();
				try {
					encodedData.reset();
				} catch (IOException e) {
					logger.warn("Couldn't reset the input stream before casting: " + encodedData.toString() + ", type: " + encodedData.getClass().getName(), e);
				}
				Streams.InReadChunked(encodedData).writeTo(os);
				os.flush();
			} else if(content.isServiceInstanceHandle()) {
				ServiceInstanceHandle instanceHandle = (ServiceInstanceHandle) content.getDataField();
				OutputStreamWriter osWriter = new OutputStreamWriter(os);
				instanceHandle.toJson().writeJSONString(osWriter);
				osWriter.flush();
			} else if(content.isReal()) {
				throw new RuntimeException("BUG: Use streamObjectInto instead: " + content.toString());
			} else{
				throw new RuntimeException("BUG: Cannot handle sede object: " + content.toString());
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}


	public static void streamObjectInto(OutputStream os, SEDEObject content, String caster, String targetSemanticType) {
		logger.trace("Casting from '{}' to semantic type '{}' using caster class: {}.", content.getType(), targetSemanticType, caster);

		Objects.requireNonNull(os);
		Objects.requireNonNull(content);
		Objects.requireNonNull(caster);
		Objects.requireNonNull(targetSemanticType);

		String sourceRealType = getSimpleNameFromClasspath(content.getType());
		String casterMethod = getCastMethod(sourceRealType, true);
		Method method = getMethodFor(caster, casterMethod);
		try {
			Object casterInstance = Class.forName(caster).getConstructor().newInstance();
			method.invoke(casterInstance, os, content.getDataField());
		} catch (ReflectiveOperationException ex){
			throw new RuntimeException(ex);
		}
	}

	public static String getCastMethod(String realType, boolean toSemantic) {
		String methodName;
		if(toSemantic) {
			methodName = "cts_";
		} else {
			methodName = "cfs_";
		}
		methodName += realType;
		return methodName;
	}

	private static Method getMethodFor(String caster, String methodName) {
		Objects.requireNonNull(caster);
		Objects.requireNonNull(methodName);
		try {
			logger.trace("Using method {} in caster {}.", methodName, caster);
			Class casterClass = Class.forName(caster);
			Method[] allDefinedMethods = casterClass.getMethods();
			for(Method method : allDefinedMethods){
				if(method.getName().equals(methodName)){
					return method;
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Caster class: " + caster + "  doesnt contain method: " + methodName);

	}

	public static String getSimpleNameFromClasspath(String classpath){
		Matcher m = REALTYPE_PATTERN.matcher(classpath);
		if(!m.matches()) {
			throw new RuntimeException("classpath " + classpath + " is not legal.");
		}
		else{
			return m.group("classpath");
		}
	}

	private static String castPrimitiveToString(Object primitiveObject){
		if(primitiveObject ==  null) {
			return "null";
		}
		else {
			return primitiveObject.toString();
		}
	}

	public static final <T> T objectDeserialize(InputStream inputStream) {
		ObjectInputStream objectIn = null;
		try {
			objectIn = new ObjectInputStream(inputStream);
			Object input = objectIn.readObject();
			return (T)input;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static final void objectSerialize(OutputStream os, Serializable serializable) {
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(os);
			objectOut.writeObject(serializable);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static SEDEObject parsePrimitive(final String constantStr, final PrimitiveType primitiveType) {
		final Object data;
		switch (Objects.requireNonNull(primitiveType)) {
		case NULL:
			data = null;
			break;
		case Bool:
			data = Boolean.valueOf(constantStr);
			break;
		case Number:
			try {
				data = NumberFormat.getInstance().parse(constantStr);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			break;
		case String:
			data = constantStr;
			break;
		default:
			throw new RuntimeException("All cases covered. Add default to have 'data' initialized.");
		}
		return new PrimitiveDataField(primitiveType.name(), data);
	}
}
