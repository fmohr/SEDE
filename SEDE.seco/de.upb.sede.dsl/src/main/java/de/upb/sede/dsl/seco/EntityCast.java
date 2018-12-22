/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Cast</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.EntityCast#getDirection <em>Direction</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityCast#getResultingEntity <em>Resulting Entity</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityCast#getAdditionalData <em>Additional Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast()
 * @model kind="class"
 * @generated
 */
public class EntityCast extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final TransformDirection DIRECTION_EDEFAULT = TransformDirection.BI;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected TransformDirection direction = DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultingEntity() <em>Resulting Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultingEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULTING_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultingEntity() <em>Resulting Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultingEntity()
	 * @generated
	 * @ordered
	 */
	protected String resultingEntity = RESULTING_ENTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdditionalData() <em>Additional Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalData()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDITIONAL_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdditionalData() <em>Additional Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalData()
	 * @generated
	 * @ordered
	 */
	protected String additionalData = ADDITIONAL_DATA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityCast() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_CAST;
	}

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link de.upb.sede.dsl.seco.TransformDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @see #setDirection(TransformDirection)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_Direction()
	 * @model
	 * @generated
	 */
	public TransformDirection getDirection() {
		return direction;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityCast#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @see #getDirection()
	 * @generated
	 */
	public void setDirection(TransformDirection newDirection) {
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
	}

	/**
	 * Returns the value of the '<em><b>Resulting Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resulting Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resulting Entity</em>' attribute.
	 * @see #setResultingEntity(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_ResultingEntity()
	 * @model
	 * @generated
	 */
	public String getResultingEntity() {
		return resultingEntity;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityCast#getResultingEntity <em>Resulting Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resulting Entity</em>' attribute.
	 * @see #getResultingEntity()
	 * @generated
	 */
	public void setResultingEntity(String newResultingEntity) {
		resultingEntity = newResultingEntity;
	}

	/**
	 * Returns the value of the '<em><b>Additional Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Data</em>' attribute.
	 * @see #setAdditionalData(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_AdditionalData()
	 * @model
	 * @generated
	 */
	public String getAdditionalData() {
		return additionalData;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityCast#getAdditionalData <em>Additional Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Data</em>' attribute.
	 * @see #getAdditionalData()
	 * @generated
	 */
	public void setAdditionalData(String newAdditionalData) {
		additionalData = newAdditionalData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SecoPackage.ENTITY_CAST__DIRECTION:
				return getDirection();
			case SecoPackage.ENTITY_CAST__RESULTING_ENTITY:
				return getResultingEntity();
			case SecoPackage.ENTITY_CAST__ADDITIONAL_DATA:
				return getAdditionalData();
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
			case SecoPackage.ENTITY_CAST__DIRECTION:
				setDirection((TransformDirection)newValue);
				return;
			case SecoPackage.ENTITY_CAST__RESULTING_ENTITY:
				setResultingEntity((String)newValue);
				return;
			case SecoPackage.ENTITY_CAST__ADDITIONAL_DATA:
				setAdditionalData((String)newValue);
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
			case SecoPackage.ENTITY_CAST__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CAST__RESULTING_ENTITY:
				setResultingEntity(RESULTING_ENTITY_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CAST__ADDITIONAL_DATA:
				setAdditionalData(ADDITIONAL_DATA_EDEFAULT);
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
			case SecoPackage.ENTITY_CAST__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case SecoPackage.ENTITY_CAST__RESULTING_ENTITY:
				return RESULTING_ENTITY_EDEFAULT == null ? resultingEntity != null : !RESULTING_ENTITY_EDEFAULT.equals(resultingEntity);
			case SecoPackage.ENTITY_CAST__ADDITIONAL_DATA:
				return ADDITIONAL_DATA_EDEFAULT == null ? additionalData != null : !ADDITIONAL_DATA_EDEFAULT.equals(additionalData);
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
		result.append(" (direction: ");
		result.append(direction);
		result.append(", resultingEntity: ");
		result.append(resultingEntity);
		result.append(", additionalData: ");
		result.append(additionalData);
		result.append(')');
		return result.toString();
	}

} // EntityCast
