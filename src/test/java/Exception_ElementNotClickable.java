package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 1. ExplicitlyWait Class with invisibilityOfElementLocated OR
 * 2. Click by JavaScript
 */

public class Exception_ElementNotClickable {

    public static WebDriver driver;
    public static Properties prop;

    public Exception_ElementNotClickable(){
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(prop.getProperty("urlFreecrm"));
    }

    @Test
    public static void elementNotClickableTest(){
        driver.findElement(By.xpath("//input[contains(@name, 'username')]")).sendKeys("univas01");
        driver.findElement(By.xpath("//input[contains(@name, 'password')]")).sendKeys("Computer1!");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Boolean preloader = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("preloader")));
        if(preloader){
            driver.findElement(By.xpath("//input[contains(@type, 'submit')]")).click();
        }
    }


    @Test (enabled = false)
    public static void elementNotClickableTest1(){
        driver.findElement(By.xpath("//input[contains(@name, 'username')]")).sendKeys("univas01");
        driver.findElement(By.xpath("//input[contains(@name, 'password')]")).sendKeys("Computer1!");
        onElementt(driver,10, "preloader");
        driver.findElement(By.xpath("//input[contains(@type, 'submit')]")).click();

    }


    public static boolean onElementt(WebDriver driver, int timeout, String id) {
        WebDriverWait one = new WebDriverWait(driver, timeout);
        one.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
        return true;
    }

    @AfterMethod
    public void tearDown() {

        // driver.quit();
    }

}
