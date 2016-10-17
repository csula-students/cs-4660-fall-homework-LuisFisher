package csula.cs4660.quizes;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.graphs.searches.DijkstraSearch;
import csula.cs4660.quizes.models.State;

import java.util.*;

/**
 * Here is your quiz entry point and your app
 */
public class App {
    public static void main(String[] args) {

        String initialID = "10a5461773e8fd60940a56d2e9ef7bf4";
        String goalId = "e577aa79473673f6158cc73e0e5dc122";

        Graph graph = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));
        buildGraph(graph, initialID);

        Node start = new Node(initialID);
        Node end = new Node(goalId);

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

        // use Id's to build a graph to make less calls to server
        Queue<String> frontier = new LinkedList<String>();
        Set<String> exploredSet = new HashSet<String>();

        String parentId = current;
        frontier.offer(parentId);

        // used to count number of nodes when loading graph
        int nodeCount  = 0;

        // while priority que is not empty
        while (!frontier.isEmpty()) {

            // remove first node
            parentId = frontier.poll();

            // if the node has been explored skips this node
            if (exploredSet.contains(parentId)) {
                continue;
            }

            // create a node
            Node parent = new Node(parentId);

            // if node has not been added to graph adds it to graph
            if (!graph.getNodes().contains(parent)) {
                graph.addNode(parent);
            }

            // adds parent to exploredSet
            exploredSet.add(parentId);
            nodeCount++;

            // get State of parent to get neighbors, this is a server call
            // 1 call per while loop for this
            State parentState = Client.getState(parentId).get();

            for (State childState: parentState.getNeighbors()) {

                String childId = childState.getId();

                // if hasn't been explored adds to frontier
                if (!exploredSet.contains(childId)) {
                    frontier.add(childId);
                }

                Node child = new Node(childId);

                // if child is not in graph adds child to graph
                if (!graph.getNodes().contains(child)) {
                    graph.addNode(child);
                }

                // get cost from parent to child, 1 call per edge
                int cost = Client.stateTransition(parentId,
                        childId).get().getEvent().getEffect();

                // adds edge
                Edge edge = new Edge(parent, child, cost);
                graph.addEdge(edge);
            }

            // this is just a counter that prints every 50 nodes to show progress
            if (nodeCount % 50 == 0) {
                System.out.println(nodeCount);
            }
        }
        System.out.println(nodeCount);
    }

    // prints result in correct order in format:
    // from : to : cost: fromId(id), toId(id)
    public static void showRooms(List<Edge> path) {

        int cost = 0;

        for(int index = 0; index < path.size(); index++) {

            String fromId = (String)path.get(index).getFrom().getData();
            String toId = (String)path.get(index).getTo().getData();

            // makes server call to get names of the fromState, and toState
            String fromRoom = Client.getState(fromId).get().getLocation().getName();
            String toRoom = Client.getState(toId).get().getLocation().getName();

            cost = path.get(index).getValue();

            System.out.println(fromRoom + " : " +  toRoom + " : " + cost + " : " +
                    "fromId(" + fromId + "), toId(" + toId + ")");
        }
    }
}