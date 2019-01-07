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
 * Class Definition</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName
 * <em>Qualified Name</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper
 * <em>Wrapper</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity
 * <em>Wrapped Entity</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#isExtension
 * <em>Extension</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getBaseEntities
 * <em>Base Entities</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getMethods
 * <em>Methods</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getCasts
 * <em>Casts</em>}</li>
 * <li>{@link de.upb.sede.dsl.seco.EntityClassDefinition#getRuntimeInfo
 * <em>Runtime Info</em>}</li>
 * </ul>
 *
 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition()
 * @model kind="class"
 * @generated
 */
public class EntityClassDefinition extends SecoObject implements EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EntityClassDefinition() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecoPackage.Literals.ENTITY_CLASS_DEFINITION;
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
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_QualifiedName()
	 * @model
	 * @generated
	 */
	public String getQualifiedName() {
		return (String) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getQualifiedName
	 * <em>Qualified Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	public void setQualifiedName(String newQualifiedName) {
		eSet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__QUALIFIED_NAME, newQualifiedName);
	}

	/**
	 * Returns the value of the '<em><b>Wrapper</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wrapper</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wrapper</em>' attribute.
	 * @see #setWrapper(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Wrapper()
	 * @model
	 * @generated
	 */
	public boolean isWrapper() {
		return (Boolean) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__WRAPPER, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isWrapper
	 * <em>Wrapper</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Wrapper</em>' attribute.
	 * @see #isWrapper()
	 * @generated
	 */
	public void setWrapper(boolean newWrapper) {
		eSet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__WRAPPER, newWrapper);
	}

	/**
	 * Returns the value of the '<em><b>Wrapped Entity</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wrapped Entity</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wrapped Entity</em>' attribute.
	 * @see #setWrappedEntity(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_WrappedEntity()
	 * @model
	 * @generated
	 */
	public String getWrappedEntity() {
		return (String) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getWrappedEntity
	 * <em>Wrapped Entity</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Wrapped Entity</em>' attribute.
	 * @see #getWrappedEntity()
	 * @generated
	 */
	public void setWrappedEntity(String newWrappedEntity) {
		eSet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__WRAPPED_ENTITY, newWrappedEntity);
	}

	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see #setExtension(boolean)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Extension()
	 * @model
	 * @generated
	 */
	public boolean isExtension() {
		return (Boolean) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__EXTENSION, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#isExtension
	 * <em>Extension</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see #isExtension()
	 * @generated
	 */
	public void setExtension(boolean newExtension) {
		eSet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__EXTENSION, newExtension);
	}

	/**
	 * Returns the value of the '<em><b>Base Entities</b></em>' attribute list. The
	 * list contents are of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Entities</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Base Entities</em>' attribute list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_BaseEntities()
	 * @model unique="false"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBaseEntities() {
		return (List<String>) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__BASE_ENTITIES, true);
	}

	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link de.upb.sede.dsl.seco.EntityMethod}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Methods()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<EntityMethod> getMethods() {
		return (List<EntityMethod>) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__METHODS, true);
	}

	/**
	 * Returns the value of the '<em><b>Casts</b></em>' containment reference list.
	 * The list contents are of type {@link de.upb.sede.dsl.seco.EntityCast}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Casts</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Casts</em>' containment reference list.
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_Casts()
	 * @model containment="true"
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<EntityCast> getCasts() {
		return (List<EntityCast>) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__CASTS, true);
	}

	/**
	 * Returns the value of the '<em><b>Runtime Info</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Info</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Runtime Info</em>' attribute.
	 * @see #setRuntimeInfo(String)
	 * @see de.upb.sede.dsl.seco.SecoPackage#getEntityClassDefinition_RuntimeInfo()
	 * @model
	 * @generated
	 */
	public String getRuntimeInfo() {
		return (String) eGet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__RUNTIME_INFO, true);
	}

	/**
	 * Sets the value of the
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition#getRuntimeInfo <em>Runtime
	 * Info</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Runtime Info</em>' attribute.
	 * @see #getRuntimeInfo()
	 * @generated
	 */
	public void setRuntimeInfo(String newRuntimeInfo) {
		eSet(SecoPackage.Literals.ENTITY_CLASS_DEFINITION__RUNTIME_INFO, newRuntimeInfo);
	}

} // EntityClassDefinition
