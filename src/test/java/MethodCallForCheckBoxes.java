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


public class MethodCallForCheckBoxes {

    public static WebDriver driver;
    public static Properties prop;

    public MethodCallForCheckBoxes(){
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
        driver.get(prop.getProperty("urlw3"));
    }

    @Test
    public static void SelectCheckBoxes(){
        // Method 1
        String checkBox = "//div[contains(text(), 'Sprouts')]";
        selectCheckBox(By.xpath(checkBox));

        // Method 2
        /*tickByCheckBoxName("Lettuce");
        tickByCheckBoxName("Tomato");
        tickByCheckBoxName("Mustard");
        tickByCheckBoxName("Sprouts");*/
    }

    public static void selectCheckBox(By by){
        WebElement tickCheckBox = driver.findElement(by);
        tickCheckBox.click();
    }

    public static void tickByCheckBoxName(String name){
        WebElement tickCheckBox = driver.findElement(By.xpath("//div[contains(text(), '"+name+"')]"));
        tickCheckBox.click();
    }

    @AfterMethod
    public void tearDown() {
        //driver.quit();
    }

}
