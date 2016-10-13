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
    private final double D = 1.5;


    /*

    function AstarSearch(start, goal) {
    // create empty queue Q
    var frontier = new Queue();
    var exploredSet = new HashSet();
    var parents = new Map();

    // use priority queue instead of normal queue
    frontier.enqueue(v);

    // initialize gScore and hScore
    gScore = new Map(); // given every cost is infinite by default
    gScore.put(start, 0);

    fScore = new Map();
    fScore.put(start, heuristicCost(start, goal));

    while (!frontier.isEmpty()) {
        // pop with the lowest fScore
        var u = queue.dequeue();
        if (u === goal) {
          return constructPath(u)
        }
        exploredSet.push(u);

        for (node in Graph.neighbors(u)) {
            if (exploredSet.contains(node)) {
                continue;
            }
            var tempGScore = gScore.get(u) + g(u, node);
            if (!frontier.contains(node)) {
                frontier.push(node);
            } else if (tempGScore >= gScore.get(node)) {
                continue; // skip because we are at the worse path
            }

            parent.put(node, u);
            gScore.put(node, tempGScore);
            fScore.put(node, gScore.get(node) + heuristicCost(node, goal))
        }
    }

    // no answer!
    return false;
}
     */

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        List<Edge> solution = new ArrayList<Edge>();
        Queue<Node> frontier = new PriorityQueue<>(new NodeComparator());
        Set<Node> exploredSet = new HashSet<Node>();

        Node srcNode = null;

        Collection<Node> nodeCollection = graph.getNodes();

        // initialize g,h,parent
        for(Node node: nodeCollection) {

            node.parent = null;

            node.g = Integer.MAX_VALUE;
            node.h = Integer.MAX_VALUE;

            // set source g and h values
            if (node.equals(source)) {
                node.g = 0;
                node.h = heuristic(source, dist);
                srcNode = node;
            }
        }

        frontier.offer(srcNode);

        Node parent = null;
        double tempGScore;

        while(!frontier.isEmpty()) {

            parent = frontier.poll();

            if (parent.equals(dist)) {
                return constructPath(graph, parent);
            }

            exploredSet.add(parent);

            for(Node child: graph.neighbors(parent)) {

                Tile childTile = (Tile) child.getData();

                if (childTile.getType() == "##") {
                    exploredSet.add(child);
                    continue;
                }

                if (exploredSet.contains(child)) {
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
                child.h = heuristic(child, dist);
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