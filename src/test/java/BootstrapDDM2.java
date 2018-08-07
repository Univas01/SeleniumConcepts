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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BootstrapDDM2 {

    public static WebDriver driver;
    public static Properties prop;

    public BootstrapDDM2(){
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
        driver.get(prop.getProperty("urlBootsnipp"));
    }

    @Test
    public static void BootstrapDDMTest(){
        driver.findElement(By.xpath("//ul[@class='nav navbar-nav navbar-right']//child::li[3]//a[1]")).click();

        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='nav navbar-nav navbar-right']//child::li[3]//ul//li"));
        int objectCount = list.size();
        System.out.println(objectCount);

        for(int i = 0; i < objectCount; i++){
            String text = list.get(i).getText();
            System.out.println(text);
            if(text.equalsIgnoreCase("VirtueMart")){
                list.get(i).click();
                break;
            }

        }
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
