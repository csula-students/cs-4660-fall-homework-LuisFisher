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

        List<Edge> answer = new ArrayList<Edge>();

        Collection<Node> nodeCollection = graph.getNodes();

        Queue<Node> nodes = new PriorityQueue<>(new NodeComparator());

        Node srcNode = null;

        for(Node node: nodeCollection) {
            node.distance = Integer.MAX_VALUE;
            node.parent = null;

            node.g = 0;
            node.h = 0;

            if (node.equals(source)) {
                node.distance = 0;
                srcNode = node;
            }
        }

        nodes.offer(srcNode);

        int alt = 0;
        Node parent = null;
        Node endNode = null;

        while(!nodes.isEmpty()) {

            parent = nodes.poll();

            for(Node child: graph.neighbors(parent)) {

                if (!nodes.contains(child)) {
                    nodes.offer(child);
                }

                if (child.distance == Integer.MAX_VALUE) {
                    child.distance = graph.distance(parent, child);
                    child.parent = parent;
                }

                alt = parent.distance + graph.distance(parent, child);

                if (alt < child.distance) {
                    child.distance = alt;
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

        int numSteps = 0;

        while (node.parent != null) {

            answer.add(new Edge(node.parent, node,
                    graph.distance(node.parent, node)));

            node = node.parent;
            node.h = numSteps;
            numSteps++;
        }
        return Lists.reverse(answer);
    }
}