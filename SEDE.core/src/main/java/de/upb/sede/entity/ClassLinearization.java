package de.upb.sede.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import de.upb.sede.dsl.SecoUtil;
import de.upb.sede.dsl.seco.*;
import de.upb.sede.util.DefaultMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.upb.sede.dsl.seco.SecoFactory.eINSTANCE;

/**
 * Encapsulates entities and offers viewers onto them.
 * This class is thread-safe.
 * 
 * @author aminfaez
 *
 */
public final class ClassLinearization implements Serializable {

	private final static Logger logger = LoggerFactory.getLogger(ClassLinearization.class);

	/**
	 * If true, EntityResolver merges incoming definitions of entities into a single one. 
	 * When false, all refinements are kept separate from definitions. <\n>
	 * 
	 * By contract, the state of this flag will not change the functionality of viewers.
	 * It only determines how entities are kept and serialized. <\n>
	 * 
	 * It is set to true, by default
	 */
	private final boolean mergeRefinements;
	
	/**
	 * Maps entity names their entity definitions.
	 */
	private final Map<String, Entries> entityMap = new HashMap<>();
	
	/**
	 * Resolved entity cache:
	 */
	private final Map<String, ClassView> cache = new HashMap<>();


	/*

	 * Finds path to cast between entities.
	 */
	private final EntityClassCastGraph castGraph = new EntityClassCastGraph();


	/**
	 * Copy constructor.
	 * @param original the entries of this class will be copied in the newly created object.
	 */
	public ClassLinearization(ClassLinearization original) {
		this(original.mergeRefinements);
		original.entries().getEntities().stream().forEach(this::add);
	}

	/**
	 * Creates an empty resolver.
	 * 
	 */
	public ClassLinearization() {
		this(false);
	}
	
	/**
	 * 
	 * Creates an empty resolver.
	 * 
	 * 
	 * @param mergeRefinements If true, EntityResolver merges definitions of entities. 
	 */
	public ClassLinearization( boolean mergeRefinements) {
		this.mergeRefinements = mergeRefinements;
	}
	
	public void add(EntityClassDefinition...entries) {
		this.add(Arrays.asList(entries));
	}
	
	/**
	 * 
	 * Adds the given entity definitions from the given Entries object to this resolver.
	 * 
	 * @param entries entity definitions will be added to this resolver.
	 */
	public void add(Entries entries) {

		Objects.requireNonNull(entries, "Cannot put null value into resolver");
		if(entries.getEntities() == null) { 
			throw new IllegalArgumentException("Cannot put entries with an unassigned entities field into resolver.");
		}
		
		this.add(entries.getEntities());
	}
	
	/**
	 * Adds the given collection of entity definitions to this resolver.
	 * @param entries new entity definitions
	 */
	public void add(Collection<EntityClassDefinition> entries) {
		
		Objects.requireNonNull(entries, "Cannot put null value into resolver");
		
		for(EntityClassDefinition entityDefinition : entries) {
			add(entityDefinition);
		}
	}
	
	/**
	 * Adds the given entity definition to this resolver.
	 * @param entity new entity definition
	 */
	public synchronized void add(EntityClassDefinition entity) {
		Objects.requireNonNull(entity, "Cannot put null value into resolver");
		clearCache();
		String entityName = entity.getQualifiedName();
		Entries entries = entityMap.get(entityName);
		if(entries == null) {
			entries = eINSTANCE.createEntries();
			entityMap.put(entityName, entries);
		}
		entries.getEntities().add(EcoreUtil.copy(entity));
		if(this.mergeRefinements) {
			/*
			 * Merge the new entity with the old one to keep a single definition per entity.
			 */
			EntityClassDefinition mergedEntityDef = mergeEntries(entries);
			entries = eINSTANCE.createEntries();
			entries.getEntities().add(mergedEntityDef);
			entityMap.put(entityName, entries);
		}
	}


	public EntityClassCastGraph entityCast() {
		return castGraph;
	}


	private synchronized void clearCache() {
		synchronized(cache) {
			if(!cache.isEmpty()) {
				cache.clear();
			}
		}
		castGraph.clearCache();
	}
	
