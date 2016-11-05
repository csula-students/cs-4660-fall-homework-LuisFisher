package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;

public class AlphaBeta {
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        // TODO: implement your alpha beta pruning algorithm here

        alphaBeta(graph, source, depth, alpha, beta, maximizingPlayer);

        int rootValue = ((MiniMaxState)source.getData()).getValue();

        for (Node node: graph.neighbors(source)) {

            int nodeValue = ((MiniMaxState)node.getData()).getValue();

            if (rootValue == nodeValue) {
                return  node;
            }
        }

        return source;
    }

    private static Node alphaBeta(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        List<Node> children = graph.neighbors(source);

        if ((depth == 0) || (children.size() == 0)) {
            // the end of game by **evaluate** function
            // return evaluate(soureNode.gameState); // return a number
            // returning root because we do not have an eval function for game
            return source;
        }

        MiniMaxState rootMMS = (MiniMaxState)source.getData();

        if (maximizingPlayer) {

            int bestValue = Integer.MIN_VALUE; // negative infinite

            for (Node node: children) {

                int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, false).getData()).getValue();

                bestValue = Math.max(bestValue, value);

                source.alpha = Math.max(node.alpha, bestValue);
                rootMMS.setValue(bestValue);

                System.out.println(rootMMS.getIndex() + " " +  node.alpha + " " + node.beta);

                if (node.beta < node.alpha) {
                    break;
                }
            }

            return source;
        }
        else {

            int bestValue = Integer.MAX_VALUE; // positive infinite

            for (Node node: children) {

                int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, true).getData()).getValue();

                bestValue = Math.min(bestValue, value);
                source.beta = Math.min(node.beta, bestValue);

                rootMMS.setValue(bestValue);

                System.out.println(rootMMS.getIndex() + " " +  node.alpha + " " + node.beta);

                if (node.beta < node.alpha) {
                    break;
                }
            }

            return source;
        }
    }
}
