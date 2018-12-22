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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.Operation#getContextField <em>Context Field</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Operation#getEntityName <em>Entity Name</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Operation#getMethod <em>Method</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.Operation#getArgs <em>Args</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation()
 * @model kind="class"
 * @generated
 */
public class Operation extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getContextField() <em>Context Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextField()
	 * @generated
	 * @ordered
	 */
	protected Field contextField;

	/**
	 * The default value of the '{@link #getEntityName() <em>Entity Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityName()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTITY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntityName() <em>Entity Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityName()
	 * @generated
	 * @ordered
	 */
	protected String entityName = ENTITY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected String method = METHOD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArgs() <em>Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<Argument> args;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.OPERATION;
	}

	/**
	 * Returns the value of the '<em><b>Context Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Field</em>' containment reference.
	 * @see #setContextField(Field)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_ContextField()
	 * @model containment="true"
	 * @generated
	 */
	public Field getContextField() {
		return contextField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContextField(Field newContextField, NotificationChain msgs) {
		Field oldContextField = contextField;
		contextField = newContextField;
		return msgs;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getContextField <em>Context Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Field</em>' containment reference.
	 * @see #getContextField()
	 * @generated
	 */
	public void setContextField(Field newContextField) {
		if (newContextField != contextField) {
			NotificationChain msgs = null;
			if (contextField != null)
				msgs = ((InternalEObject)contextField).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SecoPackage.OPERATION__CONTEXT_FIELD, null, msgs);
			if (newContextField != null)
				msgs = ((InternalEObject)newContextField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SecoPackage.OPERATION__CONTEXT_FIELD, null, msgs);
			msgs = basicSetContextField(newContextField, msgs);
			if (msgs != null) msgs.dispatch();
		}
	}

	/**
	 * Returns the value of the '<em><b>Entity Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Name</em>' attribute.
	 * @see #setEntityName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_EntityName()
	 * @model
	 * @generated
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getEntityName <em>Entity Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Name</em>' attribute.
	 * @see #getEntityName()
	 * @generated
	 */
	public void setEntityName(String newEntityName) {
		entityName = newEntityName;
	}

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_Method()
	 * @model
	 * @generated
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	public void setMethod(String newMethod) {
		method = newMethod;
	}

	/**
	 * Returns the value of the '<em><b>Args</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.Argument}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Args</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Args</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_Args()
	 * @model containment="true"
	 * @generated
	 */
	public List<Argument> getArgs() {
		if (args == null) {
			args = new BasicInternalEList<Argument>(Argument.class);
		}
		return args;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SecoPackage.OPERATION__CONTEXT_FIELD:
				return basicSetContextField(null, msgs);
			case SecoPackage.OPERATION__ARGS:
				return ((InternalEList<?>)getArgs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SecoPackage.OPERATION__CONTEXT_FIELD:
				return getContextField();
			case SecoPackage.OPERATION__ENTITY_NAME:
				return getEntityName();
			case SecoPackage.OPERATION__METHOD:
				return getMethod();
			case SecoPackage.OPERATION__ARGS:
				return getArgs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SecoPackage.OPERATION__CONTEXT_FIELD:
				setContextField((Field)newValue);
				return;
			case SecoPackage.OPERATION__ENTITY_NAME:
				setEntityName((String)newValue);
				return;
			case SecoPackage.OPERATION__METHOD:
				setMethod((String)newValue);
				return;
			case SecoPackage.OPERATION__ARGS:
				getArgs().clear();
				getArgs().addAll((Collection<? extends Argument>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SecoPackage.OPERATION__CONTEXT_FIELD:
				setContextField((Field)null);
				return;
			case SecoPackage.OPERATION__ENTITY_NAME:
				setEntityName(ENTITY_NAME_EDEFAULT);
				return;
			case SecoPackage.OPERATION__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case SecoPackage.OPERATION__ARGS:
				getArgs().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SecoPackage.OPERATION__CONTEXT_FIELD:
				return contextField != null;
			case SecoPackage.OPERATION__ENTITY_NAME:
				return ENTITY_NAME_EDEFAULT == null ? entityName != null : !ENTITY_NAME_EDEFAULT.equals(entityName);
			case SecoPackage.OPERATION__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
			case SecoPackage.OPERATION__ARGS:
				return args != null && !args.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (entityName: ");
		result.append(entityName);
		result.append(", method: ");
		result.append(method);
		result.append(')');
		return result.toString();
	}

} // Operation
