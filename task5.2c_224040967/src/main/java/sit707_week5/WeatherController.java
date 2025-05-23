package sit707_week5;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

public class WeatherController {

	public static final int HOURS_PER_DAY = 3;
	private static WeatherController instance;

	private static double[] todaysHourlyTemperature = new double[HOURS_PER_DAY];

	private static Supplier<Date> timeSupplier = Date::new;

	public static WeatherController getInstance() {
		if (instance == null) {
			instance = new WeatherController();
		}
		return instance;
	}

	public static void setTimeSupplier(Supplier<Date> customSupplier) {
		timeSupplier = customSupplier;
	}

	public static void resetTimeSupplier() {
		timeSupplier = Date::new;
	}

	private WeatherController() {
		System.out.println("Creating new weather controller.");
		sleep(2 + new Random().nextInt(5));

		Random random = new Random();
		for (int i = 0; i < HOURS_PER_DAY; i++) {
			todaysHourlyTemperature[i] = 1 + random.nextInt(30);
		}
		System.out.println(Arrays.toString(todaysHourlyTemperature));
	}

	public void close() {
		System.out.println("Closing weather controller.");
		instance = null;
		sleep(2 + new Random().nextInt(5));
	}

	public double getTemperatureMinFromCache() {
		double minVal = 1000;
		for (double temp : todaysHourlyTemperature) {
			if (minVal > temp) minVal = temp;
		}
		return minVal;
	}

	public double getTemperatureMaxFromCache() {
		double maxVal = -1;
		for (double temp : todaysHourlyTemperature) {
			if (maxVal < temp) maxVal = temp;
		}
		return maxVal;
	}

	public double getTemperatureAverageFromCache() {
		double sumVal = 0;
		for (double temp : todaysHourlyTemperature) {
			sumVal += temp;
		}
		return sumVal / todaysHourlyTemperature.length;
	}

	public double getTemperatureForHour(int hour) {
		sleep(1 + new Random().nextInt(5));
		if (hour > todaysHourlyTemperature.length) {
			hour = 1 + new Random().nextInt(todaysHourlyTemperature.length);
		}
		return todaysHourlyTemperature[hour - 1];
	}

	public String persistTemperature(int hour, double temperature) {
		SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
		String savedTime = sdf.format(timeSupplier.get());
		System.out.println("Temperature: " + temperature + " of hour: " + hour + ", saved at " + savedTime);
		sleep(1 + new Random().nextInt(2));
		return savedTime;
	}

	public int getTotalHours() {
		return todaysHourlyTemperature.length;
	}

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
