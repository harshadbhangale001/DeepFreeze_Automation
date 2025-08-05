package deepfreeze;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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
			// Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(ConfigReader.get("password"));
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
	}
	
	@AfterClass
	void logout()
	{
		//DeepFreeze Logout
		driver.findElement(By.xpath("//li[@id='logg_main']")).click();
		driver.findElement(By.xpath("//a[@id='aSignOut']")).click();
		driver.quit();
	}
	
	
}