	/**
	 * Returns collection of all entities that were added to this resolver and thus are known by this resolver. 
	 * 
	 * @return collection of all added entities.
	 */
	public Collection<String> knownEntities() {
		return entityMap.keySet();
	}
	
	/**
	 * Returns true if an entity with the given name has been added to this resolver.
	 * @param entityName name of an entity 
	 * @return true if the given entity name corresponds to a definition that was added.
	 */
	public boolean isKnown(String entityName) {
		
		Objects.requireNonNull(entityName, "entityName was null");
		
		return knownEntities().contains(entityName);
	}
	
	/**
	 * Resolves the entity with the given name and returns a view on it.
	 * @param entityName name of the entity to be resolved
	 * @return view on the resolved entity
	 */
	public ClassView classView(String entityName) {
		
		Objects.requireNonNull(entityName, "entityName was null");
		
		if(isKnown(entityName)) {
			return linearizeClass(entityName, new HashSet<String>());
		} else {
			throw new IllegalArgumentException("Entity was not known. Given name was '" + entityName + "'.");
		}
	}


	/**
	 * Recursivelty linearizes class definitions of entity.
	 */
	private ClassView linearizeClass(String entityName, Set<String> hierarchyCache) {
		/*
		 * Shield against re-entrance of a previous argument to prevent infinitely looping recursions. 
		 */
		if(hierarchyCache.contains(entityName)) {
			throw new IllegalStateException("Presence of a cyclic class hierarchy. Hierarchy cache was: " + hierarchyCache.toString() + ", Reentered entity was: " + entityName);
		}
		
		synchronized(cache) {
			if(cache.containsKey(entityName)) {
				return cache.get(entityName);
			} 
		}
		
		/*
		 * Add this entity to the cache in order to detect hierarchy cycles.
		 */
		hierarchyCache.add(entityName);

		Entries entries = entityMap.get(entityName);
		EntityClassDefinition def = mergeEntries(entries);
		Optional<ClassView> wrappedEntity;
		if(def.isWrapper()) {
			wrappedEntity = Optional.of(linearizeClass(def.getWrappedEntity(), hierarchyCache));
		} else {
			wrappedEntity = Optional.empty();
		}
		List<ClassView> parents = new ArrayList<>();
		for(String baseEntityName : def.getBaseEntities()) {
			ClassView parentView = linearizeClass(baseEntityName, hierarchyCache);
			parents.add(parentView);
		}
		LinkedClassView classView = new LinkedClassView(def, wrappedEntity, parents);
		
		hierarchyCache.remove(entityName);
		synchronized(cache) {
			if(! cache.containsKey(entityName)) {
				cache.put(entityName, classView);
			}
		}
		return classView;
	}
	
	/**
	 * Merges the partial definitions. 
	 * Delegates to mergeEntries(Collection).
	 * @param entries partial entity definition. 
	 * @return One merged entity definition.
	 */
	private EntityClassDefinition mergeEntries(Entries entries) {
		return mergeEntries(entries.getEntities());
	}
	
