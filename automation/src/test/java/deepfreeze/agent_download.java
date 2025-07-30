package deepfreeze;

import java.io.File;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


public class agent_download extends df_login {
	
	//TO Install Bootstrapper
	
	public static agentDownloadCheck adc=new agentDownloadCheck();
	public static agent_download ad=new agent_download();
	@Test(priority=1)
	public void installCloudAgent()
	{
		driver.findElement(By.xpath("//li[@id='homeimg1']")).click();
		driver.findElement(By.xpath("//a[@href='/en/Home/InstallServices']")).click();
	}
	@Test(priority=2)
	public void bootstrapper_download() throws InterruptedException
	{
			
		//System.out.println("In bootstrapper Test Driver is: " + driver);	
		driver.findElement(By.xpath("//label[@for='RDOInst']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(2, "FWAWebInstaller_");
		System.out.println("Result: "+ result);
		Thread.sleep(1*60000);//1min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);		
	}
	
	
	@Test(priority=3)
	public void msi_download() throws InterruptedException
	{
		//System.out.println("In MSI Test Driver is: " + driver);
		driver.findElement(By.xpath("//label[@for='RDODwlMSIInstaller']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(8, "WebAgent_");
		System.out.println("Result: "+ result);
		Thread.sleep(2*60000);//2min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);	
	}
	
	@Test(priority=4)
	public void deploymentment_utility_download() throws InterruptedException
	{
		driver.findElement(By.xpath("//label[@for='RDODwlPush']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(0, "DeploymentUtility_");
		System.out.println("Result: "+ result);
		Thread.sleep(1*60000);//1min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);	
	}
	
	@Test(priority=5)
	public void full_installer_download() throws InterruptedException
	{
		driver.findElement(By.xpath("//label[@for='RDOFullInst']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(150, "FullFWAWebInstaller_");
		System.out.println("Result: "+ result);
		Thread.sleep(10*60000);//10min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);	
	}
	
	@Test(priority=6)
	public void server_installer_download() throws InterruptedException
	{
		driver.findElement(By.xpath("//label[@for='rdWindowsServer']")).click();
		driver.findElement(By.xpath("//label[@for='RDOInst']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(2, "FWAWebInstaller_");
		System.out.println("Result: "+ result);
		Thread.sleep(1*60000);//1min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);	
	}
	
	@Test(priority=7)
	public void mac_installer_download() throws InterruptedException
	{
		driver.findElement(By.xpath("//label[@for='rdMac']")).click();
		driver.findElement(By.xpath("//label[@for='RDOInst']")).click();
		driver.findElement(By.xpath("//input[@id='Install']")).click();
		int result= adc.downloadCheck(2, "Installer");
		System.out.println("Result: "+ result);
		Thread.sleep(1*60000);//1min
		if(result==1)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);	
	}
}
class agentDownloadCheck{
	
	public int downloadCheck(int fileSize, String fileName)
	{
		
		
		String folderPath="C:\\Users\\admin\\Downloads";
		
		//public static String fileName=null;
		
		File folder=new File(folderPath);
		File[] files=folder.listFiles();
		
		boolean fileFound=false;
		//int downloadVerify = 0;
		
		if(files!=null)
		{
			for(File file: files)
			{
				if(file.getName().startsWith(fileName))
				{
					fileFound=true;
					
					if((file.length()/(1024*1024)) >= fileSize ) {
						System.out.println("Cloud Agent Downloaded Correctly");
						return 1;
					}
					else {
						System.out.println("Cloud Agent Not Downloaded Correctly");
						return 2;			
					}
					//break;
				}
			}
		}
		if(!fileFound) {
			System.out.println("Cloud Agent Not Downloaded");
			return 3;
		}
		System.out.println("bootstrapper");
		return 0;
	}
	
}
