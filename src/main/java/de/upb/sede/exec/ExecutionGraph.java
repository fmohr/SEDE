package de.upb.sede.exec;

public interface ExecutionGraph extends Iterable<Node>{
	public Node getNextNode();
}
