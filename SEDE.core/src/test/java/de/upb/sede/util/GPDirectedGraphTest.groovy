package de.upb.sede.util

import spock.lang.Specification

import java.util.function.BiPredicate
import java.util.function.Function

class GPDirectedGraphTest extends Specification {

    final def makeGraph = { nodes, edges ->
        new GPDirectedGraph(nodes, edges as Function)
    }

    def sorted
    final def index = { sorted.indexOf(it) }

//    GPDirectedGraph<String, String>

    GPDirectedGraph<String, String> dag
    GPDirectedGraph<String, String> dagDisconnected
    GPDirectedGraph<String, String> cyclicGraph
    GPDirectedGraph<String, String> cyclicGraph1


    /**
     * Constructing graphs that are used inside the tests.
     */
    def setup() {
        sorted = []
        def nodes
        def edges

        // SIMPLE DAG
        nodes = ["A", "B", "C", "D"]
        edges = {
            switch(it) {
                case "A":
                    ["D"]
                    break
                case "B":
                    ["D", "C"]
                    break
                case "C":
                    ["A"]
                    break
                default:
                    []
                    break
            }
        }
        dag = makeGraph(nodes, edges)

        // DISCONNECTED DAG
        nodes = ["A", "B", "C", "D", "_E", "_F", "_G"]
        edges = {
            switch(it) {
                case "A":
                    ["B"]
                    break
                case "B":
                    ["D", "C"]
                    break
                case "_E":
                    ["_F", "_G"]
                    break
                default:
                    []
                    break
            }
        }
        dagDisconnected = makeGraph(nodes, edges)

        // CYCLIC GRAPH  A -> B -> D -> A
        nodes = ["A", "B", "C", "D"]
        edges = {
            switch(it) {
                case "A":
                    ["B"]
                    break
                case "B":
                    ["C", "D"]
                    break
                case "C":
                    ["D"]
                    break
                case "D":
                    ["A"]
                    break
                default:
                    []
                    break
            }
        } as Function
        cyclicGraph = new GPDirectedGraph<String, String>(nodes, edges)

        edges = {
            switch(it) {
                case "A":
                    ["B"]
                    break
                case "B":
                    ["D", "B"]
                    break
                case "C":
                    ["D"]
                    break
                default:
                    []
                    break
            }
        } as Function
        cyclicGraph1 = new GPDirectedGraph<String, String>(nodes, edges)
    }

    def "test topological sort"() {
        when: "Constructing GP Directed Graph and performing topological sorting"
        sorted = dag.topologicalSort()

        then:
        sorted == ["B", "C", "A", "D"]

        when: "Disconnected graph"
        sorted = dagDisconnected.topologicalSort()

        then:
        index("A") < index("B")
        index("B") < index("C")
        index("B") < index("D")

        index("_E") < index("_F")
        index("_E") < index("_G")
    }

    def "test topological sort with cycle error"() {
        given: "Graph that contains a cycle:"
        cyclicGraph

        when: "Performing topological sort"
        cyclicGraph.topologicalSort()

        then: "Cyclic graph exception is thrown"
        thrown(GPDirectedGraph.CyclicGraphException)
    }

    def "test positive cyclic graph check"() {
        given: "Graph that contains a cycle: B -> D -> C -> B"
        cyclicGraph

        when: "Checking for cycles"
        def hasCycles = ! cyclicGraph.isDAG()

        then: "Positive result"
        hasCycles

        when: "Node points to itself: B -> B"
        hasCycles = ! cyclicGraph1.isDAG()

        then: "Graph has cycle"
        hasCycles

    }

    def "test negative cyclic graph check"() {
        given: "Acyclic Graph"
        dag

        when: "Checking for cycles"
        def hasCycles = ! dag.isDAG()

        then: "Negative result"
        ! hasCycles

        when: "Disconnected Acyclic Graph and checking for cycles"
        hasCycles = ! dagDisconnected.isDAG()

        then: "Negative result"
        ! hasCycles
    }


    def "test predicate"() {
        given: "Some NodeLike instances forming a simple DAG"
        def a = new NodeLike("A")
        def b = new NodeLike("B", a)
        def c = new NodeLike("C", a)
        def d = new NodeLike("D", c)
        def nodes = [a, b, c, d]
        when: "Creating the graph with an edge predicate and a key extractor"
        def keyExtractor = {
            NodeLike node -> node.name
        } as Function
        def hasEdge = {
            NodeLike node, NodeLike other -> other.otherNode == node
        }  as BiPredicate
        def graph = new GPDirectedGraph(keyExtractor, nodes, hasEdge)
        then:
        nodes.each {it.hashable = false} // disable hashes
        graph.isDAG()
        def sorted = graph.topologicalSort()
        nodes.each {it.hashable = true} // enable hashes again
        sorted == [a, b, c, d] ||
        sorted == [a, c, b, d] ||
        sorted == [a, c, d, b]
    }

    def "test cycle" () {
        given: "Graph that contains a cycle:"
        cyclicGraph

        when: "Calculating cycle"
        def cycle = cyclicGraph.cycle()

        then: "cycle is A -> B -> C -> D -> A"
        cycle == ["A", "B", "C", "D"]

        when: "Node points to itself: B -> B"
        cycle = cyclicGraph1.cycle()

        then: "Graph has '[B]' as a cycle"
        cycle == ["B"]

    }
}

/**
 * Node like objects that have a name and nullable pointer to another node instance.
 */
class NodeLike {
    String name
    NodeLike otherNode = null

    /**
     * Controls acces to {@link NodeLike#hashCode}.
     */
    boolean hashable = true

    NodeLike(String name, NodeLike otherNode) {
        this.name = name
        this.otherNode = otherNode
    }

    NodeLike(String name) {
        this.name = name
    }

    boolean equals(Object other) {
        if(other instanceof NodeLike) {
            other.name == this.name
        }
        false
    }

    int hashCode() {
        if(hashable)
            return super.hashCode()
        else
            throw new UnsupportedOperationException("Hash code is not allowed to be called.")
    }

    String toString() {
        name
    }
}