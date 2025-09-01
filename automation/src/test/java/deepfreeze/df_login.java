package deepfreeze;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.ConfigReader;


public class df_login extends BaseClass{
	
	@BeforeClass
	public void login()
	{        
        //DeepFeeze Login
		driver.get(ConfigReader.get("url"));
		driver.findElement(By.xpath("//input[@id='txtUserName']")).sendKeys(ConfigReader.get("username"));
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(ConfigReader.get("password"));
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
	}
	
	@Test
    public void verifyLogin() {
        // Add an assertion or validation here to check login was successful
        String loggedInUser = driver.findElement(By.xpath("//li[@id='logg_main']")).getText();
        System.out.println("Logged in as: " + loggedInUser);
    }
	
//	@AfterClass
//	void logout()
//	{
//		//DeepFreeze Logout
//		driver.findElement(By.xpath("//li[@id='logg_main']")).click();
//		driver.findElement(By.xpath("//a[@id='aSignOut']")).click();
//		driver.quit();
//	}
//	
	
}

