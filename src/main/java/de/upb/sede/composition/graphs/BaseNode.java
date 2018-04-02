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
    
    /**
     * Iterates all nodes to find a producer for the given fieldname.
     * Always takes the last node from the nodes list which produces the fieldname.
     * Returns null if there is no node in this graph that produces the given fieldname.
     */
    final BaseNode getLastChanger(GraphComposition graph, String fieldname, ClassesConfig configuration) {
    		// TODO data flow dependency !!!
    		BaseNode lastNode = null;
    		List<BaseNode> nodes = graph.getNodes();
    		for(BaseNode node : nodes) {
    			if(node.changesState(fieldname, configuration)) {
    				lastNode = node;
    			}
    			if(node == this) {
    				break; /* dont go over this method */ 
    			}
    		}
    		return lastNode;
    }

}