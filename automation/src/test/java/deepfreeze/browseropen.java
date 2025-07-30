package deepfreeze;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class browseropen {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		
		//For Login
		driver.manage().window().maximize();
		driver.get("https://www1.faronicsbeta.com/");
		driver.findElement(By.id("txtUserName")).sendKeys("harshad.bhangale317@gmail.com");
		driver.findElement(By.id("btnlogin")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("txtPassword")).sendKeys("Aloha@123");
		driver.findElement(By.id("btnlogin")).click();
		
		//To go to the Install Cloud Agent Page
		driver.findElement(By.className("imgTextHome")).click();
		
		//To Download Cloud Agent Bootstrapper
		//driver.findElement(By.id("Install")).click();
		
		//To Download Cloud Agent MSI
		driver.findElement(By.xpath("//*[@id=\"dvDownloadMSIInstaller\"]/span")).click();
		driver.findElement(By.id("Install")).click();
		
		//To check Cloud Agent is Downloaded or Not
		Thread.sleep(60000);
		fileCheck(8);
		driver.quit();
	}
	
	public static void fileCheck(float fileSize) 
	{
	String folderPath="C:\\Users\\admin\\Downloads";
	String fileName="FWA";
	
	File folder=new File(folderPath);
	File[] files=folder.listFiles();
	
	boolean fileFound=false;
	
	if(files!=null)
	{
		for(File file: files)
		{
			if(file.getName().startsWith(fileName))
			{
				fileFound=true;
				
				if((file.length()/(1024*1024)) >= fileSize )
					System.out.println("Cloud Agent Downloaded Correctly");
				else
					System.out.println("Cloud Agent Not Downloaded Correctly");
				break;
			}
		}
	}
	if(!fileFound)
		System.out.println("Cloud Agent Not Downloaded");
	}

}
