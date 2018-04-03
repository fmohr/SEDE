package de.upb.sede.composition.graphs;

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
	
	/**
	 * Constructor for a brand new graph.
	 */
	public GraphConstructor(ResolvePolicy resolvePolicy, ClassesConfig classConfig) {
		this.constructingGraph = new GraphComposition();
		this.resolvePolicy = resolvePolicy;
		this.classConfig = classConfig;
	}
	
	public void addInstruction(InstructionNode instNode) {
	}
}
