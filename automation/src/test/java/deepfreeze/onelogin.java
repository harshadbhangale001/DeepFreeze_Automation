package deepfreeze;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class onelogin extends df_login{
	
	@Test(priority=1)
	public void tempmail() throws HeadlessException, UnsupportedFlavorException, IOException, InterruptedException{
		System.out.println("OneLogin");
		String url1=ConfigReader.get("tempmailurl");
		String url2=ConfigReader.get("oneloginurl");
		
		((JavascriptExecutor) driver).executeScript("window.open('"+url1+"')");
		((JavascriptExecutor) driver).executeScript("window.open('"+url2+"')");
		
		Set<String> allTabs = driver.getWindowHandles();
		Iterator<String> it = allTabs.iterator();
		
		String df_tab = it.next();
		String temp_tab = it.next();
		String onelogin_tab =it.next();
		
		//Copy Temp Mail
		
		driver.switchTo().window(temp_tab);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='domain']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'chitthi.in')]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='pre_copy']"))).click();
		String temp_mail=(String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println("Temp Mail:"+temp_mail);
		
		//Create OneLogin Account
		driver.switchTo().window(onelogin_tab);
		driver.findElement(By.xpath("//a[@class='btn-v3 btn-default-color btn-v3-small']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Email']"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Email']"))).sendKeys(temp_mail);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']"))).sendKeys(ConfigReader.get("domain_name"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='chkLicense']"))).click();
		Thread.sleep(10000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Get Started']"))).click();
		
		
		//OneLogin SignUP form
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='FirstName']"))).sendKeys(ConfigReader.get("firstname"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='LastName']"))).sendKeys(ConfigReader.get("lastname"));
		new Select(driver.findElement(By.xpath("//select[@id='Title']"))).selectByIndex(3);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Company']"))).sendKeys(ConfigReader.get("company"));
		new Select(driver.findElement(By.xpath("//select[@id='CountryCode']"))).selectByIndex(2);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='City']"))).sendKeys(ConfigReader.get("city"));
		new Select(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='State_Province']")))).selectByIndex(21);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='PostalCode']"))).sendKeys(ConfigReader.get("postal_code"));
		new Select(driver.findElement(By.xpath("//select[@id='EmployeeCount']"))).selectByIndex(3);
		new Select(driver.findElement(By.xpath("//select[@id='Industry']"))).selectByIndex(16);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Phone']"))).sendKeys(ConfigReader.get("business_phone"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']"))).sendKeys(ConfigReader.get("domain_name"));
		driver.findElement(By.xpath("//div[@class='trial-additional-note']"));
		//Thread.sleep(10000);
		boolean isErrorPresent;
		try {
			
			WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Please choose another domain.')]")));
			isErrorPresent=errorElement.isDisplayed();
			
		}
		catch(TimeoutException e)
		{
			isErrorPresent = false;
		}
		
		if(isErrorPresent==true){
			
			System.out.println("Choose Another Domain Error Shown: "+ isErrorPresent);
			
		
		}
		else {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Get Started']"))).click();
			
		}
		
		
	}
	
}
