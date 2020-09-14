package de.upb.sede.util;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * General Purpose Directed Graph can be created from any suitable structure.
 * It offers some standard graph implementations.
 *
 * NodeKey should be hashable, ie. can be used as {@link java.util.HashMap} keys.
 */
public class GPDirectedGraph<Node, NodeKey> {

    private Function<Node, NodeKey> keyExtractor;
    private List<Node> nodes;
    private Function<Node, List<Node>> neighborFunction;


    public GPDirectedGraph(List<Node> nodes, Function<Node, List<Node>> edgeFunction) {
        this(n -> (NodeKey) n, nodes, edgeFunction);
    }

    public GPDirectedGraph(Function<Node, NodeKey> keyExtractor, List<Node> nodes, Function<Node, List<Node>> neighborFunction) {
        this.keyExtractor = keyExtractor;
        this.nodes = nodes;
        this.neighborFunction = neighborFunction;
    }

    public GPDirectedGraph(
            Function<Node, NodeKey> keyExtractor,
            List<Node> nodes,
            BiPredicate<Node, Node> hasEdge) {
        this(keyExtractor, nodes, neighborsFromPredicate(keyExtractor, nodes, hasEdge));
    }

    public GPDirectedGraph(
            List<Node> nodes,
            BiPredicate<Node, Node> hasEdge) {
        this(n -> (NodeKey) n, nodes, neighborsFromPredicate(n -> (NodeKey) n, nodes, hasEdge));
    }

    private static <Node, NodeKey> Function<Node, List<Node>> neighborsFromPredicate(
            Function<Node, NodeKey> keyExtractor,
            List<Node> nodes,
            BiPredicate<Node, Node> hasEdge) {
        Map<NodeKey, List<Node>> neighborCache = new HashMap<>();
        return node ->
        neighborCache.computeIfAbsent(
                keyExtractor.apply((Node) node),
                nodeKey ->
                        nodes.stream()
                                .filter(otherNode -> hasEdge.test((Node)node, otherNode))
                                .collect(Collectors.toList()));
    }

    private NodeKey key(Node node) {
        return keyExtractor.apply(node);
    }

    private List<Node> neighbors(Node node) {
        return Objects.requireNonNull(neighborFunction.apply(node));
    }

    public List<Node> topologicalSort() throws CyclicGraphException {
        DefaultMap<NodeKey, Boolean> tempMarks = new DefaultMap<>(() -> false);
        DefaultMap<NodeKey, Boolean> permMarks = new DefaultMap<>(() -> false);

        List<Node> topologicallySortedList = new LinkedList<>(); // use linked list for faster first index insertion.
        Recursive<Consumer<Node>> visit = new Recursive<>();
        visit.i = n -> {
            NodeKey keyN = key(n);
            if(permMarks.get(keyN)) {
                return;
            }
            if(tempMarks.get(keyN)) {
                throw new CyclicGraphException();
            }
            tempMarks.put(keyN, true);
            for (Node m : neighbors(n)) {
                visit.i.accept(m);
            }
            tempMarks.put(keyN, false);
            permMarks.put(keyN, true);
            topologicallySortedList.add(0, n); // add at first position.
        };

        List<Node> allUnmarkedNodes = new ArrayList<>(nodes);
        while(!allUnmarkedNodes.isEmpty()) {
            Node node = allUnmarkedNodes.get(0);
            visit.i.accept(node);
            allUnmarkedNodes.removeIf(n -> permMarks.get(key(n)));
        }
        return new ArrayList<>(topologicallySortedList); // copy into a RA list.
    }

    public List<Node> cycle() {
        DefaultMap<NodeKey, Boolean> tempMarks = new DefaultMap<>(() -> false);
        DefaultMap<NodeKey, Boolean> permMarks = new DefaultMap<>(() -> false);

        List<Node> cycle = new LinkedList<>(); // use linked list for faster first index insertion.
        Recursive<Function<Node, Node>> visit = new Recursive<>();
        visit.i = n -> {
            NodeKey keyN = key(n);
            if(permMarks.get(keyN)) {
                return null;
            }
            if(tempMarks.get(keyN)) {
                // cycle detected
                return n;
            }
            tempMarks.put(keyN, true);
            for (Node m : neighbors(n)) {
                Node cycleEndPoint = visit.i.apply(m);
                if(cycleEndPoint == null) {
                    if(!cycle.isEmpty())
                        break;
                } else {
                    cycle.add(0, n);
                    if(cycleEndPoint == n) {
                        return null;
                    } else {
                        return cycleEndPoint;
                    }
                }
            }
            tempMarks.put(keyN, false);
            permMarks.put(keyN, true);
            return null;
        };

        List<Node> allUnmarkedNodes = new ArrayList<>(nodes);
        while(!allUnmarkedNodes.isEmpty() && cycle.isEmpty()) {
            Node node = allUnmarkedNodes.get(0);
            visit.i.apply(node);
            allUnmarkedNodes.removeIf(n -> permMarks.get(key(n)));
        }
        return new ArrayList<>(cycle); // copy into a RA list.
    }

    public boolean isDAG() {
        return cycle().isEmpty();
    }

    public static class CyclicGraphException extends RuntimeException{
        private CyclicGraphException() {
            super("Graph contains cycles.");
        }
    }

}
