/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Usuari
 */
public class Sorting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       int [] n = {10,100, 1000, 10000, 100000, 200000, 300000, 500000, 600000};
       for (int i =0 ; i<n.length; i++){
        long start = System.currentTimeMillis();
        selectionSort(generateRandomArray(n[i]));
        long finish = System.currentTimeMillis();
        System.out.println("Temps per "+ n[i]+ "es: " + (finish-start));
        
       }
        // TODO code application logic here
    }
    
public static void printArray (int [] arr ){
    
    System.out.println(Arrays.toString(arr));
    
}

 public static int [] generateRandomArray (int n ){
     //semilla para generar los mismos numeros
        Random rn = new Random(126);
        int[] arr = new int[n];
            for (int i = 0; i<n; i++){
                arr[i]=rn.nextInt(1289);
            }
            return arr;
}



 public static void selectionSort (int [] arr ){
     
     int n = arr.length;
     int index = -1;
     int tmp;
     for (int i = 0; i<n; i++){
         int minim = Integer.MAX_VALUE; //Para que entra la primera vez
         for (int j = i+1; j<n; j++){
             if (arr[j]<minim){
                 minim=arr[j];
                 index = j;
             }
             
         }
         //swap
        tmp = arr[index];
        arr[index]=arr[i];
        arr[i]=tmp;
        printArray(arr);
         
         
     }
 }



}
