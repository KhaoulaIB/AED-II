

import java.util.Stack;

import static java.lang.Math.max;

public class BinaryTree<E extends Comparable<E>>  {

    /**
     * Representación interna de un nodo en el árbol binario.
     */
    private class NodeArbre {


        E element;

        NodeArbre left;

        NodeArbre right;


        public NodeArbre(E element) {
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

        public NodeArbre getLeft() {
            return left;
        }

        public void setLeft(NodeArbre left) {
            this.left = left;
        }

        public NodeArbre getRight() {
            return right;
        }

        public void setRight(NodeArbre right) {
            this.right = right;
        }
    }

    /**
     * Raíz del árbol binario.
     */
    NodeArbre root;

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

    /**
     * Método auxiliar recursivo para la inserción de un elemento nuevo en el
     * árbol. Para decidir dónde realizar la inserción, comienza desde la raíz.
     * Si el elemento es menor, examina el subárbol izquierdo; si es mayor,
     * explora el subárbol derecho. Este proceso se repite de manera recursiva
     * hasta llegar a la hoja correspondiente para realizar la inserción. Orden
     * de complejidad: O(log2(n)), ya que para cada llamada que se hace, se va
     * dividiendo el árbol por la mitad para que queden la mitad de las ramas
     * que hay en el árbol (suponiendo que el árbol es completo o lleno).
     *
     * @param element Elemento que se va a insertar.
     * @param current Nodo actual en el cual se considera la inserción.
     * @return El nodo actual después de realizar la inserción recursiva.
     */
    private NodeArbre insertRecursive(E element, NodeArbre current) {
        if (current == null) {
            return new NodeArbre(element);
        }
        //Miramos si inserimos en la parte derecha o izquierda.
        if (element.compareTo(current.element) < 0) {
            current.left = insertRecursive(element, current.left);
        } else {
            current.right = insertRecursive(element, current.right);
        }
        return current;
    }


    //insert iterative:
    void insertIterative(E e){
        NodeArbre node = new NodeArbre(e);
        if (root == null) {//es la raíz
            root = node;
        }
        //buscanos donde insertarlo
        NodeArbre aux = root;

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
    /**
     * Comprueba si un elemento está presente en el árbol. Llama al método
     * containsRecursive para introducirle el parámetro NodeArbre. El orden de
     * complejidad es O(log2(n)), porque llama al método recursivo
     * containsRecursive que es O(log2(n)) como se explica en el método.
     *
     * @param e elemento que comprobamos si existe en el árbol.
     * @return Verdadero en caso de que "e" existe en el árbol. Falso en caso
     * contrario.
     */
    public boolean contains(E e) {
        return containsRecursive(e, root);
    }

    /**
     * Verifica de manera recursiva si el árbol binario contiene el elemento
     * dado a partir del nodo actual. Si el nodo actual es nulo, el elemento no
     * está presente en este subárbol y devuelve false. En caso contrario
     * compara el elemento con el elemento del nodo actual. Si son iguales, el
     * elemento se encuentra en este nodo y devuelve verdadero. Si el elemento
     * es menor, la búsqueda continúa en el subárbol izquierdo. Si el elemento
     * es mayor, la búsqueda continúa en el subárbol derecho. Se repite el
     * proceso recursivamente hasta procesar todo el árbol. Orden de
     * complejidad: O(log2(n)) en promedio, donde "n" es el número de nodos en
     * el árbol, ya que se va mirando si el elemento está presente en el lado
     * derecho o izquierdo y se va reduciendo por la mitad el tamaño del árbol
     * en cada llamada que se realiza.
     *
     * @param element Elemento que se está buscando en el árbol.
     * @param current Nodo actual en el que se está realizando la búsqueda.
     * @return Verdadero si el elemento está presente en el árbol, falso en caso
     * contrario.
     */
    private boolean containsRecursive(E element, NodeArbre current) {
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

        NodeArbre current = root;
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

        Stack<NodeArbre> stack = new Stack<>();
        Stack<Integer> depths = new Stack<>();

        stack.push(root);
        depths.push(0);

        while (!stack.isEmpty()) {
            NodeArbre current = stack.pop();
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

    /**
     * Obtiene la madre (nodo padre) del elemento dado de manera recursiva a
     * partir del nodo actual. Si el nodo actual es nulo, el elemento no tiene
     * madre en este subárbol y devuelve null. Comprueba si el hijo izquierdo o
     * derecho del nodo actual contiene el elemento buscado. Si es así, devuelve
     * el elemento del nodo actual como la madre. Si no, continúa la búsqueda de
     * manera recursiva en los subárboles izquierdo y derecho. El orden de
     * complejidad es O(log2(n)), donde "n" representa el número de nodos en el
     * árbol, ya que se va partiendo en parte derecha o izquierda en cada
     * llamada recursiva que se realiza haciendo más pequeño el tamaño.
     *
     * @param element Elemento del cual se busca la madre.
     * @param current Nodo actual en el que se está realizando la búsqueda de la
     * madre.
     * @return El elemento de la madre del nodo que contiene el elemento dado.
     * Devuelve nulo si el elemento no tiene madre o no está presente.
     *
     */
    private E getMotherRecursive(E element, NodeArbre current) {
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
        NodeArbre current = root;
        NodeArbre parent = null;

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








    /**
     * Calcula la longitud de la rama más larga en el árbol binario. El orden de
     * complejidad es O(n), porque el orden de complejidad del método recursivo
     * al que llama es O(n).
     *
     * @return La longitud de la rama más larga a partir del nodo actual.
     */
    public int longestBranch() {
        return recursiveLongestBranch(root);
    }

    /**
     * Calcula de manera recursiva la longitud de la rama más larga a partir del
     * nodo raíz. Si el nodo actual es nulo, la longitud de la rama es 0. En
     * caso contrario calcula de manera recursiva la longitud de las ramas
     * izquierda y derecha. Devuelve la longitud máxima entre cada una de las
     * hojas, más 1 para contar el nodo actual. El orden de complejidad es O(n),
     * ya que ha de visitar todos los nodos del árbol para determinar cual es la
     * rama más larga.
     *
     * @param current Nodo actual en el que se está calculando la longitud de la
     * rama.
     * @return La longitud de la rama más larga a partir del nodo actual.
     */
    private int recursiveLongestBranch(NodeArbre current) {
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


    void printPreorder(NodeArbre node){
        if (node == null)
            return;
        // First print data of node
        System.out.print(node.element + " ");
        // Then recur on left subtree
        printPreorder(node.left);
        // Now recur on right subtree
        printPreorder(node.right);
    }

    void printPostorder(NodeArbre node)
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

    void printInorder(NodeArbre node)
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