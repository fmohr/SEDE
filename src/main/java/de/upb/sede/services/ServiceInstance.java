package de.upb.sede.services;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

public abstract class ServiceInstance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3483357164394870660L;
	String name;
	Map<String, Object> params;

	public void setName(String name) {
		this.name = name;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * invokes an operation with the given parameters.
	 * 
	 * @param opName
	 *            Operation to invoke.
	 * @param params
	 *            Parameters to call the operation with.
	 * @return Return value of the operation.
	 */
	public abstract List<Object> invokeOp(String opName, Map<String, Object> params);

	/**
	 * Returns a string representation of the instance (serialization).
	 * 
	 * @return The serialization of the instance.
	 * @throws NotSerializableException
	 *             If this service does not provide serialization.
	 * @throws SerialException
	 *             Something went wrong in the process of serializing.
	 */
	public abstract String getState() throws NotSerializableException;

	/**
	 * Recovers the state of a serialized instance (the given String is the
	 * serialization of an instance).
	 * 
	 * @param serialization
	 *            The serialization of a service instance.
	 */
	// abstract void recoverState(String serialization);
}
