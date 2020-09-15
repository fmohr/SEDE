package ai.services.composition

import ai.services.composition.choerography.emulation.executors.ExecutionGraph
import ai.services.composition.choerography.emulation.executors.GraphTraversal
import ai.services.composition.graphs.nodes.BaseNode
import ai.services.composition.graphs.nodes.ICompositionGraph
import ai.services.composition.graphs.nodes.IInstructionNode

class RRTestHelpers {

    static void assertExecutedBefore(ExecutionGraph graph, BaseNode expectedFirstNode, long instIndex) {
        def inst = graph.nodes.find {
            it instanceof IInstructionNode &&
                it.index == instIndex

        } as IInstructionNode
        assert inst != null
        assert GraphTraversal.isTherePathFromTo(graph, expectedFirstNode, inst)
        assertExecutedBefore(graph, expectedFirstNode, inst)
    }

    static void assertExecutedAfter(ExecutionGraph graph, BaseNode expectedLastNode, long instIndex) {
        def inst = graph.nodes.find {
            it instanceof IInstructionNode &&
                it.index == instIndex

        } as IInstructionNode
        assert inst != null
        assertExecutedBefore(graph, inst, expectedLastNode)
    }

    static void assertExecutedBefore(ExecutionGraph graph, BaseNode expectedFirstNode, BaseNode expectedLast) {
        assert GraphTraversal.isTherePathFromTo(graph, expectedFirstNode, expectedLast)
    }

    static void assertExecutedBefore(ICompositionGraph graph, BaseNode expectedFirstNode, BaseNode expectedLast) {
        assertExecutedBefore(new ExecutionGraph(graph), expectedFirstNode, expectedLast)
    }

}
