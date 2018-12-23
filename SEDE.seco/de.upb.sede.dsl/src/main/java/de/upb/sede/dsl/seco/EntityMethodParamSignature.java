/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
public class EntityMethodParamSignature extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityMethodParam> parameters;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityMethodParam> outputs;

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
	public List<EntityMethodParam> getParameters() {
		if (parameters == null) {
			parameters = new BasicInternalEList<EntityMethodParam>(EntityMethodParam.class);
		}
		return parameters;
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
	public List<EntityMethodParam> getOutputs() {
		if (outputs == null) {
			outputs = new BasicInternalEList<EntityMethodParam>(EntityMethodParam.class);
		}
		return outputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS:
			return ((InternalEList<?>) getOutputs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS:
			return getParameters();
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS:
			return getOutputs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends EntityMethodParam>) newValue);
			return;
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS:
			getOutputs().clear();
			getOutputs().addAll((Collection<? extends EntityMethodParam>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS:
			getParameters().clear();
			return;
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS:
			getOutputs().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case SecoPackage.ENTITY_METHOD_PARAM_SIGNATURE__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // EntityMethodParamSignature
