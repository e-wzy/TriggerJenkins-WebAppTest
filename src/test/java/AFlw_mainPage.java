import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

//This class is for airflow login page.
public class AFlw_mainPage {

    //Data member
     private String  test_data_path;
     private WebDriver driver;
     private int timeout=10;
     private boolean isLogin;

    //Locators
    String sign_in_keyc_css = "a.btn.btn-primary.btn-block";
    String user_name_css = "input#username";
    String password_css = "input#password";
    String sign_in_css =  "input#kc-login";
    String login_error_css ="span#input-error";

    //Test data
    String user_name = "";
    String password = "";

    //Constructor
    AFlw_mainPage(WebDriver web_driver, String userName, String pwd)
    {
        driver = web_driver;
        user_name = userName;
        password = pwd;
        isLogin=false;
    }

    //method
    //Submit login form.
    public void submitForm() throws IOException {

        WebElement sign_in_ele = Utility.getWebElement(driver, sign_in_keyc_css);
        sign_in_ele.click();

        WebElement ele = Utility.getWebElement(driver, user_name_css);
        ele.sendKeys(user_name);

        ele = Utility.getWebElement(driver, password_css);
        ele.sendKeys(password);

        ele = Utility.getWebElement(driver, sign_in_css);
        ele.click();

        Reporter.log("The login form submitted.");
    }


    //Check if the current page is airflow main page.
    public void assertLogin(String result_file)
    {
        //Check if main page title appears
        WebElement page_title_element = Utility.getWebElementByTag(driver,"h2");

        String page_title = page_title_element.getText();
        assertEquals(page_title, "DAGs");
        Reporter.log("The login succeeded.");
        isLogin = true;

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if ( ret ) {
            System.out.println("The Airflow main page screenshot created.");
            Reporter.log("The Airflow main page screenshot created.");
        }
        else
        {
            System.out.println("The Airflow main page screenshot not created.");
            Reporter.log("The Airflow main page screenshot not created.");
        }
    }

    // Logout from the main page
    public void Logout()
    {
        if (isLogin)
        {
            String acc_name_css = "span.navbar-user-icon";
            WebElement ele = Utility.getWebElement(driver, acc_name_css);
            ele.click();

            String logout_css = "a[href='/logout/']";
            ele = Utility.getWebElement(driver, logout_css);
            ele.click();

            isLogin=false;
        }
    }

    //Check if the current page is login page.
    public void assertLogout(String result_file)
    {
        //Check if login page title appears
        String sign_in_css = "div.panel-title";
        WebElement sign_in_ele = Utility.getWebElement(driver, sign_in_css);

        String sign_in_title = sign_in_ele.getText();
        assertEquals(sign_in_title, "Sign In");
        Reporter.log("The logout succeeded.");

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if ( ret ) {
            System.out.println("The Airflow login page screenshot created.");
            Reporter.log("The Airflow login page screenshot created.");
        }
        else
        {
            System.out.println("The Airflow login page screenshot not created.");
            Reporter.log("The Airflow login page screenshot not created.");
        }
    }

    //Check login is failed.
    public void assertFailedLogin( String result_file)
    {
        //Check if login error appears
        WebElement login_error_element = Utility.getWebElement(driver,login_error_css );
        String actual_err_msg = login_error_element.getText();
        assertEquals(actual_err_msg, "Invalid username or password.");
        Reporter.log("'Invalid username or password.' displayed.");
        Reporter.log("The login is unsuccessful.");

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

}