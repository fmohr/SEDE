package de.upb.sede.composition.graphs;

import java.util.Collection;
import java.util.List;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ResolvePolicy;

public abstract class BaseNode {

    public BaseNode(){

    }
    
    /**
     * Returns true if this node makes the given fieldname available after it's done on the executor or changes its state.
     */
    abstract boolean changesState(String fieldname, ClassesConfig configuration);

    
    /**
     * 
     * @return List of fieldnames this node is depending on being resolved before its execution.
     */
    abstract Collection<String> dependsOnFields();
    

}