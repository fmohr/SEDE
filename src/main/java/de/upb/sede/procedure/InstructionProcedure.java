package de.upb.sede.procedure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.Attributes;

import de.upb.sede.exec.ServiceInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.upb.sede.composition.graphs.nodes.ParseConstantNode.ConstantType;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Task;

/**
 * This procedure is called when an instruction task needs to be executed. This
 * procedure assumes that the necessary classes are already loaded into the
 * class loader. The main task of this class is to call the correct method/
 * constructor based on the signatures dictated by the given parameters.
 *
 */
public class InstructionProcedure implements Procedure {
	/**
	 * If the parameters include constant types then the existence of a method/
	 * constructor with the given signature is tested with the various real types
	 * from the sets below.
	 */
	private static final Set<String> CLASSES_FOR_NUMBER = new HashSet<String>() {
		private static final long serialVersionUID = 1940970420361621252L;

		{
			add("boolean.class");
			add("java.lang.Boolean");
			add("byte.class");
			add("java.lang.Byte");
			add("char.class");
			add("java.lang.Character");
			add("short.class");
			add("java.lang.Short");
			add("int.class");
			add("java.lang.Integer");
			add("long.class");
			add("java.lang.Long");
			add("float.class");
			add("java.lang.Float");
			add("double.class");
			add("java.lang.Double");
		}
	};
	private static final Set<String> CLASSES_FOR_BOOL = new HashSet<String>() {
		private static final long serialVersionUID = 8951983401200957911L;

		{
			add("boolean.class");
			add("java.lang.Boolean");
		}
	};

	private static final Set<String> CLASSES_FOR_STRING = new HashSet<String>() {
		private static final long serialVersionUID = 1581782927839968915L;

		{
			add("java.lang.String");
		}
	};

	static Logger logger = LogManager.getLogger(InstructionProcedure.class);

	@Override
	public void process(Task task) {
		ExecutionEnvironment environment = task.getExecution().getEnvironment();
		InstructionNodeAttributes nodeAttributes = new InstructionNodeAttributes(task);
		// Get class to be called.
		String contextType = getContextType(environment, nodeAttributes);
		Class<?> contextClass = getContextClassForName(contextType);
		// Get SEDEObjects of the parameters that the method is called with.
		Map<String, SEDEObject> parameterObjects = getParameterObjectsInOrder(nodeAttributes.getParameters(),
				environment);
		// Get the class of the parameters.
		List<String> parameterTypes = getParameterTypes(parameterObjects);
		Class<?>[] parameterClasses;
		parameterClasses = getParameterClasses(parameterTypes, nodeAttributes, contextClass);
		// Get the values of the parameters.
		Object[] parameterValues = getParameterValues(parameterObjects);
		InvocationResult invocationResult;
		// When the call is a constructor call the constructor is being reflected
		// and called.
		if (nodeAttributes.isConstructor()) {
			invocationResult = callConstructor(task, contextClass, parameterClasses, parameterValues);
		}
		/*
		 * ... otherwise a method is called.
		 */
		else {
			invocationResult = callMethod(contextClass, contextType, parameterClasses, parameterValues, environment,
					nodeAttributes);
		}
		/*
		 * If the left side of the call is not null, than the result of the invocation
		 * is put into the execution environment.
		 */
		if (nodeAttributes.getLeftsidefieldname() != null) {
			SEDEObject outputSEDEObject = invocationResult.toSEDEObject();
			String leftsideFieldname = nodeAttributes.getLeftsidefieldname();
			environment.put(leftsideFieldname, outputSEDEObject);
		}
		task.setSucceeded();
	}

	/**
	 * Returns the type of the context that the instruction node demands. If the
	 * invocation is called on the class name itself, this name is returned.
	 * Otherwise the class is derived from a service handle that already exists in
	 * the environment.
	 * 
	 * @param environment
	 *            Environment to look for the service handle in.
	 * @param nodeAttributes
	 *            Attributes of the task.
	 * @return Type of context.
	 */
	private String getContextType(ExecutionEnvironment environment, InstructionNodeAttributes nodeAttributes) {
		String nodeContext = nodeAttributes.getContext();
		if (!nodeAttributes.isContextAFieldname()) {
			return nodeContext;
		} else {
			// Get the service from the environment and afterwards its type.
			SEDEObject serviceInstance = environment.get(nodeContext);
			if (!serviceInstance.isServiceInstance()) {
				throw new RuntimeException("Context: " + nodeContext + " is no ServiceInstance.");
			}
			return serviceInstance.getServiceHandle().getClasspath();
		}
	}

