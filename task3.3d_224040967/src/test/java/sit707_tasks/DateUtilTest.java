package sit707_tasks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Comprehensive Test Suite for Enhanced DateUtil
 * Demonstrates advanced Boundary Value Analysis (BVA) and Equivalence Class Testing (ECT)
 * 
 * @author Binil Tom Jose
 * @studentId 224040967
 */
public class DateUtilTest {

    private DateUtil testDate;
    
    @Before
    public void setUp() {
        // Initialize test date for each test
        testDate = new DateUtil(15, 6, 2024); // June 15, 2024 (Saturday)
    }

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

    // ===================== TEMPORAL BOUNDARY VALUE ANALYSIS =====================

    // Millisecond Boundaries (0-999)
    @Test
    public void testMillisecondBoundary_999to000() {
        DateUtil dateTime = new DateUtil("2024-12-31T23:59:59.999");
        DateUtil result = dateTime.addMilliseconds(1);
        Assert.assertEquals("2025-01-01T00:00:00", result.toISOString().substring(0, 19));
        System.out.println("Millisecond boundary test: " + result.toISOString());
    }

    @Test
    public void testMillisecondBoundary_001to000() {
        DateUtil dateTime = new DateUtil("2024-01-01T00:00:00.001");
        DateUtil result = dateTime.addMilliseconds(-1);
        Assert.assertEquals("2024-01-01T00:00:00", result.toISOString().substring(0, 19));
    }

