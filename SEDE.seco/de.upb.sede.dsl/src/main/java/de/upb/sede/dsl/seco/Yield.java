/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Yield</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Yield#getYields <em>Yields</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Yield#isMultiYield <em>Multi Yield</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getYield()
 * @model kind="class"
 * @generated
 */
public class Yield extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Yield() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.YIELD;
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
	 * Returns the value of the '<em><b>Yields</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.FieldValue}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Yields</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Yields</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getYield_Yields()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<FieldValue> getYields() {
		return (EList<FieldValue>) eGet(SecoPackage.Literals.YIELD__YIELDS, true);
	}

	/**
	 * Returns the value of the '<em><b>Multi Yield</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Yield</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Multi Yield</em>' attribute.
	 * @see #setMultiYield(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getYield_MultiYield()
	 * @model
	 * @generated
	 */
	public boolean isMultiYield() {
		return (Boolean) eGet(SecoPackage.Literals.YIELD__MULTI_YIELD, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Yield#isMultiYield
	 * <em>Multi Yield</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Multi Yield</em>' attribute.
	 * @see #isMultiYield()
	 * @generated
	 */
	public void setMultiYield(boolean newMultiYield) {
		eSet(SecoPackage.Literals.YIELD__MULTI_YIELD, newMultiYield);
	}

} // Yield
