package csula.cs4660.graphs.utils;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A quick parser class to read different format of files
 */
public class Parser {

    public static Graph readRectangularGridFile(Representation.STRATEGY graphRepresentation, File file) {

        Graph graph = new Graph(Representation.of(graphRepresentation));

        BufferedReader reader;
        String curLine;

        try {
            reader = new BufferedReader(new FileReader(file));

            Tile tile;
            Node node;

            ArrayList<Node> allNodes = new ArrayList<Node>();

            curLine = reader.readLine();

            int x = (curLine.length() - 2) / 2;
            int y = -1;

            while (curLine != null) {

                for (int index = 0, i = 0; index < curLine.length(); index+=2, i++) {

                    char char1;
                    char char2;

                    switch (curLine.charAt(index)) {

                        case '+':
                        case '|':
                            index--;
                            continue;
                        case '-':
                            continue;
                    }

                    char1 = curLine.charAt(index);
                    char2 = curLine.charAt(index + 1);

                    String value = char1 + "" + char2;

                    tile = new Tile(i - 1,y,value);
                    node = new Node(tile);

                    allNodes.add(node);
                }

                y++;
                curLine = reader.readLine();
            }

            y--;

            // adds all nodes at once, used to make matrix creation faster
            graph.addAllNode(allNodes);

            // adds edges to others in order NEWS
            for(int j = 0; j < y; j++) {
                for(int i = 0; i < x; i++) {

                    Node current = graph.getNode(i + j*x).get();

                    if (j > 0) {
                        Node north = graph.getNode(i + j*x - x).get();
                        graph.addEdge(new Edge(current,north, 1));
                    }
                    if (i < x - 1) {
                        Node east = graph.getNode(i + 1 + j*x).get();
                        graph.addEdge(new Edge(current,east, 1));
                    }
                    if (i > 0) {
                        Node west = graph.getNode(i - 1 + j*x).get();
                        graph.addEdge(new Edge(current,west, 1));
                    }
                    if (j < y - 1) {
                        Node south = graph.getNode(i + j*x + x).get();
                        graph.addEdge(new Edge(current,south, 1));
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static String converEdgesToAction(Collection<Edge> edges) {

        String path = "";

        Iterator<Edge> edgeIter = edges.iterator();

        Edge e = null;

        while  (edgeIter.hasNext()) {

            e = edgeIter.next();

            Tile from = (Tile)e.getFrom().getData();
            Tile to = (Tile)e.getTo().getData();

            double dx = from.getX() - to.getX();
            double dy = from.getY() - to.getY();

            if (dy == 1) path += 'N';
            else if (dx == -1) path += 'E';
            else if (dx == 1) path += 'W';
            else if (dy == -1 )path+= 'S';
            }

        return path;
    }
}
