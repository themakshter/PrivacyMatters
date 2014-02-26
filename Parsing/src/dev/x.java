package dev;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class x {
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		File input = new File("files/natureOfWorkDescriptions/nature_of_work_description_1.html");
		String html = readFile(input.toString());
		parseNatureOfWork(html);
		long end = System.currentTimeMillis();
		long timeTaken = (end - start)/1000;
		System.out.println("Time taken : " + (timeTaken) + "seconds");
	}
	
	public static void oldFormat(Document doc) {
		System.out.println("Purposes");
		Elements paragraphs = doc.getElementsByTag("p");
		int index = 0;
		while (index < paragraphs.size()) {
			String text = paragraphs.get(index).text();
			if (text.contains("Purpose")
					&& !(text.equals("Purpose Description"))) {
				index += 1;
				System.out.println("\tPurpose : "
						+ paragraphs.get(index).text());
				index += 2;
				System.out.println("\t\tDescription : "
						+ paragraphs.get(index).text());
				index += 1;
				text = paragraphs.get(index).text();
				if (text.toLowerCase().contains("further description")) {
					index += 1;
					System.out.println("\t\tFurther description : "
							+ paragraphs.get(index).text().toLowerCase());
					index += 2;
				} else {
					index += 1;
				}
				System.out.println("\t\tData subjects : "
						+ paragraphs.get(index).text());
				index += 1;
				text = paragraphs.get(index).text();
				if (text.equals("Data Classes are:")) {
					index += 1;
				} else {
					System.out.println("\t\tFurther subjects : "
							+ paragraphs.get(index).text().toLowerCase());
					index += 2;
				}
				System.out.println("\t\tData classes : "
						+ paragraphs.get(index).text());
				index += 1;
				text = paragraphs.get(index).text();
				if (text.contains("Disclosures")) {
					index += 1;
				} else {
					System.out.println("\t\tFurther classes : "
							+ paragraphs.get(index).text().toLowerCase());
					index += 2;
				}
				text = paragraphs.get(index + 1).text();
				if (!text.equals("Transfers:")) {
					System.out.println("\t\tFurther disclosees : "
							+ paragraphs.get(index).text().toLowerCase());
					index += 1;
				}
				System.out.println("\t\tDisclosees : "
						+ paragraphs.get(index).text());
				index += 2;
				System.out.println("\t\tTransfers : "
						+ paragraphs.get(index).text());
			}
			index += 1;
		}
	}
	
	public static void oldFormat2(Document doc){
		System.out.println("Purposes");
		String tab = "\t";
		Elements paragraphs = doc.getElementsByTag("p");
		int index = 0;
		while(index < paragraphs.size()){
			String text,purpose,description,furtherDescription,subjects,classes,disclosees,transfers;
			//Purpose name
			text = paragraphs.get(index).text();
			String[] s = text.split(" ");
			if(s[0].equals("Purpose") && !s[1].equals("Description:") ){
				purpose =tab + "Purpose : ";
				index+=1;
				purpose += paragraphs.get(index).text();
				System.out.println(purpose);
			}

			//Purpose Description
			if(paragraphs.get(index).text().contains("Purpose Description")){
				index+=1;
				description = tab + "Description : ";
				description += paragraphs.get(index).text();
				System.out.println(description);
			}
			
			//Further Description
			if(paragraphs.get(index).text().toLowerCase().contains("further description")){
				index+=1;
				
			}
			//Data subjects
			
			//Data classes
			
			//Disclosees
			
			//Transfers
			
			
			index++;
		}
	}
	
	public static void parseNatureOfWork(String html) {
		ArrayList<String> list = stripTags(html);
		String heading = list.get(0);
		if (heading.contains("Nature")) {
			newFormat(list);
		} else if (heading.contains("Purpose")) {
			oldFormat3(list);
		}
	}

	
	
	public static void oldFormat3(ArrayList<String> list){
		System.out.println("Purposes");
		String tab = "\t";
		String purpose,description,furtherDescription,subjects,classes,disclosees,transfers;
		int index = 0;
		while(index < list.size()){
			String text = list.get(index);
			String[] s = text.split(" ");
			
			//Purpose
			if(s[0].equals("Purpose") && !s[1].equals("Description:")){
				index+=1;
				purpose = tab + "Purpose : ";
				purpose += list.get(index);
				System.out.println(purpose);
			}
			
			//Description
			if(text.equals("Purpose Description:")){
				index+=1;
				description = tab + tab + "Description : ";
				description += list.get(index);
				System.out.println(description);
			}
				
			//Further description
			if(text.toLowerCase().contains("further description")){
				index+=1;
				furtherDescription = tab + tab + "Further description : ";
				while(!list.get(index+1).toLowerCase().contains("data subjects are")){
					furtherDescription += list.get(index).toLowerCase() + " ";
					index++;
				}
				System.out.println(furtherDescription);
			}
				
			//Data Subjects
			if(text.toLowerCase().contains("data subjects are")){
				subjects =  tab + tab + "Subjects : ";
				index+=1;
				while(!list.get(index+1).toLowerCase().contains("data classes are")){
					subjects += list.get(index).toLowerCase() + ", ";
					index++;
				}
				System.out.println(subjects);
			}
			//Data Classes
			if(text.toLowerCase().contains("data classes are")){
				classes =  tab + tab + "Classes : ";
				index+=1;
				while(!list.get(index+1).toLowerCase().contains("disclosures")){
					classes += list.get(index).toLowerCase() + ", ";
					index++;
				}
				System.out.println(classes);
			}
						
			//Disclosees
			if(text.toLowerCase().contains("disclosures")){
				disclosees =  tab + tab + "Disclosees : ";
				index+=1;
				while(!list.get(index+1).toLowerCase().contains("transfer")){
					disclosees += list.get(index).toLowerCase() + ", ";
					index++;
				}
				System.out.println(disclosees);
			}
			
			//Transfers
			if(text.contains("Transfers")){
				index+=1;
				transfers =  tab + tab + "Transfers : " + list.get(index).toLowerCase();
				if(index +1 < list.size()){
					while(!list.get(index+1).toLowerCase().contains("purpose")){
						transfers += " " + list.get(index).toLowerCase();
						index++;
					}
				}
				System.out.println(transfers);
			}
			index++;
		}
	}

	public static void newFormat(ArrayList<String> list) {
		int index = 0;
		System.out.println(list.get(index));
		index++;
		while (index < list.size()) {
			String purposes, classes, subjects, disclosees, space;

			// Reasons/purpose for processing
			if (list.get(index).toLowerCase()
					.contains("reasons/purposes for processing")) {
				purposes = "Purpose : ";
				index += 1;
				if (list.get(index + 1).toLowerCase()
						.contains("type/classes of information")) {
					purposes += list.get(index);
				} else {
					index += 1;
					space = ", ";
					while (!list.get(index).toLowerCase()
							.contains("type/classes of information")) {
						purposes += list.get(index) + space;
						index++;
					}
				}
				purposes = purposes.trim();
				System.out.println(purposes);
			}
			// Type/classes of information processed
			if (list.get(index).toLowerCase()
					.contains("type/classes of information")) {
				classes = "Data classes : ";
				index += 1;
				if (list.get(index + 1).contains(
						"information is processed about")) { // only one line
					classes += list.get(index);
				} else {
					index += 1;
					space = ", ";
					while (!list.get(index).toLowerCase().contains("information is processed about")) {
						if (list.get(index).contains("sensitive classes")) {
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
				if (list.get(index + 1).contains(
						"information may be shared with")) { // only one line
					subjects += list.get(index);
				} else {
					space = ", ";
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("information may be shared with")) {
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
					index += 1;
					if (list.get(index).contains("necessary or required")) {
						index += 1;
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
	}
	
	public static void newFormat2(ArrayList<String> list){
		int index = 0;
		String[] headings = {"reasons/purposes for processing","type/classes of information","information is processed about","information may be shared with","transfers"};
		System.out.println(list.get(index));
		index++;
		while (index < list.size()) {
			String purposes, classes, subjects, disclosees, space;

			// Reasons/purpose for processing
			if (list.get(index).toLowerCase()
					.contains("reasons/purposes for processing")) {
				purposes = "Purpose : ";
				index += 1;
				if (list.get(index + 1).toLowerCase()
						.contains("type/classes of information")) {
					purposes += list.get(index);
				} else {
					index += 1;
					space = ", ";
					while (!list.get(index).toLowerCase()
							.contains("type/classes of information")) {
						purposes += list.get(index) + space;
						index++;
					}
				}
				purposes = purposes.trim();
				System.out.println(purposes);
			}
			// Type/classes of information processed
			if (list.get(index).toLowerCase()
					.contains("type/classes of information")) {
				classes = "Data classes : ";
				index += 1;
				if (list.get(index + 1).contains(
						"information is processed about")) { // only one line
					classes += list.get(index);
				} else {
					index += 1;
					space = ", ";
					while (!list.get(index).toLowerCase().contains("information is processed about")) {
						if (list.get(index).contains("sensitive classes")) {
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
				if (list.get(index + 1).contains(
						"information may be shared with")) { // only one line
					subjects += list.get(index);
				} else {
					space = ", ";
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("information may be shared with")) {
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
					index += 1;
					if (list.get(index).contains("necessary or required")) {
						index += 1;
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
	}

	public static void checkHeading(){
		
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
//		for (int i = 0; i < listOfStrings.size(); i++) {
//			System.out.println(i + "\t: " + listOfStrings.get(i));
//		}
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
