package de.upb.sede.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceInstanceC extends ServiceInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4096120753944546519L;
	ServiceC service;

	ServiceInstanceC(ServiceC service) {
		this.service = service;
	}

	@Override
	public List<Object> invokeOp(String opName, Map<String, Object> params) {
		// Method[] methods = this.getClass().getMethods();
		// for(Method method : methods) {
		// int modifiers = method.getModifiers();
		// boolean isNative = Modifier.isNative(modifiers);
		// }
		if (opName != "process") {
			System.err.println("Other invocations besides \"process\" are not supported.");
			return null;
		}
		List<Double> parameter = null;
		List<Image> images = null;
		Character resource = null;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.startsWith("params")) {
				if ((value instanceof List<?>)) {
					List<Object> list = (List<Object>) value;
					for (Object listEntry : list) {
						if (!(listEntry instanceof Double)) {
							System.err.println("The params for the CService \"" + service.getServiceFile().getName()
									+ "\" need to be a List<Double>");
							return null;
						}
					}
				}
				parameter = (List<Double>) value;
			} else if (key.startsWith("images")) {
				if ((value instanceof List<?>)) {
					List<Object> list = (List<Object>) value;
					for (Object listEntry : list) {
						if (!(listEntry instanceof Image)) {
							System.err.println("The images for the CService \"" + service.getServiceFile().getName()
									+ "\" need to be a List<Image>");
							return null;
						}
					}
				}
				images = (List<Image>) value;
			} else if (key.startsWith("resource")) {
				if (!(value instanceof Character)) {
					System.err.println("The images for the CService \"" + service.getServiceFile().getName()
							+ "\" need to be a List<Image>");
				}
				resource = (Character) value;
			}
		}
		List<Object> returnList = new ArrayList<>();
		Object result = service.process(resource, parameter, images);
		if (result instanceof List<?>) {
			List<?> list = (List<?>) result;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof Object) {
					returnList.add(list.get(i));
				} else {
					System.err.println("Service: " + service.getServiceFile().getName()
							+ " produced outputs that are not Objects!");
				}
			}
		} else if (result instanceof Object) {
			returnList.add(result);
		} else {
			System.err.println("Result of process-call by \"" + service.getServiceFile().getName()
					+ "\" isn't a List and not even an Object!");
		}
		return returnList;
	}

	@Override
	public String getState() {
		return "";
	}

}
