package de.upb.sede.composition.graphs;

import java.util.Objects;

import de.upb.sede.composition.graphconstructioninformation.ClassesConfig;
import de.upb.sede.composition.graphconstructioninformation.InputsFieldTypes;
import de.upb.sede.composition.graphconstructioninformation.ResolvePolicy;

/**
 * This class contains all the logic to construct a graph.
 * 
 * @author aminfaez
 *
 */
public class GraphConstructor {
	
	private final Graph constructingGraph;
	
	private final Graph orderOfInstructionGraph;
	
	private final ResolvePolicy resolvePolicy;
	
	private final ClassesConfig classConfig;
	
	private final InputsFieldTypes inputFieldTypes;
	
	private InstructionNode lastInstruction;
	
	
	
	
	/**
	 * Constructor for a brand new graph.
	 */
	public GraphConstructor(ResolvePolicy resolvePolicy, ClassesConfig classConfig, InputsFieldTypes inputFieldTypes) {
		this.constructingGraph = new Graph();
		this.orderOfInstructionGraph = new Graph();
		this.resolvePolicy = resolvePolicy;
		this.classConfig = classConfig;
		this.inputFieldTypes = inputFieldTypes;
	}
	
	/**
	 *  Adds node to the graph.
	 *  The order in which instruction nodes are added to the graph matters!!
	 */
	public void addInstructionNode(InstructionNode instNode) {
		Objects.requireNonNull(instNode);
		constructingGraph.addNode(instNode);
		orderOfInstructionGraph.addNode(instNode);
		if(lastInstruction != null) {
			/*
			 * this isn't the first instruction node added.
			 */
			orderOfInstructionGraph.connectNodes(lastInstruction, instNode);
		}
		lastInstruction = instNode;
	}
	
	
	private void resolveContext(InstructionNode instNode) {
		Objects.requireNonNull(instNode);
	}
	
	/**
	 * 
	 */
	public void resolveDataFlowDependency() {
	}
	
}
