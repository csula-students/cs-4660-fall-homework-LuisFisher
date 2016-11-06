package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.util.List;

public class AlphaBeta {


    public static void main(String[] args) {

        Graph graph = new Graph(Representation.of(Representation.STRATEGY.OBJECT_ORIENTED));

        Node root = new Node<>(new MiniMaxState(0, 0));
        graph.addNode(root);

        for (int i = 1; i < 7; i++) {
            graph.addNode(new Node<>(new MiniMaxState(i, 0)));
        }
        graph.addNode(new Node<>(new MiniMaxState(7, -6)));
        graph.addNode(new Node<>(new MiniMaxState(8, -1)));
        graph.addNode(new Node<>(new MiniMaxState(9, -9)));
        graph.addNode(new Node<>(new MiniMaxState(10, 14)));
        graph.addNode(new Node<>(new MiniMaxState(11, -15)));
        graph.addNode(new Node<>(new MiniMaxState(12, -1)));
        graph.addNode(new Node<>(new MiniMaxState(13, 6)));
        graph.addNode(new Node<>(new MiniMaxState(14, -18)));

        int diff = 1;
        for (int i = 0; i < 7; i++) {
            graph.addEdge(new Edge(new Node<>(new MiniMaxState(i, 0)), new Node<>(new MiniMaxState(i + diff, 0)), 1));
            diff++;
            graph.addEdge(new Edge(new Node<>(new MiniMaxState(i, 0)), new Node<>(new MiniMaxState(i + diff, 0)), 1));
        }

        getBestMove(graph, root, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

        for (Node d : graph.getNodes()) {

            Node n = graph.getNode(d).get();

            System.out.println(((MiniMaxState) n.getData()).getIndex() + " " + ((MiniMaxState) n.getData()).getValue() +
                    " " + n.alpha + " " + n.beta);
        }


    }

    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        // TODO: implement minimax to retrieve best move
        // NOTE: you should mutate graph and node as you traverse and update value

        Node root = null;
        if (source != null) {
            root = graph.getNode(source).get();
        }


        alphaBeta(graph, root, depth, alpha, beta, maximizingPlayer);


        int rootValue = ((MiniMaxState) root.getData()).getValue();

        for (Node node : graph.neighbors(root)) {

            int nodeValue = ((MiniMaxState) node.getData()).getValue();

            if (rootValue == nodeValue) {

                return graph.getNode(node).get();
            }
        }

        return root;
    }


    private static Node alphaBeta(Graph graph, Node root, Integer depth, Integer alpha, Integer beta, Boolean maximizingPlayer) {

        Node parent = graph.getNode(root).get();

        List<Node> children = graph.neighbors(root);

        if ((depth == 0) || (children.size() == 0)) {

            // the end of game by **evaluate** function
            // return evaluate(soureNode.gameState); // return a number
            // returning root because we do not have an eval function for game
            return parent;
        }
        else {
            parent.alpha = alpha;
            parent.beta = beta;
        }

        MiniMaxState parentMMS = (MiniMaxState)parent.getData();

        if (maximizingPlayer) {

            for (Node child: children) {

                int value = ((MiniMaxState) alphaBeta(graph, child, depth - 1, alpha, beta, false).getData()).getValue();

                child.alpha = Math.max(parent.alpha, value);

                if (child.alpha >= child.beta) {
                    parentMMS.setValue(child.alpha);
                    parent.beta = child.alpha;
                }
            }
            return parent;
        }
        else {

            for (Node child: children) {

                int value = ((MiniMaxState) alphaBeta(graph, child, depth - 1, alpha, beta, false).getData()).getValue();

                child.alpha = Math.max(parent.alpha, value);

                if (child.alpha >= child.beta) {
                    parentMMS.setValue(child.alpha);
                    parent.beta = child.alpha;
                }
            }
            return parent;
        }
    }
}