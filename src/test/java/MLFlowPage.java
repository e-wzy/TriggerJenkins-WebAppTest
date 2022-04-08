import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MLFlowPage {
    //Constructor
    MLFlowPage(WebDriver Driver)
    {
        driver =Driver;
    }

    //Data member
    private WebDriver driver;

    //Locator
      String plus_butt_css = "div.experiment-list-create-btn-container i";
      String input_experiment_name = "input#experimentName";
      String create_css = "button.ant-btn.ant-btn-primary";
      String parent_css = "div.experiment-list-container";
      String alert_css ="div.ant-form-item-explain.ant-form-item-explain-error div";


    //Method
    public void createExperiment_success(String exp_file)
    {
        WebElement plus_ele = Utility.getWebElement(driver, plus_butt_css);
        plus_ele.click();
        Reporter.log(" '+' clicked.");

        WebElement filename_ele = Utility.getWebElement(driver, input_experiment_name);
        filename_ele.sendKeys(exp_file);
        Reporter.log("File name entered");

        WebElement create_ele = Utility.getWebElement(driver, create_css);
        create_ele.click();
        Reporter.log("'Create' clicked.");

    }//createExperiment

    //To assert a file name is displayed on side menu.
    public void assertExperimentDisplayed( String exp_file, String result_file)
    {
        Reporter.log("To assert experiment file name is displayed.");
        //Locate parent element first
        WebElement  parent_ele = Utility.getWebElement(driver, parent_css);

        //Find element created or renamed.
        String fileName_css = "div[title='"+exp_file+"']";
        WebElement file_ele = Utility.getWebElement(driver, fileName_css);
        file_ele.click();   //Make this experiment show up.
        //Wait a while to save results.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Assert the found element
        String attr_value =  file_ele.getAttribute("title");
        assertEquals(exp_file, attr_value);
        String msg2show = "File name " + exp_file + " is displayed.";
        System.out.println(msg2show);
        Reporter.log(msg2show);

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if (ret) {
            System.out.println("MLFlow main page screenshot created.");
            Reporter.log("MLFlow main page screenshot created.");
        }
        else
        {
            System.out.println("MLFlow main page screenshot not created.");
            Reporter.log("MLFlow Main page screenshot not created.");
        }

    }//assertExperimentDisplayed

    public void createDuplicatedExperiment(String exp_file, String result_file)
    {
        WebElement plus_ele = Utility.getWebElement(driver, plus_butt_css);
        plus_ele.click();
        Reporter.log(" '+' clicked.");

        WebElement filename_ele = Utility.getWebElement(driver, input_experiment_name);
        filename_ele.sendKeys(exp_file);
        Reporter.log("File name entered");

        WebElement alert_ele = Utility.getWebElement(driver, alert_css);
        String actual_text = alert_ele.getText();
        String expected_text = "Please input a new name for the new experiment.";
        assertEquals(expected_text, actual_text);
        Reporter.log("Alert is displayed.");

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if (ret) {
            System.out.println("Alert screenshot created.");
            Reporter.log("Alert screenshot created.");
        }
        else
        {
            System.out.println("Alert screenshot not created.");
            Reporter.log("Alert screenshot not created.");
        }

        //Display alert for a while for showcasing.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Click Cancel to exit from create box
        String cancel_css = "div.ant-modal-footer button:nth-child(1)";
        WebElement  cancel_ele = Utility.getWebElement(driver, cancel_css);

        cancel_ele.click();

    }//createDuplicatedExperiment

    public void renameExperiment( String exp_file, String new_file) throws IOException
    {
        String ren_css ="div[title='"+exp_file+"'] button:nth-child(2)";
        WebElement ren_ele = Utility.getWebElement(driver, ren_css);
        ren_ele.click();
        Reporter.log(" 'Ren Icon' clicked.");

        String new_name_css = "input[value='"+exp_file+"']";
        WebElement new_name_ele = Utility.getWebElement(driver, new_name_css);
        new_name_ele.sendKeys(new_file);
        Reporter.log("New name entered.");

        //display rename box for a while for showcasing.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String save_css = "div.ant-modal-footer button:nth-child(2)"; //"button.ant-btn.ant-btn-primary";
        WebElement save_btn_ele = Utility.getWebElement(driver,save_css );
        save_btn_ele.click();
        Reporter.log("Save button clicked.");

    }

    public void deleteExperiment( String exp_file) throws IOException
    {
        String del_css ="div[title='"+exp_file+"'] button:nth-child(3)";
        WebElement del_ele = Utility.getWebElement(driver, del_css);
        del_ele.click();
        Reporter.log(" 'Del Icon' clicked.");

        //display delete box for a while for showcasing.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String delete_css = "div.ant-modal-footer button:nth-child(2)"; //"button.ant-btn.ant-btn-primary";
        WebElement del_btn_ele = Utility.getWebElement(driver,delete_css );
        del_btn_ele.click();
        Reporter.log("Delete button clicked.");
    }

    public void assertExperimentDeleted(String del_exp_file, String result_file)
    {
        Reporter.log("To assert experiment file name is deleted.");

        //Locate parent element first
        WebElement  parent_ele = Utility.getWebElement(driver, parent_css);

        //Get all children elements
        String child_css = "div.header-container.experiment-list-item";
        List <WebElement> ele_list = parent_ele.findElements(By.cssSelector(child_css));

        //Check if the deleted experiment not in
        for ( WebElement ele:ele_list)
        {
            String exp_file =  ele.getAttribute("title");
            System.out.println(exp_file);
            assertFalse("Deleted file still is there!", exp_file.equals(del_exp_file));
         }

        Reporter.log("The experiment " + del_exp_file + " deleted.");

        //Take screenshot
        boolean ret = Utility.saveScreenShot(driver, result_file);
        if (ret) {
            System.out.println("MLFlow main page screenshot created.");
            Reporter.log("MLFlow main page screenshot created.");
        }
        else
        {
            System.out.println("MLFlow main page screenshot not created.");
            Reporter.log("MLFlow Main page screenshot not created.");
        }

    }//assertExperimentDeleted
}