package TestRun;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Pages.createAccount;
import Pages.homePage;
import Repository.RepositoryParser;



public class SampleTests {
	RepositoryParser parser;
	WebDriver driver;
	ExtentTest test;
	ExtentReports report;
	
	@Test(dataProvider = "ToolData",threadPoolSize=7)
	public void driversetUp(HashMap<String, String> data) throws IOException, InterruptedException
	{
		String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//report = new ExtentReports("D:\\RestWorkspace\\SeleniumFramework\\src\\test\\java\\Reports\\ExtentReportResults"+fileSuffix+".html");
		report = new ExtentReports("C:\\Users\\Ramkumar\\git\\Repo_SeleniumFramework\\SeleniumFramework_GIT\\src\\test\\java\\Reports\\Amazonpurchaseresults.html");
		test = report.startTest("ExtentDemo");
		parser = new RepositoryParser("ObjectRepo.properties");
		if(data.get("ToRun").equalsIgnoreCase("Yes"))
		{
		System.setProperty("webdriver.chrome.driver", "E:\\ChromeDriverNew\\latest\\chromedriver.exe");
		
		//Prerequisites: ZAP tool has to be installed and opened
//***********************************************************************
		//Code - Penetration Testing - ZAP OWASP
		
		//Initiate proxy for ZAP application port 
		ChromeOptions options = new ChromeOptions();
		Proxy proxy = new Proxy();
		proxy.setHttpProxy("localhost:8081");
		proxy.setSslProxy("localhost:8081");
		
		//Make the browser to pass through ZAP proxy port
		options.setCapability("proxy", proxy); 
		driver = new ChromeDriver(options);
		driver.get("https://www.pearson.com/english");
		//ZAP tool automatically does the penetration tests the above URL
		
//*******************************END**************************************
		
		
		
		homePage hp = new homePage(parser,driver, data, test);
		hp.purchaseMobile(data.get("Mobile"));
		hp.purchaseBook(data.get("Book"));
		hp.purchaseLaptop(data.get("Laptop"));
		
		test.log(LogStatus.PASS, "Products has been added to cart successfully");
		Thread.sleep(1500);
		}
		report.endTest(test);
		report.flush();
	}
	
	
	@DataProvider(name="ToolData", parallel=true)
	public Iterator<Object[]> getExcelData() throws IOException{
		ArrayList<HashMap> excelData;
		TestDataReader.readExcelFile objExcelFile = new TestDataReader.readExcelFile();
		//excelData = objExcelFile.readExcel("E:\\ExcelData","ToolsQATestData.xls","Sheet1");
		excelData = objExcelFile.readExcel("D:\\RestWorkspace\\SeleniumFramework\\src\\test\\java\\TestData\\","ToolsQATestData.xls","Sheet1");
		
		List<Object[]> dataArray = new ArrayList<Object[]>();
		for(HashMap data : excelData){
			dataArray.add(new Object[] { data });
			}
		return dataArray.iterator();
	}
	//http://localhost:8090/JSON/ascan/action/scan/?apikey=12345&zapapiformat=JSON&formMethod=GET&url=https://www.webscantest.com&recurse=&inScopeOnly=false&scanPolicyName=&method=&postData=&contextId="
}
