package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.utils.NodeComparator;

import java.util.*;

/**
 * Perform A* search
 */
public class AstarSearch implements SearchStrategy {

    // D is a scale value for you to adjust performance vs accuracy
    private final double D = 1.0;

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        Queue<Node> frontier = new PriorityQueue<>(new NodeComparator());
        Set<Node> exploredSet = new HashSet<Node>();

        Node srcNode = null;

        Collection<Node> nodeCollection = graph.getNodes();

        // initialize g,h,parent
        for(Node node: nodeCollection) {

            node.parent = null;
            node.g = Double.MAX_VALUE;
            node.h = heuristic(node, dist);

            // set source g and h values
            if (node.equals(source)) {
                srcNode = node;
                node.g = 0;
            }
        }

        frontier.offer(srcNode);

        Node parent = null;
        double tempGScore;

        while(!frontier.isEmpty()) {

            parent = frontier.poll();
            exploredSet.add(parent);

            if (parent.equals(dist)) {
                return constructPath(graph, parent);
            }

            for(Node child: graph.neighbors(parent)) {

                if (exploredSet.contains(child)) {
                    continue;
                }

                Tile childTile = (Tile) child.getData();
                String data = childTile.getType();

                if (data.equals("##")) {
                    exploredSet.add(child);
                    continue;
                }

                tempGScore = parent.g + graph.distance(parent, child);

                if (!frontier.contains(child)) {
                    frontier.offer(child);
                }

                else if (tempGScore >= child.g) {
                    continue;// skip because we are at the worse path
                }

                child.parent = parent;
                child.g = tempGScore;
            }
        }
        return null;
    }

    private double heuristic(Node source, Node goal) {

        Tile sourceT = (Tile) source.getData();
        Tile goalT = (Tile) goal.getData();

        int dx = Math.abs(sourceT.getX() - goalT.getX());
        int dy = Math.abs(sourceT.getY() - goalT.getY());

        return D * (dx + dy);
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