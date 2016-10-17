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

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        Collection<Node> nodeCollection = graph.getNodes();

        Queue<Node> frontier = new PriorityQueue<>(new NodeComparator(true));
        Set<Node> exploredSet = new HashSet<Node>();

        Node srcNode = null;

        for(Node node: nodeCollection) {

            node.parent = null;
            node.g = Double.NEGATIVE_INFINITY;
            node.h = 0;

            if (node.equals(source)) {
                node.g = 0;
                srcNode = node;
            }
        }

        frontier.offer(srcNode);

        double alt = 0;
        Node parent = null;
        Node endNode = null;

        while(!frontier.isEmpty()) {

            parent = frontier.poll();

            if (exploredSet.contains(parent)) {
                continue;
            }

            exploredSet.add(parent);

            for(Node child: graph.neighbors(parent)) {

                if (!frontier.contains(child)) {
                    frontier.offer(child);
                }

                if (child.g == Double.NEGATIVE_INFINITY) {
                    child.g = parent.g + graph.distance(parent, child);
                    child.parent = parent;
                }
                else {

                    alt = parent.g + child.g;

                    if (alt >= child.g) {
                        child.g = alt;
                        child.parent = parent;
                    }
                }

                if (child.equals(dist) && endNode == null) {
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