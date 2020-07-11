package de.upb.sede.composition

import de.upb.sede.composition.choerography.emulation.executors.ExecutionGraph
import de.upb.sede.composition.choerography.emulation.executors.GraphTraversal
import de.upb.sede.composition.graphs.nodes.BaseNode
import de.upb.sede.composition.graphs.nodes.IInstructionNode

class RRTestHelpers {

    public static void assertExecutedBefore(ExecutionGraph graph, BaseNode expectedFirstNode, long instIndex) {
        def inst = graph.nodes.find {
            it instanceof IInstructionNode &&
                it.index == instIndex

        } as IInstructionNode
        assert inst != null
        assert GraphTraversal.isTherePathFromTo(graph, expectedFirstNode, inst)
    }

    public static void assertExecutedBefore(ExecutionGraph graph, BaseNode expectedFirstNode, BaseNode expectedLast) {
        assert GraphTraversal.isTherePathFromTo(graph, expectedFirstNode, expectedLast)
    }

}
