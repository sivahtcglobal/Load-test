package com.loadtest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestMultipleThreads extends Excel {

	public static Double LoadTime = 0.0;
	public static String pageURL = null;
	public static Object[][] testdata = null;
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static Date date = new Date();

	@DataProvider(name = "data-provider")
	public Object[][] dataProviderMethod() {
		return new Object[][] { { "TC001" }, { "TC002" }, };
	}

	@Test(dataProvider = "data-provider")
	public static void loadTestofNewsPage(String data) throws FileNotFoundException, IOException {
		int i = 0;
		int noOfExecution=0;
		String timestamp = null;
		while (i < 2) {
			try {
				readSpecificTestData(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			initialization();
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();
			if (currentHash.get("TestCaseId").equals("TC004") || currentHash.get("TestCaseId").equals("TC005")) {
				dr.navigate().to(currentHash.get("TestCaseUrl"));
				WebElement nonMonetizedZipCode = dr.findElement(By.xpath("//*[@id='free-quote-zip']"));
				nonMonetizedZipCode.sendKeys(currentHash.get("ZipCode"));
				WebElement getQuoteButton = dr.findElement(By.xpath("//*[@id='free-quote']/button"));
				getQuoteButton.click();
				pageURL = dr.getCurrentUrl();
				waitForPageLoaded(i);
				timestamp = dateFormat.format(date);
				System.out.println(timestamp);
				noOfExecution=i;
				writeToExcelSheet(data, pageURL, LoadTime, ++noOfExecution, timestamp);
				dr.quit();
				++i;
			} else {
				dr.navigate().to(currentHash.get("TestCaseUrl"));
				pageURL = dr.getCurrentUrl();
				waitForPageLoaded(i);
				timestamp = dateFormat.format(date);
				System.out.println(timestamp);
				noOfExecution=i;
				writeToExcelSheet(data, pageURL, LoadTime, ++noOfExecution , timestamp);
				dr.quit();
				++i;
			}
		}

		System.out.println("Average load time for " + i + " times execution is: " + LoadTime / i);
//		writeToExcelSheet(pageURL, avgLoadTime , i);
	}

	public static void waitForPageLoaded(int i) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			// Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(dr, 30);
			wait.until(expectation);
			Long loadtime = (Long) ((JavascriptExecutor) dr)
					.executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
			System.out.println(loadtime);
			Double Loadtimems = (double) (loadtime / 1000.0);

			System.out.println("Page Loadtime in Seconds:" + Loadtimems + " No of Times: " + (i + 1));
			LoadTime = Loadtimems;
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

}
