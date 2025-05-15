package sit707_week2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class MainTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
         wait = new WebDriverWait(driver, 30); 
        driver.get("https://www.bunnings.com.au/login");
    }

    @After
    public void tearDown() {
        try {
            Thread.sleep(5000); // Wait to observe before closing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    private void attemptLogin(String username, String password) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        emailField.clear();
        emailField.sendKeys(username);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.clear();
        passwordField.sendKeys(password);
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(
        	    By.xpath("//button[.//span[text()='Sign in']]")));

        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signInBtn);
        	((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInBtn);


    }

    @Test
    public void testEmptyUsernameAndPassword() {
        attemptLogin("", "");
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }



    @Test
    public void testValidUsernameEmptyPassword() {
        attemptLogin("test@example.com", "");
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void testEmptyUsernameValidPassword() {
        attemptLogin("", "12345");
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }



    @Test
    public void testInvalidCredentials() {
        attemptLogin("wrong@example.com", "wrongpass");
        assertTrue(driver.getCurrentUrl().contains("/login")); 
    }

    @Test
    public void testValidCredentials_NoAccount() {
        attemptLogin("johanj@example.com", "12345");
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    // Replace with your name and ID to simulate a "pass"
    @Test
    public void testNameAndId() {
        attemptLogin("BinilTomJose", "123456789");
        assertTrue(driver.getCurrentUrl().contains("/login")); 
    }

}
