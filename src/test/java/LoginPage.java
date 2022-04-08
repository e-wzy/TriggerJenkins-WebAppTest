import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.openqa.selenium.*;

//for xlsx
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

//for xls
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


import java.io.*;
import java.time.Duration;

import static org.testng.Assert.assertTrue;
//import static org.testng.Assert.assertEquals;

import static org.testng.Assert.*;


public class LoginPage {

    //Data member
     private String  test_data_path;
     private WebDriver driver;
     private int timeout=10;

    //Locators
    String user_name_id = "username";
    String password_id = "password";
    String page_title_id = "kc-page-title";
    String sign_in_id = "kc-login";
    String login_error_css ="span#input-error";

    //Test data
    String user_name = "";
    String password = "";

    //Constructor
    LoginPage(WebDriver web_driver, String userName, String pwd)
    {
        driver = web_driver;
        user_name = userName;
        password = pwd;
    }

    //method
    //Check if the current page is login page.
    public void assertLoginPage(String result_file)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

        //Check if login page title appears
        WebElement page_title_element = wait.until((ExpectedConditions.visibilityOfElementLocated(By.id(page_title_id))));
        String page_title = page_title_element.getText();
        assertEquals(page_title, "Sign in to your account");

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if ( ret ) {
            System.out.println("Login page screenshot created.");
            Reporter.log("Login page screenshot created.");
        }
        else
        {
            System.out.println("Login page screenshot not created.");
            Reporter.log("Login page screenshot not created.");
        }
    }

    //Check login is failed.
    public void assertFailedLogin( String result_file)
    {
        //Check if login error appears
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement login_error_element = wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector(login_error_css))));
        String actual_err_msg = login_error_element.getText();
        assertEquals(actual_err_msg, "Invalid username or password.");

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if (ret) {
            System.out.println("Failed login screenshot created.");
            Reporter.log("Failed login screenshot created.");
        }
        else
        {
            System.out.println("Failed login screenshot not created.");
            Reporter.log("Failed login screenshot not created.");
        }
    }

    //Submit login form.
    public void submitForm() throws IOException {

        driver.findElement(By.id(user_name_id)).sendKeys(user_name);
        driver.findElement(By.id(password_id)).sendKeys(password);

       // driver.findElement(By.cssSelector(submit_class_css)).click();
        driver.findElement(By.id(sign_in_id)).click();

        Reporter.log("The login form submitted.");
    }

}