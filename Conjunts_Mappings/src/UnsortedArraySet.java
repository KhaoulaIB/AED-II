import java.util.Iterator;

public class UnsortedArraySet<E> {



        private E[] array;
        private int n; //number of elements

    /**
     * Inicializa el array
     * O(1)
     * @param max
     */
        public UnsortedArraySet (int max){
            array = (E[]) new Object[max];
            n=0;
        }


    /**
     * Cerca un elemento en el conjunto
     * @param elem
     * @return true si el elemento existe
     * O(n): busqueda lineal
     */
    public boolean contains(E elem){
            int i =0; //index
            boolean trobat = false;
            while (!trobat && i<n){
                trobat = elem.equals(array[i]);
                i++;
            }
            return trobat;
        }

    /**
     * Añade un elemento al conjunto si no esta (no se admiten valores
     * repetidos)
     * @param elem
     * @return true si se ha añadido el elemento
     * O(n)
     *
     */
        public boolean add(E elem){
            if (n<array.length && !contains(elem)){
                array[n++]=elem;
                return true;
            }else{
                return false;

            }
        }

    /**
     *  Elimina un elemento del conjunto
     * @param elem
     * @return true si ha cambiado el conjunto
     * O(n)
     */
        public boolean remove (E elem){
            for (int i = 0; i < n; i++) {
                if (array[i].equals(elem)) {
                    array[i] = array[n - 1];
                    n--;

                    return true;
                }
            }

            return false;
        }


    public boolean isEmpty(){
            return n==0;
    }

    public IteratorUnsortedArraySet iterator() {
        return new IteratorUnsortedArraySet();
    }

    private class IteratorUnsortedArraySet implements Iterator<E> {

        private int iterator;

        private IteratorUnsortedArraySet() {
            iterator = 0;
        }

        @Override
        public boolean hasNext() {
            return iterator < n;
        }

        @Override
        public E next() {
            return array[iterator++];

        }
    }

}


