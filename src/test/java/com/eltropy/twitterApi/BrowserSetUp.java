package com.eltropy.twitterApi;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class BrowserSetUp {

	/**
	 * 
	 * @param BrowserName
	 * @return driver instance
	 */
	public static WebDriver browserSetup(String BrowserName) {
		WebDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("marionette", true);
		String driverPath = System.getProperty("user.dir") + File.separator + "BrowserDrivers" + File.separator;
		switch (BrowserName) {
		case "Firefox":

			driverPath = driverPath + "geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver(capabilities);
			break;
		case "Chrome":
			driverPath = driverPath + "chromedriver.exe";
			System.setProperty("webdriver.chrome.driver",driverPath);
			driver = new ChromeDriver(capabilities);
			break;

		default:
			System.out.println("browser is null, Launching Firefox as browser as a default browser..");
			driverPath = driverPath + "geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver(capabilities);
		}
		return driver;
	}

}
