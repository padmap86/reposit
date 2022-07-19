package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop, property;
	protected static String downloadPath = System.getProperty("user.dir") + File.separator + "Downloads";
	Path source = Paths.get("./BrowserLogs/console.log");
	File source1 = source.toFile();
	Path sev = Paths.get("./BrowserLogs/severe.log");
	File sev1 = sev.toFile();
	public static long PAGE_LOAD_TIMEOUT = 68;
	public static long IMPLICIT_WAIT = 40;

	public TestBase() {

		prop = new Properties();
		property = new Properties();
		FileInputStream fip = null, fis = null;
		try {
			fip = new FileInputStream("./src/main/resources/config/config.properties");
			fis = new FileInputStream("./src/main/resources/propertiesfile/ObjectRepository.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			property.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//To initiate the browser
	public static void initialization() throws IOException, InterruptedException {
		
		ChromeOptions chromeOptions = new ChromeOptions();
		LoggingPreferences logPreferences = new LoggingPreferences();
		logPreferences.enable(LogType.BROWSER, java.util.logging.Level.ALL);
		chromeOptions.setCapability("goog:loggingPrefs", logPreferences);
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("profile.default_content_setting_values.notifications", 1);
		chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/Resources/chromedriver.exe");
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		clickelement(property.getProperty("acceptcookie"));
		File f = new File(downloadPath);
		FileUtils.cleanDirectory(f);	
		//PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "/src/main/resources/propertiesfile/log4j.properties");

	}

	// Implicit Wait
	public static void implicitWait() {

		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	//To Click an element
	public static void clickelement(String xpath) throws InterruptedException {

		driver.findElement(By.xpath(xpath)).click();
	}

	//To Enter Data in Field
	public void dataentry(String value, String xpath) throws InterruptedException {
		driver.findElement(By.xpath(xpath)).sendKeys(value);
	}

	//To clear the field
	public void dataclear(String xpath) throws InterruptedException {
		driver.findElement(By.xpath(xpath)).clear();
	}

	//To get the attribute value
	public String getattributeValue(String xpath) {

		return driver.findElement(By.xpath(xpath)).getAttribute("value");
	}

	//To get attribute of class 
	public String getattributeValue_class(String xpath) {

		return driver.findElement(By.xpath(xpath)).getAttribute("class");
	}

	// To get CSS Value of Border Color
	public String getcssvalue1(String xpath) {

		return driver.findElement(By.xpath(xpath)).getCssValue("border-bottom-color");
	}

	// To get CSS Value of Color
	public String getcssvalue(String xpath) {

		return driver.findElement(By.xpath(xpath)).getCssValue("color");
	}

	//To get text of an element
	public String getdata(String xpath) {

		return driver.findElement(By.xpath(xpath)).getText();

	}

	// To perform Keyboard Enter
	public static void clickenter(String xpath) {
		driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
	}

	//To Alert on Page
	public void alert() throws InterruptedException {
		// JavascriptExecutor jse = (JavascriptExecutor) driver;

		try {
			JOptionPane.showMessageDialog(null, "Please Select the Captcha to Submit the Form");
			// jse.executeScript("alert('Please Select the Captcha to Submit the Form');");
			Thread.sleep(3000);
			// Alert alt = driver.switchTo().alert();
			// alt.accept();
			// driver.switchTo().defaultContent();
			Thread.sleep(3000);
		} catch (NoAlertPresentException noe) {

		}

	}

	// To move the mouse 
	public void mousehover(String xpath) {

		new Actions(driver).moveToElement(driver.findElement(By.xpath(xpath))).perform();

	}

	// To Get the title of the browser
	public String getpagetitle() {

		return driver.getTitle();
	}

	// To Get the Current URL
	public String geturl() {

		return driver.getCurrentUrl();
	}

	// Verify an element is displayed
	public static boolean iselementdisplayed(String xpath) {

		return (driver.findElement(By.xpath(xpath)).isDisplayed());

	}

	//To verify an element is present or not
	public static boolean iselementpresent(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Verify an element is selected
	public static boolean iselementselected_LinkText(String text) {

		return (driver.findElement(By.linkText(text)).isSelected());

	}

	// Verify an element is selected
	public static boolean iselementselected(String xpath) {

		return (driver.findElement(By.xpath(xpath)).isSelected());

	}

	//To scroll down the page
	public void scrolldown() {

		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
	}
	
	//To scroll down the page
	public void scrollup() {

		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_UP).build().perform();
	}

	// gets the list of elements from a webpage
	public List<WebElement> getListofElements(String xpath) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		return elements;
	}

	public void selectDropdown(String dropdownxpath, String option) {

		WebElement element = driver.findElement(By.xpath(dropdownxpath));
		Select listbox = new Select(element);
		List<WebElement> li = listbox.getOptions();

		for (int i = 0; i < li.size(); i++) {
			if ((li.get(i).getText()).equalsIgnoreCase(option)) {
				li.get(i).click();

			}

		}
	}

	//To validate the Fiel Download
	public Boolean isFileDownloaded(String fileName) {
		boolean flag = false;
		String dirPath = System.getProperty("user.dir") + File.separator + "Downloads";
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files.length == 0 || files == null) {
			Reporter.log("The directory is empty");
			flag = false;
		} else {
			for (File listFile : files) {
				if (listFile.getName().contains(fileName) && fileName.length() != 0) {
					Reporter.log(fileName + " is present");
					flag = true;
					break;
				}

			}
		}
		return flag;
	}

	//To console Errors
	public void verifyConsoleErrors() throws IOException, InterruptedException {
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		FileWriter consoleerr = new FileWriter(source1, true);
		FileWriter consoleerr1 = new FileWriter(sev1, true);
		for (LogEntry entry : logEntries) {
			consoleerr
					.write((new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n"));

			if (entry.getLevel().toString() == "SEVERE") {
				consoleerr1.write(
						(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "\n"));
			}
		}
		consoleerr.close();
		consoleerr1.close();

	}

}