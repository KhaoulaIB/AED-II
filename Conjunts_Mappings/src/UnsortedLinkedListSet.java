import java.util.Iterator;

public class UnsortedLinkedListSet <E> {

    public class Node {

        private E elem;
        private Node next;

        public Node(E elem, Node next) {
            this.elem = elem;
            this.next = next;
        }
    }



    private class IteratorUnsortedLinkedListSet implements Iterator {

        private Node ixdIterator;

        private IteratorUnsortedLinkedListSet() {
            ixdIterator=first;
        }

        public boolean hasNext() {
            return ixdIterator!=null;
        }

        public Object next() {
            E elem = ixdIterator.elem;
            ixdIterator=ixdIterator.next;
            return  elem;
        }
    }


    private Node first;

    public UnsortedLinkedListSet(){
        first=null;
    }
    public boolean isEmpty(){
        return first==null;
    }

    public boolean contains(E elem){

        Node current = first;
        boolean trobat = false;
        while (current!=null && !trobat){
            trobat = elem.equals(current.elem);
            current = current.next;

        }
        return trobat;
    }

    public boolean add(E elem){

        boolean trobat = contains(elem);
        if (!trobat){
            Node n = new Node(elem,first);
            first=n;
        }
        return !trobat;

    }


    public boolean remove(E elem){
        Node current = first;
        Node previous = null;
        boolean trobat = false;
        while (current!=null && !trobat){
            trobat= current.elem.equals(elem);
            if (!trobat){
                previous=current;
                current=current.next;
            }
        }
        if (trobat){

            if (previous==null){
                first=current.next;
            }else{
                previous.next=current.next;
            }
        }
        return trobat;

    }





}