	/**
	 * Merges the given list of partial definitions into one. 
	 * 
	 * @param partialDefinitions list of parial entity defitions.
	 * @return merged entity.
	 */
	private EntityClassDefinition mergeEntries(List<EntityClassDefinition> partialDefinitions) {
		if(partialDefinitions == null  || partialDefinitions.isEmpty()) {
			throw new IllegalArgumentException("Argument 'partialDefinitions' must contain at least one element.");
		}
		if(partialDefinitions.size() == 1) {
			/*
			 * Only one definition, so there is nothing to merge...
			 */
			return partialDefinitions.get(0);
		}
			
		String entityName = partialDefinitions.get(0).getQualifiedName();
		
		/*
		 * Make sure every given definition has the same entity name:
		 */
		for(EntityClassDefinition partial : partialDefinitions) {
			if(! entityName.equals(partial.getQualifiedName())) {
				throw new IllegalArgumentException("Cannot merge different entities (with different names) into one. Was: "
						+ entityName + ",  " + partial.getQualifiedName());
			}
		}		
		/*
		 * This will be the final resulting entity which is the merge result of all given partial entityDefinitions. 
		 */
		EntityClassDefinition mergedDefinition = new EntityClassDefinition();
		/*
		 * Set entity name.
		 */
		mergedDefinition.setQualifiedName(entityName);
		
		boolean wrapperAttrSet = false;
		Set<String> parentCache = new HashSet<>();
		for(EntityClassDefinition partial : partialDefinitions) {
			if(!wrapperAttrSet && partial.isWrapper()) {
				/*
				 * Set the first wrapper defined.
				 */
				wrapperAttrSet = true;
				mergedDefinition.setWrapper(true);
				mergedDefinition.setWrappedEntity(partial.getWrappedEntity());
			}
			for(String parentEntity : partial.getBaseEntities()) {
				if(parentCache.contains(parentEntity)) {
					/*
					 * Skip already added parents.
					 */
					continue;
				} else {
					parentCache.add(parentEntity);
					mergedDefinition.getBaseEntities().add(parentEntity);
				}
			}
			for(EntityMethod newMethod : EcoreUtil.copyAll(partial.getMethods())) {
				mergedDefinition.getMethods().add(newMethod);
			}
			for(EntityCast newCast :  EcoreUtil.copyAll(partial.getCasts())) {
				mergedDefinition.getCasts().add(newCast);
			}
		}
		return mergedDefinition;
	}

