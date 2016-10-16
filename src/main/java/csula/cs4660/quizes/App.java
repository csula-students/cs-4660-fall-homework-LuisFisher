package csula.cs4660.quizes;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.graphs.searches.DijkstraSearch;
import csula.cs4660.graphs.searches.SearchStrategy;
import csula.cs4660.quizes.models.State;

import java.util.*;

/**
 * Here is your quiz entry point and your app
 */
public class App {
    public static void main(String[] args) {

        State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
        State goalState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();

        Graph graph = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));

        buildGraph(graph, initialState);
        Node start = new Node(initialState);
        Node end = new Node(goalState);

        List<Edge> path = graph.search(new BFS(), start, end);

        System.out.println("BFS Path: ");
        showRooms(path);
        System.out.println();


        path = graph.search(new DijkstraSearch(), start, end);
        System.out.println("DijkstraSearch Path: ");
        showRooms(path);
        System.out.println();
    }

    public static void buildGraph(Graph graph, State current) {

        Queue<Node> nodes = new LinkedList<Node>();
        Set<String> exploredSet = new HashSet<String>();

        Node parent = new Node(current);
        graph.addNode(parent);

        nodes.offer(parent);

        int nodeCount  = 0;

        while (!nodes.isEmpty()) {

            parent = nodes.poll();
            State parentState = (State)parent.getData();

            if (exploredSet.contains(parentState.getId())) {
                continue;
            }

            exploredSet.add(parentState.getId());
            nodeCount++;

            for (State childState: parentState.getNeighbors()) {

                if (exploredSet.contains(childState.getId())) {
                    continue;
                }

                State data = Client.getState(childState.getId()).get();
                Node child = new Node(data);

                graph.addNode(child);
                nodes.add(child);

                int cost = -1 * Client.stateTransition(current.getId(),
                        childState.getId()).get().getEvent().getEffect();

                cost += 100;

                Edge edge = new Edge(parent, child, cost);

                graph.addEdge(edge);
            }

            if (nodeCount % 50 == 0) {
                System.out.println(nodeCount);
            }

        }
        System.out.println(nodeCount);
    }

    public static void showRooms(List<Edge> path) {

        int cost = 0;

        for(int index = 0; index < path.size(); index++) {
            State fromState = (State)path.get(index).getFrom().getData();

            cost = path.get(index).getValue() - 100;
            cost *= -1;

            System.out.println("room: " + fromState.getLocation().getName() +
                    ", cost: " + cost);
        }

        cost = path.get(path.size() - 1).getValue() - 100;
        cost *= -1;

        State toState = (State)path.get(path.size() - 1).getTo().getData();
        System.out.println("room: " + toState.getLocation().getName() +
                ", cost: " + cost);
    }

}
