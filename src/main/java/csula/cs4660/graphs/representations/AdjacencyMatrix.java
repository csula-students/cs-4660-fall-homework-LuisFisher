package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 *
 * TODO: please fill the method body of this class
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes;
    private int[][] adjacencyMatrix;

    public AdjacencyMatrix(File file) {
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
                nodes[index] = new Node<Integer>(index);
            }

            while ((curLine = reader.readLine()) != null) {

                String[] sRow = curLine.split(":");

                int length = sRow.length;

                int[] rowValues = new int[length];

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

        return ((adjacencyMatrix[xData][yData] != 0) || (adjacencyMatrix[yData][xData] != 0))
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

        Node[] newNodes = new Node[nodes.length + 1];
        int[][] newMatrix = new int[nodes.length + 1][nodes.length + 1];

        newNodes[nodes.length] = x;

        for(int index = 0; index < nodes.length; index++) {
            newNodes[index] = nodes[index];
        }


        for(int i = 0;  i < adjacencyMatrix.length; i++) {
            for(int j = 0; j < adjacencyMatrix[0].length; j++) {

            }
        }


        nodes = newNodes;
        adjacencyMatrix = newMatrix;

        return false;
    }

    @Override
    public boolean removeNode(Node x) {
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
