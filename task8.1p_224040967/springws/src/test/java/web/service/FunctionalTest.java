package web.service;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

public class FunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5); 
    }

    @Test
    public void testFullQuizFlowSuccess() {
        driver.get("http://localhost:8080/login");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        driver.findElement(By.name("username")).sendKeys("ahsan");
        driver.findElement(By.name("passwd")).sendKeys("ahsan_pass");
        driver.findElement(By.name("dob")).sendKeys("2000-01-01");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // Q1
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("10");
        driver.findElement(By.name("number2")).sendKeys("5");
        driver.findElement(By.name("result")).sendKeys("15");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // Q2
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("20");
        driver.findElement(By.name("number2")).sendKeys("4");
        driver.findElement(By.name("result")).sendKeys("16");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // Q3
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("result")));
        driver.findElement(By.name("result")).sendKeys("42");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("congratulations"));
    }

    @Test
    public void testInvalidLogin() {
        driver.get("http://localhost:8080/login");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        driver.findElement(By.name("username")).sendKeys("wronguser");
        driver.findElement(By.name("passwd")).sendKeys("wrongpass");
        driver.findElement(By.name("dob")).sendKeys("2000-01-01");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertTrue(driver.getPageSource().contains("Incorrect credentials"));
    }

    @Test
    public void testQ1WrongAnswer() {
        driver.get("http://localhost:8080/login");

        loginCorrect();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("10");
        driver.findElement(By.name("number2")).sendKeys("5");
        driver.findElement(By.name("result")).sendKeys("99"); // wrong answer
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/q1"));
        Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
    }

    @Test
    public void testQ2WrongAnswer() {
        driver.get("http://localhost:8080/login");

        loginCorrect();
        answerQ1Correct();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("20");
        driver.findElement(By.name("number2")).sendKeys("4");
        driver.findElement(By.name("result")).sendKeys("0"); // wrong answer
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/q2"));
        Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
    }

    @Test
    public void testQ3WrongAnswer() {
        driver.get("http://localhost:8080/login");

        loginCorrect();
        answerQ1Correct();
        answerQ2Correct();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("result")));
        driver.findElement(By.name("result")).sendKeys("99"); // wrong answer
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/q3"));
        Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
    }

    // === Helper Methods ===

    private void loginCorrect() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        driver.findElement(By.name("username")).sendKeys("ahsan");
        driver.findElement(By.name("passwd")).sendKeys("ahsan_pass");
        driver.findElement(By.name("dob")).sendKeys("2000-01-01");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    private void answerQ1Correct() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("10");
        driver.findElement(By.name("number2")).sendKeys("5");
        driver.findElement(By.name("result")).sendKeys("15");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    private void answerQ2Correct() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("number1")));
        driver.findElement(By.name("number1")).sendKeys("20");
        driver.findElement(By.name("number2")).sendKeys("4");
        driver.findElement(By.name("result")).sendKeys("16");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
