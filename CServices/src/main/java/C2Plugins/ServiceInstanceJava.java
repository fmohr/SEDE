package C2Plugins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceInstanceJava extends ServiceInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	volatile ServiceJava service;
	Object instance;

	ServiceInstanceJava(Object newInstance, ServiceJava service) {
		this.service = service;
		instance = newInstance;
	}

	public void setService(ServiceJava service) {
		this.service = service;
	}

	@Override
	public List<Object> invokeOp(String opName, Map<String, Object> params) {
		Object[] sortedParams = ServiceUtil.sort(params);
		Class<?>[] inputClasses = ServiceUtil.getClasses(sortedParams);
		Method invokeMethod = null;
		try {
			invokeMethod = instance.getClass().getMethod(opName, inputClasses);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("The method: \"" + opName + "\" could not be found for the parameters: " + params + ".");
			e.printStackTrace();
			return null;
		}
		try {
			List<Object> result = new ArrayList<>();
			result.add(invokeMethod.invoke(instance, sortedParams));
			return result;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println("The invocation of the method: \"" + invokeMethod.getName() + "\" failed.");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getState() throws NotSerializableException {
		if (!(instance instanceof Serializable)) {
			System.err.print("Instances of the Service \"");
			for (String id : service.getServiceIDs())
				System.err.print(id + " | ");
			System.err.println("\" do not imeplement the interface \"Serializable\"!");
			throw new NotSerializableException();
		}
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(instance);
			out.close();
			byteOut.close();
		} catch (IOException e) {
			System.err.println("Something went wrong while the object was serialized.");
			e.printStackTrace();
			return null;
		}
		return byteOut.toString();
	}

	public Serializable getInstance() throws NotSerializableException {
		if (!(instance instanceof Serializable)) {
			System.err.print("Instances of the Service \"");
			for (String id : service.getServiceIDs())
				System.err.print(id + " | ");
			System.err.println("\" do not implement the interface \"Serializable\"!");
			throw new NotSerializableException();
		}
		return (Serializable) instance;
	}
}
