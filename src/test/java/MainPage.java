import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.Reporter;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class MainPage {
    //Data member
    private WebDriver driver;
    private boolean  isLogin=false;
    private int timeout=10;

    //Locators

     String darc_class_css= ".row.ml-0.mr-0";
     String account_menu_id = "account-menu";
     String logout_css = "svg.svg-inline--fa.fa-sign-out-alt.fa-w-16.fa-fw";   //tag+multiple classes.

     String ppl_managment_css= "#pipelineManagementNavBarDropdown";
     String dag_files_css = "[routerlink='/dagfiles']";
     String upload_dagfile_btn_css = "[style='float: left;'] button:nth-child(1)";
     String upload_ymlfile_btn_css = "[style='float: left;'] button:nth-child(2)";

     String choose_file_css = "div.col-md-3 input";
     String upload_all_css = "button.btn.btn-success.btn-s.mr-2";
     String upload_alert_success_css = "div.alert.alert-success";
     String back_css = "button[jhitranslate='go-back']";
     String pagination_css = "ul.pagination li:nth-child(5)";
     String alert_page_css = "div.swal-text";
     String alert_ok_css = "button.swal-button.swal-button--confirm";


    //Constructor
     MainPage(WebDriver Driver)
     {
         driver =Driver;
     }

    //method

    //For successful login
    public void assertMainPage(String result_file) {

       WebElement main_title = Utility.getWebElement(driver, darc_class_css);

       String titleText = main_title.getText();
         if (titleText.contains("DA Reusable Core")) {
             assertTrue(true);
             isLogin = true;
         }

         System.out.println("Main page displayed.");
         Reporter.log("Main page is shown up.");

        //Take screenshot
       boolean ret = Utility.saveScreenShot(driver, result_file);
        if (ret) {
            System.out.println("Main page screenshot created.");
            Reporter.log("Main page screenshot created.");
        }
        else
        {
            System.out.println("Main page screenshot not created.");
            Reporter.log("Main page screenshot not created.");
        }
     }

    // Logout from the main page
    public void Logout()
    {
        if (isLogin)
        {
          driver.findElement(By.id(account_menu_id)).click();

          WebElement element = driver.findElement(By.cssSelector(logout_css));
          element.click();

          isLogin=false;
        }
    }

    //Upload DAG file successfully
    public void  uploadDAGFile_success(String dag_file, String result_file)
    {
        WebElement webElement;

        webElement = Utility.getWebElement(driver, ppl_managment_css);
        webElement.click();

        webElement = Utility.getWebElement(driver, dag_files_css);
        webElement.click();

        webElement = Utility.getWebElement(driver, upload_dagfile_btn_css);
        webElement.click();

        //Set DAG file to upload
        webElement = Utility.getWebElement(driver, choose_file_css);
        webElement.sendKeys(dag_file);

        //Upload DAG file

        webElement = Utility.getWebElement(driver, upload_all_css);
        webElement.click();

        //Check if the uploading is successful
        webElement = Utility.getWebElement(driver, upload_alert_success_css);

        String actual_text = webElement.getText();
        String expected_text = "File is uploaded to Airflow Server.";
        assertEquals(expected_text, actual_text);

        //Take screenshot for successful uploading page.
        boolean  ret = Utility.saveScreenShot(driver, result_file);
        if ( ret ) {
            System.out.println("Successful Upload DAG File screenshot created.");
            Reporter.log("Successful Upload DAG File screenshot created.");
        }
        else
        {
            System.out.println("Successful Upload DAG File screenshot not created.");
            Reporter.log("Successful Upload DAG File screenshot not created.");
        }

        //Go back to last page to show if the uploading is recorded.
//        wait = new WebDriverWait(driver, timeout);
//        WebElement back_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(back_css)));
//        back_element.click();

        //Wait for pagination elements to appear.
        /* wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement pg_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pagination_css)));

        //Take screenshot for uploading history page
        String file_name = test_result_path + test_case_id + "_HistoryPage.jpg";
        Utility.saveScreenShot(driver, file_name);*/
    }
    //Upload DAG file unsuccessfully
    public void  uploadDAGFile_failed(String dag_file, String result_file)
    {
        WebElement  web_element  = Utility.getWebElement(driver,ppl_managment_css);
        web_element.click();

        web_element  = Utility.getWebElement(driver,dag_files_css);
        web_element.click();

        web_element  = Utility.getWebElement(driver, upload_dagfile_btn_css);
        web_element.click();

        //Set DAG file to upload
        web_element  = Utility.getWebElement(driver, choose_file_css);
        web_element.sendKeys(dag_file);

        //Wait for alert page to appear and check if the uploading is successful
        web_element  = Utility.getWebElement(driver, alert_page_css);

        String actual_text = web_element.getText();
        String expected_text = "This is not DAG file.";
        assertEquals(expected_text, actual_text);

        //Take screenshot for unsuccessful uploading page.
        boolean   ret = Utility.saveScreenShot(driver, result_file);
        if ( ret ) {
            System.out.println("Unsuccessful Upload DAG File screenshot created.");
            Reporter.log("Unsuccessful Upload DAG File screenshot created.");
        }
        else
        {
            System.out.println("Unsuccessful Upload DAG File screenshot cannot be taken.");
            Reporter.log("Unsuccessful Upload DAG File screenshot cannot be taken.");
        }

//        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
//        WebElement ok_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(alert_ok_css)));
//        ok_element.click();

        //Go back to last page to show if the failed uploading is recorded.
//        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
//        WebElement back_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(back_css)));
//        back_element.click();

        //Wait for pagination elements to appear.
        /*wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement pg_element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(pagination_css)));

        //Take screenshot for uploading history page
        String file_name = test_result_path + test_case_id + "_HistoryPage.jpg";
        Utility.saveScreenShot(driver, file_name);*/
    }
}
