/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

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
 * <li>{@link de.upb.sede.dsl.seco.Field#getMember <em>Member</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getField()
 * @model kind="class"
 * @generated
 */
public class Field extends SecoObject implements EObject {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
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
		return (String) eGet(SecoPackage.Literals.FIELD__NAME, true);
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
		eSet(SecoPackage.Literals.FIELD__NAME, newName);
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
		return (Boolean) eGet(SecoPackage.Literals.FIELD__DEREFERENCE, true);
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
		eSet(SecoPackage.Literals.FIELD__DEREFERENCE, newDereference);
	}

	/**
	 * Returns the value of the '<em><b>Member</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Member</em>' containment reference.
	 * @see #setMember(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getField_Member()
	 * @model containment="true"
	 * @generated
	 */
	public Field getMember() {
		return (Field) eGet(SecoPackage.Literals.FIELD__MEMBER, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Field#getMember
	 * <em>Member</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Member</em>' containment reference.
	 * @see #getMember()
	 * @generated
	 */
	public void setMember(Field newMember) {
		eSet(SecoPackage.Literals.FIELD__MEMBER, newMember);
	}

} // Field
