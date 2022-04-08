import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;

public class TestMLFlow {

    private WebDriver driver;
    String exp_filename;
    @BeforeClass
    public void beforeClassTest()
    {
        // Experiment file name cannot be duplicated even if it is deleted.
        // currentTimeMillis(): current time in format of millisecond
        exp_filename = "Test" + System.currentTimeMillis();  // 14 length is allowed.
       /* System.setProperty("webDriver.gecko.driver", Configuration.pathToFireFoxDriver);
        driver = new FirefoxDriver();

        driver.get(Configuration.url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @BeforeMethod
    public void beforeTest()
    {
        System.setProperty("webDriver.gecko.driver", Configuration.pathToFireFoxDriver);
        driver = new FirefoxDriver();

        driver.get(Configuration.mlflow_url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    //This test is to test successful creation of experiment.
    public void Test_1() throws IOException
    {
        Reporter.log("Test 1 - Test successful creation of experiment.");

        MLFlowPage  mlflow_Page =  new MLFlowPage(driver);
        mlflow_Page.createExperiment_success(exp_filename);

        String test_case_id = "CreateExperiment-TC001";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp()+test_case_id + "_Success.jpg";
        mlflow_Page.assertExperimentDisplayed(exp_filename, result_file);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    //This test is to test creation of duplicated experiment is not allowed.
    //Execute this test after Test_1 - successful creation of experiment
    public void Test_2() throws IOException
    {
        Reporter.log("Test 2 - Test creation of duplicated experiment is not allowed.");

        MLFlowPage  mlflow_Page =  new MLFlowPage(driver);
        String test_case_id = "CreateExperiment-TC002";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp()+test_case_id + "_Fail.jpg";

        mlflow_Page.createDuplicatedExperiment(exp_filename, result_file);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    // This test is to test rename an experiment which is created by "test 1".
    public void Test_3() throws IOException
    {
        Reporter.log("Test 3 - Test Rename an experiment.");

        MLFlowPage  mlflow_Page =  new MLFlowPage(driver);
        String new_name = exp_filename + "-NewName";
        mlflow_Page.renameExperiment(exp_filename, new_name);

        String test_case_id = "RenameExperiment-TC003";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp()+test_case_id + ".jpg";

        exp_filename += "-NewName";
        mlflow_Page.assertExperimentDisplayed(exp_filename, result_file);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    // This test is to test delete an experiment which is created by "test 1"
    public void Test_4() throws IOException
    {
        Reporter.log("Test 4 - Test Delete an experiment.");

        MLFlowPage  mlflow_Page =  new MLFlowPage(driver);

        mlflow_Page.deleteExperiment(exp_filename);

        String test_case_id = "DeleteExperiment-TC004";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp()+test_case_id + ".jpg";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mlflow_Page.assertExperimentDeleted(exp_filename, result_file);

    }//Test_4

    @AfterMethod
    //@AfterClass
     public void teardown(){
        //driver.close();     //close current window
        driver.quit();    //close all windows
        System.out.println("Last method - Tear down. ");
    }
}