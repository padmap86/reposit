package Suite;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utils.ExcelUtils;
import utils.TestBase;

@Listeners(utils.TestResult.class)
public class TestCase1 extends TestBase {

	String pageLoadStatus = null;
	ExcelUtils excel = new ExcelUtils();
	static Logger log = LogManager.getLogger(TestCase1.class.getName());
	int i = 0;

	@BeforeTest(alwaysRun = true)
	public void setUp() throws InterruptedException, IOException {

		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "/src/main/resources/propertiesfile/log4j.properties");
		initialization();
		log.info("Login into Application");

	}

	// Hover over Services Link and then Click Automation link
	@Test(enabled = true, priority = 0)
	public void AA_MoveTo_AutomationLink() throws InterruptedException {
		SoftAssert soft = new SoftAssert();
		implicitWait();

		log.info("Move to Services Text");
		soft.assertEquals(iselementpresent(property.getProperty("services")), true, "Services Link is not Present");
		mousehover(property.getProperty("services"));

		log.info("Move to Automation Text");
		soft.assertEquals(iselementpresent(property.getProperty("automation")), true,
				"Automation SubLink is not Present");
		clickelement(property.getProperty("automation"));

		soft.assertAll();
	}

	// Verify that Automation Screen is displayed and “Automation” text is visible in Page
	@Test(enabled = true, priority = 1)
	public void AB_Verify_AutomationPage() throws InterruptedException {
		SoftAssert soft = new SoftAssert();
		implicitWait();

		log.info("Verify the URL Moved");
		String geturl = geturl();
		soft.assertEquals(geturl, "https://www.sogeti.com/services/automation/", "Displayed URL is not Correct ");

		// Javascript executor to return value
		log.info("Verify that Page is loaded completely");
		JavascriptExecutor j = (JavascriptExecutor) driver;
		pageLoadStatus = j.executeScript("return document.readyState").toString();
		soft.assertTrue(pageLoadStatus.equals("complete"), "Page is not Loaded completely");

		log.info("Verify the Page Title");
		String gettitle = getpagetitle();
		soft.assertEquals(gettitle, "Automation", "Automation text is not visible in Page");

		soft.assertAll();
	}

	// Hover again over Services Link. Verify that the Services and Automation are
	// selected
	@Test(enabled =true, priority = 2)
	public void AC_Verify_PageSelected() throws InterruptedException {
		SoftAssert soft = new SoftAssert();
		implicitWait();

		log.info("Move to Services Text");
		mousehover(property.getProperty("services"));

		// String getcolor = getcssvalue(property.getProperty("services"));
		// System.out.println( getcolor );
		// String getcolor1 = getcssvalue(property.getProperty("automation"));
		// System.out.println( getcolor1 );

		log.info("Verify that the Services are Selected");
		String get = getattributeValue_class(property.getProperty("services_link"));
		soft.assertTrue(get.contains("selected"), "Services Link is not Selected");

		log.info("Verify that the Automation are Selected");
		String get1 = getattributeValue_class(property.getProperty("automation_link"));
		soft.assertTrue(get1.contains("selected"), "Automation Link is not Selected");

		soft.assertAll();
	}

	/* Write Result to Excel Sheet */
	@AfterMethod(alwaysRun = true)
	public void writeResult(ITestResult result) throws IOException {

		log.info("Write the Result in Excel Sheet");
		excel.writeExcel("TestCase1", String.valueOf(result.getStatus()), result.getMethod().getMethodName(), 1);

	}

	/* Close the browser */
	@AfterSuite
	public void tearDown() throws IOException, InterruptedException {

		log.info("Log the Console Errors");
		verifyConsoleErrors();

		log.info("Close the application");
		driver.close();

		log.info("Close all browsers");
		driver.quit();
	}
}
