package test.java;

import org.openqa.selenium.By;
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


public class StaleElementException {

    public static WebDriver driver;
    public static Properties prop;

    public StaleElementException(){
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
    public static void windowSwitchTest(){
        WebElement bmwCheckBox = driver.findElement(By.xpath("//input[contains(@id, 'bmwcheck')]"));
        /*The below action will reload the page which then automatically refreshes the DOM
        and makes bmwCheckBox unavailable (StaleElementReferenceException is thrown).

        Solution:
        Comment getCurrentUrl code and run test again - no error will be thrown.
        To resolve stale element exception, ensure you perform on element after identifying such element
        https://www.youtube.com/watch?v=yYQrtmNn6Uo
        */

        driver.get(driver.getCurrentUrl());
        bmwCheckBox.click();
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
