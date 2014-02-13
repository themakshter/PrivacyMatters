import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class x {
	public static void main(String[] args) throws IOException {
		File input = new File("nature_of_work_description_1.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com./");
		//secondAttempt(doc);
		//thirdAttempt(doc);
		//fourthAttempt();
		String text = readFile(input.toString());
		fourthAttempt(text);
	}

	public static void firstAttempt(Document doc) {
		Elements paras = doc.getElementsByTag("p");
		Elements lists = doc.getElementsByTag("ul");
		Elements headings = doc.getElementsByTag("b");
		Document doc2;
		if (headings.get(0).text().substring(0, 6).equals("Nature")) {
			String[] nature_of_work = paras.get(0).text().split("-");
			System.out.println("\t" + nature_of_work[0] + " : "
					+ nature_of_work[1]);
			String[] reasons = paras.get(4).text().split(" ");
			String reasons_for_processing = "\t" + headings.get(2).text()
					+ " :";
			for (int i = 4; i < reasons.length; i++) {
				reasons_for_processing += " " + reasons[i];
			}
			System.out.println(reasons_for_processing);
			String processed = "\t" + headings.get(3).text() + " :";
			doc2 = Jsoup.parse(lists.get(0).html());
			Elements info_collected = doc2.getElementsByTag("li");
			for (int i = 0; i < info_collected.size(); i++) {
				processed += " " + info_collected.get(i).text() + ",";
			}
			doc2 = Jsoup.parse(lists.get(1).html());
			Elements sensitive_info = doc2.getElementsByTag("li");
			for (int i = 0; i < sensitive_info.size(); i++) {
				processed += " " + sensitive_info.get(i).text()
						+ "[SENSITIVE],";
			}
			processed = processed.substring(0, processed.length() - 1);
			System.out.println(processed);
			String shared_with = "\t" + headings.get(5).text() + " :";
			doc2 = Jsoup.parse(lists.get(2).html());
			Elements shared = doc2.getElementsByTag("li");
			for (int i = 0; i < shared.size(); i++) {
				shared_with += " " + shared.get(i).text() + ",";
			}
			shared_with = shared_with.substring(0, shared_with.length() - 1);
			System.out.println(shared_with);
			System.out.println("\t" + headings.get(8).text() + " : "
					+ paras.get(11).text());
		}
	}

	public static void secondAttempt(Document doc) {
		Elements paragraphs = doc.getElementsByTag("p");
		Elements headings = doc.getElementsByTag("b");
		Elements headingsv2 = doc.getElementsByTag("strong");
		
		ArrayList<String> parags = new ArrayList<String>();
		ArrayList<String> heads = new ArrayList<String>();
		int max = 0;
		for (Element e : headings) {
			int len = e.text().length();
			if (len > 1) {
				heads.add(e.text());
				if (len > max) {
					max = len;
				}
			}
		}
		for (Element e : paragraphs) {
			if (e.text().length() > max) {
				parags.add(e.text());
			}
		}
//		for (int i = 0; i < parags.size(); i++) {
//			System.out.println(i + " : " + parags.get(i) + " length : "
//					+ parags.get(i).length());
//		}
		
		for(String s : parags){
			
		}

	}
	
	public static void thirdAttempt(Document doc){
		System.out.println(doc.select("p").html());
		if(doc.text().contains("Purpose")){
			System.out.println("Purposes");
			Elements paragraphs = doc.getElementsByTag("p");
			int index = 0;
			while(index < paragraphs.size()){
				String text = paragraphs.get(index).text();
				if(text.contains("Purpose") && !(text.equals("Purpose Description"))){
					index+=1;
					System.out.println("\tPurpose : " + paragraphs.get(index).text());
					index +=2;
					System.out.println("\t\tDescription : " + paragraphs.get(index).text());
					index +=1;
					text = paragraphs.get(index).text();
					if(text.toLowerCase().contains("further description")){
						index+=1;
						System.out.println("\t\tFurther description : " + paragraphs.get(index).text().toLowerCase());
						index+=2;
					}else{
						index+=1;
					}
					System.out.println("\t\tData subjects : " + paragraphs.get(index).text());
					index+=1;
					text = paragraphs.get(index).text();
					if(text.equals("Data Classes are:")){
						index+=1;
						}else{
						System.out.println("\t\tFurther subjects : " + paragraphs.get(index).text().toLowerCase());
						index+=2;
					}
					System.out.println("\t\tData classes : " + paragraphs.get(index).text());
					index+=1;
					text = paragraphs.get(index).text();
					if(text.contains("Disclosures")){
						index+=1;
						}else{
						System.out.println("\t\tFurther classes : " + paragraphs.get(index).text().toLowerCase());
						index+=2;
					}
					text = paragraphs.get(index + 1).text();
					if(!text.equals("Transfers:")){
						System.out.println("\t\tFurther disclosees : " + paragraphs.get(index).text().toLowerCase());
						index+=1;
					}
					System.out.println("\t\tDisclosees : " + paragraphs.get(index).text());
					index +=2 ;
					System.out.println("\t\tTransfers : " + paragraphs.get(index).text());
				}
				index+=1;
			}
		}
	}
	
	public static void fourthAttempt(String text) {
		ArrayList<String> list = stripTags(text);
		int index = 0;
		if (list.get(index).contains("Nature")) {
			System.out.println(list.get(index));
			index++;
			while (index < list.size()) {				
				String purposes, classes, subjects, disclosees,space;
				
				// Reasons/purpose for processing
				if (list.get(index).toLowerCase().contains("reasons/purposes for processing")) {
					purposes = "Purpose : ";
					index += 1;
					if (list.get(index + 1).toLowerCase().contains("type/classes of information")) {
						purposes += list.get(index);
					} else {
						index+=1;
						space = ", ";
						while (!list.get(index).toLowerCase().contains("type/classes of information")) {
							purposes += list.get(index) + space;
							index++;
						}
					}
					purposes = purposes.trim();
					System.out.println(purposes);
				}
				// Type/classes of information processed
				if (list.get(index).toLowerCase().contains("type/classes of information")) {
					classes = "Data classes : ";
					index += 1;
					if (list.get(index + 1).contains("information is processed about")) { // only one line
						classes += list.get(index);
					} else {
						index += 1;
						space = ", ";
						boolean sensitive = false;
						while (!list.get(index).toLowerCase()
								.contains("information is processed about")) {
							if (list.get(index).contains("sensitive classes")) {
								sensitive = true;
								space = "[SENSITIVE], ";
							} else {
								classes += list.get(index) + space;
							}
							index++;
						}

					}
					classes = classes.trim();
					System.out.println(classes);
				}
				// Who the information is processed about
				if (list.get(index).contains("information is processed about")) {
					subjects = "Data Subjects : ";
					index += 1;
					if (list.get(index + 1).contains("information may be shared with")) { // only one line
						subjects += list.get(index);
					} else {
						space = ", ";
						index += 1;
						while (!list.get(index).toLowerCase().contains("information may be shared with")) {
							subjects += list.get(index) + space;
							index++;
						}
					}
					subjects = subjects.trim();
					System.out.println(subjects);
				}

				// Who the information may be shared with
				if (list.get(index).contains("information may be shared with")) {
					disclosees = "Data Disclosees : ";
					index += 1;
					if (list.get(index + 1).contains("Transfers")) {
						disclosees += list.get(index);
					} else {
						index+=1;
						if(list.get(index).contains("necessary or required")){
							index+=1;
						}
						space = ", ";
						while (!list.get(index).contains("Transfers")) {
							disclosees += list.get(index) + space;
							index++;
						}
					}
					disclosees = disclosees.trim();
					System.out.println(disclosees);
				}

				// Transfer
				if (list.get(index).contains("Transfers")) {
					String transfers = "Transfers : ";
					index += 1;
					transfers += list.get(index);
					System.out.println(transfers);
				}

				index++;
			}
			System.out.println();
		} else if (list.get(index).contains("Purpose")) {

		}
	}

	public static ArrayList<String> stripTags(String text) {
		boolean blob = false;
		StringBuilder sb = new StringBuilder();
		ArrayList<String> listOfStrings = new ArrayList<String>();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '<') {
				blob = true;
				if (sb.length() > 1 && !(sb.toString().equals("&nbsp;"))) {
					listOfStrings.add(sb.toString().trim());
				}
				sb = new StringBuilder();
			} else if (text.charAt(i) == '>') {
				blob = false;
			} else {
				if (!blob) {
					sb.append(text.charAt(i));
				}
			}
		}
		// for(int i = 0; i < listOfStrings.size();i++){
		// System.out.println(i + "\t: " + listOfStrings.get(i));
		// }
		return listOfStrings;
	}
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