	/**
	 * Resolved a given operation like: "fruits.Apple::__construct({i1=0,i2=0,i3=10,i4=10})"
	 *
	 * @param operation
	 * @param typeLookup
	 * @return
	 */
	public Optional<OperationResolution> resolveOperation(Operation operation, FieldLookup<String> typeLookup) {
		/*
		 * Resolve operation context entity:
		 */
		String contextEntityName;
		if(operation.getContextField() != null) {
			if(operation.getContextField().isDereference()) {
				// TODO - add support for dereference of fields
				throw new OperationResolutionException(operation, "Context field dereference not yet supported.");
			}
			Field field = operation.getContextField();
			Optional<String> fieldType = typeLookup.readLatest(field);
			if(fieldType.isPresent()) {
				contextEntityName = fieldType.get();
			} else {
				throw new OperationResolutionException(operation, "Context field '" + field.getName() + "' type lookup failed.");
			}
		} else {
			contextEntityName = operation.getEntityName();
		}
		ClassView contextEntity;
		try{
			contextEntity = classView(contextEntityName);
		} catch(IllegalArgumentException ex) {
			throw new OperationResolutionException(operation, "Couldn't resolve context entity type.", ex);
		}

		List<MethodView> methodViews = contextEntity.allMethodsWithName(operation.getMethod());

		/*
		 * prepare arguments:
		 * Order by index if all arguments are indexed: e.g.: (i2=2, i1="hello") -> ("hello", 2)
		 *
		 */
		boolean indexed = false;
		for(int argIndex = 0; argIndex < operation.getArgs().size(); argIndex ++) {
			Argument argument = operation.getArgs().get(argIndex);

			if(argument.isIndexed() && argIndex == 0) {
				indexed = true;
			} else if (argIndex > 0) {
				/*
				 * Make sure every argument is indexed:
				 */
				if ((argument.isIndexed() && !indexed) || (!argument.isIndexed() && indexed)) {
					throw new OperationResolutionException(operation, "Arguments are only partially indexed. " +
							"Make sure either all arguments are indexed or order them manually.");
				}
			}
			if(argument.getParameterName()!= null) {
				/*
				 * TODO - Add support for named arguments
			 	 */
				throw new OperationResolutionException(operation, "Has named arguments. This language feature is not yet supported.");
			}


			FieldValue argValue = argument.getValue();
			if(argValue.getOperation()!=null) {
				// TODO add support for argument operation
				throw new OperationResolutionException(operation, "Arguments are inner operations. This language feature is not yet supported.");
			}
			else if(argValue.getCastTarget()!=null) {
				// TODO add support for argument cast
				throw new OperationResolutionException(operation, "Arguments have explicit cast. This language feature is not yet supported.");
			}

		}
		List<Argument> orderedArgs = new ArrayList<>(operation.getArgs());
		if(indexed) {
			/*
			 * Sort by argument indexes
			 */
			orderedArgs.sort(Comparator.comparingInt(Argument::getIndex));
		}

		/*
		 * Infer argument types and values:
		 */

		List<String> argTypes = new ArrayList<>();
		List<String> argValues = new ArrayList<>();
		for(Argument arg : orderedArgs) {
			FieldValue argValue = arg.getValue();
			if(argValue == null) {
				throw new OperationResolutionException(operation, "Has void argument values: " + arg.toString());
			}
			else if(argValue.getBool() != null) {
				argTypes.add(BuiltinEntities.ENTITY_BOOL);
				argValues.add(argValue.getBool());
			}
			else if(argValue.getString() != null) {
				argTypes.add(BuiltinEntities.ENTITY_STRING);
				argValues.add(argValue.getString());
			}
			else if(argValue.getNumber() != null) {
				argTypes.add(BuiltinEntities.ENTITY_NUMBER);
				argValues.add(argValue.getNumber());
			}
			else if(argValue.isNull()) {
				argTypes.add(BuiltinEntities.ENTITY_NULL);
				argValues.add(null);
			}
			else if(argValue.getField() != null) {
				Field field = argValue.getField();
//				if(field.isDereference()) {
//					throw new OperationResolutionException(operation, "Cannot dereference argument: " + arg);
//				}
				Optional<String> fieldTypeOpt = typeLookup.readLatest(field);
				if( ! fieldTypeOpt.isPresent()) {
					throw new OperationResolutionException(operation, "Couldnt look up argument type of field: " + arg);
				}
				String fieldType = fieldTypeOpt.get();
				argTypes.add(fieldType);
				argValues.add(field.getName());
			}
		}

		/*
		 *
		 * Illustration of collected data for the following operation example:
		 * 		"fruits.Apple::__construct({i1=0,i2=0,i3=10,i4=10})":
		 */
		assert contextEntity != null; // this is the context type: "fruits.Apple"
		assert methodViews != null; // this is a list of all method with the name: "__construct"
		assert argTypes != null; // this is the list of argument types: "[Number, Number, Number, Number]

		/*
		 * Find method that matches the operation:
		 */
		for(MethodView methodCandidate : methodViews) {
			if(methodCandidate.requiredParams().size() != argTypes.size()) {
				/*
				 * Ignore methods with an unequal amount of arguments.
				 */
				continue;
			}
			/*
			 * Find cast paths between supplied argument types and required parameter types.
			 */
			OperationResolution resolution = new OperationResolution(operation, methodCandidate);
			Iterator<String> suppliedTypeIterator = argTypes.iterator();
			boolean methodSignatureMatches = true; // assume the signature matches until an argument cannot be casted to the required parameter.
			for(EntityMethodParam requiredParam : methodCandidate.requiredParams()) {
				assert !requiredParam.isValueFixed() : "BUG in MethodView::requiredParams, " +
						"fixed parameter slipped through: " + requiredParam.toString();
				String suppliedType = suppliedTypeIterator.next();
				String requiredType = requiredParam.getParameterType();
				List<ClassCastPath> castPath = castGraph.querry(suppliedType, requiredParam.getParameterType());
				if(castPath.size() == 0) {
					// cannot cast between suppliedType and requiredType
					logger.debug("Couldn't cast from suppliedtype '{}' to requiredtype '{}' " +
							"in operation '{}'.", suppliedType, requiredType, operation);
					methodSignatureMatches = false;
					break;
				}
				resolution.addArgCast(castPath);
			}
			if(methodSignatureMatches) {
				return Optional.of(resolution);
			}
		}

		return Optional.empty();
	}

