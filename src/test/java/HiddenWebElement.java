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


public class HiddenWebElement {

    public static WebDriver driver;
    public static Properties prop;

    public HiddenWebElement() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeMethod
    public void setUp() {
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browsers/chromedriver");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "browsers/geckodriver");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(prop.getProperty("urlHidden"));
    }

    @Test
    public static void hiddenWebElementTest() {

        List<WebElement> radioBtn = driver.findElements(By.id("male"));
        int radioBtnCount = radioBtn.size();
        System.out.println("Total number of Hidden Elements radioBtnCount is " + radioBtnCount);
        for (int i = 0; i < radioBtnCount; i++) {
            int radioBtnLocationX = radioBtn.get(i).getLocation().getX();
            if (radioBtnLocationX != 0) {
                radioBtn.get(i).click(); // Click on first radio button where X location is not 0
                break;
            }
        }
    }

    @Test
    public static void clickOnHiddenElement() {

        hiddenWebElementTest2("radio");
    }

    public static void hiddenWebElementTest2(String id) {
        List<WebElement> radioBtn2 = driver.findElements(By.xpath("//input[@type='"+id+"']"));
        int radioBtnCount2 = radioBtn2.size();
        System.out.println("Total number of Hidden Elements radioBtnCount is " + radioBtnCount2);
    }

    @AfterMethod(enabled = false)
    public void tearDown() {
        driver.quit();
    }

}
