import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestDAGFiles
{
    private WebDriver driver;

    @BeforeMethod
    public void beforeTest() {
       /*  -- chrome cannot work with Jenkins
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);*/

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
    //This test is to test successfully Uploading DAG file
    public void Test_1() throws IOException
    {
        Reporter.log("Test 1 - Test successfully Uploading DAG file.");

        LoginPage  loginPage = new LoginPage(driver, "zhiyan.wang", "MS4H5r_w");
        loginPage.submitForm();

        MainPage  mainPage =  new MainPage(driver);
        String dag_file = Configuration.testDataPath+"test-DAG-file-1.py";
        String test_case_id = "UploadDAG-TC001";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp() +test_case_id + "_SuccessUploadPage.jpg";

        mainPage.uploadDAGFile_success(dag_file,result_file);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    //This test is to test unsuccessfully Uploading DAG file
    public void Test_2() throws IOException
    {
        Reporter.log("Test 2 - Test unsuccessfully Uploading DAG file.");
        LoginPage  loginPage = new LoginPage(driver, "zhiyan.wang", "MS4H5r_w");
        loginPage.submitForm();

        MainPage  mainPage =  new MainPage(driver);
        String dag_file = Configuration.testDataPath+"test-DAG-file-1";
        String test_case_id = "UploadDAG-TC002";
        String result_file = Configuration.testRsltPath + Utility.getTimeStamp()+test_case_id + "_FailedUploadPage.jpg";

        mainPage.uploadDAGFile_failed(dag_file, result_file);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void teardown(){
        //driver.close();     //close current window
        driver.quit();    //close all windows
        System.out.println("Last method - Tear down. ");
    }
}
