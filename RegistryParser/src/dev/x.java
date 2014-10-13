package dev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import models.NewFormat;
import models.OtherPurpose;
import models.Purpose;

public class x {
	private static NewFormat newFormat;
	private static ArrayList<Purpose> oldFormat = new ArrayList<Purpose>();

	public static void main(String[] args) throws IOException {
//		File input = new File(
//				"files/natureOfWorkDescriptions/nature_of_work_description_1.html");
//		String html = readFile(input.toString());
//		doStuff(html);
//		Gson gson = new Gson();
//		HashMap<String,HashSet<String>> x = new HashMap<String,HashSet<String>>();
//		x.put("hello",new HashSet<String>());
//		x.get("hello").add("h");
//		x.get("hello").add("y");
//		x.get("hello").add("z");
//		System.out.println(gson.toJson(x));
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		list.add(3);
//		list.add(3);
//		list.add(1);
//		list.add(0);
//		list.add(9);
//		list.add(5);
//		System.out.println("normal positions");
//		for(Integer i: list){
//			System.out.println(i);
//		}
//		Collections.sort(list);
//		System.out.println("soreted");
//		for(Integer i:list){
//			System.out.println(i);
//		}
//		
//		if(list.size() % 2 ==0){
//			int middle = list.size()/2;
//			middle = (list.get(middle - 1) + list.get(middle))/2;
//			System.out.println("median in this " + list.get(middle));
//		}else{	//odd number
//			int middle = Math.round(list.size() / 2);
//			System.out.println("median in this " + list.get(middle));
//		}
//		
//		
		//System.out.println("done");
	}

	public static void doStuff(String html) {
		String heading = "none found";
		Gson gson = new Gson();
		ArrayList<String> list = stripTags(html);
		if (list.size() > 0) {
			heading = list.get(0);
		}
		if (heading.contains("Nature")) {
			newFormat(list);
			System.out.println(gson.toJson(newFormat));
		} else if (heading.contains("Purpose")) {
			System.out.println("old format");
			oldFormat(list);
			System.out.println(gson.toJson(oldFormat));
		} else {
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
		String[] headings = { "description of processing",
				"classes of information processed", "information is processed about",
				"information may be shared with",
				"reasons/purposes for processing", "transfer",
				"crime prevention", "consulting and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research" };
		String[] otherPurposes = {"crime prevention", "consulting and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research"};
		int index = 0;
		newFormat = new NewFormat();
		OtherPurpose otherPurpose;
		while (index < list.size()) {
			String dataPurpose, dataClass, dataSubject, dataDisclosee, transfer;

			if (list.get(index).contains("Nature")) {
				String natureOfWork = "";
				while (!headingsContain(list.get(index), headings)) {
					natureOfWork += list.get(index);
					index++;
				}
				newFormat.setNatureOfWork(natureOfWork);
			}

			// Reasons/purpose for processing
			if (list.get(index).toLowerCase()
					.contains("reasons/purposes for processing")) {
				dataPurpose = "";
				index += 1;
				if (headingsContain(list.get(index + 1), headings)) {
					dataPurpose = list.get(index);
					newFormat.addPurpose(dataPurpose);
				} else {
					index += 1;
					while (!headingsContain(list.get(index), headings)) {
						dataPurpose = list.get(index);
						newFormat.addPurpose(dataPurpose);
						index++;
					}
				}
			}
			// Type/classes of information processed
			if (list.get(index).toLowerCase()
					.contains("classes of information processed")) {
				boolean sensitive = false;
				dataClass = "";
				index += 1;
				if (headingsContain(list.get(index + 1), headings)) { //only one line
					dataClass = list.get(index);
				} else {
					if(!list.get(index + 1).contains("sensitive classes")){
						index += 1;
					}
					while (!headingsContain(list.get(index), headings)) {
						if (list.get(index).contains("sensitive classes")) {
							sensitive = true;
						} else {
							dataClass = list.get(index);
							if (sensitive) {
								newFormat.addSensitiveData(dataClass);
							} else {
								newFormat.addDataClass(dataClass);
							}
						}
						index++;
					}
				}
			}
			// Who the information is processed about
			if (list.get(index).contains("information is processed about")) {
				// listItems = new ArrayList<String>();
				dataSubject = "";
				index += 1;
				if (headingsContain(list.get(index + 1), headings)) { // only
																		// one
																		// line
					dataSubject = list.get(index);
					newFormat.addDataSubject(dataSubject);
				} else {
					index += 1;
					while (!headingsContain(list.get(index), headings)) {
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
				if (headingsContain(list.get(index + 1), headings)) {
					dataDisclosee = list.get(index);
					newFormat.addDataDisclosee(dataDisclosee);
				} else {
					index += 1;
					if (list.get(index).contains("necessary or required")) {
						index += 1;
					}
					while (!headingsContain(list.get(index), headings)) {
						dataDisclosee = list.get(index);
						newFormat.addDataDisclosee(dataDisclosee);
						index++;
					}
				}
			}

			// Transfer
			if (list.get(index).contains("Transfers")) {
				transfer = "not given";
				index += 1;
				transfer = list.get(index).toLowerCase();
				newFormat.setTransfers(transfer);
			}
			
			// other purposes
			if (headingsContain(list.get(index), otherPurposes)) {
				otherPurpose = new OtherPurpose();
				otherPurpose.setPurpose(list.get(index));
				index += 1;
				String statement = "";
				while (!headingsContain(list.get(index), headings)) {
					statement += list.get(index);
					index++;
				}
				otherPurpose.setStatement(statement);
				newFormat.addOtherPurpose(otherPurpose);
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
				String toAdd = sb.toString().trim().replaceAll("&nbsp;", "");
				if (toAdd.length() > 1 && !(toAdd.toString().equals("&nbsp;"))) {
					listOfStrings.add(toAdd);
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

	public static boolean headingsContain(String text, String[] headings) {
		text = text.toLowerCase();
		boolean found = false;
		for (String s : headings) {
			if (text.contains(s) && !text.contains("nature")) {
				found = true;
				break;
			}
		}
		return found;
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
