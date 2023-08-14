package common;

import java.util.Objects;

public class Node {
    public int key;
    public int val;
    public Node next;
    public Node prev;
    public Node left;
    public Node right;
    public Node parent;

    public Node(int key) {
        this.key = key;
    }

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return key == node.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}