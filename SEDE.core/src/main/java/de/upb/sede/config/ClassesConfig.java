package de.upb.sede.config;

import java.util.*;
import java.util.function.Function;


import de.upb.sede.util.FilteredIterator;

/**
 * Encapsulated configuration about classes, like wrappers names.
 * 
 * @author aminfaez
 *
 */
@SuppressWarnings("unchecked")
public class ClassesConfig extends Configuration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reads the configuration files from configPaths and appends them into itself..
	 */
	public ClassesConfig(String... configPaths) {
		this();
		appendConfigFromFiles(configPaths);
	}

	public ClassesConfig() {
		super();
	}

	public void appendConfigFromJsonStrings(String... jsonStrings) {
		super.appendConfigFromJsonStrings(jsonStrings);
		this.resolveInheritances();
	}

	/**
	 * Resolves the 'extends' attribute from classes. A class can define an
	 * 'extends' array filled with other classes from the configuration. The class
	 * will inherit all the attributes from the other classes.
	 */
	private void resolveInheritances() {
		Set<String> resolvedClasses = new HashSet<>();
		Set<String> unresolvedClasses = new HashSet<>();
		for (String classpath : this.keySet()) {
			if (resolvedClasses.contains(classpath)) {
				// this was resolved by a previous iteration.
				continue;
			} else {
				// Resolve the one class.
				Map<String, Object> classconfig = getClassConfiguration(classpath);
				unresolvedClasses.add(classpath);
				resolveInheritance(classconfig, unresolvedClasses, resolvedClasses);
				unresolvedClasses.remove(classpath);
				resolvedClasses.add(classpath);
			}
		}
	}

	/**
	 * Resolves the inheritance of the given class-config by recursively resolving
	 * the class-configs which are extended by the given class-config. By the end of
	 * the method all the extended classes will be contained in the resolved set.
	 * 
	 * Some examples:
	 * 
	 * If A extends B, because B doesn't inherit anything, all of the attributes
	 * from B are immediately added to A. Now A and B are resolved.
	 * 
	 * If A extends B and B extends C, first B needs to be resolved. So 'A' will be
	 * added to 'unresolved' and resolveInheritance(B, {A}, {}) will be called.
	 * Afterwards 'B' will be added to resolved and it's attributes will be added to
	 * A.
	 * 
	 * If A extends B and B extends C and C extends A, there is a circled
	 * dependency. After one recursion steps, resolveInheritance(C, {A, B}, {}) will
	 * be called. Then because 'C' extends a class from the unresolved set a runtime
	 * exception will be thrown.
	 * 
	 * @inheritSet Set of classes that are inheriting from this classes
	 *             configuration. If the given classconfig inherits from one of the
	 *             classes in this set a RuntimeException will be thrown to indicate
	 *             that a circled dependency has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void resolveInheritance(Map<String, Object> classconfig, Set<String> unresolved, Set<String> resolved) {
		if (classconfig.containsKey("extends")) {
			// Go once through the array to check for circled dependency.
			List<String> extensions = (List<String>) classconfig.get("extends");
			for (String superConfig : extensions) {
				if (unresolved.contains(superConfig)) {
					throw new RuntimeException("Circled dependency in Inheritance in ClassesConfig:"
							+ " The given class and class: " + superConfig + " extend from each other.");
				}
			}
			for (String superClasspath : extensions) {
				Map<String, Object> superConfig = getClassConfiguration(superClasspath);
				if (!resolved.contains(superClasspath)) {
					// first resolve the base config:
					unresolved.add(superClasspath);
					resolveInheritance(superConfig, unresolved, resolved);
					resolved.add(superClasspath);
					unresolved.remove(superClasspath);
				}
				// now add everything from superConfig to classconfig
				Iterator<String> fieldNames = superConfig.keySet().iterator();
				while (fieldNames.hasNext()) {
					String attributeName = fieldNames.next();
					if (attributeName.equals("extends")) {
						// don't want to change inheritance or else funny things happen when calling
						// resolveInheritances twice.
						continue;
					}
					Object superAttribute = superConfig.get(attributeName);
					boolean extended = false; // flag that indicates that attribute has been extended.
					if (classconfig.containsKey(attributeName)) {
						// first try to add the value to the array or dictionary
						Object baseAttribute = classconfig.get(attributeName);
						if (baseAttribute instanceof List && superAttribute instanceof List) {
							((List) baseAttribute).addAll((List) superAttribute);
							extended = true;
						} else if (baseAttribute instanceof Map && baseAttribute instanceof Map) {
							Map<String, Object> newAttrMap = new HashMap<>((Map) superAttribute);
							newAttrMap.putAll((Map) baseAttribute);
							classconfig.put(attributeName, newAttrMap);
							extended = true;
						} else {
//							System.err.println("CONFIG: Attribute " + attributeName
//									+ " is being replaced by an incompatible type from: " + superClasspath);
						}
					}
					if (!extended) {
						// couldnt add attribute, so just replace it
						(classconfig).put(attributeName, superAttribute);
					}
				}
			}
		}
	}

	/**
	 * Recursively checks if the classpath is set to extend the given baseClasspath.
	 * Deep first search in the inheritance tree.
	 */
	public boolean extendsClassConfig(String classpath, String baseClasspath) {
		Map<String, Object> classconfig = getClassConfiguration(classpath);
		if (classconfig.containsKey("extends")) {
			List<String> extensions = (List<String>) classconfig.get("extends");
			for (Object extension : extensions) {
				if (baseClasspath.equals(extension.toString())) {
					// add this class to the bases so all sub classes of this will return true
					// aswell.
					return true;
				}
				// if the extended classpath extends the base classpath also return true:
				else if (extendsClassConfig(extension.toString(), baseClasspath)) {
					return true;
				}
			}
		}
		// none found
		return false;
	}

	/**
	 * Returns list of loaded class paths.
	 * @return list of supported class paths.
	 */
	public Set<String> classesKnown() {
		return Collections.unmodifiableSet(this.keySet());
	}

	/**
	 * Returns true if the configuration has the given classpath entry.
	 */
	public boolean classknown(String classpath) {
		return this.containsKey(classpath);
	}

	/**
	 * Returns true if the configuration doesn't have the given classpath entry.
	 */
	public boolean classunknown(String classpath) {
		return !classknown(classpath);
	}

	/**
	 * Returns the JsonNode for the classpath in as it is defined in the
	 * configuration.
	 */
	private Map<String, Object> getClassConfiguration(String classpath) {
		return (Map<String, Object>) this.get(classpath);
	}

	/**
	 * Returns true if in the configuration the given classpath entry has an wrapper
	 * entry.
	 */
	public boolean isWrapped(String classpath) {
		return classknown(classpath) && getClassConfiguration(classpath).containsKey("wrapper");
	}

	/**
	 * Returns the classpath of wrapper which the given classpath was assigned onto.
	 */
	public String getWrapperClasspath(String classpath) {
		return this.getClassConfiguration(classpath).get("wrapper").toString();
	}

	/**
	 * Returns true if the given method is defined within the given class scope in
	 * the classes.json configuration.
	 * 
	 * @param classpath
	 *            fully qualified class name.
	 * @param methodName
	 *            method name to check
	 * @return true if the configuration defines the method unter the classpath.
	 */
	@SuppressWarnings("unchecked")
	public boolean methodKnown(String classpath, String methodName) {
		if (!classknown(classpath)) {
			return false;
		}
		if ("__construct".equals(methodName)) {
			// constructor is known.
			return true;
		}
		if (isWrapped(classpath)) {
			// first look at the wrapper
			String wrapperPath = getWrapperClasspath(classpath);
			if (methodKnown(wrapperPath, methodName)) {
				// method overrides it
				return true;
			}
		}
		Map<String, Object> classConfig = getClassConfiguration(classpath);
		if (classConfig.containsKey("methods")) {
			// if the 'methods' fields is defined:
			Object methods = classConfig.get("methods");
			if (methods instanceof List) {
				List<Object> methodList = (List<Object>) methods;
				// traverse array
				for (Object method : methodList) {
					if(method instanceof String && method.toString().equals(methodName)) {
					// simple string entry for method
						return true;
					} else if(method instanceof Map) {
						return ((Map)method).get("name").equals(methodName);
					} else{
						throw new RuntimeException("Faulty configuration..");
					}
				}
				// method definition not found
				return false;
			} else {
				// object node. use the has method
				return ((Map<String, Object>) methods).containsKey(methodName);
			}
		} else {
			// the 'methods' fields is not defined.
			// So return false.
			return false;
		}
	}

	/**
	 * Returns an Iterable of all the subconfigurations of the given baseConfig. For
	 * every classpath in the iterable it holds that it extends baseConfing.
	 * 
	 * @param baseConfig
	 *            base configuration
	 * @return iterable off all configuration that extend the given baseconfig.
	 */
	private Iterable<String> allSubconfigs(String baseConfig) {
		// this filter returns true if the provided string extends from one of the
		// classes in bases.
		Function<String, Boolean> inheritanceFilter = classpath -> {
			// classpaths starting with $ aren't actually classpaths
			return !classpath.startsWith("$") && extendsClassConfig(classpath, baseConfig);
		};
		// return a new filtered iterator wrapped in a iterable.
		return () -> new FilteredIterator<>(keySet().iterator(), inheritanceFilter);
	}

	public ClassInfo classInfo(String classpath) {
		if (containsKey(classpath)) {
			return new ClassInfo(classpath, (Map<String, Object>) get(classpath));
		} else {
			throw new RuntimeException("Class " + classpath + " not found.");
		}
	}


	public static class ClassInfo {
		private final Map<String, Object> configuration;
		private final String cp;

		private ClassInfo(String classpath, Map<String, Object> config) {
			cp = classpath;
			configuration = config;
		}

		private Map<String, Object> getMethods() {
			if (configuration.containsKey("methods")) {
				return (Map<String, Object>) configuration.get("methods");
			} else {
				return Collections.EMPTY_MAP;
			}
		}

		public boolean hasMethod(String methodname) {
			return getMethods().containsKey(methodname);
		}

		public MethodInfo constructInfo() {
			if (hasMethod("$construct")) {
				 Map<String, Object> constructMap = MethodInfo.emptyConstructor(cp).configuration;
				constructMap.putAll((Map<String, Object>) getMethods().get("$construct"));
				return new MethodInfo(constructMap);
			} else {
				return MethodInfo.emptyConstructor(cp);
			}
		}

		public MethodInfo methodInfo(String methodname) {
			if (hasMethod(methodname)) {
				return new MethodInfo((Map<String, Object>) getMethods().get(methodname));
			} else {
				throw new RuntimeException("Method " + methodname + " not found in: "  + cp);
			}
		}

	}

	public static class MethodInfo {
		private final Map<String, Object> configuration;

		private MethodInfo(Map<String, Object> config) {
			configuration = config;
		}

		public boolean isStateMutating() {
			if(isStatic()) {
				return false;
			}
			if (configuration.containsKey("statemutating")) {
				return (boolean) configuration.get("statemutating");
			} else {
				return true;
			}
		}

		public int paramCount() {
			if(configuration.containsKey("params")) {
				return ((List)configuration.get("params")).size();
			} else {
				return 0;
			}
		}

		private Map getParameter(int paramIndex) {
			if(paramIndex < paramCount() && paramIndex >= 0) {
				Object param = ((List<Object>) configuration.get("params")).get(paramIndex);
				if(param instanceof String) {
					Map parameterMap = new HashMap();
					parameterMap.put("type", param);
					return parameterMap;

				} else if(param instanceof  Map){
					return (Map) param;
				} else {
					throw new RuntimeException("Faulty configuration: " + param);
				}
			} else{
				throw new RuntimeException("Parameter index " + paramIndex + " is out of bound: max is " + paramCount());
			}
		}

		public String paramType(int paramIndex) {
			return (String) Objects.requireNonNull(getParameter(paramIndex).get("type"));
		}

		public boolean isParamStateMutating(int paramIndex) {
			Map parameter = getParameter(paramIndex);
			if(parameter.containsKey("statemutating")) {
				return (boolean) parameter.get("statemutating");
			} else {
				return false;
			}
		}

		/**
		 * Returns the param index of the nth state mutating parameter.
		 */
		public int indexOfNthStateMutatingParam(int nth) {
			for(int i = 0, size = paramCount(); i < size; i++) {
				if(isParamStateMutating(i)){
					if(nth == 0) {
						return i;
					} else{
						nth--;
					}
				}
			}
//			throw new RuntimeException("The " + nth + "th state mutating parameter does not exist.");
			return -1;
		}

		public static MethodInfo emptyConstructor(String classpath) {
			HashMap<String, Object> emptyConstructConfig = new HashMap<>();
			emptyConstructConfig.put("params", Collections.EMPTY_LIST);
			emptyConstructConfig.put("returntype", classpath);
			emptyConstructConfig.put("static", true);
			return new MethodInfo(emptyConstructConfig);
		}

		@SuppressWarnings("unchecked")
		public List<String> paramTypes() {
			if(configuration.containsKey("params")) {
				List<String> paramTypes = new ArrayList<>();
				for(Object param : (List<Object>) configuration.get("params")){
					if(param instanceof String) {
						paramTypes.add((String) param);
					} else if(param instanceof Map){
						paramTypes.add(Objects.requireNonNull((String) ((Map)param).get("type")));
					} else{
						throw new RuntimeException("Faulty configuration: " + param);
					}
				}
				return paramTypes;
			} else {
				return Collections.EMPTY_LIST;
			}
		}

		public boolean hasReturnType() {
			return configuration.containsKey("returntype");
		}

		public String returnType() {
			return (String) configuration.get("returntype");
		}

		public boolean isStatic()  {
			if(configuration.containsKey("static"))
				return (boolean) configuration.get("static");
			else
				return false;
		}
	}

}