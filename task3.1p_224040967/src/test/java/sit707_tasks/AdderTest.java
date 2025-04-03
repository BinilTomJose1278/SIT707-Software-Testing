package sit707_tasks;
import adder.Adder;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdderTest {

	public Adder adder = new Adder();

    // Tests for add()
    @Test
    public void testAddPositiveNumbers() {
    	int result = adder.add(2, 3);
    	System.out.println("Adding 2 + 3, Expected: 5, Got: " + result);
        assertEquals(5, result);
    }

    @Test
    public void testAddNegativeNumbers() {
    	int result = adder.add(-2, -3);
    	System.out.println("Adding -2 + -3, Expected: -5, Got: " + result);
        assertEquals(-5,result);
    }

    @Test
    public void testAddZero() {
    	 int result1 = adder.subtract(5, 0);
         int result2 = adder.subtract(0, 5);
         System.out.println("Subtracting 5 - 0, Expected: 5, Got: " + result1);
         System.out.println("Subtracting 0 - 5, Expected: -5, Got: " + result2);
         assertEquals(5, result1);
         assertEquals(-5, result2);
    }

    @Test
    public void testAddMixedSigns() {
        int result1 = adder.add(5, -3);    // 5 + (-3) = 2
        int result2 = adder.add(-5, 3);    // -5 + 3 = -2
        System.out.println("Adding 5 + (-3), Expected: 2, Got: " + result1);
        System.out.println("Adding -5 + 3, Expected: -2, Got: " + result2);

        assertEquals(2, result1);
        assertEquals(-2, result2);
    }


    // ✅ New Tests for subtract()

    @Test
    public void testSubtractPositiveNumbers() {
     int result1 = adder.subtract(4,3);
     System.out.println("Subtracting 4-3, Expected: 1, Got: " + result1);
        assertEquals(1, result1);
    }

    @Test
    public void testSubtractNegativeNumbers() {
    	int result = adder.subtract(-4, -3);
    	System.out.println("Subtracting -4-3, Expected: -7, Got: " + result);
        assertEquals(-1, result);
    }

    @Test
    public void testSubtractWithZero() {
    	int result1 = adder.subtract(5, 0);
    	int result2 = adder.subtract(0, 5);
    	 System.out.println("Subtracting 5 - 0, Expected: 5, Got: " + result1);
         System.out.println("Subtracting 0 -5, Expected: -5, Got: " + result2);
     	
        assertEquals(5, result1);
        assertEquals(-5, result2);
    }

    @Test
    public void testSubtractMixedSigns() {
        int result1 = adder.subtract(6, -3);   // 6 - (-3) = 9
        int result2 = adder.subtract(-6, 3);   // -6 - 3 = -9
        System.out.println("Subtracting 6 - (-3), Expected: 9, Got: " + result1);
        System.out.println("Subtracting -6 - 3, Expected: -9, Got: " + result2);

        assertEquals(9, result1);
        assertEquals(-9, result2);
    }


}
