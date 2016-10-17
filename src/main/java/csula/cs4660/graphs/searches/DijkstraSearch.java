package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.utils.NodeComparator;

import java.util.*;

/**
 * As name, dijkstra search using graph structure
 */
public class DijkstraSearch implements SearchStrategy {


    /*
    function Dijkstra(Graph, source) {
    // Initialization
    var dist = {};
    var prev = {};
    dist[source] ← 0

    // create priority vertex queue Q

    for (each vertex v in Graph) {
        if (v ≠ source) {
            // Unknown distance from source to v
            dist[v] ← INFINITY
            // Predecessor(parent) of v
            prev[v] ← UNDEFINED
        }

        Q.add_with_priority(v, dist[v])
    }

    while (Q is not empty) {
        // Remove and return best vertex
        u ← Q.extract_min()
        // only v that is still in Q
        for (each neighbor v of u) {
            alt = dist[u] + length(u, v)
            if (alt < dist[v]) {
                dist[v] ← alt
                prev[v] ← u
                Q.decrease_priority(v, alt)
            }
        }
    }

    // may want to consider return right away when find out
    // dest node

    return dist[], prev[]
}
     */


    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        Collection<Node> nodeCollection = graph.getNodes();

        Queue<Node> nodes = new PriorityQueue<>(new NodeComparator());

        Node srcNode = null;

        for(Node node: nodeCollection) {

            node.parent = null;
            node.g = Double.POSITIVE_INFINITY;
            node.h = 1;

            if (node.equals(source)) {
                node.g = 0;
                srcNode = node;
            }
        }

        nodes.offer(srcNode);

        double alt = 0;
        Node parent = null;
        Node endNode = null;

        while(!nodes.isEmpty()) {

            parent = nodes.poll();

            for(Node child: graph.neighbors(parent)) {

                if (!nodes.contains(child)) {
                    nodes.offer(child);
                }

                if (child.g == Double.POSITIVE_INFINITY) {
                    child.g = graph.distance(parent, child);
                    child.parent = parent;
                }

                alt = parent.g + graph.distance(parent, child);

                if (alt > child.g) {
                    child.g = alt;
                    child.parent = parent;
                }

                if (child.equals(dist)) {
                    endNode = child;
                }
            }
        }

        return constructPath(graph, endNode);
    }

    private List<Edge> constructPath(Graph graph, Node endNode) {

        List<Edge> answer = new ArrayList<Edge>();
        Node node = endNode;

        while (node.parent != null) {

            answer.add(new Edge(node.parent, node,
                    graph.distance(node.parent, node)));

            node = node.parent;
        }
        return Lists.reverse(answer);
    }
}