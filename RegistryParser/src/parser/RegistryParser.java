package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import models.NewFormat;
import models.OtherPurpose;
import models.Purpose;
import models.DataController;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public class RegistryParser extends DefaultHandler {
	// Writer for some counts
	private PrintWriter out;

	// MongoDB variables
	private static MongoClientURI dbURI;
	private MongoClient client;
	private DB database;
	private DBCollection collection;

	// Other
	private DataController dataController;
	private Gson gson;
	int count;
	
	// Tags for parser
	private int type = 0;
	private static final int REGISTRATION_NUMBER = 1;
	private static final int ORGANISATION_NAME = 2;
	private static final int COMPANIES_HOUSE_NUMBER = 3;
	private static final int ADDRESS_1 = 4;
	private static final int ADDRESS_2 = 5;
	private static final int ADDRESS_3 = 6;
	private static final int ADDRESS_4 = 7;
	private static final int ADDRESS_5 = 8;
	private static final int POSTCODE = 9;
	private static final int COUNTRY = 10;
	private static final int FOI = 11;
	private static final int START_DATE = 12;
	private static final int END_DATE = 13;
	private static final int EXEMPT_FLAG = 14;
	private static final int TRADING_NAME = 15;
	private static final int UK_CONTACT = 17;
	private static final int SUBJECT_ACCESS_CONTACT = 18;
	private static final int NATURE_OF_WORK = 19;
	private static final int REGISTRATION = 20;
	private static final int RECORD = 21;

	public RegistryParser() throws IOException {
		gson = new Gson();
		out = new PrintWriter(new BufferedWriter(new FileWriter("files/other/Errors.txt")));
		String uri = new BufferedReader(new FileReader("files/other/connection.txt")).readLine();
		dbURI = new MongoClientURI(uri);
		client = new MongoClient(dbURI);
		// client = new MongoClient("localhost",27017);
		// database = client.getDB("dataControllers");
		database = client.getDB(dbURI.getDatabase());
		collection = database.getCollection("registry");
		collection.drop();

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName.toUpperCase()) {
		case "REGISTRATION":
			type = REGISTRATION;
			System.out.println("adding records...");
			break;
		case "RECORD":
			type = RECORD;
			count++;
			dataController = new DataController();
			break;
		case "REGISTRATION_NUMBER":
			type = REGISTRATION_NUMBER;
			break;
		case "ORGANISATION_NAME":
			type = ORGANISATION_NAME;
			break;
		case "COMPANIES_HOUSE_NUMBER":
			type = COMPANIES_HOUSE_NUMBER;
			break;
		case "ORGANISATION_ADDRESS_LINE_1":
			type = ADDRESS_1;
			break;
		case "ORGANISATION_ADDRESS_LINE_2":
			type = ADDRESS_2;
			break;
		case "ORGANISATION_ADDRESS_LINE_3":
			type = ADDRESS_3;
			break;
		case "ORGANISATION_ADDRESS_LINE_4":
			type = ADDRESS_4;
			break;
		case "ORGANISATION_ADDRESS_LINE_5":
			type = ADDRESS_5;
			break;
		case "ORGANISATION_POSTCODE":
			type = POSTCODE;
			break;
		case "ORGANISATION_COUNTRY":
			type = COUNTRY;
			break;
		case "FREEDOM_OF_INFORMATION_FLAG":
			type = FOI;
			break;
		case "START_DATE_OF_REGISTRATION":
			type = START_DATE;
			break;
		case "END_DATE_OF_REGISTRATION":
			type = END_DATE;
			break;
		case "EXEMPT_PROCESSING_FLAG":
			type = EXEMPT_FLAG;
			break;
		case "TRADING_NAMES":
			type = TRADING_NAME;
			break;
		case "CONTACT_IN_UK_C1":
			type = UK_CONTACT;
			break;
		case "SUBJECT_ACCESS_CONTACT_C2":
			type = SUBJECT_ACCESS_CONTACT;
			break;
		case "NATURE_OF_WORK_DESCRIPTION":
			type = NATURE_OF_WORK;
			break;
		default:
			break;
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		BasicDBObject document;
		switch (qName.toUpperCase()) {
		case "REGISTRATION":
			client.close();
			out.close();
			System.out.println("Registry filled!");
			break;
		case "RECORD":
			document = (BasicDBObject) JSON.parse(gson.toJson(dataController));
			collection.insert(document);
			System.out.println(count);
		default:
			break;

		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		switch (type) {
		case REGISTRATION_NUMBER:
			dataController.setRegistrationNumber(new String(ch, start, length));
			type = 0;
			break;
		case ORGANISATION_NAME:
			dataController.setOrganisationName(new String(ch, start, length));
			type = 0;
			break;
		case COMPANIES_HOUSE_NUMBER:
			dataController
					.setCompaniesHouseNumber(new String(ch, start, length));
			type = 0;
			break;
		case ADDRESS_1:
			dataController.addAdressLine(new String(ch, start, length).trim());
			type = 0;
			break;
		case ADDRESS_2:
			dataController.addAdressLine(new String(ch, start, length).trim());
			type = 0;
			break;
		case ADDRESS_3:
			dataController.addAdressLine(new String(ch, start, length).trim());
			type = 0;
			break;
		case ADDRESS_4:
			dataController.addAdressLine(new String(ch, start, length).trim());
			type = 0;
			break;
		case ADDRESS_5:
			dataController.addAdressLine(new String(ch, start, length).trim());
			type = 0;
			break;
		case POSTCODE:
			dataController.setPostcode(new String(ch, start, length));
			type = 0;
			break;
		case COUNTRY:
			dataController.setCountry(new String(ch, start, length));
			type = 0;
			break;
		case FOI:
			dataController.setFoiFlag(new String(ch, start, length));
			type = 0;
			break;
		case START_DATE:
			dataController.setStartDate(new String(ch, start, length));
			type = 0;
			break;
		case END_DATE:
			dataController.setEndDate(new String(ch, start, length));
			type = 0;
			break;
		case EXEMPT_FLAG:
			dataController.setExemptFlag(new String(ch, start, length));
			type = 0;
			break;
		case TRADING_NAME:
			dataController.setTradingName(new String(ch, start, length));
			type = 0;
			break;
		case UK_CONTACT:
			dataController.setUkContact(new String(ch, start, length));
			type = 0;
			break;
		case SUBJECT_ACCESS_CONTACT:
			dataController.setSubjectAccess(new String(ch, start, length));
			type = 0;
			break;
		case NATURE_OF_WORK:
			parseWorkDescription(new String(ch, start, length));
			type = 0;
			break;
		default:
			break;
		}
	}

	public void parseWorkDescription(String html) {
		String heading = "none found";
		ArrayList<String> list = stripTags(html);
		if (list.size() > 0) {
			heading = list.get(0);
		}
		try {
			if (heading.contains("Nature")) {
				dataController.setFormat("new");
				newFormat(list);
			} else if (heading.contains("Purpose")) {
				dataController.setFormat("old");
				oldFormat(list);
				dataController.convertOldFormatToNewFormat();
			} else {
				dataController.setFormat("neither");
			}
		} catch (Exception e) {
			String format = dataController.getFormat();
			if (format.equals("old")) {
			} else if (format.equals("new")) {
			}
			out.println(html);
		}
	}

	public void oldFormat(ArrayList<String> list) {
		ArrayList<Purpose> oldFormat = new ArrayList<Purpose>();
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
			if (text.toLowerCase().contains("disclosures")) {
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
		dataController.setOldFormat(oldFormat);
	}

	public void newFormat(ArrayList<String> list) {
		String[] headings = { "description of processing",
				"classes of information processed",
				"information is processed about",
				"information may be shared with",
				"reasons/purposes for processing", "transfer",
				"crime prevention", "and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research" };
		String[] otherPurposes = { "crime prevention", "and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research" };
		int index = 0;
		NewFormat newFormat = new NewFormat();
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
			else if (list.get(index).toLowerCase()
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
			else if (list.get(index).toLowerCase()
					.contains("classes of information processed")) {
				boolean sensitive = false;
				dataClass = "";
				index += 1;
				if (headingsContain(list.get(index + 1), headings)) { // only
																		// one
																		// line
					dataClass = list.get(index);
				} else {
					if (!list.get(index + 1).contains("sensitive classes")) {
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
			else if (list.get(index).contains("information is processed about")) {
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
			else if (list.get(index).contains("information may be shared with")) {
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
			}// other purposes
			else if (headingsContain(list.get(index), otherPurposes)
					&& list.get(index).split(" ").length < 10) {
				otherPurpose = new OtherPurpose();
				otherPurpose.setPurpose(list.get(index));
				index += 1;
				String statement = "";
				while (!headingsContain(list.get(index), headings) || list.get(index).split(" ").length > 10) {
					statement += list.get(index);
					index++;
				}
				otherPurpose.setStatement(statement);
				newFormat.addOtherPurpose(otherPurpose);
			}// Transfer
			else if (list.get(index).contains("Transfers")) {
				transfer = "";
				index += 1;
				transfer = list.get(index).toLowerCase();
				newFormat.setTransfers(transfer);
			}else{
				index++;
			}

		}

		dataController.setNewFormat(newFormat);
	}

	public boolean headingsContain(String text, String[] headings) {
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

	public ArrayList<String> stripTags(String text) {
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
}
