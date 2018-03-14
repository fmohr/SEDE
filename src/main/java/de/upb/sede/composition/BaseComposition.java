package de.upb.sede.composition;

import java.util.HashMap;

public abstract class BaseComposition extends HashMap<String, Operation> {
    	/**
	 * Increment this when incompatible changes to the class occurs.
	 */
	private static final long serialVersionUID = 1;
	
	public abstract String startPoint();
	
	

}