package sit707_week6;

import static org.junit.Assert.*; 
import org.junit.Test;

public class LoopUtilsTest {

    @Test
    public void testSumUpToZero() {
        assertEquals(0, LoopUtils.sumUpTo(0));
    }

    @Test
    public void testSumUpToOne() {
        assertEquals(1, LoopUtils.sumUpTo(1));
    }

    @Test
    public void testSumUpToFive() {
        assertEquals(15, LoopUtils.sumUpTo(5));
    }

    @Test
    public void testCountEvenWithMixedValues() {
        int[] input = {1, 2, 3, 4, 5, 6};
        assertEquals(3, LoopUtils.countEvenNumbers(input));
    }

    @Test
    public void testCountEvenWithAllOdd() {
        int[] input = {1, 3, 5, 7};
        assertEquals(0, LoopUtils.countEvenNumbers(input));
    }

    @Test
    public void testCountEvenWithAllEven() {
        int[] input = {2, 4, 6, 8};
        assertEquals(4, LoopUtils.countEvenNumbers(input));
    }

    @Test
    public void testCountEvenWithEmptyArray() {
        int[] input = {};
        assertEquals(0, LoopUtils.countEvenNumbers(input));
    }

    @Test
    public void testCountEvenWithNullArray() {
        assertEquals(0, LoopUtils.countEvenNumbers(null));
    }
}
