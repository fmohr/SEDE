package de.upb.sede.services;

import java.util.List;
import java.util.Map;

public class ServiceInstancePython extends ServiceInstance {
	private static final long serialVersionUID = -8414616815309991520L;
	ServicePython service;

	ServiceInstancePython(ServicePython service) {
		this.service = service;
	}

	@Override
	public List<Object> invokeOp(String opName, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public void recoverState(String serialization) {
	// // TODO Auto-generated method stub
	//
	// }

}
