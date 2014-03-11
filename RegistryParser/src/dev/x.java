package dev;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import models.NewFormat;
import models.Purpose;



public class x {
	private static NewFormat newFormat;
	private static ArrayList<Purpose> oldFormat = new ArrayList<Purpose>();
	public static void main(String[] args) throws IOException {
		File input = new File("files/natureOfWorkDescriptions/nature_of_work_description_2.html");
		String html = readFile(input.toString());
		doStuff(html);
		System.out.println("done");
	}
	
	public static void doStuff(String html) {
		String heading = "none found";
		Gson gson = new Gson();
		ArrayList<String> list = stripTags(html);
		if (list.size() > 0) {
			heading = list.get(0);
		}
		if (heading.contains("Nature")) {
			System.out.println("new format");
			newFormat(list);
			System.out.println(gson.toJson(newFormat));
		} else if (heading.contains("Purpose")) {
			System.out.println("old format");
			oldFormat(list);
			System.out.println(gson.toJson(oldFormat));
		}else{
			System.out.println("neither");
		}
	}
	
	
	public static void oldFormat(ArrayList<String> list) {
		oldFormat = new ArrayList<Purpose>();
		Purpose oldFormatPurpose = new Purpose();
		String purpose, description, furtherDescription, dataSubject, dataClass, dataDisclosee, transfer;
		int index = 0;
		while (index < list.size()) {
			String text = list.get(index);
			String[] s = text.split(" ");
			// Purpose
			if (s[0].equals("Purpose") && !s[1].equals("Description:")) {
				oldFormatPurpose = new Purpose();
				index += 1;
				purpose = list.get(index);
				oldFormatPurpose.setPurpose(purpose);
			}

			// Description
			if (text.equals("Purpose Description:")) {
				index += 1;
				description = list.get(index);
				oldFormatPurpose.setDescription(description);
			}

			// Further description
			if (text.toLowerCase().contains("further description")) {
				index += 1;
				furtherDescription = "";
				while (!list.get(index + 1).toLowerCase()
						.contains("data subjects are")) {
					furtherDescription += list.get(index).toLowerCase() + " ";
					index++;
				}
				oldFormatPurpose.setFurtherDescription(furtherDescription);
			}

			// Data Subjects
			if (text.toLowerCase().contains("data subjects are")) {
				dataSubject = "";
				index += 1;
				while (!list.get(index + 1).toLowerCase()
						.contains("data classes are")) {
					dataSubject = list.get(index).toLowerCase();
					oldFormatPurpose.addDataSubject(dataSubject);
					index++;
				}
			}
			// Data Classes
			if (text.toLowerCase().contains("data classes are")) {
				dataClass = "";
				index += 1;
				while (!list.get(index + 1).toLowerCase()
						.contains("disclosures")) {
					dataClass = list.get(index).toLowerCase();
					oldFormatPurpose.addDataClass(dataClass);
					index++;
				}
			}

			// Disclosees
			if (text.toLowerCase().contains("sources (s) and disclosures")) {
				dataDisclosee = "";
				index += 1;
				while (!list.get(index + 1).toLowerCase().contains("transfer")) {
					dataDisclosee = list.get(index).toLowerCase();
					oldFormatPurpose.addDataDisclosee(dataDisclosee);
					index++;
				}
			}

			// Transfers
			if (text.contains("Transfers")) {
				index += 1;
				transfer = "" + list.get(index).toLowerCase();
				if (index + 1 <= (list.size() - 1)) {
					while (!list.get(index + 1).toLowerCase()
							.contains("purpose")) {
						transfer += " " + list.get(index).toLowerCase();
						index++;
						if (index >= list.size() - 1) {
							transfer += " " + list.get(index).toLowerCase();
							break;
						}
					}
				}
				oldFormatPurpose.setTransfers(transfer);
				oldFormat.add(oldFormatPurpose);
			}
			index++;
		}
	}
	
	public static void newFormat(ArrayList<String> list) {
		int index = 0;
		newFormat = new NewFormat();
		//newFormat.setNatureOfWork(list.get(index));
		while (index < list.size()) {
			String dataPurpose, dataClass, dataSubject, dataDisclosee, transfer;
			
			if(list.get(index).contains("Nature")){
				String natureOfWork = "";
				while(!list.get(index).toLowerCase()
					.contains("description ")){
					natureOfWork += list.get(index);
					index++;
				}
				newFormat.setNatureOfWork(natureOfWork);
			}
			
			// Reasons/purpose for processing
			if (list.get(index).toLowerCase()
					.contains("reasons/purposes for processing")) {
				//listItems = new ArrayList<String>();
				dataPurpose = "";
				index += 1;
				if (list.get(index + 1).toLowerCase()
						.contains("classes of information processed")) {
					dataPurpose = list.get(index);
					//listItems.add(dataPurpose);
					newFormat.addPurpose(dataPurpose);
				} else {
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("classes of information processed")) {
						dataPurpose = list.get(index);
						//listItems.add(dataPurpose);
						newFormat.addPurpose(dataPurpose);
						index++;
					}
				}
				//newFormat.setPurposes(listItems);
			}
			// Type/classes of information processed
			if (list.get(index).toLowerCase()
					.contains("classes of information processed")) {
				boolean sensitive = false;
				dataClass = "";
				index += 1;
				if (list.get(index + 1).contains(
						"information is processed about")) { // only one line
					dataClass = list.get(index);
				} else {
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("information is processed about")) {
						if (list.get(index).contains("sensitive classes")) {
						sensitive = true;
						} else {
							dataClass = list.get(index);
							if(sensitive){
								newFormat.addSensitiveData(dataClass);
							}else{
								newFormat.addDataClass(dataClass);
							}
						}
						index++;
					}
				}
			}
			// Who the information is processed about
			if (list.get(index).contains("information is processed about")) {
				//listItems = new ArrayList<String>();
				dataSubject = "";
				index += 1;
				if (list.get(index + 1).contains(
						"information may be shared with")) { // only one line
					dataSubject = list.get(index);
					newFormat.addDataSubject(dataSubject);
				} else {
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("information may be shared with")) {
						dataSubject = list.get(index);
						newFormat.addDataSubject(dataSubject);
						index++;
					}
				}
			}

			// Who the information may be shared with
			if (list.get(index).contains("information may be shared with")) {
				dataDisclosee = "";
				index += 1;
				if (list.get(index + 1).contains("Transfers")) {
					dataDisclosee = list.get(index);
					newFormat.addDataDisclosee(dataDisclosee);
				} else {
					index += 1;
					if (list.get(index).contains("necessary or required")) {
						index += 1;
					}
					while (!list.get(index).contains("Transfers")) {
						dataDisclosee = list.get(index);
						newFormat.addDataDisclosee(dataDisclosee);
						index++;
					}
				}
			}

			// Transfer
			if (list.get(index).contains("Transfers")) {
				transfer = "";
				index += 1;
				transfer = list.get(index).toLowerCase();
				newFormat.setTransfers(transfer);
			}
			index++;
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
