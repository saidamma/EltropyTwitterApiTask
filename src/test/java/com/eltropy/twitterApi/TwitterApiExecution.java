package com.eltropy.twitterApi;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.LoadOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TwitterApiExecution extends AccessTokenGeneration {

	static String excelFilePath = System.getProperty("user.dir") + File.separator + "Excelfiles";

	/**
	 * Get a Total of 100 tweets after removing re-tweets
	 */
	@Test
	public void twitterApiToGetuserTweetsList() {

		System.out.println("Twitter API to get a total of 100 tweets after removing re-tweets from the response");

		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("screen_name", "twitterapi");
		queryParamsMap.put("count", "100");
		String token = getBarerToken();

		Response response = given().auth().oauth2(token).queryParams(queryParamsMap).when()
				.get("1.1/statuses/user_timeline.json");

		String respObject = response.getBody().asString();

		HashSet tweetIdsWithOutDuplicate = new HashSet<Integer>();
		List<Long> tweetId = JsonPath.with(respObject).get("id");
		for (Long id : tweetId) {
			tweetIdsWithOutDuplicate.add(id);
		}
		System.out.println(tweetIdsWithOutDuplicate.size());
		System.out.println(tweetIdsWithOutDuplicate);

		Iterator<Integer> id = tweetIdsWithOutDuplicate.iterator();
		while (id.hasNext()) {
			Response getTweetsWithId = given().auth().oauth2(token).queryParam("id", id.next()).when()
					.get("1.1/statuses/show.json");

			System.out.println(getTweetsWithId.prettyPrint());
			System.out.println("===========");

		}

	}

	/**
	 * To fetch the top 10 tweets having most numner of re-tweets.
	 * 
	 * @throws Exception
	 */
	@Test
	public void twitterApiToGetTop10MostReTweetsCount() throws Exception {

		System.out.println("the top 10 tweets having the most number of re-tweets");

		String token = getBarerToken();
		Response response = given().auth().oauth2(token).queryParam("screen_name", "twitterapi")
				.queryParam("count", 100).when().get("1.1/statuses/user_timeline.json");

		String respObject = response.getBody().asString();

		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = mapper.readValue(respObject, new TypeReference<List<Map<String, Object>>>() {
		});

		List<Map<String, Object>> sortedResponse = SortingMethods.getSortedList(data, 0, 99, "retweet_count");
		List<Map<String, Object>> tope10Records = sortedResponse.subList(0, 10);

		List<String[]> list = new ArrayList<>();
		String[] header = { "ID", "CreatedDate", "TweetText", "FavoriteCount", "ReTweetsCount",
				"Twitter Handler name" };
		list.add(header);
		String screenName = "";
		for (int i = 0; i < tope10Records.size(); i++) {

			screenName = JsonPath.with(respObject).get("user.name[" + i + "]").toString();

			String[] result = { (tope10Records.get(i).get("id") + "-id").toString(),
					tope10Records.get(i).get("created_at").toString(), tope10Records.get(i).get("text").toString(),
					tope10Records.get(i).get("favorite_count").toString(),
					tope10Records.get(i).get("retweet_count").toString(), screenName };
			list.add(result);

		}

		try (CSVWriter writer = new CSVWriter(
				new FileWriter(excelFilePath + File.separator + "Top10MostReTweets.csv"))) {
			writer.writeAll(list);
		}

		// Opening CSV Files
		// Creating CSV LoadOptions object
		LoadOptions loadOptions = new LoadOptions(FileFormatType.CSV);
		// Creating an Workbook object with CSV file path and the loadOptions
		// object
		Workbook workbook = new Workbook(excelFilePath + File.separator + "Top10MostReTweets.csv", loadOptions);
		workbook.save(excelFilePath + File.separator + "Top10MostReTweetstestdata.xlsx", SaveFormat.XLSX);
	}

	/**
	 * 
	 * @param fileData
	 * @param fileName
	 * @throws IOException
	 */

	@Test
	/**
	 * To get the 100 friends of a user
	 * 
	 * @throws Exception
	 */
	public void twitterApitoGetfriendsOfAuser() throws Exception {
		System.out.println("Twitter API to get the 100 friends of user");

		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("screen_name", "twitterdev");
		queryParamsMap.put("include_user_entities", "true");
		queryParamsMap.put("count", "100");
		queryParamsMap.put("cursor", -1);

		String token = getBarerToken();
		Response response = given().auth().oauth2(token).queryParams(queryParamsMap).when()
				.get("1.1/friends/list.json");

		String respObject = response.getBody().asString();

		List<Long> ids = JsonPath.with(respObject).get("users.id");

		List<String[]> list = new ArrayList<>();
		String[] header = { "Id", "Name", "ScreenName" };
		list.add(header);
		for (int i = 0; i < ids.size(); i++) {

			String[] result = { JsonPath.with(respObject).get("users.id[" + i + "]") + "-id".toString(),
					JsonPath.with(respObject).get("users.name[" + i + "]").toString(),
					JsonPath.with(respObject).get("users.screen_name[" + i + "]").toString() };

			list.add(result);
		}
		try (CSVWriter writer = new CSVWriter(
				new FileWriter(excelFilePath + File.separator + "User100FriendsList.csv"))) {
			writer.writeAll(list);
		}

		// Opening CSV Files
		// Creating CSV LoadOptions object
		LoadOptions loadOptions = new LoadOptions(FileFormatType.CSV);
		// Creating an Workbook object with CSV file path and the loadOptions
		// object
		Workbook workbook = new Workbook(excelFilePath + File.separator + "User100FriendsList.csv", loadOptions);
		workbook.save(excelFilePath + File.separator + "User100FriendsListtestdata.xlsx", SaveFormat.XLSX);
	}

	/**
	 * To fetch top 10 friends having most number of followers.
	 * 
	 * @throws Exception
	 */
	@Test
	public void top10FriendsWithMaxNumberOfFollowers() throws Exception {
		System.out.println("Get the top 10 friends having the most number of followers");
		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("screen_name", "twitterdev");
		queryParamsMap.put("skip_status", "false");
		queryParamsMap.put("include_user_entities", "false");
		queryParamsMap.put("count", "100");
		queryParamsMap.put("cursor", -1);
		String token = getBarerToken();
		Response response = given().auth().oauth2(token).queryParams(queryParamsMap).when()
				.get("1.1/followers/list.json");
		String respObject = response.getBody().asString();
		JSONObject jsnobject = new JSONObject(respObject);
		org.json.JSONArray jsonArray = jsnobject.getJSONArray("users");
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = mapper.readValue(jsonArray.toString(),
				new TypeReference<List<Map<String, Object>>>() {
				});
		List<Map<String, Object>> sortedResponse = SortingMethods.getSortedList(data, 0, 99, "followers_count");
		List<Map<String, Object>> tope10Records = sortedResponse.subList(0, 10);
		List<String[]> list = new ArrayList<>();
		String[] header = { "ScreenName", "Friends_count", "followers_count" };
		list.add(header);
		for (int i = 0; i < tope10Records.size(); i++) {
			String[] result = { tope10Records.get(i).get("screen_name").toString(),
					tope10Records.get(i).get("friends_count").toString(),
					tope10Records.get(i).get("followers_count").toString() };
			list.add(result);
		}
		try (CSVWriter writer = new CSVWriter(
				new FileWriter(excelFilePath + File.separator + "Top10FriendsWithMaxNumberOfFollowers.csv"))) {
			writer.writeAll(list);
		}

		LoadOptions loadOptions = new LoadOptions(FileFormatType.CSV);
		// Creating an Workbook object with CSV file path and the loadOptions
		// object
		Workbook workbook = new Workbook(excelFilePath + File.separator + "Top10FriendsWithMaxNumberOfFollowers.csv",
				loadOptions);
		workbook.save(excelFilePath + File.separator + "Top10FriendsWithMaxNumberOfFollowersdata.xlsx",
				SaveFormat.XLSX);

	}

}
