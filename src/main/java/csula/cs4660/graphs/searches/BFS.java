package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        Collection<Node> nodeCollection = graph.getNodes();

        Queue<Node> frontier = new LinkedList<Node>();

        for(Node n: nodeCollection) {
            n.g = Double.POSITIVE_INFINITY;
            n.h = 0;
            n.parent = null;

            if (n.equals(source)) {
                n.g = 0;
                frontier.offer(n);
            }
        }

        Node parent = null;
        Node endNode = null;

        while(!frontier.isEmpty()) {

            parent = frontier.poll();

            for(Node child: graph.neighbors(parent)) {

                frontier.offer(child);

                if (child.g == Double.POSITIVE_INFINITY) {
                    child.g = graph.distance(parent, child);
                    child.parent = parent;
                }

                if (child.equals(dist)) {
                    return constructPath(graph, child);
                }
            }
        }

        return null;
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