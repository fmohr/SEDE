package de.upb.sede.entity;

import de.upb.sede.dsl.seco.*;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class offers information about builtin entities like string, boolean or numbers.
 */
public class BuiltinEntities {

	public final static String
				ENTITY_BOOL = "Bool",
				ENTITY_NUMBER = "Number",
				ENTITY_STRING = "String",
				ENTITY_NULL = "NULL";

	private final static Entries builtinEntities;

	static {
		builtinEntities = new Entries();

		EntityClassDefinition bool = new EntityClassDefinition();
		bool.setQualifiedName(ENTITY_BOOL);
		EntityMethod fromBoolConst = new EntityMethod();
		fromBoolConst.setMethodName(getFromConstMethodName(ENTITY_BOOL));
		EntityMethodParamSignature fromBoolConstSignature = new EntityMethodParamSignature();
		EntityMethodParam boolConst = new EntityMethodParam();
		boolConst.setParameterType(ENTITY_STRING);
		EntityMethodParam boolOut = new EntityMethodParam();
		boolOut.setParameterType(ENTITY_BOOL);
		fromBoolConstSignature.getParameters().add(boolConst);
		fromBoolConstSignature.getOutputs().add(boolOut);
		fromBoolConst.setParamSignature(fromBoolConstSignature);
		bool.getMethods().add(fromBoolConst);

		EntityClassDefinition number = new EntityClassDefinition();
		number.setQualifiedName(ENTITY_NUMBER);
		EntityMethod fromNumberConst = new EntityMethod();
		fromNumberConst.setMethodName(getFromConstMethodName(ENTITY_NUMBER));
		EntityMethodParamSignature fromNumberConstSignature = new EntityMethodParamSignature();
		EntityMethodParam numberConst = new EntityMethodParam();
		numberConst.setParameterType(ENTITY_STRING);
		EntityMethodParam numberOut = new EntityMethodParam();
		numberOut.setParameterType(ENTITY_NUMBER);
		fromNumberConstSignature.getParameters().add(numberConst);
		fromNumberConstSignature.getOutputs().add(numberOut);
		fromNumberConst.setParamSignature(fromNumberConstSignature);
		number.getMethods().add(fromNumberConst);

		EntityClassDefinition string = new EntityClassDefinition();
		string.setQualifiedName(ENTITY_STRING);
		EntityMethod fromStringConst = new EntityMethod();
		fromStringConst.setMethodName(getFromConstMethodName(ENTITY_STRING));
		EntityMethodParamSignature fromStringConstSignature = new EntityMethodParamSignature();
		EntityMethodParam stringConst = new EntityMethodParam();
		stringConst.setParameterType(ENTITY_STRING);
		EntityMethodParam stringOut = new EntityMethodParam();
		stringOut.setParameterType(ENTITY_STRING);
		fromStringConstSignature.getParameters().add(stringConst);
		fromStringConstSignature.getOutputs().add(stringOut);
		fromStringConst.setParamSignature(fromStringConstSignature);
		string.getMethods().add(fromStringConst);

		builtinEntities.getEntities().add(bool);
		builtinEntities.getEntities().add(number);
		builtinEntities.getEntities().add(string);

	}

	/**
	 * Returns the method name that is invoked to create Bool String or Number from their constant representation.
	 * @param entityName either Bool, String or Number
	 * @return method name for conversion between constant and builtin values.
	 */
	public static String getFromConstMethodName(String entityName) {
		if(ENTITY_BOOL.equals(entityName) || ENTITY_STRING.equals(entityName) || ENTITY_NUMBER.equals(entityName)) {
			return "from_" + entityName + "_const";
		} else {
			throw new IllegalArgumentException("Entity is not built in and doesn't have a from const method. Entity name was: " + entityName);
		}
	}

	/**
	 * Returns builtin entities
	 * @return builtin entities.
	 */
	public static Entries getBuiltinEntities(){
		return EcoreUtil.copy(builtinEntities);
	}

	public static void main(String... args) {
		System.out.println(getBuiltinEntities());
	}
}
