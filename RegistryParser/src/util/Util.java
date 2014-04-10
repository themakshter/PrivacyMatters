package util;

import java.util.ArrayList;
import java.util.Collections;

public class Util {
	
	public static int calculateMedian(ArrayList<Integer> list){
		int median = 0;
			Collections.sort(list);
			//even number in list
			if (list.size() % 2 == 0) {
				median = list.size() / 2;
				median = (list.get(median - 1) + list.get(median)) / 2;
			} else { //odd number
				median = Math.round(list.size() / 2);
				median = list.get(median);
			}
		return median;	
	}
	
	
	
	
}
