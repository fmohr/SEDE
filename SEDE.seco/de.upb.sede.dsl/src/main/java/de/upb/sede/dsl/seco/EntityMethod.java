/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

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
 * <li>{@link de.upb.sede.dsl.seco.EntityMethod#getAdditionalData <em>Additional
 * Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod()
 * @model kind="class"
 * @generated
 */
public class EntityMethod extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #getProperty() <em>Property</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected static final EntityMethodProp PROPERTY_EDEFAULT = EntityMethodProp.MUTATING;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EntityMethodProp property = PROPERTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodName() <em>Method Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodName() <em>Method Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected String methodName = METHOD_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParamSignature() <em>Param
	 * Signature</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getParamSignature()
	 * @generated
	 * @ordered
	 */
	protected EntityMethodParamSignature paramSignature;

	/**
	 * The default value of the '{@link #isRealization() <em>Realization</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isRealization()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REALIZATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRealization() <em>Realization</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isRealization()
	 * @generated
	 * @ordered
	 */
	protected boolean realization = REALIZATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodRealization() <em>Method
	 * Realization</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMethodRealization()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_REALIZATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodRealization() <em>Method
	 * Realization</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMethodRealization()
	 * @generated
	 * @ordered
	 */
	protected String methodRealization = METHOD_REALIZATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdditionalData() <em>Additional
	 * Data</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdditionalData()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDITIONAL_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdditionalData() <em>Additional
	 * Data</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdditionalData()
	 * @generated
	 * @ordered
	 */
	protected String additionalData = ADDITIONAL_DATA_EDEFAULT;

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
		return property;
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
		property = newProperty == null ? PROPERTY_EDEFAULT : newProperty;
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
		return methodName;
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
		methodName = newMethodName;
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
		return paramSignature;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetParamSignature(EntityMethodParamSignature newParamSignature,
			NotificationChain msgs) {
		EntityMethodParamSignature oldParamSignature = paramSignature;
		paramSignature = newParamSignature;
		return msgs;
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
		if (newParamSignature != paramSignature) {
			NotificationChain msgs = null;
			if (paramSignature != null)
				msgs = ((InternalEObject) paramSignature).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE, null, msgs);
			if (newParamSignature != null)
				msgs = ((InternalEObject) newParamSignature).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE, null, msgs);
			msgs = basicSetParamSignature(newParamSignature, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
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
		return realization;
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
		realization = newRealization;
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
		return methodRealization;
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
		methodRealization = newMethodRealization;
	}

	/**
	 * Returns the value of the '<em><b>Additional Data</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Data</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Additional Data</em>' attribute.
	 * @see #setAdditionalData(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethod_AdditionalData()
	 * @model
	 * @generated
	 */
	public String getAdditionalData() {
		return additionalData;
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethod#getAdditionalData <em>Additional
	 * Data</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Additional Data</em>' attribute.
	 * @see #getAdditionalData()
	 * @generated
	 */
	public void setAdditionalData(String newAdditionalData) {
		additionalData = newAdditionalData;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE:
			return basicSetParamSignature(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD__PROPERTY:
			return getProperty();
		case SecoPackage.ENTITY_METHOD__METHOD_NAME:
			return getMethodName();
		case SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE:
			return getParamSignature();
		case SecoPackage.ENTITY_METHOD__REALIZATION:
			return isRealization();
		case SecoPackage.ENTITY_METHOD__METHOD_REALIZATION:
			return getMethodRealization();
		case SecoPackage.ENTITY_METHOD__ADDITIONAL_DATA:
			return getAdditionalData();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD__PROPERTY:
			setProperty((EntityMethodProp) newValue);
			return;
		case SecoPackage.ENTITY_METHOD__METHOD_NAME:
			setMethodName((String) newValue);
			return;
		case SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE:
			setParamSignature((EntityMethodParamSignature) newValue);
			return;
		case SecoPackage.ENTITY_METHOD__REALIZATION:
			setRealization((Boolean) newValue);
			return;
		case SecoPackage.ENTITY_METHOD__METHOD_REALIZATION:
			setMethodRealization((String) newValue);
			return;
		case SecoPackage.ENTITY_METHOD__ADDITIONAL_DATA:
			setAdditionalData((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD__PROPERTY:
			setProperty(PROPERTY_EDEFAULT);
			return;
		case SecoPackage.ENTITY_METHOD__METHOD_NAME:
			setMethodName(METHOD_NAME_EDEFAULT);
			return;
		case SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE:
			setParamSignature((EntityMethodParamSignature) null);
			return;
		case SecoPackage.ENTITY_METHOD__REALIZATION:
			setRealization(REALIZATION_EDEFAULT);
			return;
		case SecoPackage.ENTITY_METHOD__METHOD_REALIZATION:
			setMethodRealization(METHOD_REALIZATION_EDEFAULT);
			return;
		case SecoPackage.ENTITY_METHOD__ADDITIONAL_DATA:
			setAdditionalData(ADDITIONAL_DATA_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD__PROPERTY:
			return property != PROPERTY_EDEFAULT;
		case SecoPackage.ENTITY_METHOD__METHOD_NAME:
			return METHOD_NAME_EDEFAULT == null ? methodName != null : !METHOD_NAME_EDEFAULT.equals(methodName);
		case SecoPackage.ENTITY_METHOD__PARAM_SIGNATURE:
			return paramSignature != null;
		case SecoPackage.ENTITY_METHOD__REALIZATION:
			return realization != REALIZATION_EDEFAULT;
		case SecoPackage.ENTITY_METHOD__METHOD_REALIZATION:
			return METHOD_REALIZATION_EDEFAULT == null ? methodRealization != null
					: !METHOD_REALIZATION_EDEFAULT.equals(methodRealization);
		case SecoPackage.ENTITY_METHOD__ADDITIONAL_DATA:
			return ADDITIONAL_DATA_EDEFAULT == null ? additionalData != null
					: !ADDITIONAL_DATA_EDEFAULT.equals(additionalData);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (property: ");
		result.append(property);
		result.append(", methodName: ");
		result.append(methodName);
		result.append(", realization: ");
		result.append(realization);
		result.append(", methodRealization: ");
		result.append(methodRealization);
		result.append(", additionalData: ");
		result.append(additionalData);
		result.append(')');
		return result.toString();
	}

} // EntityMethod