    // Second Boundaries (0-59)
    @Test
    public void testSecondBoundary_59to00() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:30:59");
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertEquals(31, result.getMinute());
        Assert.assertEquals(0, result.getSecond());
    }

    @Test
    public void testSecondBoundary_00to59() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:30:00");
        DateUtil result = dateTime.addSeconds(-1);
        Assert.assertEquals(29, result.getMinute());
        Assert.assertEquals(59, result.getSecond());
    }

    // Minute Boundaries (0-59)
    @Test
    public void testMinuteBoundary_59to00() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:59:30");
        DateUtil result = dateTime.addMinutes(1);
        Assert.assertEquals(13, result.getHour());
        Assert.assertEquals(0, result.getMinute());
    }

    // Hour Boundaries (0-23)
    @Test
    public void testHourBoundary_23to00() {
        DateUtil dateTime = new DateUtil("2024-06-15T23:30:00");
        DateUtil result = dateTime.addHours(1);
        Assert.assertEquals(16, result.getDay()); // Next day
        Assert.assertEquals(0, result.getHour());
    }

    // Day Boundaries - Complex scenarios
    @Test
    public void testLeapYearFeb28to29() {
        DateUtil dateTime = new DateUtil("2024-02-28T12:00:00");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(29, result.getDay());
        Assert.assertEquals(2, result.getMonth());
    }

    @Test
    public void testLeapYearFeb29toMar01() {
        DateUtil dateTime = new DateUtil("2024-02-29T12:00:00");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(1, result.getDay());
        Assert.assertEquals(3, result.getMonth());
    }

    @Test
    public void testNonLeapYearFeb28toMar01() {
        DateUtil dateTime = new DateUtil("2023-02-28T12:00:00");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(1, result.getDay());
        Assert.assertEquals(3, result.getMonth());
    }

    // Month Boundaries
    @Test
    public void testMonth30DayBoundary() {
        DateUtil dateTime = new DateUtil("2024-04-30T12:00:00");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(1, result.getDay());
        Assert.assertEquals(5, result.getMonth());
    }

    @Test
    public void testMonth31DayBoundary() {
        DateUtil dateTime = new DateUtil("2024-01-31T12:00:00");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(1, result.getDay());
        Assert.assertEquals(2, result.getMonth());
    }

    // Year Boundaries
    @Test
    public void testYearBoundary_Dec31toJan01() {
        DateUtil dateTime = new DateUtil("2024-12-31T23:59:59");
        DateUtil result = dateTime.addDays(1);
        Assert.assertEquals(1, result.getDay());
        Assert.assertEquals(1, result.getMonth());
        Assert.assertEquals(2025, result.getYear());
    }

    // ===================== BUSINESS LOGIC BOUNDARY VALUE ANALYSIS =====================

    // Business Hour Boundaries
    @Test
    public void testBusinessHourStart_08_59_to_09_00() {
        DateUtil dateTime = new DateUtil("2024-06-17T08:59:59"); // Monday
        Assert.assertFalse(dateTime.isBusinessHour());
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertTrue(result.isBusinessHour());
    }

    @Test
    public void testBusinessHourEnd_16_59_to_17_00() {
        DateUtil dateTime = new DateUtil("2024-06-17T16:59:59"); // Monday
        Assert.assertTrue(dateTime.isBusinessHour());
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertFalse(result.isBusinessHour());
    }

    @Test
    public void testLunchBreakStart_11_59_to_12_00() {
        DateUtil dateTime = new DateUtil("2024-06-17T11:59:59"); // Monday
        Assert.assertTrue(dateTime.isBusinessHour());
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertFalse(result.isBusinessHour()); // Lunch break
    }

    @Test
    public void testLunchBreakEnd_12_59_to_13_00() {
        DateUtil dateTime = new DateUtil("2024-06-17T12:59:59"); // Monday
        Assert.assertFalse(dateTime.isBusinessHour()); // Lunch break
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertTrue(result.isBusinessHour());
    }

    // Weekend Boundaries
    @Test
    public void testFridayToMondayBusinessDays() {
        DateUtil friday = new DateUtil("2024-06-14T17:00:00"); // Friday
        DateUtil result = friday.addBusinessDays(1);
        Assert.assertEquals(DayOfWeek.MONDAY, result.getDayOfWeek());
        Assert.assertEquals(17, result.getDay()); // Monday June 17
    }

    @Test
    public void testSundayToMondayTransition() {
        DateUtil sunday = new DateUtil("2024-06-16T10:00:00"); // Sunday
        Assert.assertFalse(sunday.isWorkingDay());
        DateUtil monday = sunday.addDays(1);
        Assert.assertTrue(monday.isWorkingDay());
    }

    // ===================== EQUIVALENCE CLASS TESTING =====================

    // EC1: Valid Date-Time Inputs
    @Test
    public void testValidInputClass_StandardDateTime() {
        DateUtil dateTime = new DateUtil("2024-06-15T14:30:00");
        Assert.assertTrue(dateTime.isValid());
        Assert.assertEquals(2024, dateTime.getYear());
        Assert.assertEquals(6, dateTime.getMonth());
        Assert.assertEquals(15, dateTime.getDay());
    }

    @Test
    public void testValidInputClass_EdgeValidDate() {
        DateUtil dateTime = new DateUtil("2024-02-29T00:00:00"); // Leap day at midnight
        Assert.assertTrue(dateTime.isValid());
        Assert.assertEquals(29, dateTime.getDay());
    }

    @Test
    public void testValidInputClass_BoundaryValidDate() {
        DateUtil dateTime = new DateUtil("2024-12-31T23:59:59"); // Last moment of year
        Assert.assertTrue(dateTime.isValid());
        Assert.assertEquals(31, dateTime.getDay());
        Assert.assertEquals(12, dateTime.getMonth());
    }

    // EC2: Invalid Date-Time Inputs
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputClass_MalformedString() {
        new DateUtil("2024-13-40T25:70:80"); // Invalid format
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidInputClass_Feb29NonLeap() {
        new DateUtil(29, 2, 2023); // Feb 29 on non-leap year
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidInputClass_InvalidDay() {
        new DateUtil(32, 1, 2024); // Day 32
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidInputClass_InvalidMonth() {
        new DateUtil(15, 13, 2024); // Month 13
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidInputClass_YearBelowRange() {
        new DateUtil(10, 1, 1699); // Year below 1700
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidInputClass_YearAboveRange() {
        new DateUtil(10, 1, 2025); // Year above 2024 (legacy constraint)
    }

    // EC3: Time Zone Equivalence Classes
    @Test
    public void testTimeZoneClass_StandardUTC() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00", "UTC");
        Assert.assertEquals(ZoneId.of("UTC"), dateTime.getTimeZone());
    }

    @Test
    public void testTimeZoneClass_OffsetTimeZone() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00", "America/New_York");
        DateUtil converted = dateTime.convertToTimeZone(ZoneId.of("UTC"));
        // During daylight saving, NY is UTC-4
        Assert.assertEquals(16, converted.getHour()); // 12 PM + 4 hours
    }

    @Test
    public void testTimeZoneClass_CrossDateConversion() {
        DateUtil dateTime = new DateUtil("2024-06-15T23:00:00", "America/Los_Angeles");
        DateUtil utcTime = dateTime.convertToTimeZone(ZoneId.of("UTC"));
        Assert.assertEquals(16, utcTime.getDay()); // Next day in UTC
        Assert.assertEquals(6, utcTime.getHour()); // 23:00 PST = 06:00 UTC next day
    }

    // EC4: Arithmetic Operations
    @Test
    public void testArithmeticClass_PositiveAdditions() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00");
        DateUtil result = dateTime.addDays(10).addHours(5).addMinutes(30);
        Assert.assertEquals(25, result.getDay());
        Assert.assertEquals(17, result.getHour());
        Assert.assertEquals(30, result.getMinute());
    }

    @Test
    public void testArithmeticClass_NegativeAdditions() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00");
        DateUtil result = dateTime.addDays(-5).addHours(-2);
        Assert.assertEquals(10, result.getDay());
        Assert.assertEquals(10, result.getHour());
    }

    @Test
    public void testArithmeticClass_ZeroOperations() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00");
        DateUtil result = dateTime.addDays(0).addHours(0).addMinutes(0);
        Assert.assertEquals(dateTime.getDay(), result.getDay());
        Assert.assertEquals(dateTime.getHour(), result.getHour());
        Assert.assertEquals(dateTime.getMinute(), result.getMinute());
    }

    @Test
    public void testArithmeticClass_LargeScaleOperations() {
        DateUtil dateTime = new DateUtil("2024-06-15T12:00:00");
        DateUtil result = dateTime.addDays(365); // Add one year
        Assert.assertEquals(2025, result.getYear());
        Assert.assertEquals(6, result.getMonth());
        Assert.assertEquals(15, result.getDay()); // Should handle leap year correctly
    }

    // EC5: Business Operations
    @Test
    public void testBusinessOperationClass_BusinessDayAddition() {
        DateUtil friday = new DateUtil("2024-06-14T10:00:00"); // Friday
        DateUtil result = friday.addBusinessDays(3);
        Assert.assertEquals(DayOfWeek.WEDNESDAY, result.getDayOfWeek());
        Assert.assertEquals(19, result.getDay()); // Skip weekend
    }

    @Test
    public void testBusinessOperationClass_BusinessHourCalculation() {
        DateUtil dateTime = new DateUtil("2024-06-17T09:00:00"); // Monday 9 AM
        DateUtil result = dateTime.addBusinessHours(8.5); // Add 8.5 business hours
        Assert.assertTrue(result.isBusinessHour());
        // Should be around 17:30 (5:30 PM) accounting for lunch break
    }

    @Test
    public void testBusinessOperationClass_HolidayAware() {
        DateUtil dateTime = new DateUtil("2024-12-24T10:00:00"); // Christmas Eve
        DateUtil nextBusiness = dateTime.nextBusinessDay();
        // Should skip Christmas Day and find next business day
        Assert.assertTrue(nextBusiness.isWorkingDay());
        Assert.assertFalse(nextBusiness.isHoliday());
    }

    // EC6: Conversion Operations
    @Test
    public void testConversionClass_FormatConversions() {
        DateUtil dateTime = new DateUtil("2024-06-15T14:30:00");
        String isoFormat = dateTime.format(DateUtil.DateTimePattern.ISO_8601);
        String usFormat = dateTime.format(DateUtil.DateTimePattern.US_DATETIME);
        
        Assert.assertEquals("2024-06-15T14:30:00", isoFormat);
        Assert.assertEquals("06/15/2024 2:30 PM", usFormat);
    }

    @Test
    public void testConversionClass_CustomFormat() {
        DateUtil dateTime = new DateUtil("2024-06-15T14:30:00");
        String customFormat = dateTime.format("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("2024-06-15 14:30:00", customFormat);
    }

    @Test
    public void testConversionClass_EpochConversion() {
        DateUtil dateTime = new DateUtil("2024-01-01T00:00:00", "UTC");
        long epochMilli = dateTime.toEpochMilli();
        Assert.assertTrue(epochMilli > 0);
        
        // Convert back and verify
        DateUtil converted = new DateUtil(LocalDateTime.ofInstant(
            Instant.ofEpochMilli(epochMilli), ZoneId.of("UTC")));
        Assert.assertEquals(dateTime.getYear(), converted.getYear());
        Assert.assertEquals(dateTime.getMonth(), converted.getMonth());
        Assert.assertEquals(dateTime.getDay(), converted.getDay());
    }

    // ===================== COMPLEX INTEGRATION TESTS =====================

    @Test
    public void testComplexScenario_BusinessDaysAcrossMonths() {
        DateUtil startDate = new DateUtil("2024-01-30T09:00:00"); // Tuesday
        List<DateUtil> businessDays = startDate.getBusinessDaysInRange(
            new DateUtil("2024-02-05T17:00:00"));
        
        // Should include business days across month boundary
        Assert.assertTrue(businessDays.size() >= 4);
        businessDays.forEach(date -> Assert.assertTrue(date.isWorkingDay()));
    }

    @Test
    public void testComplexScenario_TimeZoneBusinessHours() {
        DateUtil nyTime = new DateUtil("2024-06-17T09:00:00", "America/New_York");
        DateUtil laTime = nyTime.convertToTimeZone(ZoneId.of("America/Los_Angeles"));
        
        Assert.assertTrue(nyTime.isBusinessHour()); // 9 AM NY is business hour
        Assert.assertFalse(laTime.isBusinessHour()); // 6 AM LA is not business hour
    }

    @Test
    public void testComplexScenario_HolidayCalculations() {
        DateUtil beforeHoliday = new DateUtil("2024-12-24T16:00:00");
        DateUtil afterAddingBusinessDays = beforeHoliday.addBusinessDays(1);
        
        // Should skip Christmas Day (Dec 25) and go to next business day
        Assert.assertTrue(afterAddingBusinessDays.getDay() > 25);
        Assert.assertTrue(afterAddingBusinessDays.isWorkingDay());
    }

    // ===================== VALIDATION TESTING =====================

    @Test
    public void testValidation_AppointmentScheduling() {
        DateUtil appointmentTime = new DateUtil("2024-06-17T10:00:00"); // Monday 10 AM
        DateUtil.ValidationResult result = appointmentTime.validateForContext(
            DateUtil.BusinessContext.APPOINTMENT_SCHEDULING);
        
        Assert.assertTrue(result.isValid());
        Assert.assertTrue(result.getErrors().isEmpty());
    }

    @Test
    public void testValidation_AppointmentOnHoliday() {
        DateUtil holidayAppointment = new DateUtil("2024-12-25T10:00:00"); // Christmas
        DateUtil.ValidationResult result = holidayAppointment.validateForContext(
            DateUtil.BusinessContext.APPOINTMENT_SCHEDULING);
        
        Assert.assertFalse(result.isValid());
        Assert.assertTrue(result.getErrors().stream()
            .anyMatch(error -> error.contains("holiday")));
    }

    @Test
    public void testValidation_PaymentProcessing() {
        DateUtil weekendPayment = new DateUtil("2024-06-15T10:00:00"); // Saturday
        DateUtil.ValidationResult result = weekendPayment.validateForContext(
            DateUtil.BusinessContext.PAYMENT_PROCESSING);
        
        Assert.assertTrue(result.isValid()); // Valid but with warnings
        Assert.assertTrue(result.getWarnings().stream()
            .anyMatch(warning -> warning.contains("delayed")));
    }

    // ===================== PERFORMANCE AND EDGE CASE TESTS =====================

    @Test
    public void testPerformance_BulkBusinessDayCalculation() {
        long startTime = System.currentTimeMillis();
        
        DateUtil baseDate = new DateUtil("2024-01-01T09:00:00");
        for (int i = 0; i < 1000; i++) {
            DateUtil result = baseDate.addBusinessDays(i % 30);
            Assert.assertNotNull(result);
        }
        
        long duration = System.currentTimeMillis() - startTime;
        Assert.assertTrue("Bulk calculation should complete within 2 seconds", 
                         duration < 2000);
        System.out.println("Bulk calculation completed in: " + duration + "ms");
    }

    @Test
    public void testEdgeCase_LeapSecondHandling() {
        // Test around potential leap second insertion dates
        DateUtil dateTime = new DateUtil("2024-06-30T23:59:59", "UTC");
        DateUtil result = dateTime.addSeconds(1);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void testEdgeCase_DaylightSavingTransition() {
        ZonedDateTime beforeDST = ZonedDateTime.of(
            2024, 3, 10, 1, 59, 0, 0,
            ZoneId.of("America/New_York")
        );
        ZonedDateTime afterDST = beforeDST.plusMinutes(2); // should skip 2 AM hour

        int hour = afterDST.getHour();
        Assert.assertTrue("Hour should be >= 3 after DST spring forward", hour >= 3);
        System.out.println("DST transition: " + beforeDST + " -> " + afterDST);
    }



    @Test
    public void testEdgeCase_EndOfCentury() {
        DateUtil endOfCentury = new DateUtil("2099-12-31T23:59:59");
        DateUtil nextCentury = endOfCentury.addSeconds(1);
        
        Assert.assertEquals(2100, nextCentury.getYear());
        Assert.assertEquals(1, nextCentury.getMonth());
        Assert.assertEquals(1, nextCentury.getDay());
    }

    // ===================== COMPARISON AND UTILITY TESTS =====================

    @Test
    public void testComparison_BeforeAfter() {
        DateUtil earlier = new DateUtil("2024-06-15T10:00:00");
        DateUtil later = new DateUtil("2024-06-15T11:00:00");
        
        Assert.assertTrue(earlier.compareTo(later) < 0);
        Assert.assertTrue(later.compareTo(earlier) > 0);
        Assert.assertEquals(0, earlier.compareTo(earlier));
    }

    @Test
    public void testDuration_BetweenDates() {
        DateUtil start = new DateUtil("2024-06-15T09:00:00");
        DateUtil end = new DateUtil("2024-06-15T17:00:00");
        
        Duration duration = start.between(end);
        Assert.assertEquals(8, duration.toHours());
    }

    @Test
    public void testEquality_SameDateDifferentTimezone() {
        DateUtil utcTime = new DateUtil("2024-06-15T12:00:00", "UTC");
        DateUtil nyTime = new DateUtil("2024-06-15T08:00:00", "America/New_York");
        
        // Same instant, different representations
        Assert.assertEquals(utcTime.toEpochMilli(), nyTime.toEpochMilli());
    }

    // ===================== LEGACY COMPATIBILITY TESTS =====================

    @Test
    public void testLegacyCompatibility_BasicIncrement() {
        DateUtil date = new DateUtil(15, 6, 2024);
        date.increment();
        Assert.assertEquals(16, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }

    @Test
    public void testLegacyCompatibility_BasicDecrement() {
        DateUtil date = new DateUtil(15, 6, 2024);
        date.decrement();
        Assert.assertEquals(14, date.getDay());
        Assert.assertEquals(6, date.getMonth());
        Assert.assertEquals(2024, date.getYear());
    }

    @Test
    public void testLegacyCompatibility_MonthDuration() {
        Assert.assertEquals(31, DateUtil.monthDuration(1, 2024)); // January
        Assert.assertEquals(29, DateUtil.monthDuration(2, 2024)); // February leap year
        Assert.assertEquals(28, DateUtil.monthDuration(2, 2023)); // February non-leap
        Assert.assertEquals(30, DateUtil.monthDuration(4, 2024)); // April
    }

    @Test
    public void testLegacyCompatibility_ToString() {
        DateUtil date = new DateUtil(15, 6, 2024);
        String dateString = date.toString();
        Assert.assertTrue(dateString.contains("June"));
        Assert.assertTrue(dateString.contains("15"));
        Assert.assertTrue(dateString.contains("2024"));
        System.out.println("Legacy toString: " + dateString);
    }

    // ===================== ERROR HANDLING TESTS =====================

    @Test
    public void testErrorHandling_InvalidTimeZone() {
        try {
            new DateUtil("2024-06-15T12:00:00", "Invalid/TimeZone");
            Assert.fail("Should throw exception for invalid timezone");
        } catch (Exception e) {
            Assert.assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testErrorHandling_NullInputs() {
        try {
            new DateUtil((String) null);
            Assert.fail("Should throw exception for null input");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    // ===================== COMPREHENSIVE BOUNDARY TEST SUMMARY =====================
    
    @Test
    public void testBoundaryValueAnalysis_ComprehensiveSummary() {
        System.out.println("\n=== COMPREHENSIVE BOUNDARY VALUE ANALYSIS SUMMARY ===");
        
        // Test all critical boundaries in one comprehensive test
        DateUtil[] boundaryTests = {
            new DateUtil("2024-02-28T23:59:59"), // Leap year boundary
            new DateUtil("2024-02-29T00:00:00"), // Leap day
            new DateUtil("2024-12-31T23:59:59"), // Year boundary
            new DateUtil("2024-06-30T23:59:59"), // Month boundary (30 days)
            new DateUtil("2024-01-31T23:59:59"), // Month boundary (31 days)
        };
        
        for (DateUtil boundary : boundaryTests) {
            DateUtil incremented = boundary.addSeconds(1);
            Assert.assertTrue("Boundary increment should be valid", incremented.isValid());
            System.out.println("Boundary test: " + boundary + " -> " + incremented);
        }
        
        System.out.println("All boundary value tests completed successfully!");
    }

    @Test
    public void testEquivalenceClassTesting_ComprehensiveSummary() {
        System.out.println("\n=== COMPREHENSIVE EQUIVALENCE CLASS TESTING SUMMARY ===");
        
        // Valid equivalence classes
        DateUtil[] validClasses = {
            new DateUtil("2024-06-15T10:30:00"), // Standard business time
            new DateUtil("2024-02-29T00:00:00"), // Leap day
            new DateUtil("2024-12-31T23:59:59"), // Year end
        };
        
        for (DateUtil valid : validClasses) {
            Assert.assertTrue("Valid class should pass validation", valid.isValid());
            System.out.println("Valid EC: " + valid);
        }
        
        // Test business logic equivalence classes
        DateUtil businessDay = new DateUtil("2024-06-17T10:00:00"); // Monday
        DateUtil weekend = new DateUtil("2024-06-15T10:00:00"); // Saturday
        
        Assert.assertTrue("Monday should be working day", businessDay.isWorkingDay());
        Assert.assertFalse("Saturday should not be working day", weekend.isWorkingDay());
        
        System.out.println("All equivalence class tests completed successfully!");
    }
}