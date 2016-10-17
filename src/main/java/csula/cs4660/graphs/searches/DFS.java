package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Depth first search
 */
public class DFS implements SearchStrategy {

    List<Edge> firstAnswer;

    public DFS () {
    }

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        firstAnswer = null;
        List<Edge> answer = null;

        searchHelper(graph, source, dist, new ArrayList<Node>());

        if (firstAnswer != null) {
            answer = new ArrayList<Edge>();
            for(Edge e: firstAnswer) answer.add(e);
            firstAnswer = null;
        }

        return answer;
    }

    public void searchHelper(Graph graph, Node source, Node target , List<Node> route) {

        route.add(source);

        if (source.equals(target) && firstAnswer == null) {

            firstAnswer = new ArrayList<Edge>();

            for(int index = 0; index < route.size() - 1; index++) {

                int distance = graph.distance(route.get(index), route.get(index + 1));
                Edge edge = new Edge(route.get(index), route.get(index + 1), distance);
                firstAnswer.add(edge);

            }
        }

        for(Node child : graph.neighbors(source)) {
            searchHelper(graph, child, target, route);
            route.remove(child);
        }
    }
}