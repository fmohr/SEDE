/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import de.upb.sede.dsl.SecoObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Field
 * Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#isCast <em>Cast</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getCastTarget <em>Cast
 * Target</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getCastValue <em>Cast
 * Value</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getNumber <em>Number</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getString <em>String</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getBool <em>Bool</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#isNull <em>Null</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getOperation
 * <em>Operation</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.FieldValue#getField <em>Field</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue()
 * @model kind="class"
 * @generated
 */
public class FieldValue extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FieldValue() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.FIELD_VALUE;
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
	 * Returns the value of the '<em><b>Cast</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cast</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cast</em>' attribute.
	 * @see #setCast(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Cast()
	 * @model
	 * @generated
	 */
	public boolean isCast() {
		return (Boolean) eGet(SecoPackage.Literals.FIELD_VALUE__CAST, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#isCast
	 * <em>Cast</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Cast</em>' attribute.
	 * @see #isCast()
	 * @generated
	 */
	public void setCast(boolean newCast) {
		eSet(SecoPackage.Literals.FIELD_VALUE__CAST, newCast);
	}

	/**
	 * Returns the value of the '<em><b>Cast Target</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cast Target</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cast Target</em>' attribute.
	 * @see #setCastTarget(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_CastTarget()
	 * @model
	 * @generated
	 */
	public String getCastTarget() {
		return (String) eGet(SecoPackage.Literals.FIELD_VALUE__CAST_TARGET, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getCastTarget
	 * <em>Cast Target</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Cast Target</em>' attribute.
	 * @see #getCastTarget()
	 * @generated
	 */
	public void setCastTarget(String newCastTarget) {
		eSet(SecoPackage.Literals.FIELD_VALUE__CAST_TARGET, newCastTarget);
	}

	/**
	 * Returns the value of the '<em><b>Cast Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cast Value</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cast Value</em>' containment reference.
	 * @see #setCastValue(FieldValue)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_CastValue()
	 * @model containment="true"
	 * @generated
	 */
	public FieldValue getCastValue() {
		return (FieldValue) eGet(SecoPackage.Literals.FIELD_VALUE__CAST_VALUE, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getCastValue
	 * <em>Cast Value</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Cast Value</em>' containment
	 *              reference.
	 * @see #getCastValue()
	 * @generated
	 */
	public void setCastValue(FieldValue newCastValue) {
		eSet(SecoPackage.Literals.FIELD_VALUE__CAST_VALUE, newCastValue);
	}

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Number()
	 * @model
	 * @generated
	 */
	public String getNumber() {
		return (String) eGet(SecoPackage.Literals.FIELD_VALUE__NUMBER, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getNumber
	 * <em>Number</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	public void setNumber(String newNumber) {
		eSet(SecoPackage.Literals.FIELD_VALUE__NUMBER, newNumber);
	}

	/**
	 * Returns the value of the '<em><b>String</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>String</em>' attribute.
	 * @see #setString(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_String()
	 * @model
	 * @generated
	 */
	public String getString() {
		return (String) eGet(SecoPackage.Literals.FIELD_VALUE__STRING, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getString
	 * <em>String</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>String</em>' attribute.
	 * @see #getString()
	 * @generated
	 */
	public void setString(String newString) {
		eSet(SecoPackage.Literals.FIELD_VALUE__STRING, newString);
	}

	/**
	 * Returns the value of the '<em><b>Bool</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bool</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bool</em>' attribute.
	 * @see #setBool(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Bool()
	 * @model
	 * @generated
	 */
	public String getBool() {
		return (String) eGet(SecoPackage.Literals.FIELD_VALUE__BOOL, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getBool
	 * <em>Bool</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Bool</em>' attribute.
	 * @see #getBool()
	 * @generated
	 */
	public void setBool(String newBool) {
		eSet(SecoPackage.Literals.FIELD_VALUE__BOOL, newBool);
	}

	/**
	 * Returns the value of the '<em><b>Null</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Null</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Null</em>' attribute.
	 * @see #setNull(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Null()
	 * @model
	 * @generated
	 */
	public boolean isNull() {
		return (Boolean) eGet(SecoPackage.Literals.FIELD_VALUE__NULL, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#isNull
	 * <em>Null</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Null</em>' attribute.
	 * @see #isNull()
	 * @generated
	 */
	public void setNull(boolean newNull) {
		eSet(SecoPackage.Literals.FIELD_VALUE__NULL, newNull);
	}

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operation</em>' containment reference.
	 * @see #setOperation(Operation)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Operation()
	 * @model containment="true"
	 * @generated
	 */
	public Operation getOperation() {
		return (Operation) eGet(SecoPackage.Literals.FIELD_VALUE__OPERATION, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getOperation
	 * <em>Operation</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Operation</em>' containment reference.
	 * @see #getOperation()
	 * @generated
	 */
	public void setOperation(Operation newOperation) {
		eSet(SecoPackage.Literals.FIELD_VALUE__OPERATION, newOperation);
	}

	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Field</em>' containment reference.
	 * @see #setField(Field)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue_Field()
	 * @model containment="true"
	 * @generated
	 */
	public Field getField() {
		return (Field) eGet(SecoPackage.Literals.FIELD_VALUE__FIELD, true);
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.FieldValue#getField
	 * <em>Field</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Field</em>' containment reference.
	 * @see #getField()
	 * @generated
	 */
	public void setField(Field newField) {
		eSet(SecoPackage.Literals.FIELD_VALUE__FIELD, newField);
	}

} // FieldValue
