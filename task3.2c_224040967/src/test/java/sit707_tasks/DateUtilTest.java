package sit707_tasks;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

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

    // ===================== DAY ECs =====================

    // D1: Safe days (1–28)
    @Test
    public void testMinJanuary1ShouldIncrementToJanuary2_D1() {
        DateUtil date = new DateUtil(1, 1, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(2, date.getDay());
    }

    // D2: Feb 29 in leap year
    @Test
    public void testFeb29ShouldIncrementToMarch1LeapYear_D2() {
        DateUtil date = new DateUtil(29, 2, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(3, date.getMonth());
    }

    // D3: 30th day in 30-day month
    @Test
    public void testApril30ShouldIncrementToMay1_D3() {
        DateUtil date = new DateUtil(30, 4, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(5, date.getMonth());
    }

    // D4: 31st day in 31-day month
    @Test
    public void testDecember31ShouldIncrementToJanuary1NextYear_D4() {
        DateUtil date = new DateUtil(31, 12, 2024);
        System.out.println("Before increment: " + date);
        date.increment();
        System.out.println("After increment: " + date);
        Assert.assertEquals(1, date.getDay());
        Assert.assertEquals(1, date.getMonth());
        Assert.assertEquals(2025, date.getYear());
    }

    // ===================== MONTH ECs =====================

    @Test
    public void testValidApril30_M1() {
        DateUtil date = new DateUtil(30, 4, 2024);
        System.out.println("Valid April 30: " + date);
        Assert.assertEquals(30, date.getDay());
    }

    @Test
    public void testValidMay31_M2() {
        DateUtil date = new DateUtil(31, 5, 2024);
        System.out.println("Valid May 31: " + date);
        Assert.assertEquals(31, date.getDay());
    }

    @Test
    public void testValidLeapFeb_M3() {
        DateUtil date = new DateUtil(28, 2, 2024);
        System.out.println("Valid Feb 28 (leap year): " + date);
        Assert.assertEquals(28, date.getDay());
    }

    // ===================== YEAR ECs =====================

    @Test
    public void testLeapYearY1() {
        DateUtil date = new DateUtil(29, 2, 2024);
        System.out.println("Leap year Feb 29: " + date);
        Assert.assertEquals(29, date.getDay());
    }

    @Test
    public void testFeb28NonLeapYear_Y2() {
        DateUtil date = new DateUtil(28, 2, 2023);
        System.out.println("Non-leap year Feb 28: " + date);
        Assert.assertEquals(28, date.getDay());
    }

    // ===================== INVALID ECs =====================

    @Test(expected = RuntimeException.class)
    public void testInvalidDayOver31() {
        System.out.println("Testing invalid day > 31");
        new DateUtil(32, 1, 2024);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidFeb29NonLeap() {
        System.out.println("Testing invalid Feb 29 on non-leap year");
        new DateUtil(29, 2, 2023);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidMonthOver12() {
        System.out.println("Testing invalid month > 12");
        new DateUtil(15, 13, 2024);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidYearBelowRange() {
        System.out.println("Testing invalid year < 1700");
        new DateUtil(10, 1, 1699);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidYearAboveRange() {
        System.out.println("Testing invalid year > 2024");
        new DateUtil(10, 1, 2025);
    }
}
