package bst_ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * Class to draw the tree to the interface
 *
 * @author Le Huu Khoa - CE181099
 */
public class BSTreeDraw extends JPanel {

    //Initialize properties to draw a tree
    BSTree tree;
    int screenWidth;
    int yMin;
    //Properties to draw lines/circles..
    Graphics2D g;
    static final int r = 16;

    /**
     * Constructor to drawing the tree
     *
     * @param tree trees to draw
     * @param screenWidth user's screen width
     * @param yMin Vertical axis value
     */
    public BSTreeDraw(BSTree tree, int screenWidth, int yMin) {
        this.tree = tree;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
    }

    /**
     * Add nodes to the tree to draw
     *
     * @param data node data
     */
    public void addNode(int data) {
        tree.addNode(data);
        repaint();
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     * @param font
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    /**
     * Tree drawing method
     *
     * @param node
     */
    public void drawNode(BSTNode node) {
        if (node == null) {
            return;
        }
        int x = node.x;
        int y = node.y;
        //Line branch line color
        g.setColor(Color.black);
        //Draw left branch
        if (node.hasLeftChild()) {
            //x,y coordinates of the node under consideration go to the x y coordinates of the left child
            g.drawLine(x, y, node.left.x, node.left.y);
        }
        //Draw right branch
        if (node.hasRightChild()) {
            //x,y coordinates of the node under consideration go to the x y coordinates of the right child
            g.drawLine(x, y, node.right.x, node.right.y);
        }
        //Draw a circle
        //Circle background color
        g.setColor(Color.white);
        //Must -r to prevent the circle from being skewed
        g.fillOval(x - r, y - r, r * 2, r * 2);

        //Draw a circle border
        g.setColor(Color.black);
        g.drawOval(x - r, y - r, r * 2, r * 2);

        //Draw data
        //Font font name, style, font size
        drawCenteredString(g, node.data + "", new Rectangle(x - r, y - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
        //Draw count of node
        drawCenteredString(g, "c=" + node.count, new Rectangle(x - r, y + 25 - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));

        //Recursive
        drawNode(node.left);
        drawNode(node.right);
    }

    /**
     * Method to draw max node
     */
    public void findMaxNode() {
        tree.maxNode = tree.minNode = null;
        tree.maxNode = tree.root.findMaxNode();
        if (tree.maxNode != null) {
            int x = tree.maxNode.x;
            int y = tree.maxNode.y;
            //Draw the path to maxNode
            //Circle background color
            g.setColor(Color.red);
            //Must -r to prevent the circle from being skewed
            g.fillOval(x - r, y - r, r * 2, r * 2);

            //Draw a circle border
            g.setColor(Color.yellow);
            g.drawOval(x - r, y - r, r * 2, r * 2);

            //Draw data
            //Font font name, style, font size
            drawCenteredString(g, tree.maxNode.data + "", new Rectangle(x - r, y - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
            //Draw count of node
            drawCenteredString(g, "c=" + tree.maxNode.count, new Rectangle(x - r, y + 25 - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
        }
        repaint();
    }

    /**
     * Method to draw min node
     */
    public void findMinNode() {
        tree.maxNode = tree.minNode = null;
        tree.minNode = tree.root.findMinNode();
        if (tree.minNode != null) {
            int x = tree.minNode.x;
            int y = tree.minNode.y;
            //Draw the path to minNode
            //Circle background color
            g.setColor(Color.red);
            //Must -r to prevent the circle from being skewed
            g.fillOval(x - r, y - r, r * 2, r * 2);

            //Draw a circle border
            g.setColor(Color.yellow);
            g.drawOval(x - r, y - r, r * 2, r * 2);

            //Draw data
            //Font font name, style, font size
            drawCenteredString(g, tree.minNode.data + "", new Rectangle(x - r, y - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
            //Draw count of node
            drawCenteredString(g, "c=" + tree.minNode.count, new Rectangle(x - r, y + 25 - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
        }
        repaint();
    }

    /**
     * Method to draw a path to the node you want to find
     */
    public void drawPathSearch() {
        if (tree.listNodeFind != null) {
            g.setColor(Color.red);
            BSTNode n1, n2;
            for (int i = 1; i < tree.listNodeFind.size(); i++) {
                //To draw the first node of the line leading to the next node, there must be data from the first node and the next node
                n1 = tree.listNodeFind.get(i - 1);
                n2 = tree.listNodeFind.get(i);
                g.drawLine(n1.x, n1.y, n2.x, n2.y);
            }

            for (int i = 0; i < tree.listNodeFind.size(); i++) {
                n1 = tree.listNodeFind.get(i);
                int x = n1.x;
                int y = n1.y;
                //Circle background color
                g.setColor(Color.red);
                //Must -r to prevent the circle from being skewed
                g.fillOval(x - r, y - r, r * 2, r * 2);

                //Draw a circle border
                g.setColor(Color.yellow);
                g.drawOval(x - r, y - r, r * 2, r * 2);

                //Draw data
                //Font font name, style, font size
                drawCenteredString(g, n1.data + "", new Rectangle(x - r, y - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));
                //Draw count of node
                drawCenteredString(g, "c=" + n1.count, new Rectangle(x - r, y + 25 - r, r * 2, r * 2), new Font("Arial", Font.PLAIN, 12));

            }
        }
        repaint();
    }

    /**
     * Method to delete nodes in the tree
     *
     * @param data Node data needs to be deleted
     */
    public void deleteNode(int data) {
        tree.removeNode(data);
        repaint();
    }

    /**
     * Balancing Nodes on the tree
     */
    public void balance() {
        tree.balanceNodeData();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = (Graphics2D) g;
        //Draw the tree starting from the root node
        drawNode(tree.root);
        //min - max
        if (tree.maxNode != null) {
            findMaxNode();
        }
        if (tree.minNode != null) {
            findMinNode();
        }

        //Search node
        if (tree.listNodeFind != null) {
            drawPathSearch();
        }

    }

}
