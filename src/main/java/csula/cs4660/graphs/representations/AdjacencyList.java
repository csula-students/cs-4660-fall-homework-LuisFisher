package csula.cs4660.graphs.representations;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {

    private Map<Node, Collection<Edge>> adjacencyList;

    public AdjacencyList(File file) {

        adjacencyList = new HashMap<Node, Collection<Edge>>();
        readFile(file);
    }

    public AdjacencyList() {

        adjacencyList = new HashMap<Node, Collection<Edge>>();
    }

    private void readFile(File file) {

        BufferedReader reader;
        String curLine;
        int nodeCount;

        try {
            reader = new BufferedReader(new FileReader(file));

            ArrayList<Node> nodes = new ArrayList<Node>();
            Node tempNode;
            Edge tempEdge;

            curLine = reader.readLine();
            nodeCount = Integer.parseInt(curLine);

            for (int index = 0; index < nodeCount; index++) {

                tempNode = new Node(index);

                nodes.add(tempNode);
                adjacencyList.put(tempNode, new ArrayList<Edge>());
            }

            while ((curLine = reader.readLine()) != null) {

                String[] sRow = curLine.split(":");

                int length = sRow.length;

                int[] rowValues = new int[length];

                for(int index = 0; index < length; index++) {
                    rowValues[index] = Integer.parseInt(sRow[index]);
                }

                tempEdge = new Edge(nodes.get(rowValues[0]), nodes.get(rowValues[1]), rowValues[2]);

                adjacencyList.get(nodes.get(rowValues[0])).add(tempEdge);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean adjacent(Node x, Node y) {

        // should i include?
        if (!adjacencyList.containsKey(x)) return false;

        ArrayList<Edge> edges = (ArrayList)adjacencyList.get(x);

        for(Edge e: edges) {
            if (e.getTo().equals(y)) return true;
        }

        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {

        // should i include?
        if (!adjacencyList.containsKey(x)) return null;

        ArrayList<Edge> edges = (ArrayList<Edge>)adjacencyList.get(x);

        ArrayList<Node> neighbors = new ArrayList<Node>();

        for(Edge e: edges) {
            neighbors.add(e.getTo());
        }

        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {

        if (adjacencyList.containsKey(x)) return false;

        adjacencyList.put(x, new ArrayList<Edge>());

        return true;
    }

    @Override
    public boolean removeNode(Node x) {

        if (!adjacencyList.containsKey(x)) return false;

        Node[] keys;
        ArrayList<Edge> edges;

        /* remove all the from x edges */
        adjacencyList.remove(x);

        keys = new Node[adjacencyList.size()];

        adjacencyList.keySet().toArray(keys);

        /* remove all the to x edges */
        for(Node n: keys) {

            edges = (ArrayList<Edge>) adjacencyList.get(n);

            /* iterates in reverse order to avoid null point exceptions, and keep runtime low*/
            for(int index = edges.size() - 1; index > -1; index--) {

                if (edges.get(index).getTo().equals(x)) edges.remove(index);
            }
        }

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {

        if (!adjacencyList.containsKey(x.getFrom()) ||
                !adjacencyList.containsKey(x.getTo())) return false;

        for(Edge e: adjacencyList.get(x.getFrom())) {
            if (e.equals(x)) return false;
        }

        adjacencyList.get(x.getFrom()).add(x);

        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {

        if (!adjacencyList.containsKey(x.getFrom())) return false;
        if (!adjacencyList.get(x.getFrom()).contains(x)) return false;

        adjacencyList.get(x.getFrom()).remove(x);

        return true;
    }

    @Override
    public int distance(Node from, Node to) {

        if (!adjacencyList.containsKey(from)) return 0;

        ArrayList<Edge> edges = (ArrayList)adjacencyList.get(from);

        for(int index = 0; index < edges.size(); index++) {
        if (edges.get(index).getTo().equals(to)) return edges.get(index).getValue();
        }
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
