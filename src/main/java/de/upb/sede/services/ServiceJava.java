package de.upb.sede.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.jar.Attributes;

public class ServiceJava extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4948661597034811141L;
	private volatile Map<String, ServiceInstanceJava> instances = new HashMap<>();
	private Class<?> entryClass = null;
	private final String serializationDir = "../../serializations/";

	/**
	 * This is a ugly way to add a class to the SystemClassLoader on runtime via
	 * reflection. But adding the jar classes to it is mandatory to prevent a
	 * ClassNotFoundException on deserialization.
	 * 
	 * @param urls
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void addURLs(URL[] urls) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		if (cl instanceof URLClassLoader) {
			URLClassLoader ul = (URLClassLoader) cl;
			// addURL is a protected method, but we can use reflection to call it
			Class<?>[] paraTypes = new Class[1];
			paraTypes[0] = URL.class;
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", paraTypes);
			// change access to true, otherwise, it will throw exception
			method.setAccessible(true);
			Object[] args = new Object[1];
			for (int i = 0; i < urls.length; i++) {
				args[0] = urls[i];
				method.invoke(ul, args);
			}
		} else {
			System.err.println("SystemClassLoader is not URLClassLoader...");
		}
	}

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
			URL u = new URL("jar", "", serviceFile.toURI().toURL() + "!/");
			JarURLConnection jarURLConn = (JarURLConnection) u.openConnection();
			Attributes attr = jarURLConn.getMainAttributes();
			if (attr != null)
				System.out.println("Entry point of \"" + serviceFile.getName() + "\" : "
						+ attr.getValue(Attributes.Name.MAIN_CLASS));
			else
				System.err.println("No entry point in: \"" + serviceFile.getName() + "\"");
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { serviceFile.toURI().toURL() },
					this.getClass().getClassLoader());
			entryClass = Class.forName(attr.getValue(Attributes.Name.MAIN_CLASS), true, urlClassLoader);
			addURLs(new URL[] { serviceFile.toURI().toURL() });
		} catch (Exception e) {
			System.err.println("Service file could not be loaded! \nService file null? : " + serviceFile == null
					+ "\nService files name: " + serviceFile.getName());
			e.printStackTrace();
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
		Map<String, List<String>> operations = new HashMap<>();
		for (Method method : entryClass.getMethods()) {
			String methodName = method.getName();
			ArrayList<String> parameters = new ArrayList<>();
			for (Class<?> c : method.getParameterTypes()) {
				parameters.add(c.getName());
			}
			operations.put(methodName, parameters);
		}
		Language language = Language.JAVA;
		ServiceMetaInformation meta = new ServiceMetaInformation(ids, information, operations, resources, language);
		setMetaInfos(meta);
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

	public Class<?> getEntryClass() {
		return entryClass;
	}

	@Override
	public String newInstance(String name, Map<String, Object> params) {
		String key = ServiceManager.getNameSpace() + "|" + name;
		if (instances.containsKey(key)) {
			System.out.println("There is already a instance with the name: \"" + name + "\" in the namespace: \""
					+ ServiceManager.getNameSpace() + "\".");
			return key;
		}
		System.out.println("newInstances \"sort\" is about to get called with name: " + name + " and the params: ");
		for (Entry<String, Object> e : params.entrySet())
			System.out.println(e.getKey() + " --> " + e.getValue());
		Object[] inOrderObjects = ServiceUtil.sort(params);
		Constructor<?> constructor = null;
		Object newInstance = null;
		Class<?>[] inOrderClasses = ServiceUtil.getClasses(inOrderObjects);
		try {
			constructor = entryClass.getConstructor(inOrderClasses);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println(
					"The demanded constructor does not seem to exist for the class: \"" + entryClass.getName() + "\".");
			e.printStackTrace();
			return null;
		}
		try {
			newInstance = constructor.newInstance(inOrderObjects);
			newInstance = entryClass.cast(newInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("The constructor failed to instantiate an Object of the class:\"" + entryClass + "\"");
			e.printStackTrace();
		}
		ServiceInstanceJava instance = new ServiceInstanceJava(newInstance, this);
		instances.put(key, instance);
		return key;
	}

	@Override
	public void serializeInstance(String key) {
		ServiceInstanceJava instanceToSerialize = instances.get(key);
		if (instanceToSerialize == null) {
			System.out.println("There is no instance with the key: \"" + key + "\"");
			return;
		}
		File outputFile = new File(serializationDir + key + ".ser");
		if (outputFile.exists()) {
			System.out.println("File:\"" + outputFile + "\" already exists!");
			return;
		}
		FileOutputStream fileOutStream;
		try {
			fileOutStream = new FileOutputStream(outputFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOutStream);
			objectOut.writeObject(instanceToSerialize);
			fileOutStream.flush();
			objectOut.flush();
			fileOutStream.close();
			objectOut.close();
		} catch (FileNotFoundException e) {
			System.err.println("Something went wrong while serializing the instance.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Something went wrong while serializing the instance.");
			e.printStackTrace();
		}
	}

	@Override
	public void startSerializedInstance(String key) {
		File inputFile = new File(serializationDir + key + ".ser");
		if (!inputFile.exists()) {
			System.out.println("There is no serialized instance with the key: \"" + key + "\"");
			return;
		}
		try {
			FileInputStream fileInStream = new FileInputStream(inputFile);
			ObjectInputStream in = null;
			Object instance = null;
			try {
				in = new ObjectInputStream(fileInStream);
				instance = in.readObject();
				in.close();
				fileInStream.close();
				if (!(instance instanceof ServiceInstanceJava)) {
					System.err.println("The object that is saved with the key:\"" + key
							+ "\" is no instance of a ServiceInstanceJava.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Something went wrong while the object was deserilaized.");
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				System.err.println("The class of the object that was being deserilized could not be determined.");
				e.printStackTrace();
				in.close();
				return;
			}
			((ServiceInstanceJava) instance).setService(this);
			instances.put(key, (ServiceInstanceJava) instance);
			System.out.println("Instance successfully recovered");
		} catch (IOException e) {
			System.err.println("There was a problem with the inputstream while deserializing.");
			e.printStackTrace();
		}
	}

	@Override
	public void killInstance(String key) {
		// TODO This is not really enough to kill a instance.
		instances.remove(key);

	}

	@Override
	public ServiceInstanceJava getInstance(String key) {
		return instances.get(key);
	}
}
