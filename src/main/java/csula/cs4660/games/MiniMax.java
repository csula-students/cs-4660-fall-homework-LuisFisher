package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;

public class MiniMax {

    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean maximizingPlayer) {

        // TODO: implement minimax to retrieve best move
        // NOTE: you should mutate graph and node as you traverse and update value

        List<Node> children = graph.neighbors(root);

        if ((depth == 0) || (children.size() == 0)) {
            // the end of game by **evaluate** function
            // return evaluate(soureNode.gameState); // return a number
            return root;
        }

        MiniMaxState rootMMS = (MiniMaxState)root.getData();

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE; // negative infinite
            for (Node node: children) {

                MiniMaxState nodeMMS = (MiniMaxState)node.getData();

                int value = ((MiniMaxState)getBestMove(graph, node, depth - 1, false).getData()).getValue();

                if (bestValue <= value) {
                    rootMMS.setIndex(nodeMMS.getIndex());
                }

                bestValue = Math.max(bestValue, value);




                rootMMS.setValue(bestValue);
            }
            return root;

        }
        else {
            int bestValue = Integer.MAX_VALUE; // positive infinite
            for (Node node: children) {

                MiniMaxState nodeMMS = (MiniMaxState)node.getData();

                int value = ((MiniMaxState)getBestMove(graph, node, depth - 1, true).getData()).getValue();

                if (bestValue <= value) {
                    rootMMS.setIndex(nodeMMS.getIndex());
                }


                bestValue = Math.min(bestValue, value);

                rootMMS.setValue(bestValue);

            }
            return root;
        }
    }
}

/*
function minimax(graph, sourceNode, depth, maximizingPlayer) {
    // usually being optimized in a way to compute even before
    // the end of game by **evaluate** function
    if (depth = 0 || sourceNode is a leaf) {
        return evaluate(soureNode.gameState); // return a number
    }

    if (maximizingPlayer) {
        bestValue = Number.MAX_VALUE; // positive infinite
        for (node in graph.neighbors(sourceNode)) {
            value = minimax(node, graph, depth - 1, false);
            bestValue = Math.max(bestValue, value);
        }
        return bestValue;
    } else {
        bestValue = Number.MIN_VALUE; // negative infinite
        for (node in graph.neighbors(sourceNode)) {
            value = minimax(node, graph, depth - 1, true);
            bestValue = Math.min(bestValue, value);
        }
        return bestValue;
    }
}

// initial call of the above function to traverse 3 level for example
minimax(graph, startingNode, 3, TRUE);
 */
