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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Frames {

    public static WebDriver driver;
    public static Properties prop;

    public Frames(){
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
    public static void findNumberOfFramesTest(){
        scrollPageDown(driver);
        String searchTextBox = "//input[contains(@id, 'search-courses')]";
        int number = findNumberOfFrames(driver, By.xpath(searchTextBox));
        driver.switchTo().frame(number);
        driver.findElement(By.xpath(searchTextBox)).sendKeys("Java");
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

    public static int findNumberOfFrames(WebDriver driver, By by){
        List<WebElement> frameCount = driver.findElements(By.tagName("iframe"));
        System.out.println("Total number of frames ===> " + frameCount.size());
        int i;
        for(i = 0; i < frameCount.size(); i++){
            driver.switchTo().frame(i);
            List<WebElement> count = driver.findElements(by);
            if(count.size()>0){
                driver.findElement(by).click();
                break;
            } else {
                System.out.println("continue looping");
            }
        }
        driver.switchTo().defaultContent();
        return i;
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
