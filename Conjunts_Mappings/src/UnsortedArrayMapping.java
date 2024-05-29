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
        valors=(V[]) new Object()[max];
         n=0;
    }

    public V get(K key){}

    public V put (K key, V value){}

    publi cV remove ()
}
