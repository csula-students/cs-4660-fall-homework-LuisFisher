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

    Set<Node>sGraph;

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        List<Edge> answer = new ArrayList<Edge>();
        Collection<Node> nodeCollection = graph.getNodes();

        Comparator<Node> comparator = new NodeComparator();
        PriorityQueue<Node> nodes = new PriorityQueue<Node>(comparator);

        for(Node n: nodeCollection) {
            n.distance = Integer.MAX_VALUE;
            n.parent = null;
        }

        if (nodeCollection.contains(source)) {

            for(Node node: nodeCollection) {

                if (node.equals(source)) {

                    node.distance = 0;
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

            int alt = 0;

            for(Node child: graph.neighbors(parent)) {

                if (child.distance == Integer.MAX_VALUE) {
                    child.distance = graph.distance(parent, child);
                    child.parent = parent;
                }

                if (nodes.contains(child)) {

                    alt = parent.distance + child.distance;

                    if (alt < child.distance) {
                        child.distance = alt;
                        child.parent = parent;
                    }
                }

                if (child.equals(dist)) {
                    endNode = child;
                }

                nodes.offer(child);
            }
        }

        int distance = 0;

        while (endNode.parent != null) {

            distance = endNode.distance;

            answer.add(new Edge(endNode.parent, endNode, distance));

            endNode = endNode.parent;
        }

        return Lists.reverse(answer);
    }
}