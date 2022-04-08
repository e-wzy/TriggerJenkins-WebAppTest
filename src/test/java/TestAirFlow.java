import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestAirFlow {

    private WebDriver driver;
    String exp_filename;
    @BeforeClass
    public void beforeClassTest()
    {

    }

    @BeforeMethod
    public void beforeTest()
    {
        System.setProperty("webDriver.gecko.driver", Configuration.pathToFireFoxDriver);
        driver = new FirefoxDriver();

        driver.get(Configuration.airflow_url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //This test is to test successful login and logout
    @Test
    public void Test_1() throws IOException
    {
        Reporter.log("Test 1 - Test successful login and logout.");
        String test_case_id = "airflowLogin-TC001";

        //Submit login
        AFlw_mainPage mainPage = new AFlw_mainPage(driver, "zhiyan.wang", "MS4H5r_w");
        mainPage.submitForm();

        //Assert login result
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_SuccessLoginPage.jpg";
        mainPage.assertLogin(result_file);

        //The code below is not necessary, just wait a while to observe running result.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Verify logout
        mainPage.Logout();
        //Assert successful logout
        result_file =  Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_SuccessLogoutPage.jpg";
        mainPage.assertLogout(result_file);
        //The code below is not necessary, just wait a while to observe running result.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }//Test_1

    //This test is to test failed login.
    @Test
    public void Test_2() throws IOException {
        Reporter.log("Test 2 - Test unsuccessful login.");
        String test_case_id = "airflowLogin-TC002";

        //Submit login
        AFlw_mainPage mainPage = new AFlw_mainPage(driver, "zhiyan.wang", "MS4H5r");
        mainPage.submitForm();

        //Assert failed login result
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_FailedLoginPage.jpg";
        mainPage.assertFailedLogin(result_file);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



     @AfterMethod
    //@AfterClass
     public void teardown(){
        //driver.close();     //close current window
        driver.quit();    //close all windows
        System.out.println("Last method - Tear down. ");
    }
}