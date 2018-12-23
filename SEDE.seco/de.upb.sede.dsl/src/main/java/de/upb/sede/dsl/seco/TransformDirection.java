/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Transform Direction</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see de.upb.sede.dsl.seco.SecoPackage#getTransformDirection()
 * @model
 * @generated
 */
public enum TransformDirection implements Enumerator {
	/**
	 * The '<em><b>BI</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #BI_VALUE
	 * @generated
	 * @ordered
	 */
	BI(0, "BI", "<->"),

	/**
	 * The '<em><b>TO</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #TO_VALUE
	 * @generated
	 * @ordered
	 */
	TO(1, "TO", "->"),

	/**
	 * The '<em><b>FROM</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #FROM_VALUE
	 * @generated
	 * @ordered
	 */
	FROM(2, "FROM", "<-");

	/**
	 * The '<em><b>BI</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BI</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BI
	 * @model literal="<->"
	 * @generated
	 * @ordered
	 */
	public static final int BI_VALUE = 0;

	/**
	 * The '<em><b>TO</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TO</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #TO
	 * @model literal="->"
	 * @generated
	 * @ordered
	 */
	public static final int TO_VALUE = 1;

	/**
	 * The '<em><b>FROM</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FROM</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #FROM
	 * @model literal="<-"
	 * @generated
	 * @ordered
	 */
	public static final int FROM_VALUE = 2;

	/**
	 * An array of all the '<em><b>Transform Direction</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final TransformDirection[] VALUES_ARRAY = new TransformDirection[] { BI, TO, FROM, };

	/**
	 * A public read-only list of all the '<em><b>Transform Direction</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<TransformDirection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Transform Direction</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static TransformDirection get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TransformDirection result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Transform Direction</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static TransformDirection getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TransformDirection result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Transform Direction</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static TransformDirection get(int value) {
		switch (value) {
		case BI_VALUE:
			return BI;
		case TO_VALUE:
			return TO;
		case FROM_VALUE:
			return FROM;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private TransformDirection(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // TransformDirection
