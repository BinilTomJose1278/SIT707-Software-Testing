package sit707_week6;

public class LoopUtils {
    
    /**
     * Returns the sum of numbers from 1 to n.
     * @param n upper bound
     * @return sum of 1 to n
     */
    public static int sumUpTo(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Counts how many even numbers are present in the array.
     * @param arr integer array (can be null)
     * @return number of even integers
     */
    public static int countEvenNumbers(int[] arr) {
        if (arr == null) return 0;

        int count = 0;
        for (int num : arr) {
            if (num % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}
