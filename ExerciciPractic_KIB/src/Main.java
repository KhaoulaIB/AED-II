import java.util.*;


/**
 * Problema 152: Va de modas
 * Enunciado: Dado un conjunto, o distribución, de valores se define la moda
 * como el valor (o valores) que más se repite en dicho conjunto.
 * En este problema te pedimos que calcules la moda de un conjunto de números.
 * Se ha resuelto este problema usando el algoritmo de ordenación heapsort (sin modificación ninguna), ya que evita el peor caso
 * y su orden de complejidad es O (n*log n).
 *
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //Leemos el numero de elementos del conjunto
        int n = sc.nextInt();
        while (n != 0) {    //mientars hay casos de prueba
            int[] elements = new int[n];
            //guardamos todos los elementos
            for (int i = 0; i < n; i++) {
                elements[i] = sc.nextInt();
            }
            //llamada al metodo de ordenación
            heapSort(elements);
            mostRepeated(elements);

            n= sc.nextInt();

        }
    }


    /**
     * Ordena un arreglo de enteros de forma ascendiente.
     *
     * @param h Un arreglo de enteros que se va a ordenar
     * @complexity O(n log n)
     *  - La construcción inicial del montículo toma O(n), siendo n la longitud del arreglo.
     *  - Cada una de las extracciones de elementos del montículo toma O(log n) en el peor de los casos.
     *  - Como hay n extracciones en total, el orden de complejidad  es O(n log n).
     */

    public static  void  heapSort(int[] h) {
        int n = h.length;
        // Construir el montículo inicial
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapmoves(i,n,h);
        }
        // Extrae elementos del montículo uno por uno en orden ascendente
        for (int i = n - 1; i >= 0; i--) {
            int e = h[0];
            h[0] = h[i];
            h[i] = e;
            // Llama al método heapmoves() en el montículo reducido
            heapmoves( 0,i,h);
        }
    }



    /**
     * Ordena los nodos utilizando las reglas del montículo.
     *
     * @param i nodo a tratar
     * @param heapSize tamaño del montículo
     * @param  h    arreglo de elementos
     * @complexity O(log n)
     *  - La función heapmoves es recursiva  y en cada llamada realiza
     *   un número constante de operaciones (cálculos de índices y comparaciones).
     *  - La profundidad máxima del árbol de montículo es O(log n), por lo que
     *    la complejidad de cada llamada es O(log n).
     */
    private static void heapmoves(int i, int heapSize, int[] h) {

        int largest = i;
        // Calcula los índices de los hijos izquierdo y derecho
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Comprueba si el hijo izquierdo existe y es mayor que el elemento raiz
        if (left < heapSize && h[left]>(h[largest])) {
            largest = left;
        }
        // Comprueba si el hijo derecho existe y es mayor que largest
        if (right < heapSize && h[right]>(h[largest]) ) {
            largest = right;
        }
        // Si el índice del elemento más grande ha cambiado, realiza el intercambio.
        if (largest != i) {
            int e = h[i];
            h[i] = h[largest];
            h[largest] = e;

            heapmoves(largest, heapSize,h);
        }
    }

    /**
     * Busca el elemento más repetido dentro de un conjunto ordenado y lo imprime.
     *
     * @param elements
     *  @complexity O(n)
     *      - Itera sobre el arreglo una vez (O(n)).
     *      - En cada iteración realiza un número constante de operaciones.
     */

    private static void mostRepeated(int [] elements){

        int index = 1;
        int mostRepeted= elements[0];
        int max = 1;
        int repeticiones = 1;

        while (index < elements.length) {
            //verifica que el elemento actual es igual al anterior
            if (elements[index] == elements[index - 1]) {
                //incrementa el contador de repeticiones
                ++repeticiones;
                if (max < repeticiones) {
                    max = repeticiones;
                    mostRepeted =  elements[index];
                }
            } else
                //si el elemento actual no es igual al anterior, reiniciamos el contador
                repeticiones = 1;
            index++;
        }
        System.out.println(mostRepeted);
    }

}