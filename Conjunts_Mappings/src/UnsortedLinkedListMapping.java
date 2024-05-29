import java.util.Iterator;
import java.util.Vector;

public class UnsortedLinkedListMapping<K, V> {
    private class Node {
        private final K key;
        private final Node next;
        private V value;

        public Node(K key, V value, Node next) {
            this.value = value;
            this.key = key;
            this.next = next;
        }
    }

    public Iterator iterator(){
        return new UnsortedLinkedListMappingIterator();
    }
    private class UnsortedLinkedListMappingIterator implements Iterator{

    private Node iterator;
        @Override
        public boolean hasNext() {
            return iterator!=null;
        }

        @Override
        public Object next() {
           Pair next = new Pair(iterator.key, iterator.value);
           iterator= iterator.next;
           return next;
        }
    }
    private class Pair{
        private final K key;
        private final V value;
        public Pair(K key, V value){
            this.key=key;
            this.value=value;
        }

    }
    private Node first;

    public V get(K key) {
        Node current = first;
        boolean trobat = false;
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;

        }
        return null;
    }

    public V put(K key, V value) {

        Node current = first;
        while (current != null) {
            if (current.key.equals(key)) {
                V previous = current.value;
                current.value = value;//update the value
                return previous;
            }
            current = current.next;
        }


        //creates a new node and add it to the start of the mapping
        Node tmp = new Node(key, value, first);
        first = tmp;
        return null;
    }

    public V remove(K key) {
        Node current = first;
        Node previous = null;
        boolean trobat = false;
        while (current != null) {
            if (current.key.equals(key)) {
                V tmp = current.value;
                if (previous == null) {
                    first = current.next;
                } else {
                    previous = current.next;
                }
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return first == null;
    }


}


