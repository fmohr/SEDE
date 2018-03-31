package de.upb.sede.composition;

import java.util.Objects;

/**
 * 2-Tupel of BaseNode. 
 * Represents a directed edge in GraphComposition.
 * 
 * Immutable
 */
public final class Edge{
    private final BaseNode from;
    private final BaseNode to;

    /**
     * Standard constructor creating an 2-tupel edge. 
     * Null values will throw an exception.
     */
    public Edge(BaseNode fromNode, BaseNode toNode){
        this.from = Objects.requireNonNull(fromNode);
        this.to = Objects.requireNonNull(toNode);
    }

    /**
     * Returns the source node.
     */
    public BaseNode getFrom(){
        return from;
    }

    /**
     * Returns the tage node.
     */
    public BaseNode getTo(){
        return to;
    }

    public boolean equals(Object anotherObject){
        if(anotherObject instanceof Edge){
            Edge otherEdge = (Edge) anotherObject;
            return getFrom().equals(otherEdge.getFrom()) && getTo().equals(otherEdge.getTo());
        } else {
            return false;
        }
    }

    public int hashCode(){
        /* from and to can't be null */
        return Objects.hash(getFrom(), getTo());
    }

    /**
     * Returns true if either source or target is equal to the given node.
     */
	public boolean contains(BaseNode nodeToRemove) {
		if(nodeToRemove == null){
            /* from and to can't be null */
            return false;
        } else {
            return getFrom().equals(nodeToRemove) || getTo().equals(nodeToRemove);
        }
	}
}