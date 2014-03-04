package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import models.NewFormat;
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

public class RegistryHandler extends DefaultHandler {
	private PrintWriter out;
	private int recordCount, regNumCount, orgNameCount, companiesHouseCount,
			postcodeCount, countryCount, foiCount, startDateCount,
			endDateCount, exemptFlagCount, tradingNameCount, ukContactCount,
			subjectAccessCount, natureOfWorkCount, newBlobCount, oldBlobCount,
			neitherBlobCount;
	private static MongoClientURI dbURI; 
	private MongoClient client;
	private static DB database;
	private static DBCollection registry;	
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
	private String address = "";
	private DataController dataController;

	public RegistryHandler() throws IOException {
		out = new PrintWriter(new BufferedWriter(new FileWriter("files/other/stats.txt")));
		dbURI = new MongoClientURI("mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
		client = new MongoClient(dbURI);
		//client = new MongoClient("localhost",27017);
		//database = client.getDB("dataControllers");
		database = client.getDB(dbURI.getDatabase());
		registry = database.getCollection("registry");
		registry.drop();

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName.toUpperCase()) {
		case "REGISTRATION":
			type = REGISTRATION;
			break;
		case "RECORD":
			type = RECORD;
			address = "";
			dataController = new DataController();
			recordCount++;
			break;
		case "REGISTRATION_NUMBER":
			type = REGISTRATION_NUMBER;
			regNumCount++;
			break;
		case "ORGANISATION_NAME":
			type = ORGANISATION_NAME;
			orgNameCount++;
			break;
		case "COMPANIES_HOUSE_NUMBER":
			type = COMPANIES_HOUSE_NUMBER;
			companiesHouseCount++;
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
			postcodeCount++;
			type = POSTCODE;
			break;
		case "ORGANISATION_COUNTRY":
			type = COUNTRY;
			countryCount++;
			break;
		case "FREEDOM_OF_INFORMATION_FLAG":
			type = FOI;
			foiCount++;
			break;
		case "START_DATE_OF_REGISTRATION":
			type = START_DATE;
			startDateCount++;
			break;
		case "END_DATE_OF_REGISTRATION":
			type = END_DATE;
			endDateCount++;
			break;
		case "EXEMPT_PROCESSING_FLAG":
			type = EXEMPT_FLAG;
			exemptFlagCount++;
			break;
		case "TRADING_NAMES":
			type = TRADING_NAME;
			tradingNameCount++;
			break;
		case "CONTACT_IN_UK_C1":
			ukContactCount++;
			type = UK_CONTACT;
			break;
		case "SUBJECT_ACCESS_CONTACT_C2":
			type = SUBJECT_ACCESS_CONTACT;
			subjectAccessCount++;
			break;
		case "NATURE_OF_WORK_DESCRIPTION":
			natureOfWorkCount++;
			type = NATURE_OF_WORK;
			break;
		default:
			break;
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		switch (qName.toUpperCase()) {
		case "REGISTRATION":
			out.println("Register statistics for 01/2014" + "\nRecords : "
					+ recordCount + "\nRegistration Numbers : " + regNumCount
					+ "\nOrganisation Names : " + orgNameCount
					+ "\nCompanies House Numbers : " + companiesHouseCount
					+ "\nPostcodes : " + postcodeCount + "\nCountries : "
					+ countryCount + "\nFOI Flags : " + foiCount
					+ "\nStart Dates : " + startDateCount + "\nEnd Dates : "
					+ endDateCount + "\nExempt Flags : " + exemptFlagCount
					+ "\nTrading Names : " + tradingNameCount
					+ "\nUK Contact Flags : " + ukContactCount
					+ "\nSubject Access Flags : " + subjectAccessCount
					+ "\nNature of Work Descriptions : " + natureOfWorkCount
					+ "\nOld Data Formats (Purpose 1...) : " + oldBlobCount
					+ "\nNew Data Formats (Nature of work...) : "
					+ newBlobCount + "\nNeither Format : " + neitherBlobCount);
			out.close();
			client.close();
			System.out.println("done!");
			break;
		case "RECORD":			
			Gson gson = new Gson();
			BasicDBObject document = (BasicDBObject)JSON.parse(gson.toJson(dataController));
			registry.insert(document);
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
			address += new String(ch, start, length).trim();
			type = 0;
			break;
		case ADDRESS_2:
			address += ", " + new String(ch, start, length).trim();
			type = 0;
			break;
		case ADDRESS_3:
			address += ", " + new String(ch, start, length).trim();
			type = 0;
			break;
		case ADDRESS_4:
			address += ", " + new String(ch, start, length).trim();
			type = 0;
			break;
		case ADDRESS_5:
			address += ", " + new String(ch, start, length).trim();
			type = 0;
			break;
		case POSTCODE:
			dataController.setAddress(address);
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
			doStuff(new String(ch, start, length));
			type = 0;
			break;
		default:
			break;
		}
	}

	public void doStuff(String html) {
		String heading = "none found";
		ArrayList<String> list = stripTags(html);
		if (list.size() > 0) {
			heading = list.get(0);
		}
		if (heading.contains("Nature")) {
			newBlobCount++;
			dataController.setFormat("new");
			newFormat(list);
		} else if (heading.contains("Purpose")) {
			oldBlobCount++;
			dataController.setFormat("old");
			oldFormat(list);
			dataController.convertOldFormatToNewFormat();
		} else {
			neitherBlobCount++;
			dataController.setFormat("neither");
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
				if (index + 1 < list.size()) {
					while (!list.get(index + 1).toLowerCase()
							.contains("purpose") || index + 1 < list.size()) {
						transfer += " " + list.get(index).toLowerCase();
						if (index + 1 < list.size()) {
							break;
						}else{
							index++;
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
		int index = 0;
		NewFormat newFormat = new NewFormat();
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
						.contains("type/classes of information")) {
					dataPurpose = list.get(index);
					//listItems.add(dataPurpose);
					newFormat.addPurpose(dataPurpose);
				} else {
					index += 1;
					while (!list.get(index).toLowerCase()
							.contains("type/classes of information")) {
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
					.contains("type/classes of information")) {
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
		dataController.setNewFormat(newFormat);
	}

	public ArrayList<String> stripTags(String text) {
		boolean blob = false;
		StringBuilder sb = new StringBuilder();
		ArrayList<String> listOfStrings = new ArrayList<String>();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '<') {
				blob = true;
				if (sb.length() > 3 && !(sb.toString().equals("&nbsp;"))) {
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
}
