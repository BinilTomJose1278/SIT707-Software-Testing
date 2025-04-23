package sit707_week5;

import org.junit.*;
import java.util.*;

public class WeatherControllerTest {

    private static WeatherController wController;
    private static double[] hourlyTemps;

    @BeforeClass
    public static void setUpOnce() {
        wController = WeatherController.getInstance();
        int totalHours = wController.getTotalHours();
        hourlyTemps = new double[totalHours];
        for (int i = 0; i < totalHours; i++) {
            hourlyTemps[i] = wController.getTemperatureForHour(i + 1);
        }
    }

    @AfterClass
    public static void tearDownOnce() {
        wController.close();
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

    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");

        // Arrange
        double expectedMin = Arrays.stream(hourlyTemps).min().orElse(Double.NaN);

        // Act
        double actualMin = wController.getTemperatureMinFromCache();

        // Assert
        Assert.assertEquals(expectedMin, actualMin, 0.01);
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");

        // Arrange
        double expectedMax = Arrays.stream(hourlyTemps).max().orElse(Double.NaN);

        // Act
        double actualMax = wController.getTemperatureMaxFromCache();

        // Assert
        Assert.assertEquals(expectedMax, actualMax, 0.01);
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");

        // Arrange
        double expectedAverage = Arrays.stream(hourlyTemps).average().orElse(Double.NaN);

        // Act
        double actualAverage = wController.getTemperatureAverageFromCache();

        // Assert
        Assert.assertEquals(expectedAverage, actualAverage, 0.01);
    }

    @Test
    public void testTemperaturePersist() {
        // For task 5.3C only
    }
} 
