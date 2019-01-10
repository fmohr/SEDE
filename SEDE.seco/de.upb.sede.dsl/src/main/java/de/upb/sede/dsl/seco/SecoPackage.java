/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see de.upb.sede.dsl.seco.SecoFactory
 * @model kind="package"
 * @generated
 */
public class SecoPackage extends EPackageImpl {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String eNAME = "seco";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String eNS_URI = "http://www.upb.de/sede/dsl/Seco";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String eNS_PREFIX = "seco";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final SecoPackage eINSTANCE = de.upb.sede.dsl.seco.SecoPackage.init();

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Entries
	 * <em>Entries</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Entries
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries()
	 * @generated
	 */
	public static final int ENTRIES = 0;

	/**
	 * The feature id for the '<em><b>Instructions</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTRIES__INSTRUCTIONS = 0;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTRIES__ENTITIES = 1;

	/**
	 * The feature id for the '<em><b>Deployments</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTRIES__DEPLOYMENTS = 2;

	/**
	 * The number of structural features of the '<em>Entries</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTRIES_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Operation
	 * <em>Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Operation
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation()
	 * @generated
	 */
	public static final int OPERATION = 1;

	/**
	 * The feature id for the '<em><b>Context Field</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int OPERATION__CONTEXT_FIELD = 0;

	/**
	 * The feature id for the '<em><b>Entity Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int OPERATION__ENTITY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int OPERATION__METHOD = 2;

	/**
	 * The feature id for the '<em><b>Args</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int OPERATION__ARGS = 3;

	/**
	 * The number of structural features of the '<em>Operation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int OPERATION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Argument
	 * <em>Argument</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Argument
	 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument()
	 * @generated
	 */
	public static final int ARGUMENT = 2;

	/**
	 * The feature id for the '<em><b>Indexed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ARGUMENT__INDEXED = 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ARGUMENT__INDEX = 1;

	/**
	 * The feature id for the '<em><b>Parameter Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ARGUMENT__PARAMETER_NAME = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ARGUMENT__VALUE = 3;

	/**
	 * The number of structural features of the '<em>Argument</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ARGUMENT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Assignment
	 * <em>Assignment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Assignment
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment()
	 * @generated
	 */
	public static final int ASSIGNMENT = 3;

	/**
	 * The feature id for the '<em><b>Assigned Fields</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNMENT__ASSIGNED_FIELDS = 0;

	/**
	 * The feature id for the '<em><b>Multi Assignment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNMENT__MULTI_ASSIGNMENT = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNMENT__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Assignment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.EntityClassDefinition
	 * <em>Entity Class Definition</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition()
	 * @generated
	 */
	public static final int ENTITY_CLASS_DEFINITION = 4;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__QUALIFIED_NAME = 0;

	/**
	 * The feature id for the '<em><b>Wrapper</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__WRAPPER = 1;

	/**
	 * The feature id for the '<em><b>Wrapped Entity</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY = 2;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__EXTENSION = 3;

	/**
	 * The feature id for the '<em><b>Base Entities</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__BASE_ENTITIES = 4;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__METHODS = 5;

	/**
	 * The feature id for the '<em><b>Casts</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__CASTS = 6;

	/**
	 * The feature id for the '<em><b>Runtime Info</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION__RUNTIME_INFO = 7;

	/**
	 * The number of structural features of the '<em>Entity Class Definition</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CLASS_DEFINITION_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.EntityMethod
	 * <em>Entity Method</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityMethod
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod()
	 * @generated
	 */
	public static final int ENTITY_METHOD = 5;

	/**
	 * The feature id for the '<em><b>Property</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Method Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__METHOD_NAME = 1;

	/**
	 * The feature id for the '<em><b>Param Signature</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__PARAM_SIGNATURE = 2;

	/**
	 * The feature id for the '<em><b>Realization</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__REALIZATION = 3;

	/**
	 * The feature id for the '<em><b>Method Realization</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__METHOD_REALIZATION = 4;

	/**
	 * The feature id for the '<em><b>Runtime Info</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__RUNTIME_INFO = 5;

	/**
	 * The feature id for the '<em><b>Method Instructions</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD__METHOD_INSTRUCTIONS = 6;

	/**
	 * The number of structural features of the '<em>Entity Method</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature <em>Entity Method
	 * Param Signature</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParamSignature()
	 * @generated
	 */
	public static final int ENTITY_METHOD_PARAM_SIGNATURE = 6;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS = 1;

	/**
	 * The number of structural features of the '<em>Entity Method Param
	 * Signature</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM_SIGNATURE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.EntityMethodParam
	 * <em>Entity Method Param</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityMethodParam
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam()
	 * @generated
	 */
	public static final int ENTITY_METHOD_PARAM = 7;

	/**
	 * The feature id for the '<em><b>Final</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM__FINAL = 0;

	/**
	 * The feature id for the '<em><b>Parameter Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM__PARAMETER_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Parameter Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM__PARAMETER_NAME = 2;

	/**
	 * The feature id for the '<em><b>Value Fixed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM__VALUE_FIXED = 3;

	/**
	 * The feature id for the '<em><b>Fixed Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM__FIXED_VALUE = 4;

	/**
	 * The number of structural features of the '<em>Entity Method Param</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_PARAM_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.EntityCast <em>Entity
	 * Cast</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityCast
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast()
	 * @generated
	 */
	public static final int ENTITY_CAST = 8;

	/**
	 * The feature id for the '<em><b>Resulting Entity</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CAST__RESULTING_ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Additional Data</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CAST__ADDITIONAL_DATA = 1;

	/**
	 * The number of structural features of the '<em>Entity Cast</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_CAST_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Yield
	 * <em>Yield</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Yield
	 * @see de.upb.sede.dsl.seco.SecoPackage#getYield()
	 * @generated
	 */
	public static final int YIELD = 9;

	/**
	 * The feature id for the '<em><b>Yields</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int YIELD__YIELDS = 0;

	/**
	 * The feature id for the '<em><b>Multi Yield</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int YIELD__MULTI_YIELD = 1;

	/**
	 * The number of structural features of the '<em>Yield</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int YIELD_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.Field
	 * <em>Field</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.Field
	 * @see de.upb.sede.dsl.seco.SecoPackage#getField()
	 * @generated
	 */
	public static final int FIELD = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Dereference</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD__DEREFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Member</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD__MEMBER = 2;

	/**
	 * The number of structural features of the '<em>Field</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.FieldValue <em>Field
	 * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.FieldValue
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue()
	 * @generated
	 */
	public static final int FIELD_VALUE = 11;

