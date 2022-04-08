import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class TestLoginLogout{
    private WebDriver driver;

    //  private String url= "https://www.google.com/";
   /* private String url = "https://compute-3.dastc.stee.com:8080/";
    private String workingDir;
    private String testDataPath;
    private String testRsltPath;
    private String pathToChromeDriver;
    private String pathToFireFoxDriver;*/

    //----------------------------
    // The tag @BeforeSuite has control over the whole XML file. The tag is the parent of all the test folders
    // @BeforeSuite: The @BeforeSuite annotated method is executed before the execution of all the test cases
    // defined in the folder of testng xml.
    //
    // @BeforeTest and @AfterTest which have control over the particular folder not on the entire framework.
    //The @BeforeTest annotated method will be executed before all the test methods
    //present in the classes which are kept inside the same test folder in testng xml
    //----------------------------

    //@BeforeTest
  //  @BeforeSuite
    /*public void setup() {

        workingDir = Path.of("").toAbsolutePath().toString();
        testDataPath = workingDir + "\\test-data\\";
        testRsltPath = workingDir + "\\test-result\\";

       // pathToChromeDriver = workingDir + "\\chromedriver.exe";
        pathToFireFoxDriver = workingDir + "\\geckodriver.exe";

       //Ensure main page loaded.  Ctrl+/
        int timeout=5;
        String submit_class_css = ".btn.btn-lg.btn-primary";
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement submit = wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector(submit_class_css))));

        String submitText = submit.getText();
        assertEquals("Submit", submitText);
        System.out.println("First method - URL launched in setup().");
    }*/

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

   // @Test
    //This test (read xls file)  to test
    // 1) successful login and logout
    // 2) unsuccessful login.
   /* public void test_1() throws IOException {
        System.out.println("I am in test 1 in TestCases class");

        //Load test data
        LinkedList<String> test_case_id_list = new LinkedList<String>();
        LinkedList<LoginPage> test_data_list = new LinkedList<LoginPage>();
        File src = new File(testDataPath + "TestData.xls");
        readFormData_xls(src, test_data_list, test_case_id_list);
        System.out.println(test_case_id_list); // can print out all element

        //Conduct DDT testing
        for (int i = 0; i < test_case_id_list.size(); i++) {
            LoginPage loginPage = test_data_list.get(i);
            loginPage.submitForm();

            MainPage mainPage = new MainPage(driver, testRsltPath, test_case_id_list.get(i));
            mainPage.assertMainPage();

            //The code below is not necessary, just wait a while to observe running result.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainPage.Logout();
            loginPage.assertLoginPage();

            //The code below is not necessary, just wait a while to observe running result.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Test
    //Test successful login
    public void Test_1() throws IOException {

            Reporter.log("Test 1 - Test successful login and logout.");

            String test_case_id = "Login-TC001";

            LoginPage  loginPage = new LoginPage(driver, "zhiyan.wang", "MS4H5r_w");
            loginPage.submitForm();

            MainPage mainPage = new MainPage(driver);
            String result_file = Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_SuccessLoginPage.jpg";
            mainPage.assertMainPage(result_file);

            //The code below is not necessary, just wait a while to observe running result.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mainPage.Logout();
            //Assert successful logout
            result_file =  Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_SuccessLogoutPage.jpg";
            loginPage.assertLoginPage(result_file);

            //The code below is not necessary, just wait a while to observe running result.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Test
    //Test unsuccessful login
    public void Test_2() throws IOException {

        Reporter.log("Test 2 - Test unsuccessful login.");

        LoginPage  loginPage = new LoginPage(driver, "zhiyan.wang", "MS4H5r"); //wrong password
        loginPage.submitForm();

        String test_case_id = "Login-TC002";
        String result_file =  Configuration.testRsltPath + Utility.getTimeStamp() + test_case_id + "_FailedLoginPage.jpg";
        loginPage.assertFailedLogin(result_file);

        //The code below is not necessary, just wait a while to observe running result.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------
    //The @AfterSuite annotated method is executed after the execution of
    // all the test methods in the Suite.
    // The @AfterTest annotated method run after the execution of all the test methods
    // present in the classes which are kept inside the same test folder in testng xml
    // The @AfterMethod annotated method run after the execution of each method in a class
    //----------------------------------------
    //@AfterTest
    //@AfterSuite
    @AfterMethod
    public void teardown(){
        //driver.close();     //close current window
        driver.quit();    //close all windows
        System.out.println("Last method - Tear down. ");
    }

    //Read .xls file to create
    public void readFormData_xls(File src, LinkedList<LoginPage> formlist, LinkedList<String> test_id_list ) throws IOException {
        HSSFWorkbook workbook;
        HSSFSheet sheet;
        HSSFCell cell;

        //Import excel sheet.
        // Load the file.
        FileInputStream finput = new FileInputStream(src);

        // Load the workbook.
        workbook = new HSSFWorkbook(finput);

        // Load the sheet in which data is stored.
        sheet= workbook.getSheetAt(0);

        //Test data
        String test_id="";
        String user_name ="";
        String password  = "";

        for (int i=1; i<=sheet.getLastRowNum(); i++)
        {
            //Import data for test case id
            cell = sheet.getRow(i).getCell(0);
            cell.setCellType(CellType.STRING);

            test_id = cell.getStringCellValue();
            System.out.println(" test case id " + test_id);

            // Import data for first name
            cell = sheet.getRow(i).getCell(1);
            cell.setCellType(CellType.STRING);

            user_name = cell.getStringCellValue();
            System.out.println(" user name: " + user_name);

            // Import data for last name
            cell = sheet.getRow(i).getCell(2);
            cell.setCellType(CellType.STRING);

            password = cell.getStringCellValue();
            System.out.println(" password " + password );

            //Form link list
            LoginPage formObj = new LoginPage(driver, user_name, password);
            formlist.add(formObj);
            test_id_list.add(test_id);

            /*// Write data in the excel.
            FileOutputStream foutput=new FileOutputStream(src);

            // Specify the message needs to be written.
            String message = "Data Imported Successfully.";

            // Create cell where data needs to be written.
            sheet.getRow(i).createCell(5).setCellValue(message);

            // finally write content
            workbook.write(foutput);

            // close the file
            foutput.close();*/
        }//for

    }//readExcel_xls

    public void readFormData_xlsx() throws IOException {
        //for xlsx
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        XSSFCell cell;

        //Import excel sheet.

        File src=new File( Configuration.testDataPath + "TestData.xlsx");

        // Load the file.
        FileInputStream finput = new FileInputStream(src);

        // Load he workbook.
        workbook = new XSSFWorkbook(finput);

        // Load the sheet in which data is stored.
        sheet= workbook.getSheetAt(0);

        //Test data
        String test_id="";
        String first_name="";
        String last_name = "";
        String job_title = "";
        String picked_date = "";

        for (int i=1; i<=sheet.getLastRowNum(); i++)
        {
            // Import data for first name
            cell = sheet.getRow(i).getCell(1);
            cell.setCellType(CellType.STRING);

            first_name = cell.getStringCellValue();
            System.out.println(" first name: " + first_name);

            // Import data for last name
            cell = sheet.getRow(i).getCell(2);
            cell.setCellType(CellType.STRING);

            last_name = cell.getStringCellValue();
            System.out.println(" last name: " + last_name );

            // Import data for job title
            cell = sheet.getRow(i).getCell(3);
            cell.setCellType(CellType.STRING);

            job_title = cell.getStringCellValue();
            System.out.println(" job title: " + job_title );

            // Import data for picked_date
            cell = sheet.getRow(i).getCell(4);
            cell.setCellType(CellType.STRING);

            picked_date = cell.getStringCellValue();
            System.out.println(" picked date: " + picked_date );

            // Write data in the excel.
            /*FileOutputStream foutput=new FileOutputStream(src);

            // Specify the message needs to be written.
            String message = "Data Imported Successfully.";

            // Create cell where data needs to be written.
            sheet.getRow(i).createCell(5).setCellValue(message);

            // finally write content
            workbook.write(foutput);

            // close the file
            foutput.close(); */
        }//for

    }//readExcel_xlsx

}