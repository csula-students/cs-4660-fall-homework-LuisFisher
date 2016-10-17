package csula.cs4660.quizes;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.graphs.searches.DijkstraSearch;
import csula.cs4660.quizes.models.DTO;
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
        buildGraph(graph, initialState.getId());

        Node start = new Node(initialState.getId());
        Node end = new Node(goalState.getId());

        List<Edge> path = graph.search(new BFS(), start, end);

        System.out.println("BFS Path: ");
        showRooms(path);
        System.out.println();


        path = graph.search(new DijkstraSearch(), start, end);

        System.out.println("DijkstraSearch Path: ");
        showRooms(path);
        System.out.println();
    }

    public static void buildGraph(Graph graph, String current) {

        Queue<String> frontier = new LinkedList<String>();
        Set<String> exploredSet = new HashSet<String>();

        String parentId = current;

        graph.addNode(new Node(parentId));

        frontier.offer(parentId);

        int nodeCount  = 0;

        while (!frontier.isEmpty()) {

            parentId = frontier.poll();

            if (exploredSet.contains(parentId)) {
                continue;
            }

            exploredSet.add(parentId);
            nodeCount++;

            State parentState = Client.getState(parentId).get();

            for (State childState: parentState.getNeighbors()) {

                String childId = childState.getId();

                if (exploredSet.contains(childId)) {
                    continue;
                }

                frontier.add(childId);

                Node child = new Node(childId);
                Node parent = new Node(parentId);

                if (!graph.getNodes().contains(child)) {
                    graph.addNode(child);
                }

                int cost = Client.stateTransition(parentId,
                        childId).get().getEvent().getEffect();

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

            String fromId = (String)path.get(index).getFrom().getData();
            String toId = (String)path.get(index).getTo().getData();

            State fromState = Client.getState(fromId).get();
            State toState = Client.getState(toId).get();

            DTO dto = Client.stateTransition(fromId, toId).get();

            cost = path.get(index).getValue();

            System.out.println(fromState.getLocation().getName() + " : " +
                    toState.getLocation().getName() + " : " + cost + " : " + dto.getEvent().getDescription());
        }
    }
}
