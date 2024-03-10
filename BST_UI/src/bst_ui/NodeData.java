package bst_ui;

/**
 * Class to format a node in the tree
 *
 * @author Le Huu Khoa - CE181099
 */
public class NodeData {

    //Format variables for storage
    int data;
    int count;

    /**
     * Method for formatting a node
     *
     * @param data data in node
     * @param count number of nodes with the same data
     */
    public NodeData(int data, int count) {
        this.data = data;
        this.count = count;
    }
}
