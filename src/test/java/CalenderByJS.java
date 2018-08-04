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


public class CalenderByJS {

    public static WebDriver driver;
    public static Properties prop;

    public CalenderByJS(){
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(prop.getProperty("urlSpiceJet"));
    }

    @Test
    public static void handleCalenderByJSTest() {

        String departDateValue = "15-08-2018";
        WebElement departDateField = driver.findElement(By.xpath("//input[@id='ctl00_mainContent_txt_Fromdate']"));
        selectDateByJS(departDateField, departDateValue);

        String returnDateValue = "30-09-2018";
        WebElement returnDateField = driver.findElement(By.xpath("//input[@id='ctl00_mainContent_txt_Todate']"));
        selectDateByJS(returnDateField, returnDateValue);
    }

    public static void selectDateByJS(WebElement element, String dateValue) {
        JavascriptExecutor js = ((JavascriptExecutor)driver);
        js.executeScript("arguments[0].setAttribute('value',\'"+dateValue+"\');", element);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