	public synchronized Entries entries() {
		Collection<Entries> fragments = EcoreUtil.copyAll(entityMap.values());
		Entries entries = new Entries();
		fragments.stream().forEach(fragment -> entries.getEntities().addAll(fragment.getEntities()));
		return entries;
	}



	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException
	{
		boolean mergeFlag = in.readBoolean();
		String entriesSecoRepresentation = in.readUTF();
		Entries entries = SecoUtil.parseSources(entriesSecoRepresentation);

		/*
		 * Use reflection to set final fields:
		 */

		try {
			java.lang.reflect.Field cache = ClassLinearization.class.getField("cache");
			cache.setAccessible(true);
			cache.set(this, new HashMap<>());

			java.lang.reflect.Field castGraph = ClassLinearization.class.getField("castGraph");
			castGraph.setAccessible(true);
			castGraph.set(this, new EntityClassCastGraph());

			java.lang.reflect.Field entityMap = ClassLinearization.class.getField("entityMap");
			entityMap.setAccessible(true);
			entityMap.set(this, new HashMap<>());


			java.lang.reflect.Field mergeRefinements = ClassLinearization.class.getField("mergeRefinements");
			mergeRefinements.setAccessible(true);
			mergeRefinements.set(this, mergeFlag);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		/*
		 * Now that the fields of this object are not null anymore, add the parsed entries:
		 */
		this.add(entries);
	}

	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeBoolean(mergeRefinements);
		out.writeUTF(entries().toString());
	}
	

	public class LinkedClassView implements ClassView {

		private final EntityClassDefinition def;
		private final ClassView wrapped;
		private final List<ClassView> parents;
		private final Map<String, List<MethodView>> methodCache = new HashMap<>();
		
		LinkedClassView(EntityClassDefinition definition, Optional<ClassView> wrappedEntity, List<ClassView> parentEntities) {
			if(definition.isWrapper() && !wrappedEntity.isPresent()) {
				throw new IllegalArgumentException("Definition says entity is a wrapper. However no wrappedEntity was supplied.");
			} else if (!definition.isWrapper() && wrappedEntity.isPresent()){
				throw new IllegalArgumentException("Definition says entity is not a wrapper. However a wrappedEntity was supplied.");
			}
			
			this.def = definition;
			this.wrapped = wrappedEntity.orElse(null);
			this.parents = parentEntities;
		}



		public boolean isWrapper() {
			return def.isWrapper();
		}
		
		@Override
		public String entityName() {
			return def.getQualifiedName();
		}
		
		@Override
		public String targetEntityName() {
			if(isWrapper()) {
				return wrapped.targetEntityName();
			} else {
				return entityName();
			}
		}
		
		@Override
		public List<EntityCast> declaredCasts() {
			List<EntityCast> casts = new ArrayList<>();
			/*
			 * Cast to iteself
			 */
			EntityCast identityCast = new EntityCast();
			identityCast.setResultingEntity(this.entityName());
			identityCast.setAdditionalData("{\"implicit\":True}");
			casts.add(identityCast);

			/*
			 * All declared casts:
			 */
			casts.addAll(def.getCasts());

			/*
			 * Cast to all declared parents:
			 */
			for(ClassView declaredParent : declaredParents()){
				identityCast = new EntityCast();
				identityCast.setResultingEntity(declaredParent.entityName());
				identityCast.setAdditionalData("{\"implicit\":True}");
				casts.add(identityCast);
			}

			return casts;
		}

		@Override
		public List<ClassView> declaredParents() {
			return Collections.unmodifiableList(parents);
		}

		@Override
		public List<ClassView> allParents() {
			List<ClassView> accumulatedParents = new ArrayList<>();
			List<ClassView> parentsInQueue = new ArrayList<>(declaredParents());
			while(!parentsInQueue.isEmpty()) {
				ClassView currentParent = parentsInQueue.remove(0);
				if(!accumulatedParents.contains(currentParent)) {
					accumulatedParents.add(currentParent);
					parentsInQueue.addAll(currentParent.declaredParents());
				}
			}
			return accumulatedParents;
		}

