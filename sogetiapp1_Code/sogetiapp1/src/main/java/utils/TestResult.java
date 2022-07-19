package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestResult extends TestBase implements ITestListener {

	String screenshots = System.getProperty("user.dir") + File.separator + "Screenshots";

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("Method name:" + result.getMethod().getMethodName());

	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		DateFormat DFormat = DateFormat.getDateTimeInstance();
		String date = DFormat.format(new Date());
		Reporter.log("Test script ran on " + date);

	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("***** Error " + result.getName() + " test has failed *****");
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(screenshots + "/" + result.getName().toString().trim() + ".png");
			FileUtils.copyFile(screenshotFile, targetFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
