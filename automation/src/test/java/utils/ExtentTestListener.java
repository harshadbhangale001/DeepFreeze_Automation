package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentTestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static WebDriver driver; // Set from your tests: ExtentTestListener.driver = driver;

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        if (driver != null) {
            String screenshotPath = takeScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null) {
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // write the report
        extent.flush();

        // open the report in default browser
        String reportPath = ExtentManager.getReportFilePath();
        if (reportPath == null) {
            System.err.println("Report path is null — ensure ExtentManager.getInstance() was called.");
            return;
        }

        File htmlFile = new File(reportPath);
        if (!htmlFile.exists()) {
            System.err.println("Report file not found at: " + reportPath);
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                // fallback for environments without Desktop support
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("mac")) {
                    Runtime.getRuntime().exec(new String[] { "open", htmlFile.getAbsolutePath() });
                } else if (os.contains("win")) {
                    // the empty title "" avoids issues with paths containing spaces
                    Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start", "\"\"", htmlFile.getAbsolutePath() });
                } else {
                    Runtime.getRuntime().exec(new String[] { "xdg-open", htmlFile.getAbsolutePath() });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String takeScreenshot(String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String destDir = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "screenshots";
            new File(destDir).mkdirs();
            String filePath = destDir + File.separator + testName + "_" + timeStamp + ".png";
            Files.copy(srcFile.toPath(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
