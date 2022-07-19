package Suite;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utils.ExcelUtils;
import utils.TestBase;

/*
 * Click the Worldwide Dropdown link in Page Header
 * A Country dropdown list is displayed. These are the Country specific Sogeti links.
 * Assert that all the Country specific Sogeti links are working. 
 */

@Listeners(utils.TestResult.class)
public class TestCase3 extends TestBase {

	String url = "";
	String country = "";
	HttpURLConnection huc = null;
	int respCode = 200;
	SoftAssert soft = new SoftAssert();
	ExcelUtils excel = new ExcelUtils();
	static org.apache.log4j.Logger log = LogManager.getLogger(TestCase3.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws InterruptedException, IOException {

		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "/src/main/resources/propertiesfile/log4j.properties");
		log.info("Login into Application");
		initialization();

	}

	@Test(enabled = true, priority = 1)
	public void CA_LinksVerify() throws InterruptedException, IOException {

		log.info("Click on WorldWide");
		clickelement(property.getProperty("worldwide"));
		implicitWait();
		log.info("List all countries");
		List<WebElement> element = getListofElements(property.getProperty("listofcountries"));
		implicitWait();

		log.info("Get the Country name and URL and Validate the URL");
		for (int i = 0; i < element.size(); i++) {
			log.info("Get the country Name ");
			country = element.get(i).getText();

			log.info("Get the URL of Matched Country");
			url = element.get(i).getAttribute("href");

			log.info("Check if URL is Null or Empty");
			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured or it is empty");
				continue;
			}
			try {
				log.info("Open the Connection");
				huc = (HttpURLConnection) (new URL(url).openConnection());

				log.info("Set the Request Method");
				huc.setRequestMethod("GET");

				log.info("Establish the connection");
				huc.connect();

				log.info("Get the Response Status Code");
				respCode = huc.getResponseCode();

				log.info("Validate the URL is Valid or Not");
				if (respCode >= 400) {

					Reporter.log("The Link to " + country + "is " + url + " is not working");
				} else {

					Reporter.log("The Link to " + country + "is " + url + " is working");
	
				}
				
				log.info("Validated URL");

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/* Write Result to Excel Sheet */
	@AfterMethod(alwaysRun = true)
	public void writeResult(ITestResult result) throws IOException {

		log.info("Write the Result in Excel Sheet");
		excel.writeExcel("TestCase3", String.valueOf(result.getStatus()), result.getMethod().getMethodName(), 1);

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
