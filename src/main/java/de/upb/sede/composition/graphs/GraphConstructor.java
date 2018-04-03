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
	
	private final GraphComposition constructingGraph;
	
	private final ResolvePolicy resolvePolicy;
	
	private final ClassesConfig classConfig;
	
	private final List<InstructionNode> unresolvedInstructionNodes = new ArrayList<>();
	
	/**
	 * Constructor for a brand new graph.
	 */
	public GraphConstructor(ResolvePolicy resolvePolicy, ClassesConfig classConfig) {
		this.constructingGraph = new GraphComposition();
		this.resolvePolicy = resolvePolicy;
		this.classConfig = classConfig;
	}
	
	/**
	 *  Adds node to the graph.
	 */
	public void addInstructionNode(InstructionNode instNode) {
		Objects.requireNonNull(instNode);
		unresolvedInstructionNodesv.add((InstructionNode) instNode);
		constructingGraph.addNode(instNode);
	}
	
	/**
	 * 
	 */
	public void resolveDataFlowDependency() {
		for(InstructionNode instruction : instructionNodes) {
			
		}
	}
	
}
