package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/** No such Element Exception can be caused by 3 issues
 * 1. Timing Issues - resolved by implicitlyWait or explicitWait
 * 2. Incorrect locator or type of locator - Ensure an element is identified correctly
 * 3. Element is in iFrame
 * https://www.youtube.com/watch?v=y6SYtAh-kCk
 */

public class Exception_NoSuchElementException {

    public static WebDriver driver;
    public static Properties prop;

    public Exception_NoSuchElementException(){
        try{
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @BeforeMethod
    public void setUp(){
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/browsers/chromedriver");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"browsers/geckodriver");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("safari")){
            driver = new SafariDriver();
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(prop.getProperty("urlLetsKodeit"));
    }

    @Test
    public static void noSuchElementExceptionTest(){
        scrollPageDown(driver);
        //driver.switchTo().frame("courses-iframe");
        driver.findElement(By.xpath("//input[contains(@id,'search-courses')]")).sendKeys("Java");
        driver.switchTo().defaultContent(); // switching back to main / parent window
        WebElement bmwCheckBox = driver.findElement(By.xpath("//input[contains(@id, 'bmwcheck')]"));
        scrollIntoView(bmwCheckBox,driver);
        bmwCheckBox.click();

    }


    public static void scrollPageDown(WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    public static void scrollIntoView(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
