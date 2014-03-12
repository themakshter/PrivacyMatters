package parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import models.GeneralStatistics;
import models.NewFormat;
import models.OtherPurpose;
import models.Purpose;
import models.DataController;
import models.RegistryListItem;
import models.StatisticObject;

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
	private HashSet<String> natureOfWorkSet,dataPurposeSet,dataClassSet,sensitiveDataSet,dataSubjectSet,dataDiscloseeSet;
	private HashMap<String,StatisticObject> natureOfWorkMap,dataPurposeMap,dataClassMap,dataSubjectMap,sensitiveDataMap,dataDiscloseeMap;
	private static MongoClientURI dbURI;
	private MongoClient client;
	private DB database;
	private DBCollection collection;
	private int type = 0;
	private DataController dataController;
	private GeneralStatistics generalStats;
	private Gson gson;
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
	
	public RegistryHandler() throws IOException {
		gson = new Gson();
		generalStats = new GeneralStatistics();
		out = new PrintWriter(new BufferedWriter(new FileWriter(
				"files/other/stats.txt")));
		dbURI = new MongoClientURI(
				"mongodb://admin:incorrect@ds033629.mongolab.com:33629/data_controllers");
		client = new MongoClient(dbURI);
		natureOfWorkSet = new HashSet<String>();
		dataPurposeSet = new HashSet<String>();
		dataSubjectSet = new HashSet<String>();
		dataClassSet = new HashSet<String>();
		sensitiveDataSet =new HashSet<String>();
		dataDiscloseeSet = new HashSet<String>();
		natureOfWorkMap = new HashMap<String,StatisticObject>();
		dataPurposeMap = new HashMap<String,StatisticObject>();
		dataClassMap = new HashMap<String,StatisticObject>();
		dataSubjectMap = new HashMap<String,StatisticObject>();
		sensitiveDataMap = new HashMap<String,StatisticObject>();
		dataDiscloseeMap = new HashMap<String,StatisticObject>();
		// client = new MongoClient("localhost",27017);
		// database = client.getDB("dataControllers");
		database = client.getDB(dbURI.getDatabase());
		String[] collections = { "generalStatistics", "natureOfWorkStats",
				"purposesStats", "dataClassesStats", "dataSubjectsStats",
				"dataDiscloseeStats", "registry" };
		dropCollections(collections);

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
			dataController = new DataController();
			generalStats.incrementRecordCount();
			break;
		case "REGISTRATION_NUMBER":
			type = REGISTRATION_NUMBER;
			break;
		case "ORGANISATION_NAME":
			type = ORGANISATION_NAME;
			break;
		case "COMPANIES_HOUSE_NUMBER":
			type = COMPANIES_HOUSE_NUMBER;
			generalStats.incrementCompaniesHouseCount();
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
			generalStats.incrementPostcodeCount();
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
			generalStats.incrementTradingNameCount();
			break;
		case "CONTACT_IN_UK_C1":
			type = UK_CONTACT;
			break;
		case "SUBJECT_ACCESS_CONTACT_C2":
			type = SUBJECT_ACCESS_CONTACT;
			break;
		case "NATURE_OF_WORK_DESCRIPTION":
			type = NATURE_OF_WORK;
			generalStats.incrementNatureOfWorkCount();
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
			generalStats.setPurposesCount(dataPurposeSet.size());
			generalStats.setDataClassesCount(dataClassSet.size());
			generalStats.setSensitiveDataCount(sensitiveDataSet.size());
			generalStats.setDataSubjectsCount(dataSubjectSet.size());
			generalStats.setDataDiscloseesCount(dataDiscloseeSet.size());
			
			out.println("Register statistics for 01/2014\n" 
					+ "\nRecords : "+ generalStats.getRecordCount() 
					+ "\nCompanies House Numbers : " + generalStats.getCompaniesHouseCount()
					+ "\nPostcodes : " + generalStats.getPostcodeCount() 
					+ "\nTrading Names : " + generalStats.getTradingNameCount()
					+ "\nNature of Work Descriptions : " + generalStats.getNatureOfWorkCount()
					+ "\nOld Data Formats (Purpose 1...) : " + generalStats.getOldBlobCount()
					+ "\nNew Data Formats (Nature of work...) : "+ generalStats.getNewBlobCount()
					+ "\n\nNeither Format : " + generalStats.getNeitherBlobCount()
					+ "\n\nPurpose count : " + generalStats.getPurposesCount() + " " + dataPurposeMap.size()
					+ "\nData Classes count : " + generalStats.getDataClassesCount() + " " + dataClassMap.size()
					+ "\nSensitive Data count : " + generalStats.getSensitiveDataCount()+ " " + sensitiveDataMap.size()
					+ "\nData Subjects count : " + generalStats.getDataSubjectsCount()+ " " + dataSubjectMap.size()
					+ "\nDataDisclosees count : " + generalStats.getDataDiscloseesCount()+ " " + dataDiscloseeMap.size()
					+ "\nNature of Work count : " + natureOfWorkSet.size()  + " " + natureOfWorkMap.size()
					+ "\n\nErrors in parsing : " + generalStats.getErrorCount()
					+ "\nNew format errors : " + generalStats.getNewErrorCount()
					+ "\nOld error count :" + generalStats.getOldErrorCount());
			
			addMapToDB(natureOfWorkMap, "natureOfWorkStats");
			addMapToDB(dataPurposeMap, "purposesStats");
			addMapToDB(dataClassMap, "dataClassesStats");
			addMapToDB(dataSubjectMap, "dataSubjectsStats");
			addMapToDB(dataDiscloseeMap, "dataDiscloseeStats");
			//addMap(map, collection);
			
			collection = database.getCollection("generalStatistics");
			document = (BasicDBObject) JSON.parse(gson.toJson(generalStats));
			collection.insert(document);
			client.close();
			out.close();
			
			System.out.println("done!");
			break;
		case "RECORD":
			System.out.println(generalStats.getRecordCount());
			document = (BasicDBObject)JSON.parse(gson.toJson(dataController));
			collection.insert(document);
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
		try {
			if (heading.contains("Nature")) {
				generalStats.incrementNewBlobCount();
				dataController.setFormat("new");
				newFormat(list);
			} else if (heading.contains("Purpose")) {
				generalStats.incrementOldBlobCount();
				dataController.setFormat("old");
				oldFormat(list);
				dataController.convertOldFormatToNewFormat();
			} else {
				generalStats.incrementNeitherBlobCount();
				dataController.setFormat("neither");
			}
		} catch (Exception e) {
			generalStats.incrementErrorCount();
			String format = dataController.getFormat();
			if (format.equals("old")) {
				generalStats.incrementOldErrorCount();
			} else if (format.equals("new")) {
				generalStats.incrementNewErrorCount();
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
				dataPurposeSet.add(purpose);
				addToMap(dataPurposeMap,purpose);
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
					dataSubjectSet.add(dataSubject);
					addToMap(dataSubjectMap,dataSubject);
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
					dataClassSet.add(dataClass);
					addToMap(dataClassMap,dataClass);
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
					dataDiscloseeSet.add(dataDisclosee);
					addToMap(dataDiscloseeMap,dataDisclosee);
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
				"classes of information processed", "information is processed about",
				"information may be shared with",
				"reasons/purposes for processing", "transfer",
				"crime prevention", "and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research" };
		String[] otherPurposes = {"crime prevention", "and advisory services",
				"trading and sharing personal information",
				"providing financial services and advice",
				"undertaking research"};
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
				natureOfWorkSet.add(newFormat.getNatureOfWork());
				addToMap(natureOfWorkMap,newFormat.getNatureOfWork());
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
								sensitiveDataSet.add(dataClass);
								addToMap(sensitiveDataMap,dataClass);
								newFormat.addSensitiveData(dataClass);
							} else {
								if(dataClass.split(" ").length < 15){
									dataClassSet.add(dataClass);
									addToMap(dataClassMap,dataClass);
								}								
								newFormat.addDataClass(dataClass);
							}
						}
						index++;
					}
				}
			}
			
			// Who the information is processed about
			if (list.get(index).contains("information is processed about")) {
				dataSubject = "";
				index += 1;
				if (headingsContain(list.get(index + 1), headings)) { //only one line
					dataSubject = list.get(index);
					newFormat.addDataSubject(dataSubject);
				} else {
					index += 1;
					while (!headingsContain(list.get(index), headings)) {
						dataSubject = list.get(index);
						dataSubjectSet.add(dataSubject);
						addToMap(dataSubjectMap,dataSubject);
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
						dataDiscloseeSet.add(dataDisclosee);
						addToMap(dataDiscloseeMap,dataDisclosee);
						newFormat.addDataDisclosee(dataDisclosee);
						index++;
					}
				}
			}
			
			// other purposes
			if (headingsContain(list.get(index), otherPurposes) && list.get(index).split(" ").length < 10) {
				otherPurpose = new OtherPurpose();
				dataPurposeSet.add(list.get(index));
				addToMap(dataPurposeMap,list.get(index));
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
	
	public void addMapToDB(HashMap<String,StatisticObject> map,String collectionName){
		System.out.println("Adding statisic map to database... (" + map.size() + " items)");
		collection = database.getCollection(collectionName);
		for(Map.Entry<String, StatisticObject> entry : map.entrySet()){
			BasicDBObject document = (BasicDBObject)JSON.parse(gson.toJson(entry.getValue()));
			collection.insert(document);
		}
	}

	public void addToMap(HashMap<String,StatisticObject> map,String text){
		RegistryListItem company = new RegistryListItem(dataController.getRegistrationNumber(),dataController.getOrganisationName());
		if(!map.containsKey(text)){
			StatisticObject statObject = new StatisticObject();
			statObject.setType(text);
			statObject.addCompany(company);
			map.put(text, statObject);
		}else{
			map.get(text).addCompany(company);
		}
	}
	
	public void dropCollections(String[] collectionNames){
		for(String collectionName : collectionNames){
			collection = database.getCollection(collectionName);
			collection.drop();
		}
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
