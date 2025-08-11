package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFilePath;

    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportsDir = System.getProperty("user.dir") + File.separator + "reports";
            new File(reportsDir).mkdirs();

            reportFilePath = reportsDir + File.separator + "ExtentReport_" + timeStamp + ".html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportFilePath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("UI Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "HARSHAD BHANGALE");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    // Listener will call this to open the HTML after flush
    public static String getReportFilePath() {
        return reportFilePath;
    }
}
