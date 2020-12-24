public class Node {

    int key;
    boolean color; // 0 for black, 1 for red

    Node left;
    Node right;
    Node parent;

    public Node() {
    }

    public Node(int key) {
        this.key = key;
        this.color = true;//as any node at the beginning is red .
    }

    public Node(int key, boolean isRed) {
        this.key = key;
        this.color = isRed;
    }

    public void makeLeftChild(Node left_child) {
        this.left = left_child;
        this.left.parent = this;
    }

    public void makeRightChild(Node right_child) {
        this.right = right_child;
        this.right.parent = this;
    }

    public void removeLeftChild() {
        this.left.parent = null;
        this.left = null;
    }

    public void removeRightChild() {
        this.right.parent = null;
        this.right = null;
    }

    public void makeRed() {
        this.color = true;
    }

    public void makeBlack() {
        this.color = false;
    }

    public boolean isRed() {
        return color;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }

    public int getKey() {

        return key;
    }

    public void setLeft(Node x) {
        // TODO Auto-generated method stub
        this.left = x;
    }

    public void setRight(Node x) {
        // TODO Auto-generated method stub
        this.right = x;
    }

    public int getMinDepth() {
        if (getLeft() == null || getRight() == null) {
            return 1;
        }

        return 1 + Math.min(right.getMinDepth(), left.getMinDepth());
    }

    // gets maximum height of a tree with this node as its root
    public int getMaxDepth() {
        if (getLeft() == null && getRight() == null) {
            return 1;
        }

        if (right == null) {
            return 1 + left.getMaxDepth();
        }

        if (left == null) {
            return 1 + right.getMaxDepth();
        }

        return 1 + Math.max(right.getMaxDepth(), left.getMaxDepth());
    }

    public void setParent(Node node) {
        // TODO Auto-generated method stub
        this.parent = node;
    }

    public void setKey(int key2) {
        // TODO Auto-generated method stub
        this.key = key2;
    }
}
