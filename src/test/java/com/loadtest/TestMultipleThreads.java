package com.loadtest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMultipleThreads extends Baseclass {
	public static int i = 0;
	public static Double avgLoadTime = 0.0;

	@Test()
	public static void loadTestofNewsPage() throws FileNotFoundException, IOException {
		i = 0;
		avgLoadTime = 0.0;
		while (i < 50) {
			initialization();
//			try {
//				//Excel.readSpecificTestData("TC001");
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();

			dr.navigate().to("https://www20.thegeneraltest.com/news/");

			waitForPageLoaded();

			dr.quit();
			++i;

		}
		System.out.println("Average load time for " + i + " times execution is: " + avgLoadTime / i);
		Excel.writeToExcelSheet("www20.thegeneraltest.com/news/", avgLoadTime / i, i);
	}

	@Test()
	public static void loadTestofQuotePage() throws FileNotFoundException, IOException {
		i = 0;
		avgLoadTime = 0.0;
		while (i < 50) {
			initialization();
//			try {
//				//Excel.readSpecificTestData("TC001");
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();

			dr.navigate().to("https://www20.thegeneraltest.com/quote/");

			waitForPageLoaded();

			dr.quit();
			++i;

		}
		System.out.println("Average load time for " + i + " times execution is: " + avgLoadTime / i);
		Excel.writeToExcelSheet("www20.thegeneraltest.com/quote/", avgLoadTime / i, i);
	}

	@Test()
	public static void loadTestofHomePage() throws FileNotFoundException, IOException {
		i = 0;
		avgLoadTime = 0.0;
		while (i < 50) {
			initialization();
//			try {
//				//Excel.readSpecificTestData("TC001");
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();

			dr.navigate().to("https://www20.thegeneraltest.com/");

			waitForPageLoaded();

			dr.quit();
			++i;

		}
		System.out.println("Average load time for " + i + " times execution is: " + avgLoadTime / i);
		Excel.writeToExcelSheet("www20.thegeneraltest.com/", avgLoadTime / i, i);
	}

	public static void waitForPageLoaded() {
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

			System.out.println("Page Loadtime in Seconds:" + Loadtimems);
			avgLoadTime = avgLoadTime + Loadtimems;
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	@Test()
	public static void loadTestWithValidZipCode() throws FileNotFoundException, IOException {
		i = 0;
		avgLoadTime = 0.0;
		while (i < 50) {
			initialization();
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();
			dr.navigate().to("https://www20.thegeneraltest.com");
			WebElement nonMonetizedZipCode = dr.findElement(By.xpath("//*[@id='free-quote-zip']"));
			nonMonetizedZipCode.sendKeys("33183");
			WebElement getQuoteButton = dr.findElement(By.xpath("//*[@id='free-quote']/button"));
			getQuoteButton.click();
			waitForPageLoaded();

			dr.quit();
			++i;
		}
		System.out.println("Average load time for " + i + " times execution is: " + avgLoadTime / i);
		Excel.writeToExcelSheet("www20.thegeneraltest.com with Valid non-monetized ZIP code", avgLoadTime / i, i);
	}

	@Test()
	public static void loadTestWithInValidZipCode() throws FileNotFoundException, IOException {
		i = 0;
		avgLoadTime = 0.0;
		while (i < 50) {
			initialization();
			dr.manage().window().maximize();
			dr.manage().deleteAllCookies();
			dr.navigate().to("https://www20.thegeneraltest.com");
			WebElement nonMonetizedZipCode = dr.findElement(By.xpath("//*[@id='free-quote-zip']"));
			nonMonetizedZipCode.sendKeys("99999");
			WebElement getQuoteButton = dr.findElement(By.xpath("//*[@id='free-quote']/button"));
			getQuoteButton.click();
			waitForPageLoaded();

			dr.quit();
			++i;
		}
		System.out.println("Average load time for " + i + " times execution is: " + avgLoadTime / i);
		Excel.writeToExcelSheet("www20.thegeneraltest.com with InValid non-monetized ZIP code", avgLoadTime / i, i);
	}
}
