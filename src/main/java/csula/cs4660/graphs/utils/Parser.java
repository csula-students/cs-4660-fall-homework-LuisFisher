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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

            Edge edge1;
            Edge edge2;

            int y = 0;
            int x = 0;

            curLine = reader.readLine();

            x = (curLine.length() + 2) / 2;

            ArrayList<Node> allNodes = new ArrayList<Node>();


            while (curLine != null) {

                for (int index = 0, i = 0; index < curLine.length(); index+=2, i++) {

                    char char1;
                    char char2;

                    switch (curLine.charAt(index)) {

                        case '+':
                        case '|':
                            index--;
                            char1 = '#';
                            char2 = '#';
                            break;
                        case '-':
                            char1 = '#';
                            break;
                        default: char1 = curLine.charAt(index);
                    }

                    switch (curLine.charAt(index + 1)) {

                        case '+':
                        case '|':
                            char1 = '#';
                            char2 = '#';
                            break;
                        case '-':
                            char2 = '#';
                            break;
                        default: char2 = curLine.charAt(index + 1);
                    }

                    String value = char1 + "" + char2;

                    tile = new Tile(i,y,value);
                    node = new Node(tile);

                    allNodes.add(node);
                }

                y++;
                curLine = reader.readLine();
            }

            graph.addAllNode(allNodes);

            System.out.println(x + " " + y);

//            System.out.println(allNodes.size());
            System.out.println(graph.getNodes().size());
//

            System.out.println();

            for(int j = 0; j < y; j++) {
                for(int i = 0; i < x; i++) {
                    Node n = graph.getNode(i + j*x).get();
                    Tile t = (Tile) n.getData();
                    System.out.print(t.getType());
                }
                System.out.println();
                }

//                        Node b = graph.getNode(i + j*y).get();
//
//            System.out.println(((Tile)graph.getNode(allNodes.size() - 2 - x).get().getData()).getType());


            // adds edges to grid
            // comment out to check for heap issues
//            for(int j = 0; j < y; j++) {
//                for(int i = 0; i < x; i++) {
//
//                    // should be x*j
//                    // adds horizontal edges
//                    if (i > 0) {
//                        Node l = graph.getNode(i - 1 + j*y).get();
//                        Node r = graph.getNode(i + j*y).get();
//
//                        Edge e1 = new Edge(l,r, 1);
//                        Edge e2 = new Edge(r,l, 1);
//
//                        graph.addEdge(e1);
//                        graph.addEdge(e2);
//                    }
//
//                    // adds vertical edges
//                    if (j > 0) {
//
//                        Node t = graph.getNode(i + j*y - x).get();
//                        Node b = graph.getNode(i + j*y).get();
//
//                        Edge e1 = new Edge(t,b, 1);
//                        Edge e2 = new Edge(b,t, 1);
//
//                        graph.addEdge(e1);
//                        graph.addEdge(e2);
//                    }
//                }
//            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

    public static String converEdgesToAction(Collection<Edge> edges) {
        // TODO: convert a list of edges to a list of action
        return "";
    }
}
