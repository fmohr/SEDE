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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Field</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Field#getName <em>Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Field#isDereference
 * <em>Dereference</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Field#getMemeber <em>Memeber</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getField()
 * @model kind="class"
 * @generated
 */
public class Field extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDereference() <em>Dereference</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDereference()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEREFERENCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDereference() <em>Dereference</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDereference()
	 * @generated
	 * @ordered
	 */
	protected boolean dereference = DEREFERENCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMemeber() <em>Memeber</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMemeber()
	 * @generated
	 * @ordered
	 */
	protected FieldValue memeber;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Field() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.FIELD;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getField_Name()
	 * @model
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Field#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Returns the value of the '<em><b>Dereference</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dereference</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dereference</em>' attribute.
	 * @see #setDereference(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getField_Dereference()
	 * @model
	 * @generated
	 */
	public boolean isDereference() {
		return dereference;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Field#isDereference
	 * <em>Dereference</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Dereference</em>' attribute.
	 * @see #isDereference()
	 * @generated
	 */
	public void setDereference(boolean newDereference) {
		dereference = newDereference;
	}

	/**
	 * Returns the value of the '<em><b>Memeber</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memeber</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Memeber</em>' containment reference.
	 * @see #setMemeber(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getField_Memeber()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getMemeber() {
		return memeber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMemeber(FieldValue newMemeber, NotificationChain msgs) {
		FieldValue oldMemeber = memeber;
		memeber = newMemeber;
		return msgs;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Field#getMemeber
	 * <em>Memeber</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Memeber</em>' containment reference.
	 * @see #getMemeber()
	 * @generated
	 */
	public void setMemeber(FieldValue newMemeber) {
		if (newMemeber != memeber) {
			NotificationChain msgs = null;
			if (memeber != null)
				msgs = ((InternalEObject) memeber).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD__MEMEBER, null, msgs);
			if (newMemeber != null)
				msgs = ((InternalEObject) newMemeber).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD__MEMEBER, null, msgs);
			msgs = basicSetMemeber(newMemeber, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SecoPackage.FIELD__MEMEBER:
			return basicSetMemeber(null, msgs);
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
		case SecoPackage.FIELD__NAME:
			return getName();
		case SecoPackage.FIELD__DEREFERENCE:
			return isDereference();
		case SecoPackage.FIELD__MEMEBER:
			return getMemeber();
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
		case SecoPackage.FIELD__NAME:
			setName((String) newValue);
			return;
		case SecoPackage.FIELD__DEREFERENCE:
			setDereference((Boolean) newValue);
			return;
		case SecoPackage.FIELD__MEMEBER:
			setMemeber((FieldValue) newValue);
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
		case SecoPackage.FIELD__NAME:
			setName(NAME_EDEFAULT);
			return;
		case SecoPackage.FIELD__DEREFERENCE:
			setDereference(DEREFERENCE_EDEFAULT);
			return;
		case SecoPackage.FIELD__MEMEBER:
			setMemeber((FieldValue) null);
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
		case SecoPackage.FIELD__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case SecoPackage.FIELD__DEREFERENCE:
			return dereference != DEREFERENCE_EDEFAULT;
		case SecoPackage.FIELD__MEMEBER:
			return memeber != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", dereference: ");
		result.append(dereference);
		result.append(')');
		return result.toString();
	}

} // Field
