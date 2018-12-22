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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Method Param</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#isFinal <em>Final</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterType <em>Parameter Type</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterName <em>Parameter Name</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed <em>Value Fixed</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue <em>Fixed Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam()
 * @model kind="class"
 * @generated
 */
public class EntityMethodParam extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean final_ = FINAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameterType() <em>Parameter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterType()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameterType() <em>Parameter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterType()
	 * @generated
	 * @ordered
	 */
	protected String parameterType = PARAMETER_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameterName() <em>Parameter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterName()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameterName() <em>Parameter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterName()
	 * @generated
	 * @ordered
	 */
	protected String parameterName = PARAMETER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isValueFixed() <em>Value Fixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValueFixed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALUE_FIXED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValueFixed() <em>Value Fixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValueFixed()
	 * @generated
	 * @ordered
	 */
	protected boolean valueFixed = VALUE_FIXED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFixedValue() <em>Fixed Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedValue()
	 * @generated
	 * @ordered
	 */
	protected FieldValue fixedValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityMethodParam() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_METHOD_PARAM;
	}

	/**
	 * Returns the value of the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Final</em>' attribute.
	 * @see #setFinal(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_Final()
	 * @model
	 * @generated
	 */
	public boolean isFinal() {
		return final_;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#isFinal <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Final</em>' attribute.
	 * @see #isFinal()
	 * @generated
	 */
	public void setFinal(boolean newFinal) {
		final_ = newFinal;
	}

	/**
	 * Returns the value of the '<em><b>Parameter Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Type</em>' attribute.
	 * @see #setParameterType(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ParameterType()
	 * @model
	 * @generated
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterType <em>Parameter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Type</em>' attribute.
	 * @see #getParameterType()
	 * @generated
	 */
	public void setParameterType(String newParameterType) {
		parameterType = newParameterType;
	}

	/**
	 * Returns the value of the '<em><b>Parameter Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Name</em>' attribute.
	 * @see #setParameterName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ParameterName()
	 * @model
	 * @generated
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterName <em>Parameter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Name</em>' attribute.
	 * @see #getParameterName()
	 * @generated
	 */
	public void setParameterName(String newParameterName) {
		parameterName = newParameterName;
	}

	/**
	 * Returns the value of the '<em><b>Value Fixed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Fixed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Fixed</em>' attribute.
	 * @see #setValueFixed(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ValueFixed()
	 * @model
	 * @generated
	 */
	public boolean isValueFixed() {
		return valueFixed;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed <em>Value Fixed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Fixed</em>' attribute.
	 * @see #isValueFixed()
	 * @generated
	 */
	public void setValueFixed(boolean newValueFixed) {
		valueFixed = newValueFixed;
	}

	/**
	 * Returns the value of the '<em><b>Fixed Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed Value</em>' containment reference.
	 * @see #setFixedValue(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_FixedValue()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getFixedValue() {
		return fixedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFixedValue(FieldValue newFixedValue, NotificationChain msgs) {
		FieldValue oldFixedValue = fixedValue;
		fixedValue = newFixedValue;
		return msgs;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue <em>Fixed Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed Value</em>' containment reference.
	 * @see #getFixedValue()
	 * @generated
	 */
	public void setFixedValue(FieldValue newFixedValue) {
		if (newFixedValue != fixedValue) {
			NotificationChain msgs = null;
			if (fixedValue != null)
				msgs = ((InternalEObject)fixedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE, null, msgs);
			if (newFixedValue != null)
				msgs = ((InternalEObject)newFixedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE, null, msgs);
			msgs = basicSetFixedValue(newFixedValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE:
				return basicSetFixedValue(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SecoPackage.ENTITY_METHOD_PARAM__FINAL:
				return isFinal();
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_TYPE:
				return getParameterType();
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_NAME:
				return getParameterName();
			case SecoPackage.ENTITY_METHOD_PARAM__VALUE_FIXED:
				return isValueFixed();
			case SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE:
				return getFixedValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SecoPackage.ENTITY_METHOD_PARAM__FINAL:
				setFinal((Boolean)newValue);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_TYPE:
				setParameterType((String)newValue);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_NAME:
				setParameterName((String)newValue);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__VALUE_FIXED:
				setValueFixed((Boolean)newValue);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE:
				setFixedValue((FieldValue)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SecoPackage.ENTITY_METHOD_PARAM__FINAL:
				setFinal(FINAL_EDEFAULT);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_TYPE:
				setParameterType(PARAMETER_TYPE_EDEFAULT);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_NAME:
				setParameterName(PARAMETER_NAME_EDEFAULT);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__VALUE_FIXED:
				setValueFixed(VALUE_FIXED_EDEFAULT);
				return;
			case SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE:
				setFixedValue((FieldValue)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SecoPackage.ENTITY_METHOD_PARAM__FINAL:
				return final_ != FINAL_EDEFAULT;
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_TYPE:
				return PARAMETER_TYPE_EDEFAULT == null ? parameterType != null : !PARAMETER_TYPE_EDEFAULT.equals(parameterType);
			case SecoPackage.ENTITY_METHOD_PARAM__PARAMETER_NAME:
				return PARAMETER_NAME_EDEFAULT == null ? parameterName != null : !PARAMETER_NAME_EDEFAULT.equals(parameterName);
			case SecoPackage.ENTITY_METHOD_PARAM__VALUE_FIXED:
				return valueFixed != VALUE_FIXED_EDEFAULT;
			case SecoPackage.ENTITY_METHOD_PARAM__FIXED_VALUE:
				return fixedValue != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (final: ");
		result.append(final_);
		result.append(", parameterType: ");
		result.append(parameterType);
		result.append(", parameterName: ");
		result.append(parameterName);
		result.append(", valueFixed: ");
		result.append(valueFixed);
		result.append(')');
		return result.toString();
	}

} // EntityMethodParam
