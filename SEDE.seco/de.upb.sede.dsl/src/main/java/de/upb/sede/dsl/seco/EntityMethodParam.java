/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity
 * Method Param</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#isFinal
 * <em>Final</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterType
 * <em>Parameter Type</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterName
 * <em>Parameter Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed <em>Value
 * Fixed</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue <em>Fixed
 * Value</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam()
 * @model kind="class"
 * @generated
 */
public class EntityMethodParam extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodParam() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_METHOD_PARAM;
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
	 * Returns the value of the '<em><b>Final</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Final</em>' attribute.
	 * @see #setFinal(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_Final()
	 * @model
	 * @generated
	 */
	public boolean isFinal() {
		return (Boolean) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM__FINAL, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityMethodParam#isFinal
	 * <em>Final</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Final</em>' attribute.
	 * @see #isFinal()
	 * @generated
	 */
	public void setFinal(boolean newFinal) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_PARAM__FINAL, newFinal);
	}

	/**
	 * Returns the value of the '<em><b>Parameter Type</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Type</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter Type</em>' attribute.
	 * @see #setParameterType(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ParameterType()
	 * @model
	 * @generated
	 */
	public String getParameterType() {
		return (String) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM__PARAMETER_TYPE, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterType <em>Parameter
	 * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Parameter Type</em>' attribute.
	 * @see #getParameterType()
	 * @generated
	 */
	public void setParameterType(String newParameterType) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_PARAM__PARAMETER_TYPE, newParameterType);
	}

	/**
	 * Returns the value of the '<em><b>Parameter Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter Name</em>' attribute.
	 * @see #setParameterName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ParameterName()
	 * @model
	 * @generated
	 */
	public String getParameterName() {
		return (String) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM__PARAMETER_NAME, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getParameterName <em>Parameter
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Parameter Name</em>' attribute.
	 * @see #getParameterName()
	 * @generated
	 */
	public void setParameterName(String newParameterName) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_PARAM__PARAMETER_NAME, newParameterName);
	}

	/**
	 * Returns the value of the '<em><b>Value Fixed</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Fixed</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value Fixed</em>' attribute.
	 * @see #setValueFixed(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_ValueFixed()
	 * @model
	 * @generated
	 */
	public boolean isValueFixed() {
		return (Boolean) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM__VALUE_FIXED, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#isValueFixed <em>Value
	 * Fixed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Value Fixed</em>' attribute.
	 * @see #isValueFixed()
	 * @generated
	 */
	public void setValueFixed(boolean newValueFixed) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_PARAM__VALUE_FIXED, newValueFixed);
	}

	/**
	 * Returns the value of the '<em><b>Fixed Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed Value</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fixed Value</em>' containment reference.
	 * @see #setFixedValue(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParam_FixedValue()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getFixedValue() {
		return (FieldValue) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM__FIXED_VALUE, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam#getFixedValue <em>Fixed
	 * Value</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Fixed Value</em>' containment
	 *              reference.
	 * @see #getFixedValue()
	 * @generated
	 */
	public void setFixedValue(FieldValue newFixedValue) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_PARAM__FIXED_VALUE, newFixedValue);
	}

} // EntityMethodParam
