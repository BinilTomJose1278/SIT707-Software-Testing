package sit707_week2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation: https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * Author: Binil Tom Jose
 */
public class SeleniumOperations {

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void bunnings_registration_page(String url) {
        // Step 1: Set the path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        // Step 2: Launch Chrome browser
        System.out.println("Fire up chrome browser.");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30); 

        System.out.println("Driver info: " + driver);

        // Step 3: Open the target URL
        driver.get(url);

        // Step 4: Fill login form
        try {
        	WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        	emailField.sendKeys("johanj@example.com");

        	WebElement passwordField = driver.findElement(By.id("password"));
        	passwordField.sendKeys("12345");

        	WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(
        	        By.xpath("//button[contains(text(),'Sign in')]")));

        	((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInBtn);
        	signInBtn.click();


            // Step 5: Check for password error message
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Password')]")));
            if (errorMsg.isDisplayed()) {
                System.out.println("Password validation failed as expected. Error shown: " + errorMsg.getText());
            } else {
                System.out.println("No password error message shown.");
            }

        } catch (Exception e) {
            System.out.println("Exception during form interaction: " + e.getMessage());
        }

        // Step 6: Take a screenshot
        takeScreenshot(driver, "bunnings_login_attempt.png");

        // Step 7: Quit browser
        sleep(10); // Pause for 10 seconds
        driver.quit();

    }

    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            String screenshotDir = "C:\\Users\\binil\\OneDrive\\Pictures\\";
            File destination = new File(screenshotDir + fileName);

            Files.createDirectories(destination.getParentFile().toPath());
            Files.copy(screenshot.toPath(), destination.toPath());

            System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
