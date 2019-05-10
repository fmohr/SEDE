package de.upb.sede.util

import spock.lang.Specification

import java.util.function.BiPredicate
import java.util.function.Function

class GPDirectedGraphTest extends Specification {
    def "test topological sort"() {
        given: "nodes list and neighbor function called 'edges'"
        def nodes
        def edges

        and: "GP Directed graph"
        def graph
        def constructGraph = {
            new GPDirectedGraph<String, String>(nodes, edges as Function)
        }

        and: "topological sorted nodes"
        def sorted
        def index = { sorted.indexOf(it) }

        when: "Simple DAG"
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
        then:
        edges("A") == ["D"]
        edges("B") == ["D", "C"]

        when: "Constructing GP Directed Graph and performing topological sorting"
        graph = constructGraph()
        sorted = graph.topologicalSort()

        then:
        sorted == ["B", "C", "A", "D"]


        when: "Disconnected graph"
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
        graph = constructGraph()
        sorted = graph.topologicalSort()

        then:
        sorted.size() == nodes.size()
        nodes.each { sorted.contains(it) }
        index("A") < index("B")
        index("B") < index("C")
        index("B") < index("D")

        index("_E") < index("_F")
        index("_E") < index("_G")
    }

    def "test topological sort with cycle error"() {
        given: "Graph that contains a cycle: A -> B -> D -> A"
        def nodes = ["A", "B", "C", "D"]
        def edges = {
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
        def graph = new GPDirectedGraph<String, String>(nodes, edges)

        when: "Performing topological sort"
        graph.topologicalSort()

        then: "Cyclic graph exception is thrown"
        thrown(GPDirectedGraph.CyclicGraphException)
    }

    def "test positive cyclic graph check"() {
        given: "Graph that contains a cycle: B -> D -> C -> B"
        def nodes = ["A", "B", "C", "D"]
        def edges = {
            switch(it) {
                case "A":
                    ["B"]
                    break
                case "B":
                    ["D"]
                    break
                case "C":
                    ["B"]
                    break
                case "D":
                    ["C"]
                    break
                default:
                    []
                    break
            }
        } as Function
        def graph = new GPDirectedGraph<String, String>(nodes, edges)

        when: "Checking for cycles"
        def hasCycles = ! graph.isDAG()

        then: "Positive result"
        hasCycles

        when: "Node points to itself: B -> B"
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
        graph = new GPDirectedGraph<String, String>(nodes, edges)

        then: "Graph has cycle"
        ! graph.isDAG()

    }

    def "test negative cyclic graph check"() {
        given: "Acyclic Graph"
        def nodes = ["A", "B", "C", "D"]
        def edges = {
            switch(it) {
                case "A":
                    ["B", "D"]
                    break
                case "B":
                    ["C"]
                    break
                case "C":
                    ["D"]
                    break
                default:
                    []
                    break
            }
        }  as Function
        def graph = new GPDirectedGraph<String, String>(nodes, edges)

        when: "Checking for cycles"
        def hasCycles = ! graph.isDAG()

        then: "Negative result"
        ! hasCycles
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
                return super.hashCode();
            else
                throw new UnsupportedOperationException("Hash code is not allowed to be called.")
        }

        String toString() {
            name
        }
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
}