	/**
	 * The feature id for the '<em><b>Cast</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__CAST = 0;

	/**
	 * The feature id for the '<em><b>Cast Target</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__CAST_TARGET = 1;

	/**
	 * The feature id for the '<em><b>Cast Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__CAST_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__NUMBER = 3;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__STRING = 4;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__BOOL = 5;

	/**
	 * The feature id for the '<em><b>Null</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__NULL = 6;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__OPERATION = 7;

	/**
	 * The feature id for the '<em><b>Field</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE__FIELD = 8;

	/**
	 * The number of structural features of the '<em>Field Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition <em>Entity Deployment
	 * Definition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition()
	 * @generated
	 */
	public static final int ENTITY_DEPLOYMENT_DEFINITION = 12;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_DEPLOYMENT_DEFINITION__PROCEDURES = 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_DEPLOYMENT_DEFINITION__DEPENDENCIES = 1;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_DEPLOYMENT_DEFINITION__QUALIFIED_NAME = 2;

	/**
	 * The number of structural features of the '<em>Entity Deployment
	 * Definition</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_DEPLOYMENT_DEFINITION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.DeploymentProcedure
	 * <em>Deployment Procedure</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure()
	 * @generated
	 */
	public static final int DEPLOYMENT_PROCEDURE = 13;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_PROCEDURE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Act</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_PROCEDURE__ACT = 1;

	/**
	 * The feature id for the '<em><b>Fetch</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_PROCEDURE__FETCH = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_PROCEDURE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Deployment Procedure</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_PROCEDURE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.DeploymentDependency
	 * <em>Deployment Dependency</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.DeploymentDependency
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentDependency()
	 * @generated
	 */
	public static final int DEPLOYMENT_DEPENDENCY = 14;

	/**
	 * The feature id for the '<em><b>Deployment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_DEPENDENCY__DEPLOYMENT = 0;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_DEPENDENCY__ORDER = 1;

	/**
	 * The number of structural features of the '<em>Deployment Dependency</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int DEPLOYMENT_DEPENDENCY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions <em>Entity Method
	 * Instructions</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityMethodInstructions
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodInstructions()
	 * @generated
	 */
	public static final int ENTITY_METHOD_INSTRUCTIONS = 15;

	/**
	 * The feature id for the '<em><b>Instructions</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_INSTRUCTIONS__INSTRUCTIONS = 0;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_INSTRUCTIONS__ORDER = 1;

	/**
	 * The number of structural features of the '<em>Entity Method
	 * Instructions</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	public static final int ENTITY_METHOD_INSTRUCTIONS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.EntityMethodProp
	 * <em>Entity Method Prop</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see de.upb.sede.dsl.seco.EntityMethodProp
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodProp()
	 * @generated
	 */
	public static final int ENTITY_METHOD_PROP = 16;

