import java.util.HashMap;

public class Trie {

    public class Node{
        private HashMap<Character,Node>fills;
        private boolean isFinalClau;

    }
    Node root;

    public boolean contains(String clau){
        Node current = root;
        for (int i = 0; i < clau.length(); i++) {
            Character c = clau.charAt(i);
            Node node = current.fills.get(c);
            if (node==null){
                return false;
            }
            current=node;

        }
        return current.isFinalClau;
    }

    public boolean add(String clau){
        Node current = root;
        boolean trobat;
        for (Character c : clau.toCharArray()) {
            Node node = current.fills.get(c);
            if (node == null) {
                node = new Node();
                current.fills.put(c, node);
            }
            current = node;
        }
        trobat = current.isFinalClau;
        current.isFinalClau = true;
        return !trobat;
    }

}
