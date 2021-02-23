package com.eltropy.twitterApi;

import java.util.List;
import java.util.Map;

public class SortingMethods {

	
	/**
	 * Sorting by using QuickSort
	 * @param responseList
	 * @param begin
	 * @param end
	 * @param key
	 * @return
	 */

	public static List<Map<String, Object>> getSortedList(List<Map<String, Object>> responseList, int begin, int end,
			String key) {

		if (begin < end) {
			int partitionIndex = partition(responseList, begin, end, key);

			getSortedList(responseList, begin, partitionIndex - 1, key);
			getSortedList(responseList, partitionIndex + 1, end, key);
		}
		return responseList;
	}
	
	/**
	 * 
	 * @param responseList
	 * @param begin
	 * @param end
	 * @param key
	 * @return
	 */

	private static int partition(List<Map<String, Object>> responseList, int begin, int end, String key) {
		Map<String, Object> pivot = responseList.get(end);
		int i = (begin - 1);

		for (int j = begin; j < end; j++) {
			if ((int) responseList.get(j).get(key) >= (int) pivot.get(key)) {
				i++;

				Map<String, Object> swapTemp = responseList.get(i);
				responseList.set(i, responseList.get(j));
				responseList.set(j, swapTemp);
			}
		}

		Map<String, Object> swapTemp = responseList.get(i + 1);
		responseList.set(i + 1, responseList.get(end));
		responseList.set(end, swapTemp);

		return i + 1;
	}

}