	/**
	 * The meta object id for the '{@link de.upb.sede.dsl.seco.TransformDirection
	 * <em>Transform Direction</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @see de.upb.sede.dsl.seco.SecoPackage#getTransformDirection()
	 * @generated
	 */
	public static final int TRANSFORM_DIRECTION = 17;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entriesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass operationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass argumentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass assignmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityClassDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityMethodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityMethodParamSignatureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityMethodParamEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityCastEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass yieldEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fieldEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fieldValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityDeploymentDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass deploymentProcedureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass deploymentDependencyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass entityMethodInstructionsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum entityMethodPropEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum transformDirectionEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.upb.sede.dsl.seco.SecoPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SecoPackage() {
		super(eNS_URI, SecoFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link SecoPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SecoPackage init() {
		if (isInited)
			return (SecoPackage) EPackage.Registry.INSTANCE.getEPackage(SecoPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSecoPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		SecoPackage theSecoPackage = registeredSecoPackage instanceof SecoPackage ? (SecoPackage) registeredSecoPackage
				: new SecoPackage();

		isInited = true;

		// Create package meta-data objects
		theSecoPackage.createPackageContents();

		// Initialize created meta-data
		theSecoPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSecoPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SecoPackage.eNS_URI, theSecoPackage);
		return theSecoPackage;
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Entries
	 * <em>Entries</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entries</em>'.
	 * @see de.upb.sede.dsl.seco.Entries
	 * @generated
	 */
	public EClass getEntries() {
		return entriesEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Entries#getInstructions <em>Instructions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Instructions</em>'.
	 * @see de.upb.sede.dsl.seco.Entries#getInstructions()
	 * @see #getEntries()
	 * @generated
	 */
	public EReference getEntries_Instructions() {
		return (EReference) entriesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Entries#getEntities <em>Entities</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Entities</em>'.
	 * @see de.upb.sede.dsl.seco.Entries#getEntities()
	 * @see #getEntries()
	 * @generated
	 */
	public EReference getEntries_Entities() {
		return (EReference) entriesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Entries#getDeployments <em>Deployments</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Deployments</em>'.
	 * @see de.upb.sede.dsl.seco.Entries#getDeployments()
	 * @see #getEntries()
	 * @generated
	 */
	public EReference getEntries_Deployments() {
		return (EReference) entriesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Operation
	 * <em>Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see de.upb.sede.dsl.seco.Operation
	 * @generated
	 */
	public EClass getOperation() {
		return operationEClass;
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.Operation#getContextField <em>Context
	 * Field</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Context
	 *         Field</em>'.
	 * @see de.upb.sede.dsl.seco.Operation#getContextField()
	 * @see #getOperation()
	 * @generated
	 */
	public EReference getOperation_ContextField() {
		return (EReference) operationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Operation#getEntityName <em>Entity Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Entity Name</em>'.
	 * @see de.upb.sede.dsl.seco.Operation#getEntityName()
	 * @see #getOperation()
	 * @generated
	 */
	public EAttribute getOperation_EntityName() {
		return (EAttribute) operationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Operation#getMethod <em>Method</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see de.upb.sede.dsl.seco.Operation#getMethod()
	 * @see #getOperation()
	 * @generated
	 */
	public EAttribute getOperation_Method() {
		return (EAttribute) operationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Operation#getArgs <em>Args</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Args</em>'.
	 * @see de.upb.sede.dsl.seco.Operation#getArgs()
	 * @see #getOperation()
	 * @generated
	 */
	public EReference getOperation_Args() {
		return (EReference) operationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Argument
	 * <em>Argument</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see de.upb.sede.dsl.seco.Argument
	 * @generated
	 */
	public EClass getArgument() {
		return argumentEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Argument#isIndexed <em>Indexed</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Indexed</em>'.
	 * @see de.upb.sede.dsl.seco.Argument#isIndexed()
	 * @see #getArgument()
	 * @generated
	 */
	public EAttribute getArgument_Indexed() {
		return (EAttribute) argumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Argument#getIndex <em>Index</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see de.upb.sede.dsl.seco.Argument#getIndex()
	 * @see #getArgument()
	 * @generated
	 */
	public EAttribute getArgument_Index() {
		return (EAttribute) argumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Argument#getParameterName <em>Parameter
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Parameter Name</em>'.
	 * @see de.upb.sede.dsl.seco.Argument#getParameterName()
	 * @see #getArgument()
	 * @generated
	 */
	public EAttribute getArgument_ParameterName() {
		return (EAttribute) argumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.Argument#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see de.upb.sede.dsl.seco.Argument#getValue()
	 * @see #getArgument()
	 * @generated
	 */
	public EReference getArgument_Value() {
		return (EReference) argumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Assignment
	 * <em>Assignment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Assignment</em>'.
	 * @see de.upb.sede.dsl.seco.Assignment
	 * @generated
	 */
	public EClass getAssignment() {
		return assignmentEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Assignment#getAssignedFields <em>Assigned
	 * Fields</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Assigned
	 *         Fields</em>'.
	 * @see de.upb.sede.dsl.seco.Assignment#getAssignedFields()
	 * @see #getAssignment()
	 * @generated
	 */
	public EReference getAssignment_AssignedFields() {
		return (EReference) assignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Assignment#isMultiAssignment <em>Multi
	 * Assignment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Multi Assignment</em>'.
	 * @see de.upb.sede.dsl.seco.Assignment#isMultiAssignment()
	 * @see #getAssignment()
	 * @generated
	 */
	public EAttribute getAssignment_MultiAssignment() {
		return (EAttribute) assignmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.Assignment#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see de.upb.sede.dsl.seco.Assignment#getValue()
	 * @see #getAssignment()
	 * @generated
	 */
	public EReference getAssignment_Value() {
		return (EReference) assignmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition <em>Entity Class
	 * Definition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Class Definition</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition
	 * @generated
	 */
	public EClass getEntityClassDefinition() {
		return entityClassDefinitionEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName
	 * <em>Qualified Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_QualifiedName() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper
	 * <em>Wrapper</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Wrapper</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_Wrapper() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity
	 * <em>Wrapped Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Wrapped Entity</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_WrappedEntity() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isExtension
	 * <em>Extension</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Extension</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#isExtension()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_Extension() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute list
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getBaseEntities <em>Base
	 * Entities</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Base Entities</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getBaseEntities()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_BaseEntities() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getMethods
	 * <em>Methods</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Methods</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getMethods()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EReference getEntityClassDefinition_Methods() {
		return (EReference) entityClassDefinitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getCasts <em>Casts</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Casts</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getCasts()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EReference getEntityClassDefinition_Casts() {
		return (EReference) entityClassDefinitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getRuntimeInfo <em>Runtime
	 * Info</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Runtime Info</em>'.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition#getRuntimeInfo()
	 * @see #getEntityClassDefinition()
	 * @generated
	 */
	public EAttribute getEntityClassDefinition_RuntimeInfo() {
		return (EAttribute) entityClassDefinitionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.EntityMethod
	 * <em>Entity Method</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Method</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod
	 * @generated
	 */
	public EClass getEntityMethod() {
		return entityMethodEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Property</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getProperty()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EAttribute getEntityMethod_Property() {
		return (EAttribute) entityMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getMethodName <em>Method
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Method Name</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getMethodName()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EAttribute getEntityMethod_MethodName() {
		return (EAttribute) entityMethodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getParamSignature <em>Param
	 * Signature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Param
	 *         Signature</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getParamSignature()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EReference getEntityMethod_ParamSignature() {
		return (EReference) entityMethodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#isRealization
	 * <em>Realization</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Realization</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#isRealization()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EAttribute getEntityMethod_Realization() {
		return (EAttribute) entityMethodEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getMethodRealization <em>Method
	 * Realization</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Method Realization</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getMethodRealization()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EAttribute getEntityMethod_MethodRealization() {
		return (EAttribute) entityMethodEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getRuntimeInfo <em>Runtime
	 * Info</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Runtime Info</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getRuntimeInfo()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EAttribute getEntityMethod_RuntimeInfo() {
		return (EAttribute) entityMethodEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getMethodInstructions <em>Method
	 * Instructions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Method
	 *         Instructions</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethod#getMethodInstructions()
	 * @see #getEntityMethod()
	 * @generated
	 */
	public EReference getEntityMethod_MethodInstructions() {
		return (EReference) entityMethodEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature <em>Entity Method
	 * Param Signature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Method Param Signature</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature
	 * @generated
	 */
	public EClass getEntityMethodParamSignature() {
		return entityMethodParamSignatureEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature#getParameters
	 * <em>Parameters</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Parameters</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature#getParameters()
	 * @see #getEntityMethodParamSignature()
	 * @generated
	 */
	public EReference getEntityMethodParamSignature_Parameters() {
		return (EReference) entityMethodParamSignatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature#getOutputs
	 * <em>Outputs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Outputs</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature#getOutputs()
	 * @see #getEntityMethodParamSignature()
	 * @generated
	 */
	public EReference getEntityMethodParamSignature_Outputs() {
		return (EReference) entityMethodParamSignatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam <em>Entity Method
	 * Param</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Method Param</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam
	 * @generated
	 */
	public EClass getEntityMethodParam() {
		return entityMethodParamEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#isFinal <em>Final</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam#isFinal()
	 * @see #getEntityMethodParam()
	 * @generated
	 */
	public EAttribute getEntityMethodParam_Final() {
		return (EAttribute) entityMethodParamEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterType <em>Parameter
	 * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Parameter Type</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam#getParameterType()
	 * @see #getEntityMethodParam()
	 * @generated
	 */
	public EAttribute getEntityMethodParam_ParameterType() {
		return (EAttribute) entityMethodParamEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterName <em>Parameter
	 * Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Parameter Name</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam#getParameterName()
	 * @see #getEntityMethodParam()
	 * @generated
	 */
	public EAttribute getEntityMethodParam_ParameterName() {
		return (EAttribute) entityMethodParamEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed <em>Value
	 * Fixed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value Fixed</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed()
	 * @see #getEntityMethodParam()
	 * @generated
	 */
	public EAttribute getEntityMethodParam_ValueFixed() {
		return (EAttribute) entityMethodParamEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue <em>Fixed
	 * Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Fixed Value</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue()
	 * @see #getEntityMethodParam()
	 * @generated
	 */
	public EReference getEntityMethodParam_FixedValue() {
		return (EReference) entityMethodParamEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.EntityCast
	 * <em>Entity Cast</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Cast</em>'.
	 * @see de.upb.sede.dsl.seco.EntityCast
	 * @generated
	 */
	public EClass getEntityCast() {
		return entityCastEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityCast#getResultingEntity <em>Resulting
	 * Entity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Resulting Entity</em>'.
	 * @see de.upb.sede.dsl.seco.EntityCast#getResultingEntity()
	 * @see #getEntityCast()
	 * @generated
	 */
	public EAttribute getEntityCast_ResultingEntity() {
		return (EAttribute) entityCastEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityCast#getAdditionalData <em>Additional
	 * Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Additional Data</em>'.
	 * @see de.upb.sede.dsl.seco.EntityCast#getAdditionalData()
	 * @see #getEntityCast()
	 * @generated
	 */
	public EAttribute getEntityCast_AdditionalData() {
		return (EAttribute) entityCastEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Yield
	 * <em>Yield</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Yield</em>'.
	 * @see de.upb.sede.dsl.seco.Yield
	 * @generated
	 */
	public EClass getYield() {
		return yieldEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.Yield#getYields <em>Yields</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Yields</em>'.
	 * @see de.upb.sede.dsl.seco.Yield#getYields()
	 * @see #getYield()
	 * @generated
	 */
	public EReference getYield_Yields() {
		return (EReference) yieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Yield#isMultiYield <em>Multi Yield</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Multi Yield</em>'.
	 * @see de.upb.sede.dsl.seco.Yield#isMultiYield()
	 * @see #getYield()
	 * @generated
	 */
	public EAttribute getYield_MultiYield() {
		return (EAttribute) yieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.Field
	 * <em>Field</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Field</em>'.
	 * @see de.upb.sede.dsl.seco.Field
	 * @generated
	 */
	public EClass getField() {
		return fieldEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Field#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.upb.sede.dsl.seco.Field#getName()
	 * @see #getField()
	 * @generated
	 */
	public EAttribute getField_Name() {
		return (EAttribute) fieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.Field#isDereference <em>Dereference</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Dereference</em>'.
	 * @see de.upb.sede.dsl.seco.Field#isDereference()
	 * @see #getField()
	 * @generated
	 */
	public EAttribute getField_Dereference() {
		return (EAttribute) fieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.Field#getMember <em>Member</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Member</em>'.
	 * @see de.upb.sede.dsl.seco.Field#getMember()
	 * @see #getField()
	 * @generated
	 */
	public EReference getField_Member() {
		return (EReference) fieldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link de.upb.sede.dsl.seco.FieldValue
	 * <em>Field Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Field Value</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue
	 * @generated
	 */
	public EClass getFieldValue() {
		return fieldValueEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#isCast <em>Cast</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Cast</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#isCast()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_Cast() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getCastTarget <em>Cast Target</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Cast Target</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getCastTarget()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_CastTarget() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getCastValue <em>Cast Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Cast Value</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getCastValue()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EReference getFieldValue_CastValue() {
		return (EReference) fieldValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getNumber <em>Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getNumber()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_Number() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getString <em>String</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>String</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getString()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_String() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getBool <em>Bool</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Bool</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getBool()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_Bool() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.FieldValue#isNull <em>Null</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Null</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#isNull()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EAttribute getFieldValue_Null() {
		return (EAttribute) fieldValueEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Operation</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getOperation()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EReference getFieldValue_Operation() {
		return (EReference) fieldValueEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * Returns the meta object for the containment reference
	 * '{@link de.upb.sede.dsl.seco.FieldValue#getField <em>Field</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Field</em>'.
	 * @see de.upb.sede.dsl.seco.FieldValue#getField()
	 * @see #getFieldValue()
	 * @generated
	 */
	public EReference getFieldValue_Field() {
		return (EReference) fieldValueEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition <em>Entity Deployment
	 * Definition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Deployment Definition</em>'.
	 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition
	 * @generated
	 */
	public EClass getEntityDeploymentDefinition() {
		return entityDeploymentDefinitionEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getProcedures
	 * <em>Procedures</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Procedures</em>'.
	 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition#getProcedures()
	 * @see #getEntityDeploymentDefinition()
	 * @generated
	 */
	public EReference getEntityDeploymentDefinition_Procedures() {
		return (EReference) entityDeploymentDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getDependencies
	 * <em>Dependencies</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Dependencies</em>'.
	 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition#getDependencies()
	 * @see #getEntityDeploymentDefinition()
	 * @generated
	 */
	public EReference getEntityDeploymentDefinition_Dependencies() {
		return (EReference) entityDeploymentDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getQualifiedName
	 * <em>Qualified Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition#getQualifiedName()
	 * @see #getEntityDeploymentDefinition()
	 * @generated
	 */
	public EAttribute getEntityDeploymentDefinition_QualifiedName() {
		return (EAttribute) entityDeploymentDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure <em>Deployment
	 * Procedure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Deployment Procedure</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure
	 * @generated
	 */
	public EClass getDeploymentProcedure() {
		return deploymentProcedureEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure#getSource()
	 * @see #getDeploymentProcedure()
	 * @generated
	 */
	public EAttribute getDeploymentProcedure_Source() {
		return (EAttribute) deploymentProcedureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getAct <em>Act</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Act</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure#getAct()
	 * @see #getDeploymentProcedure()
	 * @generated
	 */
	public EAttribute getDeploymentProcedure_Act() {
		return (EAttribute) deploymentProcedureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getFetch <em>Fetch</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Fetch</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure#getFetch()
	 * @see #getDeploymentProcedure()
	 * @generated
	 */
	public EAttribute getDeploymentProcedure_Fetch() {
		return (EAttribute) deploymentProcedureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentProcedure#getName()
	 * @see #getDeploymentProcedure()
	 * @generated
	 */
	public EAttribute getDeploymentProcedure_Name() {
		return (EAttribute) deploymentProcedureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.DeploymentDependency <em>Deployment
	 * Dependency</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Deployment Dependency</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentDependency
	 * @generated
	 */
	public EClass getDeploymentDependency() {
		return deploymentDependencyEClass;
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentDependency#getDeployment
	 * <em>Deployment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deployment</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentDependency#getDeployment()
	 * @see #getDeploymentDependency()
	 * @generated
	 */
	public EAttribute getDeploymentDependency_Deployment() {
		return (EAttribute) deploymentDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.DeploymentDependency#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see de.upb.sede.dsl.seco.DeploymentDependency#getOrder()
	 * @see #getDeploymentDependency()
	 * @generated
	 */
	public EAttribute getDeploymentDependency_Order() {
		return (EAttribute) deploymentDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for class
	 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions <em>Entity Method
	 * Instructions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Entity Method Instructions</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodInstructions
	 * @generated
	 */
	public EClass getEntityMethodInstructions() {
		return entityMethodInstructionsEClass;
	}

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions#getInstructions
	 * <em>Instructions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Instructions</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodInstructions#getInstructions()
	 * @see #getEntityMethodInstructions()
	 * @generated
	 */
	public EReference getEntityMethodInstructions_Instructions() {
		return (EReference) entityMethodInstructionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the attribute
	 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions#getOrder
	 * <em>Order</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodInstructions#getOrder()
	 * @see #getEntityMethodInstructions()
	 * @generated
	 */
	public EAttribute getEntityMethodInstructions_Order() {
		return (EAttribute) entityMethodInstructionsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for enum
	 * '{@link de.upb.sede.dsl.seco.EntityMethodProp <em>Entity Method Prop</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Entity Method Prop</em>'.
	 * @see de.upb.sede.dsl.seco.EntityMethodProp
	 * @generated
	 */
	public EEnum getEntityMethodProp() {
		return entityMethodPropEEnum;
	}

	/**
	 * Returns the meta object for enum
	 * '{@link de.upb.sede.dsl.seco.TransformDirection <em>Transform
	 * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Transform Direction</em>'.
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @generated
	 */
	public EEnum getTransformDirection() {
		return transformDirectionEEnum;
	}

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public SecoFactory getSecoFactory() {
		return (SecoFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		entriesEClass = createEClass(ENTRIES);
		createEReference(entriesEClass, ENTRIES__INSTRUCTIONS);
		createEReference(entriesEClass, ENTRIES__ENTITIES);
		createEReference(entriesEClass, ENTRIES__DEPLOYMENTS);

		operationEClass = createEClass(OPERATION);
		createEReference(operationEClass, OPERATION__CONTEXT_FIELD);
		createEAttribute(operationEClass, OPERATION__ENTITY_NAME);
		createEAttribute(operationEClass, OPERATION__METHOD);
		createEReference(operationEClass, OPERATION__ARGS);

		argumentEClass = createEClass(ARGUMENT);
		createEAttribute(argumentEClass, ARGUMENT__INDEXED);
		createEAttribute(argumentEClass, ARGUMENT__INDEX);
		createEAttribute(argumentEClass, ARGUMENT__PARAMETER_NAME);
		createEReference(argumentEClass, ARGUMENT__VALUE);

		assignmentEClass = createEClass(ASSIGNMENT);
		createEReference(assignmentEClass, ASSIGNMENT__ASSIGNED_FIELDS);
		createEAttribute(assignmentEClass, ASSIGNMENT__MULTI_ASSIGNMENT);
		createEReference(assignmentEClass, ASSIGNMENT__VALUE);

		entityClassDefinitionEClass = createEClass(ENTITY_CLASS_DEFINITION);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__QUALIFIED_NAME);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__WRAPPER);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__EXTENSION);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__BASE_ENTITIES);
		createEReference(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__METHODS);
		createEReference(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__CASTS);
		createEAttribute(entityClassDefinitionEClass, ENTITY_CLASS_DEFINITION__RUNTIME_INFO);

		entityMethodEClass = createEClass(ENTITY_METHOD);
		createEAttribute(entityMethodEClass, ENTITY_METHOD__PROPERTY);
		createEAttribute(entityMethodEClass, ENTITY_METHOD__METHOD_NAME);
		createEReference(entityMethodEClass, ENTITY_METHOD__PARAM_SIGNATURE);
		createEAttribute(entityMethodEClass, ENTITY_METHOD__REALIZATION);
		createEAttribute(entityMethodEClass, ENTITY_METHOD__METHOD_REALIZATION);
		createEAttribute(entityMethodEClass, ENTITY_METHOD__RUNTIME_INFO);
		createEReference(entityMethodEClass, ENTITY_METHOD__METHOD_INSTRUCTIONS);

		entityMethodParamSignatureEClass = createEClass(ENTITY_METHOD_PARAM_SIGNATURE);
		createEReference(entityMethodParamSignatureEClass, ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS);
		createEReference(entityMethodParamSignatureEClass, ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS);

		entityMethodParamEClass = createEClass(ENTITY_METHOD_PARAM);
		createEAttribute(entityMethodParamEClass, ENTITY_METHOD_PARAM__FINAL);
		createEAttribute(entityMethodParamEClass, ENTITY_METHOD_PARAM__PARAMETER_TYPE);
		createEAttribute(entityMethodParamEClass, ENTITY_METHOD_PARAM__PARAMETER_NAME);
		createEAttribute(entityMethodParamEClass, ENTITY_METHOD_PARAM__VALUE_FIXED);
		createEReference(entityMethodParamEClass, ENTITY_METHOD_PARAM__FIXED_VALUE);

		entityCastEClass = createEClass(ENTITY_CAST);
		createEAttribute(entityCastEClass, ENTITY_CAST__RESULTING_ENTITY);
		createEAttribute(entityCastEClass, ENTITY_CAST__ADDITIONAL_DATA);

		yieldEClass = createEClass(YIELD);
		createEReference(yieldEClass, YIELD__YIELDS);
		createEAttribute(yieldEClass, YIELD__MULTI_YIELD);

		fieldEClass = createEClass(FIELD);
		createEAttribute(fieldEClass, FIELD__NAME);
		createEAttribute(fieldEClass, FIELD__DEREFERENCE);
		createEReference(fieldEClass, FIELD__MEMBER);

		fieldValueEClass = createEClass(FIELD_VALUE);
		createEAttribute(fieldValueEClass, FIELD_VALUE__CAST);
		createEAttribute(fieldValueEClass, FIELD_VALUE__CAST_TARGET);
		createEReference(fieldValueEClass, FIELD_VALUE__CAST_VALUE);
		createEAttribute(fieldValueEClass, FIELD_VALUE__NUMBER);
		createEAttribute(fieldValueEClass, FIELD_VALUE__STRING);
		createEAttribute(fieldValueEClass, FIELD_VALUE__BOOL);
		createEAttribute(fieldValueEClass, FIELD_VALUE__NULL);
		createEReference(fieldValueEClass, FIELD_VALUE__OPERATION);
		createEReference(fieldValueEClass, FIELD_VALUE__FIELD);

		entityDeploymentDefinitionEClass = createEClass(ENTITY_DEPLOYMENT_DEFINITION);
		createEReference(entityDeploymentDefinitionEClass, ENTITY_DEPLOYMENT_DEFINITION__PROCEDURES);
		createEReference(entityDeploymentDefinitionEClass, ENTITY_DEPLOYMENT_DEFINITION__DEPENDENCIES);
		createEAttribute(entityDeploymentDefinitionEClass, ENTITY_DEPLOYMENT_DEFINITION__QUALIFIED_NAME);

		deploymentProcedureEClass = createEClass(DEPLOYMENT_PROCEDURE);
		createEAttribute(deploymentProcedureEClass, DEPLOYMENT_PROCEDURE__SOURCE);
		createEAttribute(deploymentProcedureEClass, DEPLOYMENT_PROCEDURE__ACT);
		createEAttribute(deploymentProcedureEClass, DEPLOYMENT_PROCEDURE__FETCH);
		createEAttribute(deploymentProcedureEClass, DEPLOYMENT_PROCEDURE__NAME);

		deploymentDependencyEClass = createEClass(DEPLOYMENT_DEPENDENCY);
		createEAttribute(deploymentDependencyEClass, DEPLOYMENT_DEPENDENCY__DEPLOYMENT);
		createEAttribute(deploymentDependencyEClass, DEPLOYMENT_DEPENDENCY__ORDER);

		entityMethodInstructionsEClass = createEClass(ENTITY_METHOD_INSTRUCTIONS);
		createEReference(entityMethodInstructionsEClass, ENTITY_METHOD_INSTRUCTIONS__INSTRUCTIONS);
		createEAttribute(entityMethodInstructionsEClass, ENTITY_METHOD_INSTRUCTIONS__ORDER);

		// Create enums
		entityMethodPropEEnum = createEEnum(ENTITY_METHOD_PROP);
		transformDirectionEEnum = createEEnum(TRANSFORM_DIRECTION);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(entriesEClass, Entries.class, "Entries", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntries_Instructions(), ecorePackage.getEObject(), null, "instructions", null, 0, -1,
				Entries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntries_Entities(), this.getEntityClassDefinition(), null, "entities", null, 0, -1,
				Entries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntries_Deployments(), this.getEntityDeploymentDefinition(), null, "deployments", null, 0, -1,
				Entries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationEClass, Operation.class, "Operation", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperation_ContextField(), this.getField(), null, "contextField", null, 0, 1, Operation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperation_EntityName(), ecorePackage.getEString(), "entityName", null, 0, 1, Operation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperation_Method(), ecorePackage.getEString(), "method", null, 0, 1, Operation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperation_Args(), this.getArgument(), null, "args", null, 0, -1, Operation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArgument_Indexed(), ecorePackage.getEBoolean(), "indexed", null, 0, 1, Argument.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgument_Index(), ecorePackage.getEInt(), "index", null, 0, 1, Argument.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgument_ParameterName(), ecorePackage.getEString(), "parameterName", null, 0, 1,
				Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getArgument_Value(), this.getFieldValue(), null, "value", null, 0, 1, Argument.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(assignmentEClass, Assignment.class, "Assignment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssignment_AssignedFields(), this.getField(), null, "assignedFields", null, 0, -1,
				Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssignment_MultiAssignment(), ecorePackage.getEBoolean(), "multiAssignment", null, 0, 1,
				Assignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getAssignment_Value(), this.getFieldValue(), null, "value", null, 0, 1, Assignment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityClassDefinitionEClass, EntityClassDefinition.class, "EntityClassDefinition", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityClassDefinition_QualifiedName(), ecorePackage.getEString(), "qualifiedName", null, 0, 1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityClassDefinition_Wrapper(), ecorePackage.getEBoolean(), "wrapper", null, 0, 1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityClassDefinition_WrappedEntity(), ecorePackage.getEString(), "wrappedEntity", null, 0, 1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityClassDefinition_Extension(), ecorePackage.getEBoolean(), "extension", null, 0, 1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityClassDefinition_BaseEntities(), ecorePackage.getEString(), "baseEntities", null, 0, -1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				!IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityClassDefinition_Methods(), this.getEntityMethod(), null, "methods", null, 0, -1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityClassDefinition_Casts(), this.getEntityCast(), null, "casts", null, 0, -1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityClassDefinition_RuntimeInfo(), ecorePackage.getEString(), "runtimeInfo", "{}", 0, 1,
				EntityClassDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityMethodEClass, EntityMethod.class, "EntityMethod", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityMethod_Property(), this.getEntityMethodProp(), "property", "mutating", 0, 1,
				EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethod_MethodName(), ecorePackage.getEString(), "methodName", null, 0, 1,
				EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMethod_ParamSignature(), this.getEntityMethodParamSignature(), null, "paramSignature",
				null, 0, 1, EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethod_Realization(), ecorePackage.getEBoolean(), "realization", null, 0, 1,
				EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethod_MethodRealization(), ecorePackage.getEString(), "methodRealization", null, 0, 1,
				EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethod_RuntimeInfo(), ecorePackage.getEString(), "runtimeInfo", "{}", 0, 1,
				EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMethod_MethodInstructions(), this.getEntityMethodInstructions(), null,
				"methodInstructions", null, 0, -1, EntityMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityMethodParamSignatureEClass, EntityMethodParamSignature.class, "EntityMethodParamSignature",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityMethodParamSignature_Parameters(), this.getEntityMethodParam(), null, "parameters",
				null, 0, -1, EntityMethodParamSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMethodParamSignature_Outputs(), this.getEntityMethodParam(), null, "outputs", null, 0,
				-1, EntityMethodParamSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityMethodParamEClass, EntityMethodParam.class, "EntityMethodParam", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityMethodParam_Final(), ecorePackage.getEBoolean(), "final", null, 0, 1,
				EntityMethodParam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethodParam_ParameterType(), ecorePackage.getEString(), "parameterType", null, 0, 1,
				EntityMethodParam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethodParam_ParameterName(), ecorePackage.getEString(), "parameterName", null, 0, 1,
				EntityMethodParam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethodParam_ValueFixed(), ecorePackage.getEBoolean(), "valueFixed", null, 0, 1,
				EntityMethodParam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getEntityMethodParam_FixedValue(), this.getFieldValue(), null, "fixedValue", null, 0, 1,
				EntityMethodParam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityCastEClass, EntityCast.class, "EntityCast", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityCast_ResultingEntity(), ecorePackage.getEString(), "resultingEntity", null, 0, 1,
				EntityCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityCast_AdditionalData(), ecorePackage.getEString(), "additionalData", null, 0, 1,
				EntityCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(yieldEClass, Yield.class, "Yield", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getYield_Yields(), this.getFieldValue(), null, "yields", null, 0, -1, Yield.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getYield_MultiYield(), ecorePackage.getEBoolean(), "multiYield", null, 0, 1, Yield.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldEClass, Field.class, "Field", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getField_Name(), ecorePackage.getEString(), "name", null, 0, 1, Field.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_Dereference(), ecorePackage.getEBoolean(), "dereference", null, 0, 1, Field.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getField_Member(), this.getField(), null, "member", null, 0, 1, Field.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(fieldValueEClass, FieldValue.class, "FieldValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldValue_Cast(), ecorePackage.getEBoolean(), "cast", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldValue_CastTarget(), ecorePackage.getEString(), "castTarget", null, 0, 1,
				FieldValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getFieldValue_CastValue(), this.getFieldValue(), null, "castValue", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldValue_Number(), ecorePackage.getEString(), "number", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldValue_String(), ecorePackage.getEString(), "string", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldValue_Bool(), ecorePackage.getEString(), "bool", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldValue_Null(), ecorePackage.getEBoolean(), "null", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldValue_Operation(), this.getOperation(), null, "operation", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFieldValue_Field(), this.getField(), null, "field", null, 0, 1, FieldValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityDeploymentDefinitionEClass, EntityDeploymentDefinition.class, "EntityDeploymentDefinition",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityDeploymentDefinition_Procedures(), this.getDeploymentProcedure(), null, "procedures",
				null, 0, -1, EntityDeploymentDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDeploymentDefinition_Dependencies(), this.getDeploymentDependency(), null,
				"dependencies", null, 0, -1, EntityDeploymentDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDeploymentDefinition_QualifiedName(), ecorePackage.getEString(), "qualifiedName", null,
				0, 1, EntityDeploymentDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentProcedureEClass, DeploymentProcedure.class, "DeploymentProcedure", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeploymentProcedure_Source(), ecorePackage.getEString(), "source", null, 0, 1,
				DeploymentProcedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentProcedure_Act(), ecorePackage.getEString(), "act", null, 0, 1,
				DeploymentProcedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentProcedure_Fetch(), ecorePackage.getEString(), "fetch", null, 0, 1,
				DeploymentProcedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentProcedure_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				DeploymentProcedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deploymentDependencyEClass, DeploymentDependency.class, "DeploymentDependency", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeploymentDependency_Deployment(), ecorePackage.getEString(), "deployment", null, 0, 1,
				DeploymentDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeploymentDependency_Order(), ecorePackage.getEInt(), "order", "-100", 0, 1,
				DeploymentDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityMethodInstructionsEClass, EntityMethodInstructions.class, "EntityMethodInstructions",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityMethodInstructions_Instructions(), ecorePackage.getEObject(), null, "instructions",
				null, 0, -1, EntityMethodInstructions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityMethodInstructions_Order(), ecorePackage.getEInt(), "order", "5", 0, 1,
				EntityMethodInstructions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(entityMethodPropEEnum, EntityMethodProp.class, "EntityMethodProp");
		addEEnumLiteral(entityMethodPropEEnum, EntityMethodProp.STATIC);
		addEEnumLiteral(entityMethodPropEEnum, EntityMethodProp.PURE);
		addEEnumLiteral(entityMethodPropEEnum, EntityMethodProp.MUTATING);

		initEEnum(transformDirectionEEnum, TransformDirection.class, "TransformDirection");
		addEEnumLiteral(transformDirectionEEnum, TransformDirection.BI);
		addEEnumLiteral(transformDirectionEEnum, TransformDirection.TO);
		addEEnumLiteral(transformDirectionEEnum, TransformDirection.FROM);

		// Create resource
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public interface Literals {
		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Entries
		 * <em>Entries</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Entries
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries()
		 * @generated
		 */
		public static final EClass ENTRIES = eINSTANCE.getEntries();

		/**
		 * The meta object literal for the '<em><b>Instructions</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTRIES__INSTRUCTIONS = eINSTANCE.getEntries_Instructions();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTRIES__ENTITIES = eINSTANCE.getEntries_Entities();

		/**
		 * The meta object literal for the '<em><b>Deployments</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTRIES__DEPLOYMENTS = eINSTANCE.getEntries_Deployments();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Operation
		 * <em>Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Operation
		 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation()
		 * @generated
		 */
		public static final EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Context Field</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference OPERATION__CONTEXT_FIELD = eINSTANCE.getOperation_ContextField();

		/**
		 * The meta object literal for the '<em><b>Entity Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute OPERATION__ENTITY_NAME = eINSTANCE.getOperation_EntityName();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute OPERATION__METHOD = eINSTANCE.getOperation_Method();

		/**
		 * The meta object literal for the '<em><b>Args</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference OPERATION__ARGS = eINSTANCE.getOperation_Args();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Argument
		 * <em>Argument</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Argument
		 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument()
		 * @generated
		 */
		public static final EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Indexed</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ARGUMENT__INDEXED = eINSTANCE.getArgument_Indexed();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ARGUMENT__INDEX = eINSTANCE.getArgument_Index();

		/**
		 * The meta object literal for the '<em><b>Parameter Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ARGUMENT__PARAMETER_NAME = eINSTANCE.getArgument_ParameterName();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ARGUMENT__VALUE = eINSTANCE.getArgument_Value();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Assignment
		 * <em>Assignment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Assignment
		 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment()
		 * @generated
		 */
		public static final EClass ASSIGNMENT = eINSTANCE.getAssignment();

		/**
		 * The meta object literal for the '<em><b>Assigned Fields</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ASSIGNMENT__ASSIGNED_FIELDS = eINSTANCE.getAssignment_AssignedFields();

		/**
		 * The meta object literal for the '<em><b>Multi Assignment</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ASSIGNMENT__MULTI_ASSIGNMENT = eINSTANCE.getAssignment_MultiAssignment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ASSIGNMENT__VALUE = eINSTANCE.getAssignment_Value();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition <em>Entity Class
		 * Definition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityClassDefinition
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition()
		 * @generated
		 */
		public static final EClass ENTITY_CLASS_DEFINITION = eINSTANCE.getEntityClassDefinition();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__QUALIFIED_NAME = eINSTANCE
				.getEntityClassDefinition_QualifiedName();

		/**
		 * The meta object literal for the '<em><b>Wrapper</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__WRAPPER = eINSTANCE.getEntityClassDefinition_Wrapper();

		/**
		 * The meta object literal for the '<em><b>Wrapped Entity</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY = eINSTANCE
				.getEntityClassDefinition_WrappedEntity();

		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__EXTENSION = eINSTANCE
				.getEntityClassDefinition_Extension();

		/**
		 * The meta object literal for the '<em><b>Base Entities</b></em>' attribute
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__BASE_ENTITIES = eINSTANCE
				.getEntityClassDefinition_BaseEntities();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_CLASS_DEFINITION__METHODS = eINSTANCE.getEntityClassDefinition_Methods();

		/**
		 * The meta object literal for the '<em><b>Casts</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_CLASS_DEFINITION__CASTS = eINSTANCE.getEntityClassDefinition_Casts();

		/**
		 * The meta object literal for the '<em><b>Runtime Info</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CLASS_DEFINITION__RUNTIME_INFO = eINSTANCE
				.getEntityClassDefinition_RuntimeInfo();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.EntityMethod
		 * <em>Entity Method</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityMethod
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod()
		 * @generated
		 */
		public static final EClass ENTITY_METHOD = eINSTANCE.getEntityMethod();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD__PROPERTY = eINSTANCE.getEntityMethod_Property();

		/**
		 * The meta object literal for the '<em><b>Method Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD__METHOD_NAME = eINSTANCE.getEntityMethod_MethodName();

		/**
		 * The meta object literal for the '<em><b>Param Signature</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD__PARAM_SIGNATURE = eINSTANCE.getEntityMethod_ParamSignature();

		/**
		 * The meta object literal for the '<em><b>Realization</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD__REALIZATION = eINSTANCE.getEntityMethod_Realization();

		/**
		 * The meta object literal for the '<em><b>Method Realization</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD__METHOD_REALIZATION = eINSTANCE
				.getEntityMethod_MethodRealization();

		/**
		 * The meta object literal for the '<em><b>Runtime Info</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD__RUNTIME_INFO = eINSTANCE.getEntityMethod_RuntimeInfo();

		/**
		 * The meta object literal for the '<em><b>Method Instructions</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD__METHOD_INSTRUCTIONS = eINSTANCE
				.getEntityMethod_MethodInstructions();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature <em>Entity Method
		 * Param Signature</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParamSignature()
		 * @generated
		 */
		public static final EClass ENTITY_METHOD_PARAM_SIGNATURE = eINSTANCE.getEntityMethodParamSignature();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS = eINSTANCE
				.getEntityMethodParamSignature_Parameters();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS = eINSTANCE
				.getEntityMethodParamSignature_Outputs();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.EntityMethodParam <em>Entity Method Param</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityMethodParam
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam()
		 * @generated
		 */
		public static final EClass ENTITY_METHOD_PARAM = eINSTANCE.getEntityMethodParam();

		/**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD_PARAM__FINAL = eINSTANCE.getEntityMethodParam_Final();

		/**
		 * The meta object literal for the '<em><b>Parameter Type</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD_PARAM__PARAMETER_TYPE = eINSTANCE
				.getEntityMethodParam_ParameterType();

		/**
		 * The meta object literal for the '<em><b>Parameter Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD_PARAM__PARAMETER_NAME = eINSTANCE
				.getEntityMethodParam_ParameterName();

		/**
		 * The meta object literal for the '<em><b>Value Fixed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD_PARAM__VALUE_FIXED = eINSTANCE.getEntityMethodParam_ValueFixed();

		/**
		 * The meta object literal for the '<em><b>Fixed Value</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD_PARAM__FIXED_VALUE = eINSTANCE.getEntityMethodParam_FixedValue();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.EntityCast
		 * <em>Entity Cast</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityCast
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast()
		 * @generated
		 */
		public static final EClass ENTITY_CAST = eINSTANCE.getEntityCast();

		/**
		 * The meta object literal for the '<em><b>Resulting Entity</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CAST__RESULTING_ENTITY = eINSTANCE.getEntityCast_ResultingEntity();

		/**
		 * The meta object literal for the '<em><b>Additional Data</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_CAST__ADDITIONAL_DATA = eINSTANCE.getEntityCast_AdditionalData();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Yield
		 * <em>Yield</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Yield
		 * @see de.upb.sede.dsl.seco.SecoPackage#getYield()
		 * @generated
		 */
		public static final EClass YIELD = eINSTANCE.getYield();

		/**
		 * The meta object literal for the '<em><b>Yields</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference YIELD__YIELDS = eINSTANCE.getYield_Yields();

		/**
		 * The meta object literal for the '<em><b>Multi Yield</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute YIELD__MULTI_YIELD = eINSTANCE.getYield_MultiYield();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.Field
		 * <em>Field</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.Field
		 * @see de.upb.sede.dsl.seco.SecoPackage#getField()
		 * @generated
		 */
		public static final EClass FIELD = eINSTANCE.getField();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD__NAME = eINSTANCE.getField_Name();

		/**
		 * The meta object literal for the '<em><b>Dereference</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD__DEREFERENCE = eINSTANCE.getField_Dereference();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference FIELD__MEMBER = eINSTANCE.getField_Member();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.FieldValue
		 * <em>Field Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.FieldValue
		 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue()
		 * @generated
		 */
		public static final EClass FIELD_VALUE = eINSTANCE.getFieldValue();

		/**
		 * The meta object literal for the '<em><b>Cast</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__CAST = eINSTANCE.getFieldValue_Cast();

		/**
		 * The meta object literal for the '<em><b>Cast Target</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__CAST_TARGET = eINSTANCE.getFieldValue_CastTarget();

		/**
		 * The meta object literal for the '<em><b>Cast Value</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference FIELD_VALUE__CAST_VALUE = eINSTANCE.getFieldValue_CastValue();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__NUMBER = eINSTANCE.getFieldValue_Number();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__STRING = eINSTANCE.getFieldValue_String();

		/**
		 * The meta object literal for the '<em><b>Bool</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__BOOL = eINSTANCE.getFieldValue_Bool();

		/**
		 * The meta object literal for the '<em><b>Null</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute FIELD_VALUE__NULL = eINSTANCE.getFieldValue_Null();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference FIELD_VALUE__OPERATION = eINSTANCE.getFieldValue_Operation();

		/**
		 * The meta object literal for the '<em><b>Field</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference FIELD_VALUE__FIELD = eINSTANCE.getFieldValue_Field();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition <em>Entity Deployment
		 * Definition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityDeploymentDefinition
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition()
		 * @generated
		 */
		public static final EClass ENTITY_DEPLOYMENT_DEFINITION = eINSTANCE.getEntityDeploymentDefinition();

		/**
		 * The meta object literal for the '<em><b>Procedures</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_DEPLOYMENT_DEFINITION__PROCEDURES = eINSTANCE
				.getEntityDeploymentDefinition_Procedures();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_DEPLOYMENT_DEFINITION__DEPENDENCIES = eINSTANCE
				.getEntityDeploymentDefinition_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_DEPLOYMENT_DEFINITION__QUALIFIED_NAME = eINSTANCE
				.getEntityDeploymentDefinition_QualifiedName();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure <em>Deployment
		 * Procedure</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.DeploymentProcedure
		 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure()
		 * @generated
		 */
		public static final EClass DEPLOYMENT_PROCEDURE = eINSTANCE.getDeploymentProcedure();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_PROCEDURE__SOURCE = eINSTANCE.getDeploymentProcedure_Source();

		/**
		 * The meta object literal for the '<em><b>Act</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_PROCEDURE__ACT = eINSTANCE.getDeploymentProcedure_Act();

		/**
		 * The meta object literal for the '<em><b>Fetch</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_PROCEDURE__FETCH = eINSTANCE.getDeploymentProcedure_Fetch();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_PROCEDURE__NAME = eINSTANCE.getDeploymentProcedure_Name();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.DeploymentDependency <em>Deployment
		 * Dependency</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.DeploymentDependency
		 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentDependency()
		 * @generated
		 */
		public static final EClass DEPLOYMENT_DEPENDENCY = eINSTANCE.getDeploymentDependency();

		/**
		 * The meta object literal for the '<em><b>Deployment</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_DEPENDENCY__DEPLOYMENT = eINSTANCE
				.getDeploymentDependency_Deployment();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute DEPLOYMENT_DEPENDENCY__ORDER = eINSTANCE.getDeploymentDependency_Order();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions <em>Entity Method
		 * Instructions</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityMethodInstructions
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodInstructions()
		 * @generated
		 */
		public static final EClass ENTITY_METHOD_INSTRUCTIONS = eINSTANCE.getEntityMethodInstructions();

		/**
		 * The meta object literal for the '<em><b>Instructions</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EReference ENTITY_METHOD_INSTRUCTIONS__INSTRUCTIONS = eINSTANCE
				.getEntityMethodInstructions_Instructions();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public static final EAttribute ENTITY_METHOD_INSTRUCTIONS__ORDER = eINSTANCE
				.getEntityMethodInstructions_Order();

		/**
		 * The meta object literal for the '{@link de.upb.sede.dsl.seco.EntityMethodProp
		 * <em>Entity Method Prop</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see de.upb.sede.dsl.seco.EntityMethodProp
		 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodProp()
		 * @generated
		 */
		public static final EEnum ENTITY_METHOD_PROP = eINSTANCE.getEntityMethodProp();

		/**
		 * The meta object literal for the
		 * '{@link de.upb.sede.dsl.seco.TransformDirection <em>Transform
		 * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see de.upb.sede.dsl.seco.TransformDirection
		 * @see de.upb.sede.dsl.seco.SecoPackage#getTransformDirection()
		 * @generated
		 */
		public static final EEnum TRANSFORM_DIRECTION = eINSTANCE.getTransformDirection();

	}

} // SecoPackage
