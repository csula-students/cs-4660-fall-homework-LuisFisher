package csula.cs4660.graphs.utils;

import csula.cs4660.graphs.Node;

import java.util.Comparator;

/**
 * Created by luisf on 10/9/2016.
 */
public class NodeComparator implements Comparator<Node> {

    boolean reverse;

    public  NodeComparator() {
        reverse = false;

    }

    public NodeComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(Node o1, Node o2){

        double f1 = o1.g + o1.h;
        double f2 = o2.g + o2.h;

        if (reverse) return (int)(f1 - f2);
        return (int)(f2 - f1);
    }
}