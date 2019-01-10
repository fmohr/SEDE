/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see de.upb.sede.dsl.seco.SecoPackage
 * @generated
 */
public class SecoFactory extends EFactoryImpl {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final SecoFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static SecoFactory init() {
		try {
			SecoFactory theSecoFactory = (SecoFactory) EPackage.Registry.INSTANCE.getEFactory(SecoPackage.eNS_URI);
			if (theSecoFactory != null) {
				return theSecoFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SecoFactory();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public SecoFactory() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case SecoPackage.ENTRIES:
			return createEntries();
		case SecoPackage.OPERATION:
			return createOperation();
		case SecoPackage.ARGUMENT:
			return createArgument();
		case SecoPackage.ASSIGNMENT:
			return createAssignment();
		case SecoPackage.ENTITY_CLASS_DEFINITION:
			return createEntityClassDefinition();
		case SecoPackage.ENTITY_METHOD:
			return createEntityMethod();
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE:
			return createEntityMethodParamSignature();
		case SecoPackage.ENTITY_METHOD_PARAM:
			return createEntityMethodParam();
		case SecoPackage.ENTITY_CAST:
			return createEntityCast();
		case SecoPackage.YIELD:
			return createYield();
		case SecoPackage.FIELD:
			return createField();
		case SecoPackage.FIELD_VALUE:
			return createFieldValue();
		case SecoPackage.ENTITY_DEPLOYMENT_DEFINITION:
			return createEntityDeploymentDefinition();
		case SecoPackage.DEPLOYMENT_PROCEDURE:
			return createDeploymentProcedure();
		case SecoPackage.DEPLOYMENT_DEPENDENCY:
			return createDeploymentDependency();
		case SecoPackage.ENTITY_METHOD_INSTRUCTIONS:
			return createEntityMethodInstructions();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case SecoPackage.ENTITY_METHOD_PROP:
			return createEntityMethodPropFromString(eDataType, initialValue);
		case SecoPackage.TRANSFORM_DIRECTION:
			return createTransformDirectionFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case SecoPackage.ENTITY_METHOD_PROP:
			return convertEntityMethodPropToString(eDataType, instanceValue);
		case SecoPackage.TRANSFORM_DIRECTION:
			return convertTransformDirectionToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Entries createEntries() {
		Entries entries = new Entries();
		return entries;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Operation createOperation() {
		Operation operation = new Operation();
		return operation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Argument createArgument() {
		Argument argument = new Argument();
		return argument;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Assignment createAssignment() {
		Assignment assignment = new Assignment();
		return assignment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityClassDefinition createEntityClassDefinition() {
		EntityClassDefinition entityClassDefinition = new EntityClassDefinition();
		return entityClassDefinition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethod createEntityMethod() {
		EntityMethod entityMethod = new EntityMethod();
		return entityMethod;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodParamSignature createEntityMethodParamSignature() {
		EntityMethodParamSignature entityMethodParamSignature = new EntityMethodParamSignature();
		return entityMethodParamSignature;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodParam createEntityMethodParam() {
		EntityMethodParam entityMethodParam = new EntityMethodParam();
		return entityMethodParam;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityCast createEntityCast() {
		EntityCast entityCast = new EntityCast();
		return entityCast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Yield createYield() {
		Yield yield = new Yield();
		return yield;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Field createField() {
		Field field = new Field();
		return field;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FieldValue createFieldValue() {
		FieldValue fieldValue = new FieldValue();
		return fieldValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityDeploymentDefinition createEntityDeploymentDefinition() {
		EntityDeploymentDefinition entityDeploymentDefinition = new EntityDeploymentDefinition();
		return entityDeploymentDefinition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DeploymentProcedure createDeploymentProcedure() {
		DeploymentProcedure deploymentProcedure = new DeploymentProcedure();
		return deploymentProcedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DeploymentDependency createDeploymentDependency() {
		DeploymentDependency deploymentDependency = new DeploymentDependency();
		return deploymentDependency;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodInstructions createEntityMethodInstructions() {
		EntityMethodInstructions entityMethodInstructions = new EntityMethodInstructions();
		return entityMethodInstructions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodProp createEntityMethodPropFromString(EDataType eDataType, String initialValue) {
		EntityMethodProp result = EntityMethodProp.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertEntityMethodPropToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TransformDirection createTransformDirectionFromString(EDataType eDataType, String initialValue) {
		TransformDirection result = TransformDirection.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertTransformDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SecoPackage getSecoPackage() {
		return (SecoPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SecoPackage getPackage() {
		return SecoPackage.eINSTANCE;
	}

} // SecoFactory
