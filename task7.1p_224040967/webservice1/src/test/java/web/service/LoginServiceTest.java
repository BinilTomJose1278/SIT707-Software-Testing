package web.service;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginServiceTest {

    private void sleep(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WebDriver setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.navigate().to("file:///C:/Users/binil/Desktop/Softwaretesting/task7.1p_224040967/webservice1/pages/login.html");
        sleep(2);
        return driver;
    }

    @Test
    public void testLoginSuccess() {
        WebDriver driver = setupDriver();

        driver.findElement(By.id("username")).sendKeys("ahsan");
        driver.findElement(By.id("passwd")).sendKeys("ahsan_pass");
        driver.findElement(By.id("dob")).sendKeys("2000-01-01");

        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);

        Assert.assertEquals("success", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testLoginWrongUsername() {
        WebDriver driver = setupDriver();

        driver.findElement(By.id("username")).sendKeys("wrong_user");
        driver.findElement(By.id("passwd")).sendKeys("ahsan_pass");
        driver.findElement(By.id("dob")).sendKeys("2000-01-01");

        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);

        Assert.assertEquals("fail", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testLoginWrongPassword() {
        WebDriver driver = setupDriver();

        driver.findElement(By.id("username")).sendKeys("ahsan");
        driver.findElement(By.id("passwd")).sendKeys("wrong_pass");
        driver.findElement(By.id("dob")).sendKeys("2000-01-01");

        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);

        Assert.assertEquals("fail", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testLoginWrongDOB() {
        WebDriver driver = setupDriver();

        driver.findElement(By.id("username")).sendKeys("ahsan");
        driver.findElement(By.id("passwd")).sendKeys("ahsan_pass");
        driver.findElement(By.id("dob")).sendKeys("1999-12-31");

        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);

        Assert.assertEquals("fail", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testLoginEmptyFields() {
        WebDriver driver = setupDriver();

        driver.findElement(By.id("username")).sendKeys("");
        driver.findElement(By.id("passwd")).sendKeys("");
        driver.findElement(By.id("dob")).sendKeys("");

        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);

        Assert.assertEquals("fail", driver.getTitle());
        driver.quit();
    }
}
