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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BrokenLinks {

    public static WebDriver driver;
    public static Properties prop;

    public BrokenLinks(){
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
        driver.get(prop.getProperty("urlGit"));
    }

    @Test
    public static void BrokenLinkTest(){

        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        List<WebElement> ImgList = driver.findElements(By.tagName("img"));
        linkList.addAll(ImgList);

        int linkListCount = linkList.size();
        System.out.println("Total number of links and images ----> : " + linkListCount);
        List<WebElement> activeLinks = new ArrayList<>();

        for(int i = 0; i < linkListCount; i++ ){
            String linkListMessage = linkList.get(i).getAttribute("href");
            //System.out.println(linkListMessage);
            if(linkListMessage != null && (!linkListMessage.contains("javascript"))){
               activeLinks.add(linkList.get(i));
            }
        }

        for(int j = 0; j < activeLinks.size(); j++){
            String activeLinkMessages = activeLinks.get(j).getAttribute("href");
            try{
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(activeLinkMessages).openConnection();
                httpURLConnection.connect();
                String response = httpURLConnection.getResponseMessage();
                httpURLConnection.disconnect();
                if(!response.equals("OK") && !response.equals("Found")){
                    System.out.println(activeLinkMessages + " ----> " + response);
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
