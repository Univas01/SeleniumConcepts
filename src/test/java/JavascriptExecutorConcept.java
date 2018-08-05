package test.java;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class JavascriptExecutorConcept {

    public static WebDriver driver;
    public static Properties prop;

    public JavascriptExecutorConcept(){
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
        driver.get(prop.getProperty("urlFreecrm"));
    }

    @Test
    public static void javascriptTest(){
        driver.findElement(By.xpath("//input[contains(@name, 'username')]")).sendKeys("univas01");
        driver.findElement(By.xpath("//input[contains(@name, 'password')]")).sendKeys("Computer1!");
        WebElement submitBtn = driver.findElement(By.xpath("//input[contains(@type, 'submit')]"));

        // flash the element
        flash(submitBtn, driver);

        // draw a border
        drawBorder(submitBtn, driver);

        // click on any element via JavaScript
        // clickElementByJS(submitBtn, driver);

        // take screen shots
        takeScreenGrab("freecrm");

        // refresh Browser by JS
        refreshBrowserByJS(driver);

        // get page title by JS
        System.out.println("Page Title is :- "+getTitleByJS(driver));

        // get page Text by JS
        System.out.println(getPageInnerText(driver));

        // scroll down the page
        scrollPageDown(driver);

   }

    public static void takeScreenGrab(String fileName){
        try{
            File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File(System.getProperty("user.dir")+"/screenshots/"+fileName+".jpg"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void flash(WebElement element, WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        String bgColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 50; i++){
            changeColor("rgb(0,200,0)",element,driver);
            changeColor(bgColor, element, driver);
        }
    }

    public static void changeColor(String color, WebElement element, WebDriver driver) {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].style.backgroundColor = '"+color+"'", element);

        try{
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    public static void drawBorder(WebElement element, WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].style.border = '3px solid red'", element);
    }

    public static void generateAlert(WebDriver driver, String message){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("alert('"+message+"')");
    }

    public static void clickElementByJS(WebElement element, WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].click();", element);
    }

    public static void refreshBrowserByJS(WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("history.go(0)");
    }

    public static String getTitleByJS(WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        String title = jse.executeScript("return document.title;").toString();
        return title;
    }

    public static String getPageInnerText(WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        String pageText = jse.executeScript("return document.documentElement.innerText;").toString();
        return pageText;
    }

    public static void scrollPageDown(WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    public static void scrollIntoView(WebElement element, WebDriver driver){
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterMethod (enabled = false)
    public void tearDown() {
        driver.quit();
    }

}
