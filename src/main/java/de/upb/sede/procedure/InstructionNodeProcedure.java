package de.upb.sede.procedure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import org.apache.log4j.Logger;

import de.upb.sede.exec.Task;

public class InstructionNodeProcedure implements Procedure {
	static Logger logger = Logger.getLogger(InstructionNodeProcedure.class);

	@Override
	public Object process(Task task) {
		String method = (String) task.getParameters().get("method");
		boolean isConstructor = (boolean) task.getParameters().get("is-service-construction");
		String leftsidefieldname = (String) task.getParameters().get("leftsidefieldname");
		String host = (String) task.getParameters().get("host");
		String context = (String) task.getParameters().get("context");
		boolean isContextAFieldname = (boolean) task.getParameters().get("is-context-a-fieldname");
		List<String> parameters = (List<String>) task.getParameters().get("params");
		
		Class[] parameterClasses = getClassesForParameters(parameters);
		
		try {
			Class reflectedClass = loadClass(context);
			if(isConstructor) {
				Constructor constructor = reflectedClass.getConstructor(parameterClasses);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private Class[] getClassesForParameters(Map<String, Object> parameters) throws ClassNotFoundException {
		Class[] parameterClasses = new Class[parameters.size()];
		for(String className : parameters.keySet()) {
			Class parameterClass = Class.forName(className);
		}
		
	}

	private Class loadClass(String context) throws Exception {
		try {
			URL serviceFilesURL = new URL("jar", "", context + "!/");
			JarURLConnection jarURLConnection = (JarURLConnection) serviceFilesURL.openConnection();
			Attributes attr = jarURLConnection.getMainAttributes();
			if (attr != null) {
				logger.info("Servicefile: " + context + "\n Main class:" + attr.getValue(Attributes.Name.MAIN_CLASS));
			} else {
				logger.error("No entry point in: \"" + context + "\"");
			}
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { serviceFilesURL },
					this.getClass().getClassLoader());
			Class reflectedClass = Class.forName(context, true, urlClassLoader);
			addURLToClassLoader(serviceFilesURL);
			return reflectedClass;
		} catch (Exception e) {
			logger.error("Service file" + context + "could not be loaded.");
			throw e;
		}
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
}
