package de.upb.sede.services;

import java.util.Map;

/**
 * Some utilities that are used in the Services and ServiceInstances likewise.
 * 
 * @author Manuel Scheibl
 *
 */
public class ServiceUtil {
	public static final String LAMPDA_METHOD_NAME = "process";
	public static final String CONSTRUCT_FLAG = "C#";

	/**
	 * Sort the given parameters in an Array by their names which must at 0 (without
	 * any gaps). This is needed because the Map does not make any assurances about
	 * the order of the entries. For a java method to be called the order is
	 * crucial.
	 * 
	 * @param parameter
	 *            The parameters to be sorted.
	 * @return An array of Objects that are the values of the given Map sorted by
	 *         the keys of the map.
	 */
	public static Object[] sort(Map<String, Object> parameter) {
		if (parameter == null || parameter.size() == 0) {
			return new Object[0];
		}
		Object[] inOrderObjects = new Object[parameter.size()];
		for (int i = 0; i < parameter.size(); i++) {
			Object next = null;
			try {
				next = parameter.get("" + i);
				if (next == null)
					throw new NullPointerException();
			} catch (NullPointerException e) {
				System.err.println(
						"The parameters aren't numbered correctly. Make sure the parameters are named by 0 to [Number of parameters]-1.");
				e.printStackTrace();
				return null;
			}
			inOrderObjects[i] = next;
		}
		return inOrderObjects;
	}

	/**
	 * Returns an array of the Classes of the given Objects in the same order as
	 * given.
	 * 
	 * @param objects
	 *            The objects to return an array of their classes from.
	 * @return An array of the classes of the given objects (order is preserved).
	 */
	public static Class<?>[] getClasses(Object[] objects) {
		Class<?>[] inOrderClasses = new Class[objects.length];
		for (int i = 0; i < objects.length; i++) {
			inOrderClasses[i] = objects[i].getClass();
		}
		return inOrderClasses;
	}

	public static String getOpName(String calledServiceOp) {
		if (calledServiceOp.contains("."))
			return calledServiceOp.substring(calledServiceOp.lastIndexOf(".") + 1);
		else
			return LAMPDA_METHOD_NAME;
	}
}
