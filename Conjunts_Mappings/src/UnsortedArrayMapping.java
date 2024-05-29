public class UnsortedArrayMapping <K,V>{

    private K[] keys;
    private V[] values;
    private int n;

    /**
     * Reserva memòria pels dos arrays de longitud max i inicialitza n a 0
     *
     * @param max
     */
    public UnsortedArrayMapping(int max){
        keys= (K[]) new Object[max];
        values=(V[]) new Object[max];
         n=0;
    }

    public V get(K key){

        for (int i = 0; i < n; i++) {
            if (keys[i].equals(key)){
                return values[i];
            }

        }
        return null;
    }

    /**
     * Adds the pair (key,value) to se mapping. It the key already exists we update it and returns the previw value
     * Esle we return null
     * @param key
     * @param value
     * @return
     */
    public V put (K key, V value){
        //if the key already exists, we update its value
        for (int i = 0; i < n; i++) {
            if (keys[i].equals(key)){
                V previous = values[i];//get preview value for the key
                values[i]=value;
                return previous;

            }

        }
        //Can we add it?
        if (n< keys.length){
            keys[n]=key;
            values[n++]=value;
        }
        return null;



    }

    /**
     * Rremoves the key from the mapping and its values.
     * @param key
     * @return the value of the key
     */
    public V remove(K key){
        for (int i = 0; i < n; i++) {
            if (keys[i].equals(key)){
                V value = values[i];
                keys[i]=keys[n-1];
                values[i]=values[n-1];
                n--;
                return value;
            }

        }
        return null;

    }

}
