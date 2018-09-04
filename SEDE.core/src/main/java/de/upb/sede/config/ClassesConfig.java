package de.upb.sede.config;

import java.util.*;
import java.util.function.Function;


import de.upb.sede.util.FilteredIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Encapsulated configuration about classes, like service names and their methods.
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

	private final static Logger logger = LogManager.getLogger();

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
				for (String attributeName : superConfig.keySet()) {
					inheritFromRoot(classconfig, superConfig, attributeName);
				}
			}
		}
	}

	/**
	 * This method realises inheritance between two classes.
	 * Given a super config and its attributeName it adds the attribute to the base configuration.
	 *
	 */
	private void inheritFromRoot(Map<String, Object> baseConfig, Map<String, Object> superConfig, String attributeName) {
		Object superAttribute = superConfig.get(attributeName);
		boolean extended = false; // flag that indicates that attribute has been extended.
		if (attributeName.equals("extends")) {
			// don't want to change inheritance or else funny things happen when calling
			// resolveInheritances twice.
		}
		else if(attributeName.equals("wrapper")) {
			/*
				Wrapper is replaced.
			 */
			baseConfig.put("wrapper", superAttribute);
		}
		else if(attributeName.equals("methods")) {
			/*
				Let method-inheritance be done by the specific method:
			 */
			Map<String, Object> baseMethods = (Map<String, Object>) baseConfig.get("methods");
			Map<String, Object> superMethods = Collections.unmodifiableMap((Map<String, Object>) superAttribute);
			if(baseMethods==null) {
				baseMethods = new HashMap<String, Object>();
				baseConfig.put("methods", baseMethods);
			}
			if(superMethods == null){
				superMethods = Collections.EMPTY_MAP;
			}

			inheritMethods(baseMethods, superMethods);
		} else if(attributeName.equals("abstract")) {
			// abstraction flag is not inherited
		} else {
			logger.warn("Super configuration has some unknown attributes {} which can't be inherited.",
					attributeName);
		}
	}

	/**
	 * Inherit methods from super configuration.
	 */
	private void inheritMethods(Map<String, Object> baseMethods, Map<String, Object> superMethods) {
		/*
			Only overwrite methods which aren't defined by the base class:
		 */
		for(String methodname : superMethods.keySet()){
			if(baseMethods.containsKey(methodname)) {
				continue;
			} else{
				baseMethods.put(methodname, superMethods.get(methodname));
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

	/**
	 * Returns a ClassInfo instance regarding the given classpath.
	 */
	public ClassInfo classInfo(String classpath) {
		if (containsKey(classpath)) {
			Map<String, Object> config = (Map<String, Object>) get(classpath);
			if(config.containsKey("wrapper")) {
				ClassInfo ci = this.classInfo((String) config.get("wrapper"));
				return new ClassInfo(classpath, config, ci);
			}
			else {
				return new ClassInfo(classpath, config);
			}
		} else {
			throw new RuntimeException("Class " + classpath + " not found.");
		}
	}

	/**
	 * Class information like wrapper and methods.
	 */
	public static class ClassInfo {

		private final Map<String, Object> configuration;
		private final Optional<ClassInfo> wrapper;
		private final String cp;

		/**
		 * Creates a classinfo without a wrapper.
		 */
		private ClassInfo(String classpath, Map<String, Object> config) {
			this(classpath, config, null);
		}

		/**
		 * Creates a classinfo with the given wrapper. The given wrapper is itself another ClassInfo instance.
		 * @param classpath
		 * @param config
		 * @param wrapper
		 */
		private ClassInfo(String classpath, Map<String, Object> config, ClassInfo wrapper) {
			cp = classpath;
			this.configuration = config;
			this.wrapper = Optional.ofNullable(wrapper);
		}

		/**
		 * 	Returns the "methods" json object from the classinfo root.
		 */
		private Map<String, Object> getInternalMethods() {
			if (configuration.containsKey("methods")) {
				return (Map<String, Object>) configuration.get("methods");
			} else {
				return Collections.EMPTY_MAP;
			}
		}

		/**
		 * Returns list of method names that are accessible.
		 */
		public Set<String> getAccessibleMethods() {
			Set<String> methods = new HashSet<>();
			methods.addAll(getInternalMethods().keySet());
			if(wrapper.isPresent()) {
				methods.addAll(wrapper.get().getAccessibleMethods());
			}
			return methods;
		}

		/**
		 * @return true if this class offers the given method by name.
		 */
		private boolean hasMethod(String methodname) {
			return getInternalMethods().containsKey(methodname);
		}

		/**
		 * @return constructor method info
		 */
		public MethodInfo constructInfo() {
			if (hasMethod("$construct")) {
				Map<String, Object> constructMap = MethodInfo.emptyConstructor(cp).configuration;
				constructMap.putAll((Map<String, Object>) getInternalMethods().get("$construct"));
				return new MethodInfo(cp, "$construct", constructMap);
			} else if(wrapper.isPresent()){
				return wrapper.get().constructInfo();
			} else {
				return MethodInfo.emptyConstructor(cp);
			}
		}

		/**
		 *
		 * @return true if the class is marked abstract. (cannot be instantiated)
		 */
		public boolean isAbstract() {
			if(configuration.containsKey("abstract")){
				return (Boolean) configuration.get("abstract");
			}
			if(className().startsWith("$") && className().endsWith("$")) {
				return true;
			}
			return false;
		}

		/**
		 * @return true if a wrapper was defined.
		 * If a class is wrapped it is the wrapper class that is instantiated instead of the class itself.
		 * If true the class might not even exist.
		 */
		public boolean isWrapped() {
			return wrapper.isPresent();
		}

		/**
		 * @return class path of this class that will be instantiated.
		 */
		public String actualClasspath() {
			if(isWrapped()) {
				return wrapper.get().actualClasspath();
			} else {
				return cp;
			}
		}

		/**
		 * Returns the corresponding method info to the given name.
		 */
		public MethodInfo methodInfo(String methodname) {
			if(methodname.equals("$construct")) {
				return constructInfo();
			}else if (hasMethod(methodname)) {
				return new MethodInfo(cp, methodname, (Map<String, Object>) getInternalMethods().get(methodname));
			} else if(isWrapped()) {
				return wrapper.get().methodInfo(methodname);
			}else{
				throw new RuntimeException("Method " + methodname + " not found in: "  + cp);
			}
		}

		public String className() {
			return cp;
		}
	}

	public static class MethodInfo {
		private final Map<String, Object> configuration;
		private final String classname;
		private final String methodname;

		private MethodInfo(String classname, String methodname, Map<String, Object> config) {
			this.classname = classname;
			this.methodname = methodname;
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

		public void insertFixedConstants(List<String> inputs) {
			int givenInputSize = inputs.size();
			for (int i = 0, paramCount = paramCount(); i < paramCount; i++) {
				Map parameter = getParameter(i);
				if(parameter.containsKey("fixed")) {
					String fixedConstant = (String) parameter.get("fixed");
					inputs.add(i, fixedConstant);
				} else {
					givenInputSize--;
				}
			}
			if(givenInputSize!= 0) {
				throw new RuntimeException("Too few or too many inputs for method. " + classname + ":" + methodname +
						". Over/underflow: " + givenInputSize + " given inputs: " + inputs);
			}
		}

		public boolean isParamStateMutating(int paramIndex) {
			Map parameter = getParameter(paramIndex);
			if(parameter.containsKey("statemutating")) {
				return (boolean) parameter.get("statemutating");
			} else {
				return false;
			}
		}

		public boolean isParamFixed(int paramIndex) {
			Map parameter = getParameter(paramIndex);
			return parameter.containsKey("fixed");
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

		private static MethodInfo emptyConstructor(String classpath) {
			HashMap<String, Object> emptyConstructConfig = new HashMap<>();
			emptyConstructConfig.put("params", Collections.EMPTY_LIST);
			emptyConstructConfig.put("returntype", classpath);
			emptyConstructConfig.put("static", true);
			return new MethodInfo(classpath, "$construct", emptyConstructConfig);
		}

		@SuppressWarnings("unchecked")
		public List<String> paramTypes() {
			List<String> paramTypes = new ArrayList<>();
			for(Object param : getParams()){
				if(param instanceof String) {
					paramTypes.add((String) param);
				} else if(param instanceof Map){
					paramTypes.add(Objects.requireNonNull((String) ((Map)param).get("type")));
				} else{
					throw new RuntimeException("Faulty configuration: " + param);
				}
			}
			return paramTypes;
		}

		private List<Object> getParams() {
			if(configuration.containsKey("params")) {
				return (List<Object>) configuration.get("params");
			} else {
				return Collections.EMPTY_LIST;
			}
		}

		public boolean hasReturnType() {
			return configuration.containsKey("returntype");
		}

		public String returnType() {
			if(hasReturnType()){
				return (String) configuration.get("returntype");
			}
			else {
				return "NULL";
			}
		}

		public boolean isStatic()  {
			if(configuration.containsKey("static"))
				return (boolean) configuration.get("static");
			else
				return false;
		}

		public String methodName() {
			return methodname;
		}
	}

}