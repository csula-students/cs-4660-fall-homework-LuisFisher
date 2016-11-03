package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Collection;


/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes = {};
    private int[][] adjacencyMatrix = {};

    public AdjacencyMatrix(File file) {
        readFile(file);
    }

    public AdjacencyMatrix() {
    }

    private void readFile(File file) {

        BufferedReader reader;
        String curLine;
        int nodeCount;

        try {
            reader = new BufferedReader(new FileReader(file));

            // ArrayList<Node<Integer>> nodes = new ArrayList<Node<Integer>>();
            Node<Integer> tempNode;
            Edge tempEdge;

            curLine = reader.readLine();
            nodeCount = Integer.parseInt(curLine);

            nodes = new Node[nodeCount];
            adjacencyMatrix =  new int[nodeCount][nodeCount];

            for (int[] row : adjacencyMatrix) {
                Arrays.fill(row, 0);
            }

            for(int index = 0; index < nodes.length; index++) {
                Node node = new Node<Integer>(index);
                nodes[index] = node;
            }

            while ((curLine = reader.readLine()) != null) {

                String[] sRow = curLine.split(":");

                int length = sRow.length;

                int[] rowValues = new int[length];

                for(int index = 0; index < length; index++) {
                    rowValues[index] = Integer.parseInt(sRow[index]);
                }

                adjacencyMatrix[rowValues[0]][rowValues[1]] = rowValues[2];
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean adjacent(Node x, Node y) {

        int xData = (int)x.getData();
        int yData = (int)y.getData();

        return ((adjacencyMatrix[xData][yData] != 0) || (adjacencyMatrix[yData][xData] != 0));
    }

    @Override
    public List<Node> neighbors(Node x) {

        boolean inArray = false;
        int positionX = 0;

        for(int index = 0; index < nodes.length; index++) {
            if (nodes[index].equals(x)) {
                inArray = true;
                positionX = index;
                break;
            }
        }

        if (!inArray) return null;

        ArrayList<Node> neighbors = new ArrayList<Node>();

        for(int index = 0; index <  adjacencyMatrix[positionX].length; index++) {
            if (adjacencyMatrix[positionX][index] > 0) neighbors.add(nodes[index]);
        }
        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {

        for(int index = 0; index < nodes.length; index++) {
            if (nodes[index].equals(x)) return false;
        }

        Node[] newNodes = new Node[nodes.length + 1];
        int[][] newMatrix = new int[nodes.length + 1][nodes.length + 1];



        for(int index = 0; index < nodes.length; index++) {
            newNodes[index] = nodes[index];
        }

        newNodes[nodes.length] = x;

        for(int i = 0;  i < adjacencyMatrix.length; i++) {

            for(int j = 0; j < adjacencyMatrix[0].length; j++) {

                newMatrix[i][j] = adjacencyMatrix[i][j];
            }

            newMatrix[i][adjacencyMatrix[0].length] = 0;
        }

        Arrays.fill(newMatrix[adjacencyMatrix.length], 0);

        nodes = newNodes;
        adjacencyMatrix = newMatrix;

        return true;
    }

    @Override
    public boolean addAllNode(List<Node> x) {

        boolean added = false;
        List<Node> temp = Arrays.asList(nodes);

        List<Node> toAdd = new ArrayList<Node>();
        for (Node n: x) {
            if (!temp.contains(n))  {
                toAdd.add(n);
                added = true;
            }
        }

        int oldSize = nodes.length;
        int newSize = oldSize  + toAdd.size();

        Node[] newNodes = new Node[newSize];

        for (int index = 0; index < newSize; index++) {

            if (index < oldSize) {
                newNodes[index] = nodes[index];
            }
            else {
                newNodes[index] = x.get(index - oldSize);
            }
        }

        nodes = newNodes;

        int[][] newMatrix = new int[newSize][newSize];

        for(int i = 0;  i < adjacencyMatrix.length; i++) {

            for(int j = 0; j < adjacencyMatrix[0].length; j++) {

                newMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }

        adjacencyMatrix = newMatrix;

        return added;
    }

    @Override
    public boolean removeNode(Node x) {

        boolean inArray = false;
        int positionX = 0;

        for(int index = 0; index < nodes.length; index++) {
            if (nodes[index].equals(x)) {
                inArray = true;
                positionX = index;
                break;
            }
        }

        if (!inArray) return false;

        Node[] newNodes = new Node[nodes.length - 1];
        int[][] newMatrix = new int[nodes.length - 1][nodes.length - 1];

        for(int indexOld = 0, indexNew = 0; indexOld < nodes.length; indexOld++, indexNew++) {

            if (indexOld == positionX) {
                indexNew--;
                continue;
            }

            newNodes[indexNew] = nodes[indexOld];
        }

        for(int iOld = 0, iNew = 0;  iOld < adjacencyMatrix.length; iOld++, iNew++) {

            if (iOld == positionX) {
                iNew--;
                continue;
            }

            for(int jOld = 0, jNew = 0; jOld < adjacencyMatrix[0].length; jOld++, jNew++) {

                if (jOld == positionX) {
                    jNew--;
                    continue;
                }

                newMatrix[iNew][jNew] = adjacencyMatrix[iOld][jOld];
            }
        }

        nodes = newNodes;
        adjacencyMatrix = newMatrix;

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {

        boolean fromInArray = false;
        boolean toInArray = false;

        int fromPos = 0;
        int toPos = 0;

        for(int index = 0; index < nodes.length; index++) {

            if (nodes[index].equals(x.getFrom())) {
                fromInArray = true;
                fromPos = index;
            }

            if (nodes[index].equals(x.getTo())) {
                toInArray = true;
                toPos = index;
            }
        }

        if (fromInArray && toInArray && (adjacencyMatrix[fromPos][toPos] == 0)) {
            adjacencyMatrix[fromPos][toPos] = x.getValue();

            return true;
        }

        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        boolean fromInArray = false;
        boolean toInArray = false;

        int fromPos = 0;
        int toPos = 0;

        for(int index = 0; index < nodes.length; index++) {

            if (nodes[index].equals(x.getFrom())) {
                fromInArray = true;
                fromPos = index;
            }

            if (nodes[index].equals(x.getTo())) {
                toInArray = true;
                toPos = index;
            }
        }

        if (fromInArray && toInArray && (adjacencyMatrix[fromPos][toPos] != 0)) {
            adjacencyMatrix[fromPos][toPos] = 0;
            return true;
        }

        return false;
    }

    @Override
    public int distance(Node from, Node to) {

        boolean fromInArray = false;
        boolean toInArray = false;

        int fromPos = 0;
        int toPos = 0;

        for(int index = 0; index < nodes.length; index++) {

            if (nodes[index].equals(from)) {
                fromInArray = true;
                fromPos = index;
            }

            if (nodes[index].equals(to)) {
                toInArray = true;
                toPos = index;
            }
        }

        return adjacencyMatrix[fromPos][toPos];
    }

    @Override
    public Collection<Node> getNodes() {
       return Arrays.asList(nodes);
    }

    @Override
    public Optional<Node> getNode(int index) {
        return Optional.of(nodes[index]);
    }

    @Override
    public Optional<Node> getNode(Node node) {
        Iterator<Node> iterator = Arrays.asList(nodes).iterator();
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