	private Class<?> getContextClassForName(String contextType) {
		try {
			return Class.forName(contextType);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Object[] getParameterValues(Map<String, SEDEObject> parameterObjects) {
		List<Object> inOrderObjects = new ArrayList<>(parameterObjects.size());
		for (SEDEObject sedeObject : parameterObjects.values()) {
			inOrderObjects.add(sedeObject.getObject());
		}
		Object[] parameterArray = new Object[inOrderObjects.size()];
		parameterArray = inOrderObjects.toArray(parameterArray);
		return parameterArray;
	}

	private InvocationResult callConstructor(Task task, Class<?> contextClass, Class<?>[] parameterClasses,
			Object[] parameterValues) {
		Constructor<?> constructor;
		Object newInstance;
		try {
			constructor = contextClass.getConstructor(parameterClasses);
			newInstance = constructor.newInstance(parameterValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ServiceInstanceHandle serviceInstanceHandle = createServiceInstanceHandle(task, newInstance);
		return new InvocationResult(serviceInstanceHandle, SEDEObject.SERVICE_INSTANCE_HANDLE_TYPE);
	}

	/**
	 * Create new new service instance handle with the given service instance.
	 *
	 * @return a new ServiceInstanceHandle
	 */
	private ServiceInstance createServiceInstanceHandle(Task task, Object newServiceInstance) {
		String serviceInstanceId = UUID.randomUUID().toString();
		String executorId = task.getExecution().getExecutionId();
		String classpath = newServiceInstance.getClass().getName();
		ServiceInstance si = new ServiceInstance(executorId, classpath, serviceInstanceId, newServiceInstance);
		return si;
	}

	private InvocationResult callMethod(Class<?> contextClass, String contextType, Class<?>[] parameterClasses,
			Object[] parameterValues, ExecutionEnvironment environment, InstructionNodeAttributes nodeAttributes) {
		Method methodToBeCalled;
		try {
			methodToBeCalled = contextClass.getMethod(nodeAttributes.getMethod(), parameterClasses);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
		String outputType = nodeAttributes.getLeftsidefieldType();
		// If invoking a static method or a constructor the context instance is null.
		Object contextInstance = null;
		if (nodeAttributes.isContextAFieldname()) {
			/*
			 * The context is field name which points to a service instance. The context
			 * instance is the field itself.
			 */
			SEDEObject field = environment.get(nodeAttributes.getContext());
			if (!field.isServiceInstance()) {
				throw new RuntimeException("BUG: trying to operate on service of type: " + contextType
						+ " instead the SEDE Object is " + field.getType());
			}
			contextInstance = field.getServiceInstance();
		}
		Object outputValue;
		try {
			outputValue = methodToBeCalled.invoke(contextInstance, parameterValues);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
		return new InvocationResult(outputValue, outputType);
	}

	private List<String> getParameterTypes(Map<String, SEDEObject> parameterObjects) {
		List<String> paramTypes = new ArrayList<>();
		parameterObjects.values().stream().forEach(sedeObj -> paramTypes.add(sedeObj.getType()));
		return paramTypes;
	}

	/**
	 * Gathers the SEDEObjects by the variable name from the environment in order of
	 * occurrence in the parameters.
	 * 
	 * @param parameters
	 *            Parameter names to look up.
	 * @param environment
	 *            Environment to look in.
	 * @return Variable names and their corresponding object in order of the
	 *         parameters
	 */
	private Map<String, SEDEObject> getParameterObjectsInOrder(List<String> parameters,
			ExecutionEnvironment environment) {
		Map<String, SEDEObject> result = new LinkedHashMap<>(parameters.size());
		parameters.forEach(varName -> result.put(varName, environment.get(varName)));
		return result;
	}

	/**
	 * Returns the classes of the parameter given. Therefore it's necessary to
	 * consider every real type for constant types in the signature of the method to
	 * be invoked.
	 * 
	 * @param paramTypes
	 *            Types of the parameters.
	 * @param nodeAttributes
	 *            Attributes of the instruction node.
	 * @param contextClass
	 *            Class that the method is called from.
	 * @return Classes of the parameters in order of occurence in the parameters.
	 */
	private Class<?>[] getParameterClasses(List<String> paramTypes, InstructionNodeAttributes nodeAttributes,
			Class<?> contextClass) {
		List<Class<?>> inOrderClasses = new ArrayList<>(paramTypes.size());
		/*
		 * If there are ConstantTypes in the signature of the method to be called, it's
		 * iterated over all possible methods (matching name and parameter count).
		 */
		try {
			if (parameterIncludeConstantType(paramTypes)) {
				Executable executableThatMatchesSignatureWithConstantTypes = getExecutableThatMatchesSignatureWithConstantTypes(
						paramTypes, nodeAttributes, contextClass);
				for (Class<?> clazz : executableThatMatchesSignatureWithConstantTypes.getParameterTypes()) {
					inOrderClasses.add(clazz);
				}
			} else {
				for (String classType : paramTypes) {
					Class<?> clazz = Class.forName(classType);
					inOrderClasses.add(clazz);
				}
			}
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		// Convert the list to an array.
		Class<?>[] inOrderClassesArray = new Class<?>[inOrderClasses.size()];
		inOrderClassesArray = inOrderClasses.toArray(inOrderClassesArray);
		return inOrderClassesArray;
	}

	private boolean parameterIncludeConstantType(List<String> paramTypes) {
		Set<String> constantTypes = getConstantTypeNames();
		for (String paramType : paramTypes) {
			if (constantTypes.contains(paramType))
				return true;
		}
		return false;
	}

	private Set<String> getConstantTypeNames() {
		ConstantType[] constantTypes = ConstantType.values();
		Set<String> constantTypeNames = new HashSet<>();
		for (ConstantType type : constantTypes) {
			constantTypeNames.add(type.toString());
		}
		return constantTypeNames;
	}

	/**
	 * Tries to find an Executable (Method or Constructor) provided by the context
	 * class that matches the name of the invocation and its signature.
	 * 
	 * @param paramTypes
	 *            Types of the parameters.
	 * @param nodeAttributes
	 *            Attributes of the InstructionNode
	 * @param contextClass
	 *            Class to invoke the Executable from.
	 * @return Executable that matches the demanded invocation.
	 * @throws NoSuchMethodException
	 *             No Method with the demanded signature was found.
	 */
	private Executable getExecutableThatMatchesSignatureWithConstantTypes(List<String> paramTypes,
			InstructionNodeAttributes nodeAttributes, Class<?> contextClass) throws NoSuchMethodException {
		boolean isConstructor = nodeAttributes.isConstructor();
		String methodName = nodeAttributes.getMethod();
		Executable[] contextClassExecutables;
		if (isConstructor) {
			contextClassExecutables = contextClass.getConstructors();
		} else {
			contextClassExecutables = contextClass.getMethods();
		}

		List<Executable> executablesThatMatchMethodNameAndParamCount = new ArrayList<>();
		for (Executable executable : contextClassExecutables) {

			if (!isConstructor && !executable.getName().equals(methodName)) {
				/* not a constructor and method name doesn't match. */
				continue;
			}
			if (paramTypes.size() != executable.getParameterCount()) {
				/* parameter size doesn't match. */
				continue;
			}
			/* perfect fit */
			executablesThatMatchMethodNameAndParamCount.add(executable);
		}
		for (Executable executable : executablesThatMatchMethodNameAndParamCount) {
			if (matchesSignature(paramTypes, executable)) {
				return executable;
			}
		}
		throw new NoSuchMethodException(methodName);
	}

	/**
	 * Checks whether the given executable is suitable for the types of the
	 * parameters. Therefore constant types must be checked against all suitable
	 * real types.
	 * 
	 * @param calledParamTypes
	 *            Types of the parameters.
	 * @param executableToCheck
	 *            Executable that is checked against the parameters.
	 * @return True if the executable is suitable for the parameters. False
	 *         otherwise.
	 */
	private boolean matchesSignature(List<String> calledParamTypes, Executable executableToCheck) {
		List<String> methodParameterClasses = getParamTypes(executableToCheck);
		Map<Integer, String> realTypesInCall = getIndicesOfRealTypes(calledParamTypes);
		// If the real types do not match then this method is no candidate for the
		// called parameters.
		for (Entry<Integer, String> indexTypePairInCall : realTypesInCall.entrySet()) {
			if (methodParameterClasses.get(indexTypePairInCall.getKey()) != indexTypePairInCall.getValue()) {
				return false;
			}
		}
		Map<Integer, String> constantTypesInCall = getInverseIndicesTypes(realTypesInCall, calledParamTypes);
		for (Entry<Integer, String> constantType : constantTypesInCall.entrySet()) {
			int indexInSignature = constantType.getKey();
			String constantTypeName = constantType.getValue();
			String classNameOnIndex = methodParameterClasses.get(indexInSignature);
			switch (constantTypeName) {
			case "Number":
				if (isNumber(classNameOnIndex)) {
					continue;
				}
			case "String":
				if (isString(classNameOnIndex)) {
					continue;
				}
			case "Bool":
				if (isBool(classNameOnIndex)) {
					continue;
				}
			case "NULL":
				if (isNULL(classNameOnIndex)) {
					continue;
				}
			}
			return false;
		}
		return true;
	}

	private List<String> getParamTypes(Executable executable) {
		List<String> executableParamTypes = new ArrayList<>();
		for (Class<?> clazz : executable.getParameterTypes()) {
			executableParamTypes.add(clazz.getName());
		}
		return executableParamTypes;
	}

	private Map<Integer, String> getIndicesOfRealTypes(List<String> calledParamTypes) {
		Map<Integer, String> result = new HashMap<>();
		for (int i = 0; i < calledParamTypes.size(); i++) {
			String type = calledParamTypes.get(i);
			if (!isConstantType(type))
				result.put(i, type);
		}
		return result;
	}

	private Map<Integer, String> getInverseIndicesTypes(Map<Integer, String> realTypesInCall,
			List<String> calledParamTypes) {
		Map<Integer, String> mapOfParameters = new HashMap<>();
		for (int i = 0, size = calledParamTypes.size(); i < size; i++) {
			mapOfParameters.put(i, calledParamTypes.get(i));
		}
		for (Integer index : realTypesInCall.keySet()) {
			mapOfParameters.remove(index);
		}
		return mapOfParameters;
	}

	private boolean isNULL(String classNameOnIndex) {
		return true;
	}

	private boolean isBool(String classNameOnIndex) {
		return CLASSES_FOR_BOOL.contains(classNameOnIndex);
	}

	private boolean isString(String classNameOnIndex) {
		return CLASSES_FOR_STRING.contains(classNameOnIndex);
	}

	private boolean isNumber(String classNameOnIndex) {
		return CLASSES_FOR_NUMBER.contains(classNameOnIndex);
	}

	private boolean isConstantType(String type) {
		return getConstantTypeNames().contains(type);
	}

	// TODO Must be moved into another class.
	private Class<?> addClassInClassLoader(String jarFilePath) throws Exception {
		URL serviceFilesURL = new URL("jar", "", jarFilePath + "!/");
		JarURLConnection jarURLConnection = (JarURLConnection) serviceFilesURL.openConnection();
		Attributes attr = jarURLConnection.getMainAttributes();
		if (attr != null) {
			logger.info("Servicefile: " + jarFilePath + "\n Main class:" + attr.getValue(Attributes.Name.MAIN_CLASS));
		} else {
			logger.error("No entry point in: \"" + jarFilePath + "\"");
		}
		URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { serviceFilesURL },
				this.getClass().getClassLoader());
		Class<?> reflectedClass = Class.forName(jarFilePath, true, urlClassLoader);
		addURLToClassLoader(serviceFilesURL);
		return reflectedClass;
	}

	private static void addURLToClassLoader(URL url) throws Exception {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		if (systemClassLoader instanceof URLClassLoader) {
			URLClassLoader urlClassLoader = (URLClassLoader) systemClassLoader;
			// addURL is a protected method, but we can use reflection to call it
			Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			addURLMethod.setAccessible(true);
			addURLMethod.invoke(urlClassLoader, url);
		} else {
			logger.error("System ClassLoader is no URLClassLoader.");
		}
	}

	class InstructionNodeAttributes {
		private final boolean isConstructor;
		private final boolean isContextAFieldname;
		private final String method;
		private final String leftsidefieldname;
		private final String host;
		private final String context;
		private final String fmInstruction;
		private final List<String> parameters;
		private final String leftSideFieldType;

		@SuppressWarnings("unchecked")
		public InstructionNodeAttributes(Task task) {
			Map<String, Object> parameters = task.getAttributes();
			this.isConstructor = (boolean) parameters.get("is-service-construction");
			this.isContextAFieldname = (boolean) parameters.get("is-context-a-fieldname");
			this.method = (String) parameters.get("method");
			this.leftsidefieldname = (String) parameters.get("leftsidefieldname");
			this.host = (String) parameters.get("host");
			this.context = (String) parameters.get("context");
			this.fmInstruction = (String) parameters.get("fmInstruction");
			this.parameters = (List<String>) parameters.get("params");
			this.leftSideFieldType = (String) parameters.get("leftsidefieldtype");
		}

		public String getLeftsidefieldType() {
			return leftSideFieldType;
		}

		public String getFmInstruction() {
			return fmInstruction;
		}

		public boolean isConstructor() {
			return isConstructor;
		}

		public boolean isContextAFieldname() {
			return isContextAFieldname;
		}

		public String getMethod() {
			return method;
		}

		public String getLeftsidefieldname() {
			return leftsidefieldname;
		}

		public String getHost() {
			return host;
		}

		public String getContext() {
			return context;
		}

		public List<String> getParameters() {
			return parameters;
		}
	}

	class InvocationResult {
		Object outputValue;
		String outputType;

		InvocationResult(Object outputValue, String outputType) {
			this.outputValue = outputValue;
			this.outputType = outputType;
		}

		public Object getOutputValue() {
			return outputValue;
		}

		public String getOutputType() {
			return outputType;
		}

		public SEDEObject toSEDEObject() {
			return new SEDEObject(getOutputType(), getOutputValue());
		}
	}
}
