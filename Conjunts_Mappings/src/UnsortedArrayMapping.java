
import java.util.Iterator;

public class UnsortedArrayMapping <K,V>{

    private K[] claus;
    private V[] valors;
    private int n;

    /**
     * Reserva memòria pels dos arrays de longitud max i inicialitza n a 0
     *
     * @param max
     */


    public UnsortedArrayMapping(int max){
        claus= (K[]) new Object[max];
        valors=(V[]) new Object[max];
        n=0;
    }

    /**
     *
     * Consultar el valor associat a la clau. Retorn aquest valor si
     * la clau existeix o null en cas contrari
     */
    public V get(K key){
        Iterator<K>  keyIt = new IteratorUnsortedArrayMapping();
        while (keyIt.hasNext()){
            K currentKey = keyIt.next();
            int index = getIndex(currentKey);
            if ( currentKey.equals(key) ){
                return valors[index];
            }
        }
//        for (int i = 0; i < n; i++) {
//            if (claus[i].equals(key) && claus[i]!=null){
//                return valors[i];
//            }
//        }

        return null;
    }

    /** Afegir una parella clau-valor
     * Retorna el valor anterior associat a la clau, si n’hi havia, o null
     * @param key
     * @param value
     */
    public V put (K key, V value){
        int pos = getIndex(key);
        if ((pos!=-1) ){//existeix la clau
            V valorprevi = valors[pos];
            valors[pos]= value;
            return valorprevi;
        }

        //en cas que no existex la clau
        claus[n]= key;
        valors[n] = value;
        n++;

        return null;
    }

    /**
     * Eliminar l’associació d’una clau
     * Retorna el valor associat a la clau, si n’hi havia, o null
     * @return
     */
    public V remove (K key){

        int index = getIndex(key);
        if (index!=-1 && valors[index]!=null){
            V valorprevi = valors[index];
            //intercanviem el darrer element amb el peniultim
            claus[index]=claus[n-1];
            valors[index]=valors[n-1];

            //marquem nulls la darrera posicio
            claus[n-1]= null;
            valors[n-1]= null;
            n--;
            return valorprevi;

        }
        return null;
    }

    // Consultar si el mapping es troba buit
    public boolean isEmpty() {
        return n == 0;
    }

    public int getIndex (K key){
        int index =0;
        Iterator<K> it = new IteratorUnsortedArrayMapping();
        while (it.hasNext()){
            if (it.next().equals(key)){
                return index;
            }
            index++;
        }
//        for (int i = 0; i < n; i++) {
//            if (claus[i]!=null && claus[i].equals(key) ){
//                return i;
//            }
//
//        }
        return -1;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(claus[i]).append("=").append(valors[i]);
        }
        sb.append("}");
        return sb.toString();
    }

    public Iterator iterator() {
        return new IteratorUnsortedArrayMapping();
    }

    private class IteratorUnsortedArrayMapping implements Iterator<K> {
        private int indexit;

        private IteratorUnsortedArrayMapping() {
            indexit= 0;
        }
        @Override
        public boolean hasNext() {
            return indexit<n;
        }
        @Override
        public K next() {
            indexit++;
            return claus[indexit-1];
        }
    }
}



