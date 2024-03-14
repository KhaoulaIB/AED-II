import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import static java.util.Collections.swap;

public class Main {
    public static void main(String[] args) {
    int [] arr = {1,1,1,0};
      // selectionSort(arr);
        System.out.println(Arrays.toString(arr));
        quickSort(arr, 0, arr.length-1);

        System.out.println(Arrays.toString(arr));

    }


    /**
     * Este algoritmo ordena un array usando un montículo.
     *
     * @Orden de complejidad (CPU): O(n*log n), viene dado por la extracción de
     * los elementos del montículo, que es un for, de complejidad O(n) y dentro
     * de ese for hay una llamada (heapify), que es logarítmica ya que es la
     * altura del árbol de n elementos.
     * @Orden de complejidad (memoria): O(1), no requiere de memoria adicional
     * aparte del array que se utiliza.
     * @Optimizar uso de memoria: No se puede optimizar más el uso de memoria
     * porque ya es lo más eficiente posible en cuanto a uso de memoria.
     *
     * @param h array a procesar
     */
    public <E extends Comparable<E>> void  heapSort(E[] h) {
        int n = h.length;
        // Construir el montículo inicial
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapmoves(i,n,h);
        }
        // Extrae elementos del montículo uno por uno en orden ascendente
        for (int i = n - 1; i >= 0; i--) {
            E e = h[0];
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
     */
    private <E extends Comparable<E>> void heapmoves(int i, int heapSize, E[] h) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heapSize && h[left].compareTo(h[largest]) > 0) {
            largest = left;
        }
        if (right < heapSize && h[right].compareTo(h[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            E e = h[i];
            h[i] = h[largest];
            h[largest] = e;
            heapmoves(largest, heapSize,h);
        }
    }


    static void bucketSort(float arr[], int n)
    {
        if (n <= 0)
            return;

        // 1) Create n empty buckets
        @SuppressWarnings("unchecked")
        Vector<Float>[] buckets = new Vector[n];

        for (int i = 0; i < n; i++) {
            buckets[i] = new Vector<Float>();
        }

        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            float idx = arr[i] * n;
            buckets[(int)idx].add(arr[i]);
        }

        // 3) Sort individual buckets
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index++] = buckets[i].get(j);
            }
        }
    }


    // A utility function to get maximum value in arr[]
    static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    static void countSort(int arr[], int n, int exp)
    {
        int[] output = new int[n]; // output array
        int i;
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current
        // digit
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // The main function to that sorts arr[] of
    // size n using Radix Sort
    static void radixsort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that
        // instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }
    public static void mergeSort(int[] arr, int l, int r){
      if(l<r){
          int m = (l+r)/2;
          mergeSort(arr, l, m);
          mergeSort(arr, m+1, r);
          merge(arr, l, m ,r );
      }


    }

    private static void  merge(int [] arr, int l , int m, int r) {
        int [] lArr = new int[m-1+l];
        int[] rArr = new int[r-m];

        System.arraycopy(arr, l , lArr, 0, lArr.length);
        for (int i = 0; i < rArr.length; i++) {
            rArr[i]=arr[m+l+i];
        }
        int i = 0, j= 0, k=l;
        while(i<lArr.length && j<rArr.length){
            if (lArr[i]-(rArr[j])<0){
                arr[k]=(int)lArr[i];
            }else{
                arr[k]=(int)rArr[j];
                j++;
            }
            k++;
        }

        while (i<lArr.length){
            arr[k]=  lArr[i];i++;k++;

        }
        while (j<rArr.length){
            arr[k]=rArr[i];j++; k++;
        }
    }
//-----------------------------------------------------------------------
    /**
     * pivot : darer element
     * @param array
     * @param low
     * @param high
     */
    public static void quickSort(int [] array, int low, int high){

        if (low<high){
            int split = partition(array, low, high);
            //llamadas recursivas
            quickSort(array, low, split-1);
            quickSort(array, split+1, high);
        }

    }

    private static int partition(int [] array, int low, int high){
        int pivot = array[high];
        int split = low;
        for (int i = low; i<=high; i++){
            if (array[i]<pivot){
                swap(array, split, i);
                split++;
            }
        }
        swap(array, split, high);
        return split;

    }



    private static void swap(int  [] arr, int j, int i){
        int tmp = arr[j];
        arr[j]=arr[i];
        arr[i]=tmp;
    }
    //PRIMERA MODIFICACION DEL ALGORITMO QUICKSORT: PIVOT POR ESTIMACIÓN DE AL MEDIANA

    private void mediana(int[] arr){
        int n = arr.length;
        int x1 = arr[0];
        int x2 = arr[n/2];
        int x3 = arr[n-1];
        if (x1>x2){
            swap (arr,0, n/2);
        }else {
            if (x2>x3){
                swap(arr, n/2, n-1);
            }
        }

        //situzar el pivot al final de arr
        swap (arr, n/2, n-1);
    }
//----------------------------------------------------------------------------
   public static void selectionSort(int[] arr) {

        int n = arr.length;
        int index = -1;
        int tmp;
        for (int i = 0; i < n; i++) {
            int minim = Integer.MAX_VALUE; //Para que entra la primera vez
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < minim) {
                    minim = arr[j];
                    index = j;
                }

            }
            //swap
            tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;
            printArray(arr);


        }
    }

    
}
