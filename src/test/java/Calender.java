package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Calender {

    public static WebDriver driver;
    public static Properties prop;

    public Calender(){
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
        driver.get(prop.getProperty("urlEasyJet"));
    }

    @Test
    public static void handleCalenderTest(){

        // click on departing date field
        driver.findElement(By.xpath("//span[contains(text(), 'Departing')]")).click();

        // wait for presence of calendar object
        WebDriverWait waitForCalendarObject = new WebDriverWait(driver, 10);
        waitForCalendarObject.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//div[@ej-day-highlighting-is-outbound='false']//h3[contains(text(), 'August 2018')]/ancestor::div[@class='month']")));

        // calendar object in web element
        WebElement departingCalenderObject = driver
                .findElement(By.xpath("//div[@ej-day-highlighting-is-outbound='true']//h3[contains(text(), 'August 2018')]/following-sibling::*"));

        // Loop through each date and select a date
        List<WebElement> departDateList = departingCalenderObject.findElements(By.tagName("div"));
        String departDatePicker = "10";
        for (WebElement departCell : departDateList) {
            if (departCell.getText().equals(departDatePicker)) {
                System.out.println("Depart date found");
                departCell.click();
                break;
            }
        }

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
