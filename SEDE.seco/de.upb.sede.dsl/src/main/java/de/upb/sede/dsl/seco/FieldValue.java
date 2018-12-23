/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Field
 * Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
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
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getFieldValue()
 * @model kind="class"
 * @generated
 */
public class FieldValue extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #isCast() <em>Cast</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isCast()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCast() <em>Cast</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isCast()
	 * @generated
	 * @ordered
	 */
	protected boolean cast = CAST_EDEFAULT;

	/**
	 * The default value of the '{@link #getCastTarget() <em>Cast Target</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCastTarget()
	 * @generated
	 * @ordered
	 */
	protected static final String CAST_TARGET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCastTarget() <em>Cast Target</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCastTarget()
	 * @generated
	 * @ordered
	 */
	protected String castTarget = CAST_TARGET_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCastValue() <em>Cast Value</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCastValue()
	 * @generated
	 * @ordered
	 */
	protected FieldValue castValue;

	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected String number = NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getString() <em>String</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getString()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getString() <em>String</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getString()
	 * @generated
	 * @ordered
	 */
	protected String string = STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getBool() <em>Bool</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBool()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBool() <em>Bool</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBool()
	 * @generated
	 * @ordered
	 */
	protected String bool = BOOL_EDEFAULT;

	/**
	 * The default value of the '{@link #isNull() <em>Null</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNull()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NULL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNull() <em>Null</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNull()
	 * @generated
	 * @ordered
	 */
	protected boolean null_ = NULL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected Operation operation;

	/**
	 * The cached value of the '{@link #getField() <em>Field</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getField()
	 * @generated
	 * @ordered
	 */
	protected Field field;

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
		return cast;
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
		cast = newCast;
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
		return castTarget;
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
		castTarget = newCastTarget;
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
		return castValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCastValue(FieldValue newCastValue, NotificationChain msgs) {
		FieldValue oldCastValue = castValue;
		castValue = newCastValue;
		return msgs;
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
		if (newCastValue != castValue) {
			NotificationChain msgs = null;
			if (castValue != null)
				msgs = ((InternalEObject) castValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__CAST_VALUE, null, msgs);
			if (newCastValue != null)
				msgs = ((InternalEObject) newCastValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__CAST_VALUE, null, msgs);
			msgs = basicSetCastValue(newCastValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
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
		return number;
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
		number = newNumber;
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
		return string;
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
		string = newString;
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
		return bool;
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
		bool = newBool;
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
		return null_;
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
		null_ = newNull;
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
		return operation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOperation(Operation newOperation, NotificationChain msgs) {
		Operation oldOperation = operation;
		operation = newOperation;
		return msgs;
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
		if (newOperation != operation) {
			NotificationChain msgs = null;
			if (operation != null)
				msgs = ((InternalEObject) operation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__OPERATION, null, msgs);
			if (newOperation != null)
				msgs = ((InternalEObject) newOperation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__OPERATION, null, msgs);
			msgs = basicSetOperation(newOperation, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
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
		return field;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetField(Field newField, NotificationChain msgs) {
		Field oldField = field;
		field = newField;
		return msgs;
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
		if (newField != field) {
			NotificationChain msgs = null;
			if (field != null)
				msgs = ((InternalEObject) field).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__FIELD, null, msgs);
			if (newField != null)
				msgs = ((InternalEObject) newField).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SecoPackage.FIELD_VALUE__FIELD, null, msgs);
			msgs = basicSetField(newField, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SecoPackage.FIELD_VALUE__CAST_VALUE:
			return basicSetCastValue(null, msgs);
		case SecoPackage.FIELD_VALUE__OPERATION:
			return basicSetOperation(null, msgs);
		case SecoPackage.FIELD_VALUE__FIELD:
			return basicSetField(null, msgs);
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
		case SecoPackage.FIELD_VALUE__CAST:
			return isCast();
		case SecoPackage.FIELD_VALUE__CAST_TARGET:
			return getCastTarget();
		case SecoPackage.FIELD_VALUE__CAST_VALUE:
			return getCastValue();
		case SecoPackage.FIELD_VALUE__NUMBER:
			return getNumber();
		case SecoPackage.FIELD_VALUE__STRING:
			return getString();
		case SecoPackage.FIELD_VALUE__BOOL:
			return getBool();
		case SecoPackage.FIELD_VALUE__NULL:
			return isNull();
		case SecoPackage.FIELD_VALUE__OPERATION:
			return getOperation();
		case SecoPackage.FIELD_VALUE__FIELD:
			return getField();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SecoPackage.FIELD_VALUE__CAST:
			setCast((Boolean) newValue);
			return;
		case SecoPackage.FIELD_VALUE__CAST_TARGET:
			setCastTarget((String) newValue);
			return;
		case SecoPackage.FIELD_VALUE__CAST_VALUE:
			setCastValue((FieldValue) newValue);
			return;
		case SecoPackage.FIELD_VALUE__NUMBER:
			setNumber((String) newValue);
			return;
		case SecoPackage.FIELD_VALUE__STRING:
			setString((String) newValue);
			return;
		case SecoPackage.FIELD_VALUE__BOOL:
			setBool((String) newValue);
			return;
		case SecoPackage.FIELD_VALUE__NULL:
			setNull((Boolean) newValue);
			return;
		case SecoPackage.FIELD_VALUE__OPERATION:
			setOperation((Operation) newValue);
			return;
		case SecoPackage.FIELD_VALUE__FIELD:
			setField((Field) newValue);
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
		case SecoPackage.FIELD_VALUE__CAST:
			setCast(CAST_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__CAST_TARGET:
			setCastTarget(CAST_TARGET_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__CAST_VALUE:
			setCastValue((FieldValue) null);
			return;
		case SecoPackage.FIELD_VALUE__NUMBER:
			setNumber(NUMBER_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__STRING:
			setString(STRING_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__BOOL:
			setBool(BOOL_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__NULL:
			setNull(NULL_EDEFAULT);
			return;
		case SecoPackage.FIELD_VALUE__OPERATION:
			setOperation((Operation) null);
			return;
		case SecoPackage.FIELD_VALUE__FIELD:
			setField((Field) null);
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
		case SecoPackage.FIELD_VALUE__CAST:
			return cast != CAST_EDEFAULT;
		case SecoPackage.FIELD_VALUE__CAST_TARGET:
			return CAST_TARGET_EDEFAULT == null ? castTarget != null : !CAST_TARGET_EDEFAULT.equals(castTarget);
		case SecoPackage.FIELD_VALUE__CAST_VALUE:
			return castValue != null;
		case SecoPackage.FIELD_VALUE__NUMBER:
			return NUMBER_EDEFAULT == null ? number != null : !NUMBER_EDEFAULT.equals(number);
		case SecoPackage.FIELD_VALUE__STRING:
			return STRING_EDEFAULT == null ? string != null : !STRING_EDEFAULT.equals(string);
		case SecoPackage.FIELD_VALUE__BOOL:
			return BOOL_EDEFAULT == null ? bool != null : !BOOL_EDEFAULT.equals(bool);
		case SecoPackage.FIELD_VALUE__NULL:
			return null_ != NULL_EDEFAULT;
		case SecoPackage.FIELD_VALUE__OPERATION:
			return operation != null;
		case SecoPackage.FIELD_VALUE__FIELD:
			return field != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (cast: ");
		result.append(cast);
		result.append(", castTarget: ");
		result.append(castTarget);
		result.append(", number: ");
		result.append(number);
		result.append(", string: ");
		result.append(string);
		result.append(", bool: ");
		result.append(bool);
		result.append(", null: ");
		result.append(null_);
		result.append(')');
		return result.toString();
	}

} // FieldValue
