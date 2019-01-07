/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Deployment Dependency</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentDependency#getDeployment
 * <em>Deployment</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentDependency#getOrder
 * <em>Order</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentDependency()
 * @model kind="class"
 * @generated
 */
public class DeploymentDependency extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DeploymentDependency() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.DEPLOYMENT_DEPENDENCY;
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
	 * Returns the value of the '<em><b>Deployment</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deployment</em>' attribute.
	 * @see #setDeployment(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentDependency_Deployment()
	 * @model
	 * @generated
	 */
	public String getDeployment() {
		return (String) eGet(SecoPackage.Literals.DEPLOYMENT_DEPENDENCY__DEPLOYMENT, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.DeploymentDependency#getDeployment
	 * <em>Deployment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Deployment</em>' attribute.
	 * @see #getDeployment()
	 * @generated
	 */
	public void setDeployment(String newDeployment) {
		eSet(SecoPackage.Literals.DEPLOYMENT_DEPENDENCY__DEPLOYMENT, newDeployment);
	}

	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(int)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentDependency_Order()
	 * @model
	 * @generated
	 */
	public int getOrder() {
		return (Integer) eGet(SecoPackage.Literals.DEPLOYMENT_DEPENDENCY__ORDER, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.DeploymentDependency#getOrder <em>Order</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	public void setOrder(int newOrder) {
		eSet(SecoPackage.Literals.DEPLOYMENT_DEPENDENCY__ORDER, newOrder);
	}

} // DeploymentDependency
