import java.util.Arrays;
/**
    * Defines a library of selection methods
    * on array of ints.
    *
    * @author Megan Barone (mab0126@auburn.edu)
    * @author Dean Hendrix (dh@auburn.edu)
    * @version January 21, 2019
    */

public final class Selector {

   /** Constructor cannot be changed.
     */
   private Selector() { }

   /**
    * Selects minimum value from the array a.
    * Array is not changed by this method.
    *
    * @param a - Command line arguments used.
    * @return min which is an integer value.
    * @throws IllegalArgumentException if array is null or has length 0.
    */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
         
      
      }
      return min;
   }

   /**
    * Selects maximum value from the array a.
    * Array is not changed by this method.
    *
    * @param a - Command line arguments used.
    * @return max which is an integer value.
    * @throws IllegalArgumentException if array is null or has length 0.
    */
   public static int max(int[] a) throws IllegalArgumentException {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      }
      return max;
   }
   /** 
     * Selects the kth minimum value from the array.
     * @param a is array of ints.
     * @param k is integer value.
     * @return kmin shows kth minimum value from array.
     * @throws IllegalArgumentException if parameter is invalid.
     *
     */

   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] temp = a.clone();
      Arrays.sort(temp);
      int distinctValues = 1;
      for (int i = 0; i < a.length - 1; i++) {
      
         if (temp[i] != temp[i + 1]) {
            distinctValues++;
         
         }
      }
      int[] b = new int[distinctValues];
      b[0] = temp[0];
      int count = 1;
      for (int j = 1; j < a.length; j++) {
      
         if (temp[j - 1] != temp[j]) {
            b[count] = temp[j];
            count++;
         }
      }
   
      if (k > b.length) {
         throw new IllegalArgumentException();
      } else {
         return b[k - 1];
      
      }
   }
   /** 
     * Selects the kth maximum value from the array.
     * @param a is array of ints.
     * @param k is integer value.
     * @return kmin shows kth maximum value from array.
     * @throws IllegalArgumentException if parameter is invalid.
     */

   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] temp = a.clone();
      Arrays.sort(temp);
      int distinctValues = 1;
   
      for (int i = 0; i < a.length - 1; i++) {
         if (temp[i] != temp[i + 1]) {
            distinctValues++;
         
         }
      }
      int[] b = new int[distinctValues];
      b[0] = temp[0];
      int count = 1;
      for (int j = 1; j < a.length; j++) {


         if (temp[j - 1] != temp[j]) {
            b[count] = temp[j];
            count++;
         }
      }
   
      if (k > b.length) {
         throw new IllegalArgumentException();
      }
      
      else {
         return b[b.length - k];
      
      }
   }
   /**
     * Returns range of numbers from array between low and high values input. 
     * @param a is array of int values.
     * @param low is lower end of range.
     * @param high is higher end of range.
     * @return range is values who equal or fall between low and high value.
     * @throws IllegalArgumentException if array given is empty.
     */

   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int numInArray = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            
            numInArray++;
         }
      }
      int[] range = new int[numInArray];
      int k = 0;
      for (int j = 0; j < a.length; j++) {
         if (a[j] >= low && a[j] <= high) {
            
            range[k] = a[j];
            k++;
            
         }
      }
      return range;
   }

   /** Returns the smallest value in a that is greater than or equal to key.
     *
     * @param a is an array of ints.
     * @param key is the target int value.
     * @return ceiling is smallest value greater than or equal to key.
     * @throws IllegalArgumentException if parameter is invalid.
     */
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int eligibleNumbers = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key) {
            eligibleNumbers++;
         }
      }
      if (eligibleNumbers == 0) {
         throw new IllegalArgumentException();
      }
      int[] b = new int[eligibleNumbers];
      int count = 0;
      for (int j = 0; j < a.length; j++) {
         if (a[j] >= key) {
            b[count] = a[j];
            count++;
         }
      }
      int ceiling = b[0];
      for (int k = 1; k < b.length; k++) {
         if (b[k] < ceiling) {
            ceiling = b[k];
         }
      }
      return ceiling;

   }
   /** Returns the largest value in a that is less than or equal to key.
     *
     * @param a is an array of ints.
     * @param key is the target int value.
     * @return floor is largest value less than or equal to key.
     * @throws IllegalArgumentException if parameter is invalid.
     */

   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int eligibleNumbers = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key) {
            eligibleNumbers++;
         }
      }
      if (eligibleNumbers == 0) {
         throw new IllegalArgumentException();
      }
      int[] b = new int[eligibleNumbers];
      int count = 0;
      for (int j = 0; j < a.length; j++) {
         if (a[j] <= key) {
            b[count] = a[j];
            count++;
         }
      }
      int floor = b[0];
      for (int k = 1; k < b.length; k++) {
         if (b[k] > floor) {
            floor = b[k];
         }
      }
      return floor;

   }



}
   




