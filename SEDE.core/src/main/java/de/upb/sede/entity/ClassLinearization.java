package de.upb.sede.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import de.upb.sede.dsl.seco.EntityCast;
import de.upb.sede.dsl.seco.EntityClassDefinition;
import de.upb.sede.dsl.seco.EntityMethod;
import de.upb.sede.dsl.seco.EntityMethodParam;
import de.upb.sede.dsl.seco.EntityMethodParamSignature;
import de.upb.sede.dsl.seco.Entries;
import de.upb.sede.dsl.seco.TransformDirection;

import static de.upb.sede.dsl.seco.SecoFactory.eINSTANCE;
import static org.eclipse.emf.ecore.util.EcoreUtil.*;

/**
 * Encapsulates entities and offers viewers onto them.
 * 
 * @author aminfaez
 *
 */
public final class ClassLinearization {
	
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
	
	
	
	/**
	 * Creates an empty resolver.
	 * 
	 */
	public ClassLinearization(Entries entries) {
		this(entries, true);
	}
	
	/**
	 * 
	 * Creates an empty resolver.
	 * 
	 * 
	 * @param mergeRefinements If true, EntityResolver merges definitions of entities. 
	 */
	public ClassLinearization(Entries entries, boolean mergeRefinements) {

		Objects.requireNonNull(entries, "Cannot put null value into resolver");
		if(entries.getEntities() == null) { 
			throw new IllegalArgumentException("Cannot put entries with an unassigned entities field into resolver.");
		}
		
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
		synchronized(cache) {
			if(!cache.isEmpty()) {
				cache.clear();
			}
		}
		String entityName = entity.getQualifiedName();
		Entries entries = entityMap.get(entityName);
		if(entries == null) {
			entries = eINSTANCE.createEntries();
			entityMap.put(entityName, entries);
		}
		entries.getEntities().add(entity);
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
	public ClassView entityView(String entityName) {
		
		Objects.requireNonNull(entityName, "entityName was null");
		
		if(isKnown(entityName)) {
			return linearizeEntity(entityName, new HashSet<String>());
		} else {
			throw new IllegalArgumentException("Entity was not known. Given name was '" + entityName + "'.");
		}
	}
	
	
	
	private ClassView linearizeEntity(String entityName, Set<String> hierarchyCache) {
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
			wrappedEntity = Optional.of(linearizeEntity(entityName, hierarchyCache));
		} else {
			wrappedEntity = Optional.empty();
		}
		List<ClassView> parents = new ArrayList<>();
		for(String baseEntityName : def.getBaseEntities()) {
			ClassView parentView = linearizeEntity(baseEntityName, hierarchyCache);
			parents.add(parentView);
		}
		LinkedClassView classView = new LinkedClassView(def, wrappedEntity, parents);
		
		hierarchyCache.remove(entityName);
		synchronized(cache) {
			if(! cache.containsKey(entityName)) {
				cache.put(entityName, null /* TODO */);
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
			for(EntityMethod newMethod : partial.getMethods()) {
				mergedDefinition.getMethods().add(copy(newMethod));
			}
			for(EntityCast newCast : partial.getCasts()) {
				mergedDefinition.getCasts().add(copy(newCast));
			}
		}
		return mergedDefinition;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ClassLinearization clone() {
		return null; // TODO
	}
	

	public class LinkedClassView implements ClassView {
		private final EntityClassDefinition def;
		private final ClassView wrapped;
		private final List<ClassView> parents;
		
		LinkedClassView(EntityClassDefinition definition, Optional<ClassView> wrappedEntity, List<ClassView> parentEntities) {
			if(definition.isWrapper() && !wrappedEntity.isPresent()) {
				throw new IllegalArgumentException("Definition says entity is wrapper. However no wrappedEntity was supplied.");
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
		public List<EntityCast> getAllCasts() {
			List<EntityCast> casts = new ArrayList<>();
			casts.addAll(def.getCasts());
			if(isWrapper()) {
				casts.addAll(wrapped.getAllCasts());
			}
			for(ClassView parent : parents) {
				casts.addAll(parent.getAllCasts());
			}
			return casts;
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
			} else if(isWrapper() && wrapped.is(entityType)){
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
		public List<MethodView> allMethodsWithName(String methodName) {
			Objects.requireNonNull(methodName);
			List<MethodView> methods = new ArrayList<>();
			for(EntityMethod servedMethod : def.getMethods()) {
				if(servedMethod.getMethodName().equals(methodName)) {
					methods.add(new MethodResolution(servedMethod));
				}
			}
			if(isWrapper()) {
				methods.addAll(wrapped.allMethodsWithName(methodName));
			}
			for(ClassView parent : parents) {
				methods.addAll(parent.allMethodsWithName(methodName));
			}
			return methods;
		}
		
		@Override
		public Optional<MethodView> resolveMethod(EntityMethod requestedMethod) {
			for(EntityMethod method : def.getMethods()) {
				MethodResolution methodResolution = new MethodResolution(method);
				if(methodResolution.matchesSignature(requestedMethod)) {
					return Optional.of(methodResolution);
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
		
		public class MethodResolution implements MethodView {
			
			private final EntityMethod method;
			
			MethodResolution(EntityMethod method) {
				this.method = method;
			}
			
			public int paramCount(boolean input) {
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
			
			public String getParamType(int paramIndex, boolean input) {
				if(paramIndex < 0 || paramIndex > paramCount(input)) {
					throw new IllegalArgumentException("Parameter index '" + paramIndex + "' is out of bound.");
				}
				List<EntityMethodParam> parameters = input ? method.getParamSignature().getParameters() : method.getParamSignature().getOutputs();
				for(EntityMethodParam parameter : parameters) {
					if(parameter.isValueFixed()) {
						continue; // ignore fixed value
					}
					if(paramIndex == 0) {
						return parameter.getParameterName();
					} else {
						paramIndex--;
					}
				}
				{
					throw new IllegalStateException("This cannot be reached. BUG in MethodResolution::getInputParameterType(int)");
				}
			}
			
			
			/**
			 * Checks if the given method has a matching signature.
			 * In other words if true is returned, this method can be invoked with the given method signature (matching name, input and output types).
			 * @param requestedMethod requested method signature match
			 * @return true if the given method has a matching signature.
			 */
			public boolean matchesSignature(EntityMethod requestedMethod) {
				Objects.requireNonNull(requestedMethod);
				if(!requestedMethod.getMethodName().equals(method.getMethodName())) {
					return false;
				}
				
				EntityMethodParamSignature requestedSignature = requestedMethod.getParamSignature();
				
				for(int parameterIndex = 0; parameterIndex < requestedSignature.getParameters().size(); parameterIndex++) {
					String declaredType = getParamType(parameterIndex, true); 
					String requestedType = requestedSignature.getParameters().get(parameterIndex).getParameterType();
					ClassView requestedTypeView = entityView(requestedType);
					if(!requestedTypeView.is(declaredType)) {
						return false;
					}
				}
				for(int outputIndex = 0; outputIndex < requestedSignature.getOutputs().size(); outputIndex ++) {
					String declaredType = getParamType(outputIndex, false); 
					String requestedType = requestedSignature.getOutputs().get(outputIndex).getParameterType();
					ClassView requestedTypeView = entityView(requestedType);
					if(!requestedTypeView.is(declaredType)) {
						return false;
					}
				}
				
				return true;
			}
		}
	}
	
	class EntityClassCastGraph {
		
		private final Map<CastTupel, Optional<List<ClassCastPath>>> cache = new HashMap<>();
		
		List<ClassCastPath> castPaths(String start, String goal) {
			CastTupel querry = new CastTupel(start, goal);
			/*
			 * First look into the cache for the cast:
			 */
			synchronized(cache) {
				Optional<List<ClassCastPath>> cachedEntry = cache.get(querry);
				if(cachedEntry != null) {
					if(cachedEntry.isPresent()) {
						return cachedEntry.get();
					} else {
						return Collections.emptyList();
					}
				}
			}
			/*
			 * Perform depth first search from the source entity. 
			 * This operation fills the cache. 
			 */
			dfs(new ClassCastPath(start));
			
			synchronized(cache) {
				Optional<List<ClassCastPath>> cachedEntry = cache.get(querry);
				if(cachedEntry == null) {
					/*
					 * Cache the information that there is no path start -> goal:
					 */
					cache.put(querry, Optional.empty());
					return Collections.emptyList();
				}
				else if(cachedEntry.isPresent()) {
					return cachedEntry.get();
				} else {
					return Collections.emptyList();
				}
			}
		}
		
		private void dfs(ClassCastPath path) {
			String currentEntity = path.last();
			ClassView entityClass = entityView(currentEntity);
			for(EntityCast cast : entityClass.getAllCasts()) {
				String target = cast.getResultingEntity();
				boolean outGoingEdge = (cast.getDirection() != TransformDirection.FROM);  
				if(!outGoingEdge || path.contains(target)) {
					/*
					 * Ignore cast edges that are not outgoing or have targets already contained in the path: 
					 */
					continue;
				}
				/*
				 * New path found:
				 */
				ClassCastPath newPath = path.addCast(cast);
				/*
				 * Add it to cache:
				 */
				putInCache(newPath);
				/*
				 * Perform recursive depth first search:
				 */
				dfs(newPath);
			}
		}
		
		private void putInCache(ClassCastPath cp) {
			if(cp.isEmpty()) {
				return;
			}
			synchronized(cache) {
				CastTupel tupel = new CastTupel(cp.head(), cp.last());
				Optional<List<ClassCastPath>> castPaths = cache.get(tupel);
				if(castPaths == null) {
					List<ClassCastPath> castPathList = new ArrayList<>();
					castPaths = Optional.of(castPathList);
					cache.put(tupel, castPaths);
				}
				if(!castPaths.isPresent()) {
					throw new IllegalStateException("BUG: the cache in the cast graph is in an illegal state. "
							+ "cache says there is no path between " + cp.head() + " -> " + cp.last() + ","
									+ " however such a path is instructed to be cached.\n\n cache.toString() = " + cache.toString());
				} else if(!castPaths.get().contains(cp)) {
					castPaths.get().add(cp);
				}
			}
		}
		
		class CastTupel {
			final String from, to;
			CastTupel(String from , String to) {
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
				CastTupel other = (CastTupel) obj;
				return Objects.equals(from, other.from) && Objects.equals(to, other.to);
			}
		}
	}
}

