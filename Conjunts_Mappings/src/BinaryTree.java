

import java.util.Stack;

import static java.lang.Math.max;

public class BinaryTree<E extends Comparable<E>>  {

    /**
     * Representación interna de un nodo en el árbol binario.
     */
    private class Node {


        E element;

        Node left;


        Node right;


        public Node(E element) {
            this.element = element;
            left = null;
            right = null;

        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    /**
     * Raíz del árbol binario.
     */
    Node root;

    /**
     * Constructor de un arbol binario vacío.
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Verifica si el árbol binario está vacío. Orden de complejidad : O(1),
     * contiene solo una comparación.
     *
     * @return Verdadero si el árbol está vacío, falso en caso contrario.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Inserta un nuevo elemento en el árbol binario si aún no existe. Orden de
     * complejidad: O(log2(n)), donde "n" es el número de nodos del árbol
     * binario, ya que este método llama al método recursivo insertRecursive(),
     * cuyo orden de complejidad es O(log2(n)).
     *
     * @param e Elemento que se va a insertar.
     */
    public void insert(E e) {
        if (!contains(e)) {  //si el elemento no existe ya en el arbol
            root = insertRecursive(e, root);
        }
    }


    private Node insertRecursive(E element, Node current) {
        if (current == null) {
            return new Node(element);
        }
        if (element.compareTo(current.element) < 0) {
            current.left = insertRecursive(element, current.left);
        } else {
            current.right = insertRecursive(element, current.right);
        }
        return current;
    }


    void insertIterative(E e){
        Node node = new Node(e);
        if (root == null) {//es la raíz
            root = node;
        }
        //buscanos donde insertarlo
        Node aux = root;

        while (aux != null) {
            if (e.compareTo(aux.element) < 0) {//avanzar en el subarbol izquierdo
                if (aux.left == null) {
                    aux.left = node;
                    return;
                }
                aux = aux.left;
            } else if (e.compareTo(aux.element) > 0) {
                if (aux.right == null) {//avanzar en el subarbol derecho
                    aux.right= node;
                    return;
                }
                aux = aux.right;
            } else {//ya existe
                return;
            }
        }
    }

    public void remove(E element){
        if (contains(element)){
            root = removeRecusrvie(root, element);
        }
    }
    private Node removeRecusrvie(Node node, E element){
        if (node==null){
            return null;
        }
        int cmp = element.compareTo(node.element);
        if (cmp<0){
            node.left=removeRecusrvie(node.left,element);
        }else if (cmp>0){
            node.right=removeRecusrvie(node.right, element);
        }else{
            //I found you, you poor node, get ready to be deleted!
            //case 0: ZERO CHILD
            if (node.right==null && node.left==null){
                return null;
            }
            //Case 1: ONE CHILD, CAN BE LEFT OR RIGHT
            if (node.right==null){
                return node.left;
            }else if (node.left==null){
                return node.right;
            }else{
                //It has twd children, we'll have to do more stuff

                //In this case we can either search the minimum node in the left-subtree, or the maximum
                //in the right-subtree

                Node tmp = findMin(root.right);
               root.element=tmp.element;
               node.right=removeRecusrvie(node.right,tmp.element);

            }


        }
        return node;
    }
    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
    public boolean contains(E e) {
        return containsRecursive(e, root);
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

    public boolean containsIterative (E e){

        Node current = root;
        while (current!=null){
            if (current.element.compareTo(e)>0){
                current = current.right;

            }else if (current.element.compareTo(e)<0){
                current= current.left;
            }else{
                return true;
            }
        }
        return false;


    }

    public int LongestBranchIterative(){
        if (root == null) {
            return 0;
        }

        int maxLength = 0;

        Stack<Node> stack = new Stack<>();
        Stack<Integer> depths = new Stack<>();

        stack.push(root);
        depths.push(0);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int currentDepth = depths.pop();

            if (current.left == null && current.right == null) {
                maxLength = Math.max(maxLength, currentDepth);
            }

            if (current.right != null) {
                stack.push(current.right);
                depths.push(currentDepth + 1);
            }

            if (current.left != null) {
                stack.push(current.left);
                depths.push(currentDepth + 1);
            }
        }

        return maxLength;
    }

    /**
     * Obtiene la madre (nodo padre) del elemento dado en el árbol binario. El
     * orden de complejidad es O(log2(n)), ya que es el orden de complejidad del
     * método recursivo.
     *
     * @param e Elemento del cual se busca la madre.
     * @return El elemento de la madre del nodo que contiene el elemento dado.
     * Devuelve nulo si el elemento no está presente o es la raíz del árbol.
     */
    public E getMother(E e) {
        return (contains(e)) ? getMotherRecursive(e, root) : null;

    }


    private E getMotherRecursive(E element, Node current) {
        if (current == null) {
            return null;
        }
        if ((current.left != null && element.compareTo(current.left.element) == 0)
                || (current.right != null && element.compareTo(current.right.element) == 0)) {
            return current.element;
        }
        //Si no lo hemos encontrado hacemos la llamada recursiva
        E leftResult = getMotherRecursive(element, current.left);
        if (leftResult != null) {
            return leftResult;
        }
        return getMotherRecursive(element, current.right);
    }

    public E getMotherIterative(E e) {
        Node current = root;
        Node parent = null;

        while (current != null) {
            int comparisionResult = e.compareTo(current.element);

            if (comparisionResult < 0) {
                parent = current;
                current = current.left;
            } else if (comparisionResult > 0) {
                parent = current;
                current = current.right;
            } else {
                // Hemos encontrado el elemento
                return (parent != null) ? parent.element : null;
            }
        }

        return null;
    }


    public int longestBranch() {
        return recursiveLongestBranch(root);
    }


    private int recursiveLongestBranch(Node current) {
        if (current == null || (current.left == null && current.right == null)) {
            return 0;
        }
        //Vamos recorriendo todo el árbol
        int leftDepth = recursiveLongestBranch(current.left);
        int rightDepth = recursiveLongestBranch(current.right);
        //Se suma 1 para contar el nodo actual
        return max(leftDepth, rightDepth) + 1;
    }

    /**
     * Devuelve la raíz del árbol binario.
     *
     * @return la raiz del árbol binario. El orden de complejidad es O(1), ya
     * que la función simplemente devuelve el elemento de la raíz, sin realizar
     * operaciones que dependan del tamaño del árbol.
     */
    public E getRoot() {
        return root.element;
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
