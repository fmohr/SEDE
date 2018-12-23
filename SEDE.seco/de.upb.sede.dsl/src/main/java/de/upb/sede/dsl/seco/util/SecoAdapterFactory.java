/**
 * 2.15.0
 */
package de.upb.sede.dsl.seco.util;

import de.upb.sede.dsl.seco.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see de.upb.sede.dsl.seco.SecoPackage
 * @generated
 */
public class SecoAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SecoPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public SecoAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SecoPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SecoSwitch<Adapter> modelSwitch = new SecoSwitch<Adapter>() {
		@Override
		public Adapter caseEntries(Entries object) {
			return createEntriesAdapter();
		}

		@Override
		public Adapter caseOperation(Operation object) {
			return createOperationAdapter();
		}

		@Override
		public Adapter caseArgument(Argument object) {
			return createArgumentAdapter();
		}

		@Override
		public Adapter caseAssignment(Assignment object) {
			return createAssignmentAdapter();
		}

		@Override
		public Adapter caseEntityClassDefinition(EntityClassDefinition object) {
			return createEntityClassDefinitionAdapter();
		}

		@Override
		public Adapter caseEntityMethod(EntityMethod object) {
			return createEntityMethodAdapter();
		}

		@Override
		public Adapter caseEntityMethodParamSignature(EntityMethodParamSignature object) {
			return createEntityMethodParamSignatureAdapter();
		}

		@Override
		public Adapter caseEntityMethodParam(EntityMethodParam object) {
			return createEntityMethodParamAdapter();
		}

		@Override
		public Adapter caseEntityCast(EntityCast object) {
			return createEntityCastAdapter();
		}

		@Override
		public Adapter caseYield(Yield object) {
			return createYieldAdapter();
		}

		@Override
		public Adapter caseField(Field object) {
			return createFieldAdapter();
		}

		@Override
		public Adapter caseFieldValue(FieldValue object) {
			return createFieldValueAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Entries <em>Entries</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Entries
	 * @generated
	 */
	public Adapter createEntriesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Operation <em>Operation</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Operation
	 * @generated
	 */
	public Adapter createOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Argument <em>Argument</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Argument
	 * @generated
	 */
	public Adapter createArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Assignment <em>Assignment</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Assignment
	 * @generated
	 */
	public Adapter createAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.EntityClassDefinition <em>Entity Class
	 * Definition</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.EntityClassDefinition
	 * @generated
	 */
	public Adapter createEntityClassDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.EntityMethod <em>Entity Method</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.EntityMethod
	 * @generated
	 */
	public Adapter createEntityMethodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParamSignature <em>Entity Method
	 * Param Signature</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.EntityMethodParamSignature
	 * @generated
	 */
	public Adapter createEntityMethodParamSignatureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.EntityMethodParam <em>Entity Method
	 * Param</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.EntityMethodParam
	 * @generated
	 */
	public Adapter createEntityMethodParamAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.EntityCast <em>Entity Cast</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.EntityCast
	 * @generated
	 */
	public Adapter createEntityCastAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Yield <em>Yield</em>}'. <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Yield
	 * @generated
	 */
	public Adapter createYieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.Field <em>Field</em>}'. <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.Field
	 * @generated
	 */
	public Adapter createFieldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link de.upb.sede.dsl.seco.FieldValue <em>Field Value</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch
	 * all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see de.upb.sede.dsl.seco.FieldValue
	 * @generated
	 */
	public Adapter createFieldValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // SecoAdapterFactory
