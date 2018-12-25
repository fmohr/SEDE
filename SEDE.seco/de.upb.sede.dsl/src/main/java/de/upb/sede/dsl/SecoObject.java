package de.upb.sede.dsl;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Internal;

public class SecoObject extends MinimalEObjectImpl.Container{
	
	@Override
	public String toString() {
		return SecoUtil.serializeSilently(this);
	}
	
	public void setRes(Resource res) {
		eSetDirectResource((Internal) res);
	}
	
}
