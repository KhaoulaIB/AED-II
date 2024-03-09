public class LinkedList <E extends Comparable<E>> {


    private Node first;
    public LinkedList(){

    }

    // Afegeix un element al principi de la llista
    public void add(E element){
        if (first==null){
            first = new Node(element,null);
        }else{
        Node newNode = new Node(element,first);
        first = newNode;}

    }

    @Override
    // Sobreescriu toString (): genera un String amb
 // tots els elements de la llista : de principi a final
    public String toString(){
        Node top = first;
        StringBuilder s = new StringBuilder();
        while (top!=null){
        s.append(top.getItem()).append(" ");
            top = top.getNext();
        }

        return s.toString();
    }

    /**
     * Reccore la lista una vez y devuelve el nodo intermedio
     *
     * @param firstNode primer nodo de la lista
     * @return Nodo intermedio de una lista
     */
    private Node middle(Node firstNode){
        Node p1 = firstNode, p2 = firstNode;
        while (p1.getNext().getNext()!= null) {
            p1 = p1.getNext();
            p2 = p1.getNext().getNext(); // Avanzar p2 dos veces más rápido
        }
        return p1;
    }

    public void mergeSort(){
        this.first = mergeSort(first);

    }

    /**
     *Inicia la ordenación de la lista
     * @param n node de inidio de la lista a ordenar
     * @return puntero del primer ndodo de al lista ordenada
     */
    //change to public to test it
    public  Node mergeSort(Node n){
        if (n == null || n.getNext() == null) {
            return n; // Caso base: lista vacía o con un solo elemento
        }

            Node mid = middle(n); // Encuentra el nodo intermedio
            Node midNext = mid.getNext();
            mid.setNext(null); // Divide la lista en dos sublistas

            Node left = mergeSort(n); // Ordena la primera mitad
            Node right = mergeSort(midNext); // Ordena la segunda mitad

            return merge(left, right); // Combina las sublistas ordenadas

    }


    /**
     *Fusiona les subllistes
     * @param l Nodo de inicio de la primera sublista ordenada
     * @param r Node de inidio de la segunda sublista ordenada
     * @return puntero del inicio de la lista fusionada
     */

    public Node merge(Node l , Node r) {
        Node start= l;
        //inicializar el nodo inicial de la lista final
        if (l.compareTo(r) < 0) {
            l = l.getNext();    //actualizar el nodo de inicio
        } else {
            start = r;  //r es menor que l
            r = r.getNext();    //actualizar inicio de la segunda lista
        }

        Node current = start;

        while (l != null && r != null) {
            if (l.compareTo(r) < 0) {
                current.setNext(l);
                current= l;
                l = l.getNext();

            } else {
                current.setNext(r);
                current = r;
              r = r.getNext();
            }

        }

            while (l!=null){
                current.setNext(l);
                current = l;
                    l = l.getNext();
            }
            while (r!=null){
                current.setNext(r);
                current = r;
                r = r.getNext();
            }

        return start;
    }



}
