/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity
 * Method Param Signature</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParamSignature#getParameters
 * <em>Parameters</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodParamSignature#getOutputs
 * <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParamSignature()
 * @model kind="class"
 * @generated
 */
public class EntityMethodParamSignature extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodParamSignature() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_METHOD_PARAM_SIGNATURE;
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
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.EntityMethodParam}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParamSignature_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EntityMethodParam> getParameters() {
		return (EList<EntityMethodParam>) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS, true);
	}

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.EntityMethodParam}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodParamSignature_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EntityMethodParam> getOutputs() {
		return (EList<EntityMethodParam>) eGet(SecoPackage.Literals.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS, true);
	}

} // EntityMethodParamSignature
