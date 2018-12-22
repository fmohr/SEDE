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
 * A representation of the model object '<em><b>Entity Class Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper <em>Wrapper</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity <em>Wrapped Entity</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#isExtension <em>Extension</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getBaseEntities <em>Base Entities</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getMethods <em>Methods</em>}</li>
 *   <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getCasts <em>Casts</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition()
 * @model kind="class"
 * @generated
 */
public class EntityClassDefinition extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isWrapper() <em>Wrapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWrapper()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WRAPPER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWrapper() <em>Wrapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWrapper()
	 * @generated
	 * @ordered
	 */
	protected boolean wrapper = WRAPPER_EDEFAULT;

	/**
	 * The default value of the '{@link #getWrappedEntity() <em>Wrapped Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWrappedEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String WRAPPED_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWrappedEntity() <em>Wrapped Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWrappedEntity()
	 * @generated
	 * @ordered
	 */
	protected String wrappedEntity = WRAPPED_ENTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtension()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTENSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtension()
	 * @generated
	 * @ordered
	 */
	protected boolean extension = EXTENSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBaseEntities() <em>Base Entities</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<String> baseEntities;

	/**
	 * The cached value of the '{@link #getMethods() <em>Methods</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethods()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityMethod> methods;

	/**
	 * The cached value of the '{@link #getCasts() <em>Casts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCasts()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityCast> casts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityClassDefinition() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_CLASS_DEFINITION;
	}

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_QualifiedName()
	 * @model
	 * @generated
	 */
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	public void setQualifiedName(String newQualifiedName) {
		qualifiedName = newQualifiedName;
	}

	/**
	 * Returns the value of the '<em><b>Wrapper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wrapper</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wrapper</em>' attribute.
	 * @see #setWrapper(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Wrapper()
	 * @model
	 * @generated
	 */
	public boolean isWrapper() {
		return wrapper;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper <em>Wrapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wrapper</em>' attribute.
	 * @see #isWrapper()
	 * @generated
	 */
	public void setWrapper(boolean newWrapper) {
		wrapper = newWrapper;
	}

	/**
	 * Returns the value of the '<em><b>Wrapped Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wrapped Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wrapped Entity</em>' attribute.
	 * @see #setWrappedEntity(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_WrappedEntity()
	 * @model
	 * @generated
	 */
	public String getWrappedEntity() {
		return wrappedEntity;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity <em>Wrapped Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wrapped Entity</em>' attribute.
	 * @see #getWrappedEntity()
	 * @generated
	 */
	public void setWrappedEntity(String newWrappedEntity) {
		wrappedEntity = newWrappedEntity;
	}

	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see #setExtension(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Extension()
	 * @model
	 * @generated
	 */
	public boolean isExtension() {
		return extension;
	}

	/**
	 * Sets the value of the '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see #isExtension()
	 * @generated
	 */
	public void setExtension(boolean newExtension) {
		extension = newExtension;
	}

	/**
	 * Returns the value of the '<em><b>Base Entities</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Entities</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Entities</em>' attribute list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_BaseEntities()
	 * @model unique="false"
	 * @generated
	 */
	public List<String> getBaseEntities() {
		if (baseEntities == null) {
			baseEntities = new BasicInternalEList<String>(String.class);
		}
		return baseEntities;
	}

	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.EntityMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Methods()
	 * @model containment="true"
	 * @generated
	 */
	public List<EntityMethod> getMethods() {
		if (methods == null) {
			methods = new BasicInternalEList<EntityMethod>(EntityMethod.class);
		}
		return methods;
	}

	/**
	 * Returns the value of the '<em><b>Casts</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.EntityCast}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Casts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Casts</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Casts()
	 * @model containment="true"
	 * @generated
	 */
	public List<EntityCast> getCasts() {
		if (casts == null) {
			casts = new BasicInternalEList<EntityCast>(EntityCast.class);
		}
		return casts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SecoPackage.ENTITY_CLASS_DEFINITION__METHODS:
				return ((InternalEList<?>)getMethods()).basicRemove(otherEnd, msgs);
			case SecoPackage.ENTITY_CLASS_DEFINITION__CASTS:
				return ((InternalEList<?>)getCasts()).basicRemove(otherEnd, msgs);
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
			case SecoPackage.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME:
				return getQualifiedName();
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPER:
				return isWrapper();
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY:
				return getWrappedEntity();
			case SecoPackage.ENTITY_CLASS_DEFINITION__EXTENSION:
				return isExtension();
			case SecoPackage.ENTITY_CLASS_DEFINITION__BASE_ENTITIES:
				return getBaseEntities();
			case SecoPackage.ENTITY_CLASS_DEFINITION__METHODS:
				return getMethods();
			case SecoPackage.ENTITY_CLASS_DEFINITION__CASTS:
				return getCasts();
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
			case SecoPackage.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPER:
				setWrapper((Boolean)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY:
				setWrappedEntity((String)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__EXTENSION:
				setExtension((Boolean)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__BASE_ENTITIES:
				getBaseEntities().clear();
				getBaseEntities().addAll((Collection<? extends String>)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__METHODS:
				getMethods().clear();
				getMethods().addAll((Collection<? extends EntityMethod>)newValue);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__CASTS:
				getCasts().clear();
				getCasts().addAll((Collection<? extends EntityCast>)newValue);
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
			case SecoPackage.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPER:
				setWrapper(WRAPPER_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY:
				setWrappedEntity(WRAPPED_ENTITY_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__EXTENSION:
				setExtension(EXTENSION_EDEFAULT);
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__BASE_ENTITIES:
				getBaseEntities().clear();
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__METHODS:
				getMethods().clear();
				return;
			case SecoPackage.ENTITY_CLASS_DEFINITION__CASTS:
				getCasts().clear();
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
			case SecoPackage.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPER:
				return wrapper != WRAPPER_EDEFAULT;
			case SecoPackage.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY:
				return WRAPPED_ENTITY_EDEFAULT == null ? wrappedEntity != null : !WRAPPED_ENTITY_EDEFAULT.equals(wrappedEntity);
			case SecoPackage.ENTITY_CLASS_DEFINITION__EXTENSION:
				return extension != EXTENSION_EDEFAULT;
			case SecoPackage.ENTITY_CLASS_DEFINITION__BASE_ENTITIES:
				return baseEntities != null && !baseEntities.isEmpty();
			case SecoPackage.ENTITY_CLASS_DEFINITION__METHODS:
				return methods != null && !methods.isEmpty();
			case SecoPackage.ENTITY_CLASS_DEFINITION__CASTS:
				return casts != null && !casts.isEmpty();
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
		result.append(" (qualifiedName: ");
		result.append(qualifiedName);
		result.append(", wrapper: ");
		result.append(wrapper);
		result.append(", wrappedEntity: ");
		result.append(wrappedEntity);
		result.append(", extension: ");
		result.append(extension);
		result.append(", baseEntities: ");
		result.append(baseEntities);
		result.append(')');
		return result.toString();
	}

} // EntityClassDefinition
