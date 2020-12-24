import javax.swing.*;
import java.awt.*;

public class TreeDrawer extends JPanel {

    public static final int DEFAULT_NODE_DIAMETER = 40;
    public static final int DEFAULT_NIL_LENGTH = (int) (DEFAULT_NODE_DIAMETER / 1.5);
    public static final int DEFAULT_STROKE_SIZE = 3;
    public static final int MINIMUM_CHILD_DISTANCE = 50;
    public static final int MINIMUM_CHILD_ANGLE = 15;
    public static final int MAXIMUM_CHILD_DISTANCE = 325;
    public static final int MAXIMUM_CHILD_ANGLE = 60;

    public static final Font DEFAULT_NODE_FONT = new Font("Monospaced", Font.BOLD, 17);
    public static final Font DEFAULT_NIL_FONT = new Font("Monospaced", Font.BOLD, 11);

    RBTree tree;
    int x, y; // coordinates of the root

    public TreeDrawer(RBTree tree) {
        this.tree = tree;
    }

    public void draw(Graphics2D g2D, Node root, int x, int y, int level) {
        if (root == null) {
            if (tree.root != null) {
                drawNode(g2D, x, y); // draw nil
            }
            return;
        }

        // calculate distance and angle from this node to its children
        int min_depth = root.getMinDepth();
        int max_depth = root.getMaxDepth();
        double distance = (MAXIMUM_CHILD_DISTANCE) / (level + 1);
        distance *= ((double) min_depth / max_depth);
        distance = Math.max(distance, MINIMUM_CHILD_DISTANCE);
        distance = Math.min(distance, MAXIMUM_CHILD_DISTANCE);

        double angle = Math.toRadians(MINIMUM_CHILD_ANGLE * min_depth);
        angle *= ((double) max_depth / min_depth);
        angle = Math.max(angle, Math.toRadians(MINIMUM_CHILD_ANGLE));
        angle = Math.min(angle, Math.toRadians(MAXIMUM_CHILD_ANGLE));

        int left_x = (int) (x - distance * Math.sin(angle));
        int right_x = (int) (x + distance * Math.sin(angle));
        int new_y = (int) (y + distance * Math.cos(angle));


        // draw edges between nodes
        int length;

        // draw left edge
        if (root.getLeft() != null) {
            length = DEFAULT_NODE_DIAMETER / 2;
        } else {
            length = DEFAULT_NIL_LENGTH / 2;
        }
        int x1 = (int) (x - length * Math.sin(angle));
        int x2 = (int) (left_x + length * Math.sin(angle));
        int y1 = (int) (y + length * Math.cos(angle));
        int y2 = (int) (new_y - length * Math.cos(angle));
        g2D.drawLine(x1, y1, x2, y2);

        // draw right edge
        if (root.getRight() != null) {
            length = DEFAULT_NODE_DIAMETER / 2;
        } else {
            length = DEFAULT_NIL_LENGTH / 2;
        }
        x1 = (int) (x + length * Math.sin(angle));
        x2 = (int) (right_x - length * Math.sin(angle));
        y1 = (int) (y + length * Math.cos(angle));
        y2 = (int) (new_y - length * Math.cos(angle));
        g2D.drawLine(x1, y1, x2, y2);

        // draw the node itself
        drawNode(g2D, root, x, y);

        draw(g2D, root.getLeft(), left_x, new_y, level + 1);
        draw(g2D, root.getRight(), right_x, new_y, level + 1);
    }

    public void drawNode(Graphics2D g2D, Node node, int x, int y) {

        int diameter = DEFAULT_NODE_DIAMETER;

        // draw node circle
        // to draw the circle centered at the coordinates
        x -= diameter / 2;
        y -= diameter / 2;

        if (node.isRed()) {
            g2D.setColor(Color.red);
        } else {
            g2D.setColor(Color.black);
        }

        // fill before stroke so that stroke is on top
        g2D.fillOval(x, y, diameter, diameter);
        g2D.setColor(Color.black);
        g2D.drawOval(x, y, diameter, diameter);

        FontMetrics metrics = g2D.getFontMetrics(DEFAULT_NODE_FONT);

        // draw node text
        String text = String.valueOf(node.key);
        int new_x = x + (diameter - metrics.stringWidth(text)) / 2;
        int new_y = y + ((diameter - metrics.getHeight()) / 2) + metrics.getAscent();

        g2D.setFont(DEFAULT_NODE_FONT);

        g2D.setColor(Color.white);
        g2D.drawString(text, new_x, new_y);
        g2D.setColor(Color.black);
    }

    // overridden function for drawing the nil node
    public void drawNode(Graphics2D g2D, int x, int y) {

        int length = DEFAULT_NIL_LENGTH;

        // to draw the rectangle centered at the coordinates
        x -= length / 2;
        y -= length / 2;

        g2D.setColor(Color.black);

        // fill before stroke so that stroke is on top
        g2D.fillRect(x, y, length, length);
        g2D.drawRect(x, y, length, length);

        String text = "NIL";

        FontMetrics metrics = g2D.getFontMetrics(DEFAULT_NIL_FONT);
        int new_x = x + (length - metrics.stringWidth(text)) / 2;
        int new_y = y + ((length - metrics.getHeight()) / 2) + metrics.getAscent();

        // draw node text
        g2D.setFont(DEFAULT_NIL_FONT);
        g2D.setColor(Color.white);
        g2D.drawString(text, new_x, new_y);
        g2D.setColor(Color.black);
        g2D.setFont(DEFAULT_NODE_FONT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2D.setRenderingHints(rh);
        g2D.setStroke(new BasicStroke(DEFAULT_STROKE_SIZE));
        g2D.setColor(Color.black);

        draw(g2D, tree.root, x, y, 0);
    }
}
