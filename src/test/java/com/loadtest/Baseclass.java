package com.loadtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Baseclass {
	private static Workbook wb;
	private static Sheet sh;
	private static XSSFCell col = null;
	private static Row row;
	public static FileInputStream f = null;
	public static HashMap<String, String> currentHash = new HashMap<String, String>();
	public static List<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();
	public static WebDriver dr;
public static void initialization(){

		
	 String	browserName = "chrome"; 
	 {
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\chromedriver.exe");
			
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			caps.setCapability(ChromeOptions.CAPABILITY, options);

			dr = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\geckodriver.exe");
			dr = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {

			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\IEDriverServer.exe");

			InternetExplorerOptions caps = new InternetExplorerOptions();
			caps.requireWindowFocus();
			//caps.withInitialBrowserUrl("http://localhost");
			caps.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
			caps.introduceFlakinessByIgnoringSecurityDomains();
			caps.enablePersistentHovering();

			dr = new InternetExplorerDriver(caps);
		} else if (browserName.equalsIgnoreCase("gecko")) {
			FirefoxOptions options = new FirefoxOptions();
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\main\\resources\\Drivers\\geckodriver.exe");
			dr = new FirefoxDriver(options);
		}	
		
		System.out.println("METHOD(initialization) EXECUTION ENDED SUCCESSFULLY");
	}
}


}