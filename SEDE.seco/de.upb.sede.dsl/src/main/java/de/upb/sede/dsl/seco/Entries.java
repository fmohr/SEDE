/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Entries</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Entries#getInstructions
 * <em>Instructions</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Entries#getEntities <em>Entities</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Entries#getDeployments
 * <em>Deployments</em>}</li>
 * </ul>
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
	public List<EObject> getInstructions() {
		return (List<EObject>) eGet(SecoPackage.Literals.ENTRIES__INSTRUCTIONS, true);
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
	public List<EntityClassDefinition> getEntities() {
		return (List<EntityClassDefinition>) eGet(SecoPackage.Literals.ENTRIES__ENTITIES, true);
	}

	/**
	 * Returns the value of the '<em><b>Deployments</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.EntityDeploymentDefinition}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Deployments</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deployments</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntries_Deployments()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<EntityDeploymentDefinition> getDeployments() {
		return (List<EntityDeploymentDefinition>) eGet(SecoPackage.Literals.ENTRIES__DEPLOYMENTS, true);
	}

} // Entries