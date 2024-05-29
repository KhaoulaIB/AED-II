/**
 * @author  Titrit
 * @param <E>
 */
class ConstructAVLTree<E extends Comparable<E>> {
    class Node {
        E element;
        int balance_factor;  //for height
        Node left;
        Node right;

        //default constructor to create null node
        public Node() {
            left = null;
            right = null;
            element = null;
            balance_factor = 0;
        }

        // parameterized constructor
        public Node(E element) {
            left = null;
            right = null;
            this.element = element;
            balance_factor = 0;
        }


    }
    private Node rootNode;
    public ConstructAVLTree() {
        rootNode = null;
    }

    public boolean is_Empty() {
       return rootNode == null;
    }



    public void insertElement(E element) {
        if (!contains(element)){
            rootNode = insertElement(element, rootNode);

        }
    }

    //create getHeight() method to get the height of the AVL Tree  
    private int getBalanceFactor(Node node) {
        return node == null ? -1 : node.balance_factor;
    }

    //create maxNode() method to get the maximum height from left and right node  
    private int getMaxHeight(Node leftNodeHeight, Node rightNodeHeight) {
        return Math.max(getBalanceFactor(leftNodeHeight), (getBalanceFactor(rightNodeHeight)));
    }


    //create insertElement() method to insert data in the AVL Tree recursively   
    private Node insertElement(E element, Node current) {
        if (current == null) {
            return new Node(element);
        }
        //Miramos si inserimos en la parte derecha o izquierda.
        if (element.compareTo(current.element) < 0) {
            current.left = insertElement(element, current.left);
        } else {
            current.right = insertElement(element, current.right);
        }
        //return current;

        // Update balance factor and height values.
        update(current);

        // Re-balance tree.
        return balance(current);

    }

    private void update(Node node){
        int leftBF = (node.left==null)? -1:node.left.balance_factor;
        int rightBF =  (node.right==null)? -1:node.right.balance_factor;
       // node.balance_factor=1+ max(leftBF,rightBF);
        node.balance_factor=rightBF-leftBF;

    }

    private Node balance(Node node){


        // Left heavy subtree.
        if (node.balance_factor == -2) {

            // Left-Left case.
            if (node.left.balance_factor <= 0) {
                return LLcase(node);

                // Left-Right case.
            } else {
                return LRcase(node);
            }

            // Right heavy subtree needs balancing.
        } else if (node.balance_factor == 2) {

            // Right-Right case.
            if (node.right.balance_factor >= 0) {
                return RRcase(node);

                // Right-Left case.
            } else {
                return RLcase(node);
            }
        }

        return node;
    }

    private Node LLcase(Node node){
        return rightRotation(node);

    }
    private Node LRcase(Node node) {
        node.left = leftRotation(node.left);
        return LLcase(node);
    }

    private Node RRcase(Node node) {
        return leftRotation(node);
    }

    private Node RLcase(Node node) {
        node.right = rightRotation(node.right);
        return RRcase(node);
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }


    public boolean contains(E element) {
        return containsRecursive( element, rootNode);
    }

    private boolean containsRecursive(E element, Node current) {
        if (current == null) {
            return false;
        }
        //Realizamos las comparaciones
        if (element.compareTo(current.element) == 0) {
            return true;
        }
        //Hacemos la llamada recursiva si no hemos encontrado el elemento.
        if (element.compareTo(current.element) < 0) {
            return containsRecursive(element, current.left);
        } else {
            return containsRecursive(element, current.right);
        }
    }


    void printPreorder(Node node){
        if (node == null)
            return;
        // First print data of node
        System.out.print(node.element + " ");
        // Then recur on left subtree
        printPreorder(node.left);
        // Now recur on right subtree
        printPreorder(node.right);
    }

    void printPostorder(Node node)
    {
        if (node == null)
            return;
        // First recur on left subtree
        printPostorder(node.left);
        // Then recur on right subtree
        printPostorder(node.right);
        // Now deal with the node
        System.out.print(node.element + ",");
    }

    void printInorder(Node node)
    {
        if (node == null)
            return;
        // First recur on left child
        printInorder(node.left);
        // Then print the data of node
        System.out.print(node.element + " ");
        // Now recur on right child
        printInorder(node.right);
    }



}  