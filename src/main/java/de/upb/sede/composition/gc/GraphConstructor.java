package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import de.upb.sede.composition.graphs.Graph;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.config.ClassesConfig;

/**
 * This class contains all the logic to construct a graph.
 * 
 * @author aminfaez
 *
 */
public class GraphConstructor {

	private List<InstructionNode> nodes;

	private final List<Graph> graphs;

	private final Graph orderOfInstructionGraph;

	private final ResolveInformation resolveInfo;



	private InstructionNode lastInstruction;

	/**
	 */
	public GraphConstructor(ResolveInformation resolveInfo) {
		this.resolveInfo = resolveInfo;
		
		this.nodes = new ArrayList<>();
		this.graphs = new ArrayList<>();
		this.orderOfInstructionGraph = new Graph();
	}

	/**
	 * Adds node to the graph. The order in which instruction nodes are added to the
	 * graph matters!!
	 */
	public void addInstructionNode(InstructionNode instNode) {
		Objects.requireNonNull(instNode);
		nodes.add(instNode);
	}

	public void createOrderedInstructionGraph() {
		BaseNode lastNode = null;
		for (Iterator<InstructionNode> it = nodes.iterator(); it.hasNext();) {
			BaseNode currentNode = it.next();
			orderOfInstructionGraph.addNode(currentNode);
			if (lastInstruction != null) {
				/*
				 * this isn't the first instruction node added.
				 */
				orderOfInstructionGraph.connectNodes(lastInstruction, currentNode);
			}
		}
	}
	
	public void devideHosts() {
		
	}
	

	private void resolveContext(InstructionNode instNode) {

	}

	/**
	 * 
	 */
	public void resolveDataFlowDependency() {
	}

}
