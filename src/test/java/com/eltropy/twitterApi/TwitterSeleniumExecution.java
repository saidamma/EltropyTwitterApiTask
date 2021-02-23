package com.eltropy.twitterApi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Test
public class TwitterSeleniumExecution {

	static WebDriver driver;
	CommonMethods basicMethods = new CommonMethods();
	static String excelFilePath = System.getProperty("user.dir") + File.separator + "Excelfiles";

	@BeforeMethod
	@Parameters("browser")
	public void init(@Optional("Firefox") String browserName) {
		driver = BrowserSetUp.browserSetup(browserName);

	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void user100FriendsListTest() throws IOException, InterruptedException {
		TwitterPageObject tPageObject = new TwitterPageObject(driver);
		List<String[]> list = new ArrayList<>();
		String[] header = { "ID", "Name", "twitterHandle", "twitterHandleUrl", "ScreenshotLink" };
		list.add(header);
		// Get total number of rows from excel sheet
		int numberOfRows = CommonMethods.getRowCount("User100FriendsListtestdata.xlsx", "Sheet1");
		for (int i = 1; i < 10; i++) {

			// Reading test data from excel sheet
			HashMap<String, String> testData = null;

			// ExcelFileName, SheetName, Row Number
			try {
				testData = CommonMethods.readDataFromExcelSheetForSpecificRow("User100FriendsListtestdata.xlsx",
						"Sheet1", i);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail();
			}

			String screenName = testData.get("ScreenName");
			String twitterUrl = "https://twitter.com/" + screenName;
			tPageObject.sleepTime(5);
			driver.get(twitterUrl);
			tPageObject.sleepTime(5);
			driver.manage().window().maximize();
			String screenshot = basicMethods.screenshotCapture(driver, "UserFriend");
			String id[] = testData.get("Id").split("-");

			String[] result = { id[0], testData.get("Name"), testData.get("ScreenName"), twitterUrl, screenshot };
			list.add(result);

		}

		File template = new File("template.html");
		String htmlTemplate = basicMethods
				.readFromInputStream(getClass().getClassLoader().getResourceAsStream("template.html"));
		htmlTemplate = htmlTemplate.replace("@@tablebody", basicMethods.buildTable(list));
		basicMethods.writeFile(htmlTemplate, excelFilePath + File.separator + "user100FriendsList.html");
	}

	@Test
	public void top10MostReTweetsTest() throws IOException, InterruptedException {
		TwitterPageObject tPageObject = new TwitterPageObject(driver);

		List<String[]> list = new ArrayList<>();
		String[] header = { "ID", "TweetText", "FavoriteCount", "ReTweetsCount", "ScreenshotsLink" };
		list.add(header);
		// Get total number of rows from excel sheet
		int numberOfRows = CommonMethods.getRowCount("Top10MostReTweetstestdata.xlsx", "Sheet1");
		for (int i = 1; i < numberOfRows; i++) {

			// Reading test data from excel sheet
			HashMap<String, String> testData = null;

			// ExcelFileName, SheetName, Row Number
			try {
				testData = CommonMethods.readDataFromExcelSheetForSpecificRow("Top10MostReTweetstestdata.xlsx",
						"Sheet1", i);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail();
			}
			String tweetid[] = testData.get("ID").split("-");
			String tweetiduri = tweetid[0];

			String navigateTweeturl = "https://twitter.com/TwitterAPI/status/" + tweetiduri;
			driver.get(navigateTweeturl);
			String screenshot = basicMethods.screenshotCapture(driver, "Retweets");
			tPageObject.sleepTime(15);
			tPageObject.sleepTime(5);

			String tweetdata = tPageObject.getTweetTextData();
			String retweetcount = tPageObject.getRetweetCount();
			String favcount = tPageObject.getFavouriteCount();

			if (testData.get("ReTweetsCount").equalsIgnoreCase(retweetcount)) {
				System.out.println("Retweet count same in UI and API cal");

			} else {
				System.out.println("Having diff count in UI and API cal");
				CommonMethods.writeDataFromExcelSheetForSpecificRow("Top10MostReTweetstestdata.xlsx", "Sheet1", i, 4,
						retweetcount);
			}

			if (testData.get("FavoriteCount").equalsIgnoreCase(favcount)) {
				System.out.println("Favourite count same in UI and API cal");
			} else {
				System.out.println("Having diff favourite count in UI and API cal");
				CommonMethods.writeDataFromExcelSheetForSpecificRow("Top10MostReTweetstestdata.xlsx", "Sheet1", i, 3,
						favcount);
			}

			if (testData.get("TweetText").equalsIgnoreCase(tweetdata)) {
				System.out.println("Tweet data same in UI and API cal");
			} else {
				System.out.println("Having diff Tweet data in UI and API cal");

			}
			String[] result = { tweetiduri, testData.get("TweetText"), testData.get("FavoriteCount"),
					testData.get("ReTweetsCount"), screenshot };
			list.add(result);
		}

		File template = new File("template.html");
		String htmlTemplate = basicMethods
				.readFromInputStream(getClass().getClassLoader().getResourceAsStream("template.html"));
		htmlTemplate = htmlTemplate.replace("@@tablebody", basicMethods.buildTable(list));
		basicMethods.writeFile(htmlTemplate, excelFilePath + File.separator + "top10MostReTweets.html");
	}

	/**
	 * 
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */

	@Test
	public void verifedAccountTest()
			throws JsonMappingException, JsonProcessingException, IOException, JSONException, InterruptedException {
		TwitterPageObject tPageObject = new TwitterPageObject(driver);

		FriendsList fl = new FriendsList();
		List<String[]> s = fl.top10FriendsWithMaxNuberOfFollowers();

		System.out.println(s.size());

		int rn = randomNumber(s.size());

		String[] t = null;
		// To select random friend each time
		if (s.size() > 0) {

			t = s.get(rn);

			System.out.println(t[0]);
		}

		driver.get("https://twitter.com/login");

		tPageObject.sleepTime(5);
		tPageObject.loginDetails();
		tPageObject.loginClick();

		driver.manage().window().maximize();
		tPageObject.sleepTime(5);
		tPageObject.serachFriend(t[0]);

		tPageObject.sleepTime(5);

		driver.findElement(By.partialLinkText(t[0])).click();

		tPageObject.sleepTime(5);

		tPageObject.followingLinkClick();

		tPageObject.sleepTime(10);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		tPageObject.sleepTime(5);
		tPageObject.sleepTime(5);

		int verifiedCount = tPageObject.verifiedCount();
		System.out.println("Count of verified accounts: " + verifiedCount);

	}

	public int randomNumber(int number) {
		Random rand = new Random();
		return rand.nextInt(number);

	}

	@AfterMethod
	public void driverQuit() {
		driver.close();
	}
}
