/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.Assignment#getAssignedFields <em>Assigned Fields</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Assignment#isMultiAssignment <em>Multi Assignment</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Assignment#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment()
 * @model kind="class"
 * @generated
 */
public class Assignment extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getAssignedFields() <em>Assigned Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignedFields()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> assignedFields;

	/**
	 * The default value of the '{@link #isMultiAssignment() <em>Multi Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiAssignment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTI_ASSIGNMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiAssignment() <em>Multi Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiAssignment()
	 * @generated
	 * @ordered
	 */
	protected boolean multiAssignment = MULTI_ASSIGNMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected FieldValue value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignment() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ASSIGNMENT;
	}

	/**
	 * Returns the value of the '<em><b>Assigned Fields</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.Field}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assigned Fields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assigned Fields</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_AssignedFields()
	 * @model containment="true"
	 * @generated
	 */
	public List<Field> getAssignedFields() {
		if (assignedFields == null) {
			assignedFields = new BasicInternalEList<Field>(Field.class);
		}
		return assignedFields;
	}

	/**
	 * Returns the value of the '<em><b>Multi Assignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Assignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Assignment</em>' attribute.
	 * @see #setMultiAssignment(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_MultiAssignment()
	 * @model
	 * @generated
	 */
	public boolean isMultiAssignment() {
		return multiAssignment;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Assignment#isMultiAssignment <em>Multi Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multi Assignment</em>' attribute.
	 * @see #isMultiAssignment()
	 * @generated
	 */
	public void setMultiAssignment(boolean newMultiAssignment) {
		multiAssignment = newMultiAssignment;
	}

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_Value()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(FieldValue newValue, NotificationChain msgs) {
		FieldValue oldValue = value;
		value = newValue;
		return msgs;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Assignment#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(FieldValue newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SecoPackage.ASSIGNMENT__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SecoPackage.ASSIGNMENT__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
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
			case SecoPackage.ASSIGNMENT__ASSIGNED_FIELDS:
				return ((InternalEList<?>)getAssignedFields()).basicRemove(otherEnd, msgs);
			case SecoPackage.ASSIGNMENT__VALUE:
				return basicSetValue(null, msgs);
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
			case SecoPackage.ASSIGNMENT__ASSIGNED_FIELDS:
				return getAssignedFields();
			case SecoPackage.ASSIGNMENT__MULTI_ASSIGNMENT:
				return isMultiAssignment();
			case SecoPackage.ASSIGNMENT__VALUE:
				return getValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SecoPackage.ASSIGNMENT__ASSIGNED_FIELDS:
				getAssignedFields().clear();
				getAssignedFields().addAll((Collection<? extends Field>)newValue);
				return;
			case SecoPackage.ASSIGNMENT__MULTI_ASSIGNMENT:
				setMultiAssignment((Boolean)newValue);
				return;
			case SecoPackage.ASSIGNMENT__VALUE:
				setValue((FieldValue)newValue);
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
			case SecoPackage.ASSIGNMENT__ASSIGNED_FIELDS:
				getAssignedFields().clear();
				return;
			case SecoPackage.ASSIGNMENT__MULTI_ASSIGNMENT:
				setMultiAssignment(MULTI_ASSIGNMENT_EDEFAULT);
				return;
			case SecoPackage.ASSIGNMENT__VALUE:
				setValue((FieldValue)null);
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
			case SecoPackage.ASSIGNMENT__ASSIGNED_FIELDS:
				return assignedFields != null && !assignedFields.isEmpty();
			case SecoPackage.ASSIGNMENT__MULTI_ASSIGNMENT:
				return multiAssignment != MULTI_ASSIGNMENT_EDEFAULT;
			case SecoPackage.ASSIGNMENT__VALUE:
				return value != null;
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
		result.append(" (multiAssignment: ");
		result.append(multiAssignment);
		result.append(')');
		return result.toString();
	}

} // Assignment
