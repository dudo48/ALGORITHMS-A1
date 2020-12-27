import javax.swing.*;

public class Window extends JFrame {
    public static final int DEFAULT_WINDOW_WIDTH = 1000;
    public static final int DEFAULT_WINDOW_HEIGHT = 700;
    public static final int DEFAULT_BUTTON_WIDTH = 80;
    public static final int DEFAULT_BUTTON_HEIGHT = 25;
    public static final int DEFAULT_TEXTFIELD_WIDTH = 50;
    public static final int DEFAULT_TEXTFIELD_HEIGHT = 25;
    public static final int DEFAULT_ELEMENT_DISTANCE = 10;

    JButton insert_button = new JButton("Insert");
    JButton delete_button = new JButton("Delete");
    JButton clear_button = new JButton("Clear");
    JTextField input_field = new JTextField();


    public Window(TreeDrawer tree_drawer) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        // tree position
        tree_drawer.x = getWidth() / 2;
        tree_drawer.y = getHeight() / 6;
        if(tree_drawer.tree.root != null) {
            tree_drawer.y -= tree_drawer.tree.root.getMaxDepth() * 8;
        }
        tree_drawer.y = Math.max(tree_drawer.y, TreeDrawer.DEFAULT_NODE_DIAMETER / 2);

        // set other elements positions
        int x = 10;
        int y = 5;

        input_field.setBounds(x, y, DEFAULT_TEXTFIELD_WIDTH, DEFAULT_TEXTFIELD_HEIGHT);

        x += DEFAULT_ELEMENT_DISTANCE + DEFAULT_TEXTFIELD_WIDTH;
        insert_button.setBounds(x, y, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);

        x += DEFAULT_ELEMENT_DISTANCE + DEFAULT_BUTTON_WIDTH;
        delete_button.setBounds(x, y, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);

        x += DEFAULT_ELEMENT_DISTANCE + DEFAULT_BUTTON_WIDTH;
        clear_button.setBounds(x, y, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);

        // set action listeners
        insert_button.addActionListener(e -> {
            try {
                int key = Integer.parseInt(input_field.getText());
                tree_drawer.tree.insertNode(key);
                tree_drawer.y = getHeight() / 4 - tree_drawer.tree.root.getMaxDepth() * 8;
                tree_drawer.y = Math.max(tree_drawer.y, TreeDrawer.DEFAULT_NODE_DIAMETER / 2);
                repaint();
            } catch (NumberFormatException ignored) {
            }

            input_field.setText("");
        });
        delete_button.addActionListener(e -> {
            try {
                int key = Integer.parseInt(input_field.getText());
                tree_drawer.tree.deleteKey(key);
                tree_drawer.y = getHeight() / 4 - tree_drawer.tree.root.getMaxDepth() * 8;
                tree_drawer.y = Math.max(tree_drawer.y, TreeDrawer.DEFAULT_NODE_DIAMETER / 2);
                repaint();
            } catch (NumberFormatException ignored) {
            }

            input_field.setText("");
        });
        clear_button.addActionListener(e -> {
            tree_drawer.tree.clear();
            repaint();
        });

        // add elements to window
        add(insert_button);
        add(delete_button);
        add(clear_button);
        add(input_field);
        add(tree_drawer);

        setVisible(true);
    }
}
