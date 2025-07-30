package deepfreeze;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utils.ConfigReader;


public class df_login {
	
	
	public static WebDriver driver;
	//String username="harshad.bhangale317@gmail.com";
	//String password="Aloha@123";
	
	@BeforeSuite 
	public void login()
	{
        //Set download path
        String downloadFilepath = "C:\\Downloads";

        //Chrome preferences
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("safebrowsing.disable_download_protection", true);

        //Chrome options
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--safebrowsing-disable-download-protection");
        options.addArguments("--no-sandbox");

        // Initialize Chrome driver
        driver = new ChromeDriver(options);
 
		//System.out.println("In login(), driver = " + driver);
		driver.manage().window().maximize();
		driver.get(ConfigReader.get("url"));
		driver.findElement(By.xpath("//input[@id='txtUserName']")).sendKeys(ConfigReader.get("username"));
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(ConfigReader.get("password"));
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
	}
	
	@AfterSuite
	void logout()
	{
		driver.findElement(By.xpath("//li[@id='logg_main']")).click();
		driver.findElement(By.xpath("//a[@id='aSignOut']")).click();
		driver.quit();
	}
	
	
}
