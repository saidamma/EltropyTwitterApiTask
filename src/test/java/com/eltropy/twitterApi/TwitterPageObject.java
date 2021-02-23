package com.eltropy.twitterApi;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TwitterPageObject {

	public TwitterPageObject(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Get tweet text from UI

	@FindBy(xpath = "//div[@lang='en']")
	private WebElement tweetData;

	public String getTweetTextData() {
		return tweetData.getText();
	}

	// Get Retweet count from UI

	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/article[1]/div[1]/div[1]/div[1]/div[3]/div[4]/div[1]/div[1]/div[1]/a[1]/div[1]/span[1]/span[1]")
	private WebElement reTweetCount;

	public String getRetweetCount() {
		return reTweetCount.getText();
	}

	// Get Retweet count from UI

	@FindBy(xpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/article[1]/div[1]/div[1]/div[1]/div[3]/div[4]/div[1]/div[3]/div[1]/a[1]/div[1]/span[1]/span[1]")
	private WebElement favouriteCount;

	public String getFavouriteCount() {
		return favouriteCount.getText();
	}
	
	//Twitter Login
	
	@FindBy(name = "session[username_or_email]")
	private WebElement userName;
	
	@FindBy(name = "session[password]")
	private WebElement password;

	public void loginDetails() {
		userName.sendKeys("vineeth22528039");
		password.sendKeys("vineeth@123");
		
	}
	
	@FindBy(xpath = "//span[@class='css-901oao css-16my406 css-bfa6kz r-poiln3 r-bcqeeo r-qvutc0']")
	private WebElement loginButton;
	
	public void loginClick() {
		loginButton.click();
		
	}

	@FindBy(xpath = "//input[@type='text']")
	private WebElement searchTextField;
	
	public void serachFriend(String searchText) {
		searchTextField.sendKeys(searchText);
		searchTextField.sendKeys(Keys.ENTER);
		
	}
	@FindBy(xpath = "//span[contains(text(),'Following')]")
	private WebElement followingLink;
	
	public void followingLinkClick() {
		followingLink.click();
		
	}
	
	@FindBy(xpath = "//div[@class='css-901oao r-18jsvk2 r-18u37iz r-1q142lx r-1qd0xha r-a023e6 r-16dba41 r-ad9z0x r-bcqeeo r-qvutc0']")    
	List<WebElement> verifiedCount;
	
	public int verifiedCount() {
		return verifiedCount.size();
	}
	
	public void sleepTime(int seconds) throws InterruptedException {
		Thread.sleep(seconds*1000);
	}
}
