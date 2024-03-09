public class Main {
    public static void main(String[] args) {


        LinkedList <Integer> lista = new LinkedList<>();

        lista.add(5);
        lista.add(2);
        lista.add(1);
        lista.add(3);
        lista.add(4);
        lista.mergeSort();
        System.out.println(lista.toString());
//        LinkedList t = new LinkedList<>();
//        Node first = new Node(1, null);
//        Node second  =new Node(2, null);
//        Node third = new Node(5,null);
//        first.setNext(second);
//        second.setNext(third);
//        Node s2 = new Node(4, null);
//        Node s1 = new Node(3, s2);
//        Node start = t.merge(first, s1);
//
//
//        Node top = start;
//        while (top!=null){
//            System.out.print(top.getItem() + " ");
//            top = top.getNext();
//
//        }

      //  lista.mergeSort();
       // System.out.println(lista);

    }



}