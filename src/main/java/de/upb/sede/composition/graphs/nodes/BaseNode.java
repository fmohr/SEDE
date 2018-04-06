package de.upb.sede.composition.graphs.nodes;

import java.util.Collection;

import de.upb.sede.composition.gc.ResolveInformation;

public abstract class BaseNode {

    public BaseNode(){

    }
    
    /**
     * Returns true if this node makes the given fieldname available after it's done on the executor or changes its state.
     */
    public abstract boolean producesField(String fieldname, ResolveInformation resolveInfo);

    
    /**
     * Returns Collection of fieldnames which this node is depending on being resolved before its execution starts.
     * @param resolveInfo TODO
     */
    public abstract Collection<String> consumingFields(ResolveInformation resolveInfo);
    
    /**
     * Returns Collection of fieldnames which this node is depending on being resolved before its execution starts.
     */
    public abstract Collection<String> producingFields(ResolveInformation resolveInfo);

    /**
     * Returns true if the object has the same pointer.
     * Signed final so derived classes can't override the functionality of equals.
     */
    public final boolean equals(Object otherObject) {
    	return super.equals(otherObject);
    }
    
    /**
     * Signed final so derived classes can't override the functionality of hashCode.
     */
    public final int hashCode() {
    	return super.hashCode();
    }
}