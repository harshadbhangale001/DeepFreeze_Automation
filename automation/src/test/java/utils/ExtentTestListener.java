package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static WebDriver driver; // will be set in BaseClass

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportName = "ExtentReport_" + timeStamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/" + reportName);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Your Name");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("❌ Test Failed: " + result.getThrowable());

        if (driver != null) {
            try {
                // Add prefix so Extent can render it as thumbnail
                String base64Screenshot = "data:image/png;base64," +
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

                // ✅ Thumbnail at top
                test.get().addScreenCaptureFromBase64String(base64Screenshot, result.getMethod().getMethodName());

                // (Optional) Embedded screenshot in logs
                test.get().fail("Screenshot of failure:",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            } catch (Exception e) {
                test.get().fail("⚠ Failed to attach screenshot: " + e.getMessage());
            }
        }
    }




    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        // Auto-open the report
        try {
            File reportsDir = new File(System.getProperty("user.dir") + "/reports/");
            File[] files = reportsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".html"));

            if (files != null && files.length > 0) {
                // Get the latest report
                File latestReport = files[files.length - 1];
                Desktop.getDesktop().browse(latestReport.toURI());
                System.out.println("Opening report: " + latestReport.getAbsolutePath());
            } else {
                System.out.println("No HTML report found in " + reportsDir.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String takeScreenshot(String methodName) {
        if (driver == null) {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destination = System.getProperty("user.dir") + "/reports/screenshots/" + methodName + "_" + timeStamp + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/reports/screenshots/"));
            Files.copy(srcFile.toPath(), Paths.get(destination));
            return destination;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
