package bst_ui;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class to create a new tree
 *
 * @author Le Huu Khoa - CE181099
 */
public class BSTree {

    //Root node
    BSTNode root;

    //Declare a string to store the results of inorder, preorder and postorder
    String resultPre = "";
    String resultIn = "";
    String resultPost = "";

    //Declare coordinates to draw notes
    int screenWidth;
    int yMin;

    //Largest and smallest node
    BSTNode maxNode;
    BSTNode minNode;

    //Declare a string to store the results of BFS and DFS
    String resultDFS = "";
    String resultBFS = "";
    String resultBFSOrder = "";
    String resultDFSOrder = "";
    String resultFindNode = "";

    //Declares a string to store the results of the node's parents browsing in preorder style
    String resultPreOrderParent = "";

    //Create an ArrayList to store the elements
    ArrayList<BSTNode> listNodeFind = new ArrayList<>();
    ArrayList<BSTNode> listNode = new ArrayList<>();
    ArrayList<NodeData> listNodeData = new ArrayList<>();

    int searchKey;

    /**
     * Create a new tree
     */
    public BSTree() {
        this.root = null;
    }

    /**
     * Create a new tree
     *
     * @param screenWidth width of your device screen
     * @param yMin coordinates of the vertical axis when drawing the node
     */
    public BSTree(int screenWidth, int yMin) {
        this.root = null;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
    }

    /**
     * Add new node to tree
     *
     * @param data node data
     */
    public void addNode(int data) {
        //Check if there is no root node then the added node will be the root node
        if (root == null) {
            BSTNode newNode = new BSTNode(data, screenWidth, yMin);
            this.root = newNode;
        } else {
            BSTNode currentNode = root;
            boolean isAdded = false;
            while (!isAdded) {
                //Create left node
                if (data < currentNode.data) {
                    //Check if the current node has a left child
                    if (currentNode.hasLeftChild()) {
                        //If yes, then go to the next node to consider
                        currentNode = currentNode.left;
                    } else {
                        //If not, add node
                        BSTNode newNode = new BSTNode(data);
                        currentNode.setLeft(newNode);
                        isAdded = true;
                    }
                }//Create right note 
                else if (data > currentNode.data) {
                    //Check if the current node has a right child
                    if (currentNode.hasRightChild()) {
                        //If yes, then go to the next node to consider
                        currentNode = currentNode.right;
                    } else {
                        //If not, add node
                        BSTNode newNode = new BSTNode(data);
                        currentNode.setRight(newNode);
                        isAdded = true;
                    }
                } else {
                    //Consider adding duplicate value nodes
                    currentNode.count++;
                    isAdded = true;
                }
            }
        }
    }

    /**
     * Add new node to tree
     *
     * @param data node data
     * @param count number of nodes with the same data
     */
    public void addNode(int data, int count) {
        //Check if there is no root node then the added node will be the root node
        if (root == null) {
            BSTNode newNode = new BSTNode(data, screenWidth, yMin);
            this.root = newNode;
        } else {
            BSTNode currentNode = root;
            boolean isAdded = false;
            while (!isAdded) {
                //Create left node
                if (data < currentNode.data) {
                    //Check if the current node has a left child
                    if (currentNode.hasLeftChild()) {
                        //If yes, then go to the next node to consider
                        currentNode = currentNode.left;
                    } else {
                        //If not, add node
                        BSTNode newNode = new BSTNode(data, count);
                        currentNode.setLeft(newNode);
                        isAdded = true;
                    }
                }//Create right note  
                else if (data > currentNode.data) {
                    //Check if the current node has a right child
                    if (currentNode.hasRightChild()) {
                        //If yes, then go to the next node to consider
                        currentNode = currentNode.right;
                    } else {
                        //If not, add node
                        BSTNode newNode = new BSTNode(data, count);
                        currentNode.setRight(newNode);
                        isAdded = true;
                    }
                } else {
                    //Consider adding duplicate value nodes
                    currentNode.count++;
                    isAdded = true;
                }
            }
        }
    }

