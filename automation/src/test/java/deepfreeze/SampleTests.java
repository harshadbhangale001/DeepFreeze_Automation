package deepfreeze;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SampleTests {

    @DataProvider(name = "serverProvider")
    public Object[][] getServers() {
        return new Object[][] {
            {"www1"},
            {"www2"},
            {"www3"},
            {"www5"}
        };
    }

    @Test(dataProvider = "serverProvider")
    public void loginTest(String server) {
        System.out.println("Executing loginTest on server: " + server);
        Assert.assertTrue(true, "Login should pass on " + server);
    }

    @Test(dataProvider = "serverProvider")
    public void createPolicyTest(String server) {
        System.out.println("Executing createPolicyTest on server: " + server);
        Assert.assertTrue(true, "Create Policy should pass on " + server);
    }

    @Test(dataProvider = "serverProvider")
    public void downloadBootstrapperTest(String server) {
        System.out.println("Executing downloadBootstrapperTest on server: " + server);
        Assert.assertTrue(true, "Download Bootstrapper should pass on " + server);
    }

    @Test(dataProvider = "serverProvider")
    public void installCloudAgentAndServicesTest(String server) {
        System.out.println("Executing installCloudAgentAndServicesTest on server: " + server);
        Assert.assertTrue(true, "Install should pass on " + server);
    }

    @Test(dataProvider = "serverProvider")
    public void cloudAgentStatusReportedTest(String server) {
        System.out.println("Executing cloudAgentStatusReportedTest on server: " + server);
        if (server.equals("www2")) {
            Assert.assertTrue(false, "Cloud Agent status test is purposely failed on " + server);
        } else {
            Assert.assertTrue(true, "Cloud Agent status test passed on " + server);
        }
    }

    @Test(dataProvider = "serverProvider")
    public void serviceSpecificActionExecutionTest(String server) {
        System.out.println("Executing serviceSpecificActionExecutionTest on server: " + server);
        if (server.equals("www2")) {
            Assert.assertTrue(false, "Service action test is purposely failed on " + server);
        } else {
            Assert.assertTrue(true, "Service action should pass on " + server);
        }
    }

    @Test(dataProvider = "serverProvider")
    public void installRandomSUAppTest(String server) {
        System.out.println("Executing installRandomSUAppTest on server: " + server);
        if (server.equals("www3")) {
            throw new SkipException("Skipping this test on " + server);
        } else {
            Assert.assertTrue(true, "App installation test passed on " + server);
        }
    }
}