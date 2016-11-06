package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;

public class AlphaBeta {

    public static Node getBestMove(Graph graph, Node root, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        alphaBeta(graph, root, depth, alpha, beta, maximizingPlayer);

        // prints out resulting nodes with in form of rows: index value alpha beta for testing
        for (Node n : graph.getNodes()) {
            System.out.println(((MiniMaxState) n.getData()).getIndex() + " " + ((MiniMaxState) n.getData()).getValue() +
                    " " + n.alpha + " " + n.beta);
        }

        int rootValue = ((MiniMaxState)root.getData()).getValue();

        for (Node node: graph.neighbors(root)) {

            int nodeValue = ((MiniMaxState)node.getData()).getValue();

            if (rootValue == nodeValue) {  // get first child that has same value
                return  graph.getNode(node).get(); // get specific node in memory
            }
        }

        return root;
    }

    private static Node alphaBeta(Graph graph, Node root, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        root = graph.getNode(root).get(); // sets reference to correct location in memory

        root.alpha = alpha; // sets display alpha for testing
        root.beta = beta; // sets display beta for testing

        List<Node> children = graph.neighbors(root);

        if ((depth == 0) || (children.size() == 0)) {
            // returning root because we do not have an eval function for game
            return root;
        }

        MiniMaxState rootMMS = (MiniMaxState)root.getData();

        if (maximizingPlayer) {

            int bestValue = Integer.MIN_VALUE; // negative infinite

            for (Node node: children) {

                int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, false).getData()).getValue();

                bestValue = Math.max(bestValue, value); // used in both minimax and pruning
                alpha = Math.max(alpha, bestValue); // sets alpha for pruning


                root.alpha = alpha; // used to display alpha for testing

                rootMMS.setValue(bestValue); // sets value for minimax

                if (beta <= alpha) {
                    break; // beta cut-off
                }
            }

            return root;
        }
        else {

            int bestValue = Integer.MAX_VALUE; // positive infinite

            for (Node node: children) {

                int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, true).getData()).getValue();

                bestValue = Math.min(bestValue, value); // used in both minimax and pruning
                beta = Math.min(beta, bestValue); // sets beta for pruning

                root.beta = beta; // used to display beta for testing

                rootMMS.setValue(bestValue); // sets value for minimax

                if (beta <= alpha) {
                    break; // alpha cut-off
                }
            }

            return root;
        }
    }
}