		@Override
		/**
		 * Returns true if this entity is of the given type. The given type can also be the wrapped entity or a super class. 
		 * @param entity
		 * @return
		 */
		public boolean is(String entityType) {
			Objects.requireNonNull(entityType);
			if(entityName().equals(entityType)) {
				return true;
			}
			for(ClassView parent : parents) {
				if(parent.is(entityType)) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public synchronized List<MethodView> allMethodsWithName(String methodName) {
			if(methodCache.containsKey(methodName)) {
				return methodCache.get(methodName);
			}
			Objects.requireNonNull(methodName);
			List<MethodView> methods = new ArrayList<>();
			for(ClassView parent : parents) {
				methods.addAll(parent.allMethodsWithName(methodName));
			}
			for(EntityMethod servedMethod : def.getMethods()) {
				if(servedMethod.getMethodName().equals(methodName)) {

					methods.add(new NestedMethodView(servedMethod));
				}
			}
			if(isWrapper()) {
				methods.addAll(wrapped.allMethodsWithName(methodName));
			}

			methodCache.put(methodName, methods);
			return methods;
		}

		/**
		 * Resolves m
		 * @param requestedMethod
		 * @return
		 */
		@Override
		@Deprecated
		public Optional<MethodView> resolveMethod(EntityMethod requestedMethod) {
			for(EntityMethod method : def.getMethods()) {
				NestedMethodView nestedMethodView = new NestedMethodView(method);
				if(nestedMethodView.matchesSignature(requestedMethod)) {
					return Optional.of(nestedMethodView);
				}
			}
			if(isWrapper()) {
				Optional<MethodView> methodResolution = wrapped.resolveMethod(requestedMethod);
				if(methodResolution.isPresent()) {
					return methodResolution;
				}
			}
			for(ClassView parent : parents) {
				Optional<MethodView> methodResolution = parent.resolveMethod(requestedMethod);
				if(methodResolution.isPresent()) {
					return methodResolution;
				}
			}
			return Optional.empty();
 		}

		@Override
		public String toString() {
			return "LinkedClassView{" +
						entityName() +
					'}';
		}

		public class NestedMethodView implements MethodView {
			
			private final EntityMethod method;
			
			NestedMethodView(EntityMethod method) {
				this.method = method;
				if(method.getParamSignature() == null) {
					method.setParamSignature(new EntityMethodParamSignature());
				}
			}
			
			int paramCount(boolean input) {
				List<EntityMethodParam> parameters = input ? method.getParamSignature().getParameters() : method.getParamSignature().getOutputs();
				int paramCount = 0 ;
				for(EntityMethodParam parameter : parameters) {
					if(parameter.isValueFixed()) {
						continue; // ignore fixed value
					}
					paramCount ++;
				}
				return paramCount;
			}
			
			String getParamType(int paramIndex, boolean input) {
				if(paramIndex < 0 || paramIndex >= paramCount(input)) {
					throw new IllegalArgumentException("Parameter index '" + paramIndex + "' is out of bound.");
				}
				List<EntityMethodParam> parameters = input ? method.getParamSignature().getParameters() : method.getParamSignature().getOutputs();
				for(EntityMethodParam parameter : parameters) {
					if(parameter.isValueFixed()) {
						continue; // ignore fixed value
					}
					if(paramIndex == 0) {
						return parameter.getParameterType();
					} else {
						paramIndex--;
					}
				}
				{
					throw new IllegalStateException("This line cannot be reached. BUG in MethodResolution::getInputParameterType(int)");
				}
			}
			
			

			public boolean matchesSignature(EntityMethod requestedMethod) {
				Objects.requireNonNull(requestedMethod);
				if(!requestedMethod.getMethodName().equals(method.getMethodName())) {
					return false;
				}
				
				EntityMethodParamSignature requestedSignature = requestedMethod.getParamSignature();
				if(requestedMethod.getParamSignature().getParameters().size() != method.getParamSignature().getParameters().size()) {
					return false;
				}
				for(int parameterIndex = 0; parameterIndex < requestedSignature.getParameters().size(); parameterIndex++) {
					String declaredType = getParamType(parameterIndex, true); 
					String requestedType = requestedSignature.getParameters().get(parameterIndex).getParameterType();
					if(!declaredType.equals(requestedType)) {
						ClassView requestedTypeView = classView(requestedType);
						if(!requestedTypeView.is(declaredType)) {
							return false;
						}
					}
				}

				if(requestedMethod.getParamSignature().getOutputs().size() > method.getParamSignature().getOutputs().size()) {
					return false;
				}

				for(int outputIndex = 0; outputIndex < requestedSignature.getOutputs().size(); outputIndex ++) {
					String declaredType = getParamType(outputIndex, false); 
					String requestedType = requestedSignature.getOutputs().get(outputIndex).getParameterType();
					if(!declaredType.equals(requestedType)) {
						ClassView requestedTypeView = classView(requestedType);
						if(!requestedTypeView.is(declaredType)) {
							return false;
						}
					}
				}
				return true;
			}

			@Override
			public boolean isPure() {
				return method.getProperty() == EntityMethodProp.STATIC || method.getProperty() == EntityMethodProp.PURE;
			}

			@Override
			public boolean isStatic() {
				return method.getProperty() == EntityMethodProp.STATIC;
			}

			@Override
			public List<EntityMethodParam> outputParams() {
				return method.getParamSignature().getOutputs();
			}

			@Override
			public List<EntityMethodParam> inputParams() {
				return method.getParamSignature().getParameters();
			}

			@Override
			public List<EntityMethodParam> requiredParams() {
				return method.getParamSignature().getParameters()
						.stream().filter(param -> ! param.isValueFixed()) // filter out unfixed values
						.collect(Collectors.toList());
			}

			public EntityMethod getMethod() {
				EntityMethod method = EcoreUtil.copy(this.method);
				return method;
			}
			public String toString(){
				return method.toString();
			}

			public Optional<MethodView> getRealization() {
				if(method.isRealization()) {
					EntityMethod realisedMethod = getMethod();
					realisedMethod.setMethodName(method.getMethodRealization());
					Optional<MethodView> methodView = resolveMethod(realisedMethod);
					if(methodView.isPresent()) {
						return methodView;
					} else{
						/*
						 * Method realization is defined to be realised but its realised method cannot be resolved.
						 * -> Illegal class state:
						 */
						throw new IllegalStateException("Illegal class definition: Method " + toString()  +
								" is said to be realised by: " + method.getMethodRealization() +
								" but the realized method cannot be resolved.");
					}
				} else {
					return Optional.empty();
				}
			}
		}
	}
	
	class EntityClassCastGraph {
		
		private final CastTupelCache cache = new CastTupelCache();

		/*
		 * Any number of queries can read the cache in parallel.
		 * Clear operation must have exclusive access to cache.
		 *
		 * (See: The little book of semaphores, Allen B. Downey  -> Readers-Writers Problem)
		 */
		private final Lock 	queryLock = new ReentrantLock(),
							clearLock = new ReentrantLock();

		private final AtomicInteger concurrentQuerries = new AtomicInteger(0);

		List<ClassCastPath> querry(String startEntity, String goalEntity) {
			queryLock.lock();
			if( concurrentQuerries.incrementAndGet() == 1 ){
				// the first query locks the reset lock
				clearLock.lock();
			}
			queryLock.unlock();
			// critical section
			List<ClassCastPath> castPaths = dfs(startEntity, goalEntity);

			if (concurrentQuerries.decrementAndGet() == 0) {
				// last query is done, reset can happen now
				clearLock.unlock();
			}
			return castPaths;
		}

		public void clearCache() {
			queryLock.lock(); // lock the query lock to prevent starvation of the clear operation.
			clearLock.lock();
			// critical section: No one is querying:
			cache.clear();
			clearLock.unlock();
			queryLock.unlock();
		}


		/**
		 *
		 * @deprecated Deprecated used to prevent use inside this file. This method shall only be called by query(String, String)
		 */
		@SuppressWarnings("DeprecatedIsStillUsed")
		@Deprecated
		 private List<ClassCastPath> dfs(String start, String goal) {
			Objects.requireNonNull(start);
			Objects.requireNonNull(goal);
			if(start.equals(goal)) {
				return Collections.singletonList(new ClassCastPath(start));
			}

			CastTuple querry = new CastTuple(start, goal);
			/*
			 * First look into the cache for the cast:
			 */
			Optional<List<ClassCastPath>> cachedEntry = cache.get(querry);
			if(cachedEntry.isPresent()) {
				return cachedEntry.get();
			}
			/*
			 * Perform depth first search from the source entity. 
			 * This operation fills the cache. 
			 */
			dfs(new ClassCastPath(start));
			
			cachedEntry = cache.get(querry);
			if(!cachedEntry.isPresent()) {
				/*
				 * Cache the information that there is no path start -> goal:
				 */
				cache.put(querry, Optional.empty());
				return Collections.emptyList();
			}
			else {
				return cachedEntry.get();
			}
		}
		
		private void dfs(ClassCastPath path) {
			/*
			 * Find all cast targets from the last node in the path:
			 */
			String currentEntity = path.last();
			ClassView entityClass = classView(currentEntity);
			List<EntityCast> declaredCasts = entityClass.declaredCasts();
			for(EntityCast cast : declaredCasts) {
				String target = cast.getResultingEntity();

//				boolean outGoingEdge = (cast.getDirection() != TransformDirection.FROM); -> NOT NEEDED ANYMORE, ALL PATHS ARE OUTGOING
				if(path.contains(target)) {
					/*
					 * Ignore cast edges that have targets already contained in the path:
					 */
					continue;
				}
				/*
				 * New path found:
				 */
				ClassCastPath newPath = path.addCast(cast);
				/*
				 * Add it into cache:
				 */
				putInCache(newPath);
				/*
				 * Perform recursive depth first search:
				 */
				dfs(newPath);
			}
		}
		
		private void putInCache(ClassCastPath cp) {
			logger.debug("Caching cast path:\n\t" + cp.toString());
			String castInput = cp.head();
			String castOutput = cp.last();

			CastTuple tuple = new CastTuple(castInput, castOutput);
			cache.put(tuple, Optional.of(cp));
		}

		/**
		 * This class is only used as the index for the cache of CastGraph.
		 */
		private class CastTuple {
			final String from, to;
			CastTuple(String from , String to) {
				this.from = from;
				this.to = to;
			}
			
			@Override
			public int hashCode() {
				return Objects.hash(from, to);
			}
			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				CastTuple other = (CastTuple) obj;
				return Objects.equals(from, other.from) && Objects.equals(to, other.to);
			}

			@Override
			public String toString() {
				return "CastTuple{" +
						"from='" + from + '\'' +
						", to='" + to + '\'' +
						'}';
			}
		}

		private class CastTupelCache extends HashMap<String, Map<String,  Optional<List<ClassCastPath>>>> {

			synchronized void put(CastTuple cast, Optional<ClassCastPath> newPath) {
				if(!this.containsKey(cast.from)) {
					Map<String, Optional<List<ClassCastPath>>>  castPaths = new HashMap<>();
					put(cast.from, castPaths);
				}

				Map<String, Optional<List<ClassCastPath>>> allCastTargets = get(cast.from);


				if(!newPath.isPresent()) {
					allCastTargets.put(cast.to, Optional.empty());
				} else {
					Optional<List<ClassCastPath>> castPaths = allCastTargets.get(cast.to);
					if(castPaths == null) {
						castPaths = Optional.of(new ArrayList<>());
						allCastTargets.put(cast.to, castPaths);
					}
					if(!castPaths.isPresent()) {
						throw new IllegalStateException("BUG: the cache in the cast graph is in an illegal state. "
								+ "cache says there is no path between " + newPath.get().head() + " -> " + newPath.get().last() + ","
								+ " however such a path was just found.\n\n cache.toString() = " + cache.toString());
					}
					castPaths.get().add(newPath.get());
				}
			}

			synchronized  Optional<List<ClassCastPath>> get(CastTuple cast) {
				if(!this.containsKey(cast.from)) {
					return Optional.empty(); // empty to signal that a dfs wasn't done from cast.from
				} else {
					Map<String, Optional<List<ClassCastPath>>> allCastTargets = get(cast.from);
					Optional<List<ClassCastPath>> classPaths  = allCastTargets.get(cast.to);
					if(classPaths == null || !classPaths.isPresent()) {
						return Optional.of(Collections.emptyList());
					} else {
						return classPaths;
					}
				}
			}

			public synchronized void clear(){
				super.clear();
			}

		}
	}
}

