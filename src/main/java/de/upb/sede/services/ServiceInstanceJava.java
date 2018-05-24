package de.upb.sede.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceInstanceJava extends ServiceInstance {

	private static final long serialVersionUID = 4273811435753528453L;
	private static final Logger logger = LogManager.getLogger(ServiceInstanceJava.class);
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
			logger.error("The method: \"" + opName + "\" could not be found for the parameters: " + params + ".");
			logger.debug("Logging Exception", e);
			return null;
		}
		try {
			List<Object> result = new ArrayList<>();
			result.add(invokeMethod.invoke(instance, sortedParams));
			return result;
		} catch (Exception e) {
			logger.error("The invocation of the method: \"" + invokeMethod.getName() + "\" failed.");
			logger.debug("Logging Exception", e);
			return null;
		}
	}

	@Override
	public String getState() throws NotSerializableException {
		if (instance instanceof Serializable) {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(byteOut);
				out.writeObject(instance);
				out.close();
				byteOut.close();
			} catch (IOException e) {
				logger.error("Something went wrong while the object was serialized.");
				logger.debug("Logging Exception", e);
				return null;
			}
			return byteOut.toString();
		} else {
			logger.error("Instances of the Service" + service.getServiceIDs().get(0) + " are not Serializable.");
			throw new NotSerializableException();
		}
	}

	public Serializable getInstance() throws NotSerializableException {
		if (instance instanceof Serializable) {
			return (Serializable) instance;
		} else {
			logger.error("Instances of the Service " + service.getServiceIDs().get(0) + " are not Serialiazable.");
			throw new NotSerializableException();
		}
	}
}
