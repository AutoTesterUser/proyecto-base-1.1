package test.java;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.utils.Constants;
import main.java.utils.Utils;

public class BaseTest {

	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String extentDate;

	@BeforeTest
	public void beforeTestMethod() {
		String relativeDirectory = System.getProperty("user.dir") + File.separator + "report" + File.separator;
		String fileName = "AutomationReport_" + Utils.getCurrentDate();
		String format = ".html";
		htmlReporter = new ExtentHtmlReporter(relativeDirectory + fileName + format);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Automation Tests Results");
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project", "[Nombre proyecto]");
		extent.setSystemInfo("Automation tester", "[Nombre automatizador/es");
		extentDate = htmlReporter.getFilePath().replace(relativeDirectory, "").replace("AutomationReport_", "")
				.replace(format, "");
	}

	@BeforeMethod
	@Parameters(value = { "browserName" })
	public void beforeMethod(String browserName, Method testMethod) {
		logger = extent.createTest(testMethod.getName());
		setUpDriver(browserName);
		driver.manage().window().maximize();
		driver.get(Constants.URL);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		String methodName = result.getMethod().getMethodName();

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel("Test Case '" + methodName + "' passed", ExtentColor.GREEN));
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + File.separator + "screenshots"
					+ File.separator + "passed" + File.separator + methodName + "_" + extentDate + ".png", methodName);
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel("Test Case '" + methodName + "' skipped", ExtentColor.AMBER));
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + File.separator + "screenshots"
					+ File.separator + "skipped" + File.separator + methodName + "_" + extentDate + ".png", methodName);
		}
		driver.quit();
	}

	@AfterTest
	public void afterTestMethod() {
		extent.flush();
	}

	public void setUpDriver(String browserName) {

		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			driver = new ChromeDriver(options);
		} else if (browserName.contains("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserName.contains("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
	}
}
