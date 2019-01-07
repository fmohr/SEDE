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
 * '<em><b>Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.Operation#getContextField <em>Context
 * Field</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Operation#getEntityName <em>Entity
 * Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Operation#getMethod <em>Method</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.Operation#getArgs <em>Args</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation()
 * @model kind="class"
 * @generated
 */
public class Operation extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Operation() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.OPERATION;
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
	 * Returns the value of the '<em><b>Context Field</b></em>' containment
	 * reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Field</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Context Field</em>' containment reference.
	 * @see #setContextField(Field)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_ContextField()
	 * @model containment="true"
	 * @generated
	 */
	public Field getContextField() {
		return (Field) eGet(SecoPackage.Literals.OPERATION__CONTEXT_FIELD, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getContextField
	 * <em>Context Field</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Context Field</em>' containment
	 *              reference.
	 * @see #getContextField()
	 * @generated
	 */
	public void setContextField(Field newContextField) {
		eSet(SecoPackage.Literals.OPERATION__CONTEXT_FIELD, newContextField);
	}

	/**
	 * Returns the value of the '<em><b>Entity Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entity Name</em>' attribute.
	 * @see #setEntityName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_EntityName()
	 * @model
	 * @generated
	 */
	public String getEntityName() {
		return (String) eGet(SecoPackage.Literals.OPERATION__ENTITY_NAME, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getEntityName
	 * <em>Entity Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Entity Name</em>' attribute.
	 * @see #getEntityName()
	 * @generated
	 */
	public void setEntityName(String newEntityName) {
		eSet(SecoPackage.Literals.OPERATION__ENTITY_NAME, newEntityName);
	}

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_Method()
	 * @model
	 * @generated
	 */
	public String getMethod() {
		return (String) eGet(SecoPackage.Literals.OPERATION__METHOD, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.Operation#getMethod
	 * <em>Method</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	public void setMethod(String newMethod) {
		eSet(SecoPackage.Literals.OPERATION__METHOD, newMethod);
	}

	/**
	 * Returns the value of the '<em><b>Args</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.Argument}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Args</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Args</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getOperation_Args()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<Argument> getArgs() {
		return (List<Argument>) eGet(SecoPackage.Literals.OPERATION__ARGS, true);
	}

} // Operation
