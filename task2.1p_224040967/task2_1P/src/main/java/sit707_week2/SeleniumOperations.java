package sit707_week2;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.Duration;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * @author Binil Tom Jose
 */
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void officeworks_registration_page(String url) {
		// Step 1: Locate chrome driver folder in the local drive.
		System.setProperty("webdriver.chrome.driver", "C:\\\\\\\\chromedriver-win64\\\\\\\\chromedriver.exe");
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30); // seconds

		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		// Fill Officeworks form fields
		 driver.findElement(By.id("firstname")).sendKeys("johan");
         driver.findElement(By.id("lastname")).sendKeys("jacob francis");
         driver.findElement(By.id("phoneNumber")).sendKeys("0468395792");
         driver.findElement(By.id("email")).sendKeys("johanj@example.com");
         driver.findElement(By.id("password")).sendKeys("12345");
         driver.findElement(By.id("confirmPassword")).sendKeys("12345");

        // Locate and click "Create account" button

         // Wait and locate "Create Account" button using XPath
         WebElement createAccountBtn = driver.findElement(By.xpath("//button[contains(text(),'Create account')]"));

         createAccountBtn.click();
        
        sleep(2);
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Password')]") 
            ));
        if (errorMsg.isDisplayed()) {
            System.out.println("Password validation failed as expected. Error shown: " + errorMsg.getText());
        } else {
            System.out.println("No password error message shown.");
        }
        // Take a screenshot
        takeScreenshot(driver, "officeworks_registration.png");

        driver.quit();
		
	}
	public static void github_registration_page(String url) {
	    System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

	    WebDriver driver = new ChromeDriver();
	    WebDriverWait wait = new WebDriverWait(driver, 10); // ✅ Works with Selenium 4+

	    driver.get(url);
	    System.out.println("Opened GitHub registration page.");

	    try {
	        // Fill GitHub registration form
	        driver.findElement(By.id("email")).sendKeys("biniltomjose12780@example.com");
	        driver.findElement(By.id("password")).sendKeys("StrongPass@123");
	        driver.findElement(By.id("login")).sendKeys("sarun1234");

	        // ✅ FIXED: Use the correct wait variable
	        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Continue']")));
	        continueBtn.click();

	        sleep(2);
	        takeScreenshot(driver, "github_registration.png");

	    } catch (Exception e) {
	        System.out.println("Error: Some elements were not found. Check element IDs.");
	        e.printStackTrace();
	    } finally {
	        driver.quit();
	    }
	}

	
	public static void takeScreenshot(WebDriver driver, String fileName) {
	    try {
	        TakesScreenshot ts = (TakesScreenshot) driver;
	        File screenshot = ts.getScreenshotAs(OutputType.FILE);

	        // Set the desired folder for saving screenshots
	        String screenshotDir = "C:\\Users\\binil\\OneDrive\\Pictures\\";
	        File destination = new File(screenshotDir + fileName);

	        // Ensure the directory exists
	        Files.createDirectories(destination.getParentFile().toPath());

	        // Save the file
	        Files.copy(screenshot.toPath(), destination.toPath());
	        System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
}
