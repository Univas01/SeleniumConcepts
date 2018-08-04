package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KeyPress {

    public static WebDriver driver;
    public static Properties prop;

    public KeyPress(){
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
        driver.get(prop.getProperty("urlGoogle"));
    }

    @Test
    public static void KeyPressTest(){
        WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
        element.sendKeys("Bitbucket");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).build().perform();
    }

    @Test
    public void keyPressTest1() {
        WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
        element.sendKeys("Bitbucket");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).build().perform();
        element.sendKeys("GitHub");
        action.sendKeys(Keys.ENTER).build().perform();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
