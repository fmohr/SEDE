package de.upb.sede.composition.graphs;

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
     * Expands itself by adding nodes and edges to the graph to resolve its pre and post actions.
     */
    abstract void expand(GraphComposition graph, ClassesConfig configuration, ResolvePolicy policy);
    
    

}