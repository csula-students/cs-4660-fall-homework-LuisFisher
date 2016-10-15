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

        List<Edge> answer = new ArrayList<Edge>();
        Collection<Node> nodeCollection = graph.getNodes();

        Queue<Node> nodes = new LinkedList<Node>();

        for(Node n: nodeCollection) {
            n.g = Integer.MAX_VALUE;
            n.h = 0;
            n.parent = null;
        }

        if (nodeCollection.contains(source)) {

            for(Node node: nodeCollection) {

                if (node.equals(source)) {

                    node.g = 0;
                    node.parent = null;
                    nodes.offer(source);
                    break;
                }
            }
        }

        Node parent = null;
        Node endNode = null;

        while(!nodes.isEmpty()) {

            parent = nodes.poll();

            for(Node child: graph.neighbors(parent)) {

                nodes.offer(child);

                if (child.g == Integer.MAX_VALUE) {
                    child.g = graph.distance(parent, child);
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


