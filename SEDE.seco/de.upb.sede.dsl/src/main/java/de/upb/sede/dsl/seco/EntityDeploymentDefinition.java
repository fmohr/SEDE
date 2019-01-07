/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity
 * Deployment Definition</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getProcedures
 * <em>Procedures</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getQualifiedName
 * <em>Qualified Name</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition()
 * @model kind="class"
 * @generated
 */
public class EntityDeploymentDefinition extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityDeploymentDefinition() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_DEPLOYMENT_DEFINITION;
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
	 * Returns the value of the '<em><b>Procedures</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.DeploymentProcedure}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Procedures</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Procedures</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition_Procedures()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<DeploymentProcedure> getProcedures() {
		return (List<DeploymentProcedure>) eGet(SecoPackage.Literals.ENTITY_DEPLOYMENT_DEFINITION__PROCEDURES, true);
	}

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.DeploymentDependency}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<DeploymentDependency> getDependencies() {
		return (List<DeploymentDependency>) eGet(SecoPackage.Literals.ENTITY_DEPLOYMENT_DEFINITION__DEPENDENCIES, true);
	}

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityDeploymentDefinition_QualifiedName()
	 * @model
	 * @generated
	 */
	public String getQualifiedName() {
		return (String) eGet(SecoPackage.Literals.ENTITY_DEPLOYMENT_DEFINITION__QUALIFIED_NAME, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityDeploymentDefinition#getQualifiedName
	 * <em>Qualified Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	public void setQualifiedName(String newQualifiedName) {
		eSet(SecoPackage.Literals.ENTITY_DEPLOYMENT_DEFINITION__QUALIFIED_NAME, newQualifiedName);
	}

} // EntityDeploymentDefinition
