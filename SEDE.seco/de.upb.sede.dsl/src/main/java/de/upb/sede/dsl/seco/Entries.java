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
 * '<em><b>Entries</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Entries#getInstructions
 * <em>Instructions</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Entries#getEntities <em>Entities</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries()
 * @model kind="class"
 * @generated
 */
public class Entries extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Entries() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTRIES;
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
	 * Returns the value of the '<em><b>Instructions</b></em>' containment reference
	 * list. The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instructions</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Instructions</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries_Instructions()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EObject> getInstructions() {
		return (EList<EObject>) eGet(SecoPackage.Literals.ENTRIES__INSTRUCTIONS, true);
	}

	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.EntityClassDefinition}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries_Entities()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EntityClassDefinition> getEntities() {
		return (EList<EntityClassDefinition>) eGet(SecoPackage.Literals.ENTRIES__ENTITIES, true);
	}

} // Entries
