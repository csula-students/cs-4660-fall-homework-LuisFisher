package csula.cs4660.graphs.utils;

import csula.cs4660.graphs.Node;

import java.util.Comparator;

/**
 * Created by luisf on 10/9/2016.
 */
public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return o2.distance - o1.distance;
    }
}
