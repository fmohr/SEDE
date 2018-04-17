package de.upb.sede.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.databind.node.ArrayNode;

import de.upb.sede.util.FilteredIterator;

/**
 * Encapsulated configuration about classes, like wrappers names.
 * 
 * @author aminfaez
 *
 */
public class ClassesConfig extends Configuration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6262945609277195180L;

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
			for (String  superConfig : extensions) {
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
						if (baseAttribute instanceof List && superAttribute instanceof List ) {
							((ArrayNode) baseAttribute).addAll((ArrayNode) superAttribute);
							extended = true;
						} else if (baseAttribute instanceof Map  && baseAttribute instanceof Map) {
							((Map) baseAttribute).putAll((Map) superAttribute);
							extended = true;
						} else {
							System.err.println("CONFIG: Attribute " + attributeName
									+ " is being replaced by an incompatible type from: " + superClasspath);
						}
					}
					if (!extended) {
						// couldnt add attribute, so just replace it
						((Map) classconfig).put(attributeName, superAttribute);
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
	 * Returns true if the configuration has the given classpath entry.
	 */
	public boolean classknown(String classpath) {
		return this.containsKey(classpath);
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
					// simple string entry for method
					if (method.toString().equals(methodName)) {
						return true;
					}
				}
				// method definition not found
				return false;
			} else {
				// object node. use the has method
				return ((Map<String, Object>)methods).containsKey(methodName);
			}
		} else {
			// the 'methods' fields is not defined.
			// So return true, because if 'methods' is not defined, all methods of the given
			// class are white-listed.
			return true;
		}
	}

	/**
	 * The method result-map is used after method invocation to map returning
	 * keywords to values, like arguments and return value.
	 * 
	 * Example: The map returned for method with the given methodName for an
	 * instance of the class with the given classpath contains entries:
	 * -"a":"return" After the method is invoked, 'a' will be mapped to the return
	 * value of the invocation. -"b":"i2" After the method is invoked, 'b' will be
	 * mapped to the second argument which was passed to the method. Notice that
	 * some methods may apply changes to passed arguments instead of returning
	 * values. This way problems with call-by-reference can be resolved.
	 */
	public Map<String, String> getMethodResultMap(String classpath, String methodName) {
		if (!methodKnown(classpath, methodName)) {
			throw new RuntimeException(
					"The classpath: " + classpath + " and method: " + methodName + " are not known.");
		}
		if ("__construct".equals(methodName)) {
			return getStandardResultMap();
		}
		if (isWrapped(classpath)) {
			// if the class is wrapped first look if the wrapper config is defining the
			// result map of this method.
			String wrapperClasspath = getWrapperClasspath(classpath);
			if (methodKnown(wrapperClasspath, methodName)) {
				return getMethodResultMap(wrapperClasspath, methodName);
			}
		}
		Map<String, Object> classConfig = getClassConfiguration(classpath);
		if (classConfig.containsKey("methods")) {
			Object methods = classConfig.get("methods");
			if (!(methods instanceof List)) { // if its not an array it's method may define mapping
				Map<String, Object> methodMap = (Map<String, Object>) ((Map<String, Object>)(classConfig.get("methods"))).get(methodName);
				if (methodMap == null) {
					return null;
				}
				if (methodMap.containsKey("resultmap")) {
					// result map was defined
					Map<String, String> resultMap = (Map<String, String>) methodMap.get("resultmap");
					return resultMap;
				}
			}

			// although the method is defined, no result-map was defined in the classes.json
			// config. opt out to standard result-map.
		}
		// if no map was returned up to this point, it means that the class white-lists
		// all methods.
		// return standard resultMap:
		return getStandardResultMap();

	}

	/**
	 * The standard result map which maps the returned value of the method
	 * invocation to the 'out' keyword.
	 * 
	 * @return standard result map
	 */
	private Map<String, String> getStandardResultMap() {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("out", "return");
		return resultMap;
	}

	/**
	 * Returns an Iterable of all the subconfigurations of the given baseConfig. For
	 * every classpath in the iterable it holds that it extends baseConfing.
	 * 
	 * @param baseConfig
	 *            base configuration
	 * @return iterable off all configuration that extend the given baseconfig.
	 */
	public Iterable<String> allSubconfigs(String baseConfig) {
		// this filter returns true if the provided string extends from one of the
		// classes in bases.
		Function<String, Boolean> inheritanceFilter = classpath -> {
			// classpaths starting with $ aren't actually classpaths
			return !classpath.startsWith("$") && extendsClassConfig(classpath, baseConfig);
		};
		// return a new filtered iterator wrapped in a iterable.
		return () -> new FilteredIterator<>(keySet().iterator(), inheritanceFilter);
	}

	public boolean stateMutational(String classpath, String methodname) {
		return true; // TODO
	}

}