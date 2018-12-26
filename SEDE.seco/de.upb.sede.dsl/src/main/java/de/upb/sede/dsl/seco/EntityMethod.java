/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity
 * Method</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getProperty
 * <em>Property</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getMethodName <em>Method
 * Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getParamSignature <em>Param
 * Signature</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#isRealization
 * <em>Realization</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getMethodRealization <em>Method
 * Realization</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getRuntimeInfo <em>Runtime
 * Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod()
 * @model kind="class"
 * @generated
 */
public class EntityMethod extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethod() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_METHOD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * Returns the value of the '<em><b>Property</b></em>' attribute. The literals
	 * are from the enumeration {@link de.upb.sede.dsl.seco.EntityMethodProp}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Property</em>' attribute.
	 * @see de.upb.sede.dsl.seco.EntityMethodProp
	 * @see #setProperty(EntityMethodProp)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_Property()
	 * @model
	 * @generated
	 */
	public EntityMethodProp getProperty() {
		return (EntityMethodProp) eGet(SecoPackage.Literals.ENTITY_METHOD__PROPERTY, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethod#getProperty
	 * <em>Property</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Property</em>' attribute.
	 * @see de.upb.sede.dsl.seco.EntityMethodProp
	 * @see #getProperty()
	 * @generated
	 */
	public void setProperty(EntityMethodProp newProperty) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__PROPERTY, newProperty);
	}

	/**
	 * Returns the value of the '<em><b>Method Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Method Name</em>' attribute.
	 * @see #setMethodName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_MethodName()
	 * @model
	 * @generated
	 */
	public String getMethodName() {
		return (String) eGet(SecoPackage.Literals.ENTITY_METHOD__METHOD_NAME, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethod#getMethodName
	 * <em>Method Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Method Name</em>' attribute.
	 * @see #getMethodName()
	 * @generated
	 */
	public void setMethodName(String newMethodName) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__METHOD_NAME, newMethodName);
	}

	/**
	 * Returns the value of the '<em><b>Param Signature</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Param Signature</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Param Signature</em>' containment reference.
	 * @see #setParamSignature(EntityMethodParamSignature)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_ParamSignature()
	 * @model containment="true"
	 * @generated
	 */
	public EntityMethodParamSignature getParamSignature() {
		return (EntityMethodParamSignature) eGet(SecoPackage.Literals.ENTITY_METHOD__PARAM_SIGNATURE, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getParamSignature <em>Param
	 * Signature</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Param Signature</em>' containment
	 *              reference.
	 * @see #getParamSignature()
	 * @generated
	 */
	public void setParamSignature(EntityMethodParamSignature newParamSignature) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__PARAM_SIGNATURE, newParamSignature);
	}

	/**
	 * Returns the value of the '<em><b>Realization</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Realization</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Realization</em>' attribute.
	 * @see #setRealization(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_Realization()
	 * @model
	 * @generated
	 */
	public boolean isRealization() {
		return (Boolean) eGet(SecoPackage.Literals.ENTITY_METHOD__REALIZATION, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethod#isRealization
	 * <em>Realization</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Realization</em>' attribute.
	 * @see #isRealization()
	 * @generated
	 */
	public void setRealization(boolean newRealization) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__REALIZATION, newRealization);
	}

	/**
	 * Returns the value of the '<em><b>Method Realization</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Realization</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Method Realization</em>' attribute.
	 * @see #setMethodRealization(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_MethodRealization()
	 * @model
	 * @generated
	 */
	public String getMethodRealization() {
		return (String) eGet(SecoPackage.Literals.ENTITY_METHOD__METHOD_REALIZATION, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getMethodRealization <em>Method
	 * Realization</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Method Realization</em>' attribute.
	 * @see #getMethodRealization()
	 * @generated
	 */
	public void setMethodRealization(String newMethodRealization) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__METHOD_REALIZATION, newMethodRealization);
	}

	/**
	 * Returns the value of the '<em><b>Runtime Info</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Info</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Runtime Info</em>' attribute.
	 * @see #setRuntimeInfo(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_RuntimeInfo()
	 * @model
	 * @generated
	 */
	public String getRuntimeInfo() {
		return (String) eGet(SecoPackage.Literals.ENTITY_METHOD__RUNTIME_INFO, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getRuntimeInfo <em>Runtime
	 * Info</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Runtime Info</em>' attribute.
	 * @see #getRuntimeInfo()
	 * @generated
	 */
	public void setRuntimeInfo(String newRuntimeInfo) {
		eSet(SecoPackage.Literals.ENTITY_METHOD__RUNTIME_INFO, newRuntimeInfo);
	}

} // EntityMethod
