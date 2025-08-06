package deepfreeze;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.CredentialManager;

public class user_management_saml extends BaseClass{
	
	public static onelogin ol = new onelogin();
	
	
	@Test(priority=1)
	public void onelogin_signin() throws HeadlessException, UnsupportedFlavorException, IOException, InterruptedException
	{
		
		System.out.println(CredentialManager.getUsername());
		System.out.println(CredentialManager.getPassword());
		System.out.println(CredentialManager.getCreatedDate());
		
		String storedDateStr = CredentialManager.getCreatedDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate storedDate = LocalDate.parse(storedDateStr, formatter);
		LocalDate today =  LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(storedDate, today);
		System.out.println("Days Old:"+daysBetween);
		
		String oneloginprofile_url = "https://" + (CredentialManager.getUsername()).replace("@chitthi.in", "") + ".onelogin.com";
		
		System.out.println(oneloginprofile_url);
		
		if(daysBetween<30) {
			
			driver.get("https://" + (CredentialManager.getUsername()).replace("@chitthi.in", "") + ".onelogin.com");
			System.out.println(oneloginprofile_url);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='username']"))).sendKeys(CredentialManager.getUsername());
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password']"))).sendKeys(CredentialManager.getPassword());
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
		}
		else {
			
			ol.onelogin_signup(driver, wait);
			
		}
		
	}
	
	@Test(priority=2, dependsOnMethods= {"onelogin_signin"})
	public void onelogin_appCreate() throws HeadlessException, UnsupportedFlavorException, IOException, InterruptedException, AWTException
	{
		String url1 = ConfigReader.get("url");
		//String url2 = "https://" + (CredentialManager.getUsername()).replace("@chitthi.in", "") + ".onelogin.com";
		
		((JavascriptExecutor) driver).executeScript("window.open('"+url1+"')");
		//((JavascriptExecutor) driver).executeScript("window.open('"+url2+"')");
		
		Set<String> openTabs = driver.getWindowHandles();
		Iterator<String> it = openTabs.iterator();
		
		String onelogin_tab = it.next();
		String df_tab = it.next();
		
		
//		driver.switchTo().window(onelogin_tab);
//		
//		try{
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Close\"]"))).click();
//		}catch(TimeoutException e) {
//			System.out.println("Element is not present.");
//		}
//		
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Applications']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Applications']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add App']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='text']"))).sendKeys("SAML");
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[text()='SAML Custom Connector (Advanced)']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='app_name']"))).clear();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='app_name']"))).sendKeys(ConfigReader.get("app_name"));
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='save_app']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#configuration']"))).click();
//		
		driver.switchTo().window(df_tab);
		
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
		driver.findElement(By.xpath("//li[@id='logg_main']")).click();
		driver.findElement(By.xpath("//a[@id='aLogin_User_Management']")).click();
		driver.findElement(By.xpath("//button[@id='btnSAMLIntegration']")).click();
		driver.findElement(By.xpath("//input[@id='btnAudianceURI']")).click();
		String audience_uri = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		driver.findElement(By.xpath("//input[@id='btnAssertionURL']")).click();
		String assertion_consumer_uri = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		driver.findElement(By.xpath("//input[@id='btnSAMLLoginURL']")).click();
		String saml_login_uri = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	
		System.out.println("Audiende URI:" + audience_uri);
		System.out.println("Assertion Consumer URI:" + assertion_consumer_uri);
		System.out.println("SAML Login URI:" + saml_login_uri);
