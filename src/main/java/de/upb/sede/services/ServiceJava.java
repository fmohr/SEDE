package de.upb.sede.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.jar.Attributes;

import org.apache.log4j.Logger;

public class ServiceJava extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4948661597034811141L;
	private volatile Map<String, ServiceInstanceJava> instances = new HashMap<>();
	private Class<?> entryClass = null;

	private static final Logger logger = Logger.getLogger(ServiceJava.class);

	public ServiceJava(File serviceFile) {
		super(serviceFile);
		init();
	}

	public ServiceJava(File serviceFile, List<File> linkedFiles) {
		super(serviceFile, linkedFiles);
		init();
	}

	private void init() {
		try {
			URL serviceFilesURL = new URL("jar", "", serviceFile.toURI().toURL() + "!/");
			JarURLConnection jarURLConnection = (JarURLConnection) serviceFilesURL.openConnection();
			Attributes attr = jarURLConnection.getMainAttributes();
			if (attr != null) {
				logger.info("Service: " + serviceFile.getName() + "\n Main class:"
						+ attr.getValue(Attributes.Name.MAIN_CLASS));
			} else {
				logger.error("No entry point in: \"" + serviceFile.getName() + "\"");
			}
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { serviceFile.toURI().toURL() },
					this.getClass().getClassLoader());
			entryClass = Class.forName(attr.getValue(Attributes.Name.MAIN_CLASS), true, urlClassLoader);
			addURLToClassLoader(serviceFile.toURI().toURL());
		} catch (Exception e) {
			logger.error("Service file" + serviceFile.getName() + "could not be loaded.");
			logger.debug("Logged Exception", e);
		}
		List<String> resources = new ArrayList<>();
		resources.add("c");
		resources.add("C");
		resources.add("s");
		resources.add("S");
		resources.add("cpu");
		resources.add("CPU");
		resources.add("scpu");
		resources.add("SCPU");
		List<String> ids = new ArrayList<>();
		ids.addAll(getServiceIDs());
		List<String> information = new ArrayList<>();
		Map<String, List<String>> operations = getMethodsWithSignature(entryClass);
		ServiceMetaInformation meta = new ServiceMetaInformation(ids, information, operations, resources,
				Language.JAVA);
		setMetaInfos(meta);
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

	private Map<String, List<String>> getMethodsWithSignature(Class<?> classToGetMethodsFrom) {
		Map<String, List<String>> result = new HashMap<>();
		for (Method method : entryClass.getMethods()) {
			String methodName = method.getName();
			ArrayList<String> parameters = new ArrayList<>();
			for (Class<?> c : method.getParameterTypes()) {
				parameters.add(c.getName());
			}
			result.put(methodName, parameters);
		}
		return result;
	}

	public List<String> getMethodNames() {
		Method[] methods = entryClass.getMethods();
		List<String> result = new ArrayList<>();
		for (Method m : methods) {
			result.add(m.getName());
		}
		return result;
	}

	public List<String> getServiceIDs() {
		List<String> result = new ArrayList<>();
		result.add(entryClass.getName());
		result.add(serviceFile.getName());
		return result;
	}

	@Override
	public String newInstance(String name, Map<String, Object> params) {
		String key = "" + Objects.hash(name, params);
		if (instances.containsKey(key)) {
			logger.info("There is already a instance with the name: \"" + name + "\".");
			return key;
		}
		logger.info("newInstances \"sort\" is about to get called with name: " + name + " and the params: ");
		for (Entry<String, Object> e : params.entrySet())
			logger.info(e.getKey() + " --> " + e.getValue());
		Object[] inOrderObjects = ServiceUtil.sort(params);
		Constructor<?> constructor = null;
		Object newInstance = null;
		Class<?>[] inOrderClasses = ServiceUtil.getClasses(inOrderObjects);
		try {
			constructor = entryClass.getConstructor(inOrderClasses);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error(
					"The demanded constructor does not seem to exist for the class: \"" + entryClass.getName() + "\".");
			logger.debug("Logging Exception", e);
			return null;
		}
		try {
			newInstance = constructor.newInstance(inOrderObjects);
			newInstance = entryClass.cast(newInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("The constructor failed to instantiate an Object of the class:\"" + entryClass + "\"");
			logger.debug("Logging Exception", e);
		}
		ServiceInstanceJava instance = new ServiceInstanceJava(newInstance, this);
		instances.put(key, instance);
		return key;
	}

	@Override
	public void serializeInstance(String key) {
		ServiceInstanceJava instanceToSerialize = instances.get(key);
		if (instanceToSerialize == null) {
			logger.info("There is no instance with the key: \"" + key + "\"");
			return;
		}
		File outputFile = new File(SERILALIZATION_DIR + key + SERILALIZATION_SUFFIX);
		if (outputFile.exists()) {
			logger.info("File:\"" + outputFile + "\" already exists!");
		} else {
			writeObjectToFile(outputFile, instanceToSerialize);
		}
	}

	private void writeObjectToFile(File outputFile, ServiceInstance instanceToSerialize) {
		FileOutputStream fileOutStream;
		try {
			fileOutStream = new FileOutputStream(outputFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOutStream);
			objectOut.writeObject(instanceToSerialize);
			fileOutStream.flush();
			objectOut.flush();
			fileOutStream.close();
			objectOut.close();
		} catch (Exception e) {
			logger.error("Something went wrong while serializing the instance.");
			logger.debug("Logging Exception", e);
		}
	}

	@Override
	public void startSerializedInstance(String key) {
		File inputFile = new File(SERILALIZATION_DIR + key + SERILALIZATION_SUFFIX);
		if (!inputFile.exists()) {
			logger.info("There is no serialized instance with the key: \"" + key + "\"");
			return;
		}
		ServiceInstanceJava javaInstance = readJavaInstanceFromFile(inputFile);
		instances.put(key, javaInstance);
		logger.info("Instance successfully recovered");
	}

	private ServiceInstanceJava readJavaInstanceFromFile(File inputFile) {
		FileInputStream fileInStream = null;
		ObjectInputStream in = null;
		ServiceInstanceJava javaInstance = null;
		try {
			fileInStream = new FileInputStream(inputFile);
			in = new ObjectInputStream(fileInStream);
			Object instance = in.readObject();
			in.close();
			fileInStream.close();
			if (!(instance instanceof ServiceInstanceJava)) {
				throw new ClassCastException(
						"File: \"" + inputFile.getAbsolutePath() + "\" is no instance of a ServiceInstanceJava.");
			}
			javaInstance = (ServiceInstanceJava) instance;
			javaInstance.setService(this);
		} catch (ClassNotFoundException e) {
			logger.error("The class of the object that was being deserilized could not be determined.");
		} catch (Exception e) {
			logger.debug("Logging Exception", e);
		}
		return javaInstance;
	}

	@Override
	public void killInstance(String key) {
		instances.remove(key);

	}

	@Override
	public ServiceInstanceJava getInstance(String key) {
		return instances.get(key);
	}
}
