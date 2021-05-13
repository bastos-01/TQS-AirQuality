package tqs.airquality.selenium;

// Generated by Selenium IDE
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class SeleniumTest {

    private WebDriver driver;
    private Map<String, Object> vars;

    JavascriptExecutor js;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @AfterEach
    void tearDown() {
        driver.close();
    }

    @Test
    void searchCity() throws InterruptedException {
        driver.get("http://localhost:8000/");
        driver.manage().window().setSize(new Dimension(1920, 1123));
        js.executeScript("window.scrollTo(0,331)");


        driver.findElement(By.id("countries")).click();
        {
            WebElement dropdown = driver.findElement(By.id("countries"));
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0,368)");


        driver.findElement(By.id("states")).click();
        {
            WebElement dropdown = driver.findElement(By.id("states"));
            Thread.sleep(2000);
            //dropdown.findElement(By.xpath("//option['Algiers']")).click();
        }
        driver.findElement(By.cssSelector("#states > option:nth-child(2)")).click();
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0,368)");


        driver.findElement(By.id("cities")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cities"));
            Thread.sleep(2000);
            //dropdown.findElement(By.xpath("//option['Algiers']")).click();
        }
        driver.findElement(By.cssSelector("#cities > option:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("searchButton")).click();
        Thread.sleep(2000);
        assertTrue(driver.findElement(By.id("tpfill")).getSize().width > 0);
    }

    @Test
    void cacheChanges() throws InterruptedException {
        driver.get("http://localhost:8000/");
        driver.manage().window().setSize(new Dimension(1920, 1123));
        js.executeScript("window.scrollTo(0,331)");

        String requests_before = driver.findElement(By.id("requests")).getText();

        driver.findElement(By.id("countries")).click();
        {
            WebElement dropdown = driver.findElement(By.id("countries"));
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0,368)");


        driver.findElement(By.id("states")).click();
        {
            WebElement dropdown = driver.findElement(By.id("states"));
            Thread.sleep(2000);
            //dropdown.findElement(By.xpath("//option['Algiers']")).click();
        }
        driver.findElement(By.cssSelector("#states > option:nth-child(2)")).click();
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0,368)");


        driver.findElement(By.id("cities")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cities"));
            Thread.sleep(2000);
            //dropdown.findElement(By.xpath("//option['Algiers']")).click();
        }
        driver.findElement(By.cssSelector("#cities > option:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("searchButton")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("updateButton")).click();
        Thread.sleep(2000);
        assertNotEquals(requests_before, driver.findElement(By.id("requests")).getText());
    }
}
