package tasks;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SeleniumPresenceTest {

    private static ThreadLocal<WebDriver> browser = new ThreadLocal<>();

    @BeforeClass
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("start-maximized"); // open Browser in maximized mode
        WebDriver driver = new ChromeDriver(options);
        browser.set(driver);
    }

    @Test
    public void SeleniumPresenceTest() {

        WebDriver driver = getDriver();

        driver.get("http://200.168.0.1/index.php");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@name='login-submit']"))));
    }

    private static synchronized WebDriver getDriver() {
        return browser.get();
    }
}