    /**
     * Traverse the tree according to the path preOrder: root-left-right
     *
     * @param node node is being Traverse
     */
    public void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.count; i++) {
            resultPre += "," + node.data;
        }
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * Traverse the tree to find the parent of the node in preOrder style
     *
     * @param node node is being Traverse
     */
    public void preOrderParent(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.count; i++) {
            if (node.isRoot()) {
                resultPreOrderParent += "," + node.data + "[null]";
            } else {
                resultPreOrderParent += "," + node.data + "[" + node.parent.data + "]";
            }
        }
        preOrderParent(node.left);
        preOrderParent(node.right);
    }

    /**
     * Call the tree traversal function to find the node's parents in the
     * preOrder style without parameters
     */
    public void preOrderParent() {
        preOrderParent(root);
    }

    /**
     * Traverse the tree according to the path inOrder: left-root-right
     *
     * @param node node is being Traverse
     */
    public void inOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        for (int i = 0; i < node.count; i++) {
            resultIn += "," + node.data;
        }
        inOrder(node.right);
    }

    /**
     * Traverse the tree according to the path postOrder: left-right-root
     *
     * @param node node is being Traverse
     */
    public void postOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        for (int i = 0; i < node.count; i++) {
            resultPost += "," + node.data;
        }
    }

    /**
     * DFS: Tree traversal in stack style (LiFo):Last in First Out
     */
    public void DFS() {
        Stack<BSTNode> s = new Stack<>();
        s.add(root);
        BSTNode curentNode;
        //Traversal until there are no more objects in the stack (where it is saved) to consider
        while (!s.isEmpty()) {
            //Push the node out for traversal
            curentNode = s.pop();
            if (curentNode != null) {
                //When traversal, give priority to large numbers first, small numbers last => to get small numbers out first
                for (int i = 0; i < curentNode.count; i++) {
                    resultDFS += "," + curentNode.data;
                }
                //Add new node to the stack
                s.add(curentNode.right);
                s.add(curentNode.left);
            }
        }
    }

    /**
     * BFS: Tree traversal in queue style (FiFo):First in First Out
     */
    public void BFS() {
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        BSTNode curentNode;
        //Traversal until there are no more objects in the queue (where it is saved) to consider
        while (!q.isEmpty()) {
            //Push the node out for traversal
            curentNode = q.poll();
            if (curentNode != null) {
                //When traversal, give priority to small numbers first, big numbers last => to get small numbers out first
                for (int i = 0; i < curentNode.count; i++) {
                    resultBFS += "," + curentNode.data;
                }
                //Add new node to the queue
                q.add(curentNode.left);
                q.add(curentNode.right);
            }

        }
    }

    /**
     * DFS: Tree traversal in stack style (LiFo): Last in First Out and has
     * additional orders
     */
    public void BFSOrder() {
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        BSTNode curentNode;
        //Traversal until there are no more objects in the stack (where it is saved) to consider
        while (!q.isEmpty()) {
            //Push the node out for traversal
            curentNode = q.poll();
            if (curentNode != null) {
                //When traversal, give priority to large numbers first, small numbers last => to get small numbers out first
                for (int i = 0; i < curentNode.count; i++) {
                    resultBFSOrder += "," + curentNode.data + "[" + curentNode.order + "]";
                }
                //Add new node to the stack
                q.add(curentNode.left);
                q.add(curentNode.right);
            }

        }
    }

    /**
     * BFS: Tree traversal in queue style (FiFo):First in First Out and has
     * additional orders
     */
    public void DFSOrder() {
        Stack<BSTNode> s = new Stack<>();
        s.add(root);
        BSTNode curentNode;
        //Traversal until there are no more objects in the queue (where it is saved) to consider
        while (!s.isEmpty()) {
            //Push the node out for traversal
            curentNode = s.pop();
            if (curentNode != null) {
                //When traversal, give priority to small numbers first, big numbers last => to get small numbers out first
                for (int i = 0; i < curentNode.count; i++) {
                    resultDFSOrder += "," + curentNode.data + "[" + curentNode.order + "]";
                }
                //Add new node to the queue
                s.add(curentNode.right);
                s.add(curentNode.left);
            }

        }
    }

    /**
     * Method to find node: Because the function declaration has a BSTNode to
     * find the node that needs to be found along the path from the root, it
     * must have a return value (no return value is needed).
     *
     * @param data data of the node to be searched
     * @return null
     */
    public BSTNode findNode(int data) {
        resultFindNode = "";
        listNodeFind.clear();
        //The value starts looking from the root node
        BSTNode curentNode = root;
        while (curentNode != null) {
            listNodeFind.add(curentNode);
            //Save the path into a string variable
            resultFindNode += "->" + curentNode.data;
            //Determine whether the next node is larger or smaller than the desired node
            if (data == curentNode.data) {
                return curentNode;
            } else if (data < curentNode.data) {
                curentNode = curentNode.left;
            } else {
                curentNode = curentNode.right;
            }
        }
        resultFindNode = "";
        listNodeFind.clear();
        return null;
    }

    /**
     * Call the preOrder function without parameters
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * Call the inOrder function without parameters
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * Call the postOrder function without parameters
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * Add new node in the form of class NodeData(data,count)
     *
     * @param curent node is being add
     */
    public void BSTtoListNode(BSTNode curent) {
        if (curent == null) {
            return;
        }
        BSTtoListNode(curent.left);
        listNode.add(curent);
        BSTtoListNode(curent.right);
    }

    /**
     * Methods to balance trees
     *
     * @param L the left node is under consideration
     * @param R the right node is under consideration
     */
    public void balance(int L, int R) {
        if (L > R) {
            return;
        }
        //Start balance from the middle
        int m = (L + R) / 2;
        //Get the node
        BSTNode node = listNode.get(m);
        addNode(node.data);
        //Balanced from the left
        balance(L, m - 1);
        //Balanced from the right
        balance(m + 1, R);
    }

    /**
     * Call the balance function without parameters
     */
    public void balance() {
        BSTtoListNode(root);
        root = null;
        balance(0, listNode.size() - 1);
    }

    /**
     * Method to delete node
     *
     * @param node node is being remove
     * @return true if the node to be deleted is in the tree
     */
    public boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        }
        //Delete the number by 1 of the node to be deleted
        node.count--;
        //If there is only 1 node that needs to be deleted, then delete the node as wells
        if (node.count == 0) {
            //If the node is a leaf, delete the node
            if (node.isLeaf()) {
                node.parent.removeLeafChild(node);
                return true;
            } else {
                //If not, delete the node and find a replacement node
                BSTNode temp;
                //The alternative node priority is to browse left - node max or browse right - node min
                if (node.hasLeftChild()) {
                    temp = node.left.findMaxNode();
                } else {
                    temp = node.right.findMinNode();
                }
                //After finding a replacement node, perform the replacement
                node.data = temp.data;
                node.count = temp.count;
                temp.count = 1;
                return removeNode(temp);
            }
        } else {
            return true;
        }
    }

    /**
     * Method to delete node:Call the findNode() function to find the node and
     * call the removeNode() function to delete and replace the node if
     * necessary
     *
     * @param data data of node is being remove
     * @return removeNode() function
     */
    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    /**
     * Method to convert tree to array
     *
     * @param current nodes in the tree to convert into arrays
     */
    public void treeToArrayNodeData(BSTNode current) {
        if (current == null) {
            return;
        }
        treeToArrayNodeData(current.left);
        listNodeData.add(new NodeData(current.data, current.count));
        treeToArrayNodeData(current.right);
    }

    /**
     * Call treeToArrayNodeData() function without parameters
     */
    public void treeToArrayNodeData() {
        listNodeData.clear();
        treeToArrayNodeData(root);
    }

    /**
     * Tree balancing method with type nodeData(data, count)
     *
     * @param L the left node is under consideration
     * @param R the right node is under consideration
     */
    public void balanceNodeData(int L, int R) {
        if (L > R) {
            return;
        }
        //Start balance from the middle
        int m = (L + R) / 2;
        //Get the node in the form BSTNode(data, count)
        NodeData node = listNodeData.get(m);
        addNode(node.data, node.count);
        //Balanced from the left
        balanceNodeData(L, m - 1);
        //Balanced from the right
        balanceNodeData(m + 1, R);
    }

    /**
     * Call the balance function with type nodeData(data, count) without
     * parameters
     */
    public void balanceNodeData() {
        treeToArrayNodeData();
        this.root = null;
        balanceNodeData(0, listNodeData.size() - 1);
    }

    /**
     * Method gets Node from list
     *
     * @param current node is retrieved from the list
     */
    public void getListNodeDAta(BSTNode current) {
        if (current == null) {
            return;
        }
        listNodeData.add(new NodeData(current.data, current.count));
        treeToArrayNodeData(current.left);
        treeToArrayNodeData(current.right);
    }

    /**
     * Method gets Node from list without parameters
     */
    public void getListNodeDAta() {
        listNodeData.clear();
        treeToArrayNodeData(root);
    }

    /**
     * File reading method
     *
     * @param filename File name to read
     */
    public void readFile(File filename) {

        try {
            Scanner sc = new Scanner(filename);
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                int data = sc.nextInt();
                int count = sc.nextInt();
                addNode(data, count);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * File writing method
     *
     * @param fileName File name to write
     */
    public void writeFile(File fileName) {
        getListNodeDAta();
        try {
            FileWriter out = new FileWriter(fileName);
            out.write(listNodeData.size() + "\n");
            for (NodeData nodeData : listNodeData) {
                out.write(nodeData.data + " " + nodeData.count + "\n");

            }
            out.close();
        } catch (Exception e) {
        }
    }
}
