/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity
 * Cast</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityCast#getDirection
 * <em>Direction</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityCast#getResultingEntity <em>Resulting
 * Entity</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityCast#getAdditionalData <em>Additional
 * Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast()
 * @model kind="class"
 * @generated
 */
public class EntityCast extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityCast() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_CAST;
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
	 * Returns the value of the '<em><b>Direction</b></em>' attribute. The literals
	 * are from the enumeration {@link de.upb.sede.dsl.seco.TransformDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @see #setDirection(TransformDirection)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_Direction()
	 * @model
	 * @generated
	 */
	public TransformDirection getDirection() {
		return (TransformDirection) eGet(SecoPackage.Literals.ENTITY_CAST__DIRECTION, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityCast#getDirection
	 * <em>Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see de.upb.sede.dsl.seco.TransformDirection
	 * @see #getDirection()
	 * @generated
	 */
	public void setDirection(TransformDirection newDirection) {
		eSet(SecoPackage.Literals.ENTITY_CAST__DIRECTION, newDirection);
	}

	/**
	 * Returns the value of the '<em><b>Resulting Entity</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resulting Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Resulting Entity</em>' attribute.
	 * @see #setResultingEntity(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_ResultingEntity()
	 * @model
	 * @generated
	 */
	public String getResultingEntity() {
		return (String) eGet(SecoPackage.Literals.ENTITY_CAST__RESULTING_ENTITY, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityCast#getResultingEntity <em>Resulting
	 * Entity</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Resulting Entity</em>' attribute.
	 * @see #getResultingEntity()
	 * @generated
	 */
	public void setResultingEntity(String newResultingEntity) {
		eSet(SecoPackage.Literals.ENTITY_CAST__RESULTING_ENTITY, newResultingEntity);
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
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityCast_AdditionalData()
	 * @model
	 * @generated
	 */
	public String getAdditionalData() {
		return (String) eGet(SecoPackage.Literals.ENTITY_CAST__ADDITIONAL_DATA, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityCast#getAdditionalData <em>Additional
	 * Data</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Additional Data</em>' attribute.
	 * @see #getAdditionalData()
	 * @generated
	 */
	public void setAdditionalData(String newAdditionalData) {
		eSet(SecoPackage.Literals.ENTITY_CAST__ADDITIONAL_DATA, newAdditionalData);
	}

} // EntityCast
