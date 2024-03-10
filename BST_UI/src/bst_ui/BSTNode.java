package bst_ui;

/**
 * Class to create and declare a node in the tree
 *
 * @author Le Huu Khoa - CE181099
 */
public class BSTNode {

    //Variable stores node data
    int data;

    //Declare left node, right node
    BSTNode left;
    BSTNode right;

    //Declare a variable that stores the properties of a node
    int count;
    int order;
    int level;

    //Declare the coordinate axis and distance from the button to both sides of its interface
    int x, y, width;
    static final int LEVEL_BY = 60;

    //Declare the parent of node
    BSTNode parent;

    /**
     * Create new node
     *
     * @param data of node
     */
    public BSTNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.count = 1;
        this.order = 0;
        this.level = 0;
        this.parent = null;
    }

    /**
     * Create new node for draw to UI
     *
     * @param data of node
     * @param yMin space between node and element on it
     * @param screenWidth width of Screen
     */
    public BSTNode(int data, int screenWidth, int yMin) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.count = 1;
        this.order = 0;
        this.level = 0;
        this.parent = null;
        //Root note coordinates: x is equal to 1/2 of the user's device screen
        this.x = screenWidth / 2;
        //Root note coordinates: y
        this.y = yMin;
        this.width = screenWidth / 2;
    }

    /**
     * Create new node
     *
     * @param data of node
     * @param count number of nodes with the same data
     */
    public BSTNode(int data, int count) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.count = count;
        this.order = 0;
        this.level = 0;
        this.parent = null;
    }

    /**
     * Check node is leaf
     *
     * @return true if there are no left and right children
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    /**
     * Check node is root
     *
     * @return true if there are no parents
     */
    public boolean isRoot() {
        return this.parent == null;
    }

    /**
     * Check node is inside
     *
     * @return true when it is neither the root nor the leaf node
     */
    public boolean isInside() {
        return !isRoot() && !isLeaf();
    }

    /**
     * Check node has left child
     *
     * @return true if there is a left child
     */
    public boolean hasLeftChild() {
        return this.left != null;
    }

    /**
     * Check node has only left child
     *
     * @return true if there are left children and no right children
     */
    public boolean hasOnlyLeftChild() {
        return this.left != null && this.right == null;
    }

    /**
     * Check node has right child
     *
     * @return true if there is a right child
     */
    public boolean hasRightChild() {
        return this.right != null;
    }

    /**
     * Check node has only right child
     *
     * @return true if there are right children and no left children
     */
    public boolean hasOnlyRightChild() {
        return this.left == null && this.right != null;
    }

    /**
     * Check if there are both left and right children
     *
     * @return true if there are both left and right children
     */
    public boolean hasTwoChild() {
        return this.hasLeftChild() && this.hasRightChild();
    }

    /**
     * Create parent node for node
     *
     * @param parent node to make the parent
     */
    public void setParent(BSTNode parent) {
        this.parent = parent;
        this.level = this.parent.level + 1;
        //Child node distance = parent node distance/2
        this.width = this.parent.width / 2;
        if (this.data < this.parent.data) {
            this.order = this.parent.order * 2 + 1;
            //Left child x coordinate: parent coordinate - child distance
            this.x = this.parent.x - this.width;
        } else {
            this.order = this.parent.order * 2 + 2;
            //Left child x coordinate: parent coordinate - child distance
            this.x = this.parent.x + this.width;
        }
        //y coordinates are the same distance
        this.y = this.parent.y + LEVEL_BY;
    }

    /**
     * Create left child
     *
     * @param left left node
     */
    public void setLeft(BSTNode left) {
        this.left = left;
        //Check the current node, if there is no left child, add it
        if (left != null) {
            this.left.setParent(this);
        }
    }

    /**
     * Create right child
     *
     * @param right right node
     */
    public void setRight(BSTNode right) {
        this.right = right;
        //Check the current node, if there is no right child, add it
        if (right != null) {
            this.right.setParent(this);
        }
    }

    /**
     * Method to find the min node
     *
     * @return the node after finding it
     */
    public BSTNode findMinNode() {
        BSTNode curentNode = this;
        //Browse to the left to find the min node
        while (curentNode.hasLeftChild()) {
            curentNode = curentNode.left;
        }
        return curentNode;
    }

    /**
     * Method to find the min node
     *
     * @return the node after finding it
     */
    public BSTNode findMaxNode() {
        BSTNode curentNode = this;
        //Browse to the right to find the max node
        while (curentNode.hasRightChild()) {
            curentNode = curentNode.right;
        }
        return curentNode;
    }

    /**
     * The method to delete a child node is leaf
     *
     * @param node the node to be checked is the leaf
     * @return true if the node to be checked is a leaf
     */
    public boolean removeLeafChild(BSTNode node) {
        if (node == null) {
            return false;
        }
        if (node.isLeaf()) {
            //If there is a left child, move the left leaf up and delete that node
            if (this.hasLeftChild()) {
                if (this.left.data == node.data) {
                    this.setLeft(null);
                    return true;
                }
            }
            //If there is a right child, move the right leaf up and delete that node
            if (this.hasRightChild()) {
                if (this.right.data == node.data) {
                    this.setRight(null);
                    return true;
                }
            }
        }
        return false;
    }
}