//		
//		driver.switchTo().window(onelogin_tab);
//		
//		driver.findElement(By.xpath("//input[@id='param_243478']")).sendKeys(audience_uri);
//		driver.findElement(By.xpath("//input[@id='param_243480']")).sendKeys(assertion_consumer_uri);
//		driver.findElement(By.xpath("//input[@id='param_243481']")).sendKeys(assertion_consumer_uri);
//		driver.findElement(By.xpath("//input[@id='param_243494']")).sendKeys(saml_login_uri);
//		
//		driver.findElement(By.xpath("//a[@href='#fields']")).click();
//		
//		//To add user.email
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='add-parameter']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='parameter_name']"))).sendKeys("user.email");
//		driver.findElement(By.xpath("//label[@for='parameter_saml_parameter']")).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='- No default -']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Email']"))).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		
//		//To add user.firstName
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='add-parameter']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='parameter_name']"))).sendKeys("user.firstName");
//		driver.findElement(By.xpath("//label[@for='parameter_saml_parameter']")).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='- No default -']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='First Name']"))).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		
//		//To add user.lastName
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='add-parameter']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='parameter_name']"))).sendKeys("user.lastName");
//		driver.findElement(By.xpath("//label[@for='parameter_saml_parameter']")).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='- No default -']"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Last Name']"))).click();
//		driver.findElement(By.xpath("//div[text()='Save']")).click();
//		
//		driver.findElement(By.xpath("//a[@href='#sso']")).click();
//		driver.findElement(By.xpath("//span[text()='SHA-1']")).click();
//		driver.findElement(By.xpath("//div[text()='SHA-256']")).click();
//		
//		driver.findElement(By.xpath("//input[@value='Save']")).click();
//		
////		try {
////		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='flash-close icon-close-16-white']"))).click();
////		driver.findElement(By.xpath("//div[@class='flash-close icon-close-16-white']")).click();
////		}catch(TimeoutException e) {
////			e.printStackTrace();
////		}
//		
//		Thread.sleep(10000);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='more-actions']"))).click();
//		
//		new Actions(driver).moveToElement(driver.findElement(By.xpath("//a[text()='SAML Metadata']"))).click().perform();
//		
//		String app_url = driver.getCurrentUrl();
//		String[] url_parts = app_url.split("/");
//		String app_id = url_parts[4];
//		System.out.println(app_id);
//		String metadataFile_path = "C:\\Downloads\\onelogin_metadata_"+app_id+".xml";
//		System.out.println(metadataFile_path);
		
		String metadataFile_path = "C:\\Downloads\\onelogin_metadata_4063527.xml";
		//driver.switchTo().window(df_tab);
		driver.findElement(By.xpath("//a[text()='Next']")).click();
		
		if(driver.findElement(By.xpath("//input[@id='rdoUploadIdP']")).isSelected()==false)
		{
			//driver.findElement(By.xpath("//label[@for='rdoUploadIdP']")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='rdoUploadIdP']"))).click();
		}
		driver.findElement(By.xpath("//input[@id='btnIPMetadata']")).click();
	
		StringSelection selection = new StringSelection(metadataFile_path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		
		Thread.sleep(5000);
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		driver.findElement(By.xpath("//a[text()='Next']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='accessAllSitesId']"))).click();
		driver.findElement(By.xpath("//input[@value='Save']")).click();
		
		String domainName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtLoginDomain']"))).getAttribute("value");
		System.out.println(domainName);
		
		driver.findElement(By.xpath("//a[@id='logg_main']")).click();
		driver.findElement(By.xpath("//a[@id='aSignOut']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='spnLoginWithCompCredsText']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='chkLogUsingSAMLCreds']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='txtSAMP']"))).sendKeys(domainName);
		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
		
		
	}
	
}

class onelogin{
	
	public void onelogin_signup(WebDriver driver, WebDriverWait wait) throws HeadlessException, UnsupportedFlavorException, IOException, InterruptedException{
		
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
		System.out.println(temp_mail.replace("@chitthi.in", ""));
		
		String domain_name=temp_mail.replace("@chitthi.in", "");
		
		//Create OneLogin Account
		driver.switchTo().window(onelogin_tab);
		driver.findElement(By.xpath("//a[@class='btn-v3 btn-default-color btn-v3-small']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Email']"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Email']"))).sendKeys(temp_mail);
		
		boolean ferror;
		try {
		WebElement derror = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']")));
		ferror = derror.isDisplayed();
		}
		catch(TimeoutException e)
		{
			ferror = false;
		}
		
		System.out.println("FError: "+ ferror);
		
		if(ferror==true) {
		
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']"))).sendKeys(domain_name);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='chkLicense']"))).click();
			Thread.sleep(10000);
			//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Get Started']"))).click();
		}
		else
		{
			//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']"))).sendKeys(temp_mail.replace("@chitthi.in",""));
			//driver.findElement(By.xpath("//div[@class='trial-additional-note']"));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='chkLicense']"))).click();
			Thread.sleep(10000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Get Started']"))).click();
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
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='BrandSubDomain']"))).sendKeys(domain_name);
			driver.findElement(By.xpath("//div[@class='trial-additional-note']"));
		}
		
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
		
		//Switch Back to Temp Mail to Verify one login account.
		driver.switchTo().window(temp_tab);
		Thread.sleep(30000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='noreply@onelogin.com']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Click here to activate your account and set up your password.']"))).click();
		
		Set<String> newTab =  driver.getWindowHandles();
		newTab.removeAll(allTabs);
		String password_set_tab = newTab.iterator().next();
		driver.switchTo().window(password_set_tab);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Continue']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password']"))).sendKeys(ConfigReader.get("onelogin_password"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='password_confirmation']"))).sendKeys(ConfigReader.get("onelogin_password"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='login']"))).click();
		System.out.println("Password Set Successfully");
		//driver.close();	
		System.out.println("OneLogin Password window Closed");
		
		String onelogin_userName = temp_mail;
		String onelogin_password = ConfigReader.get("onelogin_password");
		
		CredentialManager.saveCredentials(onelogin_userName, onelogin_password);
		
		String oneLoginProfile_url = "https://"+ domain_name +".onelogin.com";
		
		Set<String> updatedTab = driver.getWindowHandles();
		updatedTab.removeAll(allTabs);
		String oneLoginProfile_tab= updatedTab.iterator().next();
		driver.switchTo().window(oneLoginProfile_tab);
		
		driver.switchTo().window(onelogin_tab);
		driver.close();
		
		driver.switchTo().window(temp_tab);
		driver.close();
		
		driver.switchTo().window(oneLoginProfile_tab);
		
		
	}
	
}
