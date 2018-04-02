package de.upb.sede.composition.graphs;

import de.upb.sede.config.ClassesConfig;

public abstract class BaseNode {

    public BaseNode(){

    }
    /**
     * Returns true if this node makes the given fieldname available after it's done on the executor.
     */
    abstract boolean produces(String fieldname);

    
    abstract void expand(GraphComposition graph);
}