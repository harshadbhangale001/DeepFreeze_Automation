package utils;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CustomReportListener implements IReporter {

    private static final String TEMPLATE_FILE = "report-template.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        try {
            String template = getTemplate();
            int passedTests = 0;
            int failedTests = 0;
            int skippedTests = 0;
            
            // Get the suite and test names from TestNG
            String suiteName = suites.get(0).getName();
            String testName = "";

            Map<String, Map<String, ITestResult>> testResultsByMethod = new LinkedHashMap<>();
            Set<String> allServers = new LinkedHashSet<>();

            for (ISuite suite : suites) {
                Map<String, ISuiteResult> suiteResults = suite.getResults();
                for (ISuiteResult sr : suiteResults.values()) {
                    ITestContext tc = sr.getTestContext();
                    testName = tc.getName(); // Get the test name
                    
                    passedTests += tc.getPassedTests().size();
                    failedTests += tc.getFailedTests().size();
                    skippedTests += tc.getSkippedTests().size();

                    Set<ITestResult> allResults = new HashSet<>();
                    allResults.addAll(tc.getPassedTests().getAllResults());
                    allResults.addAll(tc.getFailedTests().getAllResults());
                    allResults.addAll(tc.getSkippedTests().getAllResults());
                    
                    for (ITestResult result : allResults) {
                        String methodName = result.getMethod().getMethodName();
                        String serverName = "N/A";
                        if (result.getParameters() != null && result.getParameters().length > 0) {
                            serverName = result.getParameters()[0].toString();
                            allServers.add(serverName); 
                        }
                        
                        testResultsByMethod.computeIfAbsent(methodName, k -> new LinkedHashMap<>()).put(serverName, result);
                    }
                }
            }

            StringBuilder serverHeaders = new StringBuilder();
            for (String server : allServers) {
                serverHeaders.append("<th>").append(server).append("</th>");
            }
            
            StringBuilder testResultsRows = new StringBuilder();
            List<String> sortedServers = new ArrayList<>(allServers);
            
            for (Map.Entry<String, Map<String, ITestResult>> entry : testResultsByMethod.entrySet()) {
                String methodName = entry.getKey();
                Map<String, ITestResult> serverResults = entry.getValue();

                testResultsRows.append("<tr>");
                testResultsRows.append("<td>").append(methodName).append("</td>");
                
                for (String server : sortedServers) {
                    ITestResult result = serverResults.get(server);
                    String statusClass = "status-skip";
                    String statusText = "Skip";
                    
                    if (result != null) {
                        switch (result.getStatus()) {
                            case ITestResult.SUCCESS:
                                statusClass = "status-pass";
                                statusText = "Pass";
                                break;
                            case ITestResult.FAILURE:
                                statusClass = "status-fail";
                                statusText = "Fail";
                                break;
                            case ITestResult.SKIP:
                                statusClass = "status-skip";
                                statusText = "Skip";
                                break;
                        }
                    }
                    testResultsRows.append("<td><span class=\"").append(statusClass).append("\">").append(statusText).append("</span></td>");
                }
                testResultsRows.append("</tr>");
            }

            int totalTests = passedTests + failedTests + skippedTests;
            double successRate = (totalTests > 0) ? ((double) passedTests / totalTests) * 100 : 0;
            DecimalFormat df = new DecimalFormat("0.00");
            
            String finalReport = template.replace("$SUITE_NAME", suiteName); // New replacement
            finalReport = finalReport.replace("$TEST_NAME", testName); // New replacement
            finalReport = finalReport.replace("$PASSED_COUNT", String.valueOf(passedTests));
            finalReport = finalReport.replace("$FAILED_COUNT", String.valueOf(failedTests));
            finalReport = finalReport.replace("$SKIPPED_COUNT", String.valueOf(skippedTests));
            finalReport = finalReport.replace("$TOTAL_COUNT", String.valueOf(totalTests));
            finalReport = finalReport.replace("$SUCCESS_RATE", df.format(successRate));
            finalReport = finalReport.replace("$DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            finalReport = finalReport.replace("$TIME", new SimpleDateFormat("HH:mm:ss").format(new Date()));
            finalReport = finalReport.replace("$SERVER_HEADERS", serverHeaders.toString()); 
            finalReport = finalReport.replace("$TEST_RESULTS_ROWS", testResultsRows.toString());

            saveReport(finalReport, outputDirectory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTemplate() throws IOException {
        Path templatePath = Paths.get("src", "test", "java", "utils", TEMPLATE_FILE);
        return new String(Files.readAllBytes(templatePath));
    }

    private void saveReport(String reportContent, String outputDirectory) throws IOException {
        File outputDir = new File(outputDirectory, "custom-reports");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String filename = "custom_report_" + timestamp + ".html";
        
        File reportFile = new File(outputDir, filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            writer.write(reportContent);
        }
    }
}