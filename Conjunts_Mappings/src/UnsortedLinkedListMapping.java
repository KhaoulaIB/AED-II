public class UnsortedLinkedListMapping<K,V> {
    private class Node {
        private K key;
        private V value;
        private Node next;

        public Node(K key, V value, Node next){
            this.value=value;
            this.key=key;
            this.next=next;
        }
    }
    private Node first;

    public V get(K key) {
        Node current = first;
        boolean trobat = false;
        while (current!=null && !trobat){

            trobat = current.key.equals(key);
            if (!trobat){
                current=current.next;
            }
        }
        if (trobat){
            return current.value;
        }else{
            return null;
        }


    }
    public V put(K key, V value) {

        Node current = first;
        boolean trobat = false;
        while (current!=null && !trobat){
            trobat = current.key.equals(key);
            if (!trobat){
                current=current.next;
            }

        }
        if (trobat){
            V valuenprevi = current.value;
            current.value=value;
            return valuenprevi;
        }else{
            Node n = new Node(key,value,first);
            first = n;
            return null;
        }

    }
    public V remove(K key) {
        Node current = first;
        Node previous = null;
        boolean trobat = false;
        while (current != null && !trobat) {
            trobat = current.key.equals(key);
            if (!trobat) {
                previous = current;
                current = current.next;
            }
            }

            if (trobat) {
                V valueprevi = current.value;
                if (previous == null) {
                    first = current.next;

                } else {
                    previous.next = current.next;}
                    return valueprevi;

            } else {
                return null;
            }
        }


    public boolean isEmpty() {
            return first==null;
        }

    }


