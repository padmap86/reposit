package Suite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
//import java.util.Random;
import utils.ExcelUtils;
import utils.TestBase;

@Listeners(utils.TestResult.class)
public class TestCase2 extends TestBase {

	ExcelUtils excel = new ExcelUtils();
	Fairy fairy = Fairy.create();
	Person person = fairy.person();
	static org.apache.log4j.Logger log = LogManager.getLogger(TestCase2.class.getName());

	@BeforeTest(alwaysRun = true)
	public void setUp() throws InterruptedException, IOException {

		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator
				+ "/src/main/resources/propertiesfile/log4j.properties");
		initialization();
		log.info("Login into Application");
	}

	// Fill the Form and Submit
	@Test(enabled = true, priority = 1)
	public void BA_ContactDetails() throws InterruptedException, IOException {
		SoftAssert soft = new SoftAssert();
		implicitWait();

		log.info("Move to Services Text");
		mousehover(property.getProperty("services"));

		log.info("Move to Automation Text");
		clickelement(property.getProperty("automation"));

		// JavascriptExecutor j = (JavascriptExecutor) driver;
		// ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,
		// document.body.scrollHeight)");
		// j.executeScript("window.scrollBy(0,900)");
		log.info("Scroll down the Page");
		for (int i = 0; i < 5; i++) {
			scrolldown();
		}
		Thread.sleep(4000);

		log.info("Collect datas from Fairy tool");
		String firstName = person.firstName();
		String lastName = person.lastName();
		String phoneNumber = person.telephoneNumber();
		String message = fairy.textProducer().sentence(300);
		String company = fairy.company().name();
		// long phoneNumber = (long) (Math.random() * 100000 + 3333000000L);
		String email = person.companyEmail();

		log.info("Submit the Form to verify for Error Message");
		clickelement(property.getProperty("submitbtn"));

		log.info("Validate the Error Message and Enter Frist Name");
		// Enter First Name
		mousehover(property.getProperty("firstname"));
		soft.assertEquals(getdata(property.getProperty("firstname_errorMessage")), "This field is required.",
				"Error Message in First Name Field is not present");
		dataentry(firstName, property.getProperty("firstname"));
		clickenter(property.getProperty("firstname"));
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Last Name");
		// Enter Last Name
		mousehover(property.getProperty("lastname"));
		soft.assertEquals(getdata(property.getProperty("lastname_errorMessage")), "This field is required.",
				"Error Message in Last Name Field is not present");
		dataentry(lastName, property.getProperty("lastname"));
		clickenter(property.getProperty("lastname"));
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Email");
		// Enter Email
		mousehover(property.getProperty("email"));
		soft.assertEquals(getdata(property.getProperty("email_errorMessage")), "This field is required.",
				"Error Message in Email Field is is not present");
		dataentry(email, property.getProperty("email"));
		clickenter(property.getProperty("email"));
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Phone Number");
		// Enter Phone Number
		mousehover(property.getProperty("phoneno"));
		soft.assertEquals(getdata(property.getProperty("phoneno_errorMessage")), "",
				"Error Message in Phone Number is not present");
		dataentry(phoneNumber, property.getProperty("phoneno"));
		clickenter(property.getProperty("phoneno"));
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Company Name");
		// Enter Company
		mousehover(property.getProperty("company"));
		soft.assertEquals(getdata(property.getProperty("company_errorMessage")), "This field is required.",
				"Error Message in Company field is not present");
		dataentry(company, property.getProperty("company"));
		clickenter(property.getProperty("company"));
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Country Name");
		// Enter Country
		mousehover(property.getProperty("country"));
		soft.assertEquals(getdata(property.getProperty("country_errorMessage")), "This field is required.",
				"Error Message in Country field is not present");
		Thread.sleep(1000);
		clickelement(property.getProperty("country"));
		String countryname = excel.getdata("FormDetails", "Country", 1);
		selectDropdown(property.getProperty("country"), countryname);
		Thread.sleep(1000);

		log.info("Validate the Error Message and Enter Message");
		// Enter Message
		mousehover(property.getProperty("message"));
		soft.assertEquals(getdata(property.getProperty("message_errorMessage")), "This field is required.",
				"Error Message in Message Field is not present");
		dataentry(message, property.getProperty("message"));
		clickenter(property.getProperty("message"));
		Thread.sleep(1000);

		log.info("Click Agree CheckBox");
		clickelement(property.getProperty("agreechk"));
		scrolldown();

		log.info("Captcha Workaround");
		Thread.sleep(1000);
		/*
		 * 
		 * Handling Captcha in Selenium: 1. By disabling the Captcha in the testing
		 * environment 2. By adding a delay to the Webdriver and manually solve Captcha
		 * while testing 3. Use API of external services such as
		 * http://www.deathbycaptcha.com for text Image
		 */


		log.info("Click Captcha Check Box"); // Click Captcha Check Box 
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(
				"//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
		new WebDriverWait(driver, Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.recaptcha-checkbox-border")))
				.click();
		Thread.sleep(3000);

		log.info("Select Captcha manually"); // Manually select the Captcha //
		//alert();
		Thread.sleep(8000);
		driver.switchTo().defaultContent();
		log.info("Click Submit button");
		clickelement(property.getProperty("submitbtn"));

		/*
		 * WebElement element =
		 * driver.findElement(By.xpath(property.getProperty("submitbtn")));
		 * JavascriptExecutor executor = (JavascriptExecutor) driver;
		 * executor.executeScript("arguments[0].click();", element); Thread.sleep(1000);
		 */

		Thread.sleep(4000);
		System.out.println(getdata(property.getProperty("successMessage")));
		log.info("Validate the Success Message");
		soft.assertEquals(getdata(property.getProperty("successMessage")), "Thank you for contacting us.",
				"Thank you Message not found");
		log.info("Validated the Success Message");
		 
		soft.assertAll();
		log.info("Validated the Success Message");
	}

	/* Write Result to Excel Sheet */
	@AfterMethod(alwaysRun = true)
	public void writeResult(ITestResult result) throws IOException {

		log.info("Write the Result in Excel Sheet");
		excel.writeExcel("TestCase2", String.valueOf(result.getStatus()), result.getMethod().getMethodName(), 1);

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
