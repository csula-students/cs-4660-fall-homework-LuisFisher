package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 */

public class ObjectOriented implements Representation {
    private Collection<Node> nodes;
    private Collection<Edge> edges;

    public ObjectOriented(File file) {

        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        readFile(file);
    }

    public ObjectOriented() {

        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
    }

    private void readFile(File file) {

        BufferedReader reader;
        String curLine;
        int nodeCount;

        try {
            reader = new BufferedReader(new FileReader(file));

            ArrayList<Node> nodesTemp = new ArrayList<Node>();
            Node tempNode;
            Edge tempEdge;

            curLine = reader.readLine();
            nodeCount = Integer.parseInt(curLine);

            for (int index = 0; index < nodeCount; index++) {

                tempNode = new Node(index);

                nodesTemp.add(tempNode);
            }

            while ((curLine = reader.readLine()) != null) {

                String[] sRow = curLine.split(":");

                int[] rowValues = new int[sRow.length];

                for(int index = 0; index < sRow.length; index++) {
                    rowValues[index] = Integer.parseInt(sRow[index]);
                }

                tempEdge = new Edge(nodesTemp.get(rowValues[0]), nodesTemp.get(rowValues[1]), rowValues[2]);

                edges.add(tempEdge);
            }

            nodes.addAll(nodesTemp);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean adjacent(Node x, Node y) {

        for (Edge e: edges) {

            if (e.getFrom().equals(x) && e.getTo().equals(y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {

        if (!nodes.contains(x)) return null;

        List<Node> neighbors = new ArrayList<Node>();

        for (Edge e: edges) {

            if (e.getFrom().equals(x)) {
                neighbors.add(e.getTo());
            }
        }
        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {

        if (nodes.contains(x)) return false;

        nodes.add(x);
        return true;
    }

    @Override
    public boolean removeNode(Node x) {

        if (!nodes.contains(x)) return false;

        nodes.remove(x);

        ArrayList<Edge> edgesToRemove = new ArrayList<Edge>();

        for (Edge e: edges) {
            if(e.getFrom().equals(x) || e.getTo().equals(x)) {
                edgesToRemove.add(e);
            }
        }

        edges.removeAll(edgesToRemove);

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {

        if (edges.contains(x)) return false;

        edges.add(x);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {

        if (!edges.contains(x)) return false;

        edges.remove(x);
        return true;
    }

    @Override
    public int distance(Node from, Node to) {

        for (Edge e: edges) {

            if (e.getFrom().equals(from) && e.getTo().equals(to)) {
                return e.getValue();
            }

        }
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }

    @Override
    public Optional<Node> getNode(Node node) {
        Iterator<Node> iterator = nodes.iterator();
        Optional<Node> result = Optional.empty();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            if (next.equals(node)) {
                result = Optional.of(next);
            }
        }
        return result;
    }
}
