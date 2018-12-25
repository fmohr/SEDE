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
 * '<em><b>Assignment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Assignment#getAssignedFields <em>Assigned
 * Fields</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Assignment#isMultiAssignment <em>Multi
 * Assignment</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Assignment#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment()
 * @model kind="class"
 * @generated
 */
public class Assignment extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Assignment() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ASSIGNMENT;
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
	 * Returns the value of the '<em><b>Assigned Fields</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.Field}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assigned Fields</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Assigned Fields</em>' containment reference
	 *         list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_AssignedFields()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Field> getAssignedFields() {
		return (EList<Field>) eGet(SecoPackage.Literals.ASSIGNMENT__ASSIGNED_FIELDS, true);
	}

	/**
	 * Returns the value of the '<em><b>Multi Assignment</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Assignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Multi Assignment</em>' attribute.
	 * @see #setMultiAssignment(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_MultiAssignment()
	 * @model
	 * @generated
	 */
	public boolean isMultiAssignment() {
		return (Boolean) eGet(SecoPackage.Literals.ASSIGNMENT__MULTI_ASSIGNMENT, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.Assignment#isMultiAssignment <em>Multi
	 * Assignment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Multi Assignment</em>' attribute.
	 * @see #isMultiAssignment()
	 * @generated
	 */
	public void setMultiAssignment(boolean newMultiAssignment) {
		eSet(SecoPackage.Literals.ASSIGNMENT__MULTI_ASSIGNMENT, newMultiAssignment);
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
	 * @see de.upb.sede.dsl.seco.SecoPackage#getAssignment_Value()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getValue() {
		return (FieldValue) eGet(SecoPackage.Literals.ASSIGNMENT__VALUE, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Assignment#getValue
	 * <em>Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(FieldValue newValue) {
		eSet(SecoPackage.Literals.ASSIGNMENT__VALUE, newValue);
	}

} // Assignment
