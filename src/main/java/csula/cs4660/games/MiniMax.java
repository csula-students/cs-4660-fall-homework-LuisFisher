package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;

public class MiniMax {
    
    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean maximizingPlayer) {


        // TODO: implement minimax to retrieve best move
        // NOTE: you should mutate graph and node as you traverse and update value

        miniMax(graph, root, depth, maximizingPlayer);



        for(Node n: graph.getNodes()) {
            System.out.println(((MiniMaxState) n.getData()).getIndex() + " " +((MiniMaxState) n.getData()).getValue());
        }

        int rootValue = ((MiniMaxState)root.getData()).getValue();

        for (Node node: graph.neighbors(root)) {

            int nodeValue = ((MiniMaxState)node.getData()).getValue();

            if (rootValue == nodeValue) {

                return  graph.getNode(node).get();
            }
        }

        return root;
    }

    private static Node miniMax(Graph graph, Node root, Integer depth, Boolean maximizingPlayer) {

        root = graph.getNode(root).get();

        List<Node> children = graph.neighbors(root);

        if ((depth == 0) || (children.size() == 0)) {
            // the end of game by **evaluate** function
            // return evaluate(soureNode.gameState); // return a number
            // returning root because we do not have an eval function for game
            return root;
        }

        MiniMaxState rootMMS = (MiniMaxState)root.getData();

        if (maximizingPlayer) {

            int bestValue = Integer.MIN_VALUE; // negative infinite

            for (Node node: children) {

                int value = ((MiniMaxState) miniMax(graph, node, depth - 1, false).getData()).getValue();

                bestValue = Math.max(bestValue, value);
                rootMMS.setValue(bestValue);
            }

            return root;
        }
        else {

            int bestValue = Integer.MAX_VALUE; // positive infinite

            for (Node node: children) {

                int value = ((MiniMaxState) miniMax(graph, node, depth - 1, true).getData()).getValue();

                bestValue = Math.min(bestValue, value);
                rootMMS.setValue(bestValue);
            }

            return root;
        }
    }
}