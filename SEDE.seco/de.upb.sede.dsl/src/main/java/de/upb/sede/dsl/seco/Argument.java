/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Argument</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Argument#isIndexed <em>Indexed</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Argument#getIndex <em>Index</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Argument#getParameterName <em>Parameter
 * Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Argument#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument()
 * @model kind="class"
 * @generated
 */
public class Argument extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Argument() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ARGUMENT;
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
	 * Returns the value of the '<em><b>Indexed</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexed</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Indexed</em>' attribute.
	 * @see #setIndexed(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument_Indexed()
	 * @model
	 * @generated
	 */
	public boolean isIndexed() {
		return (Boolean) eGet(SecoPackage.Literals.ARGUMENT__INDEXED, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Argument#isIndexed
	 * <em>Indexed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Indexed</em>' attribute.
	 * @see #isIndexed()
	 * @generated
	 */
	public void setIndexed(boolean newIndexed) {
		eSet(SecoPackage.Literals.ARGUMENT__INDEXED, newIndexed);
	}

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument_Index()
	 * @model
	 * @generated
	 */
	public int getIndex() {
		return (Integer) eGet(SecoPackage.Literals.ARGUMENT__INDEX, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Argument#getIndex
	 * <em>Index</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	public void setIndex(int newIndex) {
		eSet(SecoPackage.Literals.ARGUMENT__INDEX, newIndex);
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
	 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument_ParameterName()
	 * @model
	 * @generated
	 */
	public String getParameterName() {
		return (String) eGet(SecoPackage.Literals.ARGUMENT__PARAMETER_NAME, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Argument#getParameterName
	 * <em>Parameter Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Parameter Name</em>' attribute.
	 * @see #getParameterName()
	 * @generated
	 */
	public void setParameterName(String newParameterName) {
		eSet(SecoPackage.Literals.ARGUMENT__PARAMETER_NAME, newParameterName);
	}

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getArgument_Value()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getValue() {
		return (FieldValue) eGet(SecoPackage.Literals.ARGUMENT__VALUE, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Argument#getValue
	 * <em>Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(FieldValue newValue) {
		eSet(SecoPackage.Literals.ARGUMENT__VALUE, newValue);
	}

} // Argument
