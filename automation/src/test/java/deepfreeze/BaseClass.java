package deepfreeze;

import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import utils.ExtentTestListener;

public class BaseClass {
	
	public static WebDriver driver;
    public static WebDriverWait wait;
    public static Wait<WebDriver> fwait;
    
    @BeforeSuite
    public void setUp() {
        String downloadFilepath = "C:\\Downloads";
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("safebrowsing.disable_download_protection", true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--safebrowsing-disable-download-protection");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        fwait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(120))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        
        ExtentTestListener.driver = driver;
        driver.manage().window().maximize();
    }
    
    public void logStep(Status status, String message) {
        if (ExtentTestListener.getTest() != null) {
            ExtentTestListener.getTest().log(status, message);
        } else {
            System.out.println("No ExtentTest found for this thread");
        }
    }


    
//    @AfterSuite
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
		
}
