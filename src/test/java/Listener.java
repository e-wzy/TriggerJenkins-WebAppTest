import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener
{
    @Override
    //An onTestStart() is invoked only when any test method gets started.
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    //An onTestSuccess() method is executed on the success of a test method.
    public void onTestSuccess(ITestResult result) {
    // TODO Auto-generated method stub
        System.out.println("Success of test cases and its details are : "+result.getName());
    }

    @Override
    //An onTestFailure() method is invoked when test method fails.
    public void onTestFailure(ITestResult result) {
    // TODO Auto-generated method stub
        System.out.println("Failure of test cases and its details are : "+result.getName());
    }

    @Override
    //An onTestSkipped() run only when any test method has been skipped.
    public void onTestSkipped(ITestResult result) {
    // TODO Auto-generated method stub
        System.out.println("Skip of test cases and its details are : "+result.getName());
    }

    @Override
    //This method is invoked each time when the test method fails but within success percentage.
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // TODO Auto-generated method stub
        System.out.println("Failure of test cases and its details are : "+result.getName());
    }

    @Override
    //An onStart() method is executed on the start of any test method.
    public void onStart(ITestContext context) {
    // TODO Auto-generated method stub
    }

    @Override
    //An onFinish() is invoked when any test case finishes its execution.
    public void onFinish(ITestContext context) {
    // TODO Auto-generated method stub
    }
}