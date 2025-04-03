package sit707_tasks;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    // ---------------------------- Student Info ----------------------------

    @Test
    public void testStudentIdentity() {
        String studentId = "224040967";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Binil Tom Jose";
        Assert.assertNotNull("Student name is null", studentName);
    }

    // ---------------------------- January Tests ----------------------------

    @Test
    public void testMaxJanuary31ShouldIncrementToFebruary1() {
        DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(2, date.getMonth());
    }

    @Test
    public void testMaxJanuary31ShouldDecrementToJanuary30() {
        DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(30, date.getDay());
        Assert.assertEquals(1, date.getMonth());
    }

    @Test
    public void testNominalJanuary() {
        int rand_day_1_to_31 = 1 + new Random().nextInt(31);
        DateUtil date = new DateUtil(rand_day_1_to_31, 1, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertTrue(date.getDay() >= 2 && date.getDay() <= 31);
    }

    @Test
    public void testMinJanuary1ShouldIncrementToJanuary2() {
        DateUtil date = new DateUtil(1, 1, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(2, date.getDay());
        Assert.assertEquals(1, date.getMonth());
    }

    @Test
    public void testMinJanuary1ShouldDecrementToDecember31() {
        DateUtil date = new DateUtil(1, 1, 2024);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(31, date.getDay());
        Assert.assertEquals(12, date.getMonth());
        Assert.assertEquals(2023, date.getYear());
    }

    // ---------------------------- Previous Date Tests (1A - 13A) ----------------------------

    @Test
    public void test1A_PreviousDate() {
        DateUtil date = new DateUtil(1, 6, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(31, date.getDay());
        Assert.assertEquals(5, date.getMonth());
    }

    @Test
    public void test2A_PreviousDate() {
        DateUtil date = new DateUtil(2, 6, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(1, date.getDay());
    }

    @Test
    public void test3A_PreviousDate() {
        DateUtil date = new DateUtil(15, 6, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test4A_PreviousDate() {
        DateUtil date = new DateUtil(30, 6, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(29, date.getDay());
    }

    @Test(expected = RuntimeException.class)
    public void test5A_InvalidDate() {
        new DateUtil(31, 6, 1994); // Invalid: June has only 30 days
    }

    @Test
    public void test6A_PreviousDate() {
        DateUtil date = new DateUtil(15, 1, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test7A_PreviousDate() {
        DateUtil date = new DateUtil(15, 2, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test8A_PreviousDate() {
        DateUtil date = new DateUtil(15, 11, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test9A_PreviousDate() {
        DateUtil date = new DateUtil(15, 12, 1994);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test10A_PreviousDate() {
        DateUtil date = new DateUtil(15, 6, 1700);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test11A_PreviousDate() {
        DateUtil date = new DateUtil(15, 6, 1701);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test12A_PreviousDate() {
        DateUtil date = new DateUtil(15, 6, 2023);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    @Test
    public void test13A_PreviousDate() {
        DateUtil date = new DateUtil(15, 6, 2024);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(14, date.getDay());
    }

    // ---------------------------- Next Date Tests (1B, 2B, 13B) ----------------------------

    @Test
    public void test1B_NextDate() {
        DateUtil date = new DateUtil(1, 6, 1994);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(2, date.getDay());
    }

    @Test
    public void test2B_NextDate() {
        DateUtil date = new DateUtil(2, 6, 1994);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(3, date.getDay());
    }

    @Test
    public void test13B_NextDate() {
        DateUtil date = new DateUtil(15, 6, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(16, date.getDay());
    }

    // ---------------------------- Leap Year February Tests ----------------------------

    @Test
    public void testLeapYear_February28to29() {
        DateUtil date = new DateUtil(28, 2, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(29, date.getDay());
        Assert.assertEquals(2, date.getMonth());
    }

    @Test
    public void testLeapYear_February29toMarch1() {
        DateUtil date = new DateUtil(29, 2, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(3, date.getMonth());
    }

    @Test
    public void testLeapYear_February29Previous() {
        DateUtil date = new DateUtil(29, 2, 2024);
        System.out.println("Before decrement: " + date);
        date.decrement();
        System.out.println("After decrement: " + date);
        Assert.assertEquals(28, date.getDay());
        Assert.assertEquals(2, date.getMonth());
    }
}
