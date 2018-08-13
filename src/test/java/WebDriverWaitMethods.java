package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWaitMethods {

    public static void sendKeys(WebDriver driver, WebElement element, int timeout, String value) {
        WebDriverWait one = new WebDriverWait(driver, timeout);
        one.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(value);
    }

    public static void clickOnElement(WebDriver driver, WebElement element, int timeout) {
        WebDriverWait one = new WebDriverWait(driver, timeout);
        one.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static boolean onElementt(WebDriver driver, int timeout, String id) {
        WebDriverWait one = new WebDriverWait(driver, timeout);
        one.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
       return true;
    }

}
