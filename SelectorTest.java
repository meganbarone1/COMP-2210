import org.junit.Assert;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   


   
   
   @Test public void testMin() {
      int[] a = {3, 7, 1};
      int min = Selector.min(a);
      Assert.assertEquals(1, min);
   }
   
   @Test public void testMin2() {
      int[] numbers = {7, 8, 3, 4, 2};
      int min = Selector.min(numbers);
      Assert.assertEquals(2, min);
   }
   
   @Test public void testMin3() {
      int[] numbers = {2, 4, 0, 3, 6, 7, 9, 5, 3, 8};
      int min = Selector.min(numbers);
      Assert.assertEquals(0, min);
   }
   @Test public void testMax() {
      int[] a = {1, 2, 5, 3};
      int max = Selector.max(a);
      Assert.assertEquals(5, max);
   }
   @Test public void testMax2() {
      int[] penisLengths = {3, 6, 4, 7, 5};
      int max = Selector.max(penisLengths);
      Assert.assertEquals(7, max);
      
   }
   @Test public void testMax3() {
      int[] tits = {6, 3, 2};
      int max = Selector.max(tits);
      Assert.assertEquals(6, max);
   }
   @Test public void testMax4() {
      int[] q = {6, 7};
      int max = Selector.max(q);
      Assert.assertEquals(7, max);
   }
   @Test public void testKmin1() {
      int[] a = {6, 9, 3, 1, 4};
      int kmin = Selector.kmin(a, 3);
      Assert.assertEquals(4, kmin);
   }
   @Test public void testKmin2() {
      int[] a = {7, 6, 5, 4};
      int kmin = Selector.kmin(a, 1);
      Assert.assertEquals(4, kmin);
   }
   @Test public void kMin3() {
      int[] a = {1, 2, 7, 7, 7, 8};
      int kmin = Selector.kmin(a, 4);
      Assert.assertEquals(8, kmin);
   }
   @Test public void testKMax1() {
      int[] a = {3, 6, 9};
      int kmax = Selector.kmax(a, 1);
      Assert.assertEquals(9, kmax);
   }
   @Test public void testRange() {
      int[] a = {8, 7, 2, 6, 4};
      int[] range = Selector.range(a, 2, 6);
      int[] expected = {2, 6, 4};
      Assert.assertArrayEquals(expected, range);
   }
   @Test public void testRange2() {
      int[] a = {9, 7, 1, 2, 6, 3, 4, 8, 8, 5, 5, 7};
      int[] range = Selector.range(a, 5, 9);
      int[] expected = {9, 7, 6, 8, 8, 5, 5, 7};
      Assert.assertArrayEquals(expected, range);
   }
   @Test public void testRange3() {  
      int[] a = {9, 7};
      int[] range = Selector.range(a, 5, 9);
      int[] expected = {9, 7};
      Assert.assertArrayEquals(expected, range);
   }
   @Test public void testRange4() {  
      int[] a = {9};
      int[] range = Selector.range(a, 5, 9);
      int[] expected = {9};
      Assert.assertArrayEquals(expected, range);
   }
   @Test public void testRange5() {  
      int[] a = {9, 7, 6, 5};
      int[] range = Selector.range(a, 1, 3);
      int[] expected = {};
      Assert.assertArrayEquals(expected, range);
   }
   @Test public void testCeiling() {
      int[] a = {1, 4, 9, 3, 2, 0};
      int ceiling = Selector.ceiling(a, 8);
      Assert.assertEquals(9, ceiling);
   }
   @Test public void testCeiling2() {
      int[] a = {3, 2, 9, 6, 4, 1, 7};
      int ceiling = Selector.ceiling(a, 7);
      Assert.assertEquals(7, ceiling);
   }
   @Test public void testCeiling3() {
      int[] a = {2, 4, 6, 8};
      int ceiling = Selector.ceiling(a, 2);
      Assert.assertEquals(2, ceiling);
   }
   @Test public void testCeiling4() {
      int[] a = {4, 7, 2, 9, 6, -1, 0};
      int ceiling = Selector.ceiling(a, -4);
      Assert.assertEquals(-1, ceiling);
   }
   @Test public void testCeiling5() {
      int[] a = {4, 5, 6};
      int ceiling = Selector.ceiling(a, 7);
      Assert.assertEquals(0, 7);
   }
   @Test public void testFloor() {
      int[] a = {3, 4, 2, 1, 6, 9};
      int floor = Selector.floor(a, 5);
      Assert.assertEquals(4, floor);
   }
   @Test public void testFloor2() {
      int[] a = {8, 6, 5, 9, 7};
      int floor = Selector.floor(a, 7);
      Assert.assertEquals(7, floor);
   }
   @Test public void testFloor3() {
      int[] a = {1, 3, 5, 7, 9};
      int floor = Selector.floor(a, 1);
      Assert.assertEquals(1, floor);
   }
   @Test public void testFloor4() {
      int[] a = {1, 7};
      int floor = Selector.floor(a, -11);
      Assert.assertEquals(1, floor);
   }

   }