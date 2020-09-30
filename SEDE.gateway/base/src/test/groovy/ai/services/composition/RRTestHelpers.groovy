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

    static void sortInFlow(ExecutionGraph graph, List<BaseNode> nodes) {
        Collections.sort(nodes, {BaseNode n1, BaseNode n2 ->
            if(GraphTraversal.isTherePathFromTo(graph, n1, n2)) {
                return -1;
            } else if(GraphTraversal.isTherePathFromTo(graph, n2, n1)) {
                return 1;
            } else {
                return 0;
            }
        })
    }

    static void assertExecutedInOrder(ExecutionGraph graph, List<BaseNode> nodes) {
        for (i in 0..<nodes.size()-1) {
            assertExecutedBefore(graph, nodes[i], nodes[i+1])
        }
    }

}
