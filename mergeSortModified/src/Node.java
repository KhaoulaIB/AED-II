public class Node implements Comparable<Node>{

    private Object item;
    private Node next;


    public Node(Object item, Node next){
        this.item=item;
        this.next=next;

    }


    public boolean equals(Node p2){
        return this.item==p2.item && this.next==p2.next;
    }
    public Object getItem(){
        return this.item;
    }

    public void seItem(Object item){
        this.item=item;
    }
    public Node getNext(){
        return next;
    }
    public void setNext(Node next){
        this.next=next;
    }


    @Override
    public int compareTo(Node o) {
        return Integer.compare((Integer) this.item, (Integer) o.item);
    }
}
