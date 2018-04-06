package de.upb.sede.composition.graphs;

import java.util.Collection;

import de.upb.sede.composition.graphconstructioninformation.ClassesConfig;

public abstract class BaseNode {

    public BaseNode(){

    }
    
    /**
     * Returns true if this node makes the given fieldname available after it's done on the executor or changes its state.
     */
    abstract boolean producesField(String fieldname, ClassesConfig configuration);

    
    /**
     * Returns Collection of fieldnames which this node is depending on being resolved before its execution starts.
     */
    abstract Collection<String> consumingFields();
    

}