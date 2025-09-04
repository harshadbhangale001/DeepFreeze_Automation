package deepfreeze;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class load_time_check extends BaseClass{

	@Test(priority = 1)
	public void DF_Dashboard_measureTimes() {
	    new Actions(driver).moveToElement(driver.findElement(By.id("layoutdashboard1"))).click().perform();
	    driver.findElement(By.xpath("//span[text()='DEEP FREEZE DASHBOARD']")).click();

	    long pageLoadTime = getPageLoadTime(driver);
	    System.out.println("DeepFreeze Dashboard Page Load Time: " + pageLoadTime + " Sec");

	    try {
	        long tableLoadTime = getTableLoadTime(driver,
	                By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed']//tr"),
	                wait);
	        System.out.println("Dashboard Table Load Time: " + tableLoadTime + " Sec");
	    } catch (Exception e) {
	        System.out.println("Dashboard Table not found or no rows loaded.");
	    }

	    // --- Workstation Tab ---
	    driver.findElement(By.xpath("//a[text()='Workstation Status']")).click();
	    pageLoadTime = getPageLoadTime(driver);
	    System.out.println("Workstation Tab Load Time: " + pageLoadTime + " Sec");

	    try {
	            long tableLoadTime = getTableLoadTime(driver,
	                    By.xpath("//table[@id='grdDFWorkstaionStatus_DXMainTable']"),
	                    wait);
	            System.out.println("Workstation Tab Table Load Time: " + tableLoadTime + " Sec");
	        
	    } catch (Exception e) {
	        System.out.println("Workstation Tab Table not found or no rows loaded.");
	    }

	    // --- Workstation Task Summary ---
	    driver.findElement(By.xpath("//a[text()='Workstation Task Summary']")).click();
	    pageLoadTime = getPageLoadTime(driver);
	    System.out.println("Workstation Task Summary Tab Load Time: " + pageLoadTime + " Sec");
	    
	    try {
            long tableLoadTime = getTableLoadTime(driver,
                    By.xpath("//table[@id='grdDFWorkstaionTaskSummary']"),
                    wait);
            System.out.println("Workstation Task Summary Tab Table Load Time: " + tableLoadTime + " Sec");
        
		    } catch (Exception e) {
		        System.out.println("Workstation Task Summary Tab Table not found or no rows loaded.");
		    }
	}
	
	@Test(priority = 2)
	public void AE_Dashboard_measureTimes()
	{
		 new Actions(driver).moveToElement(driver.findElement(By.xpath("//a[@id='layoutdashboard']"))).click().perform();
		 new Actions(driver).moveToElement(driver.findElement(By.xpath("//a[@id='layoutdashboard1']"))).click().perform();
		 driver.findElement(By.xpath("//span[text()='ANTI-EXECUTABLE DASHBOARD']")).click();
		 long pageLoadTime = getPageLoadTime(driver);
		 
		 System.out.println("AE Dashboard Page Load Time: " + pageLoadTime + " Sec");
		 
		 if(driver.findElement(By.xpath("//div[@class='ae-empty-dashboard-black-inner']")).isDisplayed() == true)
			 Assert.fail("AE is not installed.");
		 
		 try {
			 long tableLoadTime = getTableLoadTime(driver,
					 By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed']//tr"),
					 wait);
			 System.out.println("Dashboard Table Load Time: " + tableLoadTime + " Sec");
		 } catch (Exception e) {
			 System.out.println("Dashboard Table not found or no rows loaded.");
		 }
	}

	public long getPageLoadTime(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Object loadTime = js.executeScript(
	        "if (performance.getEntriesByType('navigation').length > 0) {" +
	        "  return performance.getEntriesByType('navigation')[0].loadEventEnd -" +
	        "         performance.getEntriesByType('navigation')[0].startTime;" +
	        "} else {" +
	        "  return performance.timing.loadEventEnd - performance.timing.navigationStart;" +
	        "}"
	    );
	    return ((Number) loadTime).longValue() / 1000; // ms → sec
	}

	public long getTableLoadTime(WebDriver driver, By rowLocator, WebDriverWait wait) {
	    long startTime = System.currentTimeMillis();

	    // Wait for at least 1 row to be present
	    wait.until(ExpectedConditions.presenceOfElementLocated(rowLocator));

	    return (System.currentTimeMillis() - startTime) / 1000; // ms → sec
	}

	
}


