import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class Utility {
   static int timeout = 20;   //20 seconds

    Utility() {}

    public static boolean saveScreenShot(WebDriver driver, String FileName)
    {
       //Take screenshot as test result.
        TakesScreenshot scrShot = ((TakesScreenshot) driver);

        //Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Copy file to test result folder
        File DestFile = new File(FileName);

        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            System.out.print("Error:" + e.toString());
            return false;
        }
        return true;
    }

    public static String getTimeStamp()
    {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss_");
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());

         String sdf_timestamp = sdf.format(timestamp);

         return  sdf_timestamp;
    }

    public static WebElement getWebElement(WebDriver driver, String cssLocator)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement web_element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssLocator)));
        return web_element;
    }

    public static WebElement getWebElementByTag( WebDriver driver, String tag)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        WebElement web_element  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(tag)));
        return web_element;
    }

}
