package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class WeatherAndMathUtilsTest {
	
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
	public void testFalseNumberIsEven() {
		Assert.assertFalse(WeatherAndMathUtils.isEven(3));
	}
	
	@Test
	public void testWeatherAdviceDangerousWind() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(72.2, 3.0));
	}
	@Test
	public void testWeatherAdviceDangerousRain() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(20.0, 6.1));
	}
	@Test
	public void testWeatherAdviceCombinedDanger() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(46.0, 4.1));
	}
	
	@Test
	public void testWeatherAdviceWarningWindOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(50.0, 3.0));
	}
	@Test
	public void testWeatherAdviceWarningRainOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(20.0, 4.5));
	}
	
	@Test
	public void testWeatherAdviceAllClear() {
	    Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(20.0, 2.0));
	}
	
	@Test
	public void testTrueNumberIsEven() {
	    Assert.assertTrue(WeatherAndMathUtils.isEven(4));
	}
	@Test
	public void testIsPrimeFor2() {
	    Assert.assertTrue(WeatherAndMathUtils.isPrime(2));
	}

	@Test
	public void testIsPrimeFor9() {
	    Assert.assertFalse(WeatherAndMathUtils.isPrime(9));
	}

	@Test
	public void testIsPrimeFor1() {
	    Assert.assertFalse(WeatherAndMathUtils.isPrime(1));
	}


	

	
	
	
}
	

	
