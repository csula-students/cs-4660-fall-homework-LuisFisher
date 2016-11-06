package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;

public class MiniMax {
    
    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean maximizingPlayer) {

        miniMax(graph, root, depth, maximizingPlayer);

        // prints out resulting nodes with in form of rows: index value for testing
        for(Node n: graph.getNodes()) {
            System.out.println(((MiniMaxState) n.getData()).getIndex() + " " +((MiniMaxState) n.getData()).getValue());
        }

        int rootValue = ((MiniMaxState)root.getData()).getValue();

        for (Node node: graph.neighbors(root)) {

            int nodeValue = ((MiniMaxState)node.getData()).getValue();

            if (rootValue == nodeValue) { // get first child that has same value

                return  graph.getNode(node).get(); // get specific node in memory
            }
        }

        return root;
    }

    private static Node miniMax(Graph graph, Node root, Integer depth, Boolean maximizingPlayer) {

        root = graph.getNode(root).get(); // sets reference to correct location in memory

        List<Node> children = graph.neighbors(root);

        if ((depth == 0) || (children.size() == 0)) {
            // returning root because we do not have an eval function for game
            return root;
        }

        MiniMaxState rootMMS = (MiniMaxState)root.getData();

        if (maximizingPlayer) {

            int bestValue = Integer.MIN_VALUE; // negative infinite

            for (Node node: children) {

                int value = ((MiniMaxState) miniMax(graph, node, depth - 1, false).getData()).getValue();

                bestValue = Math.max(bestValue, value); // used in both minimax
                rootMMS.setValue(bestValue); // sets value for minimax
            }

            return root;
        }
        else {

            int bestValue = Integer.MAX_VALUE; // positive infinite

            for (Node node: children) {

                int value = ((MiniMaxState) miniMax(graph, node, depth - 1, true).getData()).getValue();

                bestValue = Math.min(bestValue, value); // used in both minimax
                rootMMS.setValue(bestValue); // sets value for minimax
            }

            return root;
        }
    }
}