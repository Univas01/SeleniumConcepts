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


public class DynamicWebSerch {

    public static WebDriver driver;
    public static Properties prop;

    public DynamicWebSerch(){
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
        driver.get(prop.getProperty("urlYahoo"));
    }

    @Test
    public static void windowSwitchTest(){

        WebElement okBtn = driver.findElement(By.xpath("//input[@value='OK']"));
        scrollIntoView(okBtn,driver);
        okBtn.click();

        WebElement searchTextBox = driver.findElement(By.xpath("//input[@id='uh-search-box']"));
        searchTextBox.clear();
        searchTextBox.sendKeys("Software Testing");

        List<WebElement> searchResults = driver.findElements(By.xpath("//input[@id='uh-search-box']//parent::*/descendant::li"));
        int resultSize = searchResults.size();
        System.out.println(resultSize);

        for(int i=0; i <= resultSize; i++){
            String actualResult = searchResults.get(i).getText();
            System.out.println(actualResult);
            String expectedResult = "software testing help";
            if(actualResult.equalsIgnoreCase(expectedResult)){
                searchResults.get(i).click();
                break;
            }
        } System.out.println("No result found");

    }

    public static void scrollIntoView(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterMethod (enabled = false)
    public void tearDown() {
        driver.quit();
    }

}
