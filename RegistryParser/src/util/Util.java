package util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Utility class to contain generic useful methods for the classes
 * 
 * @author Mohammad Ali
 * 
 */
public class Util {

	/**
	 * Calculates median of the list of numbers
	 * 
	 * @param list
	 *            ArrayList of numbers to calculate median of
	 * @return median value of the list
	 */
	public static int calculateMedian(ArrayList<Integer> list) {
		int median = 0;
		// sort the list
		Collections.sort(list);
		// even number in list
		if (list.size() % 2 == 0) {
			median = list.size() / 2;
			median = (list.get(median - 1) + list.get(median)) / 2;
		} else { // odd number
			median = Math.round(list.size() / 2);
			median = list.get(median);
		}
		return median;
	}

}
