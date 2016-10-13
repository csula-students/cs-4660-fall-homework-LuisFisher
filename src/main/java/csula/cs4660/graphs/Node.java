package csula.cs4660.graphs;

import java.util.Comparator;

/**
 * The fundamental class to hold data
 *
 * We will be using Generic Programming to hold dynamic type of data --
 * http://www.tutorialspoint.com/java/java_generics.htm
 */
public class Node<T> {
    private final T data;

    // used for search and compare
    public int distance = Integer.MAX_VALUE;
    public int g = 0;
    public int h = 0;
    public Node parent = null;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
            "data=" + data +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node<?> node = (Node<?>) o;

        return getData() != null ? getData().equals(node.getData()) : node.getData() == null;

    }

    @Override
    public int hashCode() {
        return getData() != null ? getData().hashCode() : 0;
    }
}
