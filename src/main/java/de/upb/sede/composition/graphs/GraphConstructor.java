package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ResolvePolicy;

/**
 * This class contains all the logic to construct a graph.
 * 
 * @author aminfaez
 *
 */
public class GraphConstructor {
	
	private final Graph constructingGraph;
	
	private final Graph orderOfExectuion;
	
	private final ResolvePolicy resolvePolicy;
	
	private final ClassesConfig classConfig;
	
	
	/**
	 * Constructor for a brand new graph.
	 */
	public GraphConstructor(ResolvePolicy resolvePolicy, ClassesConfig classConfig) {
		this.constructingGraph = new Graph();
		this.orderOfExectuion = new Graph();
		this.resolvePolicy = resolvePolicy;
		this.classConfig = classConfig;
	}
	
	/**
	 *  Adds node to the graph.
	 */
	public void addInstructionNode(InstructionNode instNode) {
		Objects.requireNonNull(instNode);
//		unresolvedInstructionNodesv.add((InstructionNode) instNode);
		constructingGraph.addNode(instNode);
	}
	
	/**
	 * 
	 */
	public void resolveDataFlowDependency() {
	}
	
}
