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

public class DynamicWebTable {

    public static WebDriver driver;
    public static Properties prop;

    //*[@id="customers"]/tbody/tr[2]/td[1]
    //*[@id="customers"]/tbody/tr[3]/td[1]
    //*[@id="customers"]/tbody/tr[4]/td[1]

    //*[@id="customers"]/tbody/tr[2]/td[2]
    //*[@id="customers"]/tbody/tr[3]/td[2]
    //*[@id="customers"]/tbody/tr[4]/td[2]

    //*[@id="customers"]/tbody/tr[2]/td[3]
    //*[@id="customers"]/tbody/tr[3]/td[3]
    //*[@id="customers"]/tbody/tr[4]/td[3]

    String beforeCompany_xpath = "//*[@id='customers']/tbody/tr[";
    String afterCompany_xpath = "]/td[1]";

    String beforeContact_xpath = "//*[@id='customers']/tbody/tr[";
    String afterContact_xpath = "]/td[2]";

    String beforeCountry_xpath = "//*[@id='customers']/tbody/tr[";
    String afterCountry_xpath = "]/td[3]";


    @BeforeMethod
    public void setUp(){

        try{
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

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
        driver.get(prop.getProperty("urlDynamicWebTable"));
    }

    @Test (enabled = true, priority = 1)
    public void dynamicWebTableTest(){

        for( int i = 2; i <= 7; i++){

            System.out.println("=================");
            String company_xpath = beforeCompany_xpath+i+afterCompany_xpath;
            String companyName = driver.findElement(By.xpath(company_xpath)).getText();
            System.out.println("companyName : "+companyName);

            String contact_xpath = beforeContact_xpath+i+afterContact_xpath;
            String contactName = driver.findElement(By.xpath(contact_xpath)).getText();
            System.out.println("contactName : "+contactName);

            String country_xpath = beforeCountry_xpath+i+afterCountry_xpath;
            String countryName = driver.findElement(By.xpath(country_xpath)).getText();
            System.out.println("countryName : "+countryName);
        }
    }

    @Test (enabled = true, priority = 2)
    public void dynamicWebTableSecondTest(){

        List<WebElement> row = driver.findElements(By.xpath("//table[@id='customers']//tr"));
        int rowCount = row.size();
        System.out.println("Total number of rows "+ (rowCount-1));

        for( int i = 2; i <= rowCount; i++){

            System.out.println("=================");
            String company_xpath = beforeCompany_xpath+i+afterCompany_xpath;
            String companyName = driver.findElement(By.xpath(company_xpath)).getText();
            System.out.println("companyName : "+companyName);

            String contact_xpath = beforeContact_xpath+i+afterContact_xpath;
            String contactName = driver.findElement(By.xpath(contact_xpath)).getText();
            System.out.println("contactName : "+contactName);

            String country_xpath = beforeCountry_xpath+i+afterCountry_xpath;
            String countryName = driver.findElement(By.xpath(country_xpath)).getText();
            System.out.println("countryName : "+countryName);
        }
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
