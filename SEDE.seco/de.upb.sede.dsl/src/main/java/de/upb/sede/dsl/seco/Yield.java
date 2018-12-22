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
 * A representation of the model object '<em><b>Yield</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.Yield#getYields <em>Yields</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Yield#isMultiYield <em>Multi Yield</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getYield()
 * @model kind="class"
 * @generated
 */
public class Yield extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getYields() <em>Yields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYields()
	 * @generated
	 * @ordered
	 */
	protected EList<FieldValue> yields;

	/**
	 * The default value of the '{@link #isMultiYield() <em>Multi Yield</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiYield()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTI_YIELD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiYield() <em>Multi Yield</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiYield()
	 * @generated
	 * @ordered
	 */
	protected boolean multiYield = MULTI_YIELD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Yield() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.YIELD;
	}

	/**
	 * Returns the value of the '<em><b>Yields</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.FieldValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Yields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Yields</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getYield_Yields()
	 * @model containment="true"
	 * @generated
	 */
	public List<FieldValue> getYields() {
		if (yields == null) {
			yields = new BasicInternalEList<FieldValue>(FieldValue.class);
		}
		return yields;
	}

	/**
	 * Returns the value of the '<em><b>Multi Yield</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Yield</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Yield</em>' attribute.
	 * @see #setMultiYield(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getYield_MultiYield()
	 * @model
	 * @generated
	 */
	public boolean isMultiYield() {
		return multiYield;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Yield#isMultiYield <em>Multi Yield</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multi Yield</em>' attribute.
	 * @see #isMultiYield()
	 * @generated
	 */
	public void setMultiYield(boolean newMultiYield) {
		multiYield = newMultiYield;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SecoPackage.YIELD__YIELDS:
				return ((InternalEList<?>)getYields()).basicRemove(otherEnd, msgs);
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
			case SecoPackage.YIELD__YIELDS:
				return getYields();
			case SecoPackage.YIELD__MULTI_YIELD:
				return isMultiYield();
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
			case SecoPackage.YIELD__YIELDS:
				getYields().clear();
				getYields().addAll((Collection<? extends FieldValue>)newValue);
				return;
			case SecoPackage.YIELD__MULTI_YIELD:
				setMultiYield((Boolean)newValue);
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
			case SecoPackage.YIELD__YIELDS:
				getYields().clear();
				return;
			case SecoPackage.YIELD__MULTI_YIELD:
				setMultiYield(MULTI_YIELD_EDEFAULT);
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
			case SecoPackage.YIELD__YIELDS:
				return yields != null && !yields.isEmpty();
			case SecoPackage.YIELD__MULTI_YIELD:
				return multiYield != MULTI_YIELD_EDEFAULT;
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
		result.append(" (multiYield: ");
		result.append(multiYield);
		result.append(')');
		return result.toString();
	}

} // Yield
