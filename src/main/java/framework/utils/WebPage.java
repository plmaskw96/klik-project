package framework.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class WebPage {

    protected WebDriver driver;
    protected WebWaitUtils webWaitUtils;

    public WebPage(WebDriver driver) {
        this.driver = driver;
        this.webWaitUtils = new WebWaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void switchToFrame(WebElement webElement) {
        driver.switchTo().frame(webElement);
    }

    public void switchToNextWindow() {
        for (String curWindow : driver.getWindowHandles()) {
            driver.switchTo().window(curWindow);
        }
    }

    public void jsClickElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public void clickElementAndWait(WebElement element, int wait) {
        Actions action = new Actions(driver);
        action.click(element).pause(wait).perform();
    }

    public void enterText(WebElement element, String text) {
        element.click();
        element.sendKeys(text);
    }

    public void selectOption(WebElement element, String option) {
        new Select(element).selectByVisibleText(option);
    }

    public void scrollToElementByAction(WebElement webElement) {
        Actions action = new Actions(driver);
        action.scrollToElement(webElement).perform();
    }

    public void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public WebElement getSelectOption(WebElement element, String option) {
        return new Select(element)
                .getOptions()
                .stream()
                .filter(webElement -> webElement.getText().equals(option))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Option " + option + " not found!!!"));
    }

    public void waitForLoading(WebElement webElement, long timeout) {
        if (isDisplayed(webElement)) {
            webWaitUtils.waitForNotVisible(webElement, timeout);
        }
        webWaitUtils.waitForVisible(webElement, timeout);
    }
}
