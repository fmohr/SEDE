/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Deployment Procedure</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentProcedure#getSource
 * <em>Source</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentProcedure#getAct <em>Act</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentProcedure#getFetch
 * <em>Fetch</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.DeploymentProcedure#getName
 * <em>Name</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure()
 * @model kind="class"
 * @generated
 */
public class DeploymentProcedure extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DeploymentProcedure() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.DEPLOYMENT_PROCEDURE;
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
	 * Returns the value of the '<em><b>Source</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure_Source()
	 * @model
	 * @generated
	 */
	public String getSource() {
		return (String) eGet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__SOURCE, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getSource <em>Source</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	public void setSource(String newSource) {
		eSet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__SOURCE, newSource);
	}

	/**
	 * Returns the value of the '<em><b>Act</b></em>' attribute. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Act</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Act</em>' attribute.
	 * @see #setAct(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure_Act()
	 * @model
	 * @generated
	 */
	public String getAct() {
		return (String) eGet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__ACT, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getAct
	 * <em>Act</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Act</em>' attribute.
	 * @see #getAct()
	 * @generated
	 */
	public void setAct(String newAct) {
		eSet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__ACT, newAct);
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see #setFetch(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure_Fetch()
	 * @model
	 * @generated
	 */
	public String getFetch() {
		return (String) eGet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__FETCH, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getFetch <em>Fetch</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(String newFetch) {
		eSet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__FETCH, newFetch);
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
	 * @see de.upb.sede.dsl.seco.SecoPackage#getDeploymentProcedure_Name()
	 * @model
	 * @generated
	 */
	public String getName() {
		return (String) eGet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__NAME, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.DeploymentProcedure#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName) {
		eSet(SecoPackage.Literals.DEPLOYMENT_PROCEDURE__NAME, newName);
	}

} // DeploymentProcedure
