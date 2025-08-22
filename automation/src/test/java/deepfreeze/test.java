//package deepfreeze;
//
//import java.io.IOException;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.testng.annotations.Test;
//
//import utils.ConfigReader;
//
//public class test extends BaseClass{
//
//	@Test
//	public void testMain()
//	{
//		driver.get(ConfigReader.get("url"));
//		driver.findElement(By.xpath("//input[@id='txtUserName']")).sendKeys(ConfigReader.get("username"));
//		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(ConfigReader.get("password"));
//		driver.findElement(By.xpath("//input[@id='btnlogin']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='logg_main']"))).click();
//		driver.findElement(By.xpath("//a[@id='aLogin_User_Management']")).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='btnSAMLIntegration']"))).click();
//		
//		driver.findElement(By.xpath("//a[text()='Next']")).click();
//		
//		if(driver.findElement(By.xpath("//input[@id='rdoUploadIdP']")).isSelected()==false)
//		{
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='rdoUploadIdP']"))).click();
//		}
//		
//		driver.findElement(By.xpath("//input[@id='btnIPMetadata']")).click();
//		
//		System.out.println("Uploading File Through Auto IT");
//		
//		String metadataFile_path = "C:\\Downloads\\onelogin_metadata_4083721.xml";
//		
//		String autoItScript = "C:\\Downloads\\fileUpload.exe";
//		
//		try {
//			Runtime.getRuntime().exec(new String[] {autoItScript, metadataFile_path});
//			System.out.println("Uploaded File Through Auto IT");
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//	
//		System.out.println("Upload Done File Through Auto IT");
//		
//		
//	}
//	
//	
//}
