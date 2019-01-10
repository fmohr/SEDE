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
 * Method Instructions</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodInstructions#getInstructions
 * <em>Instructions</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityMethodInstructions#getOrder
 * <em>Order</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodInstructions()
 * @model kind="class"
 * @generated
 */
public class EntityMethodInstructions extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityMethodInstructions() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_METHOD_INSTRUCTIONS;
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
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodInstructions_Instructions()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<EObject> getInstructions() {
		return (List<EObject>) eGet(SecoPackage.Literals.ENTITY_METHOD_INSTRUCTIONS__INSTRUCTIONS, true);
	}

	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute. The default value
	 * is <code>"5"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(int)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityMethodInstructions_Order()
	 * @model default="5"
	 * @generated
	 */
	public int getOrder() {
		return (Integer) eGet(SecoPackage.Literals.ENTITY_METHOD_INSTRUCTIONS__ORDER, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityMethodInstructions#getOrder
	 * <em>Order</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	public void setOrder(int newOrder) {
		eSet(SecoPackage.Literals.ENTITY_METHOD_INSTRUCTIONS__ORDER, newOrder);
	}

} // EntityMethodInstructions
