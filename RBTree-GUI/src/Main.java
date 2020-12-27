public class Main {

    public static void constructTree(RBTree tree, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = (low + high) / 2;
        tree.insertNode(mid);

        constructTree(tree, mid + 1, high);
        constructTree(tree, low, mid);
    }

    public static void main(String[] args) {
        RBTree tree = new RBTree();

        int n = 0;
        int node_count = (int) (Math.pow(2, n + 1) - 1);
        // constructTree(tree, 0, node_count);

        TreeDrawer drawer = new TreeDrawer(tree);
        Window window = new Window(drawer);
    }
}
