package deepfreeze;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class load_time_check extends BaseClass{

	@Test(priority = 1)
	public void DF_Dashboard_measureTimes() {
	    // Page load time
		long pageLoadTime;
		
		new Actions(driver).moveToElement(driver.findElement(By.xpath("//a[@id='layoutdashboard1']"))).click().perform();
		driver.findElement(By.xpath("//span[text()='DEEP FREEZE DASHBOARD']")).click();
	    pageLoadTime = getPageLoadTime(driver);
	    System.out.println("Page Load Time: " + pageLoadTime + " Sec");

	    // Table load time (example locator)
	    By tableLocator = By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed']");
	    System.out.println("Table Locator");
	    try {
	    long tableLoadTime = getTableLoadTime(driver, tableLocator, wait);
	    System.out.println("Table Load Time: " + tableLoadTime + " Sec");
	    }catch(NoSuchElementException e)
	    {
	    	System.out.println("Data is not present in the table");
	    }
	    
	    
	    driver.findElement(By.xpath("//a[text()='Workstation Status']")).click();
	    pageLoadTime = getPageLoadTime(driver);
	    System.out.println("Page Load Time: " + pageLoadTime + " Sec");
	    
	}

	
	public long getPageLoadTime(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");
	    long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");
	    long loadTimeInMs = loadEventEnd - navigationStart; // MS
	    return (long) (loadTimeInMs/1000.0);
	}

	public long getTableLoadTime(WebDriver driver, By tableLocator, WebDriverWait wait) {
	    long startTime = System.currentTimeMillis();

	    // Wait until table is visible
	    wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));

	    long endTime = System.currentTimeMillis();
	    long loadTimeInMs = endTime - startTime; // in ms
	    
	    return (long) (loadTimeInMs/1000.0);
	}
	
}


