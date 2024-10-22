
public class BPlusTree {
    private Node root;

    public static class Node {
        private int order;
        private int size;
        private int[] keys;
        private Node[] children;
    }
}
